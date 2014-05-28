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

package neembuu.util;

import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.security.MessageDigest;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 *
 * @author Shashank Tulsyan
 */
public class CalculateHash {
    public static String asString(Path filePath,String algorithm) {
        int buff = 100*1024;
        try {
            MessageDigest hashSum = MessageDigest.getInstance(algorithm);
            int read;
            byte[] buffer;
            try (RandomAccessFile file = new RandomAccessFile(filePath.toFile(), "r")) {
                buffer = new byte[buff];
                read = 0;
                long offset = file.length();
                int unitsize;
                while (read < offset) {
                    unitsize = (int) (((offset - read) >= buff) ? buff : (offset - read));
                    file.read(buffer, 0, unitsize);
                    
                    hashSum.update(buffer, 0, unitsize);
                    
                    read += unitsize;
                }
            }
            return (new HexBinaryAdapter()).marshal(hashSum.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
