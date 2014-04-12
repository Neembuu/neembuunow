/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.versioning;

import java.util.Date;
import java.util.logging.Level;
import neembuu.release1.Application;
import neembuu.release1.Main;
import neembuu.release1.api.ui.AddLinkUI;
import neembuu.release1.api.ui.MainComponent;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/** This thread checks for new updates at every launch.
 *
 * @author vigneshwaran
 */
public class CheckUpdate extends Thread {

    private final MainComponent mc;
    private final AddLinkUI alui;

    public CheckUpdate(MainComponent mc, AddLinkUI alui) {
        this.mc = mc; this.alui = alui;
    }
    
    //variables to store current and available version
    static long availablever, currentver;
    
    static Date availableDate(){
        return d(availablever);
    }
    
    static Date currentDate(){
        return d(currentver);
    }

    /**
     * 
     * @return whether the Neembuu is uptodate or not. If not, launch Update Notification window..
     */
    public boolean isCurrentVersion() {
        //Get the version.xml and read the version value.
        HttpParams params = new BasicHttpParams();
        params.setParameter(
                "http.useragent",
                "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB; rv:1.9.2) Gecko/20100115 Firefox/3.6");
        DefaultHttpClient httpclient = new DefaultHttpClient(params);
        HttpGet httpget = new HttpGet("http://neembuu.sourceforge.net/version.xml");
        Main.getLOGGER().info("Checking for new version...");
        try {
            HttpResponse response = httpclient.execute(httpget);
            String respxml = EntityUtils.toString(response.getEntity());
            availablever = getReleaseDateFromXML(respxml);
            tryObtainingSampleLink(respxml);
            
            Main.getLOGGER().log(Level.INFO, "Available version: {0}", d(availablever));
            currentver = Application.releaseTime();

            Main.getLOGGER().log(Level.INFO, "Current version: {0}", d(currentver));

            //Compare both
            if (availablever > currentver) {
                return false;
            }




        } catch (Exception ex) {
            Main.getLOGGER().log(Level.INFO, "Exception while checking update\n{0}", ex);
        }

        return true;
    }

    private void tryObtainingSampleLink(String respxml){
        String sampleLink = getSampleLinkFromXML(respxml);
        System.out.println("obtained sample link = "+sampleLink);
        alui.setLinksText(sampleLink);
        /*if(alui.getLinksText()==null
                || alui.getLinksText().length()==0){
            alui.setLinksText(link);
        }*/
    }
    
    /**
     * 
     * @param str
     * @return the value between <version> and </version> tags from the specified string.
     */
    public static long getReleaseDateFromXML(String str) {
        long ver = 0;
        try {
            String start = "<releasedate>";
            String end = "</releasedate>";

            str = str.substring(str.indexOf(start) + start.length());

            str = str.substring(0, str.indexOf(end));
            ver = Long.parseLong(str);
        } catch (Exception any) {
            Main.getLOGGER().severe(any.toString());
        }
        return ver;
    }

    public static String getSampleLinkFromXML(String str) {
        String sampleLink = "";
        try {
            String start = "<samplelink>";
            String end = "</samplelink>";

            str = str.substring(str.indexOf(start) + start.length());

            str = str.substring(0, str.indexOf(end));
            sampleLink = str;
        } catch (Exception any) {
            Main.getLOGGER().severe(any.toString());
        }
        return sampleLink;
    }
    
    
    @Override
    public void run() {
        if (!(isCurrentVersion())) {
            Main.getLOGGER().info("New version found..");
            new NotifyUpdate(mc).setVisible(true);
        }
    }
    
    private static Date d(long t){
        return new Date(t);
    }
    
}
