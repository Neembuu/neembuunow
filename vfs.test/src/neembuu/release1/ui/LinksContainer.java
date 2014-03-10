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
