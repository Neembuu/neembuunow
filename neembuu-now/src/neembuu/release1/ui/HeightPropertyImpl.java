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

package neembuu.release1.ui;

import java.util.LinkedList;
import neembuu.release1.api.ui.HeightProperty;

/**
 *
 * @author Shashank Tulsyan
 */
public class HeightPropertyImpl implements HeightProperty{
    private int value = 0;
    
    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int v) {
        final int oldValue = this.value;
        this.value = v;
        for (Listener listener : listeners) {
            listener.changed(this, oldValue, value);
        }
    }

    private final LinkedList<Listener> listeners = new LinkedList<Listener>();
    
    @Override
    public void addListener(Listener l) {
        listeners.add(l);
    }

    @Override
    public void removeListener(Listener l) {
        listeners.remove(l);
    }
    
}
