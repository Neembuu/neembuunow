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

package neembuu.release1.ui.linkpanel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import neembuu.vfs.connection.AbstractConnection;
import neembuu.vfs.connection.NewConnectionParams;


/**
 *
 * @author Shashank Tulsyan
 */
public final class FakeConnection extends AbstractConnection{
    
    private final String srcFilePath;
    private final double fakeSpeedTarget_inKiBps;
    
    public FakeConnection(String srcFilePath, 
            double fakeSpeedTarget_inKiBps, 
            NewConnectionParams cp) {
        super(cp);
        this.srcFilePath = srcFilePath;
        this.fakeSpeedTarget_inKiBps = fakeSpeedTarget_inKiBps;
    }
    
    
    @Override
    protected final void abortImpl() {
        synchronized (keepRunning){
            keepRunning.set(false);
            keepRunning.notify(); // wake it from sleep
        }
    }
    
    @Override
    protected final void connectAndSupplyImpl() throws IOException{
        
        long offset = getAdjustedOffset();
        long toSkip = offset;
        FileInputStream is = null;

        try{
            is = new FileInputStream(new File(srcFilePath));
        }catch(Exception ae){
            cp.getTransientConnectionListener().failed(ae,cp);
            return;
        }
        
        while(toSkip>0){
            toSkip = toSkip - is.skip(toSkip);
        }
        
        int firstByte = is.read();
        if(firstByte!=-1){
            // when the first byte is send to {@link neembuu.vfs.connection.AbstractConnection#write }
            // cp.getTransientConnectionListener().success is called
        }else {
            cp.getTransientConnectionListener().failed(new IllegalStateException("EOF"),cp);
            return;
        }
        
        try{
            int read = 0;
            byte[]b=new byte[1024];
            b[0]=(byte)firstByte;
            read = is.read(b,1,b.length-1);
            if(read!=-1)read+=1;
            while(read!=-1){
                write(b,0,read);
                read=is.read(b);
                synchronized (keepRunning){
                    double sleepTime = b.length/fakeSpeedTarget_inKiBps;
                    if(sleepTime>0){ //sleepTime = 0 means infinite sleep : O
                        keepRunning.wait((long)sleepTime);
                    }
                    if(!keepRunning.get()){
                        is.close();
                        return; // abort connection called
                    }
                }
            }
        }catch(Exception e){
            downloadThreadLogger.log(Level.SEVERE, "Connection terminated", e);
        }
        
        is.close();
    }
    
    private final AtomicBoolean keepRunning = new AtomicBoolean(true);
    
}

