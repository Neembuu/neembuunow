/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.externalImpl.linkhandler;

import java.io.RandomAccessFile;
import java.lang.annotation.Annotation;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import neembuu.release1.defaultImpl.external.ExternalLinkHandlerEntry;
import neembuu.release1.defaultImpl.external.ExternalLinkHandlerProviderAnnotation;

/**
 *
 * @author Shashank Tulsyan
 */
public class GenerateExternalLinkHandlerEntry {
    private final Class c;

    public GenerateExternalLinkHandlerEntry(Class c) {
        this.c = c;
    }
    
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
        return calcFileHash(filePath,"SHA-1");
    }
    private String calcFileHash(Path filePath,String algorithm) {
        int buff = 100*1024;
        try {
            MessageDigest hashSum = MessageDigest.getInstance(algorithm);
            int read;
            byte[] buffer;
            try (RandomAccessFile file = new RandomAccessFile(filePath.toFile(), "r")) {
                buffer = new byte[buff];
                read = 0;
                long offset = file.length();
                int unitsize;
                while (read < offset) {
                    unitsize = (int) (((offset - read) >= buff) ? buff : (offset - read));
                    file.read(buffer, 0, unitsize);
                    
                    hashSum.update(buffer, 0, unitsize);
                    
                    read += unitsize;
                }
            }
            return (new HexBinaryAdapter()).marshal(hashSum.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
        
}
