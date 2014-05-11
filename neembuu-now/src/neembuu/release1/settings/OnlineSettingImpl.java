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

package neembuu.release1.settings;

import java.util.logging.Level;
import neembuu.release1.Main;
import davidepastore.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Shashank Tulsyan
 */
public class OnlineSettingImpl {
    
    public static String getRaw(String ... p){
        //Get the version.xml and read the version value.
        HttpParams params = new BasicHttpParams();
        params.setParameter(
                "http.useragent",
                "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB; rv:1.9.2) Gecko/20100115 Firefox/3.6");
        DefaultHttpClient httpclient = new DefaultHttpClient(params);
        String pth = "";
        for (String p_ele : p) {
            pth = pth+"/"+p_ele;
        }
        HttpGet httpget = new HttpGet("http://neembuu.sourceforge.net"+pth);
        Main.getLOGGER().log(Level.INFO, "Getting online setting ...{0}", p);
        try {
            HttpResponse response = httpclient.execute(httpget);
            String respxml = EntityUtils.toString(response.getEntity());
            return respxml;
        } catch (Exception ex) {
            Main.getLOGGER().log(Level.INFO, "Exception while getting resource "+p, ex);
        }

        return null;
    }
    
    public static String get(String key, String ... p){
        String respxml = getRaw(p);
        return StringUtils.getSimpleXMLData(respxml,key);
    }
    
}
