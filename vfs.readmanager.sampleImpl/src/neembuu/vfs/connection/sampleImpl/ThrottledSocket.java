/*
 *  Copyright (C) 2014 Shashank Tulsyan
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.vfs.connection.sampleImpl;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import neembuu.vfs.connection.NewConnectionParams;
import neembuu.vfs.progresscontrol.ThrottleFactory;
import neembuu.vfs.progresscontrol.ThrottledInputStream;

/**
 * Other implementation might like to adjust both buffer size and interval
 * between consecutive requests for finer control on the resultant download speed.
 * @author Shashank Tulsyan
 */
final class ThrottledSocket extends Socket{
    private ThrottledInputStream throttledInputStream = null;
    private final NewConnectionParams ncp;
    /*private static final float THROTTLE_RATIO_LOWER_LIMIT  = 1.05F;
    private static final float REQUEST_SPEED_FACTOR  = 1.01F;
    private static boolean THROTTLING_ENABLED = GlobalTestSettings.getValue("THROTTLING_ENABLED");*/
    public ThrottledSocket(NewConnectionParams cp) {
        this(null,cp);
    }
    
    public ThrottledSocket(InputStream sourceInputStream,NewConnectionParams cp) {
        super(); this.ncp = cp;
        if(sourceInputStream!=null)
            throttledInputStream = new ThrottledInputStream(
                    sourceInputStream,
                    cp.getThrottle());
    }
   
    @Override
    public final InputStream getInputStream() throws IOException {
        synchronized (this) {
            if (throttledInputStream == null) {
                throttledInputStream = new ThrottledInputStream(
                    super.getInputStream(),
                    ncp.getThrottle()
                );
            }
        }
        return throttledInputStream;
    }        
    /*private double getThrottlingInverse_ver1(){
        // download speed after throttling already happening
        double effectiveDownloadSpeed = connection.getDownloadSpeed();
        // request rate in in bytes per second
        double requestSpeed  = connection.getConnectionParams().getReadRequestState().
                getRequestSpeed();
        double averageThrottleReciprocal = getAverageThrottleInverse();
        
        double availableDownloadSpeed = 
                effectiveDownloadSpeed
                / 
                (1
                - effectiveDownloadSpeed*averageThrottleReciprocal);
                
        requestSpeed *= REQUEST_SPEED_FACTOR;
        
        if(availableDownloadSpeed < requestSpeed){
            updateAverageThrottleReciprocal(0);
            updateThrottleState(ThrottleState.NOT_THROTTLING);
            return 0;
        }
        
        double throttleInverse 
                = 
                (availableDownloadSpeed - requestSpeed)
                /
                (requestSpeed*availableDownloadSpeed);
        
        //System.out.println("availableDownloadSpeed="+availableDownloadSpeed+" effectiveDownloadSpeed="+  effectiveDownloadSpeed + " requestSpeed="+  requestSpeed + " throttleInverse" + throttleInverse +"\n" );
        
        boolean normalThrottle = true;
        
        if(!connection.getConnectionParams().getReadRequestState().
                requestsPresentAlongThisConnection()
                //requestsPresentAlongSomeConnection()
                ){
            logThrottleState(359,"throttle till dead for con="+connection);
            normalThrottle = false;
        }
        
        if(normalThrottle){
            updateAverageThrottleReciprocal(throttleInverse);
            updateThrottleState(ThrottleState.NORMAL_THROTTLING);
            return throttleInverse;
        } // perform throttle till dead         
        // this situation means no more requests are being made on this connection
        // this might be temporary and thus after a very short time requests might be made 
        // on this connection again.
        // Or this might be permanent because user actually forwarded the video. 
        // That is why we must throttle smoothly and eventually close this connection.
        // Closing of connection must not be an abrupty process as it is not possible to say
        // for sure if the connection is dead.
        // When the inbuilt vlc media player is used, we "might" get hints.
        // Even so these are only hints, should be fully relied on.
        // 
        // Special throttling impl might be needed for avi files,
        // as in this, requests are "jumpy".
        // example :
        // 0-->100 , 300->305 , 101--->299
        // the requests are not exactly linear, they go ahead and then come back.
        // For this reason new connection creation strategy should also intelligently sense this.
        // for avi files more amount of buffering must be done, to make this effect non influential.


        // We are going to throttle more than average, the 

        long delta_t = System.currentTimeMillis() - 
                connection.getConnectionParams().getReadRequestState().
                    lastRequestTime();//the time instance when last request was made

        
        // assuming a half life of 1.5 seconds
        // and exponential decay
        
        double t_ = delta_t/1500;
        
        throttleInverse =
                Math.max(throttleInverse,Math.exp(t_));
        
        updateThrottleState(ThrottleState.THROTTLE_TILL_DEAD);
        
        //if(delta_t > 4500) throw new RuntimeException("killing" );
        
        updateAverageThrottleReciprocal(0); // zero because throttling is being 
        // influenced more by time difference than by original request speed.
        return throttleInverse;
    }*/    
}
