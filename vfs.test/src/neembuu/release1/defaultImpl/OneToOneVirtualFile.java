/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.defaultImpl;

import neembuu.rangearray.DissolvabilityRule;
import neembuu.rangearray.RangeArray;
import neembuu.rangearray.RangeArrayFactory;
import neembuu.rangearray.RangeArrayParams;
import neembuu.rangearray.UIRangeArrayAccess;
import neembuu.release1.api.ui.LinkUI;
import neembuu.release1.api.VirtualFile;
import neembuu.release1.api.VirtualFilesParams;
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
final class OneToOneVirtualFile implements VirtualFile {

    private final VirtualFilesParams vfp;

    private final SeekableConnectionFile file;

    private final RangeArray<Boolean> regions;
    
    private LinkUI linkUI = null;

    public OneToOneVirtualFile(VirtualFilesParams vfp) throws Exception {
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

    @Override
    public void setUI(LinkUI linkUI) throws IllegalStateException {
        if(this.linkUI!=null){
            throw new IllegalStateException("Already initialized");
        }
        this.linkUI = linkUI;
    }

    @Override
    public LinkUI getUI() throws IllegalStateException {
        if(linkUI==null){
            throw new IllegalStateException("Not initialized");
        }
        return linkUI;
    }

}
