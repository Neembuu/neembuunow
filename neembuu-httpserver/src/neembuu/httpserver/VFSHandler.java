/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.httpserver;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Locale;
import jpfm.FileAttributesProvider;
import jpfm.FileId;
import jpfm.FileType;
import jpfm.fs.SimpleReadOnlyFileSystem;
import jpfm.volume.AbstractFile;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Shashank Tulsyan
 */
final class VFSHandler implements HttpRequestHandler {

    private final SimpleReadOnlyFileSystem fs;

    public VFSHandler(SimpleReadOnlyFileSystem fs) {
        this.fs = fs;
    }

    @Override
    public void handle(
            final HttpRequest request,
            final HttpResponse response,
            final HttpContext context) throws HttpException, IOException {

        String method = request.getRequestLine().getMethod().toUpperCase(Locale.ENGLISH);
        if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("POST")) {
            throw new MethodNotSupportedException(method + " method not supported");
        }
        String target = request.getRequestLine().getUri();

        if (request instanceof HttpEntityEnclosingRequest) {
            HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
            byte[] entityContent = EntityUtils.toByteArray(entity);
            System.out.println("Incoming entity content (bytes): " + entityContent.length);
        }

        String filePath = URLDecoder.decode(target, "UTF-8");
        FileAttributesProvider fap = fs.open(filePath.split("/"));
        if(fap==null){
            response.setStatusCode(HttpStatus.SC_NOT_FOUND);
            StringEntity entity = new StringEntity(
                    "<html><body><h1>File" + filePath
                    + " not found</h1></body></html>",
                    ContentType.create("text/html", "UTF-8"));
            response.setEntity(entity);
            System.out.println("File " + filePath + " not found");

        } else if (fap.getFileType()!=FileType.FILE || !(fap instanceof AbstractFile) ) {

            response.setStatusCode(HttpStatus.SC_FORBIDDEN);
            StringEntity entity = new StringEntity(
                    "<html><body><h1>Access denied</h1></body></html>",
                    ContentType.create("text/html", "UTF-8"));
            response.setEntity(entity);
            System.out.println("Cannot read file " + filePath+ " fap="+fap);

        } else {
            long offset = Utils.standardOffsetResponse(request, response, fap.getFileSize());
            response.setStatusCode(HttpStatus.SC_OK);
            VFileEntity body = new VFileEntity((AbstractFile)fap,offset);
            response.setEntity(body);
            System.out.println("Serving file " + filePath);
        }
    }

}
