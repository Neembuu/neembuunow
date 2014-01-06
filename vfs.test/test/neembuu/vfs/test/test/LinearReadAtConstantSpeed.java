/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.vfs.test.test;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import org.junit.Test;
import static junit.framework.Assert.*;
import static neembuu.vfs.test.test.Common.*;
/**
 *
 * @author Shashank Tulsyan
 */
public class LinearReadAtConstantSpeed {
    private static FileChannel fc_virtual_file=null;
    
    long fileSize = 0;
    volatile long offset = 0;
    
    @Test
    public void linearRead()throws Exception{
        fc_virtual_file = createNew_FC_Virtual_File();
        
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
