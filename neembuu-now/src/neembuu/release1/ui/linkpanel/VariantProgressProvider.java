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

package neembuu.release1.ui.linkpanel;

import neembuu.rangearray.Range;
import neembuu.rangearray.UIRangeArrayAccess;
import neembuu.release1.api.file.NeembuuFile;
import neembuu.release1.api.file.PropertyProvider;
import neembuu.release1.api.ui.access.CloseActionUIA;
import neembuu.release1.api.ui.linkpanel.Graph;
import neembuu.release1.api.ui.linkpanel.Progress;
import neembuu.release1.api.ui.access.ProgressUIA;
import neembuu.release1.api.ui.actions.ReAddAction;
import neembuu.release1.api.ui.linkpanel.ProgressProvider;
import neembuu.release1.api.ui.linkpanel.VariantSelector;
import neembuu.release1.api.ui.linkpanel.VariantSelector.Selectable;
import neembuu.release1.ui.actions.SaveAction_forVariants;
import neembuu.release1.ui.actions.Utils;

/**
 *
 * @author Shashank Tulsyan
 */
public class VariantProgressProvider implements ReAddAction.CallBack, ProgressProvider{
    private final ProgressUIA p;
    private final SaveAction_forVariants saveAction_forVariants;
    private final CloseActionUIA uia;

    private Progress lastSelected;
    private final Graph graph;
    
    private final boolean splitType;
    
    public VariantProgressProvider(ProgressUIA p, SaveAction_forVariants saveAction_forVariants,
            Graph graph,CloseActionUIA uia, boolean sizeFirst) {
        this.p = p; this.saveAction_forVariants = saveAction_forVariants; 
        this.graph = graph==null?new DummyGraph():graph;
        this.uia = uia; this.splitType = sizeFirst;
    }

    @Override public void doneCreation(NeembuuFile neembuuFile) {
        VariantSelector variants = p.variantSelector();
        variants.getItems().removeAll(variants.getItems());
        
        saveAction_forVariants.doneCreation(neembuuFile);
        
        for (NeembuuFile file : neembuuFile.getVariants()) {
            saveAction_forVariants.addFile(file);
            String variantName = file.getPropertyProvider().getStringPropertyValue(PropertyProvider.StringProperty.VARIANT_DESCRIPTION);
            if(variantName==null){
                variantName = Utils.getFileExtension(file.getMinimumFileInfo().getName()).substring(1);
            }
            
            VI x = new VI(variantName,file);
            variants.getItems().add(x);
        }    
    }

    @Override public Progress progress() {
        return lastSelected;
    }
    
    private final class VI implements Selectable {
        private final String display;
        private final NeembuuFile file;
        private final Progress splitProgress;

        public VI(String display, NeembuuFile file) {
            this.display = display; this.file = file;
            splitProgress = new ProgressImpl(p, graph,ProgressImpl.Mode.VariantProgressUI);
        }
        @Override public void select() {    
            splitProgress.init(file.fileBeingDownloaded()); 
            lastSelected = splitProgress;
            
            if(uia==null)return;
            Utils.TintedIcons tintedIcons = Utils.makeTintedIconsForFile(file.getMinimumFileInfo().getName());
            if(tintedIcons==null){return;}
            uia.openButton().setIcon_silent(tintedIcons.unicoloredIcon);
            uia.openButton().setIcon_active(tintedIcons.coloredIcon);
            uia.openButton().setCaption(display);            
        }
        @Override public void unSelect() {  
            splitProgress.unInit(file.fileBeingDownloaded()); 
            if(lastSelected==splitProgress){lastSelected = null;} 
        } 
        @Override public String getText() {
            return Utils.makeFileSizeString(file.getMinimumFileInfo())+" "+ display;
        }
        @Override public String getSmallText() {
            if(splitType){ return display; }
            return Utils.makeFileSizeString(file.getMinimumFileInfo());
        }
    }
    
    private static final class DummyGraph implements Graph{
        @Override public void init(UIRangeArrayAccess regionHandlers) {}
        @Override public void initGraph(Range arrayElement, boolean findFirst) {}
    }
}
