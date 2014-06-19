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

package neembuu.release1.app;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import static java.nio.file.StandardOpenOption.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.util.Throwables;

/**
 *
 * @author Shashank Tulsyan
 */
public final class EnsureSingleInstance {
    
    public static final long Instantiated = System.currentTimeMillis();
    private long lastCall = Instantiated;
    
    private final Object lock = new Object();
    private volatile Thread thread = null;
    private SingleInstanceCheckCallback callback;
    
    private FileLock fileLock = null;
    
    private final Logger l = LoggerUtil.getLogger(EnsureSingleInstance.class.getName());

    public EnsureSingleInstance(){
    }
    
    public EnsureSingleInstance(SingleInstanceCheckCallback callback){
        this.callback = callback;
    }
    
    public void setCallback(SingleInstanceCheckCallback callback) {
        if(this.callback!=null)throw new IllegalStateException("Already intialized with "+this.callback);
        this.callback = callback;
    }
    
    public void startService(){
        if(callback==null)throw new IllegalStateException("callback not intialized");
        thread = Throwables.start(new Runnable() {
            @Override public void run() {
                boolean running = tryInstanceLock();
                if(running){
                    l.info("It seems an instance is already running");                    
                }else {
                    l.log(Level.FINE, "Finished {0}", Thread.currentThread().getName());
                }
            }
        },this.toString());
    }
    
    public void stopService(){
        thread = null;
        synchronized (lock){lock.notifyAll();}
    }
    
    private boolean tryInstanceLock(){
        try(FileChannel fc = FileChannel.open(Application.getResource(".instance"), READ, WRITE,CREATE)) {
            acquireFileLock(fc);
            l.log(Level.FINE, "Initiating on {0}", Instantiated);
            boolean proceed = callback.solelyRunning(Instantiated);
            if(!proceed){
                l.info("Callback denied permission to proceed");
                return false;
            }
            shutDownHook();
            startMonitoring(fc);
            return false;
        } catch (Exception e) {
            l.log(Level.INFO,"Error in locking",e );
            return true;
        }
    }
    
    private void acquireFileLock(FileChannel fc)throws IOException{
        fileLock = fc.tryLock(100,1024,false);
        if(fileLock==null){
            long previousInstance = previousInstanceTime(fc);
            markInstance(Instantiated, fc);
            l.log(Level.INFO, "It seems an instance is already running, since : {0}", previousInstance);
            callback.alreadyRunning(previousInstance);
            throw new IOException("could not lock");
        }
        markInstance(Instantiated, fc);
    }
    
    private long previousInstanceTime(FileChannel fc)throws IOException{
        ByteBuffer bb = ByteBuffer.allocate(8);
        fc.position(0);
        int r = fc.read(bb);
        if(r<bb.capacity()){ //partial read
            l.log(Level.FINE, "Partial read ={0}", r);
            return 0;
        } bb.flip();
        return bb.asLongBuffer().get();
    }

    private boolean markInstance(long timeStamp, FileChannel fc)throws IOException{
        l.log(Level.FINE, "making={0}", timeStamp);
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putLong(timeStamp);
        bb.flip();
        fc.position(0);
        int w = fc.write(bb);
        if(w<bb.capacity()){ //partial write
            l.log(Level.FINE, "Partial write ={0}", w);
            return false;
        }
        fc.force(true);
        l.info("done marking");
        
        /*long prv = previousInstanceTime(fc);
        l.info("prv="+prv+" cur="+timeStamp);*/
        
        return true;
    }
    private void shutDownHook(){
        ShutdownHook shutdownHook = new ShutdownHook();
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }
    
    private final class ShutdownHook extends Thread {
        @Override public void run() {
            try{
                fileLock.close();
            }catch(Exception a){
            }
            synchronized (lock){
                lock.notifyAll();
            }
        }
    }
    
    private void startMonitoring(FileChannel fc){
        l.info("starting looping");
        while(Thread.currentThread() == thread){ 
            try{
                loopElement(fc);
            }catch(Exception a){
                
            }
            tryWait();
        }
    }
    
    private void loopElement(FileChannel fc)throws IOException{
        long newTimeStamp = previousInstanceTime(fc);
        if(newTimeStamp > lastCall){
            lastCall = newTimeStamp;
            callback.attemptedToRun(newTimeStamp);
        } 
    }
    
    private void tryWait(){
        synchronized (lock){
            try{
                lock.wait(300);
            }catch(InterruptedException ie){

            }
        }
    }
    
}
