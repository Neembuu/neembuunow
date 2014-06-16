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

package neembuu.release1.ui.frame;

/**
 *
 * @author Shashank Tulsyan
 */
public interface CustomJFrame {
    javax.swing.JButton b1();
    javax.swing.JButton b2();
    javax.swing.JButton b3();
    javax.swing.JPanel frameOutside();
    javax.swing.JPanel frameBorder();
    javax.swing.JPanel frameControls();
    javax.swing.JPanel headerRegion();
    void contentArea_add(javax.swing.JPanel toAdd);
    javax.swing.JLabel icon();
    javax.swing.JLabel title();
}
