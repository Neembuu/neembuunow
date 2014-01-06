/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui;

import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Shashank Tulsyan
 */
public final class LinksContainer {
    public void animateShrinkActionPerformed() {
        adjustHeightOfMainWindow(1);
        adjustHeightOfLinksSection(1f);
    }
    
    private final ArrayList<LinkPanel> linkPanels = new ArrayList<LinkPanel>();
    private final MainPanel mp;
    private final JFrame mainFrame;
    private final JPanel linksPanel;
    
    LinksContainer(MainPanel mp, JFrame mainFrame) {
        this.mp = mp;
        this.mainFrame = mainFrame;
        linksPanel = mp.linksPanel;
    }
    
    public void addLinkPanel(LinkPanel lp){
        lp.lc = this;
        linkPanels.add(lp);
        updateLayout();
    }
    
    public void removeLinkPanel(LinkPanel lp){
        lp.lc = null;
        linkPanels.remove(lp);
        linksPanel.remove(lp);
        updateLayout();
    }
    
    public void setToInitialHeight(){
        adjustHeightOfLinksSection(1);
        adjustHeightOfMainWindow(1);
    }
    
    public void updateLayout(){        
        //doing horizontal alignment
        GroupLayout linksPanelLayout = (GroupLayout)linksPanel.getLayout();
        GroupLayout.ParallelGroup parallelGroup = linksPanelLayout.createParallelGroup();
        for (LinkPanel linkPanel : linkPanels) {
            parallelGroup.addComponent(
                    linkPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE);
        }
        linksPanelLayout.setHorizontalGroup(linksPanelLayout.createSequentialGroup()
                .addGap(left, left, left)
                .addGroup(parallelGroup)
                .addGap(right, right, right));
        
        //doing vertical alignment
        adjustHeightOfLinksSection(1);
        adjustHeightOfMainWindow(1);
    }
    
    private final int left = 25;
    private final int right = 40;
    private final int bottom = 20;
    
    private void adjustHeightOfMainWindow(double f){
        int ht = mainFrame.getMinimumSize().height;
        if(mp.addLinksPanel.isVisible()){
            ht+=mp.addLinksPanel.getPreferredSize().height;
        }
        if(linkPanels.isEmpty()){
            mainFrame.setSize(mainFrame.getMinimumSize().width,ht);
        }
        else {
            for (LinkPanel linkPanel : linkPanels) {
                ht+=linkPanel.getH(f);
                ht+=bottom;
            }
            
            ht = Math.min(ht,mainFrame.getMaximumSize().height);
            if(ht < mainFrame.getSize().height){
                return;
            }
            int w = mainFrame.getSize().width;
            mainFrame.setSize(w,ht);
        }
    }
    
    private void adjustHeightOfLinksSection(double f){
        GroupLayout linksPanelLayout = (GroupLayout)linksPanel.getLayout();
        GroupLayout.SequentialGroup sequentialGroup = linksPanelLayout.createSequentialGroup();
        for (LinkPanel linkPanel : linkPanels) {
            sequentialGroup
                .addComponent(linkPanel, javax.swing.GroupLayout.DEFAULT_SIZE, linkPanel.getMinH(), linkPanel.getH(f))
                .addGap(bottom, bottom, bottom);
        }
        linksPanelLayout.setVerticalGroup(linksPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(sequentialGroup));
    }
    
}
