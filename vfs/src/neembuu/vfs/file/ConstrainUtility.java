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
package neembuu.vfs.file;

import java.util.Iterator;
import java.util.LinkedList;
import jpfm.DirectoryStream;
import jpfm.FileAttributesProvider;

/**
 *
 * @author Shashank Tulsyan
 */
public final class ConstrainUtility {    
    public static void constrain(Iterable<FileAttributesProvider> ds){
        // first unconstrain
        Iterator<FileAttributesProvider> it = ds.iterator();
        LinkedList<DownloadConstrainHandler> handlers = new LinkedList<DownloadConstrainHandler>();
        while(it.hasNext()){
            FileAttributesProvider fap = it.next();
            if(fap instanceof DirectoryStream){
                // can be safely ignored
                continue;
            }else if(fap instanceof SeekableConnectionFile){
                SeekableConnectionFile scf = (SeekableConnectionFile)fap;
                scf.getDownloadConstrainHandler().unConstrain();
            }else {
                throw new IllegalArgumentException("The given directory contains unconstrainable item "+fap);
            }
        }
        
        
        
        // constrain agains
        it = ds.iterator();
        handlers = new LinkedList<DownloadConstrainHandler>();
        while(it.hasNext()){
            FileAttributesProvider fap = it.next();
            if(fap instanceof DirectoryStream){
                // can be safely ignored
                continue;
            }else if(fap instanceof SeekableConnectionFile){
                SeekableConnectionFile scf = (SeekableConnectionFile)fap;
                handlers.add(scf.getDownloadConstrainHandler());
            }else {
                throw new IllegalArgumentException("The given directory contains unconstrainable item "+fap);
            }
        }
        
        for(DownloadConstrainHandler dch : handlers){
            contraintAllWith(handlers, dch);
        }
    }
    
    private static void contraintAllWith(LinkedList<DownloadConstrainHandler> handlers, 
            DownloadConstrainHandler dch_toConstWith){
        for(DownloadConstrainHandler dch : handlers){
            if(dch==dch_toConstWith){continue; /*ignore*/ }
            
            dch.constraintWith(dch_toConstWith);
        }
    }
}
