/*
 *  Copyright (C) 2010 Shashank Tulsyan
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

package neembuu.util;

/**
 *
 * @author Shashank Tulsyan
 */
public final class VInt {
    public static final long toLong(byte[]vint){
        return 0;
    }
    public static final byte[] toVInt(long number){
        int numOfBytes = 1;
        long d[]={
                          0x1, //2^(7*0)
                         0x80, //2^(7*1)
                       0x4000, //2^(7*2)
                     0x200000, //2^(7*3)
                   0x10000000, //2^(7*4)
                  0x800000000L,//2^(7*5)
                0x40000000000L,//2^(7*6)
              0x2000000000000L,//2^(7*7)
            0x100000000000000L,//2^(7*8)
        };
        if(number>d[8])throw new IllegalArgumentException("Cannot handle number greater than (2^7)^8 ("+d[7]+"). For number given = "+number);

        for(int j=1;j<9;j++){
            if(number<=d[j])numOfBytes = j;
        }

        byte[]ret=new byte[numOfBytes];
        byte sig[]={
             0x0, //2^(7*0)
              -1, //2^(7*1)
            (byte)0x80, //2^(7*2)
            0x40, //2^(7*3)
            0x20, //2^(7*4)
            0x10,//2^(7*5)
             0x8,//2^(7*6)
             0x4,//2^(7*7)
             0x2,//2^(7*8)
        };
        return null;
    }

    public static void main(String[] args) {
        byte sig[]={
              0x0, //null
             -128, //1000 0000
             0x40, //0100 0000
             0x20, //0010 0000
             0x10, //0001 0000
              0x8, //0000 1000
              0x4, //0000 0100
              0x2, //0000 0010
              0x1, //0000 0001
        };

        for (int i = 0; i < sig.length; i++) {
            System.out.println(Integer.toBinaryString(
                    ((byte)sig[i]&(0x100-1))
            ));
        }
        System.out.println();
        for (byte i = -128; i < 128; i++) {
            System.out.println(i+" = "+Integer.toBinaryString( i&(0x100-1) ));
        }

    }
}
