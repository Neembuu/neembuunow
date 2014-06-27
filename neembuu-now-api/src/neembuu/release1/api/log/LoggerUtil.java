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
package neembuu.release1.api.log;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 *
 * @author Shashank Tulsyan
 */
public final class LoggerUtil {
    
    private static LoggerServiceProvider serviceProvider = null;

    public static void setServiceProvider(LoggerServiceProvider serviceProvider) {
        if(LoggerUtil.serviceProvider!=null){
            throw new IllegalStateException("Already initialized with "+LoggerUtil.serviceProvider);
        }
        LoggerUtil.serviceProvider = serviceProvider;
        L = getLogger("Global");
    }
    
    private static Logger L = Logger.getGlobal();
    
    public static Logger L(){
        return L;
    }

    public static Logger getLogger(String name) {
        if(serviceProvider==null){
            return Logger.getLogger(name);
        }
        return serviceProvider.getLogger(name,true,true);
    }
    
    /*public static Logger getFileLogger(String name) {
        if(serviceProvider==null){
            return Logger.getLogger(name);
        }
        Logger l = serviceProvider.getLogger(name,false,true);
        l.addHandler(new ConsoleHandler());
        return l;
    }*/
}
