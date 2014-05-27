/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.externalImpl.linkhandler;

import java.io.RandomAccessFile;
import java.lang.annotation.Annotation;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
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
        
        if(annot.dependenciesLocalPath().length!=
                annot.dependenciesURL().length){
            throw new IllegalStateException("Local path of each resource must be given");
        }
        
        Path[]localPaths = resolveLocalPaths(annot.dependenciesLocalPath());
        
        
        for (Path path : localPaths) {
            calcFileHash(path);
        }
        
        annot.dependenciesLocalPath();
        
        return null;
    }
    
    private Path[]resolveLocalPaths(String[]relativePaths)throws Exception{
        Path[]localPaths = new Path[relativePaths.length];
        Path baseDir = Paths.get(c.getResource(".").toURI());
        for(int i =0; i < relativePaths.length; i++){
            String localPath =relativePaths[i];
            System.out.println(localPath);
            Path relative = Paths.get(localPath);
            
            localPaths[i] = baseDir;
            
            // netbeans bug
            localPaths[i] = Paths.get(localPaths[i].toString().replace("neembuu-linkhandlers", "neembuu-external-linkhandlers"));
            
            for(Path ele : relative){
                System.out.println("");
                if(ele.getFileName().toString().equals("..")){
                    localPaths[i] = localPaths[i].getParent();
                }else { 
                    localPaths[i] = localPaths[i].resolve(ele);
                }
            }
            
            System.out.println("loca="+localPaths[i]);
            System.out.println("netbeans bug");
        }return localPaths;
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
    
    private void calcFileHash(Path filePath) {
        int buff = 100*1024;
        try {
            MessageDigest hashSum = MessageDigest.getInstance("SHA-256");
            long startTime;
            
            try (RandomAccessFile file = new RandomAccessFile(filePath.toFile(), "r")) {
                startTime = System.nanoTime();
                byte[] buffer = new byte[buff];
                long read = 0;
                long offset = file.length();
                int unitsize;
                while (read < offset) {
                    unitsize = (int) (((offset - read) >= buff) ? buff : (offset - read));
                    file.read(buffer, 0, unitsize);
                    
                    hashSum.update(buffer, 0, unitsize);
                    
                    read += unitsize;
                }
            }
            //partialHash = new byte[hashSum.getDigestLength()];
            //partialHash = hashSum.digest();
            
            
            System.out.println("hash="+hashSum.toString());

            long endTime = System.nanoTime();

            System.out.println(endTime - startTime);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static final class ExternalLinkHandlerEntryImpl implements ExternalLinkHandlerEntry {
        private final ExternalLinkHandlerProviderAnnotation annotation;
        private String className;
        private long[] resourcesHash;

        public ExternalLinkHandlerEntryImpl(ExternalLinkHandlerProviderAnnotation annotation) {
            this.annotation = annotation;
        }
        
        @Override public String checkingRegex() { return annotation.checkingRegex(); }
        @Override public String checkingJavaCode() { return annotation.checkingJavaCode(); }
        @Override public long[] resourcesHash() { return resourcesHash; }
        @Override public String className() { return className; }
        @Override public String[] dependenciesURL() { return annotation.dependenciesURL(); }
    }
    
    
}
