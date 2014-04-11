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

package neembuu.release1.defaultImpl.file;

import java.io.File;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import neembuu.diskmanager.Session;
import neembuu.release1.api.file.NeembuuFile;
import neembuu.release1.api.file.PropertyProvider;
import neembuu.release1.api.ui.access.MinimalistFileSystem;
import neembuu.vfs.file.AutoCompleteControls;
import neembuu.vfs.file.FileBeingDownloaded;
import neembuu.vfs.file.MinimumFileInfo;
import neembuu.vfs.file.SeekableConnectionFile;

/**
 *
 * @author Shashank Tulsyan
 */
public class NeembuuFileWrapSCF implements NeembuuFile {
    private final SeekableConnectionFile file;
    private final MinimalistFileSystem root;
    
    private final AtomicBoolean completelyClosed = new AtomicBoolean(false);
    
    private final PropertyProvider bpp;
    private final String[] basePath;
    
    private final Session s;

    public NeembuuFileWrapSCF(SeekableConnectionFile file, MinimalistFileSystem root,Session s) {
        this(file, root, s,null,null);
    }

    public NeembuuFileWrapSCF(SeekableConnectionFile file, MinimalistFileSystem root,  Session s, PropertyProvider bpp,final String[] basePath) {
        this.file = file; this.root = root; this.bpp = bpp==null?new BasicPropertyProvider():bpp; this.s = s;
        this.basePath = basePath==null?new String[0]:basePath;
    }

    public SeekableConnectionFile getSeekableConnectionFile() {
        return file;
    }

    @Override
    public Session getSession() {
        return s;
    }
    
    @Override
    public AutoCompleteControls autoCompleteControls() {
        return file;
    }

    @Override public PropertyProvider getPropertyProvider() { return bpp; }

    Exception closingStackTrace = null;
    
    @Override
    public void closeCompletely() throws Exception{
        if(completelyClosed.compareAndSet(false, true)){
            file.closeCompletely();
            closingStackTrace = new Exception("Closing stacktrace");
        }else {
            throw new IllegalStateException("Already completely closed",closingStackTrace);
        }
    }

    @Override
    public FileBeingDownloaded fileBeingDownloaded() {
        return file;
    }

    @Override
    public MinimumFileInfo getMinimumFileInfo() {
        return file;
    }

    @Override
    public void removeFromFileSystem() throws Exception {
        root.remove(file);
    }

    @Override
    public void addToFileSystem() {
        root.add(file);
    }

    @Override
    public boolean mayBeSaved() {
        return file.getDownloadConstrainHandler().isComplete();
    }

    @Override
    public void saveACopy(File outputFilePath) throws Exception {
        FileChannel fc = FileChannel.open(outputFilePath.toPath(),
                    StandardOpenOption.WRITE,StandardOpenOption.CREATE
                );
        
        file.getFileStorageManager().copyIfCompleteTo(fc, file.getFileSize());
        fc.force(true);
        fc.close();
    }

    @Override
    public String[] relativePathInVirtualFileSystem() {
        String[]pth=new String[basePath.length+1];
        if(basePath.length>0)
            System.arraycopy(basePath, 0, pth, 0, basePath.length);
        pth[pth.length-1]=file.getName();
        return pth;
    }

    @Override
    public boolean isCompletelyClosed() {
        return completelyClosed.get();
    }

    @Override public List<NeembuuFile> getVariants() { return null; }
    
}
