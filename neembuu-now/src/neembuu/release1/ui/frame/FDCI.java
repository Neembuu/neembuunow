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
import javax.swing.JFrame;

/**
 *
 * @author Shashank Tulsyan
 */
final class FDCI implements ComponentInterface {
    private final FrameDecoration fd;
    private final CustomJFrame cjf;
    private final JFrame jf;
    
    public FDCI(FrameDecoration fd, JFrame jf) {
        this.fd = fd; this.jf = jf;
        cjf = fd.getCustomJFrame();
        fd.getCustomJFrame().frameBorder();
        fd.getCustomJFrame().headerRegion();
    }

    @Override public Rectangle getBounds() {
        return jf.getBounds();
    }

    @Override public void setLocation(int x, int y) {
        jf.setLocation(x, y);
    }

    @Override public void setCursor(Cursor c) {
        jf.setCursor(c);
    }
    
    @Override public Cursor getCursor() {
        return jf.getCursor();
    }

    @Override public int minimumWidth() {
        return 0;
    }

    @Override public int minimumHeight() {
        return 0;
    }

    @Override public boolean resizable() {
        return jf.isResizable();
    }

    @Override public void setBounds(int x, int y, int w, int h) {
        jf.setBounds(x, y, w, h);
    }

    @Override public int getWidth() {
        return jf.getWidth();
    }

    @Override public int getHeight() {
        return jf.getHeight();
    }

    @Override public void setVisible(boolean v) {
        jf.setVisible(v);
    }

    @Override public int getLocation_x() {
        return jf.getLocation().x;
    }

    @Override public int getLocation_y() {
        return jf.getLocation().y;
    }

}
