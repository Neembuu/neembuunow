/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.vfs.connection.jdimpl;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
//import jd.plugins.DownloadLink;
import java.util.logging.Logger;
import neembuu.vfs.connection.NewConnectionParams;
import neembuu.vfs.connection.NewConnectionProvider;
import org.appwork.utils.logging.Log;


/**
 *
 * @author admin
 */
public final class JD_DownloadManager 
        implements 
            NewConnectionProvider{
    //final DownloadLink downloadLink; // package private
    final String url; // package private
    final boolean b; // package private
    final int c; // package private
    
    private static final Logger LOGGER = Logger.getLogger(JD_DownloadManager.class.getName());
    
    private final ConcurrentLinkedQueue<JDHTTPConnection> connection_list 
            = new ConcurrentLinkedQueue<JDHTTPConnection>();

    public JD_DownloadManager(String url) {
        this(url,true,0);
    }

    public JD_DownloadManager(
            //DownloadLink downloadLink,
            String url, boolean b, int c) {
        //this.downloadLink = downloadLink;
        this.url = url;
        this.b = b;
        this.c = c;
    }

    final String getURL() { // package private
        return url;
    }
    
    private void connectionsRequested() {
        String message = "++++++++++ConnectionsRequested+++++++++\n";
        for (JDHTTPConnection e : connection_list) {
            message = message + e.getConnectionParams().toString() + "\n";
        }
        message = message + "---------ConnectionsRequested--------\n";
        
        LOGGER.info(message);
    }

    @Override
    public final String getSourceDescription() {
        return "JD_DownloadManager{"+url+"}";
    }
    
    @Override
    public final void provideNewConnection(final NewConnectionParams connectionParams) {
        class StartNewJDBrowserConnectionThread extends Thread {

            StartNewJDBrowserConnectionThread() {
                //always name thread, otherwise it can be extremely difficult to debug
                super("StartNewJDBrowserConnectionThread{" + connectionParams + "}");
            }

            @Override
            public final void run() {
                try {
                    JDHTTPConnection c = new JDHTTPConnection(
                            JD_DownloadManager.this,
                            connectionParams);
                    connection_list.add(c);
                    connectionsRequested();
                    c.connectAndSupply();
                } catch (Exception e) {
                    Log.L.log(Level.INFO, "Problem in new connection ", e);
                }
            }
        }

        new StartNewJDBrowserConnectionThread().start();
    }
    
    @Override
    public final long estimateCreationTime(long offset) {
        return averageConnectionCreationTime();
    }

    private long averageConnectionCreationTime() {
        int i = 0;
        long totalTime = 0;
        for (JDHTTPConnection connection : connection_list) {
            if(connection.succeededInCreation()){
                totalTime += connection.timeTakenForCreation();
                i++;
            }
        }
        if (i == 0) {
            return 0;//creation time is unknown
        }
        return ((totalTime) / i);
    }
    
    long[]totalProgress = {0};
}
