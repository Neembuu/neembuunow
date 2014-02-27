/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.defaultImpl.single;

import jpfm.DirectoryStream;
import neembuu.diskmanager.DiskManager;
import neembuu.release1.api.ReferenceLink;
import neembuu.vfs.connection.NewConnectionProvider;
import neembuu.vfs.file.AskResume;
import neembuu.vfs.file.SeekableConnectionFile;
import neembuu.vfs.file.SeekableConnectionFileParams;
import neembuu.vfs.file.TroubleHandler;
import neembuu.vfs.progresscontrol.ThrottleFactory;
import neembuu.vfs.readmanager.impl.SeekableConnectionFileImplBuilder;

/**
 *
 * @author Shashank Tulsyan
 */
public final class SeekableConnectionFile_1to1  {
    public static SeekableConnectionFile create(
            ReferenceLink referenceLink, 
            DiskManager diskManager, 
            TroubleHandler troubleHandler, 
            String fileName, DirectoryStream parent) throws Exception {

        final NewConnectionProvider newConnectionProvider = referenceLink
                .getLinkHandler().getFiles().get(0).getConnectionProvider();
        
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
                        .setFileSize(referenceLink.getLinkHandler().getGroupSize())
                        .setDiskManager(diskManager)
                        .setParent(parent)
                        .setNewConnectionProvider(newConnectionProvider)
                        .setTroubleHandler(troubleHandler)
                        .setFileName(fileName)
                        .setAskResume(askResume)
                        .setThrottleFactory(ThrottleFactory.General.SINGLETON)
                        .build()
                );
        
        return file1;
    }

}
