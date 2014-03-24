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

import jpfm.operations.readwrite.ReadRequest;
import neembuu.rangearray.RangeArray;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.vfs.progresscontrol.DownloadSpeedProvider;
import neembuu.vfs.progresscontrol.RequestSpeedProvider;
import neembuu.vfs.readmanager.NewReadHandlerProvider;
import neembuu.vfs.readmanager.ReadRequestState;
import neembuu.vfs.readmanager.RegionHandler;
import neembuu.vfs.readmanager.TotalFileReadStatistics;

/**
 *
 * @author Shashank Tulsyan
 */
public class TotalFileReadStatisticsImpl implements TotalFileReadStatistics{
            
    private final NewReadHandlerProvider provider;
    private final RangeArray<RegionHandler> handlers;
    
    private volatile long totalDataRequested = 0;
    private volatile int totalNumberOfRequestsMade = 0;
    private volatile long openTime = 0;
    
    private final TotalDownloadSpeed totalDownloadSpeed = new TotalDownloadSpeed();
    private final TotalRequestSpeed totalRequestSpeed = new TotalRequestSpeed();

    public TotalFileReadStatisticsImpl(NewReadHandlerProvider provider, RangeArray<RegionHandler> handlers) {
        this.provider = provider;
        this.handlers = handlers;
    }

    void markOpenTime(){
        openTime = System.currentTimeMillis();
    }

    void close(){
        openTime = 0; totalDataRequested = 0;
    }
    
    void newDataRequested(ReadRequest rr){
        //super synchronized(handlers.getModLock() {
        totalDataRequested+=rr.getByteBuffer().capacity();
        totalNumberOfRequestsMade++; // single threaded hence safe
        //}
    }
    
    @Override
    public final long totalDataRequestedSoFar() {
        return totalDataRequested;
    }

    @Override
    public final int totalNumberOfRequestsMade() {
        return totalNumberOfRequestsMade;
    }
    
    @Override
    public long getNewHandlerCreationTime(long offset) {
        return provider.getNewHandlerCreationTime(offset);
    }
    
    @Override
    public DownloadSpeedProvider getTotalAverageDownloadSpeedProvider() {
        return totalDownloadSpeed;
    }

    @Override
    public RequestSpeedProvider getTotalAverageRequestSpeedProvider() {
        return totalRequestSpeed;
    }

    @Override
    public final UnsyncRangeArrayCopy<ReadRequestState> getReadRequestStates() {
        return handlers.tryToGetUnsynchronizedCopy();
    }
    
    private final class TotalDownloadSpeed 
            implements 
                DownloadSpeedProvider{
        
        @Override
        public double getDownloadSpeed_KiBps() {
            //if(openTime==0)return 0;
            
            double totalDownloadSpeed=0;
            
            UnsyncRangeArrayCopy<RegionHandler> regionHandlers
                    = handlers.tryToGetUnsynchronizedCopy();
            for (int i = 0; i < regionHandlers.size(); i++) {
                RegionHandler regionHandler = regionHandlers.get(i).getProperty();
                if(regionHandler!=null)
                    if(regionHandler.isAlive())
                        totalDownloadSpeed+=
                                regionHandlers.get(i).getProperty()
                                .getThrottleStatistics().averageDownloadSpeed_Bps();
            }
            
            return (totalDownloadSpeed  /1024);
        }

    }
    
    private final class TotalRequestSpeed implements RequestSpeedProvider {
        @Override public final double getRequestSpeed_KiBps() {
            if(openTime==0)return 0;
            return totalDataRequested*1000
                    /
                   ((System.currentTimeMillis() - openTime)*1024);
        }
    }
}
