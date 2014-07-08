/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apache.http.examples;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import org.apache.http.entity.AbstractHttpEntity;

/**
 *
 * @author Shashank Tulsyan
 */
public class MonitoredFileEntity extends AbstractHttpEntity implements Cloneable {

    protected final File file;
    private final long fileOffset;

    public MonitoredFileEntity(final File file, final String contentType,final long offset) {
        this.fileOffset = offset;
        if (file == null) {
            throw new IllegalArgumentException("File may not be null");
        }
        this.file = file;
        setContentType(contentType);
    }

    public boolean isRepeatable() {
        return true;
    }

    public long getContentLength() {
        return this.file.length();
    }

    public InputStream getContent() throws IOException {
        FileInputStream instream = new FileInputStream(this.file);
        instream.skip(fileOffset);
        return instream;
    }

    public void writeTo(final OutputStream outstream) throws IOException {
        if (outstream == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        //FileInputStream instream = new FileInputStream(this.file);
        
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        raf.seek(fileOffset);
        
        long offset = fileOffset;
        try {
            byte[] tmp = new byte[4096];
            int l;
            while ((l = raf.read(tmp)) != -1) {
                offset+=l; reportOffset(offset);
                outstream.write(tmp, 0, l);
            }
            outstream.flush();
        } finally {
            raf.close();
            //instream.close();
        }
    }
    
    private void reportOffset(long offset){
        System.out.println(offset);
    }

    /**
     * Tells that this entity is not streaming.
     *
     * @return <code>false</code>
     */
    public boolean isStreaming() {
        return false;
    }

    public Object clone() throws CloneNotSupportedException {
        // File instance is considered immutable
        // No need to make a copy of it
        return super.clone();
    }

} 
