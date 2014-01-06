/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.log;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.Application;
import neembuu.release1.Main;

/**
 *
 * @author Shashank Tulsyan
 */
public final class LoggerUtil {

    public static Logger getLogger() {
        return getLogger(null);
    }

    public static Logger getLogger(String name) {
        if (name == null) {
            try {
                name = sun.reflect.Reflection.getCallerClass(2).getName();
            } catch (Exception a) {
                Main.getLOGGER().log(Level.SEVERE, "Problem in using fast class name getter", a);
                name = Thread.currentThread().getStackTrace()[2].getClassName();
            }
        }
        java.util.logging.Logger logger = null;
        try {
            logger = neembuu.util.logging.LoggerUtil.getLightWeightHtmlLogger(name, Application.getHome(), 1024 * 1024);
        } catch (IOException ioe) {
            Main.getLOGGER().log(Level.SEVERE, "Could not create HTML logger",ioe);
            logger = neembuu.util.logging.LoggerUtil.getLogger(name);
        }
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(true);
        return logger;

    }
}
