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
package neembuu.vfs.connection.jdimpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import jd.http.Browser;
import jd.http.Request;
import jd.http.URLConnectionAdapter;
import neembuu.vfs.connection.AbstractConnection;
import neembuu.vfs.connection.NewConnectionParams;
import neembuu.vfs.connection.checks.ContentSampleListener;

/**
 *
 * @author Shashank Tulsyan
 */
public final class JDHTTPConnection extends AbstractConnection{
    
    private final JD_DownloadManager jddm;
    //private HTTPConnection httpConnection;
    private URLConnectionAdapter urlca;
    
    public JDHTTPConnection(JD_DownloadManager jddm, NewConnectionParams cp) {
        super(cp);
        this.jddm = jddm;
    }
    

    @Override
    protected final void abortImpl() {
        urlca.disconnect();
    }

    /*@Override
    protected final void connectAndSupplyImpl() throws IOException{
        httpConnection = HTTPConnectionFactory.createHTTPConnection(
                new URL(jddm.getURL()),
                new Browser().getProxy()
            );
        
        List<String> a = new LinkedList<String>();
        a.add("bytes=" + getAdjustedOffset() + "-");
        //httpConnection.getHeaderFields().put("Range",a);
        httpConnection.getRequestProperties().put("Range",a.get(0));
                
        httpConnection.connect();
        
        if(!httpConnection.isOK()){
            cp.getTransientConnectionListener().failed(
                    new IllegalStateException("ResponseCode="+httpConnection.getResponseCode()),cp
                );
            return;
        }
    }*/
    
    @Override
    protected final void connectAndSupplyImpl() throws IOException{
        Browser br = new Browser();
        br.forceDebug(true);
        br.setFollowRedirects(true);
        br.setDebug(true);
        Request request = br.createGetRequest(jddm.getURL());
       
        request.getHeaders().put("Range","bytes=" + getAdjustedOffset() + "-");
        printRequest(request);
        try{
            urlca = br.openRequestConnection(request);
        }catch(Exception ae){
            cp.getTransientConnectionListener().failed(ae,cp);
            return;
        }
        printResponse(urlca);
        InputStream is = urlca.getInputStream();
        int firstByte = is.read();
        if(firstByte!=-1){
            // when the first byte is send to {@link neembuu.vfs.connection.AbstractConnection#write }
            // cp.getTransientConnectionListener().success is called
        }else {
            cp.getTransientConnectionListener().failed(new IllegalStateException("EOF"),cp);
            return;
        }
        
        try{
            int read = 0;
            byte[]b=new byte[1024];
            b[0]=(byte)firstByte;
            read = is.read(b,1,b.length-1);
            if(read!=-1)read+=1;
            
            int i = 1;
            
            reportCSL(b);
            
            while(read!=-1){
                write(b,0,read);
                while(i<10){
                    i++;
                    b=new byte[1024*i];
                }
                read=is.read(b);
            }
        }catch(Exception e){
            downloadThreadLogger.log(Level.SEVERE, "Connection terminated", e);
        }
    }
    
    private void printRequest(Request request){
        String headers = "Request Headers{";
        for (int i = 0; i < request.getHeaders().size(); i++) {
            String key = request.getHeaders().getKey(i);
            String value = request.getHeaders().getValue(i);
            headers+="\n\t"+key+":"+value;
        }headers+="\n}";

        cp.getDownloadThreadLogger().log(Level.INFO, headers);
    }
    
    private void reportCSL(byte[]b){
        ContentSampleListener csl = getContentSampleListener();
        if(csl!=null){
            csl.receiveContentSample(b,cp.getOffset());
        }
    }
    
    private void printResponse(URLConnectionAdapter urlca){
        String headers = "Response Headers{";
        for(Map.Entry s : urlca.getHeaderFields().entrySet()){
            headers+="\n\t"+s.getKey()+":"+s.getValue();
        }headers+="\n}";
        
        cp.getDownloadThreadLogger().log(Level.INFO, headers);
    }
    /*
     * Browser br = new Browser();
	coalado1	br.forceDebug(true);
	coalado1	br.setCookie("jdownloader.org", "bla", "Blub");
	coalado1	br.getHeaders().put("Accept-Language", "en");
	coalado1	try {
	coalado1	URLConnectionAdapter connection = br.openGetConnection("http://update3.jdownloader.org/speed.avi");
	coalado1	System.out.println(connection);
	coalado1	} catch (IOException e) {
	coalado1	Log.exception(Level.WARNING, e);
	coalado1	}
     */
    
}
