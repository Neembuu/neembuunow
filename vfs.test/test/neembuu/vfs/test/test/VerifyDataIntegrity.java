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
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * @author Shashank Tulsyan
 */
public final class VerifyDataIntegrity {
    final File dataDirectory;
    final File completeFile;

    public VerifyDataIntegrity(File dataDirectory, File completeFile) {
        this.dataDirectory = dataDirectory;
        this.completeFile = completeFile;
    }
    
    public final void verify()throws  IOException{
        File[]files = dataDirectory.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".partial");
            }
        });
        
        for(File f : files){
            String nm = f.getName();
            long startingOffset = Long.parseLong(nm.substring(nm.indexOf("_0x")+3,nm.lastIndexOf('.')),16);
            
            FileChannel fc_ = new FileInputStream(f).getChannel();
            FileChannel fc = new FileInputStream(completeFile).getChannel().position(startingOffset);
            ByteBuffer buffer = ByteBuffer.allocate((int)fc_.size());
            ByteBuffer buffer_ = ByteBuffer.allocate((int)fc_.size());
            fc_.read(buffer_);
            fc.read(buffer);
            
            System.out.println("file="+nm);
            BoundaryConditions.printContentPeek(buffer,buffer_);
            System.out.println("buffersize="+buffer.capacity()+" filesize="+f.length() );
            
            assert(buffer.equals(buffer_));
            
            fc.close();
            fc_.close();
        }
        
    }
    
    public static void main(String[] args)throws Exception {
        VerifyDataIntegrity itegrity = new VerifyDataIntegrity(new File("j:\\neembuu\\heap\\test120k.http.rmvb_neembuu_download_data" ),new File("j:\\neembuu\\realfiles\\test120k.rmvb"));
        itegrity.verify();
    }
    
}
