/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.defaultImpl.linkgroup;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import neembuu.diskmanager.MakeSession;
import neembuu.diskmanager.Session;
import neembuu.release1.api.file.NeembuuFile;
import neembuu.release1.api.ui.actions.ReAddAction;

/**
 *
 * @author Shashank Tulsyan
 */
public final class Utils {
    
    static Session make(final MakeSession ms,final boolean acceptAlreadyExisiting)throws Exception{
        final AtomicReference<Session> ar = new AtomicReference<>(null);
        final AtomicReference<Exception> ex = new AtomicReference<>(null);
        final AtomicBoolean exists = new AtomicBoolean(false);
        
        ms.createNew(new MakeSession.CreatNewCallback() {
            @Override public void alreadyExists(Session s) {
                ar.set(s); exists.set(true);
            }@Override public void done(Session s) {
                ar.set(s);
            }@Override public void failed(Exception a) {
                ex.set(a);
            }});
        if(exists.get()){
            ms.cancel();
            if(!acceptAlreadyExisiting){throw new Exception("Link already exists."); }
        }if(ex.get()!=null){
            ms.cancel(); throw ex.get();
        }if(ar.get()==null){
            ms.cancel(); throw new NullPointerException("Could not obtain session, reason unknown");
        }
        return ar.get();
    }
    
    static String restoreDisplayName(Session s){
        try{
            SeekableByteChannel dp = s.getOrCreateResource(DisplayName_FileName, StandardOpenOption.READ);
            ByteBuffer bb=ByteBuffer.allocate(Math.min((int)dp.size(),4*1024));
            dp.read(bb);
            String t = new String(bb.array());
            try{dp.close();}catch(Exception a){a.printStackTrace();}
            return t;
        }catch(Exception a){
            a.printStackTrace();
        }
        return null;
    }
    
    public static ReAddAction.CallBack makeDisplayNameSaver(){
        return new DisplayNameSaver();
    }
    
    private static final String DisplayName_FileName = "displayName";
    
    private static final class DisplayNameSaver implements ReAddAction.CallBack {
        @Override
        public void doneCreation(NeembuuFile neembuuFile) {
            try{
                Session s = neembuuFile.getSession();
                SeekableByteChannel dp = s.getOrCreateResource(
                        neembuu.release1.defaultImpl.linkgroup.Utils.DisplayName_FileName, 
                        StandardOpenOption.WRITE,StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING);
                String displayName = neembuuFile.getMinimumFileInfo().getName();
                ByteBuffer bb=ByteBuffer.wrap(displayName.getBytes());
                dp.write(bb);
                try{dp.close();}catch(Exception a){a.printStackTrace();}
            }catch(Exception a){
                a.printStackTrace();
            }
        }
    }
}
