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
import java.nio.channels.SeekableByteChannel;
import org.junit.BeforeClass;
import org.junit.Test;
import static junit.framework.Assert.*;
import static neembuu.vfs.test.test.Common.*;

/**
 * Source : //"http://rbidocs.rbi.org.in/rdocs/Publications/PDFs/159T_HBSE200910.pdf"
 * Using foxit reader 5.0.1.053
 * +++++++++ operations dipatched but not completed yet ++++++++++++
 * Read{ creationTime = 13:56:2.100 offset = 908591 buffercapacity = 512 { FileId = 91 } { not completed yet } }
 * -------- operations dipatched but not completed yet -------------
 * +++++++++ operations not dipatched yet ++++++++++++
 * -------- operations not dipatched yet -------------
 * +++++++159T_HBSE200910.pdf++++++
 * Requested Region
 * {
 *  index=0 0->32767 32768
 *  index=1 908591->909102 512
 *  index=2 909103->909254 152
 *  index=3 917383->917902 520
 * }
 * -------------------------------
 * @author Shashank Tulsyan
 */
public class case_start_1_newcon_end {

    private static SeekableByteChannel /*FileChannel*/ fc_virtual_file=null;//fc_virtual_file = null;
    private static SeekableByteChannel /*FileChannel*/ fc_real_file=null;//fc_real_file = null;

    @BeforeClass
    public static void initalize() throws Exception {

        emptyTemporarySplitStorageDirectory();
        fc_real_file = createNew_FC_Real_File();
        downloadFollowingRegionsBeforeTesting(
                fc_real_file,
                 RangeArrayFactory.newDefaultRangeArray(
                    new RangeArrayParams.Builder()
                    .add(0, 32767)
                    .add(908591, 909102)
                    .add(909103, 909254)
                    .add(917383, 917902)

                    //.add(908742,917382+100000)
                    .build()));

        boolean cascade = show_StartNeembuuMessage();
        fc_virtual_file = createNew_FC_Virtual_File(cascade);


        assertNotNull(fc_virtual_file);
        assertNotNull(fc_real_file);

    }

    @Test
    public void test() throws Exception {
        /*test_start_size(
                fc_virtual_file,
                fc_real_file,
                908591,
                512);*/
        
        /*test_start_end(
                fc_virtual_file,
                fc_real_file,
                0,
                1);*/
        
        test_start_end(
                fc_virtual_file,
                fc_real_file,
                908742,
                917382);
        
        test_start_end(
                fc_virtual_file,
                fc_real_file,
                908591
               //-200000
                ,
                917382);
    }
}
