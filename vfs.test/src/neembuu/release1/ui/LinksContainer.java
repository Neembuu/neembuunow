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
import neembuu.release1.api.LinkUI;
import neembuu.release1.api.LinkUIContainer;

/**
 *
 * @author Shashank Tulsyan
 */
public final class LinksContainer implements LinkUIContainer {
    
    private void animateShrinkActionPerformed() {
        adjustHeightOfMainWindow(1);
        adjustHeightOfLinksSection(1f);
    }
    
    private final ArrayList<LinkUI> linkPanels = new ArrayList<LinkUI>();
    //private final ArrayList<Constraint> constraints = new ArrayList<Constraint>();
    private final MainPanel mp;
    private final JFrame mainFrame;
    private final JPanel linksPanel;
    
    LinksContainer(MainPanel mp, JFrame mainFrame) {
        this.mp = mp;
        this.mainFrame = mainFrame;
        linksPanel = mp.linksPanel;
    }
    
    public void addLinkUI(LinkUI lpI){
        addLinkUI(lpI, -1);
    }
    
    public void addLinkUI(LinkUI lpI, int index){
        lpI.initLinkUIContainer(this);
        if(index< 0){
            linkPanels.add(lpI);
        }else {
            linkPanels.add(index,lpI);
        }
        updateLayout();
    }
    
    /*public void removeLinkPanel(LinkPanel lp){
        lp.lc = null;
        linkPanels.remove(lp);
        linksPanel.remove(lp);
        updateLayout();
    }*/
    
    public void setToInitialHeight(){
        adjustHeightOfLinksSection(1);
        adjustHeightOfMainWindow(1);
    }
    
    public void updateLayout(){        
        //doing horizontal alignment
        GroupLayout linksPanelLayout = (GroupLayout)linksPanel.getLayout();
        GroupLayout.ParallelGroup parallelGroup = linksPanelLayout.createParallelGroup();
        for (LinkUI l : linkPanels) {
            parallelGroup.addComponent(
                    l.getJComponent(), javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE);
            if(l.getContraintComponent()!=null){
                parallelGroup.addComponent(l.getContraintComponent(),javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE);
            }
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
            for (LinkUI l : linkPanels) {
                ht+=l.getH(f);
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
        
        for (LinkUI l : linkPanels) {
            sequentialGroup
                .addComponent(l.getJComponent(), javax.swing.GroupLayout.DEFAULT_SIZE, l.getMinH(), l.getH(f));
            if(l.getContraintComponent()==null){
                sequentialGroup.addGap(bottom, bottom, bottom);
            }else {
                sequentialGroup.addComponent(l.getContraintComponent(),javax.swing.GroupLayout.DEFAULT_SIZE,10,19);
            }

        }
        linksPanelLayout.setVerticalGroup(linksPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(sequentialGroup));
    }

    @Override
    public void removeLinkUI(LinkUI uI) {
        uI.uninitLinkUIContainer(this);
        linkPanels.remove(uI);
        linksPanel.remove(uI.getJComponent());
        updateLayout();
    }

    @Override
    public void animateShrinkActionPerformed(LinkUI source) {
        animateShrinkActionPerformed();
    }

    @Override
    public boolean contains(LinkUI uI) {
        return linkPanels.contains(uI);
    }
    
    
}
