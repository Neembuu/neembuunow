/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import neembuu.release1.Main;
import neembuu.release1.newlink.AddLinkAction;

/**
 *
 * @author Shashank Tulsyan
 */
public final class NeembuuUI {
    private final JFrame jf;
    private final MainPanel mp;
    private final LinksContainer lc;
    private final AddLinkAction ala;
    
    private Main main;
    
    public NeembuuUI() {
        this.jf = makeJFrame();
        this.mp = new MainPanel(this);
        ala = new AddLinkAction(mp);
        mp.neembuuVirtualFolderButton.setEnabled(false);
        this.lc = new LinksContainer(mp,jf);
    }
    
    public void initialize(Main main){
        this.main = main;
        ala.setMain(main);
        initJFrame();
        jf.setVisible(true);
    }

    public LinksContainer getLinksContainer() {
        return lc;
    }

    public JFrame getFrame() {
        return jf;
    }
    
    public void showIndefiniteProgress(boolean t,String message){
        
        mp.showIndefiniteProgress(t, message);
    }
    
    public static void initLookAndFeel(){
        try {
            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            javax.swing.UIManager.put("nimbusBase", Colors.NIMBUS_BASE);
        } catch (Exception lookandfeelexception) {
            lookandfeelexception.printStackTrace(System.err);
        }
    }
    
    void addLinks(boolean open) {
        mp.addLinksPanelEnable(false);// no chance of race condition
        ala.open(open);
        new Thread(ala,"Add links thread").start();
    }
    
    private void initJFrame(){
        jf.setTitle("NEEMBUU");
        jf.setFont(Fonts.FuturaLight);
        jf.setIconImage(new ImageIcon(NeembuuUI.class.getResource("images/7_ls_small_transparent_32x32.png") ).getImage());
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setMinimumSize(new Dimension(463,160));
        jf.setMaximumSize(new Dimension(463+200,700));
        jf.getContentPane().add(mp);
        jf.addWindowListener(new CloseHandler());
    }
    
    private JFrame makeJFrame(){
        return new JFrame(){
            @Override
            public final void paint(Graphics g) {
                Dimension d = getSize();
                Dimension m = getMaximumSize();
                boolean resize = d.width > m.width || d.height > m.height;
                d.width = Math.min(m.width, d.width);
                d.height = Math.min(m.height, d.height);
                if (resize) {
                    Point p = getLocation();
                    setVisible(false);
                    setSize(d);
                    setLocation(p);
                    setVisible(true);
                }
                super.paint(g);
            }
        };
    }
    
    void updateHeight(){
        lc.updateLayout();
    }
    
    private void addLink(){
        lc.addLinkPanel(new LinkPanel());
        lc.updateLayout();
    }
    
    class CloseHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            try{
                if(main.getMountManager().getMount()!=null)
                    main.getMountManager().getMount().unMount();
            }
            catch(Exception a){
                Main.getLOGGER().log(Level.INFO," ",a);
            }System.exit(0);
        }

    }
    
    public void successfullyMounted(){
        mp.neembuuVirtualFolderButton.setEnabled(true);
        
        mp.neembuuVirtualFolderButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    File f = main.getMountManager().getMount().getMountLocation().getAsFile();
                    java.awt.Desktop.getDesktop().open(f);
                }catch(Exception a){
                    JOptionPane.showMessageDialog(null,a.getMessage(),"Could not open NeembuuFolder",JOptionPane.ERROR_MESSAGE);
                    Main.getLOGGER().log(Level.SEVERE,"Could not open NeembuuFolder",a);
                }
            }
        });
    }
}
