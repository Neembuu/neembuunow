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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import static java.nio.channels.FileChannel.MapMode.*;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
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
    
    // we assume only that only 1 read per download thread is happening.
    private TracedMapBuffer closestRead = null, closestWrite = null; 
    private static final int mapSize = 1024*1024*1;
    
    private static final boolean USE_MEMORY_MAPPED_FILE = false;
    
    private final ArrayList<WeakReference<MappedByteBuffer>> buffers = new ArrayList<WeakReference<MappedByteBuffer>>();

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
        WeakReference<MappedByteBuffer>[]m;
        synchronized (buffers){
            m = buffers.toArray(new WeakReference[buffers.size()]);
        }
        try{
            unmapAll(m);
        }catch(Exception any){
            any.printStackTrace(System.err);
        }
        closestRead = null; closestWrite = null;
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

    /*@Override
    public final int write(ByteBuffer src) throws IOException {
        int written = fileChannel.write(src, amountWritten);
        //fileChannel.force(false);
        amountWritten += written;
        return written;
    }*/

    @Override
    public final int write(ByteBuffer src,long absoulteFileOffset)throws IOException {
        int written;
        if(USE_MEMORY_MAPPED_FILE){
            if(closestWrite==null || !closestWrite.contains(absoulteFileOffset-startingOffset,src)){
                /*if(closestWrite!=null){
                    closestWrite.freeWritable();
                }*/
                closestWrite = new TracedMapBuffer(absoulteFileOffset, startingOffset, 
                        fileChannel,src,READ_WRITE);
            }
            written = closestWrite.write(src,absoulteFileOffset);
        }else{
            written = fileChannel.write(src, absoulteFileOffset-startingOffset);       
            //fileChannel.force(false);
        }
        amountWritten += written;
        return written;
    }
    
    @Override
    public final int read(ByteBuffer src,long absoulteFileOffset) throws IOException{
        long requestEnd = absoulteFileOffset+src.capacity()-1;
        if(requestEnd > ending() ){
            throw new IllegalStateException("Data available till "+ending()+" resuquesting till "+requestEnd);
        }
        int amtRead;
        if(USE_MEMORY_MAPPED_FILE){
            if(closestRead==null || !closestRead.contains(absoulteFileOffset-startingOffset,src)){
                /*if(closestRead!=null){
                    closestRead.freeReadonly(closestWrite);
                }*/
                if(closestWrite!=null && closestWrite.contains(absoulteFileOffset-startingOffset,src)){
                    closestRead = closestWrite.duplicate();
                } else { 
                    closestRead = new TracedMapBuffer(absoulteFileOffset, startingOffset,
                        fileChannel,src, READ_ONLY);
                    if(closestRead.insideDownloadedRegion()){
                        final TracedMapBuffer x = closestRead;
                        //new Thread("load{"+x+"}"){public void run(){
                           x.mbb.load();
                        //}}.start();
                    }
                }
            }
            amtRead = closestRead.read(src,absoulteFileOffset);
        }else{
            amtRead =  fileChannel.read(src, absoulteFileOffset-startingOffset);
            /*System.err.println("DefaultRegionStorageManager:104:offset"+absoulteFileOffset+" "
                    +generatePeekString(src));*/
        }
        return amtRead;
    }

    @Override
    public void transferTo_ReOpenIfRequired(WritableByteChannel wbc,long exclusiveLimit)throws IOException {
        try(FileChannel tempFileChannel = new RandomAccessFile(regionStore, "r").getChannel()){
            long position = 0;
            long size = exclusiveLimit - startingOffset;
            long maxCount = size;
            while (position < size) {
                long count = tempFileChannel.transferTo(position, maxCount, wbc);
                if (count > 0){
                    position += count;
                    maxCount -= count;
                }
            }

            //tempFileChannel.transferTo(0, exclusiveLimit - startingOffset, wbc);
            tempFileChannel.close();
        }catch(IOException a){
            throw a;
        }
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
    
    private final class TracedMapBuffer {
        private final MappedByteBuffer mbb;
        private final long mapStartRelToChan;
        private final TracedMapBuffer dupOf;
        //private final AtomicBoolean freed = new AtomicBoolean(false);

        public TracedMapBuffer(long absoulteFileOffset, long regionStartingOffset, 
                FileChannel fileChannel, ByteBuffer request, MapMode  mm) throws IOException{
            this(absoulteFileOffset, regionStartingOffset, fileChannel, request, mm, null);
        }

        public TracedMapBuffer(long absoulteFileOffset, long regionStartingOffset, 
                FileChannel fileChannel, ByteBuffer request, MapMode  mm,TracedMapBuffer dupOf) throws IOException{
            long mapCapacity = Math.max(mapSize,request.capacity());
            mapStartRelToChan = (absoulteFileOffset - regionStartingOffset);
            mbb = fileChannel.map(mm, mapStartRelToChan, mapCapacity);
            this.dupOf = dupOf;
            
            if(dupOf!=null){
                synchronized (DefaultRegionStorageManager.this.buffers){
                    DefaultRegionStorageManager.this.buffers.add(new WeakReference<>(mbb));
                }
            }
        }

        public TracedMapBuffer(MappedByteBuffer mbb, long mapStartRelToChan, TracedMapBuffer dupOf) {
            this.mbb = mbb;
            this.mapStartRelToChan = mapStartRelToChan;
            this.dupOf = dupOf;
        }
        
        public TracedMapBuffer duplicate(){
            return new TracedMapBuffer((MappedByteBuffer)mbb.duplicate(), 
                    mapStartRelToChan, this);
        }
        
        public boolean contains(long relativeStarting, ByteBuffer bb){
            return mapStartRelToChan<= relativeStarting 
                    && relativeStarting+bb.capacity() < mapStartRelToChan+mapSize;
        }
        
        public boolean insideDownloadedRegion(){
            return (mapStartRelToChan + mapSize) < amountWritten;
        }
        
        public int write(ByteBuffer src, long absoulteFileOffset){
            //check();
            int index = (int)(absoulteFileOffset - startingOffset - mapStartRelToChan);
            mbb.clear(); 
            try{
                mbb.position(index);
                mbb.put(src);
            }catch(Exception a){
                System.out.println("Write exception->");
                a.printStackTrace();
                System.out.println("src->"+absoulteFileOffset+"+>"+src.capacity());
                System.out.println("mbb->"+(startingOffset+mapStartRelToChan)+"+>"+mbb.capacity());
                System.out.println("mbb rel to chan->"+mapStartRelToChan);
                System.out.println("chan starting->"+startingOffset);
                System.out.println("index>"+index);
                System.out.println("mbb limit>"+mbb.limit());
                System.out.println("src limit>"+src.limit());
                throw new RuntimeException(a);
            }
            return src.capacity();
        }
        
        public int read(ByteBuffer dst, long absoulteFileOffset){
            //check();
            int index = (int)(absoulteFileOffset - startingOffset - mapStartRelToChan);
            mbb.clear(); 
            mbb.position(index);
            mbb.limit(index+dst.capacity());
            dst.put(mbb);
            return dst.capacity();
        }

        /*public void freeReadonly(TracedMapBuffer checkWith){
            if(dupOf==checkWith)return;
            freeWritable();
        }
        
        public void freeWritable(){
            if(!freed.compareAndSet(false, true)){
                throw new IllegalStateException("The mapped region has been freed");
            }
            unmap(mbb);
        }
        
        public void check(){
            if(freed.get()){
                throw new IllegalStateException("The mapped region has been freed");
            }
        }*/
    }
    
    private static void unmap(MappedByteBuffer buffer) {
        sun.misc.Cleaner cleaner = ((sun.nio.ch.DirectBuffer) buffer).cleaner();
        cleaner.clean();
    }
    
    private static void unmapAll(WeakReference<MappedByteBuffer>[]m){
        if(!USE_MEMORY_MAPPED_FILE)return;
        for (WeakReference<MappedByteBuffer> m1 : m) {
            MappedByteBuffer mbb = m1.get();
            if(mbb!=null){
                unmap(mbb);
            }
        }
    }
}
