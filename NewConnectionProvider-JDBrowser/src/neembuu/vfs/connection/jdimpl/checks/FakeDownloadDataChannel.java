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

package neembuu.vfs.connection.jdimpl.checks;

import java.io.IOException;
import java.nio.ByteBuffer;
import neembuu.vfs.readmanager.DownloadDataChannel;

/**
 *
 * @author Shashank Tulsyan
 */
public class FakeDownloadDataChannel implements DownloadDataChannel{
    
    private final DownloadDataChannel inspiration;

    public FakeDownloadDataChannel(DownloadDataChannel inspiration) {
        this.inspiration = inspiration;
    }
    
    @Override public boolean isAutoHandleThrottlingEnabled() {
        return inspiration.isAutoHandleThrottlingEnabled();
    }

    @Override public void setAutoHandleThrottlingEnabled(boolean ate) { }

    @Override
    public int write(ByteBuffer src) throws IOException {
        throw new UnsupportedOperationException("The execution should not reach this point");  
    }

    @Override public boolean isOpen() { return true;}

    @Override public void close() throws IOException { }
    
}
