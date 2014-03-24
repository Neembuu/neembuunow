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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.BoxLayout;
import neembuu.release1.ui.*;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import neembuu.release1.api.ui.actions.OpenAction;

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
        
        relayout();
    }
    
    private void relayout(){
        JLayeredPane jlp = new JLayeredPane();
        jlp.setBounds(0, 0, 32, 32);
        
        if(captionLabel.getText()==null || captionLabel.getText().length()==0){
            jlp.add(openButton);
            jPanel.add(jlp);
            return;
        }

        JPanel lbbg = new JPanel(new BorderLayout());
        lbbg.setBackground(new Color(1,1,1,0.9f));
        lbbg.add(captionLabel,BorderLayout.CENTER);
        lbbg.setBounds(0, 10, 32, 12);
        jlp.add(lbbg);
        
        jlp.add(openButton);
        jPanel.add(jlp);
        
        jPanel.setVisible(false);
        jPanel.setVisible(true);
    }
    
    private final HiddenBorderButton openButton;
    
    private final JPanel jPanel = new JPanel();
    
    private JLabel captionLabel = new JLabel((String)null,SwingConstants.CENTER);

    public HiddenBorderButton getOpenButton() {
        return openButton;
    }
    
    public void setVisible(boolean f){
        openButton.setVisible(f);
        captionLabel.setVisible(f);
    }
    

    public JPanel getJPanel() {
        return jPanel;
    }
    
    public String getCaption() {
        return captionLabel.getText();
    }
    
    public void setCaption(String caption) {
        jPanel.removeAll();
        captionLabel = new JLabel(caption,SwingConstants.CENTER);
        relayout();
    }
    
    public void setOpenAction(final OpenAction oa){
        openButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                oa.actionPerformed();
            } });
    }

}
