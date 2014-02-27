/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api.ui.access;

import javax.swing.JPanel;
import neembuu.release1.api.ui.ExpansionState;

/**
 *
 * @author Shashank Tulsyan
 */
public interface GraphUIA {
    JPanel graphPanel();
    ExpansionState getExpansionState();
}
