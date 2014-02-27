/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.ref.WeakReference;
import java.util.logging.Level;
import javax.swing.Timer;
import neembuu.rangearray.DissolvabilityRule;
import neembuu.rangearray.Range;
import neembuu.rangearray.RangeArray;
import neembuu.rangearray.RangeArrayFactory;
import neembuu.rangearray.RangeArrayParams;
import neembuu.rangearray.RangeUtils;
import neembuu.rangearray.UIRangeArrayAccess;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.release1.Main;
import neembuu.release1.api.VirtualFile;
import neembuu.release1.api.ui.ExpansionState;
import neembuu.release1.api.ui.Graph;
import neembuu.release1.api.ui.Progress;
import neembuu.release1.api.ui.access.ProgressUIA;
import neembuu.swing.RangeArrayComponent;
import neembuu.swing.RangeArrayComponentBuilder;
import neembuu.swing.RangeArrayElementColorProvider;
import neembuu.swing.RangeArrayElementToolTipTextProvider;
import neembuu.swing.RangeSelectedListener;
import neembuu.vfs.file.RequestPatternListener;
import neembuu.vfs.readmanager.RegionHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public class ProgressImpl implements Progress {
    private VirtualFile vf;
    private final ProgressUIA ui;
    private final Graph graph;
    
    private RangeArrayComponent progress;
    private RangeArray<Boolean> overallProgress;
    private UnsyncRangeArrayCopy regionHandlersUnsync;
    private UnsyncRangeArrayCopy overallProgressUnsync;
    private UIRangeArrayAccess downloadedRegionHandlers ;        
    private volatile long latestRequestStarting,latestRequestEnding;
    
    private Range selectedRange = null;
    private long total_Downloaded = 0;

    public ProgressImpl(ProgressUIA ui, Graph graph) {
        this.ui = ui;
        this.graph = graph;
    }
    
    void init(final VirtualFile vf){
        this.vf = vf;
        overallProgress = RangeArrayFactory.newDefaultRangeArray(new RangeArrayParams.Builder()
                                //.setDoesCarryProperty()
                                .addDissolvabilityRule(DissolvabilityRule.COMPARE_PROPERTY_OBJECT)
                                .setFileSize(vf.getConnectionFile().getFileSize())
                                .build());
        progress = RangeArrayComponentBuilder.create()
                .setArray(overallProgress)
                .setToolTipTextProvider(new RangeArrayElementToolTipTextProvider() {
                        @Override
                        public String getToolTipText(Range element, long absolutePosition, long largestEntry, RangeArrayElementColorProvider.SelectionState selectionState) {
                            return ProgressImpl.this.getToolTipText(element, absolutePosition, largestEntry, selectionState);
                        }
                    })
                .setArrayElementColorProvider(new RangeArrayElementColorProvider() {
                    @Override
                    public Color getColor(Color defaultColor, Range element, RangeArrayElementColorProvider.SelectionState selectionState) {
                        Object p = element.getProperty();
                        Color base = Colors.PROGRESS_BAR_FILL_ACTIVE;
                        if(!vf.getConnectionFile().isAutoCompleteEnabled()){
                            base = Colors.PROGRESS_DOWNLOAD_LESS_MODE;
                        }
                        
                        if(p!=null){
                            if(p==Boolean.TRUE){
                                base = Colors.PROGRESS_BAR_FILL_BUFFER;
                                if(!vf.getConnectionFile().isAutoCompleteEnabled()){
                                    base = Colors.TINTED_IMAGE;
                                }
                                switch (selectionState) {
                                    case LIST: base = RangeArrayComponent.lightenColor(base, 0.9f); break;
                                    case MOUSE_OVER: base = RangeArrayComponent.lightenColor(base, 0.8f); break;
                                    case SELECTED: base = RangeArrayComponent.lightenColor(base, 0.7f); break;
                                    case NONE:
                                        break;
                                }
                            }
                        }
                        return base;
                    }
                })
                .setUnprogressedBaseColor(Colors.PROGRESS_BAR_BACKGROUND)
                .build();
        progress.setBackground(Color.WHITE);
        progress.addRangeSelectedListener(new RangeSelectedListener() {
            @Override
            public void rangeSelected(Range arrayElement) {
                ProgressImpl.this.rangeSelected(arrayElement);
            }
        });
        javax.swing.GroupLayout layout = (javax.swing.GroupLayout)ui.progressBarPanel().getLayout();

        int right, left;
        int top, bottom; right = left = top = bottom = 0;
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(left, left, left)
                .addComponent(progress, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                .addGap(right, right, right))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(top, top, top)
                .addComponent(progress, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                .addGap(bottom, bottom, bottom))
        );
        
        vf.getConnectionFile().addRequestPatternListener(new RequestPatternListener() {

            @Override
            public void requested(long requestStarting, long requestEnding) {
                latestRequestStarting = requestStarting;
                latestRequestEnding = requestEnding;
            }
        });
        
        final WeakReference<ProgressUIA> lpWeakReference = new WeakReference<ProgressUIA>(ui);
        
        Timer t = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProgressUIA lp = lpWeakReference.get();
                if(lp==null){
                    Main.getLOGGER().info("LinkPanel for "+this+ " was garbage collected");
                    ((Timer)e.getSource()).stop();
                }
                if(ui.getExpansionState()==ExpansionState.Contracted){
                    // no point in painting
                    return;
                }
                try{
                    handleChange();
                }catch(Exception a){
                    Main.getLOGGER().log(Level.FINE, "Error in refreshing graphs",a);
                }
            }
        });
        
        t.start();
    }
    
    
    @Override
    public void repaint(){
        progress.repaint();
    }
    
    private void handleChange(){        
        downloadedRegionHandlers = vf.getConnectionFile().getRegionHandlers();

        long totalDownloaded = 0;

        regionHandlersUnsync = downloadedRegionHandlers.tryToGetUnsynchronizedCopy();
        progress.setUpdateQuickly(false);
        for (int i = 0; i < regionHandlersUnsync.size(); i++) {
            Range<RegionHandler> r = regionHandlersUnsync.get(i);
            totalDownloaded += RangeUtils.getSize(r);
            overallProgress.addElement(r.starting(), r.getProperty().authorityLimit(),false);
        }

        
        overallProgressUnsync = overallProgress.tryToGetUnsynchronizedCopy();
        for (int i = 0; i < overallProgressUnsync.size(); i++) {
            Range r = overallProgressUnsync.get(i);
            if(r.starting() < latestRequestEnding &&
                    latestRequestEnding < r.ending()){
                overallProgress.addElement(latestRequestEnding+1,r.ending(),true);
                break;
            }
        }
        
        updateTotalDownloaded(totalDownloaded);
        
        //progress.setUpdateQuickly(true);
        progress.repaint();
    }
    
    private void updateTotalDownloaded(long newTotal){
        total_Downloaded = newTotal;
        long left = vf.getConnectionFile().getFileSize() - total_Downloaded;
        if(left == 0){
            ui.saveButton().setVisible(true);
        }else if(total_Downloaded > vf.getConnectionFile().getFileSize()){
            throw new RuntimeException("Total download size of file greater than filesize");
        } 
        
        if(ui.getExpansionState() == ExpansionState.SemiExpanded ||  ui.getExpansionState() == ExpansionState.FullyExpanded){
            double perPro = total_Downloaded*100.0/vf.getConnectionFile().getFileSize();
            ui.progressPercetLabel().setText(Math.round(perPro) + " %");
            
        }
    }
    
    private void rangeSelected(Range arrayElement){
        this.selectedRange = arrayElement;
        graph.initGraph(arrayElement,false);
        if(arrayElement!=null){
            ui.killConnectionButton().setEnabled(true);
        }else {
            ui.killConnectionButton().setEnabled(false);
        }
    }
    
    @Override
    public void switchToRegion(Range arrayElement){
        if(arrayElement==null){
            return;
        }
        progress.selectRange(arrayElement);// this sends call to rangeSelected(Range arrayElemt)
    }
    

    @Override
    public Range getSelectedRange() {
        return selectedRange;
    }
    
    @Override
    public String getSelectedRangeTooltip(){
        String toRet = null;
        try{
            toRet= getToolTipText(selectedRange, selectedRange.starting(), vf.getConnectionFile().getFileSize(), null);
        }catch(Exception a){
            
        }
        return toRet;
    }
    
    private String getToolTipText(Range element, long absolutePosition, long largestEntry, RangeArrayElementColorProvider.SelectionState selectionState) {
        String totalSpeeds = "\n("+totalDownloadSpeed()+","+totalRequestSpeed()+")KBps\n";
        if(element==null){
            return "<html>"+ absolutePosition+totalSpeeds+"</html>";
        }
        String region_start_end = element.starting()+","+
                c(Colors.PROGRESS_BAR_FILL_ACTIVE)+element.ending()+c_();
        if(ui.getExpansionState()!=ExpansionState.FullyExpanded){
            return "<html>"+region_start_end+totalSpeeds+"</html>";
        }
        
        if(downloadedRegionHandlers==null){    
            return "<html>"+region_start_end+ " not initialized </html>";
        }
        element = downloadedRegionHandlers.getUnsynchronized(absolutePosition);
        if(element==null || !(element.getProperty() instanceof RegionHandler) ){
            return "<html>"+region_start_end+ " not RegionHandler </html>";
        }
        
        RegionHandler r = (RegionHandler)element.getProperty();
        String region_start_end_auth = region_start_end;
        region_start_end_auth+=","+c(Colors.PROGRESS_DOWNLOAD_LESS_MODE)+r.authorityLimit()+c_()+"\n";
        //vf.getConnectionFile().getDownloadConstrainHandler().isComplete()
        return "<html>"+region_start_end_auth + "("+downloadSpeed(r)+","+requestSpeed(r)+")KBps\n"+
                isAlive(r)+","+isMain(r)+","+throttlingState()+"</html>";
    }
    
    private String isAlive(RegionHandler r){
        return r.isAlive()?c(Colors.PROGRESS_BAR_FILL_ACTIVE)+"alive"+c_():"dead";
    }
    
    private String isMain(RegionHandler r){
        return (r.isMainDirectionOfDownload()?"main":"notmain");
    }

    private String downloadSpeed(RegionHandler r){
        return c(Colors.PROGRESS_DOWNLOAD_LESS_MODE)+Math.round(r.getThrottleStatistics().getDownloadSpeed_KiBps())+c_();
    }
    
    private String requestSpeed(RegionHandler r){
        return c(Colors.PROGRESS_BAR_FILL_ACTIVE)+Math.round(r.getThrottleStatistics().getRequestSpeed_KiBps())+c_();
    }
    
    private String totalDownloadSpeed(){
        return c(Colors.PROGRESS_DOWNLOAD_LESS_MODE)+Math.round(vf.getConnectionFile().getTotalFileReadStatistics()
                    .getTotalAverageDownloadSpeedProvider().getDownloadSpeed_KiBps())+c_();
    }
    
    private String totalRequestSpeed(){
        return c(Colors.PROGRESS_BAR_FILL_ACTIVE)+Math.round(
                vf.getConnectionFile().getTotalFileReadStatistics().getTotalAverageRequestSpeedProvider().getRequestSpeed_KiBps())
                +c_();
    }
    
    private String throttlingState(){
        String throttlingState = "";
        try {
            throttlingState = ((RegionHandler)selectedRange.getProperty()).getThrottleStatistics().getThrottleState().toString();
        } catch (Exception e) {
        }
        return throttlingState;
    }
    
    private static String c(Color c){
        return "<font color=#"+ Integer.toHexString(c.getRGB()).substring(2)+ ">";
    }
    
    private static String c_(){
        return "</font>";
    }

}
