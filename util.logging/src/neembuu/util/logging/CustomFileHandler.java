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
            System.err.println("CustomFileHandler line:73 closing ->"+path);
            fc.close();
        }catch(IOException ioe){
            ioe.printStackTrace(System.err);
        }
    }
    
}
