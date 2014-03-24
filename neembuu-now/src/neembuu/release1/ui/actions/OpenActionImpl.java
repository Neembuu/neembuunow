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

import java.util.logging.Level;
import javax.swing.JOptionPane;
import neembuu.release1.Main;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.file.NeembuuFile;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.actions.OpenAction;
import neembuu.release1.api.ui.actions.ReAddAction;

/**
 *
 * @author Shashank Tulsyan
 */
public class OpenActionImpl implements OpenAction, ReAddAction.CallBack{
    private final RealFileProvider realFileProvider;
    private final MainComponent mainComponent;

    public OpenActionImpl(RealFileProvider realFileProvider, MainComponent mainComponent) {
        this.realFileProvider = realFileProvider;
        this.mainComponent = mainComponent;
    }

    private NeembuuFile file;
    
    @Override
    public void doneCreation(NeembuuFile neembuuFile) {
        this.file = neembuuFile;
    }
    
    
    @Override public void actionPerformed() { 
        try{
            openVirtualFile(); 
        }catch(Exception a){
            JOptionPane.showMessageDialog(mainComponent.getJFrame(),a.getMessage(),"Could not open file",JOptionPane.ERROR_MESSAGE);
            Main.getLOGGER().log(Level.SEVERE,"Could not open file",a);
        }
    }
    
    private void openVirtualFile()throws Exception{
        java.io.File f = realFileProvider.getRealFile(file.relativePathInVirtualFileSystem());
        if(f==null){
            JOptionPane.showMessageDialog(mainComponent.getJFrame(),"This file is cannot be opened directly.","Cannot open file",JOptionPane.ERROR_MESSAGE);
            return;
        }
        java.awt.Desktop.getDesktop().open(f);
    }
}
