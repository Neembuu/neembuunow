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

package neembuu.vfs.test.test;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import org.junit.Test;
import static junit.framework.Assert.*;

/**
 *
 * @author Shashank Tulsyan
 */
public final class SeekableHttpFileTest {

    //@Test
    public final void bufferEquality(){
        ByteBuffer actualContent = ByteBuffer.allocate(1024);
        actualContent.put("some random content".getBytes());
        ByteBuffer virtualContent = ByteBuffer.allocate(1024);
        virtualContent.put("some random content".getBytes());
        ByteBuffer unequalContent = ByteBuffer.allocate(1024);
        unequalContent.put("some different random content".getBytes());
        assertFalse(actualContent.equals(unequalContent));
        assertTrue(actualContent.equals(virtualContent));
    }

    @Test
    public final void analyzeSimplestRequest() throws Exception {
        FileChannel fc = new  FileInputStream("j:\\neembuu\\realfiles\\test120k.rmvb").getChannel();
        ByteBuffer actualContent = ByteBuffer.allocate(1024);
        fc.position(0);
        fc.read(actualContent);
        fc.close();

        FileChannel fcVirtual = null;
        int trial = 0;
        while(fcVirtual==null){
            try{
                fcVirtual = new  FileInputStream("j:\\neembuu\\virtual\\monitored.nbvfs\\test120k.http.rmvb").getChannel();
            }catch(Exception e){
                System.out.println("trial="+trial);
                trial++;
                Thread.sleep(1000);
            }
        }
        ByteBuffer virtualContent = ByteBuffer.allocate(1024);
        fcVirtual.position(0);
        fcVirtual.read(virtualContent);
        fcVirtual.close();

        virtualContent.equals(actualContent);
    }

    
}
