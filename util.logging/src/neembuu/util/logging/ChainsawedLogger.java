/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
