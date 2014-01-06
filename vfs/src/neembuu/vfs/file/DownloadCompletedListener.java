/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.vfs.file;

import neembuu.vfs.annotations.MainDirectionDeciderThread;

/**
 * Called from main direction decider thread
 * @author Shashank Tulsyan
 */
public interface DownloadCompletedListener {
    @MainDirectionDeciderThread
    void downloadCompleted();
}
