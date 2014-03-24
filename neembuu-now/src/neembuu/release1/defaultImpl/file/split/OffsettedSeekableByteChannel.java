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
package neembuu.release1.defaultImpl.file.split;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;

/**
 *
 * @author Shashank Tulsyan
 */
public class OffsettedSeekableByteChannel implements SeekableByteChannel {

    private final long offset;
    private final SeekableByteChannel sbc;

    public OffsettedSeekableByteChannel(long offset, SeekableByteChannel sbc) {
        this.offset = offset;
        this.sbc = sbc;
    }

    @Override
    public int read(ByteBuffer dst) throws IOException {
        return sbc.read(dst);
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
        return sbc.write(src);
    }

    @Override
    public long position() throws IOException {
        return offset + sbc.position();
    }

    @Override
    public SeekableByteChannel position(long newPosition) throws IOException {
        sbc.position(newPosition + offset);
        return this;
    }

    @Override
    public long size() throws IOException {
        return offset + sbc.size();
    }

    @Override
    public SeekableByteChannel truncate(long size) throws IOException {
        if(true)throw new UnsupportedOperationException();
        sbc.truncate(size + offset);
        return this;
    }

    @Override
    public boolean isOpen() {
        return sbc.isOpen();
    }

    @Override
    public void close() throws IOException {
        //sbc.close();
    }
}
