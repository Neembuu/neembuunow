/*
 * Copyright (C) 2011 admin
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

/*
 * AddTestLinks.java
 *
 * Created on Jul 21, 2011, 12:17:50 PM
 */
package neembuu.vfs.test;

import java.net.URL;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class AddTestLinks extends javax.swing.JDialog {

    /** Creates new form AddTestLinks */
    public AddTestLinks(
            java.awt.Frame parent, 
            boolean modal,
            LinkedList<String> result
        ) {
        super(parent, modal);
        initComponents();
        this.result = result;
    }

    final private LinkedList<String> result;
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        okaybutton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listOfLinks = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        sampleLink = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        okaybutton.setText(org.openide.util.NbBundle.getMessage(AddTestLinks.class, "AddTestLinks.okaybutton.text")); // NOI18N
        okaybutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okaybuttonActionPerformed(evt);
            }
        });

        jButton2.setText(org.openide.util.NbBundle.getMessage(AddTestLinks.class, "AddTestLinks.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        listOfLinks.setColumns(20);
        listOfLinks.setRows(5);
        listOfLinks.setText(org.openide.util.NbBundle.getMessage(AddTestLinks.class, "AddTestLinks.listOfLinks.text")); // NOI18N
        jScrollPane1.setViewportView(listOfLinks);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(AddTestLinks.class, "AddTestLinks.jLabel1.text")); // NOI18N

        sampleLink.setEditable(false);
        sampleLink.setText(org.openide.util.NbBundle.getMessage(AddTestLinks.class, "AddTestLinks.sampleLink.text")); // NOI18N
        sampleLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sampleLinkActionPerformed(evt);
            }
        });

        jLabel2.setText(org.openide.util.NbBundle.getMessage(AddTestLinks.class, "AddTestLinks.jLabel2.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(AddTestLinks.class, "AddTestLinks.jLabel3.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(sampleLink, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(okaybutton, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                            .addComponent(jLabel1))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(sampleLink, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(okaybutton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void sampleLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sampleLinkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sampleLinkActionPerformed

    private void okaybuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okaybuttonActionPerformed
        // TODO add your handling code here:
        String testStr = listOfLinks.getText();
        System.out.println("testStr="+testStr);
        try{
            String[]lnks=testStr.split("\n");
            
            for (int i = 0; i < lnks.length; i++) {
                System.out.println("link="+lnks[i]);
                URL url = new URL(lnks[i]);
                if(url.getProtocol().equals("http")){
                    result.add(lnks[i].trim());
                }else {throw new Exception("Only http links allowed. Faulty link -> "+url);}
            }
            
            setVisible(false);
            
        }catch(Exception any){
            JOptionPane.showMessageDialog(
                    this, 
                    any.getMessage(),
                    "Invalid links, try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_okaybuttonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                AddTestLinks dialog = new AddTestLinks(new javax.swing.JFrame(), true, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea listOfLinks;
    private javax.swing.JButton okaybutton;
    private javax.swing.JTextField sampleLink;
    // End of variables declaration//GEN-END:variables
}
