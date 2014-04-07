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

package neembuu.release1.api.file;

import java.util.List;
import neembuu.vfs.file.AutoCompleteControls;
import neembuu.vfs.file.FileBeingDownloaded;

/**
 *
 * @author Shashank Tulsyan
 */
public interface NeembuuFile extends Saveable{

    AutoCompleteControls autoCompleteControls();

    void closeCompletely()throws Exception;

    FileBeingDownloaded fileBeingDownloaded();

    void removeFromFileSystem() throws Exception;
    
    void addToFileSystem() ;
    
    String[] relativePathInVirtualFileSystem();
    
    boolean isCompletelyClosed();
    
    List<NeembuuFile> getVariants();  
    
    PropertyProvider getPropertyProvider();
}
