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
 * 1 global disk manager is required. It supplies a {@link FileStorageManager} for each
 * file being downloaded. Read the function of {@link FileStorageManager} to understand
 * why we have these interfaces.
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
public interface DiskManager {
    
    //FileStorageManager makeNewFileStorageManager(FileStorageManagerParams fsmp);
    
    //DiskManagerParams getDiskManagerParams();
    
    void findPreviousSessions(FindPreviousSessionCallback callback);
    
    MakeSession makeSession(String type);
    
    MakeSession modifySession(Session source);
    
    Session createTestSession()throws IOException;
    
    //LinkedList<java.util.logging.LogRecord> deleteFileStorage(String fileName);
}
