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
package neembuu.util.logging;

import java.net.InetAddress;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
//import org.apache.log4j.Priority;

final class ChainsawedLogger /*extends Logger*/ {

    /*private final org.apache.log4j.Logger logger;

    public ChainsawedLogger(String name) {
        super(name, Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).getResourceBundleName());

        System.setProperty("log4j.debug", "false");
        System.setProperty("log4j.defaultInitOverride", "false");
        System.setProperty("ignoreTCL", "false");


        logger = org.apache.log4j.Logger.getLogger(name);
        try {
            logger.addAppender(new org.apache.log4j.net.SocketAppender(
                    InetAddress.getByName("127.0.0.1"), 4445));
        } catch (Exception a) {
            throw new RuntimeException(a);
        }
        System.err.println("inited chainsawed "+name);
        this.setLevel(Level.ALL);
    }

    @Override
    public final void log(LogRecord record) {
        org.apache.log4j.Priority priority = toLog4j(record.getLevel());
        logger.log(priority, toLog4jMessage(record), record.getThrown());
        super.log(record);
    }

    private String toLog4jMessage(LogRecord record) {
        String message = record.getMessage();
        // Format message
        try {
            Object parameters[] = record.getParameters();
            if (parameters != null && parameters.length != 0) {
                // Check for the first few parameters ?
                if (message.indexOf("{0}") >= 0
                        || message.indexOf("{1}") >= 0
                        || message.indexOf("{2}") >= 0
                        || message.indexOf("{3}") >= 0) {
                    message = MessageFormat.format(message, parameters);
                }
            }
        } catch (Exception ex) {
            // ignore Exception
        }
        return message;
    }

    private org.apache.log4j.Level toLog4j(Level level) {//converts levels
        if (Level.SEVERE == level) {
            return org.apache.log4j.Level.ERROR;
        } else if (Level.WARNING == level) {
            return org.apache.log4j.Level.WARN;
        } else if (Level.INFO == level) {
            return org.apache.log4j.Level.INFO;
        } else if (Level.OFF == level) {
            return org.apache.log4j.Level.OFF;
        }
        return org.apache.log4j.Level.INFO;
    }

    public static void main(String[] args) throws Exception {
        ChainsawedLogger cl = new ChainsawedLogger(ChainsawedLogger.class.getName());
        System.in.read();

        cl.entering(ChainsawedLogger.class.getName(), "main");
        cl.log(Level.SEVERE, "Bachao", new Throwable());
        cl.log(Level.INFO, "Success");
        cl.entering(ChainsawedLogger.class.getName(), "main");
        cl.log(Level.SEVERE, "Bachao", new Throwable());
        //for (int i = 0; i < 600; i++) {
          //  Thread.sleep(500);
            //cl.log(Level.INFO, "Success");
        //}

        System.in.read();
    }*/
}
