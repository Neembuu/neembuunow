/*
 * Copyright (C) 2012 Shashank Tulsyan
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
package neembuu.release1.hjsplits;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jpfm.FileAttributesProvider;
import jpfm.fs.splitfs.CascadableSplitFS;
import jpfm.volume.vector.VectorRootDirectory;
import neembuu.vfs.NeembuuVirtualFileSystem;

/**
 * 
 * @author Shashank Tulsyan
 */
public class HJSplitsHandler {

    // @Override
    public boolean canHandle(List<DownloadSession> downloadEntrys) {
        if (downloadEntrys.size() < 1) return false;
        synchronized (downloadEntrys) {
            for (DownloadSession de : downloadEntrys) {
                int index = -2;
                String n = de.getSeekableConnectionFile().getName();
                try {
                    index = Integer.parseInt(n.substring(n.length() - 3));
                } catch (Exception a) {
                    return false;
                }
                if (de.getSeekableConnectionFile().getDownloadConstrainHandler().index() < 0) {
                    de.getSeekableConnectionFile().getDownloadConstrainHandler().setIndex(index);
                }
                if (index < 0) { return false; }
            }
        }
        return true;
    }

    // @Override
    public boolean handle(List<DownloadSession> sessions, NeembuuVirtualFileSystem bfs, String mntLoc) {
        if (!canHandle(sessions)) { throw new IllegalArgumentException("Cannot handle"); }

        Set<FileAttributesProvider> files = new LinkedHashSet<FileAttributesProvider>();
        synchronized (sessions) {
            for (DownloadSession downloadSession : sessions) {
                files.add(downloadSession.getSeekableConnectionFile());
            }
        }

        String name = sessions.get(0).getFinalFileName();
        name = name.substring(0, name.lastIndexOf("."));

        CascadableSplitFS.CascadableSplitFSProvider cascadableSplitFS = new CascadableSplitFS.CascadableSplitFSProvider(files, name);

        bfs.cascadeMount(cascadableSplitFS);

        // remove splited files .001 , .002 ... from virtual folder
        // to avoid confusion
        VectorRootDirectory vrd = (VectorRootDirectory) bfs.getRootDirectoryStream();
        synchronized (sessions) {
            for (DownloadSession downloadSession : sessions) {
                vrd.remove(downloadSession.getSeekableConnectionFile());
                // the file does not exist, therefore the open button should be
                // disabled
                downloadSession.enableOpenButton(false);
            }
        }

        return true;
    }

}
