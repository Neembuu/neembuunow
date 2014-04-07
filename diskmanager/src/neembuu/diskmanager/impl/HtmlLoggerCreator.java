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

package neembuu.diskmanager.impl;

import java.nio.channels.SeekableByteChannel;
import java.util.logging.Logger;
import neembuu.diskmanager.LoggerCreateSPI;
import neembuu.util.logging.LoggerUtil;

/**
 *
 * @author Shashank Tulsyan
 */
public final class HtmlLoggerCreator implements LoggerCreateSPI {

    private static final int fileLoggerLimit = 2*1024*1024;
    private static final int regionLoggerLimit = 100*1024;
    
    
    @Override public Logger make(SeekableByteChannel sbc,String displayName, Type type) {
        Logger l;
        try{
            int limit;
            switch (type) {
                case Session: 
                case File: limit = fileLoggerLimit; break;
                case Region: limit = regionLoggerLimit; break;
                default: throw new AssertionError();
            }
            l = LoggerUtil.getLightWeightHtmlLogger(displayName, sbc, limit);
        }catch(Exception a){
            Exception n = new Exception("Could not create html logger",a);
            n.printStackTrace();
            l = Logger.getLogger(displayName);
        }
        return l;
        
    }
    
}
