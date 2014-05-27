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

package neembuu.release1.defaultImpl.linkhandler;

/**
 *
 * @author Shashank Tulsyan
 */
public class Utils {
    public static String normalize(String url){
        return getRidOfHash(getRidOfHttps(url));
    }
    
    public static String getRidOfHttps(String url){
        url = url.trim();
        if(url.contains("https://")){
            url = url.replace("https://", "http://");
        }
        return url; 
    }
    public static String getRidOfHash(String url){
        if(url.contains("#")){
            url = url.substring(0,url.indexOf('#'));
        }return url;
    }
    public static String getNameFromURL(String url){
        String fileName = url;
        try{
            if(fileName.contains("/")){
                fileName = url.substring( url.lastIndexOf('/')+1, url.length() );
            }if(fileName.contains("?")){
                fileName = url.substring(0, url.lastIndexOf('?'));
            }
        }catch(Exception a){
        }
        //fileName = jpfm.util.UniversallyValidFileName.makeUniversallyValidFileName(url);
        return fileName;
    }
}
