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

package neembuu.vfs.connection.checks;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

/**
 *
 * @author Shashank Tulsyan
 */
public final class SeekingAbilityImpl implements SeekingAbility{
    private final LinkedList<WeakReference<Listener>> ls = new LinkedList<WeakReference<Listener>>();
    private volatile CanSeek canSeek = CanSeek.UNKNOWN;
    
    public void update(CanSeek now,long triggeringOffset){
        CanSeek old = this.canSeek;
        this.canSeek = now;
        
        for (WeakReference<Listener> wl : ls) {
            Listener l = wl.get();
            if(l!=null){
                l.stateChanged(old, now, triggeringOffset);
            }
        }
    }

    /*@Override public void reportSuccessInSeeking() {
        new Throwable("Someone report success in seeking").printStackTrace(System.err);
        canSeek = CanSeek.YES;
    }*/
    
    @Override public void addListener(Listener l) { ls.add(new WeakReference<Listener>(l)); }

    @Override public void removeListener(Listener toRemove) { 
        WeakReference<Listener> toRemoveWl = null;
        for (WeakReference<Listener> wl : ls) {
            Listener l = wl.get();
            if(l!=toRemove){
                toRemoveWl = wl;
                break;
            }
        }
        
        ls.remove(toRemoveWl);
    }
    
    @Override public CanSeek get() { return canSeek; }
}
