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

import java.awt.Color;
import neembuu.swing.HiddenBorderButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import neembuu.release1.api.ui.actions.CloseAction;
import neembuu.release1.api.ui.actions.ExpandAction;
import neembuu.release1.api.ui.actions.ForceDownloadAction;
import neembuu.release1.api.ui.actions.SaveAction;
import neembuu.release1.ui.MainPanel;
import neembuu.util.Throwables;

/**
 *
 * @author Shashank Tulsyan
 */
final class RightControlsPanel {
    
    private final JButton saveBtn;
    private final JButton crossBtn;
    private final JButton downBtn;
    
    private final JButton forceBtn;
    
    private final JPanel panel;
    
    private final ImageIcon fd_rollover = new ImageIcon(RightControlsPanel.class.getResource("/neembuu/release1/ui/images/forceDownload.png"));
    private final ImageIcon fd_normal = new ImageIcon(new BufferedImage(fd_rollover.getIconWidth(),fd_rollover.getIconHeight(),BufferedImage.TYPE_INT_ARGB));
    private final ImageIcon fd_active = new ImageIcon(RightControlsPanel.class.getResource("/neembuu/release1/ui/images/forceDownloading.png"));
    
    private volatile ForceDownloadAction fda;

    RightControlsPanel() {
        panel = new JPanel();
        saveBtn = HiddenBorderButton.make(RightControlsPanel.class,"/neembuu/release1/ui/images/save.png", "/neembuu/release1/ui/images/save_s.png",false);
        crossBtn = HiddenBorderButton.make(RightControlsPanel.class,"/neembuu/release1/ui/images/cross.png", "/neembuu/release1/ui/images/cross_s.png",false);
        downBtn = HiddenBorderButton.make(RightControlsPanel.class,"/neembuu/release1/ui/images/down.png", "/neembuu/release1/ui/images/down_s.png",false);
        
        forceBtn = makeForceBtn();
        
        saveBtn.setBounds(0, 8, 16, 24);
        forceBtn.setBounds(17, 8, 20, 24);
        crossBtn.setBounds(37, 8, 12, 24);
        downBtn.setBounds(49, 8, 15, 24);
        
        panel.add(saveBtn);
        panel.add(forceBtn);
        panel.add(crossBtn);
        panel.add(downBtn);
        
        saveBtn.setToolTipText("Save a copy");
        saveBtn.setVisible(false);
        crossBtn.setToolTipText("Close");
        downBtn.setToolTipText("Details");
        forceBtn.setToolTipText("Force Download");
        forceBtn.setVisible(false);// Implementation is not complete
        // not intended for this version
    }

    private JButton makeForceBtn(){
        final JButton forceDownload = new JButton();
        forceDownload.setContentAreaFilled(false); //this is the piece of code you needed
        forceDownload.setBorderPainted(false);
        forceDownload.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                Throwables.start(new Runnable() {@Override public void run() {
                    if(fda==null)
                        return;
                    fda.forceDownload(!fda.isForceDownloading());

                    SwingUtilities.invokeLater(new Runnable(){@Override public void run() {
                        forceDownloadButtonStateChanged();
                    }});}},"ForceDownload action thread",true);}});
        forceDownload.setBackground(Color.WHITE);
        forceDownload.setIcon(fd_normal);
        forceDownload.setRolloverIcon(fd_rollover);
        return forceDownload;
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

    public JButton getForceBtn() {
        return forceBtn;
    }
    
    public final void forceDownloadButtonStateChanged(){
        ForceDownloadAction fda1 = RightControlsPanel.this.fda;
        if(!fda1.isForceDownloading()){
            forceBtn.setIcon(fd_normal);
            forceBtn.setToolTipText("Force Download");
            forceBtn.setRolloverIcon(fd_rollover);
        }else{
            forceBtn.setIcon(fd_active);
            forceBtn.setToolTipText("Force Downloading");
            forceBtn.setRolloverIcon(fd_active);
        }
    }
    
    static RightControlsPanel makeRightControlPanel(){
        return new RightControlsPanel();
    }
    
    
    void initActions(
            final ExpandAction expandContractPressed, 
            final SaveAction saveFileClicked, 
            final CloseAction closeAction,
            final ForceDownloadAction fda){
        downBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                expandContractPressed.actionPerformed(); }});
        saveBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                saveFileClicked.actionPerformed(); }});
        crossBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                closeAction.actionPerformed(); }});
        this.fda =fda;
    }
}
