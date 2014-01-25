/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import neembuu.release1.api.ui.ExpansionState;
import neembuu.swing.TextBubbleBorder;

/**
 *
 * @author Shashank Tulsyan
 */
public interface UIAccess {
    JPanel actualContentsPanel();
    JToggleButton changeDownloadModeButton();
    JButton delete();
    JLabel fileNameLabel();
    JPanel fileNamePane();
    JLabel fileSizeLabel();
    JPanel graphPanel();
    JPanel hiddenStatsPane();
    JButton killConnectionButton();
    JLayeredPane layeredPane();
    JButton linkEditButton();
    JButton nextConnectionButton();
    JPanel overlay();
    JButton previousConnectionButton();
    JPanel progressBarPanel();
    JLabel progressPercetLabel();
    JButton reEnableButton();
    RightControlsPanel rightControlsPanel();
    JLabel selectedConnectionLabel();
    JPanel sizeAndProgressPane();
    JPanel vlcPane();
    
    TextBubbleBorder border();
    
    void setExpansionState(ExpansionState es);
    void repaintUI();
}
