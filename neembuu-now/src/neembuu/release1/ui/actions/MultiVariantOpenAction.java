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

import java.util.ArrayList;
import java.util.List;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.file.NeembuuFile;
import neembuu.release1.api.file.PropertyProvider;
import neembuu.release1.api.open.Open;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.access.ProgressUIA;
import neembuu.release1.api.ui.actions.OpenAction;
import neembuu.release1.api.ui.actions.ReAddAction.CallBack;
import neembuu.release1.api.ui.linkpanel.VariantSelector;
import neembuu.release1.ui.ChooseVariantTimeOut;
import neembuu.vfs.progresscontrol.DownloadSpeedProvider;

/**
 *
 * @author Shashank Tulsyan
 */
public class MultiVariantOpenAction implements OpenAction, CallBack{
    private NeembuuFile neembuuFile;
    private final RealFileProvider realFileProvider;
    private final MainComponent mainComponent;
    
    private final DownloadSpeedProvider downloadSpeedProvider;
    
    private final ProgressUIA progressUIA;

    public MultiVariantOpenAction(RealFileProvider realFileProvider, MainComponent mainComponent, DownloadSpeedProvider downloadSpeedProvider,ProgressUIA progressUIA) {
        this.realFileProvider = realFileProvider; this.progressUIA = progressUIA;
        this.mainComponent = mainComponent; this.downloadSpeedProvider = downloadSpeedProvider;
    }
    

    @Override public void doneCreation(NeembuuFile neembuuFile) {
        this.neembuuFile = neembuuFile;
    }

    @Override
    public void actionPerformed() {
        List<NeembuuFile> files = neembuuFile.getVariants();
        if(files==null || files.isEmpty()) {
            OpenActionImpl impl = new OpenActionImpl(realFileProvider, mainComponent);
            impl.doneCreation(neembuuFile);
            impl.actionPerformed(); throw new UnsupportedOperationException("Expected multiple files to be present as this is multivariant type");
        }
        
        final double downloadSpeed  = downloadSpeedProvider.getDownloadSpeed_KiBps();

        
        double previous_delta = Double.MAX_VALUE; 
        NeembuuFile idealFile=null;
        for (NeembuuFile file : files) {
            if(idealFile==null){ idealFile = file; }
            
            long duration_in_milliseconds = file.getPropertyProvider().getLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS);
            long fileSize = file.getMinimumFileInfo().getFileSize();
            
            double speedRequired = fileSize/(duration_in_milliseconds*1.024d);
            
            double delta = downloadSpeed - speedRequired;
            
            if(file.getPropertyProvider().getBooleanPropertyValue(PropertyProvider.BooleanProperty.UNSTABLE_VARIANT)){
                continue;
            }
            
            if(delta< previous_delta && delta >= 0){
                idealFile = file; previous_delta = delta;
            }
        }
        
        String sdef = idealFile.getPropertyProvider().getStringPropertyValue(PropertyProvider.StringProperty.VARIANT_DESCRIPTION);
        if(sdef==null || sdef.length()==0){ sdef=idealFile.getMinimumFileInfo().getName(); }
        ChooseVariantTimeOut.Entry defaultOption = ChooseVariantTimeOut.newEntry(
            sdef, 
            idealFile.getMinimumFileInfo().getFileSize(),
            idealFile.getPropertyProvider().getLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS),
            false
        );
        
        ArrayList<ChooseVariantTimeOut.Entry> entries 
                = new ArrayList<>();

        for (NeembuuFile file : files) {
            if(file==idealFile){
                entries.add(defaultOption);
            }else {
                String s = file.getPropertyProvider().getStringPropertyValue(PropertyProvider.StringProperty.VARIANT_DESCRIPTION);
                if(s==null || s.length()==0){ s=file.getMinimumFileInfo().getName(); }
                entries.add(ChooseVariantTimeOut.newEntry(
                    s, 
                    file.getMinimumFileInfo().getFileSize(),
                    file.getPropertyProvider().getLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS),
                    file.getPropertyProvider().getBooleanPropertyValue(PropertyProvider.BooleanProperty.UNSTABLE_VARIANT)
                ));
            }
        }
        ChooseVariantTimeOut.Entry choice = ChooseVariantTimeOut.showMessage(mainComponent, 50, defaultOption, entries);

        if(choice==null){return;}
        
        NeembuuFile fileToOpen = findFileAndOpen(files, choice);
        if(fileToOpen==null){
            mainComponent.newMessage().error()
                .setMessage("Could not find type."+choice.type())
                .setTitle("Cannot open file")
                .show();
            return;
        }
        
        findAndAutoSelectChoice(fileToOpen, choice);        
    }
    
    private void findAndAutoSelectChoice(NeembuuFile fileToOpen, ChooseVariantTimeOut.Entry choice){
        try{
            List<VariantSelector.Selectable> selectables = progressUIA.variantSelector().getItems();
            for (VariantSelector.Selectable selectable : selectables) {
                try{
                    if(compare(fileToOpen, selectable.getFile())){
                        progressUIA.variantSelector().actionPerformed(selectable);
                        return;
                    }
                }catch(Exception ignore){
                    ignore.printStackTrace();
                }
            }
        }catch(Exception ignore){
            ignore.printStackTrace();
        }
    }
    
    private boolean compare(NeembuuFile f1,NeembuuFile f2){
        if(f1==null & f2==null)return true;
        if(f1==null || f2==null)return false;
        if(f1==f2)return true;
        
        if(f1.getMinimumFileInfo()==null || f2.getMinimumFileInfo()==null)return false;
        if(f1.getMinimumFileInfo().getName()==null || f2.getMinimumFileInfo().getName()==null)return false;
        
        if(f1.getMinimumFileInfo().getFileSize() == f2.getMinimumFileInfo().getFileSize()
                && 
                f1.getMinimumFileInfo().getName().equals(f2.getMinimumFileInfo().getName())
                )return true;
        return false;
    }
    
    private NeembuuFile findFileAndOpen(List<NeembuuFile> files, ChooseVariantTimeOut.Entry choice){
        for (NeembuuFile file : files) {
            String s = file.getPropertyProvider().getStringPropertyValue(PropertyProvider.StringProperty.VARIANT_DESCRIPTION);
            if(s==null || s.length()==0){ s=file.getMinimumFileInfo().getName(); }
            if(choice.type().equalsIgnoreCase( s )){
                OpenActionImpl impl = new OpenActionImpl(realFileProvider, mainComponent);
                impl.doneCreation(file);
                try{
                    open = impl.openVirtualFile();
                    return file;
                }catch(Exception a){
                    
                }
            }
        }return null;
    }
    
    private Open open = null;

    @Override
    public void close() {
        if(open!=null){
            try{
                open.close();
            }catch(Exception a){
                
            }
        }
    }
    
}
