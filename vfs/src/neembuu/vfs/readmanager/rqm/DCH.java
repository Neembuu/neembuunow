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
package neembuu.vfs.readmanager.rqm;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.vfs.file.DownloadConstrainHandler;
import neembuu.vfs.readmanager.RegionHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public class DCH implements DownloadConstrainHandler {

    private int index = -2;
    
    private volatile long lastExternalRequestTime = 0;
    
    private final DCH_RQM_Access RQM_Access;
    private final LinkedList<DownloadConstrainHandler> constraintedWith = 
            new LinkedList<DownloadConstrainHandler>();
    private final AtomicBoolean requiredConnectionAtZero = new AtomicBoolean(false);

    public DCH(DCH_RQM_Access RQM_Access) { this.RQM_Access = RQM_Access; }
    
    List<DownloadConstrainHandler> constraintedWith(){ return constraintedWith; }
    
    long lastExternalRequestTime(){ return lastExternalRequestTime; }
    
    boolean message_requiredConnectionAtZero(){
        return requiredConnectionAtZero.compareAndSet(false, true);
    }
    
    void sendZeroRequestObservation(int ele_s/*inclusive*/, long creationTime,boolean x){
        synchronized (RQM_Access.handlers().getModLock()){
            sendZeroRequestObservation(ele_s, RQM_Access.handlers().size()-1,creationTime,x);
        }
    }
    
    void sendZeroRequestObservation(int ele_s/*inclusive*/, int ele_e/*inclusive*/, long creationTime,boolean x){
        synchronized (RQM_Access.handlers().getModLock()){
            // lock held handlers.getModLock()
            if(ele_s>=RQM_Access.handlers().size())return;
            for (int i = ele_s; i <= ele_e; i++) {
                RQM_Access.rqmLogger().log(Level.INFO,"sendingzero"+(x?" external =":" =")
                        + RQM_Access.handlers().get(i));
                try{
                    (RQM_Access.handlers().get(i).getProperty()).addNewZeroRequestObservation(creationTime);
                }catch(ClassCastException cce){
                    RQM_Access.rqmLogger().log(Level.SEVERE, RQM_Access.handlers().get(i).toString(),cce);
                    throw new RuntimeException(cce);
                    //ignore
                }
            }
        }
        // lock still held
    }

    
    @Override
    public void checkMessages() {
        if(requiredConnectionAtZero.compareAndSet(true, false)){
            // make a fake read request
            SpecialReadRequest rr = new SpecialReadRequest();
            RQM_Access.rqmLogger().log(Level.SEVERE,"Received a special request to create conneciton at zero. Sending fake request "+rr);        
            RQM_Access.read(rr);
        }else {
            // nothing happend
        }
    }

    @Override
    public boolean hasAMainConnection() {
        return RQM_Access.hasAMainConnection(); 
    }

    @Override
    public String toString() {
        return DCH.class.getName() + "{" + RQM_Access.provider_getName() + "}";
    }

    // invoked from other RQMs only
    @Override public void receiveExternalZeroRequestObservation(DownloadConstrainHandler src, long creationTime) {
        lastExternalRequestTime = creationTime;
        
        if(src==this){throw new IllegalArgumentException("Should be invoked from an external RQM");}
        
        synchronized (RQM_Access.handlers().getModLock()){
            sendZeroRequestObservation(0, creationTime,true);
        }
    }

    @Override
    public void constraintWith(DownloadConstrainHandler dch) {
        synchronized (constraintedWith){
            constraintedWith.add(dch);
            Comparator<DownloadConstrainHandler> c = new Comparator<DownloadConstrainHandler> () {

                @Override
                public int compare(DownloadConstrainHandler o1, DownloadConstrainHandler o2) {
                    return o1.index()-o2.index();
                }
            };

            Collections.sort(constraintedWith,c);
        }
    }

    @Override public void unConstrain() {
        synchronized (constraintedWith){
            Iterator<DownloadConstrainHandler> it = constraintedWith.iterator();
            while(it.hasNext()){it.next();it.remove();}
        }
    }

    @Override public boolean isComplete() {
        return RQM_Access.isComplete();
    }

    @Override public boolean hasPendingRequests() {
        UnsyncRangeArrayCopy<RegionHandler> uhandlers
                = RQM_Access.handlers().tryToGetUnsynchronizedCopy();
        for (int i = 0; i < uhandlers.size(); i++) {
            if (uhandlers.get(i).getProperty().hasPendingRequests()) {
                return true;
            }
        }
        return false;
    }

    @Override public int index() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        if (this.index != -2) {
            throw new IllegalStateException("Already initialized to " + this.index);
        }
        this.index = index;
    }
}
