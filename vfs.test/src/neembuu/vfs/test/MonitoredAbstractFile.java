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
import jpfm.DirectoryStream;
import jpfm.annotations.NonBlocking;
import jpfm.operations.readwrite.ReadRequest;
import jpfm.util.ReadUtils;
import jpfm.volume.AbstractFile;
import jpfm.volume.BasicAbstractFileWrap;
import neembuu.rangearray.RangeArray;
import neembuu.rangearray.RangeArrayFactory;
import neembuu.rangearray.RangeArrayParams;
import neembuu.rangearray.UIRangeArrayAccess;
import neembuu.util.logging.LoggerUtil;


/**
 *
 * @author Shashank Tulsyan
 */
public class MonitoredAbstractFile extends BasicAbstractFileWrap implements MonitorableNonHttpFile {
    private final RangeArray requestedRegion;
    private MonitoredFilePanel filePanel;
    private static final Logger LOGGER = LoggerUtil.getLogger();

    public MonitoredAbstractFile(AbstractFile srcfile,DirectoryStream fileContainer) throws IOException{
        super(srcfile,fileContainer);
        
        requestedRegion = RangeArrayFactory.newDefaultRangeArray(
                new RangeArrayParams.Builder()
                    .build() );
        
        requestedRegion.setFileSize(super.getFileSize());
        filePanel = new MonitoredFilePanel(this);

        /*if(fileContainer==null)return;s
        if(!((VectorFileContainer)fileContainer).contains(this))
            ((VectorFileContainer)fileContainer).add(this);*/
    }

    
    private long lastTime = System.currentTimeMillis();

    @Override
    @NonBlocking
    public void read(ReadRequest read) throws Exception {
        long currentTime = System.currentTimeMillis();
        try{
            long supplySpeed =
                read.getByteBuffer().capacity()
                / 
                (currentTime - lastTime)
                ;
            lastTime = currentTime;
            getFilePanel().setSupplySpeed(supplySpeed);
        }catch(ArithmeticException ae){
            //ignore
        }
        
        requestedRegion.addElement(read.getFileOffset(), ReadUtils.endingOffset(read), null);
        abstractFile.read(read);
    }

    @Override
    public UIRangeArrayAccess getRequestedRegion() {
        return requestedRegion;
    }

    public MonitoredFilePanel getFilePanel() {
        return filePanel;
    }

    public static Logger getLogger(){
        return LOGGER;
    }

    @Override
    public void open() {
        abstractFile.open();
    }

    @Override
    public void close() {
        abstractFile.close();
    }



}
