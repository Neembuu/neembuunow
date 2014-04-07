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

package neembuu.diskmanager.impl.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.util.Collection;

/**
 *
 * @author Shashank Tulsyan
 */
public final class SBCRef implements SeekableByteChannel{
    private final SeekableByteChannel sbc;
    private final Collection parent;

    public SBCRef(SeekableByteChannel sbc, Collection c) {
        this.sbc = sbc;
        this.parent = c;
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
        return sbc.position();
    }

    @Override
    public SeekableByteChannel position(long newPosition) throws IOException {
        return sbc.position(newPosition);
    }

    @Override
    public long size() throws IOException {
        return sbc.size();
    }

    @Override
    public SeekableByteChannel truncate(long size) throws IOException {
        return sbc.truncate(size);
    }

    @Override
    public boolean isOpen() {
        return sbc.isOpen();
    }

    void onlyClose() throws IOException {
        sbc.close();
    }
    
    @Override
    public void close() throws IOException {
        sbc.close();
        parent.remove(this);
    }
    
}
