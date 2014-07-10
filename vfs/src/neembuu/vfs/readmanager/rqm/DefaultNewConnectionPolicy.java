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

import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.vfs.connection.checks.CanSeek;
import neembuu.vfs.connection.checks.SeekingAbility;
import neembuu.vfs.readmanager.NewReadHandlerProvider;
import neembuu.vfs.readmanager.WaitForExpansionOrCreateNewConnectionPolicy;

/**
 *
 * @author Shashank Tulsyan
 */
public class DefaultNewConnectionPolicy implements WaitForExpansionOrCreateNewConnectionPolicy {

    private final NewReadHandlerProvider provider;
    private final static Logger l = Logger.getLogger(DefaultNewConnectionPolicy.class.getName());

    public DefaultNewConnectionPolicy(NewReadHandlerProvider provider) {
        this.provider = provider;
    }
    

    @Override
    public final String toString() {
        return "1. Assumes that there is no harm in creating many connection.\n"
                + "2. Finds what would take lesser time, new connection creation or downloading till requirement\n"
                + "3. Zero fill in case of infinite connection time\n"
                + "4. Makes slightly biased decision :\n"
                + "\tif creation of a new connection is better, but the advantage is less"
                + "\tthen new connection is not created.";
    }

    @Override
    public WaitForExpansionOrCreateNewConnectionPolicy.Result result(
            long offset, // ignored in our case, might be useful in case of bittorrent
            long estimatedTimeTakenForCreatingANewConnection,
            double previousConnectionSpeed_inKiBps,
            long distanceToCover,
            long fileSize) {
        boolean hasSeekingAbility = hasSeekingAbility(offset);
        if(!hasSeekingAbility){
            estimatedTimeTakenForCreatingANewConnection = Integer.MAX_VALUE;
        }
        
        double estimateTimeForExpansion = (distanceToCover * 1.0d
                / (previousConnectionSpeed_inKiBps * 1024)) * 1000; //convert to milliseconds
            /*System.err.println("offset="+offset+" estimateTimeForExpansion="+estimateTimeForExpansion
         +" estimatedTimeTakenForCreatingANewConnection="+estimatedTimeTakenForCreatingANewConnection+""
         + " d="+distanceToCover+" speed="+previousConnectionSpeed_inKiBps);*/
        if (estimatedTimeTakenForCreatingANewConnection < 0) {
            throw new IllegalArgumentException("Estimated time taken for creating new connection cannot be negative");
        }
        estimatedTimeTakenForCreatingANewConnection = Math.min(estimatedTimeTakenForCreatingANewConnection, Integer.MAX_VALUE);
        if (estimateTimeForExpansion - estimatedTimeTakenForCreatingANewConnection
                < 1000) {
                // expansion will take less time or will not take too long comparatively
            // too long is defined as 1000ms

            if (estimateTimeForExpansion > 7000 && estimatedTimeTakenForCreatingANewConnection < 10000
                    && hasSeekingAbility) {
                return WaitForExpansionOrCreateNewConnectionPolicy.Result.NEW_CONNECTION_SHOULD_BE_CREATED;
            } // is expansion will take more 
            // than x seconds do not expand. This is to avoid excessive downloading.

            if (estimatedTimeTakenForCreatingANewConnection >= Integer.MAX_VALUE || !hasSeekingAbility) {
                if (estimateTimeForExpansion <= 7000) {
                    return WaitForExpansionOrCreateNewConnectionPolicy.Result.WAIT_FOR_EXPANSION;//expand
                }
                return WaitForExpansionOrCreateNewConnectionPolicy.Result.ZERO_FILL_THIS_READ_REQUEST;
            }
            return WaitForExpansionOrCreateNewConnectionPolicy.Result.WAIT_FOR_EXPANSION;//expand
        }
        if(!hasSeekingAbility){
            return WaitForExpansionOrCreateNewConnectionPolicy.Result.ZERO_FILL_THIS_READ_REQUEST;
        }
        return WaitForExpansionOrCreateNewConnectionPolicy.Result.NEW_CONNECTION_SHOULD_BE_CREATED;//make new connection
    }

    
    int longSeekCheckCount = 0;
    @Override public boolean hasSeekingAbility(long offset){
        return hasSeekingAbility(offset,0);
    }
    
    private boolean hasSeekingAbility(long offset, int attempt){
        if(offset==0)return true; // don't block first connection dummy!
        final SeekingAbility sa = provider.seekingAbility();
        final long newConnectionCreationTime = provider.getNewHandlerCreationTime(offset);
                    //sa.get()==Seek
        if(sa.get()==CanSeek.NO)return false;
        if(newConnectionCreationTime >= Integer.MAX_VALUE){
            return false;
        }       
        if(sa.get()==CanSeek.YES)return true;
        l.log(Level.INFO,"Waiting for seekability check {0} {1} ",new Object[]{attempt,longSeekCheckCount});
        if(sa.get()!=CanSeek.UNKNOWN){
            l.log(Level.INFO,"Assuming eekability {0}",sa.get());
            return true;
        }
        if(attempt<5 && longSeekCheckCount<15){
            try{Thread.sleep(1000);}catch(Exception a){}
            longSeekCheckCount++;
            return hasSeekingAbility(offset, attempt+1);
        } // assume true
        return true;
    }
    
}
