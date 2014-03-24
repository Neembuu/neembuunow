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

import neembuu.rangearray.PropertyRescaler;
import neembuu.vfs.progresscontrol.ThrottleStatistics;
import neembuu.vfs.readmanager.ConnectionControls;
import neembuu.vfs.readmanager.ReadRequestState;
import neembuu.vfs.readmanager.TotalFileReadStatistics;

/**
 *
 * @author Shashank Tulsyan
 */
public class ReadRequestRescaler implements PropertyRescaler<ReadRequestState> {

    @Override
    public ReadRequestState rescale(ReadRequestState torescale, long offsetBy) {
        return new RescaledReadRequestState(torescale, offsetBy);
    }
    
    public static final class RescaledReadRequestState implements ReadRequestState{
        private final ReadRequestState original; private final long offset;

        public RescaledReadRequestState(ReadRequestState original, long offset) {
            this.original = original; this.offset = offset;}

        
        @Override
        public long starting() {
            return original.starting()+offset;
        }

        @Override
        public long ending() {
            return original.ending()+offset;
        }

        @Override
        public long authorityLimit() {
            return original.authorityLimit()+offset;
        }

        
        @Override public long fileSize() { return original.fileSize(); }
        @Override public String getFileName() { return original.getFileName(); }
        @Override public TotalFileReadStatistics getTotalFileReadStatistics() { return original.getTotalFileReadStatistics(); }
        @Override public boolean isAlive() { return original.isAlive(); }
        @Override public ThrottleStatistics getThrottleStatistics() { return original.getThrottleStatistics(); }
        @Override public int numberOfPendingRequests() { return original.numberOfPendingRequests(); }
        @Override public boolean hasPendingRequests() { return original.hasPendingRequests(); }
        @Override public String tryGettingPendingRequestsList() { return original.tryGettingPendingRequestsList(); }
        @Override public boolean isMainDirectionOfDownload() { return original.isMainDirectionOfDownload();         }
        @Override public ConnectionControls getConnectionControls() { return original.getConnectionControls(); }
    }
}
