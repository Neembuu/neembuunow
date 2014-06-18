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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import neembuu.release1.api.file.NeembuuFile;
import neembuu.release1.api.file.Saveable;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.release1.api.settings.Settings;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.actions.ReAddAction;
import neembuu.release1.api.ui.actions.SaveAction;
import neembuu.util.Throwables;

/**
 *
 * @author Shashank Tulsyan
 */
public class SaveActionImpl implements SaveAction,ReAddAction.CallBack{
    private Saveable connectionFile;
    private final MainComponent mainComponent;
    private final Settings settings;

    private String warning = null;
    
    public SaveActionImpl(MainComponent mainComponent,Settings settings) {
        this.mainComponent = mainComponent; this.settings = settings;
        /*if(connectionFile==null){
            throw new IllegalArgumentException("Connection file not initialized");
        }*/
    }        

    public void setFile(Saveable saveable){
        this.connectionFile = saveable;
    }
    
    @Override
    public void doneCreation(NeembuuFile neembuuFile) {
        connectionFile = neembuuFile;
    }

    @Override
    public void sendWarning(String warning) {
        this.warning = warning;
    }
    
    private boolean warning(){
        if(warning==null)return false;
        return mainComponent.newMessage().error()
                .setMessage(warning)
                .setTitle("There might be issues in saving the file")
                .setTimeout(10)
                .ask();
    }
    
    private void saveAction(java.io.File outputFilePath){
        if(warning())return;
        try{
            connectionFile.saveACopy(outputFilePath);
        }catch(Exception a){
            mainComponent.newMessage().error()
                .setMessage(a.getMessage())
                .setTitle("Could not save file")
                .show();
            LoggerUtil.L().log(Level.SEVERE, "Could not save file",a);
        }
    }
    
    private void saveFileClicked(){
        String p = getSaveLocation();
        
        final javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setSelectedFile(new File(p+File.separator+
                connectionFile.getMinimumFileInfo().getName()));
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
        int retVal = fileChooser.showSaveDialog(mainComponent.getJFrame());
        if(retVal == javax.swing.JFileChooser.APPROVE_OPTION){
            saveAction(fileChooser.getSelectedFile().getAbsoluteFile());
            
            Throwables.start(new Runnable(){@Override public void run() {try{
                    setSaveLocation(fileChooser);
                }catch(Exception a){throw new RuntimeException(a);}}});
        }else {

        }
    }
    
    private String getSaveLocation(){
        String p = settings.get("SaveActionImpl","previousSaveLocation");
        String defaultP = System.getProperty("java.home");
        if(p==null){
            return defaultP;
        }
        Path pp = Paths.get(p);
        if(Files.exists(pp)){
            if(Files.isDirectory(pp)){
                return p;
            }
        }
        return defaultP;
    }
    
    private void setSaveLocation(final javax.swing.JFileChooser fileChooser)throws java.io.IOException{
        File f = fileChooser.getSelectedFile();
        String dir = f.getParent();
        settings.set(dir,"SaveActionImpl","previousSaveLocation");
    }

    @Override public void actionPerformed() {
        saveFileClicked();
    }
}
