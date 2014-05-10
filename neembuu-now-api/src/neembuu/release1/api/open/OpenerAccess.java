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

package neembuu.release1.api.open;

/**
 *
 * @author Shashank Tulsyan
 */
public interface OpenerAccess {
    /**
     * We do not want to expose this to other modules/classes.
     * This is called when the NeembuuNow instance exists.
     * It's job is to close all instances of any type of file
     * or folder left open, because these zombie open instances 
     * vomit many error messages and user gets a bad impression 
     * about the NeembuuNow program.
     */
    void closeAll();
    
    /**
     * Removes an instance of Open associated with this file path
     * from the list (or map) of open handles.
     * @param filePath
     * @return the removed open handle associated with the given filePath
     */
    Open openHandles_remove(String filePath);
}
