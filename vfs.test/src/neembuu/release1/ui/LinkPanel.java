/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import neembuu.rangearray.Range;
import neembuu.rangearray.UIRangeArrayAccess;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.release1.Main;
import neembuu.vfs.file.SeekableConnectionFile;

/**
 *
 * @author Shashank Tulsyan
 */
final class LinkPanel extends javax.swing.JPanel {

    private TextBubbleBorder border;
    final Graph graph;
    final Progress progress;
    
    private final int ht_smallest = 50;
    private final int ht_medium = 80;
    private final int ht_tallest = 300;
    
    private int ht_old = 0;
    private int ht_new = 0;
    
    private int state = 1;    
    
    final RightControlsPanel rightControlsPanel = new RightControlsPanel();
    final FileIconPanel fileIconPanel = new FileIconPanel();
    final SingleFileLinkUI singleFileLinkUI;
    
    private final String downloadFullFileToolTip = "<html>"
                + "<b>Download entire file mode</b><br/>"
                + "In this mode Neembuu tried to download<br/>"
                + "the entire file without slowing the download speed.<br/>"
                + "</html>";
    
    private final String downloadMinimumToolTip = "<html>"
                + "<b>Download Minimum Mode</b><br/>"
                + "In this mode Neembuu limits the download speed.<br/>"
                + "This mode is useful for people with limited internet<br/>"
                + "usage plans."
                + "</html>";

    /**
     * Creates new form FilePanel
     */
    LinkPanel() {
        this(null);
    }
        
    LinkPanel(SingleFileLinkUI singleFileLinkUI) {
        this.singleFileLinkUI = singleFileLinkUI;
        border = new TextBubbleBorder(Colors.BORDER , 4, 16, 0);
        border.getBorderInsets(null).bottom = 8;
        border.getBorderInsets(null).top = 8;
        border.getBorderInsets(null).right = 1;
        border.getBorderInsets(null).left = 1;
        initComponents();
        initHeight();
        hiddenPaneInit();
        overlayInit();
        graph = new Graph(this);
        progress =  new Progress();
        changeDownloadModeButton.setToolTipText(downloadFullFileToolTip);
        killConnectionButton.setEnabled(false);
    }
    
    void setFile(){

        fileNameLabel.setText(singleFileLinkUI.getVirtualFile().getConnectionFile().getName());
        updateFileSizeString();
        progress.init(this,singleFileLinkUI.getVirtualFile());

        try{
            if(!getAsRealFile().exists()){
                throw new IllegalStateException("File not created yet");
            }
            Icon clr = null, bw = null;
            try{
                Image clri = sun.awt.shell.ShellFolder.getShellFolder( getAsRealFile() ).getIcon( true ) ;
                clr = new ImageIcon(clri);
            }catch(Exception a){
                clr = javax.swing.filechooser.FileSystemView.getFileSystemView().getSystemIcon( getAsRealFile() );
            }
            bw = TintedGreyScaledImage.getTintedImage(getBF(clr), Colors.TINTED_IMAGE, false);
            fileIconPanel.makeOpenButton(vlcPane, bw, clr, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openVirtualFile();
                }
            } );
        }catch(Exception a){
            Main.getLOGGER().log(Level.INFO, "Could not find icon, using default", a);
        }
    }
    
    private static BufferedImage getBF(Icon icon){
        BufferedImage bi = new BufferedImage(
            icon.getIconWidth(),
            icon.getIconHeight(),
            BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
        // paint the Icon to the BufferedImage.
        icon.paintIcon(null, g, 0,0);
        return bi;
    }
    
    private static BufferedImage getBF(Image img){
        BufferedImage bi = new BufferedImage(32,32,BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        g.setColor(Color.WHITE);
        bi.getGraphics().drawImage(img, 0, 0 , null);
        return bi;
    }
    
    void closeAction(){
        closeAction(true);
    }
        
    void closeAction(boolean closeOrOpen){
        rightCtrlPane.setVisible(!closeOrOpen);
        overlay.setVisible(closeOrOpen);
        if(closeOrOpen){
            this.border.setColor(Color.WHITE);
            fileNameLabel.setForeground(Colors.BORDER);
            setExpansionState(ExpansionState.Contracted);
            singleFileLinkUI.deactivateOpenButton(true);
            closeActionProcess();
        }else {
            this.border.setColor(Colors.BORDER);
            this.repaint();
            fileNameLabel.setForeground(Color.BLACK);
            fileNameLabel.setText(singleFileLinkUI.getVirtualFile().getConnectionFile().getName());
            singleFileLinkUI.deactivateOpenButton(false);
            singleFileLinkUI.getMountManager().addFile(singleFileLinkUI.getVirtualFile());
            openVirtualFile();
        }
    }
    
    void closeActionProcess(){
        singleFileLinkUI.getMountManager().removeFile(singleFileLinkUI.getVirtualFile());
        try{
            singleFileLinkUI.getVirtualFile().getConnectionFile().closeCompletely();
        }catch(Exception a){
            Main.getLOGGER().log(Level.SEVERE, "Erorr in completely closing file",a);
        }
    }
    
    void saveAction(File outputFilePath){
        closeActionProcess();

        try{
            singleFileLinkUI.getVirtualFile().getConnectionFile().getFileStorageManager().completeSession(outputFilePath, singleFileLinkUI.getVirtualFile().getConnectionFile().getFileSize());
        }catch(Exception a){
            Main.getLOGGER().log(Level.SEVERE, "Could not save file",a);
            JOptionPane.showMessageDialog(singleFileLinkUI.getNeembuuUI().getFrame(), a.getMessage(),"Could not save file", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    void saveFileClicked(){
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setSelectedFile(new File(System.getProperty("java.home")+File.separator+
                singleFileLinkUI.getVirtualFile().getConnectionFile().getName()));
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
        int retVal = fileChooser.showSaveDialog(singleFileLinkUI.getNeembuuUI().getFrame());
        if(retVal == javax.swing.JFileChooser.APPROVE_OPTION){
            saveAction(fileChooser.getSelectedFile().getAbsoluteFile());
        }else {

        }
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
        overlay = new javax.swing.JPanel();
        reEnableButton = HiddenBorderButton.make("images/small+.png", "images/small+_s.png", false);
        delete = HiddenBorderButton.make("images/delete.png", "images/delete_s.png", false);
        actualContentsPanel = new javax.swing.JPanel();
        vlcPane = getFileIconPanelWithButton();
        fileNamePane = new javax.swing.JPanel();
        fileNameLabel = new javax.swing.JLabel();
        rightCtrlPane = getRightControlPanel();
        sizeAndProgressPane = new javax.swing.JPanel();
        fileSizeLabel = new javax.swing.JLabel();
        progressBarPanel = new javax.swing.JPanel();
        progressPercetLabel = new javax.swing.JLabel();
        graphPanel = new javax.swing.JPanel();
        hiddenStatsPane = new javax.swing.JPanel();
        selectedConnectionLabel = new javax.swing.JLabel();
        connectionControlPane = new javax.swing.JPanel();
        previousConnectionButton = new javax.swing.JButton();
        nextConnectionButton = new javax.swing.JButton();
        killConnectionButton = new javax.swing.JButton();
        changeDownloadModeButton = new javax.swing.JToggleButton();
        linkEditButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(border);

        overlay.setBackground(Colors.OVERLAY);
        overlay.setOpaque(false);
        overlay.setPreferredSize(new java.awt.Dimension(40, 40));

        reEnableButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/neembuu/release1/ui/images/small+.png"))); // NOI18N
        reEnableButton.setText(org.openide.util.NbBundle.getMessage(LinkPanel.class, "LinkPanel.reEnableButton.text")); // NOI18N
        reEnableButton.setToolTipText(org.openide.util.NbBundle.getMessage(LinkPanel.class, "LinkPanel.reEnableButton.toolTipText")); // NOI18N
        reEnableButton.setContentAreaFilled(false);
        reEnableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reEnableButtonActionPerformed(evt);
            }
        });

        delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/neembuu/release1/ui/images/delete.png"))); // NOI18N
        delete.setText(org.openide.util.NbBundle.getMessage(LinkPanel.class, "LinkPanel.delete.text")); // NOI18N
        delete.setToolTipText(org.openide.util.NbBundle.getMessage(LinkPanel.class, "LinkPanel.delete.toolTipText")); // NOI18N
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

        actualContentsPanel.setBackground(new java.awt.Color(255, 255, 255));

        vlcPane.setBackground(new java.awt.Color(255, 255, 255));
        vlcPane.setToolTipText(org.openide.util.NbBundle.getMessage(LinkPanel.class, "LinkPanel.vlcPane.toolTipText")); // NOI18N
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
        fileNameLabel.setText(org.openide.util.NbBundle.getMessage(LinkPanel.class, "LinkPanel.fileNameLabel.text")); // NOI18N
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
        fileSizeLabel.setText(org.openide.util.NbBundle.getMessage(LinkPanel.class, "LinkPanel.fileSizeLabel.text")); // NOI18N

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
        progressPercetLabel.setText(org.openide.util.NbBundle.getMessage(LinkPanel.class, "LinkPanel.progressPercetLabel.text")); // NOI18N

        javax.swing.GroupLayout sizeAndProgressPaneLayout = new javax.swing.GroupLayout(sizeAndProgressPane);
        sizeAndProgressPane.setLayout(sizeAndProgressPaneLayout);
        sizeAndProgressPaneLayout.setHorizontalGroup(
            sizeAndProgressPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sizeAndProgressPaneLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(fileSizeLabel)
                .addGap(20, 20, 20)
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

        graphPanel.setPreferredSize(new java.awt.Dimension(364, 100));

        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        hiddenStatsPane.setBackground(new java.awt.Color(255, 255, 255));
        hiddenStatsPane.setMaximumSize(new java.awt.Dimension(32767, 16));

        selectedConnectionLabel.setText(org.openide.util.NbBundle.getMessage(LinkPanel.class, "LinkPanel.selectedConnectionLabel.text")); // NOI18N

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
        previousConnectionButton.setText(org.openide.util.NbBundle.getMessage(LinkPanel.class, "LinkPanel.previousConnectionButton.text")); // NOI18N
        previousConnectionButton.setToolTipText(org.openide.util.NbBundle.getMessage(LinkPanel.class, "LinkPanel.previousConnectionButton.toolTipText")); // NOI18N
        previousConnectionButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        previousConnectionButton.setPreferredSize(new java.awt.Dimension(79, 18));
        previousConnectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousConnectionButtonActionPerformed(evt);
            }
        });

        nextConnectionButton.setFont(Fonts.MyriadPro.deriveFont(10f).deriveFont(Font.BOLD)
        );
        nextConnectionButton.setText(org.openide.util.NbBundle.getMessage(LinkPanel.class, "LinkPanel.nextConnectionButton.text")); // NOI18N
        nextConnectionButton.setToolTipText(org.openide.util.NbBundle.getMessage(LinkPanel.class, "LinkPanel.nextConnectionButton.toolTipText")); // NOI18N
        nextConnectionButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        nextConnectionButton.setPreferredSize(new java.awt.Dimension(79, 18));
        nextConnectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextConnectionButtonActionPerformed(evt);
            }
        });

        killConnectionButton.setFont(Fonts.MyriadPro.deriveFont(10f));
        killConnectionButton.setText(org.openide.util.NbBundle.getMessage(LinkPanel.class, "LinkPanel.killConnectionButton.text")); // NOI18N
        killConnectionButton.setToolTipText(org.openide.util.NbBundle.getMessage(LinkPanel.class, "LinkPanel.killConnectionButton.toolTipText")); // NOI18N
        killConnectionButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        killConnectionButton.setPreferredSize(new java.awt.Dimension(79, 18));
        killConnectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                killConnectionButtonActionPerformed(evt);
            }
        });

        changeDownloadModeButton.setBackground(Colors.PROGRESS_BAR_FILL_BUFFER);
        changeDownloadModeButton.setFont(Fonts.MyriadPro.deriveFont(10f));
        changeDownloadModeButton.setText(org.openide.util.NbBundle.getMessage(LinkPanel.class, "LinkPanel.changeDownloadModeButton.text")); // NOI18N
        changeDownloadModeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeDownloadModeButtonActionPerformed(evt);
            }
        });

        linkEditButton.setFont(Fonts.MyriadPro.deriveFont(10f)
        );
        linkEditButton.setText(org.openide.util.NbBundle.getMessage(LinkPanel.class, "LinkPanel.linkEditButton.text")); // NOI18N
        linkEditButton.setToolTipText(org.openide.util.NbBundle.getMessage(LinkPanel.class, "LinkPanel.linkEditButton.toolTipText")); // NOI18N
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
                            .addComponent(sizeAndProgressPane, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))))
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
                .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(hiddenStatsPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(connectionControlPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
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
                    .addComponent(overlay, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        layeredPane.setLayer(overlay, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layeredPane.setLayer(actualContentsPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
        // TODO add your handling code here:
        Range selection =  progress.getSelectedRange();
        if(selection == null){
            throw new RuntimeException("Null connection was selected. The kill button should automatically disable if no connection is selected");
        }
        UIRangeArrayAccess regions = singleFileLinkUI.getVirtualFile().getConnectionFile().getRegionHandlers();
        selection = regions.getUnsynchronized(selection.ending());
        try{
            ((neembuu.vfs.readmanager.impl.BasicRegionHandler)selection.getProperty()).
                    getConnection().abort();
        }catch(Exception any){
            Main.getLOGGER().log(Level.SEVERE, "Connection killing exception", any);
        }
    }//GEN-LAST:event_killConnectionButtonActionPerformed

    private void nextConnectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextConnectionButtonActionPerformed
        UIRangeArrayAccess regions = singleFileLinkUI.getVirtualFile().getConnectionFile().getRegionHandlers();
        if(regions.isEmpty()){
            return;
        }
        Range initial =  progress.getSelectedRange();
        initial = getClosestRange(initial);        
        Range next = regions.getNext(initial);
        progress.switchToRegion(next);
    }//GEN-LAST:event_nextConnectionButtonActionPerformed

    private Range getClosestRange(Range initial){        
        UIRangeArrayAccess regions = singleFileLinkUI.getVirtualFile().getConnectionFile().getRegionHandlers();
        UnsyncRangeArrayCopy unsyncFncCopy = regions.tryToGetUnsynchronizedCopy();
        
        if(initial == null){
            return regions.getFirst();
        }
        long ending = initial.ending();
        initial = regions.getUnsynchronized(initial.ending());
        
        if(initial!=null){
            return initial;
        }
        if(unsyncFncCopy.size()==0){
            return null;
        }
        Range closest = unsyncFncCopy.get(0);
        long dmin = ending - closest.ending();
        for (int i = 0; i < unsyncFncCopy.size(); i++) {
            Range range = unsyncFncCopy.get(i);
            long d = ending - range.ending();
            if (d<0) {
                break;
            }
            if(d<dmin){
                dmin = d;
                closest = range;
            }
        }
        return closest;
    }
    
    private void changeDownloadModeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeDownloadModeButtonActionPerformed
        if(changeDownloadModeButton.getModel().isSelected()){
            changeDownloadModeButton.setBackground(Colors.TINTED_IMAGE);
            changeDownloadModeButton.setText("Download Minimum");
            changeDownloadModeButton.setToolTipText(downloadMinimumToolTip);
        }else {
            changeDownloadModeButton.setText("Download Full File");
            changeDownloadModeButton.setBackground(Colors.PROGRESS_BAR_FILL_BUFFER);
            changeDownloadModeButton.setToolTipText(downloadFullFileToolTip);
        }
    
        SeekableConnectionFile file = singleFileLinkUI.getVirtualFile().getConnectionFile();
        file.setAutoCompleteEnabled(!file.isAutoCompleteEnabled());
        
        progress.repaint(); // the color of progress bar change this needs to be notified.
    }//GEN-LAST:event_changeDownloadModeButtonActionPerformed

    private void previousConnectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousConnectionButtonActionPerformed
        UIRangeArrayAccess regions = singleFileLinkUI.getVirtualFile().getConnectionFile().getRegionHandlers();
        if(regions.isEmpty()){
            return;
        }
        Range initial =  progress.getSelectedRange();
        initial = getClosestRange(initial);        
        Range previous = regions.getPrevious(initial);
        progress.switchToRegion(previous);
    }//GEN-LAST:event_previousConnectionButtonActionPerformed

    private void linkEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_linkEditButtonActionPerformed
        // TODO add your handling code here:
        //singleFileLinkUI.getVirtualFile().getRegions()
                
    }//GEN-LAST:event_linkEditButtonActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        int x = JOptionPane.showConfirmDialog(singleFileLinkUI.getNeembuuUI().getFrame(),"Are you sure you want to delete this file","Delete",JOptionPane.YES_NO_OPTION);
        if(x == JOptionPane.YES_OPTION){
            saveAction(null);
        }
        singleFileLinkUI.getLinkUIContainer().removeLinkUI(singleFileLinkUI);
    }//GEN-LAST:event_deleteActionPerformed

    private void reEnableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reEnableButtonActionPerformed
        closeAction(false);
    }//GEN-LAST:event_reEnableButtonActionPerformed

    
    private JPanel getFileIconPanelWithButton(){
        return fileIconPanel.getFileIconPanelWithButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openVirtualFile();
            }
        });
    }
    
    private JPanel getRightControlPanel(){
        return rightControlsPanel.getRightControlPanel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                expandContractPressed();
            }
        }, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFileClicked();
            }
        }, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeAction();
            }
        });
    }

    private void initHeight(){
        sizeAndProgressPane.setVisible(false);
        graphPanel.setVisible(false);
        hiddenStatsPane.setVisible(false);
        connectionControlPane.setVisible(false);

        ht_old = ht_new = ht_smallest;
    }
    
    private void expandContractPressed(){        
        if(state%3==0){
            setToContracted();
        }else if(state%3==1){
            setToSemiExpanded();
        }else {
            setToFullyExpanded();
        }//state++;
        ht_old = ht_new;
        singleFileLinkUI.getLinkUIContainer().animateShrinkActionPerformed(singleFileLinkUI);
    }
    
    private void setToContracted(){
        sizeAndProgressPane.setVisible(false);
        graphPanel.setVisible(false);{
            graph.initGraph(null);
        }
        hiddenStatsPane.setVisible(false);
        connectionControlPane.setVisible(false);
        ht_new = ht_smallest;
        ht_old = ht_tallest;
        
        state = 1;
    }
    
    private void setToSemiExpanded(){
        sizeAndProgressPane.setVisible(true);
        graphPanel.setVisible(false);{
            graph.initGraph(null);
        }
        hiddenStatsPane.setVisible(false);
        connectionControlPane.setVisible(false);
        ht_new = ht_medium;
        ht_old = ht_smallest;
        
        state = 2;
    }
    
    private void setToFullyExpanded(){
        sizeAndProgressPane.setVisible(true);
        graph.initGraph(null,true);
        graphPanel.setVisible(true);
        hiddenStatsPane.setVisible(true);
        connectionControlPane.setVisible(true);
        ht_new = ht_tallest;
        ht_old = ht_medium;
        
        state = 3;
    }
    
    public void openVirtualFile(){
        try{
            File f = getAsRealFile();
            java.awt.Desktop.getDesktop().open(f);

        }catch(Exception a){
            JOptionPane.showMessageDialog(null,a.getMessage(),"Could not open file",JOptionPane.ERROR_MESSAGE);
            Main.getLOGGER().log(Level.SEVERE,"Could not open file",a);
        }
    }
    
    private File getAsRealFile(){
        return new File(singleFileLinkUI.getMountManager().getMount().getMountLocation().getAsFile(),
                    singleFileLinkUI.getVirtualFile().getConnectionFile().getName());
    }
    
    int getH(double f){
        if(ht_old>ht_new){ // compressing
            return (int)(ht_new - (ht_old-ht_new)*f);
        }
        //expanding
        return (int)(ht_old + (ht_new-ht_old)*f);
    }
    
    int getMinH(){
        return Math.min(ht_old, ht_new);
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
    
    
    private void updateFileSizeString(){
        double sz = singleFileLinkUI.getVirtualFile().getConnectionFile().getFileSize();
        String suffix;
        if(sz < 1000){
            suffix = " B";
        }else if(sz < 1000*1000){
            suffix = " KB";
            sz/=1024;
        }else if(sz < 1000*1000*1000){
            suffix = " MB";
            sz/=1024*1024;
        }else if(sz < 1000*1000*1000*1000){
            suffix = " GB";
            sz/=1024*1024*1024;
        }else {
            suffix = " TB";
            sz/=1024*1024*1024*1024;
        }
        if(sz < 10){
            sz = Math.round(sz * 100.0) / 100.0;
        }else if(sz < 100){
            sz = Math.round(sz * 10.0) / 10.0;
        }
        fileSizeLabel.setText(sz+ " "+suffix);
    }

    public ExpansionState getExpansionState(){
        int s = this.state%3;
        switch (s) {
            case 0:
                return ExpansionState.FullyExpanded;
            case 1:
                return ExpansionState.Contracted;
            case 2:
                return ExpansionState.SemiExpanded;
            default:
                return ExpansionState.Contracted;
        }
    }
    
    public void setExpansionState(ExpansionState es){
        switch (es) {
            case FullyExpanded: setToFullyExpanded(); break;
            case Contracted: setToContracted(); break;
            case SemiExpanded: setToSemiExpanded(); break;
            default:
                throw new AssertionError();
        }
        ht_old = ht_new;
        singleFileLinkUI.getLinkUIContainer().animateShrinkActionPerformed(singleFileLinkUI);
    }
    
    public enum ExpansionState {
        Contracted,
        SemiExpanded,
        FullyExpanded
    }
    
    private static class TranslucentJPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f)); // draw transparent background
            super.paintComponent(g);
            ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // turn on opacity
            //g.setColor(new Color(255,0,0,100));
            g.setColor(Colors.OVERLAY);
            g.fillRect(0,0,getWidth(),getHeight());
            //super.paintComponent(g);
        }

    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actualContentsPanel;
    private javax.swing.JToggleButton changeDownloadModeButton;
    private javax.swing.JPanel connectionControlPane;
    private javax.swing.JButton delete;
    private javax.swing.JLabel fileNameLabel;
    private javax.swing.JPanel fileNamePane;
    private javax.swing.JLabel fileSizeLabel;
    javax.swing.JPanel graphPanel;
    private javax.swing.JPanel hiddenStatsPane;
    javax.swing.JButton killConnectionButton;
    private javax.swing.JLayeredPane layeredPane;
    private javax.swing.JButton linkEditButton;
    private javax.swing.JButton nextConnectionButton;
    javax.swing.JPanel overlay;
    private javax.swing.JButton previousConnectionButton;
    javax.swing.JPanel progressBarPanel;
    javax.swing.JLabel progressPercetLabel;
    private javax.swing.JButton reEnableButton;
    private javax.swing.JPanel rightCtrlPane;
    javax.swing.JLabel selectedConnectionLabel;
    javax.swing.JPanel sizeAndProgressPane;
    private javax.swing.JPanel vlcPane;
    // End of variables declaration//GEN-END:variables
}
