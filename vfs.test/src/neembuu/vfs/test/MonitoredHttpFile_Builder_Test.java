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
package neembuu.vfs.test;

import java.io.File;
import java.lang.reflect.Field;
import jpfm.DirectoryStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import jpfm.util.UniversallyValidFileName;
import neembuu.config.GlobalTestSettings;
import neembuu.diskmanager.DiskManagerParams;
import neembuu.diskmanager.DiskManagers;
import neembuu.util.logging.LoggerUtil;
import neembuu.vfs.connection.NewConnectionProvider;
import neembuu.vfs.connection.sampleImpl.DownloadManager;
import neembuu.vfs.file.AskResume;
import neembuu.vfs.file.MonitoredHttpFile;
import neembuu.vfs.file.SeekableConnectionFile;
import neembuu.vfs.file.SeekableConnectionFileParams;
import neembuu.vfs.file.TroubleHandler;
import neembuu.vfs.progresscontrol.ThrottleFactory;
import neembuu.vfs.readmanager.impl.SeekableConnectionFileImplBuilder;

/**
 *
 * @author Shashank Tulsyan
 */
public class MonitoredHttpFile_Builder_Test {

    private String fileName = null;
    private long fileSize = -1;
    private String storagePath = null;
    private DirectoryStream parent = null;
    private NewConnectionProvider newConnectionProvider = null;
    private String url = null;
    private TroubleHandler troubleHandler = null;
    private static final Logger LOGGER = LoggerUtil.getLogger();

    public MonitoredHttpFile_Builder_Test() {
    }

    public MonitoredHttpFile_Builder_Test setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public MonitoredHttpFile_Builder_Test setNewConnectionProvider(NewConnectionProvider newConnectionProvider) {
        this.newConnectionProvider = newConnectionProvider;
        return this;
    }

    public MonitoredHttpFile_Builder_Test setParent(DirectoryStream parent) {
        this.parent = parent;
        return this;
    }

    public MonitoredHttpFile_Builder_Test setTroubleHandler(TroubleHandler troubleHandler) {
        this.troubleHandler = troubleHandler;
        return this;
    }

    public MonitoredHttpFile_Builder_Test setFileSize(long size) {
        this.fileSize = size;
        return this;
    }

    public MonitoredHttpFile_Builder_Test setStoragePath(String storagePath) {
        this.storagePath = storagePath;
        return this;
    }

    public MonitoredHttpFile_Builder_Test setUrl(String url) {
        this.url = url;
        return this;
    }

    public MonitoredHttpFile build() throws Exception {
        if (storagePath == null || parent == null) {
            throw new IllegalArgumentException("Required parameters storagePath and/or parent not initialized");
        }
        if (newConnectionProvider == null) {
            if (url == null) {
                throw new IllegalStateException("newConnectionProvider==null url==null");
            } else {
                newConnectionProvider =
                        new DownloadManager(url);
                        //new JD_DownloadManager(url);
                long size_obt;

                FileNameAndSizeFinderService.SIZE_AND_NAME size_and_name =
                        FileNameAndSizeFinderService.getSingleton().getSizeAndName(url);
                size_obt = size_and_name.fileSize;
                this.fileName = size_and_name.fileName;
                LOGGER.log(Level.INFO, "Filename={0} FileSize={1}", new Object[]{fileName, fileSize});
                if (size_obt == -1) {
                    throw new IllegalStateException("could not determine size");
                }
                fileSize = size_obt;
            }
        } else {
            if (fileSize <= 0) {
                throw new IllegalStateException("fileSize zero or negative");
            }
            if (fileName == null) {
                throw new IllegalArgumentException("name==null");
            }
            if (!UniversallyValidFileName.isUniversallyValidFileName(fileName)) {
                throw new IllegalArgumentException("name not universally valid. name=" + fileName);
            }
        }


        File f = new File(storagePath);
        if (f.exists()) {
            if (!f.isDirectory()) {
                throw new IllegalArgumentException("Storage path is not a directory");
            }
        } else {
            throw new IllegalArgumentException("Storage path does not exists");
        }

        SeekableConnectionFileParams fileParams = new SeekableConnectionFileParams.Builder()
                .setFileName(fileName)
                .setFileSize(fileSize)
                .setParent(parent)
                .setNewConnectionProvider(newConnectionProvider)
                .setDiskManager(DiskManagers.getDefaultManager(new DiskManagerParams.Builder().setBaseStoragePath(storagePath).build()))
                .setThrottleFactory(ThrottleFactory.General.SINGLETON)
                .setTroubleHandler(troubleHandler)
                .setAskResume(new UnprofessionalAskResume(fileName))
                .build();

        SeekableConnectionFile scf = SeekableConnectionFileImplBuilder.build(fileParams);

        MonitoredHttpFile mhf = new MonitoredHttpFile(scf, newConnectionProvider);
        MonitoredSeekableHttpFilePanel mshfp = new MonitoredSeekableHttpFilePanel(mhf);
        mhf.setFilePanel(mshfp);
        return mhf;
    }
    
    private static final class UnprofessionalAskResume implements AskResume{
        private final String this_seekableHttpFile_getName;

        private UnprofessionalAskResume(String this_seekableHttpFile_getName) {
            this.this_seekableHttpFile_getName = this_seekableHttpFile_getName;
        }
        
        @Override
        public boolean resume() {
            if(System.getProperty("neembuu.vfs.test.MoniorFrame.resumepolicy")!=null){
                if(System.getProperty("neembuu.vfs.test.MoniorFrame.resumepolicy")
                        .equals("resumeFromPreviousState")){
                    return true;
                }
            }
            if(GlobalTestSettings.IS_RUNNING_FROM_JAR){
                JFrame parent_frame = null;
                try{
                    Class mfc = this.getClass().getClassLoader().loadClass("neembuu.vfs.test.MonitorFrame");
                    Field f = mfc.getDeclaredField("frame");
                    f.setAccessible(true);
                    parent_frame = (JFrame)f.get(null);//the field is static
                }catch(Exception a){

                }


                int i = JOptionPane.showConfirmDialog(parent_frame,
                        "It looks "+this_seekableHttpFile_getName+"\n"
                        + "was watched sometime earlier. \n"
                        + "Some data is available from the previous session. \n"
                        + "Do you want to resume with this data.\n"
                        + "This will save some of your time.\n",
                        "Should I continue from where I left?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        GlobalTestSettings.ONION_EMOTIONS.SIMPLE_DOUBT
                    );
                if(i==JOptionPane.OK_OPTION){
                    return true;
                }else {
                    return false;
                }
            }else{
                return false;
            }
        }
    }
}
