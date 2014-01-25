/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
