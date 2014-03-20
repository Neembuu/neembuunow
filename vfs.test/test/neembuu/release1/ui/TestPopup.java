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



import java.awt.event.*;
import javax.swing.*;
/**
 *
 * @author Shashank Tulsyan
 */
public class TestPopup {

    public static void main(String[] args) {

        Runnable r = new Runnable() {
            public void run() {
                final JButton b = new JButton("Pop Up");

                final JPopupMenu menu = new JPopupMenu("Menu");
                menu.add("A");
                final JMenuItem jmi = new JMenuItem( "Hidabel ");
                menu.add("B");
                menu.add(jmi);
                menu.add("C");
                b.addActionListener( new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        if(Math.random()>0.5){
                            jmi.setVisible(false);
                        }else jmi.setVisible(true);
                        menu.show(b, b.getWidth()/2, b.getHeight()/2);
                    }
                } );
                JOptionPane.showMessageDialog(null,b);
            }
        };
        SwingUtilities.invokeLater(r);
    }

}
