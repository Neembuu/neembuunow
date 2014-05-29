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

import java.io.FileOutputStream;
import java.lang.annotation.Annotation;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import neembuu.release1.defaultImpl.external.ELHEntryImpl;
import neembuu.release1.defaultImpl.external.ELHProvider;
import neembuu.util.CalculateHash;

/**
 *
 * @author Shashank Tulsyan
 */
public class GenerateELHEntry {
    private final Class c;
    private final Path destDir;

    public GenerateELHEntry(Class c, Path destDir) {
        this.c = c;
        this.destDir = destDir;
    }

    public static String HASHING_ALGORITHM = "SHA-1";
    
    public ELHEntryImpl generate()throws Exception{
        ELHProvider annot = getAsAnnotation();
        if(annot==null){
            throw new IllegalStateException("The class "+c+" has not been annotated properly");
        }
        ELHEntryImpl entryImpl = new ELHEntryImpl(annot);
        String zipName = c.getSimpleName()+".zip";
        
        Path[]localPaths = resolveLocalPaths();
        
        Path zipPth = destDir.resolve(zipName);
        System.out.println("zipPth="+zipPth);
        if(Files.exists(zipPth))Files.delete(zipPth);
        
        String[] hashes;
        
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPth.toFile()))) {
            zos.setLevel(9);
            hashes = new String[localPaths.length];
            for (int i =0; i < localPaths.length; i++) {
                
                String nm = c.getName(); nm = nm.substring(0,nm.lastIndexOf('.')+1);
                nm = nm.replace('.', '/');
                nm = nm + localPaths[i].getFileName().toString();
                ZipEntry ze = new ZipEntry(nm);
                zos.putNextEntry(ze);
                byte[]b=Files.readAllBytes(localPaths[i]);
                zos.write(b, 0, b.length);
                zos.closeEntry();
                zos.finish();
            }   
            
        }
        
        hashes=new String[]{calcFileHash(zipPth)};
        
        entryImpl.setDependenciesLocalPath(new String[]{zipPth.toString()});
        entryImpl.setMinimumReleaseVerReq(annot.minimumReleaseVerReq());
        entryImpl.setLastWorkingOn(System.currentTimeMillis());
        entryImpl.setBroken(annot.isBroken());
        entryImpl.setClassName(c.getName());
        entryImpl.setResourcesHash(hashes);
        entryImpl.setDependenciesURL(new String[]{
            "http://neembuu.com/now/update/neembuu-external-linkhandlers/"+zipName});
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
    
    private ELHProvider getAsAnnotation(){
        try{
            ELHProvider a = (ELHProvider)c.getAnnotation(ELHProvider.class);
            if(a!=null)return a;
            
            for(Annotation a1 : c.getAnnotations()){
                System.out.println("a1="+a1);
                if(a1.annotationType().equals(ELHProvider.class)){
                    return (ELHProvider)a1;
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
