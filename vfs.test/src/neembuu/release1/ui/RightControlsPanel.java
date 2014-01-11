/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Shashank Tulsyan
 */
public class RightControlsPanel {
    
    JButton saveBtn;
    JButton crossBtn;
    
    
    JPanel getRightControlPanel(
            ActionListener expandContractPressed, 
            ActionListener saveFileClicked, 
            ActionListener closeAction
    ){
        JPanel panel = new JPanel();
        
        saveBtn = HiddenBorderButton.make("images/save.png", "images/save_s.png",false);
        crossBtn = HiddenBorderButton.make("images/cross.png", "images/cross_s.png",false);
        JButton downBtn = HiddenBorderButton.make("images/down.png", "images/down_s.png",false);
        
        downBtn.addActionListener(expandContractPressed);
        saveBtn.addActionListener(saveFileClicked);
        crossBtn.addActionListener(closeAction);
        
        saveBtn.setBounds(0, 8, 24, 24);
        crossBtn.setBounds(25, 8, 24, 24);
        downBtn.setBounds(50, 8, 24, 24);
        
        panel.add(saveBtn);
        panel.add(crossBtn);
        panel.add(downBtn);
        
        saveBtn.setToolTipText("Save File");
        saveBtn.setVisible(false);
        crossBtn.setToolTipText("Delete file");
        downBtn.setToolTipText("Show/Hide more controls");
        return panel;
    }
}
