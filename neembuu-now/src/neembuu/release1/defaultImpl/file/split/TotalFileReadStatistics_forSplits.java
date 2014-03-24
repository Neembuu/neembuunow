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

import java.util.List;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.vfs.file.SeekableConnectionFile;
import neembuu.vfs.progresscontrol.DownloadSpeedProvider;
import neembuu.vfs.progresscontrol.RequestSpeedProvider;
import neembuu.vfs.readmanager.ReadRequestState;
import neembuu.vfs.readmanager.TotalFileReadStatistics;

/**
 *
 * @author Shashank Tulsyan
 */
public class TotalFileReadStatistics_forSplits implements TotalFileReadStatistics {
    private final List<SeekableConnectionFile> connectionFiles;

    public TotalFileReadStatistics_forSplits(List<SeekableConnectionFile> connectionFiles) {
        this.connectionFiles = connectionFiles;
    }
    
    @Override public DownloadSpeedProvider getTotalAverageDownloadSpeedProvider() {return totalDownloadSpeedProvider;}
    @Override public RequestSpeedProvider getTotalAverageRequestSpeedProvider() { return totalRequestSpeedProvider; }
    @Override public long totalDataRequestedSoFar() { throw new UnsupportedOperationException("Not supported yet.");  }
    @Override public int totalNumberOfRequestsMade() {  throw new UnsupportedOperationException("Not supported yet.");  }
    @Override public UnsyncRangeArrayCopy<ReadRequestState> getReadRequestStates() { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public long getNewHandlerCreationTime(long offset) { throw new UnsupportedOperationException("Not supported yet."); }
    
    private final DownloadSpeedProvider totalDownloadSpeedProvider = new DownloadSpeedProvider() {
        @Override public double getDownloadSpeed_KiBps() {
            double totalAverageDownloadSpeed = 0;
            for (SeekableConnectionFile seekableConnectionFile : connectionFiles) {
                totalAverageDownloadSpeed += seekableConnectionFile.getTotalFileReadStatistics()
                        .getTotalAverageDownloadSpeedProvider().getDownloadSpeed_KiBps();
            }return totalAverageDownloadSpeed; 
        }};
    
    
    private final RequestSpeedProvider totalRequestSpeedProvider = new RequestSpeedProvider() {
        @Override public double getRequestSpeed_KiBps() {
            double totalAverageRequestSpeed = 0;
            for (SeekableConnectionFile seekableConnectionFile : connectionFiles) {
                totalAverageRequestSpeed += seekableConnectionFile.getTotalFileReadStatistics()
                        .getTotalAverageRequestSpeedProvider().getRequestSpeed_KiBps();
            }return totalAverageRequestSpeed; 
        }};
}
