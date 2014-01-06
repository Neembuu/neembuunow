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

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Iterator;
import java.util.LinkedList;
import neembuu.rangearray.RangeArray;
import neembuu.rangearray.RangeArrayFactory;
import neembuu.rangearray.RangeArrayParams;
import neembuu.rangearray.RangeUtils;
/**
 * This is not thread safe. Use new RangeArrayVIntFile(rangeArray.copy()) instead.
 * @author Shashank Tulsyan
 */
public final class RangeArrayVIntFile {
    private RangeArray rangeArray;
    private int checksumGap = DEFAULT_CHECKSUM_GAP;
    private static final int DEFAULT_CHECKSUM_GAP = 10000;

    public RangeArrayVIntFile(RangeArray rangeArray) {
        if(rangeArray.size() > 0)
            if(rangeArray.get(0).starting()<0)throw new IllegalArgumentException("RangeArray extending in negative region cannot be converted into a RangeArrayVIntChannel");
        this.rangeArray = rangeArray;
    }

    public int getChecksumGap() {
        return checksumGap;
    }

    public void setChecksumGap(int checksumGap) {
        if(checksumGap<0)throw new IllegalArgumentException("checksumGap should be a positive integer");
        this.checksumGap = checksumGap;
    }



    public final long[] writeTo(String destPath)throws IOException{
        FileChannel fc = new RandomAccessFile(destPath, "rw").getChannel();
                //FileChannel.open(destPath,StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING,StandardOpenOption.WRITE);
        long[]ret=writeTo(fc);
        fc.force(false);
        fc.close();return ret;
    }
    
    public final long[] writeTo(WritableByteChannel fc)throws IOException{
        LinkedList<Long> checksumOffsets = new LinkedList<Long>();

        long curentDataOffset = 0;

        ByteBuffer b;
        if(rangeArray.size()==0)return new long[0];
        long previous = 0;
        for (int i = 0; i < rangeArray.size(); i++) {

            if(i%checksumGap==0){
                //writting a checksum entrys
                checksumOffsets.add(curentDataOffset);

                b=ByteBuffer.wrap(new byte[1]);//store a null
                curentDataOffset+=b.capacity();
                fc.write(b);//write the null
                b=ByteBuffer.wrap(VIntUtils.toVIntByteArray(previous));//the checksum
                curentDataOffset+=b.capacity();
                fc.write(b);//writting the checksum
            }

            b=ByteBuffer.wrap(VIntUtils.toVIntByteArray(rangeArray.get(i).starting() - previous));
            curentDataOffset+=b.capacity();
            fc.write(b);
            b=ByteBuffer.wrap(VIntUtils.toVIntByteArray((int)RangeUtils.getSize(rangeArray.get(i))));
            curentDataOffset+=b.capacity();
            fc.write(b);
            previous = rangeArray.get(i).ending();
        }
        //atleast one checksum should be always written
        //writting at the end makes the most sence
        checksumOffsets.add(curentDataOffset);
        b=ByteBuffer.wrap(new byte[1]);//store a null
        curentDataOffset+=b.capacity();
        fc.write(b);//write the null
        b=ByteBuffer.wrap(VIntUtils.toVIntByteArray(previous));//the checksum
        curentDataOffset+=b.capacity();
        fc.write(b);//writting the checksum

        long[]ret=new long[checksumOffsets.size()];
        Iterator<Long> it = checksumOffsets.iterator(); int index=0;
        while(it.hasNext()){
            ret[index]=it.next();
            index++;
        }return ret;
    }

    public final void readFrom(String srcPath)throws IOException{
        FileChannel fc = new RandomAccessFile(srcPath, "r").getChannel();
                //FileChannel.open(srcPath, StandardOpenOption.READ);
        readFrom(fc);
        fc.close();
    }

    @SuppressWarnings(value="unchecked")
    public final void readFrom(ReadableByteChannel fc)throws IOException{
        //RangeArray rangeArray;
        ByteBuffer b=ByteBuffer.allocate(1024*100);
        boolean filled = refillBuffer(b, fc);
        if(!filled){
            throw new IOException("There is no data available in channel");
        }
        //rangeArray = new RangeArray();

        try{
            long previous = 0,starting,ending,currentVInt;
            long checksum;
            while(b.hasRemaining()){
                currentVInt =  VIntUtils.getNextVIntAsLong(b, fc);
                if(!b.hasRemaining()){
                    filled = refillBuffer(b, fc);
                    if(!filled){break;}
                }

                if(currentVInt==VIntUtils.LONG_EQUIVALENT_OF_VINT_NULL){
                    //this is a checksum entry
                    //we can do a checksum or simply ignore this
                    checksum = VIntUtils.getNextVIntAsLong(b, fc);                  
                    if(checksum!=previous){
                        throw new Exception("Corrupt, expected = "+checksum+" instead of="+previous);
                    }else{
                        //we can commit whatever we had till now
                        this.rangeArray.addAll(rangeArray);
                    }
                    continue;
                }
                starting = previous + currentVInt;
                previous = starting;
                //   ending = previous +               size                 - 1
                ending = previous + VIntUtils.getNextVIntAsLong(b, fc) - 1;
                previous = ending;

                //System.out.println("adding="+ele);
                rangeArray.addElement(starting,ending,null);

                if(!b.hasRemaining()){
                    filled = refillBuffer(b, fc);
                    if(!filled){break;}
                }
            }
        }catch(Exception ayn){
            ayn.printStackTrace(System.out);
        }
        
    }

    private boolean refillBuffer(ByteBuffer b, ReadableByteChannel readChannel) throws IOException{
        b.clear();

        int read = readChannel.read(b);
        if(read<1){
            return false;
        }
        //System.out.println("read="+read);
        //System.out.println(b);
        // using the above check, it was found that limit needs to be explicitly set
        b.limit(read);
        b.rewind();
        return true;
    }
    
    public static void main(String[] args) throws IOException {
        //testMerge();
        RangeArray test = StatePatternFile.createRangeArrayFrom("J:\\neembuu\\patterns\\deathnote_avi_audio_extract.requeststate");

        RangeArrayVIntFile booleanRangeArrayFile = new RangeArrayVIntFile(test);
        java.io.File pth = new java.io.File("J:\\neembuu\\patterns\\deathnote_avi_audio_extract.booleanrequeststate");
        long[]checkSumOffsets = booleanRangeArrayFile.writeTo(pth.toString());
        System.out.println("Checksum offsets==");
        for (int i = 0; i < checkSumOffsets.length; i++) {
            System.out.println(checkSumOffsets[i]);
        }System.out.println("Checksum offsets---");

        booleanRangeArrayFile = new RangeArrayVIntFile(
                RangeArrayFactory.newDefaultRangeArray(
                    new RangeArrayParams.Builder()
                        .setDoesNotCarryProperty()
                        .build()));
        booleanRangeArrayFile.readFrom(pth.toString());

        System.out.println("reason for failure");
        //System.out.println(test.notEqualsGetReason(booleanRangeArrayFile.rangeArray));

    }

    /*private static void testMerge(){
        RangeArray rangeArray = new RangeArray();
        rangeArray.add(new RangeArrayElement(0,256037));
        rangeArray.add(new RangeArrayElement(256038,293062));
        rangeArray.add(new RangeArrayElement(293063,833589));
        rangeArray.add(new RangeArrayElement(868178,879092));
        rangeArray.add(new RangeArrayElement(1024393,1305010));
        rangeArray.add(new RangeArrayElement(2709795,2984782));
        rangeArray.add(new RangeArrayElement(3015884,3042299));
        rangeArray.add(new RangeArrayElement(3042300,3114290));

        RangeArray rangeArray1 = new RangeArray();
        rangeArray1.add(new RangeArrayElement(3114291,3128786));
        rangeArray1.add(new RangeArrayElement(3217806,3290846));
        rangeArray1.add(new RangeArrayElement(3290847,3308627));
        rangeArray1.add(new RangeArrayElement(3308628,3442214));
        rangeArray1.add(new RangeArrayElement(4332048,4500163));
        rangeArray1.add(new RangeArrayElement(4500164,4668951));
        rangeArray1.add(new RangeArrayElement(4748357,4828383));
        rangeArray1.add(new RangeArrayElement(4828384,5132429));
        rangeArray1.add(new RangeArrayElement(5516730,5559158));
        rangeArray1.add(new RangeArrayElement(6140573,6453121));
        rangeArray1.add(new RangeArrayElement(7310368,7453817));
        rangeArray1.add(new RangeArrayElement(7477082,7690939));
        rangeArray1.add(new RangeArrayElement(8377224,8385788));
        rangeArray1.add(new RangeArrayElement(9213014,9298991));
        rangeArray1.add(new RangeArrayElement(9439957,9454267));
        rangeArray1.add(new RangeArrayElement(9525799,9816636));
        rangeArray1.add(new RangeArrayElement(11285707,11487011));
        rangeArray1.add(new RangeArrayElement(12374526,12391483));
        rangeArray1.add(new RangeArrayElement(12580269,12616526));
        rangeArray1.add(new RangeArrayElement(12871043,13030268));
        rangeArray1.add(new RangeArrayElement(13265395,13319641));
        rangeArray1.add(new RangeArrayElement(13319642,13570906));
        rangeArray1.add(new RangeArrayElement(13749149,13755577));
        rangeArray1.add(new RangeArrayElement(13755578,13831882));
        rangeArray1.add(new RangeArrayElement(13831883,13965867));
        rangeArray1.add(new RangeArrayElement(13965868,13998890));
        rangeArray1.add(new RangeArrayElement(13998891,14036026));
        rangeArray1.add(new RangeArrayElement(14036027,14204181));
        rangeArray1.add(new RangeArrayElement(14204182,14299008));
        rangeArray1.add(new RangeArrayElement(14355878,14412770));

        RangeArray rangeArray2 = new RangeArray();
        rangeArray2.add(new RangeArrayElement(14412771,14525563));
        rangeArray2.add(new RangeArrayElement(14525564,14642418));
        rangeArray2.add(new RangeArrayElement(14796846,14883788));
        rangeArray2.add(new RangeArrayElement(15434120,15501949));
        rangeArray2.add(new RangeArrayElement(15627581,15638672));
        rangeArray2.add(new RangeArrayElement(16414473,16548082));
        rangeArray2.add(new RangeArrayElement(16637524,16799536));
        rangeArray2.add(new RangeArrayElement(18490642,18562883));
        rangeArray2.add(new RangeArrayElement(18618475,18621215));
        rangeArray2.add(new RangeArrayElement(20098388,20146626));
        rangeArray2.add(new RangeArrayElement(21094117,21363276));
        rangeArray2.add(new RangeArrayElement(22045158,22052957));
        rangeArray2.add(new RangeArrayElement(22052958,22258083));
        rangeArray2.add(new RangeArrayElement(22258084,22292857));
        rangeArray2.add(new RangeArrayElement(22595584,22641883));
        rangeArray2.add(new RangeArrayElement(24091515,24099829));
        rangeArray2.add(new RangeArrayElement(24099830,24206037));
        rangeArray2.add(new RangeArrayElement(24206038,24320867));
        rangeArray2.add(new RangeArrayElement(24320868,24383711));
        rangeArray2.add(new RangeArrayElement(24571375,24614950));


        System.out.println(rangeArray );
        System.out.println("+++++++++++++");
        System.out.println(rangeArray2 );
        System.out.println("============");
        rangeArray.merge(rangeArray2);
        System.out.println(rangeArray );        
        System.out.println("+++++++++++++");
        System.out.println(rangeArray1 );
        System.out.println("============");
        rangeArray.merge(rangeArray1);
        System.out.println(rangeArray );



        System.exit(0);
    }*/

}
