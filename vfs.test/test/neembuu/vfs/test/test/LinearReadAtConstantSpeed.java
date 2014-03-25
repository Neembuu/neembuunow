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
package neembuu.vfs.test.test;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import static neembuu.vfs.test.test.Common.createNew_FC_Virtual_File;
import static neembuu.vfs.test.test.Common.show_StartNeembuuMessage;
import org.junit.Test;
/**
 *
 * @author Shashank Tulsyan
 */
public class LinearReadAtConstantSpeed {
    private static SeekableByteChannel /*FileChannel*/ fc_virtual_file=null;//fc_virtual_file = null;

    
    long fileSize = 0;
    volatile long offset = 0;
    
    @Test
    public void linearRead()throws Exception{
        boolean cascade = show_StartNeembuuMessage();
        fc_virtual_file = createNew_FC_Virtual_File(cascade);
        
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new JumpToOffset(LinearReadAtConstantSpeed.this).setVisible(true);
            }
        });
        
        fileSize = fc_virtual_file.size(); 
        ByteBuffer bb = ByteBuffer.allocate(1024);

        double requestSpeed_Bps = 30*1000;
        int lastRead = 0;
        
        long offset_now = offset;
        fc_virtual_file.position(offset);
        
        while(lastRead!=-1){
            if(offset_now!=offset){
                System.out.println("jumping to "+offset);
                offset_now = offset;
                fc_virtual_file.position(offset_now);
            }
            lastRead = fc_virtual_file.read(bb) ;
            
            bb.rewind();
            
            int sleepInterval = (int)(
                    bb.capacity()*1000
                    /
                    requestSpeed_Bps
                );
            System.out.println("sleeping "+sleepInterval);
            Thread.sleep(sleepInterval);
        }
    }
}
