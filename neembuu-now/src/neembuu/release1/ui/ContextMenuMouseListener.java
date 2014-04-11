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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.release1.ui;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Bozhidar Batsov
 * http://stackoverflow.com/a/2793959/2448015
 */
public final class ContextMenuMouseListener extends MouseAdapter {
    private final JPopupMenu popup = new JPopupMenu();

    private final Action cutAction;
    private final Action copyAction;
    private final Action pasteAction;
    private final Action undoAction;
    private final Action selectAllAction;
    private final Action deleteAction;
    private final Action clearAllAction;

    private JTextComponent textComponent;
    private String savedString = "";
    private Actions lastActionSelected;

    public static enum Actions { UNDO, CUT, COPY, PASTE, SELECT_ALL, DELETE, CLEAR_ALL };

    public ContextMenuMouseListener(Actions... requiredActionsAr) {
        List<Actions> requiredActions;
        if(requiredActionsAr==null){
            requiredActions = Arrays.asList(Actions.values());
            requiredActions.remove(Actions.DELETE);
            requiredActions.remove(Actions.CLEAR_ALL);
        }else{ 
            requiredActions = Arrays.asList(requiredActionsAr);
        }
        
        deleteAction = new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent e) {
                lastActionSelected = Actions.DELETE;
                savedString = textComponent.getText();
                textComponent.replaceSelection("");
            }
        };
        
        if(requiredActions.contains(Actions.DELETE)){
            popup.add(deleteAction);popup.addSeparator();
        }
        
        undoAction = new AbstractAction("Undo") {

            @Override
            public void actionPerformed(ActionEvent ae) {
                    textComponent.setText("");
                    textComponent.replaceSelection(savedString);

                    lastActionSelected = Actions.UNDO;
            }
        };

        if(requiredActions.contains(Actions.UNDO)){
            popup.add(undoAction);
            popup.addSeparator();
        }

        cutAction = new AbstractAction("Cut") {

            @Override
            public void actionPerformed(ActionEvent ae) {
                lastActionSelected = Actions.CUT;
                savedString = textComponent.getText();
                textComponent.cut();
            }
        };

        if(requiredActions.contains(Actions.CUT)){popup.add(cutAction);}

        copyAction = new AbstractAction("Copy") {

            @Override
            public void actionPerformed(ActionEvent ae) {
                lastActionSelected = Actions.COPY;
                textComponent.copy();
            }
        };

        if(requiredActions.contains(Actions.COPY)){popup.add(copyAction);}

        pasteAction = new AbstractAction("Paste") {

            @Override
            public void actionPerformed(ActionEvent ae) {
                lastActionSelected = Actions.PASTE;
                savedString = textComponent.getText();
                textComponent.paste();
            }
        };

        if(requiredActions.contains(Actions.PASTE)){popup.add(pasteAction);}
        popup.addSeparator();

        selectAllAction = new AbstractAction("Select All") {

            @Override
            public void actionPerformed(ActionEvent ae) {
                lastActionSelected = Actions.SELECT_ALL;
                textComponent.selectAll();
            }
        };

        if(requiredActions.contains(Actions.SELECT_ALL)){popup.add(selectAllAction);}
        
        
        clearAllAction = new AbstractAction("Clear all") {
            @Override
            public void actionPerformed(ActionEvent e) {
                lastActionSelected = Actions.DELETE;
                savedString = textComponent.getText();
                textComponent.setText("");
            }
        };
        
        if(requiredActions.contains(Actions.CLEAR_ALL)){
            popup.add(clearAllAction);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
            if (!(e.getSource() instanceof JTextComponent)) {
                return;
            }

            textComponent = (JTextComponent) e.getSource();
            textComponent.requestFocus();

            boolean enabled = textComponent.isEnabled();
            boolean editable = textComponent.isEditable();
            boolean nonempty = !(textComponent.getText() == null || textComponent.getText().equals(""));
            boolean marked = textComponent.getSelectedText() != null;

            boolean pasteAvailable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).isDataFlavorSupported(DataFlavor.stringFlavor);

            undoAction.setEnabled(enabled && editable && (lastActionSelected == Actions.CUT || lastActionSelected == Actions.PASTE));
            cutAction.setEnabled(enabled && editable && marked);
            copyAction.setEnabled(enabled && marked);
            pasteAction.setEnabled(enabled && editable && pasteAvailable);
            selectAllAction.setEnabled(enabled && nonempty);

            int nx = e.getX();

            if (nx > 500) {
                nx = nx - popup.getSize().width;
            }

            popup.show(e.getComponent(), nx, e.getY() - popup.getSize().height);
        }
    }
}
