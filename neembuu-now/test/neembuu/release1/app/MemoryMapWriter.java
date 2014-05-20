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
import static java.nio.file.StandardOpenOption.*;

public class MemoryMapWriter {

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        File f = new File("f:\\tempimages\\mapped.txt");
        f.delete();
        FileChannel fc = FileChannel.open(f.toPath(), READ, WRITE);
            //new RandomAccessFile(f, "rw").getChannel();
        long bufferSize = 8 * 1000;
        MappedByteBuffer mem = fc.map(FileChannel.MapMode.READ_WRITE, 0, bufferSize);
        int start = 0;
        long counter = 1;
        long HUNDREDK = 100000;
        long startT = System.currentTimeMillis();
        long noOfMessage = HUNDREDK * 10 * 10;
        for (;;) {
            if (!mem.hasRemaining()) {
                start += mem.position();
                mem = fc.map(FileChannel.MapMode.READ_WRITE, start, bufferSize);
            }
            mem.putLong(counter);
            counter++;
            if (counter > noOfMessage) {
                break;
            }
        }
        long endT = System.currentTimeMillis();
        long tot = endT - startT;
        System.out.println(String.format("No Of Message %s , Time(ms) %s ", noOfMessage, tot));
    }

}
