/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1;

import java.util.logging.Level;

/**
 *
 * @author Shashank Tulsyan
 */
public class InitUtils {

    public static void printNativeLibraries() {
        try {
            final String[] libraries = ClassScope.getLoadedLibraries(ClassLoader.getSystemClassLoader());
            String st = "";
            st+="==================Native Libraries loaded=============\n";
            for (int i = 0; i < libraries.length; i++) {
                st+=libraries[i]+"\n";
            }
            st+="==================Native Libraries loaded=============\n";
            Main.getLOGGER().log(Level.INFO, st);
        } catch (Exception a) {
            a.printStackTrace();
        }
        
    }

    public static final class ClassScope {

        private static final java.lang.reflect.Field LIBRARIES;

        static {
            LIBRARIES = initLibs();
            LIBRARIES.setAccessible(true);
        }

        public static String[] getLoadedLibraries() {
            return getLoadedLibraries(ClassScope.class.getClassLoader());
        }

        public static String[] getLoadedLibraries(final ClassLoader loader) {
            String[] toRet = new String[0];
            try {
                toRet = getLoadedLibrariesImpl(loader);
            } catch (Exception a) {

            }
            return toRet;

        }

        static String[] getLoadedLibrariesImpl(final ClassLoader loader) throws Exception {
            final java.util.Vector<String> libraries = (java.util.Vector<String>) LIBRARIES.get(loader);
            return libraries.toArray(new String[]{});
        }

        private static java.lang.reflect.Field initLibs() {
            java.lang.reflect.Field libs = null;
            try {
                libs = ClassLoader.class.getDeclaredField("loadedLibraryNames");
            } catch (Exception a) {
                libs = null;
            }
            return libs;
        }
    }
    
    
}
