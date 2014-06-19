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

package neembuu.release1.ui.actions;

import java.util.HashMap;
import jpfm.AccessLevel;
import jpfm.FileId;
import jpfm.fs.ReadOnlyRawFileData;
import neembuu.release1.api.file.NeembuuFile;
import neembuu.release1.api.ui.actions.ForceDownloadAction;
import neembuu.release1.api.ui.actions.ReAddAction;
import neembuu.release1.api.ui.linkpanel.VariantSelector;
import neembuu.release1.defaultImpl.file.multiVariant.MultiVariantSession;
import neembuu.release1.defaultImpl.file.split.SplitGroupSession;
import neembuu.vfs.file.SeekableConnectionFile;

/**
 *
 * @author Shashank Tulsyan
 */
public class ForceDownloadActionImpl implements ForceDownloadAction,
        ReAddAction.CallBack {
    
    private final VariantSelector variantSelector;
    
    private final HashMap<NeembuuFile,ReadOnlyRawFileData> forceDownloading = new HashMap<>();
    
    private NeembuuFile nf = null;
    
    public ForceDownloadActionImpl(VariantSelector variantSelector) {
        this.variantSelector = variantSelector;
    }
    
    @Override public boolean isForceDownloading() {
        if(nf==null)return false;
        if(nf instanceof SplitGroupSession){
            return true;
        }else if(nf instanceof MultiVariantSession){
            return checkMV((MultiVariantSession)nf);
        }
        
        if(!(nf.fileBeingDownloaded() instanceof SeekableConnectionFile)){
            return false;
        }
        SeekableConnectionFile scf = (SeekableConnectionFile)nf.fileBeingDownloaded();
        return scf.isOpenByCascading();
    }

    private boolean checkMV(MultiVariantSession mvs){
        NeembuuFile neembuuFile = variantSelector.getSelectedItem().getFile();
        final ReadOnlyRawFileData data;
        synchronized (forceDownloading){
            data = forceDownloading.get(neembuuFile);    
        }
        if(data==null)return false;
        return data.isOpen();
    }
    
    @Override public void forceDownload(boolean force) {
        if(nf==null)return;
        if(nf instanceof SplitGroupSession){
            return;
        }else if(nf instanceof MultiVariantSession){
            forceDownload(variantSelector.getSelectedItem().getFile(),force);
            return;
        }
        forceDownload(nf, force);
    }
    
    
    
    private void forceDownload(NeembuuFile neembuuFile, boolean force){
        synchronized (forceDownloading){
            if(!(neembuuFile.fileBeingDownloaded() instanceof SeekableConnectionFile)){
                return;
            }   
            SeekableConnectionFile scf = (SeekableConnectionFile)neembuuFile.fileBeingDownloaded();
            
            if(force){
                ReadOnlyRawFileData data = scf.getReference(FileId.INVALID, AccessLevel.READ_DATA);
                forceDownloading.put(neembuuFile,data);
            }else{
                ReadOnlyRawFileData data = forceDownloading.get(neembuuFile);
                if(data==null)return;
                try{
                    data.close();
                }catch(Exception a){a.printStackTrace();}
                forceDownloading.remove(neembuuFile);
            }
        }
    }

    @Override
    public void doneCreation(NeembuuFile neembuuFile) {
        this.nf = neembuuFile;
        if(neembuuFile instanceof MultiVariantOpenAction){
            this.nf = neembuuFile;
        }else if(neembuuFile instanceof SplitGroupSession){
            this.nf = neembuuFile;
        }else { // assume single
            this.nf = neembuuFile;
        }
        neembuuFile.getVariants();
        
    }
    
}
