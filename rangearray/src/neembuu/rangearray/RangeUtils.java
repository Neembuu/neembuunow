/*
 * Copyright (c) 2011 Shashank Tulsyan <shashaanktulsyan@gmail.com>. 
 * 
 * This is part of free software: you can redistribute it and/or modify
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
 * along with this.  If not, see <http ://www.gnu.org/licenses/>.
 */
package neembuu.rangearray;

import java.lang.ref.WeakReference;
import java.sql.Ref;
import java.util.Comparator;

/**
 *
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
public final class RangeUtils {
    /**
     * A convenient method to check if given range if supported by RangeArray
     * @param start Starting (inclusive) index of range value
     * @param end Ending (inclusive) index of range value
     * @throws ArrayIndexOutOfBoundsException If start or end are beyond allowed value
     * @throws IllegalArgumentException If start>end
     */
    public static void checkRange(long start,long end){
        if(start>end)throw new IllegalArgumentException("Starting of range entry cannot be greater than ending ");
        if(start<RangeArray.MIN_VALUE_SUPPORTED)
            throw new ArrayIndexOutOfBoundsException(
                    "Starting of range entry cannot be smaller than minimum supported value  "+RangeArray.MIN_VALUE_SUPPORTED
            );
        if(end>RangeArray.MAX_VALUE_SUPPORTED)
            throw new ArrayIndexOutOfBoundsException(
                    "Ending of range entry cannot be greater than maximum supported value  "+RangeArray.MAX_VALUE_SUPPORTED
            );
    }
    
    public final Comparator<Range> getComparator(){
        return new C();
    }
    
    public static boolean contains(Range r, long s, long e){
        return r.starting()<=s && r.ending()>=e;
    }
    
    private static final class C implements Comparator<Range> {
        static WeakReference<C> reference = new WeakReference<C>(new C());
        static C instance(){
            C instance = reference.get();
            if(instance==null){
                instance = new C();
                reference = new WeakReference<C>(new C());
            }return instance;
        }
        
        @Override
            public int compare(Range o1, Range o2) {
                return RangeUtils.compare(o1, o2);
            }
            
        }
    
    public static String toString(Range range){
        if(range==null){
            return null;
        }
        return (range.starting()+"-->"+range.ending())
                +(range.getProperty()==null?"":" property="+range.getProperty());
    }
    
    public static long getSize(Range range){
        return range.ending()-range.starting()+1;
    }
    
    public static Range wrapAsARange(long start, long end){
        checkRange(start, end);
        return new RangeAE(start, end);
    }
    
    private static final class RangeAE implements Range{
        private final long start,end;

        public RangeAE(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public final long starting() {
            return start;
        }

        @Override
        public final long ending() {
            return end;
        }

        @Override
        public final Object getProperty() {
            return null;
        }
    }
    
    public static int compare(Range o1, Range o2) {
        if(o2.ending()>o1.ending())return -1;//o1 is smaller
        if(o1.starting()>o2.starting())return 1;//o2 is gr8er
        return 0;
    }
    
    
    public static Range intersection(Range... rs){
        if(rs.length < 1) {
            return null;
        }
        Range toRet = rs[0];
        if(rs.length < 2) {
            return toRet;
        }
        
        for (int i = 1; i < rs.length; i++) {
            toRet = intersection(toRet, rs[i]);
            if(toRet==null){
                return null;
            }
        }
        return toRet;
    }
    
    public static Range intersection(Range A, Range B) {
        
        if(A==null || B==null){
            return null;
        }
        
        long as = A.starting();
        long bs = B.starting();
        long ae = A.ending();
        long be = B.ending();
        
        if(bs <= as){
            if(be <= ae){
        //A                          as----->ae
        //B                       bs----->be
        //A intersection B           as-->be
                if(as <= be){
                    checkRange(as, be);
                    return new RangeAE(as, be);
                }
        //A                                  as----->ae
        //B                       bs----->be
        //A intersection B           null
                return null;
            } else{
        //A                          as----->ae
        //B                       bs------------>be
        //A intersection B           as----->ae
                return A;
            }
        }
        
        else{
            if(ae <= be){
        //A                       as----->ae
        //B                          bs----->be
        //A intersection B           bs-->ae
                if(bs<=ae){
                    checkRange(bs, ae);
                    return new RangeAE(bs, ae);
                }
        //A                       as----->ae
        //B                                  bs----->be
        //A intersection B           null
                return null;
            } else {
        //A                       as------------>ae
        //B                          bs----->be
        //A intersection B           bs----->be
                return B;
            }
        }
    }
}
