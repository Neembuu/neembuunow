/*
 * Copyright (C) 2011 Shashank Tulsyan
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

package neembuu.vfs.connection.sampleImpl;

import java.io.IOException;
import java.util.logging.Level;
import neembuu.vfs.connection.AbstractConnection;
import neembuu.vfs.connection.NewConnectionParams;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.Header;

/**
 *
 * @author Shashank Tulsyan
 */
final class NewConnection extends AbstractConnection {
    private final DownloadManager downloadManager;

    public NewConnection(DownloadManager downloadManager, final NewConnectionParams cp) {
        super(cp);
        this.downloadManager = downloadManager;
    }
    
    private HttpGet request = null; 
    
    @Override
    protected final void connectAndSupplyImpl() throws  IOException { // package private
        HttpResponse response = null;
        request = new HttpGet(downloadManager.getDownloadLink());

        if(getAdjustedOffset()!=0){
            request.addHeader(new BasicHeader("Range", "bytes=" + getAdjustedOffset() + "-"));
        }
        
        DefaultHttpClient myClient = new DefaultHttpClient();
        boolean unsuccessful = true; int times = 0;
        
        downloadThreadLogger.log(Level.SEVERE, "executing="+cp.getOffset()+" adjustedto="+getAdjustedOffset());
        while(unsuccessful){
            try{
                response = //file.httpClient
                    myClient
                    .execute(request/*,context*/);
                unsuccessful = false;
            }catch(java.net.ConnectException ce){
                downloadThreadLogger.log(Level.INFO, "trying again "+times, ce);
                try{
                    Thread.sleep(1000);
                }catch(Exception c){

                }times++;
                cp.getTransientConnectionListener().reportNumberOfRetriesMadeSoFar(times);
                if(times >= 10){
                    cp.getTransientConnectionListener().failed(ce,cp);
                    return;
                }
            }
        }
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_PARTIAL_CONTENT){
            //LOGGER.log(Level.INFO, "Could not make a new connection at the point requested {0}", response.getStatusLine().getStatusCode());
        }
        Header contentLengthHeader =null;
        try{ 
            contentLengthHeader = response.getHeaders("Content-length")[0];
            //request.getResponseHeader("content-length");
        }catch(ArrayIndexOutOfBoundsException aioobe){
        }
        if (contentLengthHeader != null )
            downloadThreadLogger.log(Level.INFO, "remoteFileSize = {0}", Long.parseLong(contentLengthHeader.getValue())) ;
        else {
            long length = response.getEntity().getContentLength();
            if(length<0){
                throw new IllegalStateException("Length is less than zero");
            }
            downloadThreadLogger.info("content length "+length);
        }

        try{
            response.getEntity().writeTo(this);
        }catch(Exception a){
            downloadThreadLogger.log(Level.INFO, cp.toString());
            downloadThreadLogger.log(Level.INFO, "Connection terminated", a);
        }
    }
    
    @Override
    public void abortImpl() {
        request.abort();
    }
        
}
