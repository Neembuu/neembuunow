/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.defaultImpl.linkgroup;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import neembuu.diskmanager.MakeSession;
import neembuu.diskmanager.Session;

/**
 *
 * @author Shashank Tulsyan
 */
final class Utils {
    
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
}
