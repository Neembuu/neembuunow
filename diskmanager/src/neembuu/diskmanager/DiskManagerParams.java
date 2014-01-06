/*
 * Copyright (c) 2011 Shashank Tulsyan <shashaanktulsyan@gmail.com>. 
 * 
 * This is part of free software: you can redistribute it and/or modify
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
 * along with this.  If not, see <http ://www.gnu.org/licenses/>.
 */
package neembuu.diskmanager;

/**
 *
 * @author Shashank Tulsyan
 */


public final class DiskManagerParams {
    private final int maxReadQueueManagerThreadLogSize;
    private final int maxDownloadThreadLogSize;
    private final int maxReadHandlerThreadLogSize;
    private final String baseStoragePath;
    private final ResumeStateCallback resumeStateCallback;

    private DiskManagerParams(Builder b) {
        this.maxReadQueueManagerThreadLogSize = b.maxReadQueueManagerThreadLogSize;
        this.maxDownloadThreadLogSize = b.maxDownloadThreadLogSize;
        this.maxReadHandlerThreadLogSize = b.maxReadHandlerThreadLogSize;
        this.baseStoragePath = b.baseStoragePath;
        this.resumeStateCallback = b.resumeStateCallback;
    }

    public ResumeStateCallback getResumeStateCallback() {
        return resumeStateCallback;
    }
    
    public int getMaxDownloadThreadLogSize() {
        return maxDownloadThreadLogSize;
    }

    public int getMaxReadHandlerThreadLogSize() {
        return maxReadHandlerThreadLogSize;
    }

    public int getMaxReadQueueManagerThreadLogSize() {
        return maxReadQueueManagerThreadLogSize;
    }

    public String getBaseStoragePath() {
        return baseStoragePath;
    }
    
    public static final class Builder {
        private int maxReadQueueManagerThreadLogSize = 2*1024*1024;
        private int maxDownloadThreadLogSize = 100*1024;
        private int maxReadHandlerThreadLogSize = 100*1024;
        private String baseStoragePath = null;
        private ResumeStateCallback resumeStateCallback = null;

        public Builder setBaseStoragePath(String baseStoragePath) {
            this.baseStoragePath = baseStoragePath;
            return this;
        }

        public Builder setMaxDownloadThreadLogSize(int maxDownloadThreadLogSize) {
            this.maxDownloadThreadLogSize = maxDownloadThreadLogSize;
            return this;
        }

        public Builder setMaxReadHandlerThreadLogSize(int maxReadHandlerThreadLogSize) {
            this.maxReadHandlerThreadLogSize = maxReadHandlerThreadLogSize;
            return this;
        }

        public Builder setMaxReadQueueManagerThreadLogSize(int maxReadQueueManagerThreadLogSize) {
            this.maxReadQueueManagerThreadLogSize = maxReadQueueManagerThreadLogSize;
            return this;
        }

        public Builder setResumeStateCallback(ResumeStateCallback resumeStateCallback) {
            this.resumeStateCallback = resumeStateCallback;
            return this;
        }
        
        public DiskManagerParams build(){
            if(baseStoragePath==null)throw new IllegalArgumentException("Base path not set");
            java.io.File f = new java.io.File(baseStoragePath);
            if(!f.exists())throw new IllegalArgumentException("Base path does not exist");
            if(!f.isDirectory())throw new IllegalArgumentException("Base path is not a directory");
            return new DiskManagerParams(this);
        }
    }
}
