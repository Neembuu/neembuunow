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

package neembuu.release1.user_analytics;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import neembuu.release1.Application;
import neembuu.release1.Main;
import neembuu.release1.api.file.NeembuuFile;
import neembuu.release1.api.ui.actions.ReAddAction;
import neembuu.release1.httpclient.NHttpClient;
import neembuu.vfs.file.MinimumFileInfo;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Shashank Tulsyan
 */
public class UserAnalytics {
    
    public static ReAddAction.CallBack newReportHandler(){
        return new ReAddAction.CallBack() {
            @Override public void doneCreation(NeembuuFile neembuuFile) {
                report(neembuuFile.getMinimumFileInfo());
            }
        };
    }
    
    public static void report(final MinimumFileInfo minimumFileInfo){
        Thread t = new Thread("Report file "+minimumFileInfo){
            @Override public void run(){
                try{
                    reportImpl(minimumFileInfo);
                }catch(Exception a){
                    Main.getLOGGER().log(Level.FINE,"Failed Reporting Statistics",a);
                }
            }
        };
        t.setDaemon(true);
        t.start();
    }
    
    
    private static void reportImpl(final MinimumFileInfo file)throws Exception{
        Main.getLOGGER().info("Sending statistics..");
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("releasedate", Application.releaseTime()+""));
        formparams.add(new BasicNameValuePair("filename", file.getName()));
        formparams.add(new BasicNameValuePair("size", file.getFileSize()+""));
        formparams.add(new BasicNameValuePair("os", System.getProperty("os.name")));

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
        HttpPost httppost = new HttpPost("http://neembuu.sourceforge.net/insert.php");
        httppost.setEntity(entity);
        HttpParams params = new BasicHttpParams();
        params.setParameter(
                "http.useragent",
                "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB; rv:1.9.2) Gecko/20100115 Firefox/3.6");
        httppost.setParams(params);
        DefaultHttpClient httpclient = NHttpClient.getNewInstance();
        EntityUtils.consume(httpclient.execute(httppost).getEntity());
        Main.getLOGGER().info("done sending stats");
    }
    
    public static void main(String[] args) throws Exception{
        reportImpl(new MinimumFileInfo() {

            @Override
            public String getName() {
                return "testname"+Integer.toHexString((int)(Math.random()*10000));
            }

            @Override
            public long getFileSize() {
                return ((long)(Math.random()*10000));
            }
        });
    }
}
