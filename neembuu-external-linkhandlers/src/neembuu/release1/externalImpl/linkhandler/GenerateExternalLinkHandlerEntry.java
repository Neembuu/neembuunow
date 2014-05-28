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

package neembuu.release1.externalImpl.linkhandler;

import java.lang.annotation.Annotation;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import neembuu.release1.defaultImpl.external.ExternalLinkHandlerEntry;
import neembuu.release1.defaultImpl.external.ExternalLinkHandlerProviderAnnotation;
import neembuu.util.CalculateHash;

/**
 *
 * @author Shashank Tulsyan
 */
public class GenerateExternalLinkHandlerEntry {
    private final Class c;

    public GenerateExternalLinkHandlerEntry(Class c) {
        this.c = c;
    }
    
    public static String HASHING_ALGORITHM = "SHA-1";
    
    public ExternalLinkHandlerEntry getExternalLinkHandlerEntry()throws Exception{
        ExternalLinkHandlerProviderAnnotation annot = getAsAnnotation();
        if(annot==null){
            throw new IllegalStateException("The class "+c+" has not been annotated properly");
        }
        ExternalLinkHandlerEntryImpl entryImpl = new ExternalLinkHandlerEntryImpl(annot);
        
        Path[]localPaths = resolveLocalPaths();
        
        String[]hashes = new String[localPaths.length];
        for (int i =0; i < localPaths.length; i++) {
            hashes[i]=calcFileHash(localPaths[i]);
        }
        
        entryImpl.setClassName(c.getName());
        entryImpl.setResourcesHash(hashes);
        
        return entryImpl;
    }
    
    private Path[]resolveLocalPaths()throws Exception{
        ArrayList<Path> localPaths = new ArrayList<Path>();
        Path baseDir = Paths.get(c.getResource(".").toURI());
        
        try(DirectoryStream<Path> ds = Files.newDirectoryStream(baseDir)){
            for(Path p : ds){
                if(p.getFileName().toString().startsWith(c.getSimpleName())){
                    localPaths.add(p);
                }
            }
        }
        return localPaths.toArray(new Path[localPaths.size()]);
    }
    
    private ExternalLinkHandlerProviderAnnotation getAsAnnotation(){
        try{
            ExternalLinkHandlerProviderAnnotation a = (ExternalLinkHandlerProviderAnnotation)c.getAnnotation(ExternalLinkHandlerProviderAnnotation.class);
            if(a!=null)return a;
            
            for(Annotation a1 : c.getAnnotations()){
                System.out.println("a1="+a1);
                if(a1.annotationType().equals(ExternalLinkHandlerProviderAnnotation.class)){
                    return (ExternalLinkHandlerProviderAnnotation)a1;
                }
            }
            throw new IllegalStateException("could not find annotation");
        }catch(Exception a){
            a.printStackTrace();
            return null;
        }
    }
    
    private String calcFileHash(Path filePath) {
        return CalculateHash.asString(filePath, HASHING_ALGORITHM);
    }        
}
