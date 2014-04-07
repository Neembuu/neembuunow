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
import javax.swing.JOptionPane;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.file.NeembuuFile;
import neembuu.release1.api.file.PropertyProvider;
import neembuu.release1.api.open.Open;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.actions.OpenAction;
import neembuu.release1.api.ui.actions.ReAddAction.CallBack;
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

    public MultiVariantOpenAction(RealFileProvider realFileProvider, MainComponent mainComponent, DownloadSpeedProvider downloadSpeedProvider) {
        this.realFileProvider = realFileProvider;
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
            
            if(delta< previous_delta && delta >= 0){
                idealFile = file; previous_delta = delta;
            }
        }
        
        String sdef = idealFile.getPropertyProvider().getStringPropertyValue(PropertyProvider.StringProperty.VARIANT_DESCRIPTION);
        if(sdef==null || sdef.length()==0){ sdef=idealFile.getMinimumFileInfo().getName(); }
        ChooseVariantTimeOut.Entry defaultOption = ChooseVariantTimeOut.newEntry(
            sdef, 
            idealFile.getMinimumFileInfo().getFileSize(),
            idealFile.getPropertyProvider().getLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS));
        
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
                    file.getPropertyProvider().getLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS)));
            }
        }
        ChooseVariantTimeOut.Entry choice = ChooseVariantTimeOut.showMessage(mainComponent, 50, defaultOption, entries);

        if(choice==null){return;}
        for (NeembuuFile file : files) {
            String s = file.getPropertyProvider().getStringPropertyValue(PropertyProvider.StringProperty.VARIANT_DESCRIPTION);
            if(s==null || s.length()==0){ s=file.getMinimumFileInfo().getName(); }
            if(choice.type().equalsIgnoreCase( s )){
                OpenActionImpl impl = new OpenActionImpl(realFileProvider, mainComponent);
                impl.doneCreation(file);
                try{
                    open = impl.openVirtualFile();
                    return;
                }catch(Exception a){
                    
                }
            }
        }
        
        mainComponent.newMessage().error()
            .setMessage("Could not find type."+choice.type())
            .setTitle("Cannot open file")
            .show();
        
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
