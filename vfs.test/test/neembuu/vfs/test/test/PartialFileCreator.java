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

package neembuu.vfs.test.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import neembuu.rangearray.Range;
import neembuu.rangearray.RangeArray;
import neembuu.rangearray.RangeUtils;

/**
 *
 * @author Shashank Tulsyan
 */
public final class PartialFileCreator {
    
    private final RangeArray<Range> array;
    private final File destinationDirectory;
    private final File sourceFile;
    

    public PartialFileCreator(RangeArray<Range> array, File destinationDirectory, File sourceFile) {
        this.array = array;
        this.destinationDirectory = destinationDirectory;
        this.sourceFile = sourceFile;
    }    
    
    public void create() throws IOException {
        FileChannel fc = new FileInputStream(sourceFile).getChannel();
        for(Range element : array){
            FileChannel fc_= fc.position(element.starting());
            File part = new File(destinationDirectory,"_0x"+Long.toHexString(element.starting())+".partial" );
            FileChannel partfc = new FileOutputStream(part).getChannel();
            partfc.transferFrom(fc_, 0, RangeUtils.getSize(element));
            partfc.force(true);
            partfc.close();
        }
        fc.close();
    }
}
