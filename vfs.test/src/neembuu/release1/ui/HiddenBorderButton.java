/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolTip;
import javax.swing.Painter;
import javax.swing.UIDefaults;

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
