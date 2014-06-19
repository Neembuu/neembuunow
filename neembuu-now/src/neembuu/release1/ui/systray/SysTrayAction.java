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

package neembuu.release1.ui.systray;

import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import neembuu.release1.api.ui.systray.SysTray;

/**
 *
 * @author Shashank Tulsyan
 */
public final class SysTrayAction implements SysTray.Action{
    private String displayName;
    private SysTray.Callback c;
    
    private final PopupMenu pm;

    public SysTrayAction(PopupMenu pm) {
        this.pm = pm;
    }
    
    @Override public final SysTray.Action displayName(String dn) {
        this.displayName = dn; return this;
    }

    @Override public final SysTray.Action callback(SysTray.Callback c) {
        this.c = c; return this;
    }

    @Override public final SysTray.Removable make() {
        if(c==null || displayName==null){
            throw new IllegalStateException("Not initialized nicely. c="+c+" dp="+displayName);
        }
        return new RMimpl();
    }
    
    private final class RMimpl implements SysTray.Removable {

        private final MenuItem mi;
        
        public RMimpl() {
            mi = new MenuItem(displayName);
            mi.addActionListener(new ActionListener() {
                @Override public void actionPerformed(ActionEvent e) {
                    c.actionPerformed();
                }
            });
            pm.add(mi);
        }
    
        @Override public final void remove() {
            pm.remove(mi);
        }
        
    }
    
}
