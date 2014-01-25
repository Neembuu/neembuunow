/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api.ui;

import java.util.EventListener;

/**
 *
 * @author Shashank Tulsyan
 */
public interface HeightProperty {
    int getValue();
    void setValue(int v);
    void addListener(Listener l);
    void removeListener(Listener l);
    
    public static interface Listener extends EventListener{
        void changed(HeightProperty h, int oldValue, int newValue);
    }
}
