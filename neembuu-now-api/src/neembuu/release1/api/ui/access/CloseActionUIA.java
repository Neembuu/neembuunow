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

import java.awt.Color;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Shashank Tulsyan
 */
public interface CloseActionUIA {
    void overlay_setVisible(boolean show);
    void indefiniteOverlay(boolean show);
    void border_setColor(Color c);
    JPanel rightControlsPanel();
    JLabel fileNameLabel();
    OpenButton openButton();
    
    void contract();
    void repaint();
    
    JLabel fileSizeLabel();
    
    public interface OpenButton {
        void setVisible(boolean v);
        void setIcon_silent(Icon bw);
        void setIcon_active(Icon clr);
        void setCaption(String caption);
        String getCaption();
    }
}
