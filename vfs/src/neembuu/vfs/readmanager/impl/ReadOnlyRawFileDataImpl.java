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
package neembuu.vfs.readmanager.impl;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import jpfm.fs.ReadOnlyRawFileData;
import jpfm.operations.readwrite.ReadRequest;
import jpfm.operations.readwrite.SimpleReadRequest;

/**
 *
 * @author Shashank Tulsyan
 */
public final class ReadOnlyRawFileDataImpl implements ReadOnlyRawFileData {

    private final SeekableConnectionFileImpl scfi;
    private final AtomicBoolean open = new AtomicBoolean(false);
    
    private final int suggestedDataGlimpseSize = 10;

    ReadOnlyRawFileDataImpl(SeekableConnectionFileImpl scfi) {
        this.scfi = scfi;
        scfi.openCascade();
        open.set(true);
    }

    @Override
    public ByteBuffer getDataGlimpse() {
        checkOpen();
        ByteBuffer buffer;
        if (suggestedDataGlimpseSize == 0) {
            buffer = ByteBuffer.allocate(10);
        } else {
            buffer
                    = ByteBuffer.allocate(suggestedDataGlimpseSize);
        }
        SimpleReadRequest srr
                = new SimpleReadRequest(buffer, 0);
        try {
            read(srr);
        } catch (Exception any) {
            return buffer;
        }
        while (!srr.isCompleted()) {
            try {
                Thread.sleep(100);
            } catch (Exception any) {

            }
        }
        return buffer;
    }

    @Override
    public String getName() {
        checkOpen();
        return scfi.getName();
    }

    @Override
    public void read(ReadRequest readRequest) throws Exception {
        checkOpen();
        scfi.read(readRequest);
    }

    @Override
    public long getFileSize() {
        checkOpen();
        return scfi.getFileSize();
    }

    @Override
    public boolean isOpen() {
        return open.get();
    }

    private void checkOpen() {
        if (!isOpen()) {
            throw new IllegalStateException("This ReadOnlyRawFileData has been closed, open a new one");
        }
    }

    @Override
    public void close() {
        checkOpen();
        scfi.getCascadeReferenceCount().decrementAndGet();
        scfi.closeCascade();
    }

}
