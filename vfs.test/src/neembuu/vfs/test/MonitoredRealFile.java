/*
 * Copyright 2009-2010 Shashank Tulsyan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * File:   MonitoredRealFile.java
 * Author: Shashank Tulsyan
 */

package neembuu.vfs.test;

import java.io.IOException;
import java.util.logging.Logger;
import jpfm.AccessLevel;
import jpfm.DirectoryStream;
import jpfm.FileDescriptor;
import jpfm.FileFlags;
import jpfm.FileId;
import jpfm.FileType;
import jpfm.annotations.NonBlocking;
import jpfm.fs.ReadOnlyRawFileData;
import jpfm.operations.readwrite.ReadRequest;
import jpfm.util.ReadUtils;
import jpfm.volume.CascadableAbstractFile;
import jpfm.volume.RealFile;
import jpfm.volume.RealFileProvider;
import neembuu.rangearray.RangeArray;
import neembuu.rangearray.RangeArrayFactory;
import neembuu.rangearray.RangeArrayParams;
import neembuu.rangearray.UIRangeArrayAccess;

/**
 *
 * @author Shashank Tulsyan
 */
public class MonitoredRealFile implements MonitorableNonHttpFile, CascadableAbstractFile{
    private final RangeArray requestedRegion;
    private MonitoredFilePanel filePanel;
    public final Logger logger;
    private final RealFile realFile;

    public MonitoredRealFile(String srcfile,DirectoryStream fileContainer) throws IOException{
        this(new java.io.File(srcfile), fileContainer);
    }

    public MonitoredRealFile(java.io.File srcfile,DirectoryStream fileContainer) throws IOException{
        realFile = RealFileProvider.getNonBlockingRealFile(srcfile.getAbsolutePath());
        requestedRegion = RangeArrayFactory.newDefaultRangeArray(
                new RangeArrayParams.Builder()
                    .build() );
        requestedRegion.setFileSize(realFile.getFileSize());
        logger = Logger.getLogger(realFile.getName());
        filePanel = new MonitoredFilePanel(this);
        //this(Paths.get(srcfile),fileContainer);
    }


    @Override
    @NonBlocking
    public void read(ReadRequest read) throws Exception {
        //System.gc();
        //logger.log(Level.INFO,"requested=\t"+read.getFileOffset() +"\t"+ read.getByteBuffer().capacity() );
        requestedRegion.addElement(read.getFileOffset(), ReadUtils.endingOffset(read), null);
        realFile.read(read);
        //super.read(read);
                //reading is quick as source is real
        int x = 0;
        while(!read.isCompleted()){
            x++;
            try{
                Thread.sleep(100);
            }catch(Exception any){
                any.printStackTrace();
            }
            if(x>5)System.out.println("waiting...in"+this+" with req="+read);
        }

        //logger.log(Level.INFO,"completed=\t"+read.getFileOffset() +"\t"+  ContentPeek.generatePeekString(read));
    }

    @Override
    public UIRangeArrayAccess getRequestedRegion() {
        return requestedRegion;
    }

    public MonitoredFilePanel getFilePanel() {
        return filePanel;
    }

    public Logger getLogger(){
        return logger;
    }

    @Override
    public void open() {
        realFile.open();
    }

    public final boolean isOpen(){
        return realFile.getFileDescriptor().isOpen();
    }

    @Override
    public void close() {
        realFile.close();
    }

    @Override
    public final FileType getFileType() {
        return realFile.getFileType();
    }

    @Override
    public final FileDescriptor getFileDescriptor() {
        return realFile.getFileDescriptor();
    }

    @Override
    public final long getFileSize() {
        return realFile.getFileSize();
    }

    @Override
    public final long getCreateTime() {
        return realFile.getCreateTime();
    }

    @Override
    public final long getAccessTime() {
        return realFile.getAccessTime();
    }

    @Override
    public final long getWriteTime() {
        return realFile.getWriteTime();
    }

    @Override
    public final long getChangeTime() {
        return realFile.getChangeTime();
    }

    @Override
    public final String getName() {
        return realFile.getName();
    }

    @Override
    public final FileDescriptor getParentFileDescriptor() {
        return realFile.getParentFileDescriptor();
    }

    @Override
    public final FileFlags getFileFlags() {
        return realFile.getFileFlags();
    }

    @Override
    public final boolean isOpenByCascading() {
        return realFile.isOpenByCascading();
    }

    @Override
    public final void setCannotClose(boolean cannotClose) {
        realFile.setCannotClose(cannotClose);
    }

    @Override
    public final ReadOnlyRawFileData getReference(FileId fileId, AccessLevel level) {
        return realFile.getReference(fileId, level);
    }

    @Override
    public final DirectoryStream getParent() {
        return realFile.getParent();
    }
}
