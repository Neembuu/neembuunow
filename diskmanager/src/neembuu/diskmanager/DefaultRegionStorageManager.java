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
package neembuu.diskmanager;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.logging.Handler;
import java.util.logging.Logger;
import neembuu.util.logging.LoggerUtil;

/**
 *
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
final class DefaultRegionStorageManager implements RegionStorageManager{

    private final DefaultFileStorageManager fsm;
    private final long startingOffset;
    private volatile long amountWritten;//non inclusive ending
    private final String regionStore;
    
    private final FileChannel fileChannel;
    
    private final Logger downloadThreadLogger,readHandlerThreadLogger;

    public DefaultRegionStorageManager(DefaultFileStorageManager fsm, long startingOffset) throws IOException{
        this(fsm, startingOffset, 0,null);
    }
        
    public DefaultRegionStorageManager(
            DefaultFileStorageManager fsm, 
            long startingOffset,
            long amountWritten,
            String storageFile) throws IOException{
        this.fsm = fsm;
        this.startingOffset = startingOffset;
        this.amountWritten = amountWritten;
        
        if(storageFile==null){
            String store = fsm.store + File.separator + Math.random()+"("+startingOffset+")_0x"+Long.toHexString(startingOffset)+".partial";
            regionStore = store;
            //((DefaultDiskManager)fsm.getDiskManager()).logger.log(Level.WARNING,"creating "+store,new Throwable());
        }else{
            regionStore = storageFile;
            //((DefaultDiskManager)fsm.getDiskManager()).logger.log(Level.WARNING,"resumed "+regionStore,new Throwable());
        }
        
        
        fileChannel = new RandomAccessFile(regionStore, "rw").getChannel();
        
        downloadThreadLogger = LoggerUtil.getLightWeightHtmlLogger("DownloadThread("+startingOffset+")",fsm.store,fsm.getDiskManager().getDiskManagerParams().getMaxDownloadThreadLogSize());
        readHandlerThreadLogger = LoggerUtil.getLightWeightHtmlLogger("ReadHandlerThread("+startingOffset+")",fsm.store,fsm.getDiskManager().getDiskManagerParams().getMaxReadHandlerThreadLogSize());
    }

    @Override
    public Logger getDownloadThreadLogger() {
        return downloadThreadLogger;
    }

    @Override
    public Logger getReadHandlerThreadLogger() {
        return readHandlerThreadLogger;
    }
    
    @Override
    public void close() throws IOException {
        closeLogger(downloadThreadLogger);
        closeLogger(readHandlerThreadLogger);
        fileChannel.close();
    }
    
    static void closeLogger(Logger l){
        if(l.getHandlers()==null)return;
        if(l.getHandlers().length==0)return;
        for(Handler h : l.getHandlers()){
            h.close();
        }
    }

    @Override
    public boolean isOpen() {
        return fileChannel.isOpen();
    }
    
    @Override
    public FileStorageManager getFileStorageManager() {
        return fsm;
    }

    @Override
    public final int write(ByteBuffer src) throws IOException {
        int written = fileChannel.write(src, amountWritten);
        //fileChannel.force(false);
        amountWritten += written;
        return written;
    }

    @Override
    public final int write(ByteBuffer src,long absoulteFileOffset)throws IOException {
        int written = fileChannel.write(src, absoulteFileOffset-startingOffset);
        //fileChannel.force(false);
        amountWritten += written;
        return written;
    }
    
    @Override
    public final int read(ByteBuffer src,long absoulteFileOffset) throws IOException{
        long requestEnd = absoulteFileOffset+src.capacity()-1;
        if(requestEnd > ending() ){
            throw new IllegalStateException("Data available till "+ending()+" resuquesting till "+requestEnd);
        }
        int amtRead =  fileChannel.read(src, absoulteFileOffset-startingOffset);
        /*System.err.println("DefaultRegionStorageManager:104:offset"+absoulteFileOffset+" "
                +generatePeekString(src));*/
        return amtRead;
    }

    @Override
    public void transferToReOpenIfRequired(WritableByteChannel wbc)throws IOException {
        FileChannel tempFileChannel;
        if(fileChannel.isOpen()){
            tempFileChannel = fileChannel;
        }else {
            tempFileChannel = new RandomAccessFile(regionStore, "r").getChannel();
        }
        tempFileChannel.transferTo(0, amountWritten, wbc);
        tempFileChannel.close();
    }
    
    public static String generatePeekString(ByteBuffer byteBuffer){
        int NUMBER_OF_VALUES = 30;
        StringBuilder sb = new StringBuilder(30*2+10);
        sb.append('{');
        for (int i = 0; i < NUMBER_OF_VALUES && i < byteBuffer.capacity(); i++) {
            sb.append(Integer.toHexString(byteBuffer.get(i)));
            sb.append(',');
        }
        sb.append('}');
        sb.append('{');
        byte[]b=new byte[NUMBER_OF_VALUES];
        byteBuffer.position(0);
        byteBuffer.get(b, 0,
                Math.min(NUMBER_OF_VALUES, byteBuffer.capacity())
            );
        for (int i = 0; i < b.length; i++) {
            if(!Character.isLetterOrDigit((char)b[i]))
                b[i]=32;
        }
        sb.append(new String(b).replace( '\n', ' '));
        sb.append('}');
        return sb.toString();
    }

    @Override
    public long starting() {
        return startingOffset;
    }

    @Override
    public long ending() {
        if(amountWritten==0)return startingOffset;//faking 1 byte
        return startingOffset+amountWritten-1;
    }
    
    @Override
    public long endingByFileSize()throws IOException{
        long size = fileChannel.size();
        return startingOffset+size-1;
    }

    @Override
    public final RegionStorageManager getProperty() {
        return this;
    }

    public final String getStoreLocation() {
        return regionStore;
    }

    @Override
    public String toString() {
        return DefaultRegionStorageManager.class.getName()+"{"+startingOffset+"}"+regionStore;
    }
}
