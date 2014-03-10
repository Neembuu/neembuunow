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
import java.util.logging.Level;
import javax.swing.JOptionPane;
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
import neembuu.config.GlobalTestSettings;
import neembuu.diskmanager.DiskManager;
import neembuu.release1.Application;
import neembuu.release1.Main;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.VirtualFile;
import neembuu.release1.api.ui.IndefiniteTaskUI;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.access.AddRemoveFromFileSystem;
import neembuu.release1.api.ui.access.MainUIA;
import neembuu.release1.pismo.PismoInstaller;
import neembuu.vfs.file.TroubleHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public class MountManager {
    private Mount mount;
    private JPfm.Manager manager = null;
    
    private final VectorRootDirectory volume;
    private final SimpleReadOnlyFileSystem fs;
    
    private final MainComponent mainComponent;
    private final IndefiniteTaskUI indefiniteTaskUI;
    private final MainUIA mainUIA;
    private final TroubleHandler troubleHandler;
    private final DiskManager diskManager;

    public MountManager(MainComponent mainComponent, IndefiniteTaskUI indefiniteTaskUI, MainUIA mainUIA, TroubleHandler troubleHandler, DiskManager diskManager) {
        this.mainComponent = mainComponent;
        this.indefiniteTaskUI = indefiniteTaskUI;
        this.mainUIA = mainUIA;
        this.troubleHandler = troubleHandler;
        this.diskManager = diskManager;
        volume = new VectorRootDirectory(10, 3,CommonFileAttributesProvider.DEFAULT);
        fs = new SimpleReadOnlyFileSystem(volume);
        addRemoveFromFileSystem = new AddRemoveFromFileSystem_Root(volume,troubleHandler, diskManager,realFileProvider);
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
            mount = mount(0, Application.getResource("NeembuuVirtualFolder").toString());
        }catch(Exception a){
            Main.getLOGGER().log(Level.SEVERE,"Could not create NeembuuVirtualFolder",a);
            javax.swing.JOptionPane.showMessageDialog(mainComponent.getJFrame(),
                    "Could not initialize Java pismo file mount\n"+
                    "Pismo file mount might not be installed.\n"+
                    "Actual error message="+a.getMessage(),
                    "Error in initialize JPfm",
                    javax.swing.JOptionPane.ERROR_MESSAGE
                    ,GlobalTestSettings.ONION_EMOTIONS.I_AM_DEAD);
            System.exit(-1);
        }
    }
    
    private Mount mount(int attempt, String mntLoc) throws Exception {
        Mount m = null;
        boolean retry = true;// false;
        if (attempt >= 0) { //1 for testing //0 for release
            try {
                manager =  JPfm.setDefaultManager();
                m = Mounts.mount(new MountParamsBuilder()
                        .set(MountParams.ParamType.MOUNT_LOCATION, mntLoc)
                        .set(MountParams.ParamType.FILE_SYSTEM, fs)
                        .set(MountParams.ParamType.LISTENER, new MountListener() {
                            @Override public void eventOccurred(FormatterEvent event) {
                                successfullyMounted();
                            }
                        }).build());
            } catch (NullPointerException ne) {
                Main.getLOGGER().log(Level.SEVERE, "NullPointerException while test mounting", ne);
                Throwable e = JPfm.getLastException();
                if (e != null) {
                    Main.getLOGGER().log(Level.SEVERE, "", e);
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
                PismoInstaller.tryInstallingPismoFileMount(false, new ICBL(mainComponent, indefiniteTaskUI));
                return mount(attempt + 1, mntLoc);
            }
        }
        throw new RuntimeException("Neither can use pismo file mount nor can install it. Retried " + attempt + " time(s)");
    }
    
    private void successfullyMounted(){
        mainUIA.neembuuVirtualFolderButton().setEnabled(true);
        
        mainUIA.neembuuVirtualFolderButton().addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    File f = getMount().getMountLocation().getAsFile();
                    java.awt.Desktop.getDesktop().open(f);
                }catch(Exception a){
                    JOptionPane.showMessageDialog(null,a.getMessage(),"Could not open NeembuuFolder",JOptionPane.ERROR_MESSAGE);
                    Main.getLOGGER().log(Level.SEVERE,"Could not open NeembuuFolder",a);
                }
            }
        });
    }
    
    private final RealFileProvider realFileProvider =  new RealFileProvider() {
            @Override public File getRealFile(VirtualFile vf) {
                return new File(getMount().getMountLocation().getAsFile(),
                        vf.getConnectionFile().getName());      } }; 

    private final AddRemoveFromFileSystem addRemoveFromFileSystem;
    
    public final RealFileProvider getRealFileProvider() {  return realFileProvider; }
    public final AddRemoveFromFileSystem getAddRemoveFromFileSystem() { return addRemoveFromFileSystem; }
    
    
}
