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
package neembuu.release1;

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
            System.out.println(st);
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
