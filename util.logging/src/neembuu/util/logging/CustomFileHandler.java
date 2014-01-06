/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.util.logging;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *
 * @author Shashank Tulsyan
 */
final class CustomFileHandler extends Handler{
    
    private final String path;
    private final long limit;
    private final boolean append;
    
    private final FileChannel fc;

    public CustomFileHandler(String path, long limit, int count, boolean append) throws IOException{
        this.path = path;
        this.limit = limit;
        this.append = append;
        
        fc = new RandomAccessFile(path, "rwd").getChannel();
        fc.position(fc.size());
    }
    
    @Override
    public synchronized void publish(LogRecord record) {
        String message = getFormatter().format(record);
        ByteBuffer bb = ByteBuffer.wrap(message.getBytes());
        try{
            if(fc.position()+message.length() > limit){
                fc.position(0);
            }
            if(fc.position()==0){
                writeHead();
            }
            fc.write(bb);
        }catch(Exception a){
            throw new LogRecordContainingException(record);
        }
    }

    private synchronized void writeHead()throws IOException{
        String head = getFormatter().getHead(this);
        ByteBuffer bb = ByteBuffer.wrap(head.getBytes());
        fc.write(bb);
    }
    
    @Override
    public synchronized void flush() {
        try{
            fc.force(true);
        }catch(IOException ioe){
            ioe.printStackTrace(System.err);
        }
    }

    @Override
    public synchronized void close() throws SecurityException {
        try{
            fc.close();
        }catch(IOException ioe){
            ioe.printStackTrace(System.err);
        }
    }
    
}
