/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api.ui.access;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import neembuu.swing.TextBubbleBorder;

/**
 *
 * @author Shashank Tulsyan
 */
public interface CloseActionUIA {
    JPanel overlay();
    TextBubbleBorder border();
    JPanel rightControlsPanel();
    JLabel fileNameLabel();
    JButton openButton();
    
    void contract();
    void repaint();
}
