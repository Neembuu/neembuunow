/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSource;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shashank Tulsyan
 */
public class Environment {

    public static File getRoot() {
        try {
            // todo :
            InputStream prof = null;
            File propertiesFile = null;
            CodeSource src = Main.class.getProtectionDomain().getCodeSource();
            //boolean assume = false;
            if (src == null) {
                System.err.println("Asumming because code source is null");
                throw new NullPointerException();
                //assume = true;
            } else {
                Main.class.getProtectionDomain().getCodeSource();
                if (src.getLocation().toString().endsWith("classes/")) {
                    String urlpth = src.getLocation().toString();
                    urlpth = urlpth.substring(0, urlpth.lastIndexOf('/'));
                    urlpth = urlpth.substring(0, urlpth.lastIndexOf('/'));
                    urlpth = urlpth.substring(0, urlpth.lastIndexOf('/'));
                    urlpth = urlpth.substring(0, urlpth.lastIndexOf('/'));
                    urlpth = urlpth.substring(0, urlpth.lastIndexOf('/') + 1);
                    urlpth = urlpth + "release1_development_environment/";
                    Logger.getGlobal().log(Level.INFO,"Running in development mode, using properties = " + urlpth);
                    return new File(new URL(urlpth).toURI());
                } else if (src.getLocation().toString().endsWith(".jar")) {
                    String urlpth = src.getLocation().toString();
                    urlpth = urlpth.substring(0, urlpth.lastIndexOf('/') + 1);
                    //urlpth = urlpth + "neembuu.properties";
                    Logger.getGlobal().log(Level.INFO,"Running from jar, using properties = " + urlpth);
                    propertiesFile = new File(new URL(urlpth).toURI());
                    return propertiesFile;
                } else {
                    Logger.getGlobal().log(Level.INFO,"Asumming because code source=" + src.getLocation().toString());
                    throw new NullPointerException();
                    //assume = true;
                }
            }

        } catch (Exception a) {
            Logger.getGlobal().log(Level.SEVERE, "Error initializing environment",a);
        }
        
        Logger.getGlobal().log(Level.INFO,"Could not initialize environment");
        throw new NullPointerException();
    }
}