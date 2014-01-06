/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import jpfm.annotations.NonBlocking;
import neembuu.rangearray.DissolvabilityRule;
import neembuu.rangearray.ModificationType;
import neembuu.rangearray.Range;
import neembuu.rangearray.RangeArray;
import neembuu.rangearray.RangeArrayFactory;
import neembuu.rangearray.RangeArrayListener;
import neembuu.rangearray.RangeArrayParams;
import neembuu.rangearray.RangeArrayUtils;
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
                        if(p!=null){
                            if(p==Boolean.TRUE){
                                base = Colors.PROGRESS_BAR_FILL_BUFFER;
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
    
    long total_Downloaded = 0;
    public long totalDownloaded(){
        return total_Downloaded;
    }
    
    UnsyncRangeArrayCopy regionHandlersUnsync;
    UnsyncRangeArrayCopy overallProgressUnsync;
    UIRangeArrayAccess downloadedRegionHandlers ;        
    volatile long latestRequestStarting,latestRequestEnding;
    
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
            overallProgress.addElement(r.starting(), r.ending(),false);
        }

        
        overallProgressUnsync = overallProgress.tryToGetUnsynchronizedCopy();
        for (int i = 0; i < overallProgressUnsync.size(); i++) {
            Range<RegionHandler> r = overallProgressUnsync.get(i);
            if(RangeUtils.contains(r, latestRequestEnding, latestRequestEnding)){
                if(latestRequestEnding < r.ending()){
                    overallProgress.addElement(latestRequestEnding+1,r.ending(),true);
                    break;
                }
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
            lp.saveBtn.setVisible(true);
        }else if(total_Downloaded > vf.getConnectionFile().getFileSize()){
            throw new RuntimeException("Total download size of file greater than filesize");
        } 
        
        if(lp.sizeAndProgressPane.isVisible()){
            double perPro = total_Downloaded*100.0/vf.getConnectionFile().getFileSize();
            lp.progressPercetLabel.setText(Math.round(perPro) + " %");
            
        }
    }
    
    void rangeSelected(Range arrayElement){
        lp.graph.initGraph(arrayElement);
    }
    
    String getToolTipText(Range element, long absolutePosition, long largestEntry, RangeArrayElementColorProvider.SelectionState selectionState) {
        return null;
    }

}
