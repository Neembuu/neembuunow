/*
 *  Copyright (C) 2010 admin
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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

/**
 *
 * @author admin
 */
public final class VIntUtils {


    private static final long extractByte[]={
        (0x100000000000000L-1),
          (0x2000000000000L-1),
            (0x40000000000L-1),
              (0x800000000L-1),
                (0x10000000-1),
                  (0x200000-1),
                    (0x4000-1),
                      (0x80-1),
    };
    private static final long d[]={
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
    private static final byte sig[]={
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


    public static final long LONG_EQUIVALENT_OF_VINT_NULL = Long.MIN_VALUE;

    public static final long toLong(byte[]vint){
        if(vint.length==0)throw new ArrayIndexOutOfBoundsException("size of the vint array is zero");

        byte firstByte = vint[0];
        if(firstByte==0){
            return 0;
        } //null stored
        
        int numOfBytes = getNumberOfBytesInVint(firstByte);

        //remove indicator bit from firstByte

        //data types in java are signed
        //a byte might get coverted into negative integer
        //that is why anding to 0xff is needed
        firstByte = (byte)((firstByte&0xFF)^(sig[numOfBytes]&0xFF));
        long ret = 0;

        int i=0;
        for (; i < numOfBytes -1 ; i++) {
            ret|=(vint[numOfBytes-1-i]&0xFF)<<(8*i);
        }
        ret|=(firstByte&0xFF)<<(8*(numOfBytes-1));
        return ret;
    }

    public static final int getNumberOfBytesInVint(byte firstByte){
        if(firstByte==0)return 1; //null stored

        int numOfDig = 1,t;
        for (int i = 1; i < sig.length; i++) {
            //data types in java are signed
            //a byte might get coverted into negative integer
            //that is why anding to 0xff is needed
            t = (firstByte&0xFF)&(sig[i]&0xFF);
            if(t!=0){
                numOfDig = i; break;
            }
        }

        return numOfDig;
    }
    public static final byte[] toVIntByteArray(long number){
        int numOfBytes = 1;



        if(number>d[8] || number<0)throw new IllegalArgumentException("Cannot handle number greater than (2^7)^8 ("+d[7]+"). For number given = "+number);

        if(number==0){
            //return new byte[1];
            // null can be used to indicate special values
        }

        for(int j=1;j<9;j++){
            if(number<=d[j]){
                numOfBytes = j;break;
            }
        }
        byte[]ret=new byte[numOfBytes];
        //System.out.println("numberofbytes="+numOfBytes);
        for (int i = numOfBytes-1; i >= 0; i--) {
            /*System.out.print("ret["+(numOfBytes-1-i)+"]=");
            System.out.println(Integer.toHexString(
                    ((int)((( number&extractByte[i] )>>8*i) & 0xFF)))
            );*/
            ret[numOfBytes-1-i] = (byte)((( number&extractByte[i] )>>8*i)&0xFF);
        }
        ret[0]|=sig[numOfBytes];
        return ret;
    }

    public static final long getNextVIntAsLong(ByteBuffer buffer, ReadableByteChannel readChannel) throws IOException{
        if(buffer==null){
            buffer = ByteBuffer.allocate(1);
            readChannel.read(buffer);
        }
        byte firstByte;
        if(buffer.hasRemaining()){
            firstByte = buffer.get();
        }else {
            firstByte = readAByte(readChannel);
        }
        if(firstByte==0)return LONG_EQUIVALENT_OF_VINT_NULL;

        int numOfBytes = getNumberOfBytesInVint(firstByte);

        //remove indicator bit from firstByte

        //data types in java are signed
        //a byte might get coverted into negative integer
        //that is why anding to 0xff is needed
        firstByte = (byte)((firstByte&0xFF)^(sig[numOfBytes]&0xFF));
        long ret = 0;        
        byte next;
        //handling first byte
        ret|=(firstByte&0xFF)<<(8*(numOfBytes-1));
        //starting from 2nd byte
        for (int i=1; i < numOfBytes; i++) {
            if(buffer.hasRemaining()){
                next = buffer.get();//vint[numOfBytes-1-i];
            }else{        
                next = readAByte(readChannel);
            }
            ret|=(next&0xFF)<<(8*(numOfBytes-1-i));
        }

        
        return ret;
    }

    private static final byte readAByte(ReadableByteChannel readChannel)throws IOException{
        byte[]tmp=new byte[1];
        if(readChannel==null){
            throw new NullPointerException("Buffer exhausted and read channel is null, cannot continue");
        }
        int read = readChannel.read(ByteBuffer.wrap(tmp));
        if(read<0){
            throw new IOException("Channel abruptly ended while expecting more data.");
        }
        return tmp[0];
    }


    /*
     @Test
     public static void main(String[] args) throws Exception{

        long ori = 12311322;
        Path pth = Paths.get("j:\\neembuu\\x.txt");
        FileChannel r= FileChannel.open(pth,StandardOpenOption.CREATE,StandardOpenOption.WRITE);

        byte[]s = toVIntByteArray(0x0FEE3301);
        System.out.println("to normal="+Long.toHexString(toLong(s)));

        byte[]q = toVIntByteArray(ori);
        System.out.println("to normal="+toLong(q));
        r.write(ByteBuffer.wrap(q));
        

        r.write(ByteBuffer.wrap(new byte[10]));

        r.write(ByteBuffer.wrap(new byte[]{(byte)0xDD,(byte)0x22,(byte)0x04}));

        r.force(true);


        long ans = getNextVInt(ByteBuffer.wrap(q),null);
        System.out.println("ori="+ori);
        System.out.println("ans="+ans);

    }*/
}
