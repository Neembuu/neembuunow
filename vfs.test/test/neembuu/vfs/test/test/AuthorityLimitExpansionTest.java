/*
 *  Copyright (C) 2011 Shashank Tulsyan
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

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JOptionPane;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Shashank Tulsyan
 */
public final class AuthorityLimitExpansionTest {
    @BeforeClass
    public static void init(){
        JOptionPane.showMessageDialog(null, "Start" );
    }

    @Test
    public final void testAuthoityExpansion() throws Exception {
        AtomicBoolean t1_success = new AtomicBoolean();
        Thread t1 = testAtOffsetInNewThread(0,t1_success);
        AtomicBoolean t2_success = new AtomicBoolean();
        Thread t2 = testAtOffsetInNewThread(1024*1024*10,t2_success);
        t1.join();
        t2.join();
        Assert.assertTrue(t1_success.get());
        Assert.assertTrue(t2_success.get());
    }

    private Thread testAtOffsetInNewThread(
            final long offset,
            final AtomicBoolean success){
        
        final class ThreadT extends Thread{

            public ThreadT() {
                setDaemon(true);
            }

            @Override
            public final void run(){
                try{
                    testAtOffset(offset);
                }catch(Exception a){
                    a.printStackTrace(System.err);
                    success.set(false);
                }success.set(true);
            }
        }
        Thread toRet  = new ThreadT();
        toRet.start();
        return toRet;
    }

    private void testAtOffset(final long OFFSET) throws Exception{
        int bufcap  = 102400; // 100KB

        /*
        java.nio.channels.AsynchronousFileChannel afc = java.nio.channels.AsynchronousFileChannel.open(
                java.nio.file.Paths.get("j:\\neembuu\\virtual\\monitored.nbvfs\\test120k.http.rmvb"));
        FileChannel fc_real = FileChannel.open(
                java.nio.file.Paths.get("j:\\neembuu\\realfiles\\test120k.rmvb"));

        Future[] readReqs = new Future[10];
        ByteBuffer[] buffers = new ByteBuffer[readReqs.length];
        ByteBuffer[] buffers_2 = new ByteBuffer[readReqs.length];

        long offset = OFFSET;
        for (int i = 0; i < readReqs.length; i++) {
            System.out.println("offset="+offset);
            buffers[i] = ByteBuffer.allocateDirect(bufcap);
            buffers_2[i] = ByteBuffer.allocateDirect(bufcap);
            readReqs[i] = afc.read(buffers[i], offset);
            offset+=bufcap;
        }

        offset = OFFSET;
        for (int i = 0; i < buffers_2.length; i++) {
            fc_real.read(buffers_2[i],offset);
            offset+=bufcap;
        }

        for (int i = 0; i < readReqs.length; i++) {
            readReqs[i].get();
            BoundaryConditions.printContentPeek(buffers[i], buffers_2[i]);;
            BoundaryConditions.checkBuffers(buffers[i], buffers_2[i]);
        } 
         */
    }
}
