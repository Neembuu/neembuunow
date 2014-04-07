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

import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Logger;
import neembuu.diskmanager.DiskManagerParams;
import neembuu.diskmanager.LoggerCreateSPI;
import neembuu.diskmanager.SeekableByteChannelCreator;

/**
 *
 * @author Shashank Tulsyan
 */
public final class Utils {
    
    public static final Logger createLogger(
            String nm,DiskManagerParams dmp, SeekableByteChannelCreator sbcc,LoggerCreateSPI.Type t){
        SeekableByteChannel sbc = null; 
        try{ sbc = sbcc.getOrCreateResource(nm,
                WRITE, CREATE, TRUNCATE_EXISTING);}catch(Exception a){
            return Logger.getLogger(nm);
        }
        return dmp.getLoggerCreateSPI().make(sbc,nm,t);
    }
    
}
