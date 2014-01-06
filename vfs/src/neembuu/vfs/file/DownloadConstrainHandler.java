/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
    
    boolean isAutoCompleteEnabled();
    
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
