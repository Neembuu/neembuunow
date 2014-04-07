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

import neembuu.diskmanager.impl.DefaultNomenclature;
import neembuu.diskmanager.impl.HtmlLoggerCreator;

/**
 *
 * @author Shashank Tulsyan
 */


public final class DiskManagerParams {
    private final String baseStoragePath;
    private final ResumeStateCallback resumeStateCallback;
    private final Nomenclature nomenclature;
    private final LoggerCreateSPI loggerCreateSPI;

    private DiskManagerParams(Builder b) {
        this.baseStoragePath = b.baseStoragePath;
        this.resumeStateCallback = b.resumeStateCallback;
        this.nomenclature = b.nomenclature;
        this.loggerCreateSPI = b.loggerCreateSPI;
    }

    public LoggerCreateSPI getLoggerCreateSPI() {
        return loggerCreateSPI;
    }

    public Nomenclature getNomenclature() {
        return nomenclature;
    }
    
    public ResumeStateCallback getResumeStateCallback() {
        return resumeStateCallback;
    }

    public String getBaseStoragePath() {
        return baseStoragePath;
    }
    
    public static final class Builder {
        private String baseStoragePath = null;
        private ResumeStateCallback resumeStateCallback = null;
        private Nomenclature nomenclature;
        private LoggerCreateSPI loggerCreateSPI;
        
        public static Builder create(){
            return new Builder();
        }

        public Builder setNomenclature(Nomenclature nomenclature) {
            this.nomenclature = nomenclature; return this;
        }
        
        public Builder useDefaultNomenclatureAndLoggerCreate(){
            setNomenclature(new DefaultNomenclature());
            setLoggerCreateSPI(new HtmlLoggerCreator());
            return this;
        }
        
        public Builder setBaseStoragePath(String baseStoragePath) {
            this.baseStoragePath = baseStoragePath; return this;
        }

        public Builder setResumeStateCallback(ResumeStateCallback resumeStateCallback) {
            this.resumeStateCallback = resumeStateCallback; return this;
        }

        public Builder setLoggerCreateSPI(LoggerCreateSPI loggerCreateSPI) {
            this.loggerCreateSPI = loggerCreateSPI; return this;
        }
        
        public DiskManagerParams build(){
            if(baseStoragePath==null)throw new IllegalArgumentException("Base path not set");
            java.io.File f = new java.io.File(baseStoragePath);
            if(!f.exists())throw new IllegalArgumentException("Base path does not exist");
            if(!f.isDirectory())throw new IllegalArgumentException("Base path is not a directory");
            if(nomenclature==null){
                throw new IllegalArgumentException("Nomenclature not specified");
            }if(loggerCreateSPI==null){
                throw new IllegalArgumentException("Logger create service provider not specified");
            }
            return new DiskManagerParams(this);
        }
    }
}
