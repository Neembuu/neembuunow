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
package neembuu.release1.mountmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpfm.DefaultManager;
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
import neembuu.diskmanager.DiskManager;
import neembuu.release1.app.Application;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.release1.api.open.Openers;
import neembuu.release1.api.ui.IndefiniteTaskUI;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.Message;
import neembuu.release1.api.ui.access.MinimalistFileSystem;
import neembuu.release1.api.ui.access.MainUIA;
import neembuu.release1.pismo.PismoInstaller;
import neembuu.vfs.file.TroubleHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public final class MountManager {
    private Mount mount;
    private JPfm.Manager manager = null;
    
    private final VectorRootDirectory volume;
    private final SimpleReadOnlyFileSystem fs;
    
    private final MainComponent mainComponent;
    private final IndefiniteTaskUI indefiniteTaskUI;
    private final MainUIA mainUIA;
    private final TroubleHandler troubleHandler;
    private final DiskManager diskManager;
    
    private final MinimalistFileSystem minimalistFileSystem;
    
    private static final Logger L = LoggerUtil.getLogger(MountManager.class.getName());
    
    public MountManager(MainComponent mainComponent, IndefiniteTaskUI indefiniteTaskUI, MainUIA mainUIA, TroubleHandler troubleHandler, DiskManager diskManager) {
        this.mainComponent = mainComponent;
        this.indefiniteTaskUI = indefiniteTaskUI;
        this.mainUIA = mainUIA;
        this.troubleHandler = troubleHandler;
        this.diskManager = diskManager;
        volume = new VectorRootDirectory(10, 3,CommonFileAttributesProvider.DEFAULT);
        fs = new SimpleReadOnlyFileSystem(volume);
        minimalistFileSystem = new MinimalistFileSystem_Root(volume,troubleHandler, diskManager,realFileProvider,fs);
    }

    public Mount getMount() {
        return mount;
    }
    
    public SimpleReadOnlyFileSystem getFileSystem() {
        return fs;
    }
    
    public VectorRootDirectory getRootDirectory(){
        return volume;
    }
    
    public void initialize(){
        try{
            mount = mount(0, Application.getResource(Application.Resource.VirtualFolderMountLocation));
        }catch(Exception a){
            L.log(Level.SEVERE,"Could not create NeembuuVirtualFolder",a);
            
            mainComponent.newMessage().error()
                .setMessage("Could not initialize Java pismo file mount\n"+
                    "Pismo file mount might not be installed.\n"+
                    "Actual error message="+a.getMessage())
                .setTitle("Error in initialize JPfm")
                .setEmotion(Message.Emotion.I_AM_DEAD)
                .show();
            
            System.exit(-1);
        }
    }
    
    private Mount mount(int attempt, Path mntLoc) throws Exception {
        Mount m = null;
        boolean retry = true;// false;
        if (attempt >= 0) { //1 for testing //0 for release
            try {
                manager =  JPfm.setDefaultManager(LoggerUtil.getLogger("JPfm.Manager"),new DefaultManager.LibraryLoader() {
                    @Override public boolean loadLibrary(Logger logger) {
                        try{System.loadLibrary("jpfm_x86_rev113");}
                        catch(Exception a){logger.log(Level.SEVERE,"could not load",a);return false;}
                        return true;
                    }
                },false);
                m = Mounts.mount(new MountParamsBuilder()
                        .set(MountParams.ParamType.MOUNT_LOCATION, mntLoc)
                        .set(MountParams.ParamType.FILE_SYSTEM, fs)
                        .set(MountParams.ParamType.LISTENER, new MountListener() {
                            @Override public void eventOccurred(FormatterEvent event) {
                                mountEventReceived(event);
                            }
                        }).build());
            } catch (NullPointerException ne) {
                L.log(Level.SEVERE, "NullPointerException while test mounting", ne);
                Throwable e = JPfm.getLastException();
                if (e != null) {
                    L.log(Level.SEVERE, "", e);
                    if (e.getMessage().equalsIgnoreCase("Pismo file mount incorrectly installed or not installed")) {
                        retry = true;
                    }
                }
            }
        }
        if (m != null) { return m; }

        if (retry) {
            if (attempt < 2) {
                // TODO : show a gui message informing user that Pismo is being
                // installed
                PismoInstaller.tryInstallingPismoFileMount(mainComponent,false, new ICBL(mainComponent, indefiniteTaskUI));
                return mount(attempt + 1, mntLoc);
            }
        }
        throw new RuntimeException("Neither can use pismo file mount nor can install it. Retried " + attempt + " time(s)");
    }
    
    int c = 0;
    private void mountEventReceived(FormatterEvent event){
        checkDetached(event);
        if(c>0)return;
        c++;
        
        addHelpFile();
        
        mainUIA.neembuuVirtualFolderButton().setEnabled(true);
        mainUIA.neembuuVirtualFolderButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                System.out.println("executing="+e);
                openVirtualFolder();
            }
        });
    }
    
    private void checkDetached(FormatterEvent event){
        if(event.getEventType()==FormatterEvent.EVENT.DETACHED){
            if(!event.getMessage().contains("external"))return;
            mainComponent.getJFrame().setVisible(false);
            mainComponent.getJFrame().setVisible(true);
            mainComponent.newMessage()
                    .setTitle("Virtual folder externally unmounted")
                    .setMessage("Some external tool might have initiated an\n"
                            + "unmount operation or and error might have occurred.\n"
                            + "\n"
                            + "The application is going to exit.")
                    .setTimeout(10000)
                    .show();
            System.exit(-1);
        }
    }
    
    private void openVirtualFolder(){
        String fileToOpen = "";
        try{
            File f = getMount().getMountLocation().getAsFile();
            fileToOpen = f.getAbsolutePath();
            Openers.I().openFolder(fileToOpen);
        }catch(Exception a){
            L.log(Level.SEVERE,"Could not open NeembuuFolder",a);
            mainComponent.newMessage().error()
                .setMessage(fileToOpen
                        + "\n.Reason : "
                        + "\n"+a.getMessage())
                .setTitle("Could not open virtual folder ")
                .show();
        }
    }
    
    private void addHelpFile(){
        // The file name and file contents should respect local language
        Path helpFile = Application.getResource(Application.Resource.Installation, "help","Getting started.pdf");
        try {
            if(!Files.exists(helpFile))return;
            if(!Files.isRegularFile(helpFile))return;
            jpfm.volume.RealFile rf = 
                jpfm.volume.RealFileProvider.getNonBlockingRealFile(helpFile.toAbsolutePath().toString(), volume);
            volume.add(rf);
        } catch (Exception e) {
            L.log(Level.SEVERE,"Could not put help file in virtual folder",e);
        }
    }
    
    private final RealFileProvider realFileProvider =  new RealFileProvider() {
            @Override public File getRealFile(String... relativePathInVirtualFileSystem) {
                String pth=getMount().getMountLocation().toString();
                for (String stringPath : relativePathInVirtualFileSystem) {
                    pth+=(File.separatorChar+stringPath);
                }return new File(pth);
            } }; 

    public final RealFileProvider getRealFileProvider() {  return realFileProvider; }
    public final MinimalistFileSystem getMinimalistFileSystem() { return minimalistFileSystem; }

}
