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
package davidepastore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import neembuu.release1.Main;

/**
 * This class provides simple common methods to work with Strings.
 * @author davidepastore
 */
public class StringUtils{
    
    /**
     * Non-instantiable
     */
    private StringUtils() {
    }

    /**
     *
     * @param response
     * @param stringStart
     * @param stringEnd
     * @return Return the content of response string between stringStart and stringEnd.
     */
    public static String stringBetweenTwoStrings(String response, String stringStart, String stringEnd) {
        return stringBetweenTwoStrings(response, stringStart, stringEnd, false);
    }

    /**
     *
     * @param response
     * @param stringStart
     * @param stringEnd
     * @param lastindexof
     * @return Return the content of response string between stringStart and stringEnd. If lastindexof is set,
     * the string is evaluated from lastIndexOf stringStart.
     */
    public static String stringBetweenTwoStrings(String response, String stringStart, String stringEnd, boolean lastindexof) {
        if (!lastindexof) {
            response = stringStartingFromString(response, stringStart);
        } else {
            response = response.substring(response.lastIndexOf(stringStart));
            response = response.replaceFirst(stringStart, "");
        }
        response = stringUntilString(response, stringEnd);
        return response;
    }

    /**
     * Create the string until <i>stringEnd</i>.
     *
     * @param string
     * @param stringEnd
     * @return Return the string until <i>stringEnd</i>.
     */
    public static String stringUntilString(String string, String stringEnd) {
        return string.substring(0, string.indexOf(stringEnd));
    } 
    
    /**
     * Create the string starting from <i>stringStart</i>.
     * @param string
     * @param stringStart
     * @return Return the string starting from <i>stringStart</i>.
     */
    public static String stringStartingFromString(String string, String stringStart){
        string = string.substring(string.indexOf(stringStart));
        return string.replaceFirst(stringStart, "");
    }
    
    /**
     * Create the string starting from <i>stringStart</i>.
     * @param string
     * @param stringStart
     * @param regExp
     * @return Return the string starting from <i>stringStart</i>.
     */
    public static String stringStartingFromString(String string, String stringStart, boolean regExp){
        string = string.substring(string.indexOf(stringStart));
        if(regExp){
            return string.replaceFirst(stringStart, "");
        }
        else{
            return string.replace(stringStart, "");
        }
    }
    
    public static int countString(String string, String stringToCount){
        Pattern p = Pattern.compile(stringToCount);
        Matcher m = p.matcher(string);
        int count = 0;
        while (m.find()){
            count +=1;
        }
        return count;
    }
    
    /**
     * Calculate an uuid with size random numbers.
     * @param size the size of the uuid
     * @param number the number to multiply with Math.random()
     * @return The calculated uuid.
     */
    public static String uuid(int size, int number) {
        String uid = "";
        for (int i = 0; i < size; i++) {
            uid += (int) (Math.random() * number);
        }
        //NULogger.getLogger().info(uid);
        return uid;
    }
    
    public static String getSimpleXMLData(String src, String tag){
        String value = null;
        try {
            String start = "<"+tag+ ">";
            String end = "</"+ tag+ ">";

            src = src.substring(src.indexOf(start) + start.length());

            src = src.substring(0, src.indexOf(end));
            value = src;
        } catch (Exception any) {
            Main.getLOGGER().severe(any.toString());
        }
        return value;
    }
    
}
