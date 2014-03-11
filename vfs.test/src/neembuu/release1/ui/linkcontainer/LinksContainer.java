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

package neembuu.release1.ui.linkcontainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import neembuu.release1.api.ui.ExpandableUI;
import neembuu.release1.api.ui.HeightProperty;
import neembuu.release1.api.ui.ExpandableUIContainer;
import neembuu.release1.ui.MainPanel;

/**
 *
 * @author Shashank Tulsyan
 */
public final class LinksContainer implements ExpandableUIContainer {
    
    final ArrayList<ExpandableUI> expandableUIs = new ArrayList<ExpandableUI>(){
        @Override public boolean add(ExpandableUI e) {
            linkContainerHeightProperty.notifyChange();
            return super.add(e);}
        @Override public boolean addAll(int index, Collection<? extends ExpandableUI> c) {
            linkContainerHeightProperty.notifyChange();
            return super.addAll(index, c);}
        @Override public boolean remove(Object o) {
            linkContainerHeightProperty.notifyChange();
            return super.remove(o); }
    };
    
    private final MainPanel mp;
    private final JPanel linksPanel;
    
    private final LCHeightProperty linkContainerHeightProperty = new LCHeightProperty(this);
    
    public LinksContainer(MainPanel mp, JPanel linksPanel) {
        this.mp = mp;
        this.linksPanel = linksPanel;
    }
    
    public void addUI(ExpandableUI lpI, int index){
        if(index< 0){
            expandableUIs.add(lpI);
        }else {
            expandableUIs.add(index,lpI);
        }
        lpI.heightProperty().addListener(listener_of_height_of_individual_linkuis);
        updateLayout();
    }
    
    private void updateLayout(){        
        //doing horizontal alignment
        GroupLayout linksPanelLayout = (GroupLayout)linksPanel.getLayout();
        GroupLayout.ParallelGroup parallelGroup = linksPanelLayout.createParallelGroup();
        for (ExpandableUI eui : expandableUIs) {
            parallelGroup.addComponent(
                    eui.getJComponent(), javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE);
        }
        linksPanelLayout.setHorizontalGroup(linksPanelLayout.createSequentialGroup()
                .addGap(left, left, left)
                .addGroup(parallelGroup)
                .addGap(right, right, right));
        adjustHeightOfLinksSection(1);
    }
    
    
    
    private final int left = 25;
    private final int right = 40;
    final int bottom = 20;
    
    private void adjustHeightOfLinksSection(double f){
        GroupLayout linksPanelLayout = (GroupLayout)linksPanel.getLayout();
        GroupLayout.SequentialGroup sequentialGroup = linksPanelLayout.createSequentialGroup();
        
        for (ExpandableUI eui : expandableUIs) {
            sequentialGroup
                .addComponent(eui.getJComponent(), javax.swing.GroupLayout.DEFAULT_SIZE, eui.heightProperty().getValue(), eui.heightProperty().getValue())
                .addGap(bottom, bottom, bottom);
        }
        linksPanelLayout.setVerticalGroup(linksPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(sequentialGroup));
    }

    @Override
    public void removeUI(ExpandableUI uI) {
        expandableUIs.remove(uI);
        uI.heightProperty().removeListener(listener_of_height_of_individual_linkuis);
        linksPanel.remove(uI.getJComponent());
        updateLayout();
    }

    @Override public HeightProperty heightProperty() { return linkContainerHeightProperty; }
    
    private final HeightProperty.Listener listener_of_height_of_individual_linkuis = new HeightProperty.Listener() {
            @Override public void changed(HeightProperty h, int oldValue, int newValue) {
                updateLayout(); // to layout link container
                linkContainerHeightProperty.notifyChange(); // to layout main frame
            }};
}
