/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api.ui.actions;

import java.awt.event.ActionListener;
import neembuu.release1.api.ui.ExpansionState;

/**
 *
 * @author Shashank Tulsyan
 */
public interface ExpandAction extends ActionListener{
    ExpansionState getExpansionState();
    
    void setExpansionState(ExpansionState es);
}
