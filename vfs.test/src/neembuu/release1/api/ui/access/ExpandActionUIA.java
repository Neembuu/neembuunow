/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api.ui.access;

import javax.swing.JPanel;
import neembuu.rangearray.Range;
import neembuu.release1.api.ui.HeightProperty;

/**
 *
 * @author Shashank Tulsyan
 */
public interface ExpandActionUIA {
    JPanel connectionControlPane();
    JPanel graphPanel();
    JPanel sizeAndProgressPane();
    JPanel hiddenStatsPane();
    
    void initGraph(boolean findFirst);
    
    short ht_smallest();
    short ht_medium();
    short ht_tallest();
    
    HeightProperty getHeight();
}
