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

package neembuu.release1.defaultImpl.multiVariant;

import neembuu.release1.defaultImpl.splitImpl.*;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import neembuu.release1.api.file.NeembuuFile;
import jpfm.FileAttributesProvider;
import neembuu.release1.api.file.PropertyProvider;
import neembuu.release1.api.ui.access.MinimalistFileSystem;
import neembuu.release1.defaultImpl.file.BasicPropertyProvider;
import neembuu.vfs.file.AutoCompleteControls;
import neembuu.vfs.file.FileBeingDownloaded;
import neembuu.vfs.file.MinimumFileInfo;
import neembuu.vfs.file.RequestPatternListener;

/**
 *
 * @author Shashank Tulsyan
 */
public class MultiVariantSession implements NeembuuFile {
    private final List<NeembuuFile> connectionFiles;
    
    private final FileAttributesProvider folder;
    
    private final AtomicBoolean completelyClosed = new AtomicBoolean(false);
    
    private final MinimalistFileSystem root;
    
    public static final class Holder  { RequestPatternListener requestPatternListener; }
    private final Holder h = new Holder();
    
    private final BasicPropertyProvider bpp = new BasicPropertyProvider();
    
    public MultiVariantSession(List<NeembuuFile> connectionFiles,
            FileAttributesProvider folder,
            MinimalistFileSystem root) {
        this.connectionFiles = connectionFiles;
        this.folder = folder; this.root = root;
    }

    @Override public List<NeembuuFile> getVariants() {
        return connectionFiles;
    }
    
    @Override public void removeFromFileSystem() throws Exception{ 
        root.remove(folder);
    }
    
    @Override public void addToFileSystem(){
        root.add(folder);
    }

    @Override public PropertyProvider getPropertyProvider() { return bpp; }


    @Override
    public FileBeingDownloaded fileBeingDownloaded() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Override public String[] relativePathInVirtualFileSystem(){
        if(true)throw new UnsupportedOperationException();
        return new String[]{folder.getName()}; 
    }
    
    @Override
    public void closeCompletely()throws Exception{
        List<Exception> es = new LinkedList<Exception>();
        if(completelyClosed.compareAndSet(false, true)){
            for (NeembuuFile file : connectionFiles) {
                try{ file.closeCompletely();}
                catch(Exception a){ es.add(a);  }
        }}else {
            throw new IllegalStateException("Already completely closed");
        }if(!es.isEmpty()){
            throw new AggregateException("Could not closeCompletely some files", null, es.toArray(new Exception[es.size()]));
        }}
    
    @Override
    public void deleteSession()throws Exception {
        List<Exception> es = new LinkedList<Exception>();
        for (NeembuuFile file : connectionFiles) {
            try{ file.deleteSession();}
            catch(Exception a){ es.add(a);  }
        }if(!es.isEmpty()){
            throw new AggregateException("Could not deleteSession of some files", null, es.toArray(new Exception[es.size()]));
        }}
    
    @Override
    public void saveACopy(java.io.File outputFilePath)throws Exception{
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean mayBeSaved() {
        return false;/*cannot save a folder lol!*/
    }
    
    private final MinimumFileInfo minimumFileInfo = new MinimumFileInfo() {
        @Override public String getName() { return folder.getName(); }
        @Override public long getFileSize() { return folder.getFileSize(); }};
    
    private final AutoCompleteControls autoCompleteControls = new AutoCompleteControls() {
        private boolean autoCompleteEnabled = true;
        @Override public boolean isAutoCompleteEnabled() { return autoCompleteEnabled; }
        
        @Override public void setAutoCompleteEnabled(boolean autoCompleteEnabled) {
            if(this.autoCompleteEnabled != autoCompleteEnabled){
                for (NeembuuFile file : connectionFiles) {
                    file.autoCompleteControls().setAutoCompleteEnabled(autoCompleteEnabled);
                }
            } this.autoCompleteEnabled = autoCompleteEnabled;
        }};
        
    @Override public MinimumFileInfo getMinimumFileInfo(){ return minimumFileInfo; }
    @Override public AutoCompleteControls autoCompleteControls(){ return autoCompleteControls; }
    @Override public boolean isCompletelyClosed() { return completelyClosed.get();}
}