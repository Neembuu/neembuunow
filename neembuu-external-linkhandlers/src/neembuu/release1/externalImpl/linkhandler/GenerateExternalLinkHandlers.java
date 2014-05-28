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

package neembuu.release1.externalImpl.linkhandler;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import neembuu.release1.api.linkhandler.LinkHandlerProvider;
import neembuu.release1.defaultImpl.external.ExternalLinkHandlerEntry;
import neembuu.release1.defaultImpl.external.ExternalLinkHandlers;
import org.json.JSONObject;

/**
 *
 * @author Shashank Tulsyan
 */
public class GenerateExternalLinkHandlers {
    private final Class baseClass;

    public GenerateExternalLinkHandlers(Class baseClass) {
        this.baseClass = baseClass;
    }
    
    private Path baseDir(){
        try{
            return Paths.get(baseClass.getResource(".").toURI());
        }catch(Exception a){
            throw new RuntimeException(a);
        }
    }
    
    private List<Class> makeList()throws IOException,ClassNotFoundException{
        ArrayList<Class> classes = new ArrayList<>();
        Path baseDir = baseDir();
        DirectoryStream<Path> ds = Files.newDirectoryStream(baseDir);
        String packageName = baseClassPackageName();
        for(Path p : ds){
            String fn = p.getFileName().toString();
            if(fn.contains(".")){ fn = fn.substring(0,fn.lastIndexOf("."));} 
            
            try{
                Class c = baseClass.getClassLoader().loadClass(packageName+"."+fn);
                
                if(isLinkHandler(c)){
                    classes.add(c);
                }else {
                    System.out.println("not of type linkhandlerprovider "+c);
                }
            }catch(ClassNotFoundException cnfe){
                cnfe.printStackTrace();
            }
            
        }return classes;
    }
    
    private static boolean isLinkHandler(Class c){
        if(c.isAssignableFrom(LinkHandlerProvider.class))return true;
        
        //netbeans runtime bug :(
                
        Object o = null;
        try{ 
            o = c.newInstance();
            boolean a = o instanceof LinkHandlerProvider;
            if(a)return a;
            try{
                System.out.println(((LinkHandlerProvider)o).tryHandling(null));
            }catch(Exception xa){
                
            }
            boolean b = o instanceof LinkHandlerProvider;
            if(a!=b){System.out.println("a!=b"+c);}
            return ( o instanceof LinkHandlerProvider);
        }catch(Exception a){}
        return false;
    }
    
    public ExternalLinkHandlers elh()throws Exception{
        ArrayList<ExternalLinkHandlerEntry> handlers = new ArrayList<>();
        List<Class> classes = makeList();
        
        for (Class class1 : classes) {
            GenerateExternalLinkHandlerEntry 
                entry = new GenerateExternalLinkHandlerEntry(class1);
            ExternalLinkHandlerEntry elhe = entry.getExternalLinkHandlerEntry();
            handlers.add(elhe);
        }
        
        return new ExternalLinkHandlers(handlers.toArray(new ExternalLinkHandlerEntry[handlers.size()]),
                GenerateExternalLinkHandlers.class.getName(), 
                GenerateExternalLinkHandlerEntry.HASHING_ALGORITHM);
    }
    
    private String baseClassPackageName(){
        String nm = baseClass.getName();
        if(nm.contains(".")){ nm = nm.substring(0,nm.lastIndexOf("."));} 
        return nm;
    }
    
    public static void main(String[] args) throws Exception{
        GenerateExternalLinkHandlers gelh = new GenerateExternalLinkHandlers(GenerateExternalLinkHandlers.class);
        ExternalLinkHandlers elh = gelh.elh();
        JSONObject jsono = new JSONObject(elh);
        System.out.println(jsono);
    }
}
