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

import neembuu.release1.api.file.NeembuuFile;
import neembuu.release1.api.ui.actions.FileNameClickedAction;
import neembuu.release1.api.ui.actions.OpenAction;
import neembuu.release1.api.ui.actions.ReAddAction;

/**
 *
 * @author Shashank Tulsyan
 */
public class FileNameClickedActionImpl implements ReAddAction.CallBack,FileNameClickedAction{
    private final ReAddAction addAndPlay;
    private final OpenAction openAction;

    public FileNameClickedActionImpl(ReAddAction addAndPlay, OpenAction openAction) {
        this.addAndPlay = addAndPlay;
        this.openAction = openAction;
    }
    
    private volatile NeembuuFile neembuuFile = null;
    
    @Override public void actionPerformed(){
        if(neembuuFile==null || neembuuFile.isCompletelyClosed()){
            //addAndPlay.actionPerformed(true);
            return;
        }
        openAction.actionPerformed();
    }

    @Override public void doneCreation(NeembuuFile neembuuFile) {
        this.neembuuFile = neembuuFile;
    }

}
