/*
 * MonitoredHttpFilePanel.java
 *
 * Created on 23 Dec, 2009, 11:01:09 PM
 */

package neembuu.vfs.test;

import java.awt.Color;
import java.awt.event.ActionEvent;
import neembuu.rangearray.ModificationType;
import neembuu.swing.RangeArrayElementColorProvider.SelectionState;
import neembuu.vfs.test.graphprovider.SpeedGraphJFluid;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import neembuu.rangearray.Range;
import neembuu.rangearray.RangeArrayListener;
import neembuu.rangearray.RangeUtils;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.swing.RangeArrayComponent;
import neembuu.swing.RangeArrayElementColorProvider;
import neembuu.swing.RangeArrayElementToolTipTextProvider;
import neembuu.swing.RangeSelectedListener;
import neembuu.util.logging.LoggerUtil;
import neembuu.vfs.file.MonitoredHttpFile;
import neembuu.vfs.readmanager.RegionHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public final class MonitoredSeekableHttpFilePanel
        extends
            javax.swing.JPanel
        implements
            RangeSelectedListener{

    private final MonitoredHttpFile file;
    private SpeedGraphJFluid graphJFluid = null;
    private volatile String virtualPathOfFile = null;
    private static final Logger LOGGER = LoggerUtil.getLogger();

    private volatile Range lastRegionSelected = null;
    private volatile RegionHandler lastRegionHandler = null;
    //private final Object rangeSelectedLock = new Object();
    RegionHandler previousRegionOfInterest;
    //Map<Long,Color> rm = new 
    private final RequestedRegionColorProvider requestedRegionColorProvider 
            = new RequestedRegionColorProvider();
    private final JLabel activateLable = new JLabel(
            "<html>Click on a <b>downloaded region</b> to activate speed chart</html>",
            new ImageIcon(MonitoredSeekableHttpFilePanel.class.getResource("activate.png")),
            SwingConstants.CENTER);
    
    private final RangeArrayElementColorProvider downloadedRegionColorProvider =
            new RangeArrayElementColorProvider() {

        @Override
        public Color getColor(Color defaultColor, Range element, SelectionState selectionState) {
            RegionHandler rh = (RegionHandler)element.getProperty();
            Color base = defaultColor;
            try{
                if(rh.isAlive()){
                    base = new Color(102,93,149);
                    //base = new Color(126,193,160);//RangeArrayComponent.lightenColor(Color.GREEN, 0.56f);
                }
            }catch(NullPointerException npe){
                return defaultColor;
            }
            if(rh.isMainDirectionOfDownload()){
                if(!rh.isAlive()){
                    base = new Color(168,122,92);
                }
                else{
                    //base = new Color(30, 50, 200);
                    base = new Color(138,170,231);//RangeArrayComponent.lightenColor(Color.BLUE, 0.56f);
                }
            }
            
            switch (selectionState) {
                case LIST: base = RangeArrayComponent.lightenColor(base, 0.9f); break;
                case MOUSE_OVER: base = RangeArrayComponent.lightenColor(base, 0.8f); break;
                case SELECTED: base = RangeArrayComponent.lightenColor(base, 0.7f); break;
                case NONE:
                    break;
            }
            
            return base;
        }
    };
    
    private static final class RequestedRegionColorProvider 
            implements RangeArrayElementColorProvider, RangeArrayListener {

        long lastModStart, lastModEnd;
        
        @Override
        public void rangeArrayModified(long modificationResultStart, long modificationResultEnd, Range elementOperated, ModificationType modificationType, boolean removed, long modCount) {
            lastModEnd = modificationResultEnd;
            lastModStart = modificationResultStart;
        }
        
        @Override
        public Color getColor(Color defaultColor, Range element, SelectionState selectionState) {
            
            Color base = defaultColor;
            if(RangeUtils.contains(element, lastModStart, lastModEnd)){
                base = new Color(138,170,231);
            }
            
            switch (selectionState) {
                case LIST: base = RangeArrayComponent.lightenColor(base, 0.9f); break;
                case MOUSE_OVER: base = RangeArrayComponent.lightenColor(base, 0.8f); break;
                case SELECTED: break;
                case NONE:break;
            }
            
            return base;
        }
    };
    
    private final RangeArrayElementToolTipTextProvider downloadedRegionToolTipProvider = new RangeArrayElementToolTipTextProvider(){

        @Override
        public String getToolTipText(Range element, long absolutePosition, long largestEntry, SelectionState selectionState) {
            return RangeArrayElementToolTipTextProvider.Default.makeRangeArrayElementToolTipText(element, absolutePosition, largestEntry, getAnnotationString(element));
        }
        
        private String getAnnotationString(Range element){
            if(!(element.getProperty() instanceof RegionHandler))return "";
            RegionHandler regionHandler = (RegionHandler)element.getProperty();
            String toRet = "";
            if(file.getDownloadConstrainHandler().isComplete()){
                toRet = "File completely downloaded \n";
            }
            return toRet + NumberFormat.getInstance().format( regionHandler.getThrottleStatistics().getDownloadSpeed_KiBps())+" KBps \n"+
                    " RequestSpeed = " + NumberFormat.getInstance().format( regionHandler.getThrottleStatistics().getRequestSpeed_KiBps())+" KBps \n"+
                    (regionHandler.isAlive()?"alive":"dead")+" "+(regionHandler.isMainDirectionOfDownload()?"main":"notmain")  ;
        }
        
    };
    
    final Timer updateGraphTimer = new Timer(500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            downloadSpeedVal.setText(
                    file.getTotalFileReadStatistics().getTotalAverageDownloadSpeedProvider().getDownloadSpeed_KiBps()
                    +" KBps");
            requestRateVal.setText(
                    file.getTotalFileReadStatistics().getTotalAverageRequestSpeedProvider().getRequestSpeed_KiBps()
                    +" KBps");
            
            
            RegionHandler currentlySelectedRegion = lastRegionHandler;
            if(currentlySelectedRegion==null){
                throttleStateLable.setText("");
                return;
            }
            if(graphJFluid!=null)
                graphJFluid.speedChanged(
                        currentlySelectedRegion.getThrottleStatistics().getDownloadSpeed_KiBps(),
                        currentlySelectedRegion.getThrottleStatistics().getRequestSpeed_KiBps());
            
            throttleStateLable.setText(currentlySelectedRegion.getThrottleStatistics().getThrottleState().toString());
        }
    });
    
    /** Creates new form MonitoredHttpFilePanel */
    public MonitoredSeekableHttpFilePanel(MonitoredHttpFile par) {
        this.file = par;
        initComponents();
        
        this.speedGraphPanel.setLayout(new BorderLayout());
        activateLable.setFont(new Font(activateLable.getFont().getName(),
                activateLable.getFont().getStyle(),(int)(activateLable.getFont().getSize()*1.5)));
        this.speedGraphPanel.add(activateLable);
        
        filenameLabel.setText(filenameLabel.getText()+par.getName());
        filesizeLabel.setText(filesizeLabel.getText()+par.getFileSize());
        connectionDescText.setText(par.getSourceDescription());

        ((RangeArrayComponent)regionDownloadedBar).addRangeSelectedListener(this);
        updateGraphTimer.start();
        
        file.getRequestedRegion().addRangeArrayListener(requestedRegionColorProvider);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        urlLabel = new javax.swing.JLabel();
        filesizeLabel = new javax.swing.JLabel();
        filenameLabel = new javax.swing.JLabel();
        regionDownloadedBar = new neembuu.swing.RangeArrayComponent(file.getRegionHandlers(),downloadedRegionColorProvider,true,downloadedRegionToolTipProvider);
        regionRequestedBar = new neembuu.swing.RangeArrayComponent(file.getRequestedRegion(),requestedRegionColorProvider,true);
        printStateButton = new javax.swing.JButton();
        averageDownloadSpeedLabel = new javax.swing.JLabel();
        downloadSpeedVal = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        speedGraphPanel = new javax.swing.JPanel();
        previousButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        requestRateLabel = new javax.swing.JLabel();
        requestRateVal = new javax.swing.JLabel();
        autoCompleteEnabledButton = new javax.swing.JToggleButton();
        killConnection = new javax.swing.JButton();
        connectionStatusLabel = new javax.swing.JLabel();
        throttleStateLable = new javax.swing.JLabel();
        connectionDescText = new javax.swing.JTextField();
        openFile = new javax.swing.JButton();

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setMaximumSize(new java.awt.Dimension(628, 10000));
        setVerifyInputWhenFocusTarget(false);

        urlLabel.setText("Connection Description :");

        filesizeLabel.setText("FileSize :");

        filenameLabel.setText("FileName :");

        regionDownloadedBar.setMaximumSize(new java.awt.Dimension(32767, 10));
        regionDownloadedBar.setMinimumSize(new java.awt.Dimension(10, 10));
        regionDownloadedBar.setPreferredSize(new java.awt.Dimension(146, 10));

        printStateButton.setText("Print State");
        printStateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printStateButtonActionPerformed(evt);
            }
        });

        averageDownloadSpeedLabel.setText("Total Download Speed : ");

        downloadSpeedVal.setText("KBps");

        jLabel3.setText("Region Downloaded");

        jLabel5.setText("Region Requested");

        speedGraphPanel.setMaximumSize(new java.awt.Dimension(412, 350));
        speedGraphPanel.setName(""); // NOI18N
        speedGraphPanel.setPreferredSize(new java.awt.Dimension(412, 200));

        javax.swing.GroupLayout speedGraphPanelLayout = new javax.swing.GroupLayout(speedGraphPanel);
        speedGraphPanel.setLayout(speedGraphPanelLayout);
        speedGraphPanelLayout.setHorizontalGroup(
            speedGraphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        speedGraphPanelLayout.setVerticalGroup(
            speedGraphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 224, Short.MAX_VALUE)
        );

        previousButton.setText("Previous Region");
        previousButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousButtonActionPerformed(evt);
            }
        });

        nextButton.setText("Next Region");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        requestRateLabel.setText("Total Request Rate : ");

        requestRateVal.setText("KBps");

        autoCompleteEnabledButton.setText("Auto Complete Enabled");
        autoCompleteEnabledButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoCompleteEnabledButtonActionPerformed(evt);
            }
        });

        killConnection.setText("Kill Connection");
        killConnection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                killConnectionActionPerformed(evt);
            }
        });

        connectionStatusLabel.setText("No connection selected");

        throttleStateLable.setText("Throttle state");

        connectionDescText.setEditable(false);

        openFile.setText("Open File");
        openFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(urlLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(connectionDescText, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(filesizeLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(filenameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE))
                        .addGap(28, 28, 28)
                        .addComponent(openFile, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(connectionStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(regionDownloadedBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(regionRequestedBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(109, 109, 109)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(speedGraphPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(throttleStateLable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(killConnection, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(nextButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(requestRateLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(averageDownloadSpeedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(requestRateVal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(downloadSpeedVal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(previousButton)
                                        .addGap(6, 6, 6)
                                        .addComponent(printStateButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(autoCompleteEnabledButton)
                                        .addGap(11, 11, 11)))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(urlLabel)
                    .addComponent(connectionDescText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(filesizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(filenameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openFile)))
                .addGap(1, 1, 1)
                .addComponent(regionDownloadedBar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(1, 1, 1)
                .addComponent(regionRequestedBar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(connectionStatusLabel)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(throttleStateLable))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(killConnection)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(speedGraphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(averageDownloadSpeedLabel)
                    .addComponent(downloadSpeedVal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(requestRateLabel)
                    .addComponent(requestRateVal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nextButton)
                    .addComponent(previousButton)
                    .addComponent(printStateButton)
                    .addComponent(autoCompleteEnabledButton)))
        );

        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void printStateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printStateButtonActionPerformed
        System.out.println("+++++++"+file.getName()+file.getFileDescriptor().getFileId()+"++++++");
        System.out.println("Requested Region");
        System.out.println(file.getRequestedRegion());
        System.out.println("-------------------------------");
        System.out.println("Downloaded Region");
        System.out.println(file.getRegionHandlers());
        System.out.println("-------"+file.getName()+"------");
        System.out.println();
        System.out.println("Pending regions in each connection");
        
        try{
            UnsyncRangeArrayCopy<RegionHandler> downloadedRegions = file.getRegionHandlers().tryToGetUnsynchronizedCopy();
            Range<RegionHandler> downloadedRegion;
            for(int j=0; j<downloadedRegions.size();j++){
            downloadedRegion = downloadedRegions.get(j);
            System.out.println("In Channel="+downloadedRegion);
            
            String[]pendingRqs = downloadedRegion.getProperty().getPendingOperationsAsString();
                System.out.println("++++++++++++++++++");
                for (int i = 0; i < pendingRqs.length; i++) {
                    System.out.println(pendingRqs[i]);
                }
                System.out.println("------------------");
                //((neembuu.vfs.readmanager.impl.BasicRegionHandler)downloadedRegion.getProperty()).printPendingOps(System.out);
            }
        }catch(Exception l){
            System.out.println("could not print");
        }
        System.out.println("-------------------------------");
    }//GEN-LAST:event_printStateButtonActionPerformed
    
    final Object regionTraversalLock = new Object();

    @SuppressWarnings(value="unchecked")
    private void previousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousButtonActionPerformed
        // TODO add your handling code here:
        synchronized(regionTraversalLock){
            if(lastRegionSelected ==null){
                if(file.getRequestedRegion().isEmpty())return;
                ((RangeArrayComponent)regionDownloadedBar).selectRange(file.getRegionHandlers().getFirst());
            }
            try{
                Range previouselement =
                        file.getRegionHandlers().getPrevious(lastRegionSelected);
                ((RangeArrayComponent)regionDownloadedBar).selectRange(previouselement);
                lastRegionSelected = previouselement;
            }catch(ArrayIndexOutOfBoundsException exception){
                // ignore
            }catch(Exception anyother){
                LOGGER.log(Level.SEVERE,"problem in region traversing", anyother);
            }
        }

    }//GEN-LAST:event_previousButtonActionPerformed

    @SuppressWarnings(value="unchecked")
    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        synchronized(regionTraversalLock){
            if(lastRegionSelected ==null){
                if(file.getRequestedRegion().isEmpty())return;
                ((RangeArrayComponent)regionDownloadedBar).selectRange(file.getRegionHandlers().getFirst());
            }
            try{
                System.out.println("previous="+lastRegionSelected);
                //System.out.println("(previousindex)="+file.getDownloadedRegion().get(file.getRequestedRegion().getIndexOf(lastRegionSelected)));
                    Range nextElement =
                        file.getRegionHandlers().getNext(lastRegionSelected);
                System.out.println("next="+nextElement);
                System.out.println("last="+lastRegionSelected);
                lastRegionSelected = nextElement;
                ((RangeArrayComponent)regionDownloadedBar).selectRange(nextElement);
            }catch(ArrayIndexOutOfBoundsException exception){
                // ignore
            }catch(Exception anyother){
                LOGGER.log(Level.SEVERE,"problem in region traversing", anyother);
            }
        }
    }//GEN-LAST:event_nextButtonActionPerformed

    private void killConnectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_killConnectionActionPerformed
        System.err.println("kill button pressed for region="+lastRegionSelected);
        try{
            ((neembuu.vfs.readmanager.impl.BasicRegionHandler)lastRegionSelected.getProperty()).
                    getConnection().abort();
        }catch(Exception any){
            LOGGER.log(Level.SEVERE, "Connection killing exception", any);
        }
    }//GEN-LAST:event_killConnectionActionPerformed

private void autoCompleteEnabledButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoCompleteEnabledButtonActionPerformed
// TODO add your handling code here:
    file.setAutoCompleteEnabled(!file.isAutoCompleteEnabled());
    if(file.isAutoCompleteEnabled()){
        autoCompleteEnabledButton.setText("AutoCompleteEnabled");
    }else autoCompleteEnabledButton.setText("AutoCompleteDisabled");
}//GEN-LAST:event_autoCompleteEnabledButtonActionPerformed

private void openFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileActionPerformed
// TODO add your handling code here:
    try{
        java.awt.Desktop.getDesktop().open(new java.io.File(virtualPathOfFile));
    }catch(Exception a){
        JOptionPane.showMessageDialog(this, "Could not show the file");
        a.printStackTrace(System.err);
    }
}//GEN-LAST:event_openFileActionPerformed

    public void setVirtualPathOfFile(String virtualPathOfFile) {
        this.virtualPathOfFile = virtualPathOfFile;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton autoCompleteEnabledButton;
    private javax.swing.JLabel averageDownloadSpeedLabel;
    private javax.swing.JTextField connectionDescText;
    private javax.swing.JLabel connectionStatusLabel;
    public javax.swing.JLabel downloadSpeedVal;
    private javax.swing.JLabel filenameLabel;
    private javax.swing.JLabel filesizeLabel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JButton killConnection;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton openFile;
    private javax.swing.JButton previousButton;
    private javax.swing.JButton printStateButton;
    public javax.swing.JProgressBar regionDownloadedBar;
    public javax.swing.JProgressBar regionRequestedBar;
    private javax.swing.JLabel requestRateLabel;
    private javax.swing.JLabel requestRateVal;
    private javax.swing.JPanel speedGraphPanel;
    private javax.swing.JLabel throttleStateLable;
    private javax.swing.JLabel urlLabel;
    // End of variables declaration//GEN-END:variables
   
    @Override
    public void rangeSelected(Range arrayElement) {
        LOGGER.log(Level.INFO, "region selected {0}", arrayElement);
        lastRegionSelected = arrayElement;
        
        RegionHandler regionOfInterest;
        
        if(arrayElement==null){
            connectionStatusLabel.setText("No connection selected");
            this.speedGraphPanel.removeAll();
            this.speedGraphPanel.add(activateLable,BorderLayout.CENTER);
            lastRegionHandler = null; graphJFluid = null;
            this.repaint();
            return;
        }
        
        regionOfInterest = (RegionHandler)arrayElement.getProperty();
        lastRegionHandler = regionOfInterest;
        
        LOGGER.info("Channel of interest="+regionOfInterest);
        
        connectionStatusLabel.setText("Connection "+arrayElement.starting()+
                " selected. This connection is "+
                (regionOfInterest.isAlive()?"alive.":"dead."));
        throttleStateLable.setText(regionOfInterest.getThrottleStatistics().getThrottleState().toString()) ;
        
        graphJFluid = new SpeedGraphJFluid();
        this.speedGraphPanel.removeAll();
        this.speedGraphPanel.add(graphJFluid,BorderLayout.CENTER);
        this.repaint();
    }

}