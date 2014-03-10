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
package neembuu.vfs.file;


/**
 *
 * @author Shashank Tulsyan
 */

import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JPanel;
import jpfm.AccessLevel;
import jpfm.DirectoryStream;
import jpfm.FileDescriptor;
import jpfm.FileFlags;
import jpfm.FileId;
import jpfm.FileType;
import jpfm.annotations.NonBlocking;
import jpfm.fs.ReadOnlyRawFileData;
import jpfm.operations.readwrite.ReadRequest;
import jpfm.volume.OpenCloseListener;
import neembuu.diskmanager.FileStorageManager;
import neembuu.rangearray.RangeArray;
import neembuu.rangearray.RangeArrayFactory;
import neembuu.rangearray.RangeArrayParams;
import neembuu.rangearray.UIRangeArrayAccess;
import neembuu.vfs.connection.NewConnectionProvider;
import neembuu.vfs.readmanager.TotalFileReadStatistics;

/**
 *
 * @author Shashank Tulsyan
 */
public class MonitoredHttpFile 
        implements SeekableConnectionFile{

    private final RangeArray requestedRegion = RangeArrayFactory.newDefaultRangeArray(
            new RangeArrayParams.Builder()
                /*addDissolvabilityRule(
                    DissolvabilityRule.LINEAR_EXPANSIONS_ONLY)*/.build()
            );
    
    public /*MonitoredSeekableHttpFilePanel*/JPanel filePanel;

    private final SeekableConnectionFile vfile ;
    final AtomicInteger cascadeReferenceCount = new AtomicInteger(0);
    
    public MonitoredHttpFile(
            SeekableConnectionFile scf,
            NewConnectionProvider newConnectionProvider){
        this.vfile = scf;
        this.newConnectionProvider = newConnectionProvider;
        requestedRegion.setFileSize(scf.getFileSize());
    }

    public void setFilePanel(JPanel filePanel) {
        this.filePanel = filePanel;
    }
    
    private ReadRequest previous = null;

    private final NewConnectionProvider newConnectionProvider;

    @Override
    @NonBlocking
    public final void read(ReadRequest read) throws Exception {
        
        vfile.read(read);
        requestedRegion.addElement(
                read.getFileOffset(), 
                read.getFileOffset()+read.getByteBuffer().capacity()-1,
                null);
    }

    @Override
    public TroubleHandler getTroubleHandler() {
        return vfile.getTroubleHandler();
    }

    @Override
    public FileStorageManager getFileStorageManager() {
        return vfile.getFileStorageManager();
    }
    
    @Override
    public SeekableConnectionFileParams getParams() {
        return vfile.getParams();
    }
    
    public final RangeArray getRequestedRegion() {
        return requestedRegion;
    }
    
    public final String getSourceDescription(){
        return newConnectionProvider.getSourceDescription();
    }

    public JPanel getFilePanel() {
        return filePanel;
    }

    @Override
    public DownloadConstrainHandler getDownloadConstrainHandler() {
        return vfile.getDownloadConstrainHandler();
    }
    
    @Override
    public void setAutoCompleteEnabled(boolean autoCompleteEnabled) {
        vfile.setAutoCompleteEnabled(autoCompleteEnabled);
    }

    @Override
    public boolean isAutoCompleteEnabled() {
        return vfile.isAutoCompleteEnabled();
    }
    
    @Override
    public UIRangeArrayAccess getRegionHandlers() {
        return vfile.getRegionHandlers();
    }

    @Override
    public TotalFileReadStatistics getTotalFileReadStatistics() {
        return vfile.getTotalFileReadStatistics();
    }


    @Override
    public void addOpenCloseListener(OpenCloseListener closeListener) {
        vfile.addOpenCloseListener(closeListener);
    }

    @Override
    public void removeOpenCloseListener(OpenCloseListener closeListener) {
        vfile.removeOpenCloseListener(closeListener);
    }

    @Override
    public void close() {
        vfile.close();
        requestedRegion.removeElement(0, vfile.getFileSize()-1);
    }

    @Override
    public boolean isOpenByCascading() {
        return cascadeReferenceCount.get()>0;
    }

    @Override
    public void open() {
        vfile.open();
    }

    @Override
    public void closeCompletely() throws Exception{
        vfile.closeCompletely();
    }

    @Override
    public void setCannotClose(boolean cannotClose) {
        vfile.setCannotClose(cannotClose);
    }

    @Override
    public ReadOnlyRawFileData getReference(FileId fileId, AccessLevel level) {
        cascadeReferenceCount.incrementAndGet();
        return new MonitorHttpFileCascade(this, 10);
    }

    @Override
    public FileType getFileType() {
        return vfile.getFileType();
    }

    @Override
    public FileDescriptor getFileDescriptor() {
        return vfile.getFileDescriptor();
    }

    @Override
    public long getFileSize() {
        return vfile.getFileSize();
    }

    @Override
    public long getCreateTime() {
        return vfile.getCreateTime();
    }

    @Override
    public long getAccessTime() {
        return vfile.getAccessTime();
    }

    @Override
    public long getWriteTime() {
        return vfile.getWriteTime();
    }

    @Override
    public long getChangeTime() {
        return vfile.getChangeTime();
    }

    @Override
    public String getName() {
        return vfile.getName();
    }

    @Override
    public FileDescriptor getParentFileDescriptor() {
        return vfile.getParentFileDescriptor();
    }

    @Override
    public FileFlags getFileFlags() {
        return vfile.getFileFlags();
    }

    @Override
    public DirectoryStream getParent() {
        return vfile.getParent();
    }
    
    @Override
    public void setParent(DirectoryStream ds){
        vfile.setParent(ds);
    }
    
    @Override
    public void addDownloadCompletedListener(DownloadCompletedListener dcl) {
        vfile.addDownloadCompletedListener(dcl);
    }

    @Override
    public void removeDownloadCompletedListener(DownloadCompletedListener dcl) {
        vfile.removeDownloadCompletedListener(dcl);
    }

    @Override
    public void addRequestPatternListener(RequestPatternListener rpl) {
        vfile.addRequestPatternListener(rpl);
    }

    @Override
    public void removeRequestPatternListener(RequestPatternListener rpl) {
        vfile.removeRequestPatternListener(rpl);
    }
    
    
    
    
    
}
