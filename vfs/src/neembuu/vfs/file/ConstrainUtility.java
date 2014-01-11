/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
