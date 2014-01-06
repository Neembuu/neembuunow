/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1;

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
import neembuu.release1.api.VirtualFile;
import neembuu.release1.pismo.InstallerCallbackListener;
import neembuu.release1.pismo.PismoInstaller;
import neembuu.release1.ui.InstallPermissionAndProgress;

/**
 *
 * @author Shashank Tulsyan
 */
public class MountManager implements InstallerCallbackListener{
    Mount mount;
    JPfm.Manager manager = null;
    
    private final VectorRootDirectory volume = new VectorRootDirectory(10, 3,CommonFileAttributesProvider.DEFAULT);
    private final SimpleReadOnlyFileSystem fs = new SimpleReadOnlyFileSystem(volume);

    public Mount getMount() {
        return mount;
    }
    
    public String getSuitableFileName(String filename){
        filename = UniversallyValidFileName.makeUniversallyValidFileName(filename);
        filename = checkConflict(filename);
        return filename;
    }
    
    private String checkConflict(String filename){
        for(FileAttributesProvider fap : volume){
            if(fap.getName().equalsIgnoreCase(filename)){
                filename+="1";
                return checkConflict(filename);
            }
        }
        return filename;
    }
    
    public void addFile(VirtualFile v){
        volume.add(v.getConnectionFile());
        v.getConnectionFile().setParent(volume);
    }
    
    public void removeFile(VirtualFile v){
        volume.remove(v.getConnectionFile());
        v.getConnectionFile().setParent(null);
    }
    
    public void initialize(Main m){
        try{
            mount = mount(0, Application.getResource("NeembuuVirtualFolder").toString());
        }catch(Exception a){
            Main.getLOGGER().log(Level.SEVERE,"Could not create NeembuuVirtualFolder",a);
            javax.swing.JOptionPane.showMessageDialog(m.getNui().getFrame(),
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
                            public void eventOccurred(FormatterEvent event) {
                                Main.get().getNui().successfullyMounted();
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
                PismoInstaller.tryInstallingPismoFileMount(false, this);
                return mount(attempt + 1, mntLoc);
            }
        }
        throw new RuntimeException("Neither can use pismo file mount nor can install it. Retried " + attempt + " time(s)");
    }

    @Override
    public void informUserAboutInstallation() {
        InstallPermissionAndProgress.showMessage(Main.get());
        Main.get().getNui().showIndefiniteProgress(true, "Installing PismoFileMount");
    }

    @Override
    public void installationTakingTooLong(int c) {
        if(c<10){
            Main.get().getNui().showIndefiniteProgress(true, "Installation is taking longer than usual : "+c+"seconds ellapsed");
        }else if(c>10 && c<20){
            Main.get().getNui().showIndefiniteProgress(true, "Neembuu will quitting if it takes longer than 2mins to install this");
        }else if(c>20){
            JOptionPane.showMessageDialog(Main.get().getNui().getFrame(), "Installation of Pismo failed or stuck.", "Application will exit", JOptionPane.ERROR_MESSAGE);
            System.exit(c);
        }
    }

    @Override
    public void installationSuccessful() {
        Main.get().getNui().showIndefiniteProgress(false,"");       
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
        JOptionPane.showMessageDialog(Main.get().getNui().getFrame(), 
                "Your sandboxing or antivirus environment \n"
                + "might be preventing installation.\n"
                + logfileText, 
                "Installation failed. Neembuu will exit", JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
    }
    
}
