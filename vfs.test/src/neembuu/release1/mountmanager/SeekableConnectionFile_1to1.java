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
package neembuu.release1.mountmanager;

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
            String fileName,
            long fileSize,
            final NewConnectionProvider newConnectionProvider, 
            DiskManager diskManager, 
            TroubleHandler troubleHandler, 
            DirectoryStream parent) throws Exception {
        
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
                        .setFileSize(fileSize)
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
