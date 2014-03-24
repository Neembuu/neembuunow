/*
 *  Copyright (C) 2014 Shashank Tulsyan
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.release1.api.ui.access;

import javax.swing.JPanel;
import neembuu.release1.api.ui.HeightProperty;

/**
 *
 * @author Shashank Tulsyan
 */
public interface ExpandActionUIA {
    JPanel connectionControlPane();
    JPanel graphPanel();
    JPanel hiddenStatsPane();
    
    void initGraph(boolean findFirst);
    void initVariants();
    
    short ht_smallest();
    short ht_medium();
    short ht_tallest();
    
    HeightProperty getHeight();
    
    void setVisibleProgress(boolean b);
    void setVisibleVariantProgress(boolean b);
    void setVisibleFileSize(boolean b);
    void setVariantChooser(boolean b);
}
