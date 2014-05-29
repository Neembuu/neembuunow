package neembuu.release1.externalImpl.linkhandler;

/*
 *  Copyright (C) 2014 Shashank Tulsyan
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */



import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import neembuu.release1.defaultImpl.external.ELHEntry;
import org.json.JSONObject;

/**
 *
 * @author Shashank Tulsyan
 */
public class Generator {
    public static void main(String[] args) throws Exception{ 
        Path destDir = Paths.get(Generator.class.getResource("/").toURI());
        destDir = destDir.getParent();
        destDir = destDir.getParent();
        destDir = destDir.getParent();
        destDir = destDir.getParent();
        destDir = destDir.resolve("export_external_plugins");
        Files.createDirectories(destDir);
        
        GenerateELHEntry 
                entry = new GenerateELHEntry(
                        DailymotionLinkHandlerProvider.class,
                        destDir
                        //YoutubeLinkHandlerProvider.class
                );
        
        
        ELHEntry elhe = entry.generate();
        JSONObject jsono = new JSONObject(elhe);
        System.out.println(jsono);
    }
}
