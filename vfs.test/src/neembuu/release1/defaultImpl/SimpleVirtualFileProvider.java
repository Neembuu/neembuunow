/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.defaultImpl;

import java.util.LinkedList;
import java.util.List;
import neembuu.diskmanager.DiskManagerParams;
import neembuu.diskmanager.DiskManagers;
import neembuu.rangearray.DissolvabilityRule;
import neembuu.rangearray.RangeArray;
import neembuu.rangearray.RangeArrayFactory;
import neembuu.rangearray.RangeArrayParams;
import neembuu.rangearray.UIRangeArrayAccess;
import neembuu.release1.api.Link;
import neembuu.release1.api.VirtualFile;
import neembuu.release1.api.VirtualFilesParams;
import neembuu.release1.api.VirtualFilesProvider;
import neembuu.vfs.connection.NewConnectionProvider;
import neembuu.vfs.file.AskResume;
import neembuu.vfs.file.SeekableConnectionFile;
import neembuu.vfs.file.SeekableConnectionFileParams;
import neembuu.vfs.progresscontrol.ThrottleFactory;
import neembuu.vfs.readmanager.impl.SeekableConnectionFileImplBuilder;

/**
 *
 * @author Shashank Tulsyan
 */
public class SimpleVirtualFileProvider implements VirtualFilesProvider {

    @Override
    public List<VirtualFile> create(VirtualFilesParams vfp) throws Exception{
        LinkedList<VirtualFile> files = new LinkedList<VirtualFile>();
        SimpleVirtualFile svf = new SimpleVirtualFile(vfp);
        files.add(svf);
        return files;
    }

    private static final class SimpleVirtualFile implements VirtualFile {

        private final VirtualFilesParams vfp;

        private final SeekableConnectionFile file;

        private final RangeArray<Boolean> regions;

        public SimpleVirtualFile(VirtualFilesParams vfp) throws Exception {
            this.vfp = vfp;
            regions = RangeArrayFactory.newDefaultRangeArray(new RangeArrayParams.Builder<Boolean>()
                    .addDissolvabilityRule(DissolvabilityRule.COMPARE_PROPERTY_OBJECT)
                    .setFileSize(vfp.getLink().getLinkHandler().getGroupSize())
                    .build());
            file = initFile();
        }

        private SeekableConnectionFile initFile() throws Exception {
            final NewConnectionProvider newConnectionProvider = vfp.getLinkHandler().getFiles().get(0).getConnectionProvider();
            
            final AskResume askResume = new AskResume() {
                public boolean resume() {
                    if (newConnectionProvider.estimateCreationTime(1) >= Integer.MAX_VALUE) {
                        return false;
                    }
                    // for rapidshare type of links clean the
                    // download directory and start fresh

                    // retain stuff for others
                    return true;
                }
            };
            
            SeekableConnectionFile file1
                    = SeekableConnectionFileImplBuilder.build(new SeekableConnectionFileParams.Builder()
                            .setFileSize(vfp.getLink().getLinkHandler().getGroupSize())
                            .setDiskManager(vfp.getDiskManager())
                            .setParent(null)
                            .setNewConnectionProvider(newConnectionProvider)
                            .setTroubleHandler(vfp.getTroubleHandler())
                            .setFileName(vfp.getFileName())
                            .setAskResume(askResume)
                            .setThrottleFactory(ThrottleFactory.General.SINGLETON)
                            .build()
                    );
            return file1;
        }

        @Override
        public UIRangeArrayAccess getRegions() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public SeekableConnectionFile getConnectionFile() {
            return file;
        }

        @Override
        public VirtualFilesParams getVirtualFilesParams() {
            return vfp;
        }

        @Override
        public boolean tryUpdating(String newUrl) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

}
