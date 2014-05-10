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

package neembuu.release1.api.settings;

import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.OpenOption;

/**
 * No thread safety. Implement thread safety on top of this.
 * Implemented to achieve concurrent read/write access and allow 
 * persistence(save), to orthogonal data.
 * @author Shashank Tulsyan
 */
public interface Settings {

    boolean getBoolean(String ... name);
    
    boolean setBoolean(boolean value,String ... name);
    
    long getLong(String ... name);
    /**
     * 
     * @param value
     * @param name
     * @return data committed or not
     */
    boolean setLong(long value, String ... name);
    
    boolean set(String value, String ... name);
    
    String get(String ... name);

    /**
     * with WRITE,READ,CREATE
     * @param name
     * @return
     * @throws IOException 
     */
    SeekableByteChannel getResource(String ... name) throws IOException;
    
    SeekableByteChannel getResource(String[]name, OpenOption... options) throws IOException;
    
}
