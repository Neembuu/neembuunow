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

import javax.swing.JComboBox;
import neembuu.rangearray.Range;
import neembuu.rangearray.UIRangeArrayAccess;
import neembuu.release1.api.file.NeembuuFile;
import neembuu.release1.api.file.PropertyProvider;
import neembuu.release1.api.ui.linkpanel.Graph;
import neembuu.release1.api.ui.linkpanel.Progress;
import neembuu.release1.api.ui.access.ProgressUIA;
import neembuu.release1.api.ui.actions.ReAddAction;
import neembuu.release1.api.ui.actions.VariantSelectionAction;
import neembuu.release1.ui.actions.SaveAction_forVariants;
import neembuu.release1.ui.actions.Utils;
import neembuu.vfs.file.FileBeingDownloaded;

/**
 *
 * @author Shashank Tulsyan
 */
public class VariantProgressInitializer implements ReAddAction.CallBack{
    private final ProgressUIA p;
    private final SaveAction_forVariants saveAction_forVariants;

    public VariantProgressInitializer(ProgressUIA p, SaveAction_forVariants saveAction_forVariants) {
        this.p = p; this.saveAction_forVariants = saveAction_forVariants;}

    @Override public void doneCreation(NeembuuFile neembuuFile) {
        JComboBox variants = p.variantSelectionComboBox();
        variants.removeAllItems();
        
        saveAction_forVariants.doneCreation(neembuuFile);
        
        for (NeembuuFile file : neembuuFile.getVariants()) {
            saveAction_forVariants.addFile(file, p);
            String variantName = file.getPropertyProvider().getStringPropertyValue(PropertyProvider.StringProperty.VARIANT_DESCRIPTION);
            if(variantName==null){
                variantName = Utils.getFileExtension(file.getMinimumFileInfo().getName()).substring(1);
            }
            
            VI x = new VI(variantName,file.fileBeingDownloaded(), p);
            variants.addItem(x);
        }    
    }
    
    private static final class VI implements VariantSelectionAction.Selectable {
        private final String display;
        private final FileBeingDownloaded file;
        private final Progress splitProgress;
        public VI(String display, FileBeingDownloaded file, ProgressUIA puia) {
            this.display = display; this.file = file;
            splitProgress = new ProgressImpl(puia, new DummyGraph(),ProgressImpl.Mode.VariantProgressUI);
        }

        @Override public String toString() {    return display; }
        @Override public void select() {        splitProgress.init(file); }
        @Override public void unSelect() {      splitProgress.unInit(file); }
    }
    
    private static final class DummyGraph implements Graph{
        @Override public void init(UIRangeArrayAccess regionHandlers) {}
        @Override public void initGraph(Range arrayElement, boolean findFirst) {}
    }
}
