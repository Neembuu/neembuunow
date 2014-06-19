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

package neembuu.release1.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import neembuu.release1.api.ui.MainComponent;
import neembuu.swing.HiddenBorderButton;
import neembuu.swing.TextBubbleBorder;
import neembuu.util.Throwables;

/**
 *
 * @author Shashank Tulsyan
 */
public class ChooseVariantTimeOut extends javax.swing.JPanel {
    private final JDialog jd;
    private final long waitDuration;
    private Entry selected;
    
    public static interface Entry {
        String type(); String speed(); boolean hidden();
    }
    
    ChooseVariantTimeOut(
            JDialog jd, Entry defaultOption,
            List<Entry> options, long waitDuration) {
        this.jd = jd; this.waitDuration = waitDuration;
        selected = defaultOption;
        initComponents();
        initHiddenPanel();
        showHiddenButton.setForeground(Colors.CONTROL_ICONS);
        
        addEntries(defaultOption, options);
        
        t.start();
    }
    
    private void initHiddenPanel(){
        TextBubbleBorder border = new TextBubbleBorder(Colors.BORDER , 4, 16, 0);
        border.getBorderInsets(null).bottom = 8;
        border.getBorderInsets(null).top = 8;
        border.getBorderInsets(null).right = 1;
        border.getBorderInsets(null).left = 1;
        variantsPanel_hidden.setBorder(border);
        variantsPanel_hidden.setVisible(false);
        //border.setVisible(false);
    }
    
    private void addEntries(Entry defaultOption,List<Entry> options){
        int i = 1;
        addSpace(10, i,variantsPanel);
        addSpace(10, i,variantsPanel_hidden); i++; //common
        for (Entry entry : options) {
            if(entry.hidden()){
                addSpace(5, i,variantsPanel_hidden); i++;
                addEntry(entry, defaultOption==entry,i,variantsPanel_hidden);
            }else {
                addSpace(5, i,variantsPanel); i++;
                addEntry(entry, defaultOption==entry,i,variantsPanel);
            }i++;
        }
    }
    
    private final Timer t = new Timer(300, new ActionListener() {
        double waited = 0;
        @Override public void actionPerformed(ActionEvent e) {
            if(waited>waitDuration){t.stop(); jd.setVisible(false); jd.dispose(); }
            waited += 0.3d;
            
            String p = "Automatically opening " + selected.type() + " in "
                    + ((int)(waitDuration-waited))
                    + " seconds ...";
            automaticallyOpenWarningMessage.setText(p);
            
        }
    });

    public static Entry newEntry(final String type,final long sizeInBytes, long durationInMillisec, final boolean hidden){
        double s = sizeInBytes/(durationInMillisec/1000d);
        final String speedToStr = toString(s);
        return new Entry() {
            @Override public String type() { return type; }
            @Override public String speed() { return speedToStr; }
            @Override public boolean hidden() { return hidden; }
        };
    }
    
    private static String toString(double sz){
        String suffix;
        if (sz < 1000) {
            suffix = " Bps";
        } else if (sz < 1000 * 1000) {
            suffix = " KiBps";
            sz /= 1024;
        } else if (sz < 1000 * 1000 * 1000) {
            suffix = " MiBps";
            sz /= 1024 * 1024;
        } else if (sz < 1000 * 1000 * 1000 * 1000) {
            suffix = " GiBps";
            sz /= 1024 * 1024 * 1024;
        } else {
            suffix = " TiBps";
            sz /= 1024 * 1024 * 1024 * 1024;
        }
        if (sz < 10) {
            sz = Math.round(sz * 100.0) / 100.0;
        } else if (sz < 100) {
            sz = Math.round(sz * 10.0) / 10.0;
        }
        return " "+((int)sz) + " " + suffix;
    }
    
    public static Entry showMessage(
            MainComponent m,
            final long waitDurationSeconds,
            final Entry defaultChoice,
            final List<Entry>choices){
        JFrame jf = null;
        try{
            jf = m.getJFrame();
        }catch(NullPointerException npe){

        }
        final JDialog jd = new JDialog(jf, "Please select a type", true);
        jd.setAlwaysOnTop(true);
                
        //jd.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        final ChooseVariantTimeOut x = new ChooseVariantTimeOut(jd, defaultChoice, choices, waitDurationSeconds);
        jd.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        jd.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                System.out.println("closed");
                x.selected = null; x.t.stop(); jd.setVisible(false);jd.dispose();
            }});
        jd.getContentPane().add(x);        
        adjustSize(jd, x);
        //jd.setResizable(false);
        jd.setVisible(true);
        
        return x.selected;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        automaticallyOpenWarningMessage = new javax.swing.JLabel();
        variantsPanel = new javax.swing.JPanel();
        typeLabel = new javax.swing.JLabel();
        speedRequiredLabel = new javax.swing.JLabel();
        showHiddenButton = HiddenBorderButton.make("Show other qualities");
        variantsPanel_hidden = new javax.swing.JPanel();
        typeLabel1 = new javax.swing.JLabel();
        speedRequiredLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        automaticallyOpenWarningMessage.setText(org.openide.util.NbBundle.getMessage(ChooseVariantTimeOut.class, "ChooseVariantTimeOut.automaticallyOpenWarningMessage.text")); // NOI18N

        variantsPanel.setLayout(new java.awt.GridBagLayout());

        typeLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        typeLabel.setText(org.openide.util.NbBundle.getMessage(ChooseVariantTimeOut.class, "ChooseVariantTimeOut.typeLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        variantsPanel.add(typeLabel, gridBagConstraints);

        speedRequiredLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        speedRequiredLabel.setText(org.openide.util.NbBundle.getMessage(ChooseVariantTimeOut.class, "ChooseVariantTimeOut.speedRequiredLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        variantsPanel.add(speedRequiredLabel, gridBagConstraints);

        showHiddenButton.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        showHiddenButton.setText(org.openide.util.NbBundle.getMessage(ChooseVariantTimeOut.class, "ChooseVariantTimeOut.showHiddenButton.text")); // NOI18N
        showHiddenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showHiddenButtonActionPerformed(evt);
            }
        });

        variantsPanel_hidden.setLayout(new java.awt.GridBagLayout());

        typeLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        typeLabel1.setText(org.openide.util.NbBundle.getMessage(ChooseVariantTimeOut.class, "ChooseVariantTimeOut.typeLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        variantsPanel_hidden.add(typeLabel1, gridBagConstraints);

        speedRequiredLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        speedRequiredLabel1.setText(org.openide.util.NbBundle.getMessage(ChooseVariantTimeOut.class, "ChooseVariantTimeOut.speedRequiredLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        variantsPanel_hidden.add(speedRequiredLabel1, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(automaticallyOpenWarningMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                    .addComponent(variantsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(variantsPanel_hidden, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(showHiddenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(variantsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showHiddenButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(variantsPanel_hidden, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(automaticallyOpenWarningMessage)
                .addGap(7, 7, 7))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void showHiddenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showHiddenButtonActionPerformed
        variantsPanel_hidden.setVisible(!variantsPanel_hidden.isVisible());
        adjustSize(jd, this);
    }//GEN-LAST:event_showHiddenButtonActionPerformed

    private static void adjustSize(JDialog jd, ChooseVariantTimeOut cvto){
        jd.setSize(cvto.getPreferredSize().width + 20,
                cvto.getPreferredSize().height + 50);
    }
    
    private void addSpace(int s, int i, JPanel panel){
        java.awt.GridBagConstraints gridBagConstraints;
        
        javax.swing.Box.Filler filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, s), new java.awt.Dimension(0, s), new java.awt.Dimension(32767, s));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = i;
        panel.add(filler1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = i;
        panel.add(filler1, gridBagConstraints);
    }
    
    private void addEntry(final Entry entry,boolean default_,int i, JPanel panel){
        java.awt.GridBagConstraints gridBagConstraints;
        
        JButton typeSelector = new JButton(entry.type());
        JLabel speedInfo = new JLabel(entry.speed());
        
        if(default_){
            typeSelector.setBackground(Colors.PROGRESS_BAR_FILL_BUFFER);
        }else {
            typeSelector.setFocusable(false);
        }
        
        typeSelector.setText(entry.type()); // NOI18N
        typeSelector.addActionListener(new java.awt.event.ActionListener() {
            @Override public void actionPerformed(java.awt.event.ActionEvent evt) {
                selected = entry; t.stop(); jd.setVisible(false);jd.dispose();
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = i;
        panel.add(typeSelector, gridBagConstraints);

        speedInfo.setText(entry.speed()); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = i;
        panel.add(speedInfo, gridBagConstraints);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel automaticallyOpenWarningMessage;
    private javax.swing.JButton showHiddenButton;
    private javax.swing.JLabel speedRequiredLabel;
    private javax.swing.JLabel speedRequiredLabel1;
    private javax.swing.JLabel typeLabel;
    private javax.swing.JLabel typeLabel1;
    private javax.swing.JPanel variantsPanel;
    private javax.swing.JPanel variantsPanel_hidden;
    // End of variables declaration//GEN-END:variables

    public static void main(String[] args) {
        InitLookAndFeel.init();
        
        List<Entry> es = new LinkedList<>();
        
        Entry e = newEntry("480 P", 50*1024, 122,false);
        
        es.add(newEntry("320 P", 25*1024, 122,true));
        es.add(e);
        es.add(newEntry("720 P", 100*1024, 122,false));
        es.add(newEntry("1080 P", 300*1024, 122,false));
        
        MainComponent mc = new neembuu.release1.ui.mc.MainComponentImpl(new JFrame());
        mc.getJFrame().setSize(100,100);
        mc.getJFrame().setVisible(true);
        try{Thread.sleep(4000);}catch(Exception a){}
        
        Entry x = showMessage(mc, 200, e, es);
        System.out.println("x="+x.type());
    }
}
