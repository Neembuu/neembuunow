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

/**
 *
 * @author Shashank Tulsyan
 */
public final class MessageFromException {
    public static String make(Exception a){
        String exceptionString = a.getClass().getSimpleName();
        if(a instanceof CombinedException){
            exceptionString = ((CombinedException)a).mostCommonCause().getSimpleName();
        }
        final String E = "Exception";
        if(exceptionString.length()<E.length() ){
            return exceptionString + " - "+ a.getMessage();
        }
        exceptionString = exceptionString.substring(0,exceptionString.lastIndexOf(E));
        
        String ex = ""; int l =0; int cnt = 0;
        for (int i = 0; i < exceptionString.length(); i++) {
            if(i==exceptionString.length()-1){
                cnt ++;
                ex = ex+exceptionString.substring(l,i+1)+" "; l=i+1;
            }
            if(Character.isUpperCase(exceptionString.charAt(i))){
                if(l>=i)continue;
                cnt++;
                ex = ex+exceptionString.substring(l,i)+" "; l=i;
            }
        }
        String reason;
        if(cnt<=0){
            reason = "Unknown exception "+ a.getMessage();
        }
        if(cnt==1){
            reason = ex.toString() + " exception" +"- "+ a.getMessage();
        }else{
            reason = ex.toString() +"- "+ a.getMessage();
        }
        return reason;
    }
    
    public static void main(String[] args) {
        System.out.println(make(new java.net.UnknownHostException("neembuu.com")));
        System.out.println(make(new Exception("neembuu.com")));
        System.out.println(make(new java.net.ConnectException("neembuu.com")));
        
        System.out.println(make(new java.net.NoRouteToHostException("neembuu.com")));
    }
}
