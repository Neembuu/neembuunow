/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.vfs.file;

import java.util.List;
import jpfm.operations.readwrite.ReadRequest;
import neembuu.vfs.connection.NewConnectionParams;

/**
 *
 * @author Shashank Tulsyan
 */
public interface TroubleHandler {
    /**
     * Called when several attempts to make a new connection failed. 
     * It is called when Neembuu needs a connection and there is no other
     * way out, but it has failed to do so and nothing can be done.
     * In default implementation the file is simply unmounted.
     * @param ncp The parameters of new connection.
     * @param numberOfRetries Number of attempts made to make a new connection
     */
    void cannotCreateANewConnection(
            NewConnectionParams ncp, int numberOfRetries);
    
    /**
     * This is called when a read request has been pending since a long time.
     * If the player being used is something other than vlc, it is surely in
     * "NOT RESPONDING" state since <b>atleastMillisec</b> . The program is waiting for
     * read requests to complete. The read requests cannot complete because they are 
     * so big or download speed is so less, that data required to satisfy them has not 
     * been downloaded yet. We can either send a buffer which has only zero and complete the request.
     * The application might crash as zeros are not valid data, following which the application might
     * simply make another read request, and try again. Sending zeros will atleast prevent
     * the application from getting stuck in "NOT RESPONDING" state. But it doesn't serve
     * the purpose of the user. User wants to watch actual video, not junk, but his internet
     * speed doesn't seem to be sufficient for the given file OR the file is simply not suitable
     * to be downloaded and watched simultaneously. The user should download the entire file.
     * The file can be unmounted if <b>atleastMillisec</b> is greater than say 2 minutes. 
     * This function might simply return if it thinks the Neembuu should continue to try. <br/>
     * The request request can be completed by simply calling {@link ReadRequest#complete(jpfm.JPfmError) }
     * passing <code>jpfm.JPfmError.SUCCESS</code> to it.
     * @param pendingReadRequest list of requests that have been pending since <code>atleastMillisec</code> milliseconds or more.
     * @param atleastMillisec 
     */
    void readRequestsPendingSinceALongTime(List<ReadRequest> pendingReadRequest, long atleastMillisec);
    
    /**
     * The responsible manager, checks for pending readrequest after {@link #DEFAULT_CHECKING_INTERAL_MILLISECONDS }
     * or {@link #preferredCheckingInterval() } whichever is lesser.
     * If it finds such a readrequest, it is forwarded to {@link #readRequestsPendingSinceALongTime(java.util.List, long) }
     * @return the minimum interval of time after which this trouble handler should be updated with 
     * list of requests pending.
     */
    long preferredCheckingInterval();
    
    /**
     * The responsible manager, checks if a readrequest is pending for atleast {@link #DEFAULT_PENDING_ATLEAST_FOR_MILLISECONDS }
     * or {@link #pendingAtleastFor() } whichever is lesser.
     * If it finds such a readrequest, it is forwarded to {@link #readRequestsPendingSinceALongTime(java.util.List, long) }
     * @return the number of milliseconds for which the request must be pending
     * requests pending for an interval less than this would be ignored
     * and not send to {@link #readRequestsPendingSinceALongTime(java.util.List, long) }
     */
    long pendingAtleastFor();
    
    static int DEFAULT_CHECKING_INTERAL_MILLISECONDS = 60000;
    static int DEFAULT_PENDING_ATLEAST_FOR_MILLISECONDS = 5000;
}
