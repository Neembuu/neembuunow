/*
 * Copyright (C) 2014 Shashank Tulsyan
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

package neembuu.vfs.test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import jpfm.fs.ReadOnlyRawFileData;
import jpfm.operations.readwrite.SimpleReadRequest;
import neembuu.vfs.file.SeekableConnectionFile;

/**
 *
 * @author Shashank Tulsyan
 */
public class SeekableByteChannel_from_SeekableConnectionFile implements SeekableByteChannel{
    private final SeekableConnectionFile scf;

    private long position = 0 ;
    
    private final ReadOnlyRawFileData fileData;
    
    public SeekableByteChannel_from_SeekableConnectionFile(SeekableConnectionFile scf) {
        this.scf = scf;
        fileData = scf.getReference(null, null);
    }
    
    @Override
    public int read(ByteBuffer dst) throws IOException {
        SimpleReadRequest  srr = new SimpleReadRequest(dst, position);
        System.out.println(srr);
        try{
            scf.read(srr);
            while(!srr.isCompleted()){
                Thread.sleep(100);
            }
        }catch(Exception a){
            a.printStackTrace();
        }
        return dst.capacity();
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long position() throws IOException {
        return position;
    }

    @Override
    public SeekableByteChannel position(long newPosition) throws IOException {
        position = newPosition;
        return this;
    }

    @Override
    public long size() throws IOException {
        return scf.getFileSize();
    }

    @Override
    public SeekableByteChannel truncate(long size) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isOpen() {
        return fileData.isOpen();
    }

    @Override
    public void close() throws IOException {
        fileData.close();
    }
}
