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
package neembuu.release1.ui.linkpanel;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import neembuu.release1.api.ui.ExpandableUI;
import neembuu.release1.api.ui.ExpansionState;
import neembuu.release1.api.ui.Graph;
import neembuu.release1.api.ui.HeightProperty;
import neembuu.release1.api.ui.OpenableEUI;
import neembuu.release1.api.ui.Progress;
import neembuu.release1.api.ui.access.ChangeDownloadModeUIA;
import neembuu.release1.api.ui.access.CloseActionUIA;
import neembuu.release1.api.ui.access.ExpandActionUIA;
import neembuu.release1.api.ui.access.GraphUIA;
import neembuu.release1.api.ui.access.LowerControlsUIA;
import neembuu.release1.api.ui.access.ProgressUIA;
import neembuu.release1.api.ui.access.split.MultiLinkProgressUI;
import neembuu.release1.api.ui.actions.ChangeDownloadModeAction;
import neembuu.release1.api.ui.actions.CloseAction;
import neembuu.release1.api.ui.actions.DeleteAction;
import neembuu.release1.api.ui.actions.ExpandAction;
import neembuu.release1.api.ui.actions.ConnectionActions;
import neembuu.release1.api.ui.actions.OpenAction;
import neembuu.release1.api.ui.actions.ReAddAction;
import neembuu.release1.api.ui.actions.SaveAction;
import neembuu.release1.ui.Colors;
import neembuu.release1.ui.Fonts;
import neembuu.release1.ui.GraphImpl;
import neembuu.release1.ui.HeightPropertyImpl;
import neembuu.release1.ui.ProgressImpl;
import neembuu.swing.TextBubbleBorder;

/**
 *
 * @author Shashank Tulsyan
 */
final class GenericLinkPanel extends javax.swing.JPanel {

    private final TextBubbleBorder border;
    private final Graph graph;
    private final Progress progress;
    
    private final int ht_smallest = 50;
    private final int ht_medium = 80;
    private final int ht_tallest = 300;
    
    private final RightControlsPanel rightControlsPanel = 
            RightControlsPanel.makeRightControlPanel();
    private final FileIconPanel fileIconPanel = new FileIconPanel();

    private ExpandAction expandAction;
    private DeleteAction deleteAction;
    private ReAddAction reAddAction;
    private ChangeDownloadModeAction  changeDownloadModeAction;
    private ConnectionActions connectionActions;
    
    
    private final String downloadFullFileToolTip = "<html>"
                + "<b>Download entire file mode</b><br/>"
                + "In this mode Neembuu tried to download<br/>"
                + "the entire file without slowing the download speed.<br/>"
                + "</html>";

    final HeightProperty heightProperty =  new HeightPropertyImpl();
    
    /**
     * Creates new form 
     */
    GenericLinkPanel() {
        border = new TextBubbleBorder(Colors.BORDER , 4, 16, 0);
        border.getBorderInsets(null).bottom = 8;
        border.getBorderInsets(null).top = 8;
        border.getBorderInsets(null).right = 1;
        border.getBorderInsets(null).left = 1;
        
        initComponents();
        initHeight();
        hiddenPaneInit();
        overlayInit();
        graph = new GraphImpl(graphUIA);
        progress =  new ProgressImpl(progressUIA,graph);
        changeDownloadModeButton.setToolTipText(downloadFullFileToolTip);
        killConnectionButton.setEnabled(false);
    }
    
    void initActions(
            ExpandAction expandAction, OpenAction openAction, 
            CloseAction closeAction, DeleteAction deleteAction, 
            ReAddAction reAddAction, SaveAction saveAction, 
            ConnectionActions connectionActions, ChangeDownloadModeAction  changeDownloadModeAction) {
        this.expandAction = expandAction;
        this.deleteAction = deleteAction;
        this.reAddAction = reAddAction;
        this.connectionActions = connectionActions;
        this.changeDownloadModeAction = changeDownloadModeAction;
        rightControlsPanel.initActions(expandAction, saveAction, closeAction);
        fileIconPanel.getOpenButton().addActionListener(openAction);
    }
    
    
    void initializeName(String fileName){
        fileNameLabel.setText(fileName);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        layeredPane = new javax.swing.JLayeredPane();
        actualContentsPanel = new javax.swing.JPanel();
        vlcPane = getFileIconPanelWithButton();
        fileNamePane = new javax.swing.JPanel();
        fileNameLabel = new javax.swing.JLabel();
        rightCtrlPane = rightControlsPanel.getPanel();
        sizeAndProgressPane = new javax.swing.JPanel();
        fileSizeLabel = new javax.swing.JLabel();
        progressBarPanel = new javax.swing.JPanel();
        progressPercetLabel = new javax.swing.JLabel();
        sizeAndProgressPane_ofSplit = new javax.swing.JPanel();
        progressBarPanel_ofSplit = new javax.swing.JPanel();
        progressPercetLabel_ofSplit = new javax.swing.JLabel();
        splitNumberComboBox = new javax.swing.JComboBox();
        graphPanel = new javax.swing.JPanel();
        hiddenStatsPane = new javax.swing.JPanel();
        selectedConnectionLabel = new javax.swing.JLabel();
        connectionControlPane = new javax.swing.JPanel();
        previousConnectionButton = new javax.swing.JButton();
        nextConnectionButton = new javax.swing.JButton();
        killConnectionButton = new javax.swing.JButton();
        changeDownloadModeButton = new javax.swing.JToggleButton();
        linkEditButton = new javax.swing.JButton();
        overlay = new javax.swing.JPanel();
        reEnableButton = HiddenBorderButton.make("images/small+.png", "images/small+_s.png", false);
        delete = HiddenBorderButton.make("images/delete.png", "images/delete_s.png", false);

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(border);

        actualContentsPanel.setBackground(new java.awt.Color(255, 255, 255));

        vlcPane.setBackground(new java.awt.Color(255, 255, 255));
        vlcPane.setToolTipText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.vlcPane.toolTipText")); // NOI18N
        vlcPane.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        vlcPane.setPreferredSize(new java.awt.Dimension(32, 32));

        javax.swing.GroupLayout vlcPaneLayout = new javax.swing.GroupLayout(vlcPane);
        vlcPane.setLayout(vlcPaneLayout);
        vlcPaneLayout.setHorizontalGroup(
            vlcPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );
        vlcPaneLayout.setVerticalGroup(
            vlcPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        fileNamePane.setBackground(new java.awt.Color(255, 255, 255));
        fileNamePane.setFocusable(false);
        fileNamePane.setPreferredSize(new java.awt.Dimension(200, 40));

        fileNameLabel.setBackground(new java.awt.Color(255, 255, 255));
        fileNameLabel.setFont(Fonts.MyriadPro.deriveFont(17f));
        fileNameLabel.setText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.fileNameLabel.text")); // NOI18N
        fileNameLabel.setMaximumSize(new java.awt.Dimension(152, 32));
        fileNameLabel.setMinimumSize(new java.awt.Dimension(152, 32));
        fileNameLabel.setPreferredSize(new java.awt.Dimension(152, 32));

        javax.swing.GroupLayout fileNamePaneLayout = new javax.swing.GroupLayout(fileNamePane);
        fileNamePane.setLayout(fileNamePaneLayout);
        fileNamePaneLayout.setHorizontalGroup(
            fileNamePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fileNamePaneLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(fileNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        fileNamePaneLayout.setVerticalGroup(
            fileNamePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fileNamePaneLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(fileNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        rightCtrlPane.setBackground(new java.awt.Color(255, 255, 255));
        rightCtrlPane.setMinimumSize(new java.awt.Dimension(56, 32));
        rightCtrlPane.setName(""); // NOI18N
        rightCtrlPane.setPreferredSize(new java.awt.Dimension(74, 32));
        rightCtrlPane.setLayout(null);

        sizeAndProgressPane.setBackground(new java.awt.Color(255, 255, 255));
        sizeAndProgressPane.setMinimumSize(new java.awt.Dimension(100, 30));
        sizeAndProgressPane.setPreferredSize(new java.awt.Dimension(300, 30));

        fileSizeLabel.setBackground(new java.awt.Color(255, 255, 255));
        fileSizeLabel.setFont(Fonts.MyriadPro.deriveFont(15f));
        fileSizeLabel.setText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.fileSizeLabel.text")); // NOI18N

        javax.swing.GroupLayout progressBarPanelLayout = new javax.swing.GroupLayout(progressBarPanel);
        progressBarPanel.setLayout(progressBarPanelLayout);
        progressBarPanelLayout.setHorizontalGroup(
            progressBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        progressBarPanelLayout.setVerticalGroup(
            progressBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        progressPercetLabel.setFont(Fonts.MyriadPro.deriveFont(18f)
        );
        progressPercetLabel.setText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.progressPercetLabel.text")); // NOI18N

        javax.swing.GroupLayout sizeAndProgressPaneLayout = new javax.swing.GroupLayout(sizeAndProgressPane);
        sizeAndProgressPane.setLayout(sizeAndProgressPaneLayout);
        sizeAndProgressPaneLayout.setHorizontalGroup(
            sizeAndProgressPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sizeAndProgressPaneLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(fileSizeLabel)
                .addGap(25, 25, 25)
                .addComponent(progressBarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(progressPercetLabel)
                .addGap(10, 10, 10))
        );
        sizeAndProgressPaneLayout.setVerticalGroup(
            sizeAndProgressPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fileSizeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sizeAndProgressPaneLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(progressBarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(2, 2, 2))
            .addComponent(progressPercetLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        sizeAndProgressPane_ofSplit.setBackground(new java.awt.Color(255, 255, 255));
        sizeAndProgressPane_ofSplit.setMinimumSize(new java.awt.Dimension(100, 30));
        sizeAndProgressPane_ofSplit.setPreferredSize(new java.awt.Dimension(300, 30));

        javax.swing.GroupLayout progressBarPanel_ofSplitLayout = new javax.swing.GroupLayout(progressBarPanel_ofSplit);
        progressBarPanel_ofSplit.setLayout(progressBarPanel_ofSplitLayout);
        progressBarPanel_ofSplitLayout.setHorizontalGroup(
            progressBarPanel_ofSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        progressBarPanel_ofSplitLayout.setVerticalGroup(
            progressBarPanel_ofSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        progressPercetLabel_ofSplit.setFont(Fonts.MyriadPro.deriveFont(18f)
        );
        progressPercetLabel_ofSplit.setText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.progressPercetLabel_ofSplit.text")); // NOI18N

        splitNumberComboBox.setFont(Fonts.MyriadPro.deriveFont(11f));
        splitNumberComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "001", "001/100MB", "700MB/420p", "1080p/700MB", "1080p/.7GB", "0.7GB/1080p", "70.0MB", "420p/500MB" }));
        splitNumberComboBox.setMinimumSize(new java.awt.Dimension(30, 16));
        splitNumberComboBox.setPreferredSize(new java.awt.Dimension(45, 16));

        javax.swing.GroupLayout sizeAndProgressPane_ofSplitLayout = new javax.swing.GroupLayout(sizeAndProgressPane_ofSplit);
        sizeAndProgressPane_ofSplit.setLayout(sizeAndProgressPane_ofSplitLayout);
        sizeAndProgressPane_ofSplitLayout.setHorizontalGroup(
            sizeAndProgressPane_ofSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sizeAndProgressPane_ofSplitLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(splitNumberComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(progressBarPanel_ofSplit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(progressPercetLabel_ofSplit)
                .addGap(10, 10, 10))
        );
        sizeAndProgressPane_ofSplitLayout.setVerticalGroup(
            sizeAndProgressPane_ofSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sizeAndProgressPane_ofSplitLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(progressBarPanel_ofSplit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(2, 2, 2))
            .addComponent(progressPercetLabel_ofSplit, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sizeAndProgressPane_ofSplitLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(splitNumberComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        graphPanel.setPreferredSize(new java.awt.Dimension(364, 100));

        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        hiddenStatsPane.setBackground(new java.awt.Color(255, 255, 255));
        hiddenStatsPane.setMaximumSize(new java.awt.Dimension(32767, 16));

        selectedConnectionLabel.setText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.selectedConnectionLabel.text")); // NOI18N

        javax.swing.GroupLayout hiddenStatsPaneLayout = new javax.swing.GroupLayout(hiddenStatsPane);
        hiddenStatsPane.setLayout(hiddenStatsPaneLayout);
        hiddenStatsPaneLayout.setHorizontalGroup(
            hiddenStatsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hiddenStatsPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectedConnectionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        hiddenStatsPaneLayout.setVerticalGroup(
            hiddenStatsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hiddenStatsPaneLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(selectedConnectionLabel))
        );

        connectionControlPane.setBackground(new java.awt.Color(255, 255, 255));

        previousConnectionButton.setFont(Fonts.MyriadPro.deriveFont(10f).deriveFont(Font.BOLD)
        );
        previousConnectionButton.setText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.previousConnectionButton.text")); // NOI18N
        previousConnectionButton.setToolTipText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.previousConnectionButton.toolTipText")); // NOI18N
        previousConnectionButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        previousConnectionButton.setPreferredSize(new java.awt.Dimension(79, 18));
        previousConnectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousConnectionButtonActionPerformed(evt);
            }
        });

        nextConnectionButton.setFont(Fonts.MyriadPro.deriveFont(10f).deriveFont(Font.BOLD)
        );
        nextConnectionButton.setText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.nextConnectionButton.text")); // NOI18N
        nextConnectionButton.setToolTipText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.nextConnectionButton.toolTipText")); // NOI18N
        nextConnectionButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        nextConnectionButton.setPreferredSize(new java.awt.Dimension(79, 18));
        nextConnectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextConnectionButtonActionPerformed(evt);
            }
        });

        killConnectionButton.setFont(Fonts.MyriadPro.deriveFont(10f));
        killConnectionButton.setText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.killConnectionButton.text")); // NOI18N
        killConnectionButton.setToolTipText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.killConnectionButton.toolTipText")); // NOI18N
        killConnectionButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        killConnectionButton.setPreferredSize(new java.awt.Dimension(79, 18));
        killConnectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                killConnectionButtonActionPerformed(evt);
            }
        });

        changeDownloadModeButton.setBackground(Colors.PROGRESS_BAR_FILL_BUFFER);
        changeDownloadModeButton.setFont(Fonts.MyriadPro.deriveFont(10f));
        changeDownloadModeButton.setText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.changeDownloadModeButton.text")); // NOI18N
        changeDownloadModeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeDownloadModeButtonActionPerformed(evt);
            }
        });

        linkEditButton.setFont(Fonts.MyriadPro.deriveFont(10f)
        );
        linkEditButton.setText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.linkEditButton.text")); // NOI18N
        linkEditButton.setToolTipText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.linkEditButton.toolTipText")); // NOI18N
        linkEditButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        linkEditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                linkEditButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout connectionControlPaneLayout = new javax.swing.GroupLayout(connectionControlPane);
        connectionControlPane.setLayout(connectionControlPaneLayout);
        connectionControlPaneLayout.setHorizontalGroup(
            connectionControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(connectionControlPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(previousConnectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nextConnectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(killConnectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(linkEditButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(changeDownloadModeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        connectionControlPaneLayout.setVerticalGroup(
            connectionControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(connectionControlPaneLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(connectionControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(previousConnectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nextConnectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(killConnectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changeDownloadModeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(linkEditButton, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2))
        );

        javax.swing.GroupLayout actualContentsPanelLayout = new javax.swing.GroupLayout(actualContentsPanel);
        actualContentsPanel.setLayout(actualContentsPanelLayout);
        actualContentsPanelLayout.setHorizontalGroup(
            actualContentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actualContentsPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(actualContentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                    .addComponent(connectionControlPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hiddenStatsPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(actualContentsPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(actualContentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(actualContentsPanelLayout.createSequentialGroup()
                                .addComponent(vlcPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(fileNamePane, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                                .addGap(10, 10, 10)
                                .addComponent(rightCtrlPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(sizeAndProgressPane, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                            .addComponent(sizeAndProgressPane_ofSplit, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))))
                .addContainerGap())
        );
        actualContentsPanelLayout.setVerticalGroup(
            actualContentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actualContentsPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(actualContentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(actualContentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(fileNamePane, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(vlcPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(rightCtrlPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(sizeAndProgressPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(sizeAndProgressPane_ofSplit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(graphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(hiddenStatsPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(connectionControlPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        overlay.setBackground(Colors.OVERLAY);
        overlay.setOpaque(false);
        overlay.setPreferredSize(new java.awt.Dimension(40, 40));

        reEnableButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/neembuu/release1/ui/images/small+.png"))); // NOI18N
        reEnableButton.setText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.reEnableButton.text")); // NOI18N
        reEnableButton.setToolTipText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.reEnableButton.toolTipText")); // NOI18N
        reEnableButton.setContentAreaFilled(false);
        reEnableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reEnableButtonActionPerformed(evt);
            }
        });

        delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/neembuu/release1/ui/images/delete.png"))); // NOI18N
        delete.setText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.delete.text")); // NOI18N
        delete.setToolTipText(org.openide.util.NbBundle.getMessage(GenericLinkPanel.class, "GenericLinkPanel.delete.toolTipText")); // NOI18N
        delete.setContentAreaFilled(false);
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout overlayLayout = new javax.swing.GroupLayout(overlay);
        overlay.setLayout(overlayLayout);
        overlayLayout.setHorizontalGroup(
            overlayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, overlayLayout.createSequentialGroup()
                .addContainerGap(298, Short.MAX_VALUE)
                .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reEnableButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        overlayLayout.setVerticalGroup(
            overlayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(overlayLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(overlayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reEnableButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 119, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layeredPaneLayout = new javax.swing.GroupLayout(layeredPane);
        layeredPane.setLayout(layeredPaneLayout);
        layeredPaneLayout.setHorizontalGroup(
            layeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layeredPaneLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(actualContentsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
            .addGroup(layeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layeredPaneLayout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addComponent(overlay, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                    .addGap(10, 10, 10)))
        );
        layeredPaneLayout.setVerticalGroup(
            layeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layeredPaneLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(actualContentsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
            .addGroup(layeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layeredPaneLayout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(overlay, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        layeredPane.setLayer(actualContentsPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredPane.setLayer(overlay, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(layeredPane)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layeredPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void killConnectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_killConnectionButtonActionPerformed
        connectionActions.kill(evt);
    }//GEN-LAST:event_killConnectionButtonActionPerformed

    private void nextConnectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextConnectionButtonActionPerformed
        connectionActions.next(evt);
    }//GEN-LAST:event_nextConnectionButtonActionPerformed

    private void changeDownloadModeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeDownloadModeButtonActionPerformed
        changeDownloadModeAction.actionPerformed(evt);
    }//GEN-LAST:event_changeDownloadModeButtonActionPerformed

    private void previousConnectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousConnectionButtonActionPerformed
        connectionActions.previous(evt);
    }//GEN-LAST:event_previousConnectionButtonActionPerformed

    private void linkEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_linkEditButtonActionPerformed
        throw new UnsupportedOperationException();
    }//GEN-LAST:event_linkEditButtonActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        deleteAction.actionPerformed(evt);
    }//GEN-LAST:event_deleteActionPerformed

    private void reEnableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reEnableButtonActionPerformed
        reAddAction.actionPerformed(evt,true);
    }//GEN-LAST:event_reEnableButtonActionPerformed

    private JPanel getFileIconPanelWithButton(){
        return fileIconPanel.getJPanel();
    }

    private void initHeight(){
        sizeAndProgressPane.setVisible(false);
        graphPanel.setVisible(false);
        hiddenStatsPane.setVisible(false);
        connectionControlPane.setVisible(false);

        heightProperty.setValue(ht_smallest);
    }

    private void overlayInit(){
        overlay.setVisible(false);
        delete.setVisible(false);
        reEnableButton.setVisible(false);
        MouseAdapter ma = new  MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                delete.setVisible(true);
                reEnableButton.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                delete.setVisible(false);
                reEnableButton.setVisible(false);
            }
            
        };
        overlay.addMouseListener(ma);
        delete.addMouseListener(ma);
        reEnableButton.addMouseListener(ma);
    }
    
    private void hiddenPaneInit(){
        javax.swing.GroupLayout layout = (javax.swing.GroupLayout)hiddenStatsPane.getLayout();
        layout.setHonorsVisibility(selectedConnectionLabel, Boolean.FALSE);
        
        nextConnectionButton.setBackground(Colors.BUTTON_TINT);
        previousConnectionButton.setBackground(Colors.BUTTON_TINT);
        linkEditButton.setBackground(Colors.BUTTON_TINT);
        killConnectionButton.setBackground(Colors.BUTTON_TINT);
        
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                String s = progress.getSelectedRangeTooltip();
                if(s==null){s="";}
                selectedConnectionLabel.setText(s.replace("\n", " "));
                selectedConnectionLabel.setVisible(true);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseEntered(e);
            }
            

            @Override
            public void mouseExited(MouseEvent e) {
                selectedConnectionLabel.setVisible(false);
            }
        }; 
        hiddenStatsPane.addMouseListener(ma);
        ma.mouseExited(null);
    }

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actualContentsPanel;
    private javax.swing.JToggleButton changeDownloadModeButton;
    private javax.swing.JPanel connectionControlPane;
    private javax.swing.JButton delete;
    private javax.swing.JLabel fileNameLabel;
    private javax.swing.JPanel fileNamePane;
    private javax.swing.JLabel fileSizeLabel;
    private javax.swing.JPanel graphPanel;
    private javax.swing.JPanel hiddenStatsPane;
    private javax.swing.JButton killConnectionButton;
    private javax.swing.JLayeredPane layeredPane;
    private javax.swing.JButton linkEditButton;
    private javax.swing.JButton nextConnectionButton;
    private javax.swing.JPanel overlay;
    private javax.swing.JButton previousConnectionButton;
    private javax.swing.JPanel progressBarPanel;
    private javax.swing.JPanel progressBarPanel_ofSplit;
    private javax.swing.JLabel progressPercetLabel;
    private javax.swing.JLabel progressPercetLabel_ofSplit;
    private javax.swing.JButton reEnableButton;
    private javax.swing.JPanel rightCtrlPane;
    private javax.swing.JLabel selectedConnectionLabel;
    private javax.swing.JPanel sizeAndProgressPane;
    private javax.swing.JPanel sizeAndProgressPane_ofSplit;
    private javax.swing.JComboBox splitNumberComboBox;
    private javax.swing.JPanel vlcPane;
    // End of variables declaration//GEN-END:variables

    private final ProgressUIA progressUIA = new ProgressUIA() {
        @Override public JButton saveButton() { return rightControlsPanel.getSaveBtn(); }
        @Override public ExpansionState getExpansionState() {return expandAction.getExpansionState(); }
        @Override public JLabel progressPercetLabel() { return progressPercetLabel;}
        @Override public JButton killConnectionButton() { return killConnectionButton;}
        @Override public JPanel progressBarPanel() { return progressBarPanel;} };
    
    private final GraphUIA graphUIA = new GraphUIA() {
        @Override public JPanel graphPanel() {return graphPanel;}
        @Override public ExpansionState getExpansionState() {return expandAction.getExpansionState();}  };
    
    final LowerControlsUIA lowerControlsUIA = new LowerControlsUIA(){
        @Override public Progress progress() { return progress; } };

    final ChangeDownloadModeUIA changeDownloadModeUIA = new ChangeDownloadModeUIA() {
        @Override public JToggleButton changeDownloadModeButton() { return changeDownloadModeButton; }
        @Override public void repaintProgressBar() {progress.repaint();} };
    
    final CloseActionUIA closeActionUIA = new CloseActionUIA() {
        @Override public JPanel overlay() { return overlay; }
        @Override public TextBubbleBorder border() { return border; }
        @Override public JPanel rightControlsPanel() { return rightCtrlPane; }
        @Override public JLabel fileNameLabel() { return fileNameLabel; }
        @Override public OpenButton openButton() { return openButton; }
        @Override public void contract() { expandAction.setExpansionState(ExpansionState.Contracted); }
        @Override public void repaint() { GenericLinkPanel.this.repaint(); }
        @Override public JLabel fileSizeLabel(){return fileSizeLabel;}  };
    
    final ExpandActionUIA expandActionUIA = new ExpandActionUIA() {
        @Override public JPanel connectionControlPane() { return connectionControlPane; }
        @Override public JPanel graphPanel() { return graphPanel; }
        @Override public JPanel sizeAndProgressPane() { return sizeAndProgressPane; }
        @Override public JPanel hiddenStatsPane() { return hiddenStatsPane; }
        @Override public void initGraph(boolean findFirst) { graph.initGraph(null, findFirst); }
        @Override public short ht_smallest() { return ht_smallest; }
        @Override public short ht_medium() { return ht_medium; }
        @Override public short ht_tallest() { return ht_tallest; }
        @Override public HeightProperty getHeight() { return heightProperty; } };
    
    final CloseActionUIA.OpenButton openButton = new CloseActionUIA.OpenButton() {
        @Override public void setVisible(boolean v) { fileIconPanel.getOpenButton().setVisible(v); }
        @Override public void setIcon_bw(Icon bw) { fileIconPanel.getOpenButton().setIcon_bw(bw);}
        @Override public void setIcon_clr(Icon clr) {fileIconPanel.getOpenButton().setIcon_clr(clr);}
        @Override public String getCaption() { return fileIconPanel.getCaption(); }
        @Override public void setCaption(String caption) { fileIconPanel.setCaption(caption); } };
    
    final MultiLinkProgressUI multiLinkProgressUI = new MultiLinkProgressUI() {
        @Override public JLabel progressPercetLabel() {return progressPercetLabel_ofSplit; }
        @Override public JPanel progressBarPanel() { return progressBarPanel_ofSplit;}
        @Override public JComboBox linksComboBox() { return splitNumberComboBox;}};
    
    final OpenableEUI expandableUI = new OpenableEUI() {
        @Override public JComponent getJComponent() { return GenericLinkPanel.this; }
        @Override public HeightProperty heightProperty() { return heightProperty; }
        @Override public void open(){ reAddAction.actionPerformed(null, false); }
    };
}