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

package neembuu.release1.ui;

import neembuu.release1.ui.linkcontainer.LinksContainer;
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
import neembuu.release1.api.open.OpenerAccess;
import neembuu.release1.api.ui.AddLinkUI;
import neembuu.release1.api.ui.HeightProperty;
import neembuu.release1.api.ui.IndefiniteTaskUI;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.access.MainUIA;
import neembuu.release1.api.ui.actions.AddLinksAction;
import neembuu.release1.ui.actions.AddLinkAction;
import neembuu.release1.api.ui.LinkGroupUICreator;
import neembuu.release1.open.OpenerImpl;

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
    private OpenerAccess openerA;
    
    private final LinkedList<IndefiniteTask> indefiniteTasks = new LinkedList<IndefiniteTask>();
    
    private final MainComponent mainComponent;
    
    private final IndefiniteTaskUI indefiniteTaskUI = new IndefiniteTaskUI() {
        @Override public IndefiniteTask showIndefiniteProgress(String message) {
            return NeembuuUI.this.showIndefiniteProgress(message); }};
    
    private final MainUIA mainUIA = new MainUIA() {
        @Override public JButton neembuuVirtualFolderButton() {
            return mp.neembuuVirtualFolderButton; }};
    
    public NeembuuUI() {
        this.jf = makeJFrame();
        mainComponent = new MainComponentImpl(jf);
        this.mp = new MainPanel(addLinksAction,mainComponent,listener);
        this.lc = new LinksContainer(mp,mp.linksPanel);
        lc.heightProperty().addListener(listener);
        ala = new AddLinkAction(this,mp);
        mp.neembuuVirtualFolderButton.setEnabled(false);
    }

    public AddLinkUI getAddLinkUI(){
        return mp.getAddLinkUI();
    }

    public LinkGroupUICreator getLinkGroupUICreator() {
        return ala;
    }
    
    public void initialize(Main main){
        this.main = main;
        ala.setMain(main);
        initJFrame();
        jf.setVisible(true);
    }
    
    public void initOpenerA(OpenerAccess openerA){
        this.openerA = openerA;
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
    
    /*void updateHeight(){
        lc.updateLayout();
    }*/
    
    class CloseHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            try{
                if(main.getMountManager().getMount()!=null)
                    main.getMountManager().getMount().unMount();
            }
            catch(Exception a){
                Main.getLOGGER().log(Level.INFO," ",a);
            }
            try{
                Main.getLOGGER().log(Level.INFO," Closing all open files");
                openerA.closeAll();
            }
            catch(Exception a){
                Main.getLOGGER().log(Level.INFO," ",a);
            }
            
            System.exit(0);
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
    
    private final HeightProperty.Listener listener = new HeightProperty.Listener() {
            @Override public void changed(HeightProperty h, int oldValue, int newValue) {
                adjustHeightOfMainWindow(0);
            } };
    
    private final AddLinksAction addLinksAction = new AddLinksAction() {
        @Override public void addLinks(boolean open) { NeembuuUI.this.addLinks(open); }};
    
    private void adjustHeightOfMainWindow(double f){
        int ht = jf.getMinimumSize().height;
        if(mp.addLinksPanel.isVisible()){
            ht+=mp.addLinksPanel.getPreferredSize().height;
        }
        int linkContainerHeight = lc.heightProperty().getValue();
        if(linkContainerHeight == 0){ // isEmpty
            jf.setSize(jf.getMinimumSize().width,ht);
        }
        else {
            ht += linkContainerHeight;
            
            ht = Math.min(ht,jf.getMaximumSize().height);
            if(ht < jf.getSize().height){
                return;
            }
            int w = jf.getSize().width;
            jf.setSize(w,ht);
        }
    }
}
