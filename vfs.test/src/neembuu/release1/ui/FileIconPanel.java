/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JButton;
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

    public HiddenBorderButton getOpenButton() {
        return openButton;
    }

    public JPanel getJPanel() {
        return jPanel;
    }

}
