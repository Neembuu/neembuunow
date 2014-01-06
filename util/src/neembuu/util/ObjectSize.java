/*
 *  Copyright (C) 2009-2010 Shashank Tulsyan
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
 *
 *
 *
 *  Linking this library statically or
 *  dynamically with other modules is making a combined work based on this library.
 *  Thus, the terms and conditions of the GNU General Public License cover the whole combination.
 *
 *
 *  As a special exception, the copyright holders of this library give you permission to
 *  link this library with independent modules to produce an executable, regardless of
 *  the license terms of these independent modules, and to copy and
 *  distribute the resulting executable under terms of your choice,
 *  provided that you also meet, for each linked independent module,
 *  the terms and conditions of the license of that module.
 *  An independent module is a module which is not derived from or based on this library.
 *  If you modify this library, you may extend this exception to your version of the library,
 *  but you are not obligated to do so. If you do not wish to do so,
 *  delete this exception statement from your version.
 */


package neembuu.util;

import java.lang.reflect.*;


/**
 * Utility class to find size of Objects.
 * @author Shashank Tulsyan
 */
public final class ObjectSize {
    /**
     * This is the size in x86, i am not sure about 64bit, and plus there is
     * something new in jdk7 called compressed pointers, or something
     */
    public final static int OBJECT_POINTER_SIZE=4;
    /**
     * This space is used to store info like
     * who What's the kind of this Object.
     * And allowances for moving this object ... may be hashCode integer as well
     */
    public final static int OBJECT_INSTANCE_MINIMUM_SIZE=8;

    private int rawSize;
    private int fullSize;
    private int rawSizeWithoutJVMRoundOff;

    String className;

    private boolean complete=false;

    private ObjectSize() {
    }

    /**
     * Calculates sizeOf the given Object.
     */
    public static ObjectSize sizeOf(Object obj){
        ObjectSize size=incompleteSizeOf(obj.getClass());
        size.complete();
        return size;
    }
    /**
     * Calculates size of an instance of given Class
     */
    public static ObjectSize sizeOf(Class clzz){
        ObjectSize size=incompleteSizeOf(clzz);
        size.complete();
        return size;
    }
    /**
     * Calculates size of an instance of given Class, but does not
     * add the space used by variables of superclasses.
     */
    public static ObjectSize incompleteSizeOfSubmostClass(Class clzz){
        ObjectSize size=incompleteSizeOfClass(clzz);
        size.complete=true;
        size.rawSizeWithoutJVMRoundOff=-1;
        return size;
    }

    private static ObjectSize incompleteSizeOf(Class clzz){
        ObjectSize size=incompleteSizeOfClass(clzz);
        if(!(clzz.getSuperclass()==Object.class || clzz.getSuperclass()==null )){
            ObjectSize sizeDeeper=incompleteSizeOfClass(clzz.getSuperclass());
            size.fullSize+=sizeDeeper.fullSize;
            size.rawSize+=sizeDeeper.rawSize;

        }        
        return size;
    }
    private static ObjectSize incompleteSizeOfClass(Class clzz){
        Field[] fields = clzz.getDeclaredFields();
        ObjectSize size=new ObjectSize(); size.className=clzz.getSimpleName();
        for(Field field : fields){
            if(!Modifier.isStatic(field.getModifiers())){
                Type type = field.getType();
                if(type==byte.class || type==boolean.class ){
                    size.rawSize+=1;
                    size.fullSize+=1;
                }else if(type==short.class || type==char.class){
                    size.rawSize+=2;
                    size.fullSize+=2;
                }else if(type==long.class){
                    size.rawSize+=8;
                    size.fullSize+=8;
                }else if(type==int.class){
                    size.rawSize+=4;
                    size.fullSize+=4;
                }else{//Object or array, reference type
                    size.rawSize+=OBJECT_POINTER_SIZE;
                    size.fullSize+=OBJECT_POINTER_SIZE;
                    size.fullSize+=OBJECT_INSTANCE_MINIMUM_SIZE;
                }
            }
        }
        return size;
    }
    private void complete(){
        if(complete)return;
        fullSize+=OBJECT_INSTANCE_MINIMUM_SIZE;
        rawSize+=OBJECT_INSTANCE_MINIMUM_SIZE;

        rawSizeWithoutJVMRoundOff=rawSize;
        boolean add=false;
        add = (rawSize%8 !=0 );
        rawSize=((int)(rawSize/8))*8;
        fullSize=((int)(fullSize/8))*8;
        if(add){rawSize+=8;fullSize+=8;}
        //JVMs resolution is limited to 8bytes
        //so space allocated for a 12 byte Object is 16byes, thus some space is often unused
        
        complete=true;
    }
    public static void main(String []as ){
        System.out.println(sizeOf(new Object()));
        System.out.println(sizeOf(java.nio.ByteBuffer.class));
        //System.out.println(sizeOf(jpfm.operations.Read.class));

        //System.out.println(sizeOf(new neembuu.common.RangeArray() ));
        /*System.out.println(sizeOf(jpfm.operations.Read.class));
        System.out.println(sizeOf(jpfm.FileDescriptor.class));
        System.out.println(sizeOf(java.nio.ByteBuffer.class));*/
        System.out.println(incompleteSizeOfSubmostClass( ObjectSize.class ));

    }
    /**
     * The size of the region of memory pointed to by
     * a reference of this Object is raw size.
     * This Object might have references to other objects,
     * taking the minimum size of these references
     * we get minimum full size.
     * Although it is also possible that an Object is ever assigned
     * to a given reference variable, it is assumed in calculation of
     * this quantity that a empty Object would be created and assigned to it.
     * @return minimum full size
     */
    public int getMinimumFullSize() {
        return fullSize;
    }
    /**
     * The size of the region of memory pointed to by
     * a reference of this Object is raw size.
     * This Object might have references to other objects.
     * The size of these references can be any positive multiple of 8.
     * <br/>
     * Also that Object might be referenced by many (like a constant or enum ),
     * so taking size of that Object into consideration might not be necessary.
     * It is also possible that an Object is ever assigned
     * to a given reference variable
     * So this makes rawSize the most useful value.
     * This value can be controled by the programmer by keeping a good eye
     * on memory usage.
     * <br/>
     * JVM allocates space in in multiples 8byte.
     * So a class that should have taken 12byte takes 16bytes.
     * So taking that into account, the maximum that the Object will
     * consume in stored in rawSize.
     * @return raw size
     */
    public int getMaximumRawSize() {
        return rawSize;
    }

    public int getRawSizeWithoutJVMRoundOff() throws Exception {
        if(rawSizeWithoutJVMRoundOff < 0) throw new Exception("RawSizeWithoutJVMRoundOff cannot be calculated ");
        return rawSizeWithoutJVMRoundOff;
    }

    public int getRawSizeWithoutJVMRoundOffIgnoringObjectPayload() throws Exception {
        return getRawSizeWithoutJVMRoundOff()-8;
    }

    @Override
    public String toString() {
        StringBuilder br=new StringBuilder(80);
        br.append(className);
        br.append(" {MinimumFullSize=");
        br.append(fullSize);
        br.append(" MaximumRawSize=");
        br.append(rawSize);
        br.append(" Size without JVM round off=");
        if(rawSizeWithoutJVMRoundOff>0){
            br.append(rawSizeWithoutJVMRoundOff);
        }
        br.append('}');
        return br.toString();
    }



}
