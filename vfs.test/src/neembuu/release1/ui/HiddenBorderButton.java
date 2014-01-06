/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui;

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
final class HiddenBorderButton {
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
    static JButton make(final Icon icon_bw,final Icon icon_clr,final boolean fillOnHover){
        JButton button;
        button = new JButton(icon_bw); //adds resized image to button.
        button.setContentAreaFilled(false); //this is the piece of code you needed
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                JButton buton = (JButton) event.getSource();
                buton.setIcon(icon_clr);
                if(fillOnHover)
                    buton.setContentAreaFilled(true);  //when hoovered it will show borders and fill area.
            }

            @Override
            public void mouseExited(MouseEvent event) {
                JButton buton = (JButton) event.getSource();
                buton.setIcon(icon_bw);
                if(fillOnHover)
                    buton.setContentAreaFilled(false); //when mouse is not on button then it will look the same.
            }
        });
        button.setBackground(Color.WHITE);
        return button;
    }
    
    static JButton make(String text){
        JButton button = new JButton(text);
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
