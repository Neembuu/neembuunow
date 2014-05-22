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
package neembuu.release1.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Shashank Tulsyan
 */
public final class FlashGotTemplate {

    private final String URL;
    private final String COMMENT;
    private final String REFERER;
    private final String COOKIE;
    private final String FOLDER;
    private final String FNAME;
    private final String POST;
    private final String ULIST;
    private final String UFILE;
    private final String CFILE;
    private final String USERPASS;
    private final String UA;

    public FlashGotTemplate(String[]args) {
        if(args.length<12)throw new IllegalArgumentException("length should be 12, not "+args.length);
        
        int i = 0;
        URL=args[i];i++;
        COMMENT=args[i];i++;
        REFERER=args[i];i++;
        COOKIE=args[i];i++;
        FOLDER=args[i];i++;
        FNAME=args[i];i++;
        POST=args[i];i++;
        ULIST=args[i];i++;
        UFILE=args[i];i++;
        CFILE=args[i];i++;
        USERPASS=args[i];i++;
        UA=args[i];i++;
    }
    
    private static JSONObject makeObject(Path file)throws IOException,JSONException{
        byte[]b = Files.readAllBytes(file);
        String commandString = new String(b);
        return new JSONObject(commandString);
    }
    
    public FlashGotTemplate(Path file)throws IOException,JSONException{
        this(makeObject(file));
    }
    
    public FlashGotTemplate(JSONObject object)throws JSONException{        
        URL=object.get("URL").toString();
        COMMENT=object.get("COMMENT").toString();
        REFERER=object.get("REFERER").toString();
        COOKIE=object.get("COOKIE").toString();
        FOLDER=object.get("FOLDER").toString();
        FNAME=object.get("FNAME").toString();
        POST=object.get("POST").toString();
        ULIST=object.get("ULIST").toString();
        UFILE=object.get("UFILE").toString();
        CFILE=object.get("CFILE").toString();
        USERPASS=object.get("USERPASS").toString();
        UA=object.get("UA").toString();
    }

    public String getURL() {
        return URL;
    }

    public String getCOMMENT() {
        return COMMENT;
    }

    public String getREFERER() {
        return REFERER;
    }

    public String getCOOKIE() {
        return COOKIE;
    }

    public String getFOLDER() {
        return FOLDER;
    }

    public String getFNAME() {
        return FNAME;
    }

    public String getPOST() {
        return POST;
    }

    public String getULIST() {
        return ULIST;
    }

    public String getUFILE() {
        return UFILE;
    }

    public String getCFILE() {
        return CFILE;
    }

    public String getUSERPASS() {
        return USERPASS;
    }

    public String getUA() {
        return UA;
    }

    
}
