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

package neembuu.release1.ui.linkpanel;

import neembuu.rangearray.RangeArray;
import neembuu.rangearray.RangeArrayFactory;
import neembuu.rangearray.RangeArrayParams;
import neembuu.rangearray.UIRangeArrayAccess;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.vfs.file.FileBeingDownloaded;
import neembuu.vfs.file.RequestPatternListener;
import neembuu.vfs.progresscontrol.DownloadSpeedProvider;
import neembuu.vfs.progresscontrol.RequestSpeedProvider;
import neembuu.vfs.readmanager.ReadRequestState;
import neembuu.vfs.readmanager.RegionHandler;
import neembuu.vfs.readmanager.TotalFileReadStatistics;

/**
 *
 * @author Shashank Tulsyan
 */
public class FakeFileBeingDownloaded implements FileBeingDownloaded{

    final long fileSize = 20*1024*1024+112312;
    
    RangeArray<RegionHandler> downloadedRegions = RangeArrayFactory.newDefaultRangeArray(
            new RangeArrayParams.Builder()
                    .setFileSize(fileSize)
                    .build()
    );

    
    @Override
    public UIRangeArrayAccess getRegionHandlers() {
        return downloadedRegions;
    }

    @Override
    public TotalFileReadStatistics getTotalFileReadStatistics() {
        return new TotalFileReadStatistics() {

            @Override
            public long totalDataRequestedSoFar() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int totalNumberOfRequestsMade() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public UnsyncRangeArrayCopy<ReadRequestState> getReadRequestStates() {
                return downloadedRegions.tryToGetUnsynchronizedCopy();
            }

            @Override
            public DownloadSpeedProvider getTotalAverageDownloadSpeedProvider() {
                return new DownloadSpeedProvider() {

                    @Override
                    public double getDownloadSpeed_KiBps() {
                        return 150.45d;
                    }
                };
            }

            @Override
            public RequestSpeedProvider getTotalAverageRequestSpeedProvider() {
                return new RequestSpeedProvider() {

                    @Override
                    public double getRequestSpeed_KiBps() {
                        return 120.2321d;
                    }
                };
            }

            @Override
            public long getNewHandlerCreationTime(long offset) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }

    @Override
    public boolean isAutoCompleteEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "XYZ Ep - 09 When Aliens came to planet earth.rmvb";
    }

    @Override
    public long getFileSize() {
        return fileSize;
    }

    @Override
    public void addRequestPatternListener(RequestPatternListener rpl) {
        
    }

    @Override
    public void removeRequestPatternListener(RequestPatternListener rpl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
