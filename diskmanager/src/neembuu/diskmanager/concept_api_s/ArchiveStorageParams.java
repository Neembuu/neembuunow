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
package neembuu.diskmanager.concept_api_s;

/**
 *
 * @author Shashank Tulsyan
 */


public final class ArchiveStorageParams {
    private final String archiveName;

    private ArchiveStorageParams(Builder b) {
        this.archiveName = b.archiveName;
    }


    
    public static final class Builder {
        private String archiveName;

        public Builder setArchiveName(String archiveName) {
            this.archiveName = archiveName;
            return this;
        }

        public ArchiveStorageParams build(){
            return new ArchiveStorageParams(this);
        }
    }
}
