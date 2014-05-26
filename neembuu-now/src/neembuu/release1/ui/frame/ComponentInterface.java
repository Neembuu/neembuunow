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

import java.awt.Cursor;
import java.awt.Rectangle;

/**
 *
 * @author Shashank Tulsyan
 */
public interface ComponentInterface {
    Rectangle getBounds();
    void setLocation(int x, int y);
    void setCursor(Cursor c);
    int minimumWidth();
    int minimumHeight();
    boolean resizable();
    void setBounds(int x,int y,int w,int h);
    
    int getWidth();
    int getHeight();
    
    void setVisible(boolean v);
    
    int getLocation_x();
    int getLocation_y();
}
