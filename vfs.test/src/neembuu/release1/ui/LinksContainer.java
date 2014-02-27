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
import neembuu.release1.api.ui.ExpandableUI;
import neembuu.release1.api.ui.HeightProperty;
import neembuu.release1.api.ui.ExpandableUIContainer;

/**
 *
 * @author Shashank Tulsyan
 */
public final class LinksContainer implements ExpandableUIContainer {
    
    private final ArrayList<ExpandableUI> expandableUIs = new ArrayList<ExpandableUI>();
    //private final ArrayList<Constraint> constraints = new ArrayList<Constraint>();
    private final MainPanel mp;
    private final JFrame mainFrame;
    private final JPanel linksPanel;
    
    LinksContainer(MainPanel mp, JFrame mainFrame) {
        this.mp = mp;
        this.mainFrame = mainFrame;
        linksPanel = mp.linksPanel;
    }
    
    public void addUI(ExpandableUI lpI, int index){
        if(index< 0){
            expandableUIs.add(lpI);
        }else {
            expandableUIs.add(index,lpI);
        }
        lpI.heightProperty().addListener(listener);
        updateLayout();
    }
    
    public void updateLayout(){        
        //doing horizontal alignment
        GroupLayout linksPanelLayout = (GroupLayout)linksPanel.getLayout();
        GroupLayout.ParallelGroup parallelGroup = linksPanelLayout.createParallelGroup();
        for (ExpandableUI eui : expandableUIs) {
            parallelGroup.addComponent(
                    eui.getJComponent(), javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE);
            /*if(l.getContraintComponent()!=null){
                parallelGroup.addComponent(l.getContraintComponent(),javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE);
            }*/
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
        if(expandableUIs.isEmpty()){
            mainFrame.setSize(mainFrame.getMinimumSize().width,ht);
        }
        else {
            for (ExpandableUI eui : expandableUIs) {
                ht+=eui.heightProperty().getValue();
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
        
        for (ExpandableUI eui : expandableUIs) {
            sequentialGroup
                .addComponent(eui.getJComponent(), javax.swing.GroupLayout.DEFAULT_SIZE, eui.heightProperty().getValue(), eui.heightProperty().getValue());
            /*if(l.getContraintComponent()==null){
                sequentialGroup.addGap(bottom, bottom, bottom);
            }else {
                sequentialGroup.addComponent(l.getContraintComponent(),javax.swing.GroupLayout.DEFAULT_SIZE,10,19);
            }*/

        }
        linksPanelLayout.setVerticalGroup(linksPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(sequentialGroup));
    }

    @Override
    public void removeUI(ExpandableUI uI) {
        expandableUIs.remove(uI);
        linksPanel.remove(uI.getJComponent());
        updateLayout();
    }
    
    private final HeightProperty.Listener listener = new HeightProperty.Listener() {
            @Override public void changed(HeightProperty h, int oldValue, int newValue) {
                updateLayout();         
            } };
    
}
