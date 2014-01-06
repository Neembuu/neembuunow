/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
