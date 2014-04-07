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

package neembuu.diskmanager;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author Shashank Tulsyan
 */
public class NioUtils {
        public static void deleteDirectory(Path dirToDelete)throws IOException{
        if(!Files.isDirectory(dirToDelete)) throw new IllegalArgumentException("the path is not a directory");
        Files.walkFileTree(dirToDelete, new SimpleFileVisitor<Path>() {
            @Override public FileVisitResult visitFile(Path file,
                    BasicFileAttributes attrs) throws IOException {
                System.out.println("Deleting file: " + file);
                Files.delete(file);
                return CONTINUE;
            }

            @Override public FileVisitResult postVisitDirectory(Path dir,
                    IOException exc) throws IOException {
                System.out.println("Deleting dir: " + dir);
                if (exc == null) {
                    try{
                        Files.delete(dir);
                    }catch(Exception a){
                        listDirectory(dir); 
                    }
                    return CONTINUE;
                } else {
                    throw exc;
                }
            }
            
            private int listDirectory(Path dir){
                int cnt = 0;try{
                System.out.println("+++dir+++");
                try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir)) {
                    for (Path path : ds) {
                        System.out.println(path); cnt++;
                    }
                }
                System.out.println("---dir---");}catch(Exception a){}
                return cnt;
            }
        });
    }
}
