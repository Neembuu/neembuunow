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

import org.junit.Test;
import java.nio.channels.SeekableByteChannel;
import org.junit.BeforeClass;
import static junit.framework.Assert.*;
import static neembuu.vfs.test.test.Common.*;

/**
 *
 * @author Shashank Tulsyan
 */
public final class LinearReadTest {
    private static SeekableByteChannel /*FileChannel*/ fc_virtual_file=null;//fc_virtual_file = null;
    private static SeekableByteChannel /*FileChannel*/ fc_real_file=null;//fc_real_file = null;

    @BeforeClass
    public static void initalize() throws Exception{
        emptyTemporarySplitStorageDirectory();       
        boolean cascade = show_StartNeembuuMessage();
        fc_virtual_file = createNew_FC_Virtual_File(cascade);
        fc_real_file = createNew_FC_Real_File();        
        
        assertNotNull(fc_virtual_file);
        assertNotNull(fc_real_file);
    }
    
    @Test
    public void linearRead() throws Exception{
        test_start_size(fc_virtual_file, fc_real_file, 0, 1*MB);
    }
    
}
