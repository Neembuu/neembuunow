/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import jpfm.operations.readwrite.SimpleReadRequest;
import jpfm.volume.AbstractFile;
import org.apache.http.entity.AbstractHttpEntity;

/**
 *
 * @author Shashank Tulsyan
 */
public class VFileEntity extends AbstractHttpEntity{
    private final AbstractFile af;
    private final long startingOffset;

    public VFileEntity(AbstractFile af,long startingOffset) {
        this.startingOffset = startingOffset;
        setContentType("application/octet-stream");
        this.af = af;
    }

    @Override
    public boolean isRepeatable() {
        return true;
    }

    @Override
    public long getContentLength() {
        return af.getFileSize();
    }

    @Override
    public InputStream getContent() throws IOException, IllegalStateException {
        return new InputStream() {
            long pos = startingOffset;
            @Override public int read() throws IOException {
                throw new IllegalStateException("This is not used.");
            }

            @Override
            public int read(byte[] b) throws IOException {
                return read(b,0,b.length); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                ByteBuffer bb = ByteBuffer.wrap(b,0,len);
                BlockingReadRequest brr = new BlockingReadRequest(bb, pos);
                try{
                    af.read(new SimpleReadRequest(bb, pos));
                }catch(Exception a){
                    IOException ioe = new IOException();
                    ioe.addSuppressed(a);
                }
                int read = brr.read();
                pos+=read;
                return read;
            }
            
            
        };
    }

    @Override
    public void writeTo(OutputStream outstream) throws IOException {
        if (outstream == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        
        InputStream instream = getContent();
        try {
            byte[] tmp = new byte[4096];
            int l;
            while ((l = instream.read(tmp)) != -1) {
                outstream.write(tmp, 0, l);
            }
            outstream.flush();
        } finally {
            instream.close();
        }
    }

    @Override
    public boolean isStreaming() {
        return true;
    }
  
}
