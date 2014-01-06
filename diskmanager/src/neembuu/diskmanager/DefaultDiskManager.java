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

import java.io.IOException;

/**
 *
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
final class DefaultDiskManager implements DiskManager{

    private final DiskManagerParams dmp;

    public DefaultDiskManager(DiskManagerParams dmp) {
        this.dmp = dmp;
    }    
    
    @Override
    public FileStorageManager makeNewFileStorageManager(FileStorageManagerParams fsmp) {
        FileStorageManager fsm = null;
        try{
            fsm = new DefaultFileStorageManager(this,fsmp);
        }catch(IOException ioe){
            throw new RuntimeException(ioe);
        }
        return fsm;
    }

    @Override
    public DiskManagerParams getDiskManagerParams() {
        return dmp;
    }
}
