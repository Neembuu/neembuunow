/*
 *  Copyright (C) 2014 Shashank Tulsyan
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package neembuu.release1.ui.actions;


import neembuu.release1.api.file.NeembuuFile;
import neembuu.release1.api.ui.access.CloseActionUIA;
import neembuu.release1.api.ui.linkpanel.ProgressProvider;
import neembuu.release1.api.ui.actions.ChangeDownloadModeAction;
import neembuu.release1.api.ui.actions.ReAddAction;
import neembuu.vfs.file.FileBeingDownloaded;
import neembuu.vfs.file.MinimumFileInfo;


/**
 *
 * @author Shashank Tulsyan
 */
public class ReAddActionCallBackImpl implements ReAddAction.CallBack {

    private final CloseActionUIA uia;
    private final ProgressProvider lowerControlUIA;
    private final ChangeDownloadModeAction changeDownloadModeAction;
    
    private final boolean initalizeProgress;

    public ReAddActionCallBackImpl(CloseActionUIA uia, ProgressProvider lowerControlUIA,
            ChangeDownloadModeAction changeDownloadModeAction,boolean initalizeProgress) {
        this.uia = uia; this.initalizeProgress = initalizeProgress;
        this.lowerControlUIA = lowerControlUIA;
        this.changeDownloadModeAction = changeDownloadModeAction;
    }

    @Override
    public void doneCreation(NeembuuFile neembuuFile) {

        uia.fileNameLabel().setText(neembuuFile.getMinimumFileInfo().getName());
        updateFileSizeString(neembuuFile.getMinimumFileInfo());

        if(initalizeProgress){
                lowerControlUIA.progress().init(neembuuFile.fileBeingDownloaded());
        }
        changeDownloadModeAction.init(neembuuFile.autoCompleteControls());
        
        Utils.TintedIcons tintedIcons = Utils.makeTintedIconsForFile(neembuuFile.getMinimumFileInfo().getName());
        if(tintedIcons==null){return;}
        uia.openButton().setIcon_silent(tintedIcons.unicoloredIcon);
        uia.openButton().setIcon_active(tintedIcons.coloredIcon);
    }

    private void updateFileSizeString(MinimumFileInfo fileInfo) {
        uia.fileSizeLabel().setText(Utils.makeFileSizeString(fileInfo));
    }

}
