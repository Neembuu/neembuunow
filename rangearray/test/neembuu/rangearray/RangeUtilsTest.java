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
package neembuu.rangearray;

import neembuu.rangearray.RangeUtils;
import org.junit.Test;

/**
 *
 * @author Shashank Tulsyan
 */

public class RangeUtilsTest {
    @Test
    public void intersectionTest(){
        Range A,B,C,D;
        A = RangeUtils.wrapAsARange(100,     300);
        B = RangeUtils.wrapAsARange(    200,      400);
        
        C = RangeUtils.wrapAsARange(100,          400);
        D = RangeUtils.wrapAsARange(                 440,      500);
        
        System.out.println("A int B = "+RangeUtils.toString(RangeUtils.intersection(A, B)));
        
        System.out.println("B int A = "+RangeUtils.toString(RangeUtils.intersection(B, A)));
        
        System.out.println("C int A = "+RangeUtils.toString(RangeUtils.intersection(C, A)));
        
        System.out.println("A int C = "+RangeUtils.toString(RangeUtils.intersection(A, C)));
        
        System.out.println("B int C = "+RangeUtils.toString(RangeUtils.intersection(B, C)));
        
        System.out.println("C int B = "+RangeUtils.toString(RangeUtils.intersection(C, B)));
        
        System.out.println("A int D = "+RangeUtils.toString(RangeUtils.intersection(A, D)));
        
        System.out.println("D int A = "+RangeUtils.toString(RangeUtils.intersection(D, A)));
    }
}
