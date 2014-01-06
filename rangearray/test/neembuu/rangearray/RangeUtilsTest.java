/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
