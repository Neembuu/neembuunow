package org.shashaank.utils.jni;

/**
 *
 * @author Shashank Tulsyan
 */
public final class ClassScope {

    private static final java.lang.reflect.Field LIBRARIES;

    static {
        LIBRARIES = initLibs();
        LIBRARIES.setAccessible(true);
    }

    public static String[] getLoadedLibraries()  {
        return getLoadedLibraries(ClassScope.class.getClassLoader());
    }
    
    public static String[] getLoadedLibraries(final ClassLoader loader)  {
        String[]toRet = new String[0];
        try{
            toRet = getLoadedLibrariesImpl(loader);
        }catch(Exception a){
            
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