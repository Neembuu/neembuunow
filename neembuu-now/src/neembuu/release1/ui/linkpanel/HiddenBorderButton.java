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
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Shashank Tulsyan
 */
final class HiddenBorderButton extends JButton{

    private Icon icon_bw;
    private Icon icon_clr;
    private boolean fillOnHover;
    
    HiddenBorderButton(Icon icon) {
        super(icon);
    }

    HiddenBorderButton(String text) {
        super(text);
    }

    public Icon getIcon_bw() {
        return icon_bw;
    }

    public void setIcon_bw(Icon icon_bw) {
        this.icon_bw = icon_bw;
        setIcon(icon_bw);
    }

    public Icon getIcon_clr() {
        return icon_clr;
    }

    public void setIcon_clr(Icon icon_clr) {
        this.icon_clr = icon_clr;
    }

    public boolean isFillOnHover() {
        return fillOnHover;
    }

    public void setFillOnHover(boolean fillOnHover) {
        this.fillOnHover = fillOnHover;
    }
    
    

    static JButton makeWithTinting(String imgLoc,Color tintingColor){
        final TintedGreyScaledImage icon;
        icon = TintedGreyScaledImage.make(imgLoc,true);
        return HiddenBorderButton.make(icon.getBaseImage(), icon.getTintedImage(tintingColor));
    }
    
    static JButton make(final String p1,final String p2,boolean fillOnHover){
        final ImageIcon i1 = new ImageIcon(TintedGreyScaledImage.class.getResource(p1));
        final ImageIcon i2 = new ImageIcon(TintedGreyScaledImage.class.getResource(p2));
        
        return make(i1, i2,fillOnHover);
    }
    
    static JButton make(final Icon icon_bw,final Icon icon_clr){
        return make(icon_bw, icon_clr, true);
    }
    
    static HiddenBorderButton make(Icon icon_bw,Icon icon_clr,boolean fillOnHover){
        final HiddenBorderButton button;
        button = new HiddenBorderButton(icon_bw);
        
        button.fillOnHover = fillOnHover;
        button.icon_bw = icon_bw;
        button.icon_clr = icon_clr;
        
        button.setContentAreaFilled(false); //this is the piece of code you needed
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                button.setIcon(button.icon_clr);
                if(button.fillOnHover)
                    button.setContentAreaFilled(true);  //when hoovered it will show borders and fill area.
            }

            @Override
            public void mouseExited(MouseEvent event) {
                button.setIcon(button.icon_bw);
                if(button.fillOnHover)
                    button.setContentAreaFilled(false); //when mouse is not on button then it will look the same.
            }
        });
        button.setBackground(Color.WHITE);
        return button;
    }
    
    static JButton make(String text){
        final HiddenBorderButton button = new HiddenBorderButton(text);
        button.setContentAreaFilled(false); //this is the piece of code you needed
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                JButton buton = (JButton) event.getSource();
                buton.setContentAreaFilled(true);  //when hoovered it will show borders and fill area.
            }

            @Override
            public void mouseExited(MouseEvent event) {
                JButton buton = (JButton) event.getSource();
                buton.setContentAreaFilled(false); //when mouse is not on button then it will look the same.
            }
        });
        button.setBackground(Color.WHITE);
        return button;
    }

}
