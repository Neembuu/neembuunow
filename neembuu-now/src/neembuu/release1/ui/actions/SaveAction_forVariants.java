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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import neembuu.release1.api.file.NeembuuFile;
import neembuu.release1.api.file.Saveable;
import neembuu.release1.api.settings.Settings;
import neembuu.release1.api.ui.IndefiniteTaskUI;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.access.ProgressUIA;
import neembuu.release1.api.ui.actions.ReAddAction.CallBack;
import neembuu.release1.api.ui.actions.SaveAction;

/**
 *
 * @author Shashank Tulsyan
 */
public class SaveAction_forVariants implements SaveAction,CallBack {
    private final MainComponent mainComponent;
    private final ProgressUIA ui;
    private final Settings settings;
    private final IndefiniteTaskUI itui;
    private final List<PopupEntry> doneFiles = new LinkedList<PopupEntry>();

    private PopupEntry mainFileMenuItem = null;
    private String warning = null;

    public SaveAction_forVariants(MainComponent mainComponent,
            ProgressUIA ui,Settings settings,IndefiniteTaskUI itui) {
        this.mainComponent = mainComponent; this.ui = ui; 
        this.settings = settings; this.itui = itui;
    }
    
    @Override
    public void sendWarning(String warning){
        this.warning = warning;
    }
    
    @Override
    public void doneCreation(final NeembuuFile neembuuFile) {
        doneFiles.removeAll(doneFiles);       
        mainFileMenuItem = new PopupEntry(neembuuFile, neembuuFile.getMinimumFileInfo().getName());
        mainFileMenuItem.setVisible(false);
    }
    
    public void addFile(Saveable a){
        PopupEntry puia = new PopupEntry(a, a.getMinimumFileInfo().getName());
        doneFiles.add(puia);
    }
    
    public final class PopupEntry extends JMenuItem implements ActionListener{
        private final Saveable file;
        private final String displayName;
        PopupEntry(Saveable file, String displayName) {
            super(displayName);this.file = file; this.displayName = displayName;
            super.addActionListener(this);setVisible(false);
        } 

        @Override public void setVisible(boolean aFlag) {
            if(aFlag)SaveAction_forVariants.this.ui.saveButton().setVisible(true);
            super.setVisible(aFlag); 
        }

        @Override public void actionPerformed(ActionEvent e) {
            SaveActionImpl sai = new SaveActionImpl(mainComponent,settings,itui);
            sai.setFile(file);
            sai.sendWarning(warning); // delegating the task to show warning
            sai.actionPerformed();}
    }
    
    @Override
    public void actionPerformed() {
        final JPopupMenu menu = new JPopupMenu("Menu");
        if(mainFileMenuItem!=null){
            menu.add(mainFileMenuItem);
            for (PopupEntry popupEntry : doneFiles) {
                popupEntry.setVisible(popupEntry.file.mayBeSaved());
            }
            mainFileMenuItem.setVisible(mainFileMenuItem.file.mayBeSaved());
        }
        for (PopupEntry popupEntry : doneFiles) { menu.add(popupEntry); }
        JComponent b = ui.saveButton(); menu.show(b,0,b.getHeight());
    }
    
}
