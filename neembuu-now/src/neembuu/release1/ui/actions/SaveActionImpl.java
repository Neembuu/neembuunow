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
import neembuu.release1.api.IndefiniteTask;
import neembuu.release1.api.file.NeembuuFile;
import neembuu.release1.api.file.Saveable;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.release1.api.settings.Settings;
import neembuu.release1.api.ui.IndefiniteTaskUI;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.Message;
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
    private final IndefiniteTaskUI itui;
    private String warning = null;
    
    public SaveActionImpl(MainComponent mainComponent,Settings settings, IndefiniteTaskUI itui) {
        this.mainComponent = mainComponent; this.settings = settings; this.itui = itui;
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
        IndefiniteTask itui1 = itui.showIndefiniteProgress("Saving file");
        itui1.done(true, 10000); // not done, but close after 10seconds
        try{
            connectionFile.saveACopy(outputFilePath);
        }catch(Exception a){
            mainComponent.newMessage().error()
                .setMessage(a.getMessage())
                .setTitle("Could not save file")
                .show();
            LoggerUtil.L().log(Level.SEVERE, "Could not save file",a);
        }
        itui1.done(true,0);//actually done
    }
    
    private void saveFileClicked(){
        Object[]options={"Yes, I understand","No","Tell me more"};
        Object selected = mainComponent.newMessage()
                .setTitle("Are you sure you want to save a copy of this file?")
                .setEmotion(Message.Emotion.NOT_SURE)
                .setTimeout(15000) //15seconds
                .setMessage("This file is in Neembuu and maybe viewed\n"
                    +"offline whenever you like.You may delete this\n"
                    +"file from Neembuu whenever you like.\n"
                    +"\n"
                    +"By saving a copy of this, you might \n"
                    +"unknowingly violate someone's copyright.")
                .ask(options,1);
        if(selected!=options[0]){
            if(selected==options[2]){
                try{java.awt.Desktop.getDesktop().browse(
                    new java.net.URL("http://neembuu.com/now/help/#save").toURI()
                );}catch(Exception a){}
            }
            return;
        }
        doSave();
    }
    
    private void doSave(){
        String p = getSaveLocation();
        
        final javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setSelectedFile(new File(p+File.separator+
                connectionFile.getMinimumFileInfo().getName()));
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
        int retVal = fileChooser.showSaveDialog(mainComponent.getJFrame());
        if(retVal == javax.swing.JFileChooser.APPROVE_OPTION){
            File f = fileChooser.getSelectedFile().getAbsoluteFile();
            if(!isSelectedFileOk(f)){
                return;
            }
            saveAction(f);
            
            Throwables.start(new Runnable(){@Override public void run() {try{
                    setSaveLocation(fileChooser);
                }catch(Exception a){throw new RuntimeException(a);}}});
        }else {

        }
    }
    
    private boolean isSelectedFileOk(File f){
        if(f.exists()){
            Object[]options={"Copy & Replace","Try a different name","Cancel"};
            Object selected = mainComponent.newMessage()
                .setTitle("Copy/Save File")
                .setEmotion(Message.Emotion.NOT_SURE)
                .setTimeout(15000) //15seconds
                .setMessage("There is already a file with the  \n"
                        + "same name in this location.")
                .ask(options,2);
            
            if(selected==options[0]){
                try{
                    boolean fx = f.delete();
                    if(!fx)throw new java.io.IOException("Could not replace the original the file.");
                }catch(Exception a){
                    mainComponent.newMessage().error()
                        .setMessage(a.getMessage())
                        .setTitle("Could not save file")
                        .setTimeout(5000)
                        .show();
                    return false; // quit <= IOException
                }return true; // proceed with saving normally <= old file deleted
            }else if(selected==options[1]){
                doSave();
                return false; // quit <= file save already reattempted, now 
            }
            return false; // quit <= user selected cancel, or timeout
        }
        return true;// proceed with saving normally <= no file with similar name found
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
