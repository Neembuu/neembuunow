/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolTip;
import javax.swing.Painter;
import javax.swing.UIDefaults;

/**
 *
 * @author Shashank Tulsyan
 */
public final class HiddenBorderButton extends JButton{

    private boolean clickable = true;
    
    private ActionListener[]actionListeners = new ActionListener[0];

    public HiddenBorderButton(Icon icon) {
        super(icon);
    }

    public HiddenBorderButton(String text) {
        super(text);
    }

    public JToolTip createToolTip_ignore() {
        JToolTip tip = new JToolTip(){
            /*@Override
            protected void paintComponent(final Graphics g) {
                JLabel jl = new JLabel(getToolTipText()){
                    @Override
                    public void setToolTipText(String text) {
                        paintComponent(g);
                    }
                };
                jl.setSize(100, 20);
                jl.setToolTipText(null);
            }*/
        };/*super.createToolTip();*/
        
        UIDefaults defaults = new UIDefaults(); 
        defaults.put("ToolTip[Enabled].backgroundPainter", new Painter() {

            @Override
            public void paint(Graphics2D g, Object object, int width, int height) {
                g.setColor(new Color(255,255,255,50));
                g.fillRect(0, 0, width, height);
            }
        }); 
        tip.setFont(Fonts.MyriadPro.deriveFont(16f));
        //tip.putClientProperty("Nimbus.Overrides", defaults);
        
        tip.setComponent(this);
        //tip.setOpaque(false);
        //tip.setBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0));
        return tip;
    }

    /*public boolean isClickable() {
        return clickable;
    }
    
    public void setClickable(boolean b) {
        if(b==this.clickable){
            return;
        }
        this.clickable = b;
        
        if(!b){
            actionListeners = (ActionListener[])this.listenerList.getListeners(ActionListener.class);
            for (int i = 0; i < actionListeners.length; i++) {
                this.removeActionListener(actionListeners[i]);
            }
        }else {
            for (int i = 0; i < actionListeners.length; i++) {
                this.addActionListener(actionListeners[i]);
            }
        }
        
        repaint();
    }*/
    
    static JButton makeWithTinting(String imgLoc,Color tintingColor){
        final TintedGreyScaledImage icon;
        icon = TintedGreyScaledImage.make(imgLoc,true);
        return HiddenBorderButton.make(icon.getBaseImage(), icon.getTintedImage(tintingColor));
    }
    
    static JButton make(final String p1,final String p2,boolean fillOnHover){
        final ImageIcon i1 = new ImageIcon(TintedGreyScaledImage.class.getResource(p1));
        final ImageIcon i2 = new ImageIcon(TintedGreyScaledImage.class.getResource(p2));
        
        return make(i1, i2,fillOnHover);
    }
    
    static JButton make(final Icon icon_bw,final Icon icon_clr){
        return make(icon_bw, icon_clr, true);
    }
    static HiddenBorderButton make(final Icon icon_bw,final Icon icon_clr,final boolean fillOnHover){
        final HiddenBorderButton button;
        button = new HiddenBorderButton(icon_bw);
        button.setContentAreaFilled(false); //this is the piece of code you needed
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                if(!button.clickable)return;
                JButton buton = (JButton) event.getSource();
                buton.setIcon(icon_clr);
                if(fillOnHover)
                    buton.setContentAreaFilled(true);  //when hoovered it will show borders and fill area.
            }

            @Override
            public void mouseExited(MouseEvent event) {
                JButton buton = (JButton) event.getSource();
                buton.setIcon(icon_bw);
                if(fillOnHover)
                    buton.setContentAreaFilled(false); //when mouse is not on button then it will look the same.
            }
        });
        button.setBackground(Color.WHITE);
        return button;
    }
    
    static JButton make(String text){
        final HiddenBorderButton button = new HiddenBorderButton(text);
        button.setContentAreaFilled(false); //this is the piece of code you needed
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                if(!button.clickable)return;
                JButton buton = (JButton) event.getSource();
                buton.setContentAreaFilled(true);  //when hoovered it will show borders and fill area.
            }

            @Override
            public void mouseExited(MouseEvent event) {
                JButton buton = (JButton) event.getSource();
                buton.setContentAreaFilled(false); //when mouse is not on button then it will look the same.
            }
        });
        button.setBackground(Color.WHITE);
        return button;
    }

}
