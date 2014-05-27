/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.versioning;

import java.util.Date;
import java.util.logging.Level;
import neembuu.release1.app.Application;
import davidepastore.StringUtils;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.settings.OnlineSettingImpl;

/** This thread checks for new updates at every launch.
 *
 * @author vigneshwaran
 */
public class CheckUpdate extends Thread {

    private final MainComponent mc;

    public CheckUpdate(MainComponent mc) {
        this.mc = mc;
    }
    
    public static void checkLater(MainComponent mc){
        CheckUpdate cu = new CheckUpdate(mc);
        cu.start();
    }
    
    //variables to store current and available version
    static long availablever, currentver;
    
    static Date availableDate(){
        return d(availablever);
    }
    
    static Date currentDate(){
        return d(currentver);
    }

    /**
     * 
     * @return whether the Neembuu is uptodate or not. If not, launch Update Notification window..
     */
    public boolean isCurrentVersion() {
        //Get the version.xml and read the version value.
        LoggerUtil.L().info("Checking for new version...");
        try {
            String respxml = OnlineSettingImpl.getRaw("version.xml");
            if(respxml==null)return true;
            
            availablever = getReleaseDateFromXML(respxml);            
            LoggerUtil.L().log(Level.INFO, "Available version: {0}", d(availablever));
            currentver = Application.releaseTime();

            LoggerUtil.L().log(Level.INFO, "Current version: {0}", d(currentver));

            //Compare both
            if (availablever > currentver) {
                return false;
            }
        } catch (Exception ex) {
            LoggerUtil.L().log(Level.INFO, "Exception while checking update\n{0}", ex);
        }

        return true;
    }
    
    /**
     * 
     * @param str
     * @return the value between <version> and </version> tags from the specified string.
     */
    public static long getReleaseDateFromXML(String str) {
        long ver = 0;
        String valStr = StringUtils.getSimpleXMLData(str, "releasedate");
        try {
            ver = Long.parseLong(valStr);
        } catch (Exception any) {
            LoggerUtil.L().severe(any.toString());
        }
        return ver;
    }
    
    @Override
    public void run() {
        if (!(isCurrentVersion())) {
            LoggerUtil.L().info("New version found..");
            new NotifyUpdate(mc).setVisible(true);
        }
    }
    
    private static Date d(long t){
        return new Date(t);
    }
    
}
