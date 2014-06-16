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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import neembuu.release1.ui.InitLookAndFeel;

/**
 *
 * @author Shashank Tulsyan
 */
public class JFrameDecorator {
    public JFrame fr;
    private final FrameDecoration fd;
    private final MouseHandler mh;

    public JFrameDecorator(final JFrame fr) {
        fr.setUndecorated(true);
        fr.setSize(100, 100);
        fd = new FrameDecoration(make());
        fd.getCustomJFrame().b3().setVisible(false);
        fd.getCustomJFrame().b2().setVisible(false);
        
        fd.getCustomJFrame().b1().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                for(WindowListener wl :  fr.getWindowListeners()){
                    wl.windowClosing(new WindowEvent(fr, WindowEvent.WINDOW_CLOSING));
                }
            }});
        this.fr = fr;
        fr.getContentPane().add(fd);
        
        FDCI fdci = new FDCI(fd, fr);
        mh = new MouseHandler(fdci);
        
        fd.getCustomJFrame().frameOutside().addMouseMotionListener(mh);
        fd.getCustomJFrame().frameOutside().addMouseListener(mh);
        fd.getCustomJFrame().frameBorder().addMouseMotionListener(mh.normalize(3, 3));
        fd.getCustomJFrame().frameBorder().addMouseListener(mh.normalize(3, 3));
        fd.getCustomJFrame().headerRegion().addMouseMotionListener(mh.normalize(3, 3));
        fd.getCustomJFrame().headerRegion().addMouseListener(mh.normalize(3, 3));
    }

    public FrameDecoration getFrameDecoration() {
        return fd;
    }

    public JFrame getJFrame() {
        return fr;
    }
    
    public interface ContentArea_add_callback { 
        void contentArea_add(JPanel toAdd);
    }
    
    public ContentArea_add_callback make(){
        return new ContentArea_add_callback(){
            @Override public void contentArea_add(JPanel toAdd) {
                System.out.println("adding");
                toAdd.addMouseListener(mh.normalize(3, 3));
                toAdd.addMouseMotionListener(mh.normalize(3, 3));
            }
        };
    }
            
    public static void main(String[] args) {
        InitLookAndFeel.init();
        JFrameDecorator jfd = new JFrameDecorator(new JFrame("Neembuu"));
        jfd.getJFrame().setLocation(200, 200);
        jfd.getJFrame().setVisible(true);
    }
}
