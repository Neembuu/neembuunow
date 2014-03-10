/*
 *  Copyright (C) 2014 Shashank Tulsyan
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package neembuu.vfs.file;

import jpfm.DirectoryStream;
import neembuu.diskmanager.DiskManager;
import neembuu.vfs.connection.NewConnectionProvider;
import neembuu.vfs.progresscontrol.ThrottleFactory;

/**
 *
 * @author Shashank Tulsyan
 */
public final class SeekableConnectionFileParams {
    private final String fileName;
    private final long size;
    private final DirectoryStream parent;
    private final NewConnectionProvider connectionProvider;
    private final DiskManager diskManager;
    private final ThrottleFactory throttleFactory;
    private final TroubleHandler troubleHandler;
    private final AskResume askResume;

    private SeekableConnectionFileParams(
            Builder b) {
        this.fileName = b.fileName;
        this.size = b.size;
        this.parent = b.parent;
        this.connectionProvider = b.connectionProvider;
        this.diskManager = b.diskManager;
        this.throttleFactory = b.throttleFactory;
        this.troubleHandler = b.troubleHandler;
        this.askResume = b.askResume;
    }

    public TroubleHandler getTroubleHandler() {
        return troubleHandler;
    }
    
    public NewConnectionProvider getConnectionProvider() {
        return connectionProvider;
    }

    public DiskManager getDiskManager() {
        return diskManager;
    }

    public String getFileName() {
        return fileName;
    }

    public DirectoryStream getParent() {
        return parent;
    }

    public long getSize() {
        return size;
    }

    public ThrottleFactory getThrottleFactory() {
        return throttleFactory;
    }

    public AskResume getAskResume() {
        return askResume;
    }
    
    public static final class Builder {
        private String fileName;
        private long size;
        private DirectoryStream parent;
        private NewConnectionProvider connectionProvider;
        private DiskManager diskManager;
        private ThrottleFactory throttleFactory;
        private TroubleHandler troubleHandler;
        private AskResume askResume;

        public Builder setNewConnectionProvider(NewConnectionProvider connectionProvider) {
            this.connectionProvider = connectionProvider;
            return this;
        }

        public Builder setTroubleHandler(TroubleHandler troubleHandler) {
            this.troubleHandler = troubleHandler;
            return this;
        }

        public Builder setDiskManager(DiskManager diskManager) {
            this.diskManager = diskManager;
            return this;
        }

        public Builder setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder setParent(DirectoryStream parent) {
            this.parent = parent;
            return this;
        }

        public Builder setFileSize(long size) {
            this.size = size;
            return this;
        }

        public Builder setThrottleFactory(ThrottleFactory throttleFactory) {
            this.throttleFactory = throttleFactory;
            return this;
        }

        public Builder setAskResume(AskResume askResume) {
            this.askResume = askResume;
            return this;
        }        
        
        public SeekableConnectionFileParams build(){
            if(fileName==null){
                throw new IllegalArgumentException("File name is a compulsary parameter");
            }if(size<=0){
                throw new IllegalArgumentException("Filesize less or than equal to zero");
            }if(throttleFactory==null){
                throw new IllegalArgumentException("Throttle factory is a compulsary parameter. "
                        + "Try using GeneralThrottle");
            }if(diskManager==null){
                throw new IllegalArgumentException("DiskManager is a compulsary parameter. "
                        + "Try using DiskManagers.getDefaultManager");
            }if(parent==null){
                //throw new IllegalArgumentException("No parent specified");
            }if(connectionProvider == null){
                throw new IllegalArgumentException("NewConnectionProvider is a compulsary parameter. ");
            }if(troubleHandler==null){
                throw new IllegalArgumentException("TroubleHandler is a compulsary parameter. ");
            }if(askResume==null){
                throw new IllegalArgumentException("AskResume is a compulsary parameter. ");
            }
            return new SeekableConnectionFileParams(this);
        }
    }
    
}
