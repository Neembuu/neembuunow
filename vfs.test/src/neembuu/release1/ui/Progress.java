/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.Timer;
import neembuu.rangearray.DissolvabilityRule;
import neembuu.rangearray.Range;
import neembuu.rangearray.RangeArray;
import neembuu.rangearray.RangeArrayFactory;
import neembuu.rangearray.RangeArrayParams;
import neembuu.rangearray.RangeUtils;
import neembuu.rangearray.UIRangeArrayAccess;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.release1.api.VirtualFile;
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
public class Progress {
    
    private RangeArrayComponent progress;
    private VirtualFile vf;
    private RangeArray<Boolean> overallProgress;
    
    private LinkPanel lp;
    private long total_Downloaded = 0;
    
    private UnsyncRangeArrayCopy regionHandlersUnsync;
    private UnsyncRangeArrayCopy overallProgressUnsync;
    private UIRangeArrayAccess downloadedRegionHandlers ;        
    private volatile long latestRequestStarting,latestRequestEnding;
    
    private Range selectedRange = null;
    
    void init(LinkPanel lp,final VirtualFile vf){
        this.vf = vf;
        this.lp = lp;
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
                            return Progress.this.getToolTipText(element, absolutePosition, largestEntry, selectionState);
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
                Progress.this.rangeSelected(arrayElement);
            }
        });
        javax.swing.GroupLayout layout = (javax.swing.GroupLayout)lp.progressBarPanel.getLayout();

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
        
        Timer t = new Timer(300, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(vf.getConnectionFile().getParent()==null){
                    // file has been closed
                    ((Timer)e.getSource()).stop();
                }
                handleChange();
            }
        });
        
        t.start();
    }
    
    
    public long totalDownloaded(){
        return total_Downloaded;
    }
    
    public void repaint(){
        progress.repaint();
    }
    
    void handleChange(){
        downloadedRegionHandlers = vf.getConnectionFile().getRegionHandlers();

        lp.totalDownloadSpeed.setText(Math.round(vf.getConnectionFile().getTotalFileReadStatistics()
                    .getTotalAverageDownloadSpeedProvider().getDownloadSpeed_KiBps())
            +" KBps");
        lp.totalRequestSpeed.setText(Math.round(
                vf.getConnectionFile().getTotalFileReadStatistics().getTotalAverageRequestSpeedProvider().getRequestSpeed_KiBps())
                +" KBps");
        
        
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
            lp.rightControlsPanel.saveBtn.setVisible(true);
        }else if(total_Downloaded > vf.getConnectionFile().getFileSize()){
            throw new RuntimeException("Total download size of file greater than filesize");
        } 
        
        if(lp.sizeAndProgressPane.isVisible()){
            double perPro = total_Downloaded*100.0/vf.getConnectionFile().getFileSize();
            lp.progressPercetLabel.setText(Math.round(perPro) + " %");
            
        }
    }
    
    private void rangeSelected(Range arrayElement){
        this.selectedRange = arrayElement;
        lp.graph.initGraph(arrayElement);
        lp.killConnectionButton.setEnabled(arrayElement!=null);
    }
    
    public void switchToRegion(Range arrayElement){
        lp.killConnectionButton.setEnabled(arrayElement!=null);
        if(arrayElement==null){
            return;
        }
        progress.selectRange(arrayElement);
    }
    

    public Range getSelectedRange() {
        return selectedRange;
    }
    
    private String getToolTipText(Range element, long absolutePosition, long largestEntry, RangeArrayElementColorProvider.SelectionState selectionState) {
        if(element==null){
            return null;
        }
        String defaultString = element.starting()+"->"+element.ending();
        if(lp.getExpansionState()!=LinkPanel.ExpansionState.FullyExpanded){
            return defaultString;
        }
        
        if(downloadedRegionHandlers==null){    
            return defaultString+ " not initialized ";
        }
        element = downloadedRegionHandlers.getUnsynchronized(absolutePosition);
        if(element==null || !(element.getProperty() instanceof RegionHandler) ){
            return defaultString+ " not RegionHandler ";
        }
        
        RegionHandler regionHandler = (RegionHandler)element.getProperty();
        String toRet = "";
        if(vf.getConnectionFile().getDownloadConstrainHandler().isComplete()){
            toRet = "File completely downloaded \n";
        }
        return toRet + NumberFormat.getInstance().format( regionHandler.getThrottleStatistics().getDownloadSpeed_KiBps())+" KBps \n"+
                " RequestSpeed = " + NumberFormat.getInstance().format( regionHandler.getThrottleStatistics().getRequestSpeed_KiBps())+" KBps \n"+
                (regionHandler.isAlive()?"alive":"dead")+" "+(regionHandler.isMainDirectionOfDownload()?"main":"notmain")  ;
    }

}
