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
package neembuu.release1.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMapReader {

    /**
     * @param args
     * @throws IOException
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        FileChannel fc = new RandomAccessFile(new File("f:\\tempimages\\mapped.txt"), "rw").getChannel();
        long bufferSize = 8 * 1000;
        MappedByteBuffer mem = fc.map(FileChannel.MapMode.READ_ONLY, 0, bufferSize);
        long oldSize = fc.size();
        long currentPos = 0;
        long xx = currentPos;
        long startTime = System.currentTimeMillis();
        long lastValue = -1;
        
        long lastMTime = 0;
        
        for (;;) {
            while (mem.hasRemaining()) {
                lastValue = mem.getLong();
                currentPos += 8;
            }
            if (currentPos < oldSize) {
                xx = xx + mem.position();
                mem = fc.map(FileChannel.MapMode.READ_ONLY, xx, bufferSize);
                continue;
            } else {
                long end = System.currentTimeMillis();
                long tot = end - startTime;
                System.out.println(String.format("Last Value Read %s , Time(ms) %s ", lastValue, tot));
                System.out.println("Waiting for message");
                /*while (true) {
                    long newSize = fc.size();
                    if (newSize != oldSize) {
                        oldSize = newSize;
                        xx = xx + mem.position();
                        mem = fc.map(FileChannel.MapMode.READ_ONLY, xx, oldSize - xx);
                        System.out.println("Got some data");
                        break;
                    }
                }*/
                
                while (true) {
                    mem = fc.map(FileChannel.MapMode.READ_ONLY, 0, 4);
                    long mTime = readTimeStamp(mem);
                    if (newSize != oldSize) {

                        oldSize = newSize;
                        xx = xx + mem.position();
                        mem = fc.map(FileChannel.MapMode.READ_ONLY, xx, oldSize - xx);
                        System.out.println("Got some data");
                        break;
                    }
                    try{ Thread.sleep(300);}catch(Exception a){a.printStackTrace();}
                }
            }
        }
    }
    
    private static long readTimeStamp(MappedByteBuffer mem){
        if(mem.hasRemaining()){
            long x = mem.asLongBuffer().get();
            return x;
        }
        return 0;
    }

}
