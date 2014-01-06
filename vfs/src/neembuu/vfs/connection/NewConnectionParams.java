/*
 * Copyright (C) 2011 Shashank Tulsyan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General public final License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General public final License for more details.
 *
 * You should have received a copy of the GNU General public final License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.vfs.connection;

import java.util.logging.Logger;
import neembuu.vfs.readmanager.DownloadDataChannel;
import neembuu.vfs.readmanager.ReadRequestState;
import neembuu.vfs.progresscontrol.Throttle;

/**
 *
 * @author Shashank Tulsyan
 */
public final class NewConnectionParams {
    
    private final long offset;
    private final int minimumSizeRequired;
    private final DownloadDataChannel downloadDataCacheManager;
    private final ReadRequestState readRequestState;
    private final TransientConnectionListener transientConnectionListener;
    private final Object throttlingLock;
    private final Throttle throttle;
    private final Logger downloadThreadLogger;

    private NewConnectionParams(Builder b) {
        this.offset = b.offset;
        this.minimumSizeRequired = b.minimumSizeRequired;
        this.downloadDataCacheManager = b.downloadDataCacheManager;
        this.readRequestState = b.readRequestState;
        this.transientConnectionListener = b.transientConnectionListener;
        this.throttlingLock = b.throttlingLock;
        this.throttle = b.throttle;
        this.downloadThreadLogger = b.downloadThreadLogger;
    }

    /**
     * Sample : <br/>
     * Blocking <br/>
     * <pre>
     * NewConnectionParams newConnectionParams; 
     * Socket s;
     * InputStream in =  s.getInputStream();
     * byte[]b=new byte[BUFFER_SIZE]; int r=0;
     * while( (r=in.read())!=-1){
     *     newConnectionParams.getDownloadDataChannel().write(b,0,r);
     * }
     * <br/>
     * Non Blocking implementation would be made by
     * using <tt> java.nio.channels.SocketChannel </tt> <br/>
     * 
     * </pre>
     * The choice of buffer size and throttling of the connection
     * is the responsibility of the implementation.<br/>
     * The underlying implementation of OutputStream will be non-blocking.
     * <br/><br/>
     * The following specifies why this method is required : <br/>
     * <small>
     * The download data must be passed to this. 
     * Download Data CacheManager will cache the data in memory. 
     * The download data will be saved in a file if and only if
     * Neembuu was given the responsibility to do the disk managing
     * (see {@link DownloadDataStoragePathNegotiator } and {@link #getDownloadDataStoragePathNegotiator() } ). <br/>
     * Cache will be very small (around 3MB) and will improve performance. If caching is not done
     * the time taken for each atomic read request to fulfill would be sum of
     * downloading time, writing time (to disk) and reading time(from disk).
     * If caching is done, the net time is only downloading time.
     * The reading and writing time on disk are small, but sometimes, when the
     * system is busy, the operating system, leaves such requests pending
     * for a visible (in order of seconds) amount of time. To avoid 
     * this and provide user with a jerk free "watch as you download" experience
     * caching is done.</small>
     * @return The output stream where recently downloaded data must be send
     * for quick completion of pending read requests.
     */
    public final DownloadDataChannel getDownloadDataChannel() { 
        return downloadDataCacheManager;
    }

    /**
     * @return An object over which the thread should wait for all throttling
     * purposes. This object should be notified to restart a waiting thread.
     */
    public final Object getThrottlingLock() {
        return throttlingLock;
    }

    /**
     * @return The minimum number of bytes that MUST be downloaded.
     * The connection should not terminate before downloading this number of bytes.
     * This is actually the buffer size of the request, which caused the opening 
     * of this connection. The connection can close on it's own discretion. 
     */
    public final int getMinimumSizeRequired() { 
        return minimumSizeRequired;
    }

    /**
     * @return The absolute String offset where download must begin
     */
    public final long getOffset() { 
        return offset;
    }

    /**
     * 
     * @return This contains information that is useful
     * for deciding whether the speed of the connection should be increased or
     * decreased or whether the connection should be terminated. This is purely
     * suggestive. Not paying attention to these values will not stop the 
     * "watch as you download" system from working, but would make the user
     * experience bad. Therefore throttle should be designed carefully which
     * functions based on values obtained by this.
     */
    public final ReadRequestState getReadRequestState() { 
        return readRequestState;
    }

    /**
     * @return This should receive
     * the connection when it is made.
     */
    public final TransientConnectionListener getTransientConnectionListener() { 
        return transientConnectionListener;
    }

    public final Throttle getThrottle() {
        return throttle;
    }
    
    @Override
    public final String toString() {
        return "Offset="+offset
                + " readrequeststate="+readRequestState; 
    }

    public Logger getDownloadThreadLogger() {
        return downloadThreadLogger;
    }    
    
    public final static class Builder {
        private long offset = -1;
        private int minimumSizeRequired = 0;
        private DownloadDataChannel downloadDataCacheManager = null;
        private ReadRequestState readRequestState = null;
        private TransientConnectionListener transientConnectionListener = null;
        private Object throttlingLock;
        private Throttle throttle;
        private Logger downloadThreadLogger;

        public Builder() {
        }

        public final  DownloadDataChannel getDownloadDataChannel() {
            return downloadDataCacheManager;
        }

        public final  int getMinimumSizeRequired() {
            return minimumSizeRequired;
        }

        public final  long getOffset() {
            return offset;
        }

        public Logger getDownloadThreadLogger() {
            return downloadThreadLogger;
        }
        
        public final  ReadRequestState getReadRequestState() {
            return readRequestState;
        }

        public final  TransientConnectionListener getTransientConnectionListener() {
            return transientConnectionListener;
        }

        public Builder setDownloadThreadLogger(Logger downloadThreadLogger) {
            this.downloadThreadLogger = downloadThreadLogger;
            return this;
        }
        
        public Builder setThrottlingLock(Object throttlingLock) {
            this.throttlingLock = throttlingLock;return this;
        }

        /**
         * @param downloadDataCacheManager The download data must be passed to this. 
         * Download data CacheManager will cache the data in memory and also save it in appropriate String. 
         * Cache will be very small (around 3MB) and will improve performance. If caching is not done
         * the time taken for each atomic read request to fulfill would be sum of
         * downloading time, writing time (to disk) and reading time(from disk).
         * If caching is done, the net time is only downloading time.
         * The reading and writing time on disk are small, but something, when the
         * system is buys, the operating system, leaves such requests pending
         * for a visible (in order of seconds) amount of time.
         * @return this
         */
        public final Builder setDownloadDataChannel(DownloadDataChannel downloadDataCacheManager) {
            this.downloadDataCacheManager = downloadDataCacheManager;
            return this;
        }

        /**
         * optional parameter, default = 0;
         * @param minimumSizeRequired  The minimum number of bytes that MUST be downloaded.
         * The connection should not terminate before downloading this number of bytes.
         * This is actually the buffer size of the request, which caused the opening 
         * of this connection. The connection can close on it's own discretion. 
         * @return 
         */
        public final Builder setMinimumSizeRequired(int minimumSizeRequired) {
            this.minimumSizeRequired = minimumSizeRequired;
            return this;
        }
     
        /**
         * @param offset The absolute String offset where download must begin
         * @return this
         */
        public final Builder setOffset(long offset) {
            if(offset == -1) throw new IllegalArgumentException("Offset has not been specified");
            this.offset = offset;
            return this;
        }

        /**
         * 
         * @param readRequestState This contains information that is useful
         * for deciding whether the speed of the connection should be increased or
         * decreased or whether the connection should be terminated. This is purely
         * suggestive. Not paying attention to these values will not stop the 
         * "watch as you download" system from working, but would make the user
         * experience bad. Therefore throttle should be designed carefully which
         * functions based on values obtained by this.
         * @return this
         */
        public final Builder setReadRequestState(ReadRequestState readRequestState) {
            this.readRequestState = readRequestState;
            return this;
        }

        /**
         * 
         * @param transientConnectionListener This should receive
         * the connection when it is made.
         * @return 
         */
        public final Builder setTransientConnectionListener(TransientConnectionListener transientConnectionListener) {
            this.transientConnectionListener = transientConnectionListener;
            return this;
        }

        public final Builder setThrottle(Throttle throttle) {
            this.throttle = throttle;
            return this;
        }
        
        public final NewConnectionParams build(){
            if(offset == -1) throw new IllegalArgumentException("Offset has not been specified");
            if(downloadDataCacheManager==null || readRequestState==null
                    || transientConnectionListener==null )
                throw new IllegalArgumentException("some compulsary parameter was null");
            
            NewConnectionParams cp = new NewConnectionParams(this);
            if(throttle==null){
                //cp.throttle = ThrottleFactory.General.SINGLETON.createNewThrottle(cp);
                throw new RuntimeException("Should use throttle");
            }
            
            if(downloadThreadLogger==null){
                throw new NullPointerException("Download Thread Logger is an essential parameter");
            }
            if(cp.getOffset()>=cp.getReadRequestState().fileSize()){
                throw new IllegalArgumentException("Requsting new connection beyond filesize."
                        + " Filesize="+cp.getReadRequestState().fileSize()+"."
                        + " Requested offset="+cp.getOffset());
            }
            return cp;
        }
        
    }

}
