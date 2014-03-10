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

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import neembuu.rangearray.Range;
import neembuu.rangearray.UIRangeArrayAccess;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.release1.Main;
import neembuu.release1.api.VirtualFile;
import neembuu.release1.api.ui.access.LowerControlsUIA;
import neembuu.release1.api.ui.actions.ConnectionActions;

/**
 *
 * @author Shashank Tulsyan
 */
public class ConnectionActionsImpl implements ConnectionActions {

    private final LowerControlsUIA ui;

    public ConnectionActionsImpl(LowerControlsUIA ui) {
        this.ui = ui;
    }
    
    VirtualFile vf;

    @Override
    public void next(ActionEvent e) {
        UIRangeArrayAccess regions = vf.getConnectionFile().getRegionHandlers();
        if (regions.isEmpty()) {
            return;
        }
        Range initial = ui.progress().getSelectedRange();
        initial = getClosestRange(initial);
        Range next = regions.getNext(initial);
        ui.progress().switchToRegion(next);
    }

    @Override
    public void previous(ActionEvent e) {
        UIRangeArrayAccess regions = vf.getConnectionFile().getRegionHandlers();
        if (regions.isEmpty()) {
            return;
        }
        Range initial = ui.progress().getSelectedRange();
        initial = getClosestRange(initial);
        Range previous = regions.getPrevious(initial);
        ui.progress().switchToRegion(previous);
    }

    @Override
    public void kill(ActionEvent e) {
        Range selection = ui.progress().getSelectedRange();
        if (selection == null) {
            throw new RuntimeException("Null connection was selected. The kill button should automatically disable if no connection is selected");
        }
        UIRangeArrayAccess regions = vf.getConnectionFile().getRegionHandlers();
        selection = regions.getUnsynchronized(selection.ending());
        try {
            ((neembuu.vfs.readmanager.impl.BasicRegionHandler) selection.getProperty()).
                    getConnection().abort();
        } catch (Exception any) {
            Main.getLOGGER().log(Level.SEVERE, "Connection killing exception", any);
        }
    }

    private Range getClosestRange(Range initial) {
        UIRangeArrayAccess regions = vf.getConnectionFile().getRegionHandlers();
        UnsyncRangeArrayCopy unsyncFncCopy = regions.tryToGetUnsynchronizedCopy();

        if (initial == null) {
            return regions.getFirst();
        }
        long ending = initial.ending();
        initial = regions.getUnsynchronized(initial.ending());

        if (initial != null) {
            return initial;
        }
        if (unsyncFncCopy.size() == 0) {
            return null;
        }
        Range closest = unsyncFncCopy.get(0);
        long dmin = ending - closest.ending();
        for (int i = 0; i < unsyncFncCopy.size(); i++) {
            Range range = unsyncFncCopy.get(i);
            long d = ending - range.ending();
            if (d < 0) {
                break;
            }
            if (d < dmin) {
                dmin = d;
                closest = range;
            }
        }
        return closest;
    }

}
