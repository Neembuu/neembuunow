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
package neembuu.vfs.readmanager.rqm;

import java.nio.ByteBuffer;
import jpfm.JPfmError;
import jpfm.operations.readwrite.Completer;
import jpfm.operations.readwrite.ReadRequest;
import jpfm.operations.readwrite.SimpleReadRequest;

/**
 *
 * @author Shashank Tulsyan
 */
public class SpecialReadRequest implements ReadRequest {

    private final ReadRequest rr = new SimpleReadRequest(ByteBuffer.allocate(1), 0);

    @Override
    public void setCompleter(Completer completehandler) {
        rr.setCompleter(completehandler);
    }

    @Override
    public boolean isCompleted() {
        return rr.isCompleted();
    }

    @Override
    public void handleUnexpectedCompletion(Exception exception) {
        rr.handleUnexpectedCompletion(exception);
    }

    @Override
    public long getFileOffset() {
        return rr.getFileOffset();
    }

    @Override
    public JPfmError getError() throws IllegalStateException {
        return rr.getError();
    }

    @Override
    public long getCreationTime() {
        return rr.getCreationTime();
    }

    @Override
    public long getCompletionTime() {
        return rr.getCompletionTime();
    }

    @Override
    public ByteBuffer getByteBuffer() {
        return rr.getByteBuffer();
    }

    @Override
    public void complete(JPfmError error) throws IllegalArgumentException, IllegalStateException {
        rr.complete(error);
    }

    @Override
    public void complete(JPfmError error, int actualRead, Completer completer) throws IllegalArgumentException, IllegalStateException {
        rr.complete(error, actualRead, completer);
    }

    @Override
    public boolean canComplete(Completer completehandler) {
        return rr.canComplete(completehandler);
    }

}
