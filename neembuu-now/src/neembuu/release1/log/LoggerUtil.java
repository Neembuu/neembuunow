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
