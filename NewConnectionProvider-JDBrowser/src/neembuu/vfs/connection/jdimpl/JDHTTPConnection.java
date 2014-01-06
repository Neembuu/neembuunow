package neembuu.vfs.connection.jdimpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import jd.http.Browser;
import jd.http.Request;
import jd.http.URLConnectionAdapter;
import neembuu.vfs.connection.AbstractConnection;
import neembuu.vfs.connection.NewConnectionParams;

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
        try{
            urlca = br.openRequestConnection(request);
        }catch(Exception ae){
            cp.getTransientConnectionListener().failed(ae,cp);
            return;
        }
        
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
            while(read!=-1){
                write(b,0,read);
                read=is.read(b);
            }
        }catch(Exception e){
            downloadThreadLogger.log(Level.SEVERE, "Connection terminated", e);
        }
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
