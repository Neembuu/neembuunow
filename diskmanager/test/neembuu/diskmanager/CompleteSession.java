package neembuu.diskmanager;

import java.util.List;
import org.junit.Test;

/**
 *
 * @author Shashank Tulsyan
 */


public class CompleteSession {
    public static void main(String[] args) throws Exception{
        /*Mar 29, 2012 7:29:10 PM jpfm.FormatterListener eventOccurred
        INFO: Formatter Event {Mount = NB_VirtualFileSystem@C:\Users\Shashank Tulsyan\NeembuuWatchAsYouDownload\Various Files (mountid=23) }{ {DETACHED : Unmount intiated internally was successful} } 
        Mar 29, 2012 7:29:11 PM org.jdownloader.extensions.neembuu.WatchAsYouDownloadSessionImpl$3 run
        SEVERE: Problem in completing session
        java.lang.IllegalStateException: Download incomplete or corrupt. Total downloaded=33577522 Expected filesize=33576498
                at neembuu.diskmanager.DefaultFileStorageManager.completeSession(DefaultFileStorageManager.java:199)
                at org.jdownloader.extensions.neembuu.WatchAsYouDownloadSessionImpl$3.run(WatchAsYouDownloadSessionImpl.java:274)*/
        DefaultDiskManager ddm = new DefaultDiskManager(new DiskManagerParams.Builder().setBaseStoragePath("C:\\Users\\Shashank Tulsyan\\Downloads").build());
        
        DefaultFileStorageManager dfsm = (DefaultFileStorageManager)ddm.makeNewFileStorageManager(new FileStorageManagerParams.Builder()
                .setFileName("tl")
                .setResumeStateCallback(new ResumeStateCallback() {
                    @Override
                    public boolean resumeState(List<RegionStorageManager> previouslyDownloadedData) {
                        return true;
                    }
                }).build()
        );
        
        dfsm.completeSession(new java.io.File("C:\\Users\\Shashank Tulsyan\\Downloads\\tl.flv"), 33576498);
    }
}
