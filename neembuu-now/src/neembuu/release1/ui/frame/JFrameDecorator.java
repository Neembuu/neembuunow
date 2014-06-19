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
import neembuu.release1.api.settings.Settings;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.systray.SysTray;
import neembuu.release1.ui.InitLookAndFeel;
import neembuu.release1.ui.mc.MainComponentImpl;
import neembuu.util.Throwables;

/**
 *
 * @author Shashank Tulsyan
 */
public class JFrameDecorator {
    private final FrameDecoration fd;
    private final MouseHandler mh;
    
    private final Settings settings;
    private final MainComponent mc;
    private final SysTray sysTray;

    public JFrameDecorator(final MainComponent mc,SysTray sysTray,Settings settings) {
        this.settings = settings; this.sysTray = sysTray;
        this.mc = mc;
        mc.getJFrame().setUndecorated(true);
        mc.getJFrame().setSize(400, 100);
        fd = new FrameDecoration(make());
        //fd.getCustomJFrame().b3().setVisible(false);
        //fd.getCustomJFrame().b2().setVisible(false);
        
        initButtons();
        initSysTray();
        
        mc.getJFrame().getContentPane().add(fd);
        
        FDCI fdci = new FDCI(fd, mc.getJFrame());
        mh = new MouseHandler(fdci);
        
        fd.getCustomJFrame().frameOutside().addMouseMotionListener(mh);
        fd.getCustomJFrame().frameOutside().addMouseListener(mh);
        fd.getCustomJFrame().frameBorder().addMouseMotionListener(mh.normalize(3, 3));
        fd.getCustomJFrame().frameBorder().addMouseListener(mh.normalize(3, 3));
        fd.getCustomJFrame().headerRegion().addMouseMotionListener(mh.normalize(3, 3));
        fd.getCustomJFrame().headerRegion().addMouseListener(mh.normalize(3, 3));
    }

    private void initButtons(){
        fd.getCustomJFrame().b1().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                closeAction();
            }});
        
        fd.getCustomJFrame().b2().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                Throwables.start(new Runnable() {
                    @Override public void run() {
                        b2_action();
                    }
                },"Hide Action thread",true);
        }});
        
        fd.getCustomJFrame().b3().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                mc.getJFrame().setState(JFrame.ICONIFIED);
        }});
    }
    
    private void b2_action(){
        final String dontShow = "Don't show this message again",
                yes="Yes",no="No";
        boolean dontShowMessage = false;
        if(settings!=null){ dontShowMessage = settings.getBoolean("JFrameDecorator","dontShowMessage");}
        if(!dontShowMessage){
            Object resp = mc.newMessage().setTitle("Minimize to system tray")
                    .setMessage("Are you sure you want to hide Neembuu?\n"
                            + "Neembuu will still keep running,\n"
                            + "it will just be hidden.")
                    .setTimeout(7000)
                    .ask(new Object[]{yes,no,dontShow},1);
            if(resp==null)return;
            if(dontShow.hashCode()==resp.hashCode()){
                if(settings!=null){settings.setBoolean(true,"JFrameDecorator","dontShowMessage");}
                hide();
            }else if(yes.hashCode()==resp.hashCode()){
                hide();
            }
        }else {
            hide();
        }
    }
    
    private void closeAction(){
        for(WindowListener wl :  mc.getJFrame().getWindowListeners()){
            wl.windowClosing(new WindowEvent(mc.getJFrame(), WindowEvent.WINDOW_CLOSING));
        }
    }
    
    private void initSysTray(){
        if(sysTray==null)return;
        sysTray.newAction().displayName("Exit").callback(new SysTray.Callback() {
            @Override public final void actionPerformed() {
                closeAction();
            }
        }).make();
        SysTray.Callback hideShowAction = new SysTray.Callback() {
            @Override public final void actionPerformed() {
                if(!mc.getJFrame().isVisible()){
                     mc.getJFrame().setVisible(true);
                }else { 
                    hide();
                }
            }
        };
        sysTray.newAction().displayName("Show/Hide").callback(hideShowAction).make();
        try{
            sysTray.setDefaultAction(hideShowAction);
        }catch(IllegalStateException ise){
            ise.printStackTrace();
        }
    }
    
    private void hide(){
        if(sysTray!=null){
            sysTray.newMessage()
                .setMessage("Neembuu Now has been minimixed here.\n"
                        + "Double-Click here to maximum the window again.")
                .info().show();
        }
        mc.getJFrame().setVisible(false);
    }
    
    public FrameDecoration getFrameDecoration() {
        return fd;
    }

    
    public interface ContentArea_add_callback { 
        void contentArea_add(JPanel toAdd);
    }
    
    private ContentArea_add_callback make(){
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
        MainComponent mc1 = new MainComponentImpl(new JFrame("Neembuu"));
        JFrameDecorator jfd = new JFrameDecorator(mc1,null,null);
        mc1.getJFrame().setLocation(200, 200);
        mc1.getJFrame().setVisible(true);
    }
}
