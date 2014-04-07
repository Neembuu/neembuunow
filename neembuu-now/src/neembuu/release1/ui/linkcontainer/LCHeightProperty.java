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
package neembuu.release1.ui.linkcontainer;

import java.util.LinkedList;
import neembuu.release1.api.ui.linkpanel.ExpandableUI;
import neembuu.release1.api.ui.HeightProperty;

/**
 *
 * @author Shashank Tulsyan
 */
final class LCHeightProperty implements HeightProperty {

    private final LinksContainer lc;

    LCHeightProperty(LinksContainer lc) {
        this.lc = lc;
    }

    private final LinkedList<HeightProperty.Listener> listeners = new LinkedList<HeightProperty.Listener>();
    private int ht = 0;

    @Override
    public int getValue() {
        int ht_temp = 0;
        for (ExpandableUI eui : lc.expandableUIs) {
            ht_temp += eui.heightProperty().getValue();
            ht_temp += lc.bottom;
            ht_temp += 10; // extra padding
        }
        ht = ht_temp;
        return ht;
    }

    void notifyChange() {
        int oldValue = ht;
        int newValue = getValue();
        for (Listener listener : listeners) {
            listener.changed(this, oldValue, newValue);
        }
    }

    @Override
    public void setValue(int v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addListener(HeightProperty.Listener l) {
        listeners.add(l);
    }

    @Override
    public void removeListener(HeightProperty.Listener l) {
        listeners.remove(l);
    }

}
