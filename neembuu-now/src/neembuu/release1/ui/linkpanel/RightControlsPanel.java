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

import neembuu.swing.HiddenBorderButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import neembuu.release1.api.ui.actions.CloseAction;
import neembuu.release1.api.ui.actions.ExpandAction;
import neembuu.release1.api.ui.actions.SaveAction;

/**
 *
 * @author Shashank Tulsyan
 */
final class RightControlsPanel {
    
    private final JButton saveBtn;
    private final JButton crossBtn;
    private final JButton downBtn;
    
    private final JPanel panel;

    RightControlsPanel() {
        panel = new JPanel();
        saveBtn = HiddenBorderButton.make(this,"../images/save.png", "../images/save_s.png",false);
        crossBtn = HiddenBorderButton.make(this,"../images/cross.png", "../images/cross_s.png",false);
        downBtn = HiddenBorderButton.make(this,"../images/down.png", "../images/down_s.png",false);
        
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
        return new RightControlsPanel();
    }
    
    
    void initActions(
            final ExpandAction expandContractPressed, 
            final SaveAction saveFileClicked, 
            final CloseAction closeAction){
        downBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                expandContractPressed.actionPerformed(); }});
        saveBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                saveFileClicked.actionPerformed(); }});
        crossBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                closeAction.actionPerformed(); }});
    }
}
