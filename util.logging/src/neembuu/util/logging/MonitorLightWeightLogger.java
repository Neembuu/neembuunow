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
package neembuu.util.logging;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import neembuu.util.logging.LightWeightLogger;

/**
 *
 * @author Shashank Tulsyan
 */
final class MonitorLightWeightLogger {
    
    static {
        staticinit();
    }

    private static void staticinit() {
        final JFrame jf = new JFrame("LightWeightLogger logs pending");
        final JLabel jl = new JLabel();
        JButton jb = new JButton("R");
        jb.setBounds(0, 0, 40, 40);
        JPanel jp = new JPanel(null);
        jb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jl.setText("" + LightWeightLogger.pendingRqs.get() + " @"
                        + ((double) LightWeightLogger.totalTime.get() / (double) LightWeightLogger.totalCnt.get() * 1d));
            }
        });
        jl.setBounds(40, 0, 100, 40);
        jp.add(jl);
        jp.add(jb);
        jf.getContentPane().add(jp);
        jf.setLocation(100, 100);
        jf.setSize(150, 80);
        jf.setVisible(true);
        jf.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                jf.dispose();
                //LightWeightLogger.F.interrupt();
                System.exit(-1);
            }
        });
    }
}
