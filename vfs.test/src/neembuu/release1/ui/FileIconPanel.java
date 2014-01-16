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
public class FileIconPanel {
    
    
    JPanel getFileIconPanelWithButton(ActionListener openVirtualFile) {
        JPanel panel = new JPanel();
        final TintedGreyScaledImage image = TintedGreyScaledImage.make("/neembuu/release1/ui/images/vlc.png",false);
        makeOpenButton(panel,image.getTintedImage(Colors.TINTED_IMAGE ), image.getBaseImage(), openVirtualFile);
        return panel;
    }
    
    HiddenBorderButton openButton = null;
    
    void makeOpenButton(JPanel panel, Icon bw, Icon clr, ActionListener openVirtualFile){
        if(openButton!=null){
            panel.remove(openButton);
        }
        openButton = HiddenBorderButton.make(bw,clr,false);
        openButton.setBounds(0, 0, 32, 32);
        openButton.setToolTipText("Click to Open/Watch");
        panel.add(openButton);
        if(openVirtualFile==null){
            throw new NullPointerException();
        }
        openButton.addActionListener(openVirtualFile);        
    }
}
