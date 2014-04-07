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

package neembuu.release1.defaultImpl.restore_previous;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import javax.swing.SwingUtilities;
import neembuu.diskmanager.DiskManager;
import neembuu.diskmanager.FindPreviousSessionCallback;
import neembuu.diskmanager.Session;
import neembuu.release1.api.linkgroup.LinkGroup;
import neembuu.release1.api.linkgroup.LinkGroupMakers;
import neembuu.release1.api.ui.LinkGroupUICreator;
import neembuu.release1.ui.NeembuuUI;

/**
 *
 * @author Shashank Tulsyan
 */
public class RestorePreviousSessionImpl implements FindPreviousSessionCallback {
    private final DiskManager dm;
    private final NeembuuUI nui;
    private final LinkGroupUICreator linkGroupUICreator;

    public RestorePreviousSessionImpl(DiskManager dm, LinkGroupUICreator linkGroupUICreator,NeembuuUI nui) {
        this.dm = dm; this.nui = nui;
        this.linkGroupUICreator = linkGroupUICreator;
    }
    
    
    public void checkAndRestoreFromPrevious(){
        dm.findPreviousSessions(this);
    }

    @Override public Action foundSession(Session s) {
        if(!s.restoredCorrectly()){
            return Action.Delete;
        }
        return Action.Keep;
    }

    @Override public Action foundResource(Path p) {
        return Action.Delete; // resources are currently meaningless and not used
    }

    @Override
    public void done(Exception a, boolean success, List<Session> results) {
        if(a!=null)a.printStackTrace();
        if(!success)return;
        
        final List<LinkGroup> lgs = new LinkedList<>();
        for (Session session : results) {
            try{
                LinkGroup lg = LinkGroupMakers.restore(session);
                lgs.add(lg);
            }catch(Exception axz){
                deleteFailedSessions(session);
            }
        }
        linkGroupUICreator.createUIFor(lgs);
        
    }
    
    private void deleteFailedSessions(Session s){
        try {
            s.delete();
        } catch (Exception e) {/*ignore*/ }
    }
    
}
