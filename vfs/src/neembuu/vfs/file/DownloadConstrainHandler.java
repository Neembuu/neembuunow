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
package neembuu.vfs.file;

import neembuu.vfs.annotations.ReadQueueManagerThread;

/**
 *
 * @author Shashank Tulsyan
 */
public interface DownloadConstrainHandler {
    void receiveExternalZeroRequestObservation(DownloadConstrainHandler src,long creationTime);
    /**
     * Constraint is injective. To make it bijective both should apply on each other.
     * <pre>
     * DownloadConstrainHandler dch1,dch2;
     * dch1.constraintWith(dch2);
     * dch2.constraintWith(dch1);
     * </pre>
     * @param parent 
     */
    void constraintWith(DownloadConstrainHandler dch);
    
    /**
     * @return -1 if this constraint file is not a part of a series
     */
    int index();
    
    void setIndex(int index);
    
    //boolean isAutoCompleteEnabled();
    
    boolean hasAMainConnection();
    
    boolean hasPendingRequests();
    
    boolean isComplete();
    
    void unConstrain();
    
    /**
     * time to time called from ReadQueueManagerThread
     */
    @ReadQueueManagerThread
    void checkMessages();
}
