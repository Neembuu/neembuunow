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

package neembuu.diskmanager.impl;

import java.nio.ByteBuffer;

/**
 *
 * @author Shashank Tulsyan
 */
public class JavaStringHashAlgoTest {
    public static void main(String[] args) throws Exception{
        String bigString = "";
        for (int i = 0; i < 100; i++) {
            String x = random();
            System.out.print("i="+i);testString(x);
            bigString += x;
        }
        System.out.println("Overall");testString(bigString);
    }
    
    private static void testString(String x)throws Exception{
        int h1 = x.hashCode();
        ByteBuffer bb = ByteBuffer.wrap(x.getBytes());
        int h2 = JavaStringHashAlgo.makeHash(bb).getValue();
        System.out.println("h1="+h1+" h2="+h2+ "equals="+(h1==h2) );
    }
    
    private static String random(){
        java.security.SecureRandom random = new java.security.SecureRandom();
        return new java.math.BigInteger(130, random).toString(32);
    }
}
