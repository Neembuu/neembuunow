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

package neembuu.vfs.readmanager.impl;

import jpfm.DirectoryStream;
import neembuu.diskmanager.FileStorageManager;
import neembuu.vfs.file.DownloadConstrainHandler;
import neembuu.vfs.file.FileBeingDownloaded;
import neembuu.vfs.file.SeekableConnectionFile;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpfm.FileFlags;
import jpfm.annotations.NonBlocking;
import jpfm.operations.readwrite.ReadRequest;
import jpfm.volume.BasicCascadableAbstractFile;
import jpfm.volume.OpenCloseListener;
import neembuu.rangearray.Range;
import neembuu.rangearray.UIRangeArrayAccess;
import neembuu.util.logging.LoggerUtil;
import neembuu.util.weaklisteners.WeakListeners;
import neembuu.vfs.annotations.ReadQueueManagerThread;
import neembuu.vfs.connection.NewConnectionProvider;
import neembuu.vfs.file.DownloadCompletedListener;
import neembuu.vfs.file.RequestPatternListener;
import neembuu.vfs.file.SeekableConnectionFileParams;
import neembuu.vfs.file.TroubleHandler;
import neembuu.vfs.readmanager.NewReadHandlerProvider;
import neembuu.vfs.readmanager.rqm.ReadQueueManager;
import neembuu.vfs.readmanager.RegionHandler;
import neembuu.vfs.readmanager.TotalFileReadStatistics;
import net.jcip.annotations.NotThreadSafe;

/**
 *
 * @author Shashank Tulsyan
 */
@NotThreadSafe
final class SeekableConnectionFileImpl // package private
        extends
            BasicCascadableAbstractFile
        implements
            FileBeingDownloaded, SeekableConnectionFile{
    protected String store;

    private final NewReadHandlerProvider readHandlerProvider;
    private final ReadQueueManager readManager;
    private final NewConnectionProvider newConnectionProvider;

    private final SeekableConnectionFileParams scfp;
    private static final Logger LOGGER = LoggerUtil.getLogger();
    
    public SeekableConnectionFileImpl(SeekableConnectionFileParams scfp)throws IOException  {
        //super(name, INVALID_FILE_SIZE);
        super(scfp.getFileName(),scfp.getSize(), scfp.getParent());
        this.scfp = scfp;
        readHandlerProvider = new NewReadHandlerProviderImpl(this,scfp.getSession(),scfp.getThrottleFactory());
        readManager = readHandlerProvider.getReadQueueManager();
        
        LOGGER.log(Level.INFO, "quick url={0} for file={1}", new Object[]{scfp.getConnectionProvider(), scfp.getFileName()});
        
        this.newConnectionProvider = scfp.getConnectionProvider();
        readManager.getRegionHandlers().setFileSize(fileSize);
    }

    final NewConnectionProvider getNewConnectionProvider() {//package private
        return newConnectionProvider;
    }
    
    public final void deleteAllDownloadedData(){
        readManager.getRegionHandlers().removeElement(0,getFileSize());
    }
    
    protected final ReadQueueManager getReadQueueManager(){
        return readManager;
    }

    
    
    @Override
    public void setParent(DirectoryStream parent) {
        this.parent = parent;
    }
    
    @Override
    public DownloadConstrainHandler getDownloadConstrainHandler() {
        return readManager.getDownloadConstrainHandler();
    }
    
    @Override
    public void setAutoCompleteEnabled(boolean autoCompleteEnabled) {
        readManager.setAutoCompleteEnabled(autoCompleteEnabled);
    }

    @Override
    public boolean isAutoCompleteEnabled() {
        return readManager.isAutoCompleteEnabled();
    }
    
    @Override
    public void addDownloadCompletedListener(DownloadCompletedListener dcl) {
        readManager.addDownloadCompletedListener(dcl);
    }

    @Override
    public void removeDownloadCompletedListener(DownloadCompletedListener dcl) {
        readManager.removeDownloadCompletedListener(dcl);
    }

    @Override
    public final TroubleHandler getTroubleHandler() {
        return scfp.getTroubleHandler();
    }

    @Override
    public final FileStorageManager getFileStorageManager() {
        return readHandlerProvider.getFileStorageManager();
    }
    
    @Override
    public final SeekableConnectionFileParams getParams() {
        return scfp;
    }
    
    @Override
    public final void closeCompletely() throws Exception{
        readManager.closeCompletely();
        
    }

    @Override
    public final UIRangeArrayAccess getRegionHandlers(){
        return readManager.getRegionHandlers();//.getRangeArrayAccessforUI()/*connections*/;
    }

    @Override
    public final String toString() {
        return super.toString()+
                (newConnectionProvider==null?"null connection provider":newConnectionProvider.getSourceDescription())+"}";
    }
    
    @Override
    @NonBlocking
    public void read(ReadRequest read) throws Exception {
        readManager.read(read);
        // very risky, in the read event dispatch thread
        synchronized (openCloseListeners){
            if(requestPatternListener!=null){
                requestPatternListener.requested(read.getFileOffset(), read.getFileOffset()+read.getByteBuffer().capacity()-1);
            }if(requestPatternListener2!=null){
                requestPatternListener2.requested(read.getFileOffset(), read.getFileOffset()+read.getByteBuffer().capacity()-1);
            }
        }
    }

    @Override
    public final long getFileSize() {
        return super.getFileSize();
    }

    @Override
    @ReadQueueManagerThread
    public final void close()  {
        readManager.close();
        /*synchronized(readManager.getRegionHandlers().getModLock()){
            for(Range<RegionHandler> handler : readManager.getRegionHandlers()){
                handler.getProperty().close();
            }
        }*/
        /*synchronized (openCloseListeners){
            for(OpenCloseListener ocl : openCloseListeners){
                ocl.closed(this);
            }
        }*/
    }

    @Override
    public FileFlags getFileFlags() {
        return new FileFlags.Builder()
               .setReadOnly()
               .setNoIndex()// these 2 are so that
               .setOffline()// explorer does not attempt to render thumbnails
               .build();
    }

    @Override
    public final void open() {
        readManager.open();
        synchronized(readManager.getRegionHandlers().getModLock()){
            for(Range<RegionHandler> handler : readManager.getRegionHandlers()){
                handler.getProperty().open();
            }
        }
        /*synchronized (openCloseListeners){
            for(OpenCloseListener ocl : openCloseListeners){
                ocl.opened(this);
            }
        }*/
    }
    
    private final List<OpenCloseListener> openCloseListeners
            = new LinkedList<OpenCloseListener>();
    
    private RequestPatternListener requestPatternListener = null, requestPatternListener2 = null;;

    @Override
    public TotalFileReadStatistics getTotalFileReadStatistics() {
        return readManager.getTotalFileReadStatistics();
    }
    
    @Override
    public void addOpenCloseListener(OpenCloseListener closeListener) {
        if(true)throw new UnsupportedOperationException("disabled");
        synchronized (openCloseListeners){
            openCloseListeners.add(
                WeakListeners.create(OpenCloseListener.class,closeListener,this)
            );
        }
    }

    @Override
    public void removeOpenCloseListener(OpenCloseListener closeListener) {
        if(true)throw new UnsupportedOperationException("disabled");
        synchronized (openCloseListeners){
            openCloseListeners.remove(closeListener);
        }
    }

    @Override
    public void addRequestPatternListener(RequestPatternListener rpl) {
        synchronized (openCloseListeners){
            if(requestPatternListener==null){
                requestPatternListener = rpl;
            }else {
                if(requestPatternListener2==null){
                    requestPatternListener2 = rpl;
                }else { 
                    Exception k =
                    new UnsupportedOperationException("Only 2 supported for now "+requestPatternListener);
                    k.printStackTrace();
                    requestPatternListener2 = rpl;
                }
            }
        }
    }

    @Override
    public void removeRequestPatternListener(RequestPatternListener rpl) {
        synchronized (openCloseListeners){
            if(requestPatternListener==rpl){
                requestPatternListener = requestPatternListener2;
                requestPatternListener2 = null;
            }else if(requestPatternListener2==rpl){
                requestPatternListener2 = null;
            }
        }
    }

}
