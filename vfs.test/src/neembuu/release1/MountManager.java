/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import jpfm.FileAttributesProvider;
import jpfm.FormatterEvent;
import jpfm.JPfm;
import jpfm.MountListener;
import jpfm.fs.SimpleReadOnlyFileSystem;
import jpfm.mount.Mount;
import jpfm.mount.MountParams;
import jpfm.mount.MountParamsBuilder;
import jpfm.mount.Mounts;
import jpfm.util.UniversallyValidFileName;
import jpfm.volume.CommonFileAttributesProvider;
import jpfm.volume.vector.VectorRootDirectory;
import neembuu.config.GlobalTestSettings;
import neembuu.release1.api.IndefiniteTask;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.VirtualFile;
import neembuu.release1.api.ui.IndefiniteTaskUI;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.access.AddRemoveFromFileSystem;
import neembuu.release1.api.ui.access.MainUIA;
import neembuu.release1.pismo.InstallerCallbackListener;
import neembuu.release1.pismo.PismoInstaller;
import neembuu.release1.ui.InstallPermissionAndProgress;
import neembuu.release1.ui.NeembuuUI;

/**
 *
 * @author Shashank Tulsyan
 */
public class MountManager {
    Mount mount;
    JPfm.Manager manager = null;
    
    private final VectorRootDirectory volume = new VectorRootDirectory(10, 3,CommonFileAttributesProvider.DEFAULT);
    private final SimpleReadOnlyFileSystem fs = new SimpleReadOnlyFileSystem(volume);
    
    private final MainComponent mainComponent;
    private final IndefiniteTaskUI indefiniteTaskUI;
    private final MainUIA mainUIA;

    MountManager(MainComponent mainComponent, IndefiniteTaskUI indefiniteTaskUI, MainUIA mainUIA) {
        this.mainComponent = mainComponent;
        this.indefiniteTaskUI = indefiniteTaskUI;
        this.mainUIA = mainUIA;
    }

    public Mount getMount() {
        return mount;
    }
    
    public String getSuitableFileName(String filename){
        if(filename.length()>50){
            filename = filename.substring(0,50);
        }
        filename = UniversallyValidFileName.makeUniversallyValidFileName(filename);
        filename = checkConflict(filename);
        return filename;
    }

    public SimpleReadOnlyFileSystem getFileSystem() {
        return fs;
    }
    
    public VectorRootDirectory getRootDirectory(){
        return volume;
    }
    
    private String checkConflict(String filename){
        for(FileAttributesProvider fap : volume){
            if(fap.getName().equalsIgnoreCase(filename)){
                filename="2_"+filename;
                return checkConflict(filename);
            }
        }
        return filename;
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
                PismoInstaller.tryInstallingPismoFileMount(false, new ICBL());
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

    private final class ICBL implements InstallerCallbackListener {
        private IndefiniteTask installationProgress = null;
        private IndefiniteTask installationTakingTooLong = null;
        
        @Override
        public void informUserAboutInstallation() {
            InstallPermissionAndProgress.showMessage(mainComponent);
            installationProgress = indefiniteTaskUI.showIndefiniteProgress("Installing PismoFileMount");
        }

        @Override
        public void installationTakingTooLong(int c) {
            if(c<60){
                if(installationTakingTooLong!=null){
                   installationTakingTooLong.done();
                }
                installationTakingTooLong = indefiniteTaskUI.showIndefiniteProgress("Installation is taking longer than usual : "+c+"seconds ellapsed");
            }else if(c>60 && c<90){
                if(installationTakingTooLong!=null){
                   installationTakingTooLong.done();
                }
                installationTakingTooLong = indefiniteTaskUI.showIndefiniteProgress("Neembuu will quitting if it takes longer than 2mins to install this");
            }else if(c>90){
                JOptionPane.showMessageDialog(mainComponent.getJFrame(), "Installation of Pismo failed or stuck.", "Application will exit", JOptionPane.ERROR_MESSAGE);
                System.exit(c);
            }
        }

        @Override
        public void installationSuccessful() {
            if(installationTakingTooLong!=null){
                installationTakingTooLong.done();
            }
            if(installationProgress!=null){
                installationProgress.done();
            }
        }

        @Override
        public void installationFailed() {
            java.io.File logFilePath = Application.getResource("install_logs.txt");
            String logfileText = "The log file mentions the exact reason.";
            try{
                java.awt.Desktop.getDesktop().open(logFilePath);
            }catch(Exception a){
                logfileText = logfileText+"\n"+logFilePath.getName();
            }
            JOptionPane.showMessageDialog(mainComponent.getJFrame(), 
                    "Your sandboxing or antivirus environment \n"
                    + "might be preventing installation.\n"
                    + logfileText, 
                    "Installation failed. Neembuu will exit", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }
    
    private final RealFileProvider realFileProvider =  new RealFileProvider() {
            @Override public File getRealFile(VirtualFile vf) {
                return new File(getMount().getMountLocation().getAsFile(),
                        vf.getConnectionFile().getName());      } }; 

    private final AddRemoveFromFileSystem addRemoveFromFileSystem = new AddRemoveFromFileSystem() {
            @Override public void remove(VirtualFile v) {
                volume.remove(v.getConnectionFile());
                v.getConnectionFile().setParent(null);  
            }
            @Override  public void add(VirtualFile v) { 
                volume.add(v.getConnectionFile());
                v.getConnectionFile().setParent(volume); 
            } }; 
    
    public final RealFileProvider getRealFileProvider() {  return realFileProvider; }
    public final AddRemoveFromFileSystem getAddRemoveFromFileSystem() { return addRemoveFromFileSystem; }

}
