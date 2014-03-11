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

package neembuu.release1.ui.linkpanel;

import neembuu.release1.ui.*;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Shashank Tulsyan
 */
final class FileIconPanel {
    
    FileIconPanel(){
        final TintedGreyScaledImage image = TintedGreyScaledImage.make("/neembuu/release1/ui/images/vlc.png",false);
        
        Icon bw = image.getTintedImage(Colors.TINTED_IMAGE );
        Icon clr = image.getBaseImage();
        
        openButton = HiddenBorderButton.make(bw,clr,false);
        openButton.setBounds(0, 0, 32, 32);
        openButton.setToolTipText("Click to Open/Watch");
        jPanel.add(openButton);
        
    }
    
    private HiddenBorderButton openButton = null;
    
    private final JPanel jPanel = new JPanel();
    
    private final JLabel captionLabel = new JLabel("1080p");

    public HiddenBorderButton getOpenButton() {
        return openButton;
    }

    public JPanel getJPanel() {
        return jPanel;
    }
    
    public String getCaption() {
        return captionLabel.getText();
    }

    public void setCaption(String caption) {
        captionLabel.setText(caption);
    }

}
