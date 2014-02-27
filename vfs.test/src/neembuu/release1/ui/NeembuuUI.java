/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import neembuu.release1.Main;
import neembuu.release1.api.IndefiniteTask;
import neembuu.release1.api.ui.IndefiniteTaskUI;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.access.MainUIA;
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
    private final LinkedList<IndefiniteTask> indefiniteTasks = new LinkedList<IndefiniteTask>();
    
    private final MainComponent mainComponent = new MainComponent() {
        @Override public JFrame getJFrame() { return jf; }};
    
    private final IndefiniteTaskUI indefiniteTaskUI = new IndefiniteTaskUI() {
        @Override public IndefiniteTask showIndefiniteProgress(String message) {
            return NeembuuUI.this.showIndefiniteProgress(message); }};
    
    private final MainUIA mainUIA = new MainUIA() {
        @Override public JButton neembuuVirtualFolderButton() {
            return mp.neembuuVirtualFolderButton; }};
    
    public NeembuuUI() {
        this.jf = makeJFrame();
        this.mp = new MainPanel(this);
        ala = new AddLinkAction(this,mp);
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

    public MainUIA getMainUIA() {
        return mainUIA;
    }
    
    public MainComponent getMainComponent(){
        return mainComponent;
    }

    public IndefiniteTaskUI getIndefiniteTaskUI() {
        return indefiniteTaskUI;
    }
    
    private IndefiniteTask showIndefiniteProgress(String message){
        IndefTask it = new IndefTask(message);
        indefiniteTasks.add(it);
        updateIndefTasks();
        return it;
    }
    
    private void updateIndefTasks(){
        if(indefiniteTasks.isEmpty()){
            mp.showIndefiniteProgress(false, null);
            return;
        }
        String message = "";
        for (IndefiniteTask indefiniteTask : indefiniteTasks) {
            message+=indefiniteTask.displayMessage()+"\n";
        }
        mp.showIndefiniteProgress(true, message);
    }
    
    void addLinks(boolean open) {
        mp.getAddLinkUI().addLinksPanelEnable(false);// no chance of race condition
        ala.open(open);
        new Thread(ala,"Add links thread").start();
    }
    
    private void initJFrame(){
        jf.setTitle("NEEMBUU");
        jf.setFont(Fonts.FuturaLight);
        jf.setIconImage(new ImageIcon(NeembuuUI.class.getResource("images/7_ls_small_transparent_32x32.png") ).getImage());
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setMinimumSize(new Dimension(463+30,160));
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
    
    private final class IndefTask implements IndefiniteTask{
        private final String message;
        private final long submittedOn = System.currentTimeMillis();
        private AtomicLong finishedOn = new AtomicLong(-1);
        
        public IndefTask(String message) {
            this.message = message;
        }
        
        @Override
        public String displayMessage() {
            return message;
        }

        @Override
        public long submittedOn() {
            return submittedOn;
        }

        @Override
        public long completedOn() {
            if(!hasCompleted()){
                throw new IllegalStateException("Task not completed");
            }
            return finishedOn.get();
        }

        @Override
        public void done() throws IllegalStateException {
            if(!finishedOn.compareAndSet(-1, System.currentTimeMillis())){
                throw new IllegalStateException("Task alreadt completed on "+finishedOn.get());
            }
            indefiniteTasks.remove(this);
            updateIndefTasks();
        }

        @Override
        public boolean hasCompleted() {
            return finishedOn.get() > 0;
        }
        
    }
}
