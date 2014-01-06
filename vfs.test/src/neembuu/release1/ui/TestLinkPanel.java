/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui;

import java.util.logging.Level;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author Shashank Tulsyan
 */
public class TestLinkPanel {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception lookandfeelexception) {
            //LOGGER.log(Level.INFO," ",lookandfeelexception);
        }
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        
        frame.getContentPane().add(new LinkPanel());
        frame.setVisible(true);
    }
}
