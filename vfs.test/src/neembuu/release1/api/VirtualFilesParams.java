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

package neembuu.release1.api;

import neembuu.diskmanager.DiskManager;
import neembuu.vfs.file.TroubleHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public final class VirtualFilesParams {
    private final ReferenceLink link;
    private final DiskManager diskManager;
    private final TroubleHandler troubleHandler;
    private final String fileName;

    public VirtualFilesParams(Builder b) {
        this.link = b.link;
        this.diskManager = b.diskManager;
        this.troubleHandler = b.troubleHandler;
        this.fileName = b.fileName;;
    }

    public ReferenceLink getReferenceLink() {
        return link;
    }

    public DiskManager getDiskManager() {
        return diskManager;
    }

    public TroubleHandler getTroubleHandler() {
        return troubleHandler;
    }

    public String getFileName() {
        return fileName;
    }

    public static final class Builder {
        private ReferenceLink link;
        private DiskManager diskManager;
        private TroubleHandler troubleHandler;
        private String fileName;
        
        private Builder(){
            
        }
        
        public VirtualFilesParams build(){
            return new VirtualFilesParams(this);
        }
        
        public static Builder create(){
            return new Builder();
        }

        public Builder setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }
        
        public Builder setReferenceLink(ReferenceLink link) {
            this.link = link;
            return this;
        }

        public Builder setDiskManager(DiskManager diskManager) {
            this.diskManager = diskManager;
            return this;
        }

        public Builder setTroubleHandler(TroubleHandler troubleHandler) {
            this.troubleHandler = troubleHandler;
            return this;
        }

    }

}
