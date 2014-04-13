/*
 * Copyright (C) 2014 Shashank Tulsyan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MSelectableRCHANTABILITY or FITNSelectableSS FOR A PARTICULAR PURPOSSelectable.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.release1.ui.linkpanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import neembuu.release1.api.ui.linkpanel.VariantSelector;

/**
 *
 * @author Shashank Tulsyan
 */
public class VariantSelectorImpl implements VariantSelector{

    private final JButton component;
    private final List<Selectable> l = new ArrayList<Selectable>(){
        @Override public boolean add(Selectable e) {
            if(isEmpty()){ actionPerformed(e); }
            return super.add(e);
        }

        @Override public void add(int index, Selectable element) {
            if(isEmpty()){ actionPerformed(element); }
            super.add(index,element);
        }
    };
    
    private Selectable selectedItem ; 

    public VariantSelectorImpl(JButton component) {
        this.component = component;
    }
    
    @Override public List<Selectable> getItems() { return l; }

    @Override public Selectable getSelectedItem() { return selectedItem; }

    @Override public void actionPerformed() {
        final JPopupMenu menu = new JPopupMenu();
        
        for (final Selectable element : l) {
            final JMenuItem jmi = new JMenuItem(element.getText());
            jmi.addActionListener(new ActionListener() {
                @Override public void actionPerformed(ActionEvent e) {
                    VariantSelectorImpl.this.actionPerformed(element);
                }
            });
            menu.add(jmi);
        }
        menu.show(component,0,component.getHeight());
    }
    
    @Override public void actionPerformed(Selectable element) {
        if(selectedItem!=null )selectedItem.unSelect();
        selectedItem = element;
        component.setText(element.getSmallText());
        element.select();
    }
    
}
