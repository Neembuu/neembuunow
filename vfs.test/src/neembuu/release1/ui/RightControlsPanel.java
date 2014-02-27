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
final class RightControlsPanel {
    
    private final JButton saveBtn;
    private final JButton crossBtn;
    private final JButton downBtn;
    
    private final JPanel panel;

    RightControlsPanel(JButton saveBtn, JButton crossBtn, JButton downBtn, JPanel panel) {
        this.saveBtn = saveBtn;
        this.crossBtn = crossBtn;
        this.downBtn = downBtn;
        this.panel = panel;
    }

    public JButton getSaveBtn() {
        return saveBtn;
    }

    public JButton getCrossBtn() {
        return crossBtn;
    }

    public JButton getDownBtn() {
        return downBtn;
    }

    public JPanel getPanel() {
        return panel;
    }
    
    
    
    static RightControlsPanel makeRightControlPanel(){
        JPanel panel = new JPanel();
        JButton saveBtn;
        JButton crossBtn;
        JButton downBtn;
        
        saveBtn = HiddenBorderButton.make("images/save.png", "images/save_s.png",false);
        crossBtn = HiddenBorderButton.make("images/cross.png", "images/cross_s.png",false);
        downBtn = HiddenBorderButton.make("images/down.png", "images/down_s.png",false);
        
        saveBtn.setBounds(0, 8, 24, 24);
        crossBtn.setBounds(25, 8, 24, 24);
        downBtn.setBounds(50, 8, 24, 24);
        
        panel.add(saveBtn);
        panel.add(crossBtn);
        panel.add(downBtn);
        
        saveBtn.setToolTipText("Save a copy");
        saveBtn.setVisible(false);
        crossBtn.setToolTipText("Close");
        downBtn.setToolTipText("Details");
        
        return new RightControlsPanel(saveBtn, crossBtn, downBtn, panel);
    }
    
    
    void initActions(
            ActionListener expandContractPressed, 
            ActionListener saveFileClicked, 
            ActionListener closeAction){
        downBtn.addActionListener(expandContractPressed);
        saveBtn.addActionListener(saveFileClicked);
        crossBtn.addActionListener(closeAction);
    }
}
