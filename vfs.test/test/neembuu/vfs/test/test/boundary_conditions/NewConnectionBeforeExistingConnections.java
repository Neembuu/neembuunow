/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.vfs.test.test.boundary_conditions;

import neembuu.rangearray.RangeArrayFactory;
import neembuu.rangearray.RangeArrayParams;
import org.junit.Test;
import java.nio.channels.FileChannel;
import org.junit.BeforeClass;
import static junit.framework.Assert.*;
import static neembuu.vfs.test.test.Common.*;

/**
 *
 * @author Shashank Tulsyan
 */
public class NewConnectionBeforeExistingConnections {
    private static FileChannel fc_virtual_file=null;//fc_virtual_file = null;
    private static FileChannel fc_real_file=null;//fc_real_file = null;
    
    @BeforeClass
    public static void initalize() throws Exception{
        if(true)throw new UnsupportedOperationException("This test is currently useless.\n"
                + "Original reason for the error was incorrect implementation of"
                + " getIndexPair(long start, long end) in RangeArrayImpl, which has now been fixed."
                + "Don\'t bother about it");
        emptyTemporarySplitStorageDirectory();
        
        // * Index                     0                
        // * Offset value          5M----->6M-1
        
        fc_real_file = createNew_FC_Real_File("s.003");

        /*INFO: Handlers	{
        [0] BasicRegionHandler{130882998-->130914601 ,authl=130914601 isAlive=false ,size=31604}
        [1] BasicRegionHandler{130914602-->130919951 ,authl=130919951 isAlive=false ,size=5350}
        [2] BasicRegionHandler{130919952-->130919952 ,authl=130919952 isAlive=false ,size=1}
        }*/
        
        downloadFollowingRegionsBeforeTesting(
            fc_real_file,
            RangeArrayFactory.newDefaultRangeArray(
                new RangeArrayParams.Builder()
                .setEntriesNeverDissolve()
                .add(130882998,130914601)
                .add(130914602,130919951)
                .add(130919952,130919952)
                .build()),
            "s.003"
        );
        
        show_StartNeembuuMessage();
        
        fc_virtual_file = createNew_FC_Virtual_File("s.003");
        
        
        assertNotNull(fc_virtual_file);
        assertNotNull(fc_real_file);
    }
    
     @Test
    public void test() throws Exception{
        test_start_end(
                fc_virtual_file, 
                fc_real_file, 
                11905247,11968992);
    }
}
