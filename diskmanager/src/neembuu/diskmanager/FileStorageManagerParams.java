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


public final class FileStorageManagerParams {
    private final String fileName;
    private final ResumeStateCallback resumeStateCallback;
    private final long estimatedFileSize;
    //private final String ID;

    private FileStorageManagerParams(Builder b) {
        this.fileName = b.fileName;
        this.resumeStateCallback = b.resumeStateCallback;
        this.estimatedFileSize = b.estimatedFileSize;
        //this.ID = b.ID;
    }

    /*public String getID() {
    return ID;
    }*/
    public long getEstimatedFileSize() {
        return estimatedFileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public ResumeStateCallback getResumeStateCallback() {
        return resumeStateCallback;
    }
    
    public static final class Builder {
        private String fileName;
        private ResumeStateCallback resumeStateCallback;
        private String ID;
        private long estimatedFileSize = 0;

        public Builder() {
        }

        public Builder setEstimatedFileSize(long estimatedFileSize) {
            this.estimatedFileSize = estimatedFileSize;
            return this;
        }

        
        
        public Builder setID(String ID) {
            this.ID = ID;
            return this;
        }

        public Builder setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder setResumeStateCallback(ResumeStateCallback resumeStateCallback) {
            this.resumeStateCallback = resumeStateCallback;
            return this;
        }
        
        public FileStorageManagerParams build(){
            if(this.fileName==null){
                throw new IllegalArgumentException("File name not specified");
            }
            if(this.resumeStateCallback==null){
                throw new IllegalArgumentException("Resume callback not specified");
            }if(this.estimatedFileSize==0){
                throw new IllegalArgumentException("File size not specified");
            }
            return new FileStorageManagerParams(this);
        }
    }
}

