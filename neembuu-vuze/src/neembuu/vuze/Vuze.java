/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.vuze;

import org.gudy.azureus2.plugins.PluginManager;

/**
 *
 * @author Shashank Tulsyan
 */
public class Vuze {
    private final PluginManager pm;

    public Vuze(PluginManager pm) {
        this.pm = pm;
    }

    public PluginManager getPluginManager() {
        return pm;
    }
    
}
