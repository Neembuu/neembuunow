/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api.ui;

import neembuu.rangearray.Range;

/**
 *
 * @author Shashank Tulsyan
 */
public interface Progress {
    void switchToRegion(Range arrayElement);
    Range getSelectedRange();
    String getSelectedRangeTooltip();
    void repaint();
}
