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

package neembuu.diskmanager.impl;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;

/**
 *
 * @author Shashank Tulsyan
 */
public final class JavaStringHashAlgo {
    public static ByteBuffer makeByteBuffer(SeekableByteChannel fc)throws IOException{
        if(fc.size()>Integer.MAX_VALUE){
            throw new UnsupportedOperationException("File too big to make hash");
        }
        int length = (int)fc.size();
        ByteBuffer bb =  ByteBuffer.allocate(length);
        if(fc.read(bb)!=length){
            throw new UnsupportedOperationException("FileChannel didn't read content in one go");
        }
        return bb;
    }
    
    public static Hash makeHash(ByteBuffer bb)throws IOException{
        int h = 0;
        for (int i = 0; i < bb.capacity(); i++) {
            h = 31 * h + bb.get(i);
        }
        return new Hash(h);
    }
    
    public static final class Hash {
        private final int value;

        public Hash(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
        
        public String getAsString(){
            return Integer.toHexString(value);
        }
    }
    
    
}
