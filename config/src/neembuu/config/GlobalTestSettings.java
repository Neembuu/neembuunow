/*
 *  Copyright (C) 2011 Shashank Tulsyan
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

package neembuu.config;

import javax.swing.ImageIcon;

/**
 * This is used internally and parameter are subject to change anytime.
 * Do not use this.
 * @author Shashank Tulsyan
 */
public final class GlobalTestSettings {
    private static final boolean DISABLE_ALL_LOGS = true; 
    private static final boolean STORE_PROFILEABLE_READ_REQUEST_PROPERTIES = true;
    
    public static final class ONION_EMOTIONS {        
        public static ImageIcon getQuestionImageIcon(String iconName){
            ImageIcon toRet = null;

            try{
                toRet = new ImageIcon(
                        GlobalTestSettings.class
                        .getResource("resources/"+iconName));
            }catch(Exception ignore){
                ignore.printStackTrace(System.err);
            }
            return toRet;
        }

    }
    
    
    public static final class ProxySettings {
        public final String userName, password;
        public final String host; 
        public final int port;
        public ProxySettings(String username, String password, String host, int port) {
            this.userName = username;
            this.password = password;
            this.host = host;
            this.port = port;
        }

        @Override
        public final String toString() {
            return "ProxySettings{host="+host+" port="+port+" username="+userName+" password="+password+"}";
        }
    }
    
    private static ProxySettings ps = null;
    
    public static void setGlobalProxySettings(ProxySettings ps_){
        if(ps!=null){
            throw new IllegalStateException("Proxy already set to "+ps);
        }
        ps=ps_;
    }
    
    public static ProxySettings getGlobalProxySettings(){
        return ps;
    }
    
    public static final boolean IS_RUNNING_FROM_JAR = 
            GlobalTestSettings.class.getProtectionDomain().getCodeSource().getLocation().toString().endsWith(".jar");
    public static boolean getValue(String property){
        if(property.equals("DEBUG_REGION_REGISTRATION")){
            if(DISABLE_ALL_LOGS)return false;
            return true;
        }
        if(property.equals("DEBUG_SEEKABLE_CHANNEL")){
            if(DISABLE_ALL_LOGS)return false;
            return false;
        }if(property.equals("DEBUG_READ_QUEUE_MANAGER")){
            if(DISABLE_ALL_LOGS)return false;
            return true;
        }if(property.equals("DEBUG_SPLITTED_REQUESTS")){
            if(DISABLE_ALL_LOGS)return false;
            return true;
        }if(property.equals("THROTTLING_ENABLED")){
            return true;
        }if(property.equals("STORE_PROFILEABLE_READ_REQUEST_PROPERTIES")){
            return STORE_PROFILEABLE_READ_REQUEST_PROPERTIES;
        }
        throw new AssertionError(); 
        //return false;
    }
}
