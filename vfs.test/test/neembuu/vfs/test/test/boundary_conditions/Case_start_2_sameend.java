/*
 * Copyright (C) 2011 Shashank Tulsyan
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
package neembuu.vfs.test.test.boundary_conditions;

import neembuu.rangearray.RangeArrayFactory;
import neembuu.rangearray.RangeArrayParams;
import java.nio.channels.FileChannel;
import org.junit.BeforeClass;
import org.junit.Test;
import static junit.framework.Assert.*;
import static neembuu.vfs.test.test.Common.*;
/**
 *
 * @author Shashank Tulsyan
 */
public class Case_start_2_sameend {
        
    private static FileChannel fc_virtual_file=null;//fc_virtual_file = null;
    private static FileChannel fc_real_file=null;//fc_real_file = null;
    
    @BeforeClass
    public static void initalize() throws Exception{
        
        emptyTemporarySplitStorageDirectory();
        
        // * Index                     0                1               2
        // * Offset value          0------>1M-1    3M----->4M-1    5M----->6M-1
        
        fc_real_file = createNew_FC_Real_File();        

        downloadFollowingRegionsBeforeTesting(
            fc_real_file,
             RangeArrayFactory.newDefaultRangeArray(
                new RangeArrayParams.Builder()
                .add(0*MB,1*MB-1)
                .add(3*MB,4*MB-1)
                .add(5*MB,6*MB-1)
                .build()));
        
        show_StartNeembuuMessage();
        
        fc_virtual_file = createNew_FC_Virtual_File();
        
        
        assertNotNull(fc_virtual_file);
        assertNotNull(fc_real_file);

    }
    
    @Test
    public void case_start_2_sameend() throws Exception{
        // * Index                     0                 1              2               3
        // * Offset value          0------>1M-1---M   M+a-->2M-1    3M----->4M-1    5M----->6M-1
        test_start_end(fc_virtual_file, fc_real_file, 1*MB+allowance, 2*MB);
    }
}
