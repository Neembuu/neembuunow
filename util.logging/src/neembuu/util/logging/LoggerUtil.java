/*
 *  Copyright (C) 2010 Shashank Tulsyan
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General public final License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General public final License for more details.
 * 
 *  You should have received a copy of the GNU General public final License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.util.logging;

import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.util.ResourceBundle;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * <pre>
 * private static final Logger LOG = LoggerUtil.getLogger();
 * Taken from :
 * http://stackoverflow.com/questions/80692/java-logger-that-automatically-determines-callers-class-name
 * </pre>
 * @author Shashank Tulsyan
 */
public final class LoggerUtil {

    public final static Level level=Level.ALL;

    public static java.util.logging.Logger getLightWeightLogger(String name) {
        if(name==null){
                throw new IllegalArgumentException("name should not be null");
                /*try{
                    name = sun.reflect.Reflection.getCallerClass(2).getName();//takes around 40microseconds
                }catch(Exception a){
                    Logger.getGlobal().log(Level.SEVERE,"Problem in using fast class name getter",a);
                    name = Thread.currentThread().getStackTrace()[2].getClassName();// takes around 228.3 microsec
                }*/
            }
        return new LightWeightLogger(name);
        //return LoggerImpl.SINGLETON;
    }
    
    public static java.util.logging.Logger getLightWeightHtmlLogger(
            String name,
            SeekableByteChannel fc,
            int limit)throws IOException{
        Logger logger = LoggerUtil.getLightWeightLogger(name);
        Handler[]hs=logger.getHandlers();
        for (int i = 0; i < hs.length; i++) {
            logger.removeHandler(hs[i]);       
        }
        //boolean exists = new java.io.File(store_path + java.io.File.separator + name+"_log.html").exists();
        
        java.util.logging.Handler fh = 
                //new java.util.logging.FileHandler(
                new CustomFileHandler(
                    fc,
                    limit,
                    1,
                    true
                );
        logger.addHandler(fh);
        
        logger.getHandlers()[0].setFormatter(new HtmlFormatter());
        logger.getHandlers()[0].setLevel(Level.ALL);
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
        return logger;
    }

   public static java.util.logging.Logger getLogger(String name) {
        if(true){
            if(name==null){
                throw new IllegalArgumentException("name is null");
                /*try{
                    name = sun.reflect.Reflection.getCallerClass(2).getName();
                }catch(Exception a){
                    Logger.getGlobal().log(Level.SEVERE,"Problem in using fast class name getter",a);
                    name = Thread.currentThread().getStackTrace()[2].getClassName();
                }*/
            }
            final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(name);
            logger.setLevel(level);

            return logger;
        }else{
            return LoggerImpl.SINGLETON;
        }
    }
    
    private static final class LoggerImpl extends Logger{
        private static final LoggerImpl SINGLETON = new LoggerImpl();

        private LoggerImpl() {
            super("NO_LOGGER",Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).getResourceBundleName());
        }

        @Override
        public final void addHandler(Handler handler) throws SecurityException {
            return;
        }

        @Override
        public final void config(String msg) {
            return;
        }

        @Override
        public final void entering(String sourceClass, String sourceMethod) {
            return;
        }

        @Override
        public final void entering(String sourceClass, String sourceMethod, Object param1) {
            return;
        }

        @Override
        public final void entering(String sourceClass, String sourceMethod, Object[] params) {
            return;
        }

        @Override
        public final void exiting(String sourceClass, String sourceMethod) {
            return;
        }

        @Override
        public final void exiting(String sourceClass, String sourceMethod, Object result) {
            return;
        }

        @Override
        public final void fine(String msg) {
            return;
        }

        @Override
        public final void finer(String msg) {
            return;
        }

        @Override
        public final void finest(String msg) {
            return;
        }

        @Override
        public final Filter getFilter() {
            return null;
        }

        @Override
        public final Handler[] getHandlers() {
            return null;
        }

        @Override
        public final Level getLevel() {
            return Level.OFF;
        }

        @Override
        public final String getName() {
            return super.getName();
        }

        @Override
        public final Logger getParent() {
            return null;
        }

        @Override
        public final ResourceBundle getResourceBundle() {
            return null;
        }

        @Override
        public final String getResourceBundleName() {
            return null;
        }

        @Override
        public final boolean getUseParentHandlers() {
            return super.getUseParentHandlers();
        }

        @Override
        public final void info(String msg) {
            return ;
        }

        @Override
        public final boolean isLoggable(Level level) {
            return false;
        }

        @Override
        public final void log(LogRecord record) {
            return ;
        }

        @Override
        public final void log(Level level, String msg) {
            return ;
        }

        @Override
        public final void log(Level level, String msg, Object param1) {
            return ;
        }

        @Override
        public final void log(Level level, String msg, Object[] params) {
            return ;
        }

        @Override
        public final void log(Level level, String msg, Throwable thrown) {
            return ;
        }

        @Override
        public final void logp(Level level, String sourceClass, String sourceMethod, String msg) {
            return ;
        }

        @Override
        public final void logp(Level level, String sourceClass, String sourceMethod, String msg, Object param1) {
            return ;
        }

        @Override
        public final void logp(Level level, String sourceClass, String sourceMethod, String msg, Object[] params) {
            return ;
        }

        @Override
        public final void logp(Level level, String sourceClass, String sourceMethod, String msg, Throwable thrown) {
            return ;
        }

        @Override
        public final void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg) {
            return ;
        }

        @Override
        public final void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Object param1) {
            return ;
        }

        @Override
        public final void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Object[] params) {
            return ;
        }

        @Override
        public final void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Throwable thrown) {
            return ;
        }

        @Override
        public final void removeHandler(Handler handler) throws SecurityException {
            return ;
        }

        @Override
        public final void setFilter(Filter newFilter) throws SecurityException {
            return ;
        }

        @Override
        public final void setLevel(Level newLevel) throws SecurityException {
            return ;
        }

        @Override
        public final void setParent(Logger parent) {
            return ;
        }

        @Override
        public final void setUseParentHandlers(boolean useParentHandlers) {
            return ;
        }

        @Override
        public final void severe(String msg) {
            return ;
        }

        @Override
        public final void throwing(String sourceClass, String sourceMethod, Throwable thrown) {
            return ;
        }

        @Override
        public final void warning(String msg) {
            return ;
        }

    }
}

