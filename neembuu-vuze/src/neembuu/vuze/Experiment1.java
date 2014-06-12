/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.vuze;

import java.io.File;
import java.util.Properties;
import org.gudy.azureus2.plugins.PluginInterface;
import org.gudy.azureus2.plugins.PluginListener;
import org.gudy.azureus2.plugins.PluginManager;

/**
 *
 * @author Shashank Tulsyan
 */
public class Experiment1 {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put( PluginManager.PR_DISABLE_NATIVE_SUPPORT, "true" );
        File az_dir = new File("C:\\Users\\Shashank Tulsyan\\.neembuu-now\\release1\\vuze");

        props.put(PluginManager.PR_APP_DIRECTORY, az_dir.getAbsolutePath());
        props.put(PluginManager.PR_USER_DIRECTORY, az_dir.getAbsolutePath());

        File doc_dir = new File("J:\\Vuze_Downloads");

        props.put(PluginManager.PR_DOC_DIRECTORY, doc_dir.getAbsolutePath());

        PluginManager pm = PluginManager.startAzureus(
                PluginManager.UI_NONE,
                //PluginManager.UI_SWT,
                props);
        System.out.println("returned from start");
        Vuze vuze = new Vuze(pm);
        
        final DoStuff d = new DoStuff(vuze);
        
        
        final PluginInterface pi = pm.getDefaultPluginInterface();

        pi.addListener(
            new PluginListener() {
                @Override public void initializationComplete() {
                    d.doStuff();
                }
                @Override public void closedownInitiated() {
                    System.out.println("closeDownInitiated");
                }
                @Override public void closedownComplete() {
                    System.out.println("closedownComplete");
                }
            });
    }
}
