/*
 * Copyright (C) 2014 Shashank Tulsyan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.release1.defaultImpl.log;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.Main;
import neembuu.release1.api.log.LoggerServiceProvider;
import neembuu.release1.app.Application;

/**
 *
 * @author Shashank Tulsyan
 */
public class LoggerServiceProviderImpl implements LoggerServiceProvider{

    @Override public Logger getLogger(String name) {
        if (name == null) {
            try {
                Main.getLOGGER().log(Level.SEVERE, "Using fast class name @depcrecated in jdk8");
                name = sun.reflect.Reflection.getCallerClass(4).getName();
            } catch (Exception a) {
                Main.getLOGGER().log(Level.SEVERE, "Problem in using fast class name getter", a);
                name = Thread.currentThread().getStackTrace()[4].getClassName();
            }
        }
        java.util.logging.Logger logger = null;
        try {
            SeekableByteChannel sbc = FileChannel.open(Application.getResource(
                    Application.Resource.Logs,name+".log.html"), WRITE, CREATE, TRUNCATE_EXISTING);
            logger = neembuu.util.logging.LoggerUtil.getLightWeightHtmlLogger(name, sbc, 1024 * 1024);
            logger.addHandler(new ConsoleHandler());
        } catch (IOException ioe) {
            Main.getLOGGER().log(Level.SEVERE, "Could not create HTML logger",ioe);
            logger = Logger.getLogger(name);
            //logger = neembuu.util.logging.LoggerUtil.getLogger(name);
        }
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(true);
        return logger;
    }
}
