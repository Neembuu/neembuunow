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
        
        ChooseVariantTimeOut.Entry defaultOption = ChooseVariantTimeOut.newEntry(
            idealFile.getPropertyProvider().getStringPropertyValue(PropertyProvider.StringProperty.VARIANT_DESCRIPTION), 
            idealFile.getMinimumFileInfo().getFileSize(),
            idealFile.getPropertyProvider().getLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS));
        
        ArrayList<ChooseVariantTimeOut.Entry> entries 
                = new ArrayList<>();

        for (NeembuuFile file : files) {
            if(file==idealFile){
                entries.add(defaultOption);
            }else {
                entries.add(ChooseVariantTimeOut.newEntry(
                    file.getPropertyProvider().getStringPropertyValue(PropertyProvider.StringProperty.VARIANT_DESCRIPTION), 
                    file.getMinimumFileInfo().getFileSize(),
                    file.getPropertyProvider().getLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS)));
            }
        }
        ChooseVariantTimeOut.Entry choice = ChooseVariantTimeOut.showMessage(mainComponent, 50, defaultOption, entries);

        if(choice==null){return;}
        for (NeembuuFile file : files) {
            if(choice.type().equalsIgnoreCase(file.getPropertyProvider().getStringPropertyValue(PropertyProvider.StringProperty.VARIANT_DESCRIPTION))){
                OpenActionImpl impl = new OpenActionImpl(realFileProvider, mainComponent);
                impl.doneCreation(file);
                impl.actionPerformed(); 
                return;
            }
        }
        
        JOptionPane.showMessageDialog(mainComponent.getJFrame(), "Could not find type."+choice.type(), "Cannot open file", JOptionPane.ERROR_MESSAGE);
        
    }
    
}
