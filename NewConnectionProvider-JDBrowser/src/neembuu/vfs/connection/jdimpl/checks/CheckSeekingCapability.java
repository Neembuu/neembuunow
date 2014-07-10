/*
 * Copyright (C) 2014 Shashank Tulsyan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.vfs.connection.jdimpl.checks;

import neembuu.vfs.connection.checks.ContentSampleListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.util.Throwables;
import neembuu.vfs.connection.Connection;
import neembuu.vfs.connection.NewConnectionParams;
import neembuu.vfs.connection.TransientConnectionListener;
import neembuu.vfs.connection.checks.CanSeek;
import neembuu.vfs.connection.checks.SeekingAbilityImpl;
import neembuu.vfs.connection.jdimpl.JDHTTPConnection;
import neembuu.vfs.connection.jdimpl.JD_DownloadManager;

/**
 *
 * @author Shashank Tulsyan
 */
public class CheckSeekingCapability {
    private final JD_DownloadManager jddm;
    
    private final AtomicReference<byte[]> firstSample = new AtomicReference<byte[]>(null);
    private final AtomicReference<byte[]> offsetSample = new AtomicReference<byte[]>(null);
    
    private final SeekingAbilityImpl sai = new SeekingAbilityImpl();
    
    private static final Logger l = Logger.getLogger(CheckSeekingCapability.class.getName());
    
    
    public CheckSeekingCapability(JD_DownloadManager jddm) {
        this.jddm = jddm;
    }

    public SeekingAbilityImpl seekingAbility() { return sai; }   
    
    private void check(final NewConnectionParams triggeringCP)throws IOException{
        NewConnectionParams ncp = createNewParams(triggeringCP);
        final JDHTTPConnection c = new JDHTTPConnection(jddm, ncp);
        c.setContentSampleListener(getOffsetSampleListener(triggeringCP,c));
        try{c.connectAndSupply();}
        catch(Throwable t){   
            l.log(
                Level.SEVERE, "Seek checking connection closed ", t);
        }
    }
    
    private void proceedChecking(NewConnectionParams triggeringCP, long offset){
        final byte[]a = firstSample.get();
        final byte[]a2 = offsetSample.get();
        if(!checkNullOfEmpty(a, true, "first")){
            sai.update(CanSeek.NO,offset);
            return;
        }
        if(!checkNullOfEmpty(a2, false,"offset")){
            sai.update(CanSeek.NO,offset);
            return;
        }
        
        if(contentsEqual(firstSample.get(), offsetSample.get())){
            sai.update(CanSeek.NO,offset); // assumption
            // actually the content headers should be checked.
            offsetSample.set(null);//we will make another check attempt some time later
        }else { 
            sai.update(CanSeek.YES,offset);
        }
        
    }
    
    private boolean contentsEqual(byte[]a,byte[]a2){
        int length = Math.min(a.length, a2.length);
        for (int i=0; i<length; i++)
            if (a[i] != a2[i])
                return false;
        return true;
    }
    
    private boolean checkNullOfEmpty(byte[]a,boolean emptyCheck,String name){
        if(a==null){
            l.log(Level.SEVERE, "Cannot seek since "
                +name+ " sample is null");
            return false;
        }
        if(!emptyCheck)return true;
        boolean nonEmpty = false;
        for (byte b : a) {
            if(b!=0){
                nonEmpty=true;break;
            }
        }
        if(!nonEmpty){
            l.log(Level.SEVERE, "Cannot seek since "
                +name+ " sample is empty");
            return false;
        }//buffer looks empty, > 50% is filled with null
        return true;
    }
    
    private NewConnectionParams createNewParams(NewConnectionParams triggeringCP){
        return new NewConnectionParams.Builder()
            .copyFrom(triggeringCP)
            .setDownloadThreadLogger(l)
            .setDownloadDataChannel(new FakeDownloadDataChannel(triggeringCP.getDownloadDataChannel()))
            .setOffset(Math.min(2048, triggeringCP.getReadRequestState().fileSize()))
            .setTransientConnectionListener(new FakeTransientConnectionListener(
                    triggeringCP.getTransientConnectionListener(), 
                    l))
            .build();
    }
    
    private ContentSampleListener getOffsetSampleListener(final NewConnectionParams triggeringCP,
            final JDHTTPConnection c){
        return new ContentSampleListener() {
            @Override public void receiveContentSample(byte[] b,long offset) {
                offsetSampleChecker(triggeringCP, b, offset);
                if(c!=null){ c.abort(); }
            }
        };
    }
    
    private void offsetSampleChecker(final NewConnectionParams triggeringCP,byte[]b,long offset){
        if(!offsetSample.compareAndSet(null, b)){
            l.log(Level.SEVERE, "Receiving content offset sample more than once. new->{0}", offset);
        }
        proceedChecking(triggeringCP,offset);
    }
    
    public ContentSampleListener getFirstSampleListener(final NewConnectionParams triggeringCP){
        return new ContentSampleListener() {
            @Override public void receiveContentSample(final byte[] b,final long offset) {
                String c = new String(b, 0, Math.min(b.length,1024),Charset.forName("UTF-8"));
                if(!firstSample.compareAndSet(null, b)){
                    l.log(Level.SEVERE, "Receiving content sample more than once. new->{0}", c);
                    if(sai.get()!=CanSeek.NO || sai.get()!=CanSeek.YES){
                        Throwables.start(new Runnable() { @Override public void run() {
                            offsetSampleChecker(triggeringCP, b, offset);
                        }}, "Offset sample checker thread 2 {" + triggeringCP + "}");
                    }
                }else {
                    l.log(Level.SEVERE, "Received content sample {0}", c);
                    startCheckThread(triggeringCP);
                }                
            }};
    }
    
    private void startCheckThread(final NewConnectionParams triggeringCP){
        Throwables.start(new Runnable() { @Override public void run() {
            try { check(triggeringCP); }
            catch(Exception a){l
                .log(Level.SEVERE,"Failed to check seeking ability",a);}
        }}, "Checking seeking ability {" + triggeringCP + "}");
    }
    
    private final class FakeTransientConnectionListener implements TransientConnectionListener{   
        private final TransientConnectionListener inspiration;
        private final Logger l;

        public FakeTransientConnectionListener(TransientConnectionListener inspiration, Logger l) {
            this.inspiration = inspiration; this.l = l;
        }

        @Override public void describeState(String state) { 
            inspiration.describeState(state);
        }

        @Override public void reportNumberOfRetriesMadeSoFar(int numberOfretries) {
            l.log(Level.INFO, "Fake connection retry attempt{0}", numberOfretries);
        }

        @Override public void successful(Connection c, NewConnectionParams ncp) {
            l.log(Level.SEVERE, "Success event send {0} earlier value={1}", new Object[]{ncp, sai.get()});
        }

        @Override public void failed(Throwable reason, NewConnectionParams ncp) {
            l.log(Level.SEVERE, "Fake connection "+ncp+ "failed ", reason);
            sai.update(CanSeek.NO,ncp.getOffset());
        }
    }
}