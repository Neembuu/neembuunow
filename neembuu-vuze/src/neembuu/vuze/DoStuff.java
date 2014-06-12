/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.vuze;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpfm.FileAttributesProvider;
import jpfm.FormatterEvent;
import jpfm.JPfm;
import jpfm.MountListener;
import jpfm.fs.SimpleReadOnlyFileSystem;
import jpfm.mount.Mount;
import jpfm.mount.MountParams;
import jpfm.mount.MountParamsBuilder;
import jpfm.mount.Mounts;
import jpfm.volume.CommonFileAttributesProvider;
import jpfm.volume.vector.VectorRootDirectory;
import org.gudy.azureus2.plugins.PluginInterface;
import org.gudy.azureus2.plugins.download.Download;

/**
 *
 * @author Shashank Tulsyan
 */
public class DoStuff {
    private final Vuze v;

    public DoStuff(Vuze v) {
        this.v = v;
    }
    
    public void doStuff(){
        new Thread(){
            @Override public void run() {
                try {
                    doStuffImpl();
                } catch (Exception ex) {
                    Logger.getLogger(DoStuff.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }
    
    private void doStuffImpl() throws Exception{
        listDownloads();
        getSomeDownloadInternals();
        
        makeVirtualFileSystem(); // :D
    }
    
    private void listDownloads(){
        final PluginInterface pi = v.getPluginManager().getDefaultPluginInterface();
        Download[] downloads = pi.getDownloadManager().getDownloads();
        for (Download d : downloads) {
            System.out.println("Download: " + d.getName());
        }
    }
    
    private final List<DownloadWrapSFC> files = new ArrayList<>();
    
    private void getSomeDownloadInternals()throws Exception {
        final PluginInterface pi = v.getPluginManager().getDefaultPluginInterface();
        Download[] downloads = pi.getDownloadManager().getDownloads();
        
        for (Download d : downloads) {
            System.out.println("Download: " + d.getName());
            if(d.getName().contains("X-")){               
                DownloadWrapSFC dwsfc = new DownloadWrapSFC(d, 1);
                System.out.println(dwsfc.getName());
                System.out.println(dwsfc.getFileSize());
                
                files.add(dwsfc);
                /*SimpleReadRequest srr = new SimpleReadRequest(ByteBuffer.allocate(1024), 0);
                dwsfc.open();
                dwsfc.read(srr);*/
            }
        }
    }
    
    private void makeVirtualFileSystem(){
        
        VectorRootDirectory volume = new VectorRootDirectory(10, 3,CommonFileAttributesProvider.DEFAULT);
        SimpleReadOnlyFileSystem fs = new SimpleReadOnlyFileSystem(volume);
        for (DownloadWrapSFC file : files) {
            volume.add(file);
            file.setParent(volume);
        }
        
        try {
            final String mntLoc = "J:\\neembuu\\virtual\\monitored.nbvfs";
            JPfm.Manager manager =  JPfm.setDefaultManager();
            Mount m = Mounts.mount(new MountParamsBuilder()
                    .set(MountParams.ParamType.MOUNT_LOCATION, mntLoc)
                    .set(MountParams.ParamType.FILE_SYSTEM, fs)
                    .set(MountParams.ParamType.LISTENER, new MountListener() {
                        @Override public void eventOccurred(FormatterEvent event) {
                            try {
                                java.awt.Desktop.getDesktop().open(new java.io.File(mntLoc));
                                //mountEventReceived(event);
                            } catch (IOException ex) { ex.printStackTrace(); }
                        }
                    }).build());
        } catch (Exception ne) {
            ne.printStackTrace();
        }
    }
}
