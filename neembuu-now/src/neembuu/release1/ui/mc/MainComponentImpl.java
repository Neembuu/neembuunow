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

package neembuu.release1.ui.mc;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.*;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.Message;

/**
 *
 * @author Shashank Tulsyan
 */
public class MainComponentImpl implements MainComponent{
    private final LazyFrame ff;

    public MainComponentImpl(final JFrame jf) {
        this(new LazyFrame() {
            @Override public JFrame getJFrame() { return jf;}
            @Override public boolean available() { return true; }
        });
    }
    
    public MainComponentImpl(LazyFrame ff) {
        this.ff = ff;
    }
    
    @Override public JFrame getJFrame() { 
        if(!ff.available()){throw new IllegalStateException("UI not initialized yet");}
        return ff.getJFrame(); 
    }

    @Override public Message newMessage() {
        return new MessageImpl();
    }

    @Override
    public boolean allowReplacementWith(MainComponent mc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private class MessageImpl implements Message {
        private String message,title;
        private int type = INFORMATION_MESSAGE; 
        
        private PreferredLocation pl;
        
        private Emotion e; private int timeout = 0;
        private JTextArea jta = null; private volatile JDialog dialog = null;
        
        @Override public Message warning() {
            type = WARNING_MESSAGE;return this;
        }
        
        @Override public Message error() {
            type = ERROR_MESSAGE;return this;
        }

        @Override public Message info() {
            type = INFORMATION_MESSAGE;return this;
        }

        @Override public Message setTitle(String title) {
            this.title = title;return this;
        }

        @Override public Message setMessage(String message) {
            this.message = message; if(message.length() > 1024 && jta==null)editable();
            if(jta!=null){
                jta.setText(message);jta.repaint();
            }
            return this;
        }

        @Override public Message setEmotion(Emotion e) {
            this.e=e; return this;
        }
        
        @Override public boolean ask(){
            /*Icon i = getIconForEmotion(e);
            type = QUESTION_MESSAGE;
            int x = showConfirmDialog(getJFrame(),message,title,YES_NO_OPTION,type,i);          
            return x==YES_OPTION;*/
            Object selectedValue = askImpl(null,true,-1);
            System.out.println("reso="+selectedValue);
            if(selectedValue == null)
                return false;
            
            if(selectedValue instanceof Integer)
                return ((Integer)selectedValue).intValue()==YES_OPTION;
            return false;
        }
        
        @Override public Object ask(Object[]options,int indexOfDefaultOption){
            return askImpl(options, false,indexOfDefaultOption);
        }
        
        @Override public Object ask(Object[]options){
            return askImpl(options, false,-1);
        }
        
        private Object askImpl(Object[]options,boolean bool,int indexOfDefaultOption){
            Icon i = getIconForEmotion(e);
            type = QUESTION_MESSAGE;
            
            final JOptionPane pane;
            if(!bool){
                if(indexOfDefaultOption>=0){
                    pane = new JOptionPane(message,QUESTION_MESSAGE,DEFAULT_OPTION,
                        i,options,options[indexOfDefaultOption]);
                }else {
                    pane = new JOptionPane(message,QUESTION_MESSAGE,DEFAULT_OPTION,
                        i,options);
                }
            }else{
                pane = new JOptionPane(message, QUESTION_MESSAGE,YES_NO_OPTION,i);
            }
            
            dialog = pane.createDialog(getJFrame(), title);
            setDialogLocation(dialog, pl);
            javax.swing.Timer t = null;
            if(timeout!=0){
                startTimer();
            }
            dialog.show();
            if(t!=null)t.stop();
            return pane.getValue();
        }

        @Override public Message editable() {
            jta = new JTextArea(); return this;
        }
        
        @Override public String askPassword(){
            Icon i = getIconForEmotion(e);
            JPasswordField pf = new JPasswordField();
            JPanel jp = new JPanel(new GridLayout(0, 1, 5, 5));
            String[]messages=message.split("\n");
            for (String message_i : messages) {
                JLabel messageLB = new JLabel(message_i);
                jp.add(messageLB);
            }
            jp.add(pf);
            int okCxl = showConfirmDialog(getJFrame(), jp, title, OK_CANCEL_OPTION, type,i);
            
            if (okCxl == OK_OPTION) {
              String password = new String(pf.getPassword());
              return password;
            }return null;
        }
        
        @Override public Message setTimeout(int timeout){
            this.timeout = timeout; return this;
        }

        
        @Override public Message setPreferredLocation(PreferredLocation pl){
            this.pl = pl;
            return this;
        }
        
        @Override public void close() {
            dialog.setVisible(false);
        }
        
        @Override public void show() { showImpl(false); }
        @Override public Message showNonBlocking() { showImpl(true); return this; }
        
        private void showImpl(boolean notBlock){
            final Icon i = getIconForEmotion(e);
            
            Object m = message;
            
            if(message!=null){
                if(jta!=null){
                    jta.setText(message);
                    JScrollPane jsp = new JScrollPane(jta);
                    Dimension sz = new Dimension(150,150);
                    jsp.setMinimumSize(sz);jsp.setPreferredSize(sz);
                    jsp.setMaximumSize(sz);
                    m = jsp;
                }
            }
            
            final JOptionPane pane = new JOptionPane(m,type,DEFAULT_OPTION,i);
            dialog = pane.createDialog(getJFrame(), title);
            setDialogLocation(dialog, pl);
            
            if(notBlock){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override public void run() {
                        showC(dialog);
                    }});
            }else {
                showC(dialog);
            }
        }
        
        private void showC(final JDialog dialog){
            startTimer();
            dialog.setVisible(true); dialog.dispose();
        }
        
        private void startTimer(){
            Timer t = new Timer(100,new ActionListener() {
                private int totalDelay = 0;
                @Override public void actionPerformed(ActionEvent e) {
                    totalDelay+=((Timer)e.getSource()).getDelay();
                    if(totalDelay>timeout){
                        ((Timer)e.getSource()).stop();
                        dialog.setVisible(false);
                    }else{
                        dialog.setTitle(title+" ..."+((timeout - totalDelay)/1000)
                                + " sec(s)");
                    }
                }
            }); if(timeout > 0) t.start();
        }
        
        private Icon getIconForEmotion(Emotion e){
            if(e==Emotion.I_AM_DEAD){
                return neembuu.config.GlobalTestSettings.ONION_EMOTIONS.getQuestionImageIcon("Onion16.gif");
            }else if(e==Emotion.EMBARRASSED){
                return neembuu.config.GlobalTestSettings.ONION_EMOTIONS.getQuestionImageIcon("Onion6.gif");
            }else if(e==Emotion.NOT_SURE){
                return neembuu.config.GlobalTestSettings.ONION_EMOTIONS.getQuestionImageIcon("efb50fe2.gif");
            }else if(e==Emotion.EXPERT){
                return neembuu.config.GlobalTestSettings.ONION_EMOTIONS.getQuestionImageIcon("suggestions.gif");
            }
            return null;
        }
        
    }
    
    private void setDialogLocation(JDialog dialog, Message.PreferredLocation pl){
        if(pl!=null){
            if(pl==Message.PreferredLocation.Aside){
                int x = getJFrame().getLocation().x;
                int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
                if(x > screenWidth){
                    x -= dialog.getWidth();
                }else {
                    x += getJFrame().getWidth();
                }
                dialog.setLocation(x,getJFrame().getLocation().y);
            }else if(pl==Message.PreferredLocation.OnTopOfAll){
                dialog.setAlwaysOnTop(true);
            }
        }
    }
    
}
