/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.vfs.file;

import java.util.EventListener;
import jpfm.annotations.NonBlocking;

/**
 *
 * @author Shashank Tulsyan
 */
public interface RequestPatternListener extends EventListener{
    /**
     * Do not block as this is in the Read dispatch thread.
     * If you block this thread, the user's operating system applications
     * might become non-responsive
     * @param requestStarting
     * @param requestEnding 
     */
    @NonBlocking()
    void requested(long requestStarting,long requestEnding);
}
