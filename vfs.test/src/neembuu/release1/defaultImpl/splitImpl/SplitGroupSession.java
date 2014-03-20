/*
 * Copyright (C) 2014 Shashank Tulsyan
 *
 * This program is free software: you can redistribute it and/or modify
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.release1.defaultImpl.splitImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import neembuu.release1.api.file.NeembuuFile;
import jpfm.FileAttributesProvider;
import jpfm.mount.BasicCascadeMount;
import neembuu.rangearray.RangeArrayFactory;
import neembuu.rangearray.UIRangeArrayAccess;
import neembuu.release1.api.file.PropertyProvider;
import neembuu.release1.defaultImpl.file.BasicPropertyProvider;
import neembuu.release1.mountmanager.NeembuuFileWrapSCF;
import neembuu.vfs.file.AutoCompleteControls;
import neembuu.vfs.file.FileBeingDownloaded;
import neembuu.vfs.file.MinimumFileInfo;
import neembuu.vfs.file.RequestPatternListener;
import neembuu.vfs.file.SeekableConnectionFile;
import neembuu.vfs.readmanager.ReadRequestState;
import neembuu.vfs.readmanager.TotalFileReadStatistics;

/**
 *
 * @author Shashank Tulsyan
 */
public class SplitGroupSession implements NeembuuFile {
    private final BasicCascadeMount bcm;
    private final List<SeekableConnectionFile> connectionFiles;
    
    private final FileAttributesProvider fap;   
    private final UIRangeArrayAccess<ReadRequestState> totalRegion;
    private final TotalFileReadStatistics totalFileReadStatistics;
    
    private final AtomicBoolean completelyClosed = new AtomicBoolean(false);
    
    public static final class Holder  { RequestPatternListener requestPatternListener; }
    private final Holder h = new Holder();
    
    private final BasicPropertyProvider bpp = new BasicPropertyProvider();
    
    public SplitGroupSession(BasicCascadeMount bcm,
             List<SeekableConnectionFile> connectionFiles) {
        this.bcm = bcm;
        this.connectionFiles = connectionFiles;
        
        fap = bcm.getFileSytem().list(bcm.getFileSytem().getRootAttributes().getFileDescriptor().getFileId())
                .iterator().next();
        
        
        List<UIRangeArrayAccess<ReadRequestState>> accesses = new LinkedList<UIRangeArrayAccess<ReadRequestState>>();
        for (SeekableConnectionFile seekableConnectionFile : connectionFiles) {
            accesses.add(seekableConnectionFile.getRegionHandlers());
        }        
        ReadRequestRescaler rrr = new ReadRequestRescaler();
        totalRegion = RangeArrayFactory.merge(accesses,rrr);
        
        totalFileReadStatistics = new TotalFileReadStatistics_forSplits(connectionFiles);
        AggregateRequestPatternListener.initializeAggregateRequestListener(connectionFiles,h);
    }

    @Override public List<NeembuuFile> getVariants() {
        final ArrayList<NeembuuFile> neembuuFiles = new ArrayList<>();
        for (SeekableConnectionFile seekableConnectionFile : connectionFiles) {
            neembuuFiles.add(new NeembuuFileWrapSCF(seekableConnectionFile, null, null,null));
        } return neembuuFiles;
    }

    @Override public PropertyProvider getPropertyProvider() { return bpp; }
    
    @Override public String[] relativePathInVirtualFileSystem(){
        return new String[]{bcm.getFileSytem().getRootAttributes().getName(),fap.getName()};
    }
    
    @Override
    public void closeCompletely()throws Exception{
        List<Exception> es = new LinkedList<Exception>();
        if(completelyClosed.compareAndSet(false, true)){
            for (SeekableConnectionFile file : connectionFiles) {
                try{ file.closeCompletely();}
                catch(Exception a){ es.add(a);  }
        }}else {
            throw new IllegalStateException("Already completely closed");
        }if(!es.isEmpty()){
            throw new AggregateException("Could not closeCompletely some files", null, es.toArray(new Exception[es.size()]));
        }}
    
    @Override
    public void deleteSession()throws Exception {
        List<Exception> es = new LinkedList<Exception>();
        for (SeekableConnectionFile file : connectionFiles) {
            try{ file.getFileStorageManager().deleteSession();}
            catch(Exception a){ es.add(a);  }
        }if(!es.isEmpty()){
            throw new AggregateException("Could not deleteSession of some files", null, es.toArray(new Exception[es.size()]));
        }}
    
    @Override
    public void saveACopy(java.io.File outputFilePath)throws Exception{
        // We shall save only the merge. Saving splits is not useful for the user
        try(FileChannel dest = new FileOutputStream(outputFilePath).getChannel()){
            long[]cummulativeFileSizes = new long[connectionFiles.size()];
            int i = 0; SeekableByteChannel osbc;
            Collections.sort(connectionFiles, new Comparator<SeekableConnectionFile>() {
                @Override public int compare(SeekableConnectionFile o1, SeekableConnectionFile o2) {
                    return o1.getDownloadConstrainHandler().index() - o2.getDownloadConstrainHandler().index();
                }
            });
            for(SeekableConnectionFile file : connectionFiles){
                if(i==0){
                    osbc = dest;
                    cummulativeFileSizes[i]=0+file.getFileSize();
                }else{
                    dest.position(cummulativeFileSizes[i]);
                    osbc = new OffsettedSeekableByteChannel(cummulativeFileSizes[i-1], dest);
                    cummulativeFileSizes[i]=cummulativeFileSizes[i-1]+file.getFileSize();
                }i++;
                file.getFileStorageManager().copyIfCompleteTo(osbc, file.getFileSize());
                
            }
            dest.force(true);
        }
    }
    
    private final FileBeingDownloaded fileBeingDownloaded = new FileBeingDownloaded(){
        @Override public void addRequestPatternListener(RequestPatternListener rpl) {
            if(h.requestPatternListener==null){h.requestPatternListener = rpl;
            }else { throw new UnsupportedOperationException("Only one supported for now "+h.requestPatternListener);}}

        @Override public void removeRequestPatternListener(RequestPatternListener rpl) {
            if(h.requestPatternListener==rpl){ h.requestPatternListener = null;}}
        
        @Override public UIRangeArrayAccess getRegionHandlers() {return totalRegion;}
        @Override public TotalFileReadStatistics getTotalFileReadStatistics() {return totalFileReadStatistics; }
        @Override public boolean isAutoCompleteEnabled() { return autoCompleteControls.isAutoCompleteEnabled(); }
        @Override public String getName() { return minimumFileInfo.getName(); }
        @Override public long getFileSize() { return minimumFileInfo.getFileSize(); }};
    
    private final MinimumFileInfo minimumFileInfo = new MinimumFileInfo() {
        @Override public String getName() { return fap.getName(); }
        @Override public long getFileSize() { return fap.getFileSize(); }};
    
    private final AutoCompleteControls autoCompleteControls = new AutoCompleteControls() {
        private boolean autoCompleteEnabled = true;
        @Override public boolean isAutoCompleteEnabled() { return autoCompleteEnabled; }
        
        @Override public void setAutoCompleteEnabled(boolean autoCompleteEnabled) {
            if(this.autoCompleteEnabled != autoCompleteEnabled){
                for (SeekableConnectionFile seekableConnectionFile : connectionFiles) {
                    seekableConnectionFile.setAutoCompleteEnabled(autoCompleteEnabled);
                }
            } this.autoCompleteEnabled = autoCompleteEnabled;
        }};

    @Override public boolean mayBeSaved() {
        boolean mayBeSaved = true;
        for(SeekableConnectionFile file : connectionFiles){
            if(!file.getDownloadConstrainHandler().isComplete()) {
                mayBeSaved = false;break;
            }
        }
        return mayBeSaved;
    }
        
    
    @Override public MinimumFileInfo getMinimumFileInfo(){ return minimumFileInfo; }
    @Override public FileBeingDownloaded fileBeingDownloaded(){ return fileBeingDownloaded; }
    @Override public AutoCompleteControls autoCompleteControls(){ return autoCompleteControls; }
    @Override public boolean isCompletelyClosed() { return completelyClosed.get();}
    @Override public void removeFromFileSystem() throws Exception{ bcm.unMount(); }
    @Override public void addToFileSystem(){ /* When cascade mount is create, then it automatically added as well.*/}
}