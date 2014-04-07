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
package neembuu.diskmanager.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Logger;
import neembuu.diskmanager.DiskManagerParams;
import neembuu.diskmanager.LoggerCreateSPI;
import neembuu.diskmanager.RegionStorageManager;
import neembuu.diskmanager.impl.channel.ChannelList;

/**
 *
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
final class DefaultRegionStorageManager implements RegionStorageManager{

    private final DefaultFileStorageManager fsm;
    private final long startingOffset;
    private volatile long amountWritten;//non inclusive ending
    private final String regionStore;
    
    private final long estimatedSizeOfCompleteFile;
    
    private final FileChannel fileChannel;
    private final DiskManagerParams dmp;
    
    private final ChannelList channels = new ChannelList();

    public DefaultRegionStorageManager(DiskManagerParams dmp,DefaultFileStorageManager fsm, long startingOffset,long fileSize) throws IOException{
        this(dmp,fsm,startingOffset, 0,fileSize,null);
    }
        
    public DefaultRegionStorageManager(DiskManagerParams dmp,
            DefaultFileStorageManager fsm, long startingOffset,long amountWritten,
            long estimatedSizeOfCompleteFile,String storageFile) throws IOException{
        this.fsm = fsm; this.dmp = dmp; 
        this.startingOffset = startingOffset;
        this.amountWritten = amountWritten;
        this.estimatedSizeOfCompleteFile = estimatedSizeOfCompleteFile;
        if(storageFile==null){
            String store = fsm.store + File.separator + 
                    dmp.getNomenclature().getRegionStorage().makeName(startingOffset,estimatedSizeOfCompleteFile);
            //String store = fsm.store + File.separator + __****_ ;
            regionStore = store;
        }else{
            regionStore = storageFile;
        }
        fileChannel = FileChannel.open(Paths.get(regionStore),StandardOpenOption.SYNC,
                StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
    }
    
    @Override
    public void close() throws IOException {
        channels.close();
        if(fileChannel.isOpen()){
            fileChannel.close();
        }
    }

    @Override
    public SeekableByteChannel getOrCreateResource(String nm,OpenOption... openOptions) throws Exception {
        return channels.addAndGet(FileChannel.open(Paths.get(fsm.store, nm),openOptions));//Utils.create(fsm.store, nm, openOptions));
    }

    @Override
    public Logger createLogger(String nm) {
        String filePath = dmp.getNomenclature().getLogStorage().makeForRegion(nm, startingOffset,estimatedSizeOfCompleteFile);
        
        return Utils.createLogger(filePath, dmp, this, LoggerCreateSPI.Type.Region);
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
    public void transferTo_ReOpenIfRequired(WritableByteChannel wbc)throws IOException {
        FileChannel tempFileChannel;
        tempFileChannel = new RandomAccessFile(regionStore, "r").getChannel();
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
    public long startingOffset() {
        return startingOffset;
    }

    @Deprecated public long ending() {
        if(amountWritten==0)return startingOffset;//faking 1 byte
        return startingOffset+amountWritten-1;
    }
    
    @Override
    public long endingByFileSize()throws IOException{
        long size = fileChannel.size();
        return startingOffset+size-1;
    }
    
    @Override
    public String toString() {
        return DefaultRegionStorageManager.class.getName()+"{"+startingOffset+"}"+regionStore;
    }
}
