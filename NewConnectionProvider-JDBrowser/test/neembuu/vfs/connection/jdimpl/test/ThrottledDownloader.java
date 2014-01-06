/*
 * Copyright (c) 2011 Shashank Tulsyan <shashaanktulsyan@gmail.com>. 
 * 
 * This is part of free software: you can redistribute it and/or modify
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
 * along with this.  If not, see <http ://www.gnu.org/licenses/>.
 */
package neembuu.vfs.connection.jdimpl.test;

import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import jd.http.HTTPConnectionFactory;
import org.appwork.utils.net.httpconnection.HTTPConnection;
import org.appwork.utils.net.httpconnection.HTTPProxy;

/**
 *
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
public final class ThrottledDownloader{
    HTTPConnection httpConnection;
    
    public void con()throws Exception{
        httpConnection = HTTPConnectionFactory.createHTTPConnection(
                new URL(
                        "http://localhost:8080/LocalFileServer-war/servlet/FileServer?"
                            + "totalFileSpeedLimit=100&"
                            + "mode=constantAverageSpeed"
                            //+ "mode=constantFlow"
                            + "&newConnectionTimemillisec=500&file=test120k.rmvb"
                        ),
                HTTPProxy.NONE
            );
        List<String> a = new LinkedList<String>();
        httpConnection.connect();
        InputStream is = httpConnection.getInputStream();   
        int read = 0;
        byte[]b=new byte[1024];
        ByteBuffer bb = ByteBuffer.wrap(b);
        while(read!=-1){
            
        }
    }

}
