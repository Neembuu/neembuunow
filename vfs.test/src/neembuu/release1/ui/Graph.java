/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui;

import com.sun.tools.visualvm.charts.ChartFactory;
import com.sun.tools.visualvm.charts.SimpleXYChartDescriptor;
import com.sun.tools.visualvm.charts.SimpleXYChartSupport;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import neembuu.rangearray.Range;
import neembuu.rangearray.RangeUtils;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.release1.Main;
import neembuu.vfs.file.SeekableConnectionFile;
import neembuu.vfs.readmanager.RegionHandler;
import neembuu.vfs.test.MonitoredSeekableHttpFilePanel;
import org.netbeans.lib.profiler.charts.ExposeChartsPackagePrivateAPI;

/**
 *
 * @author Shashank Tulsyan
 */
final class Graph {
    
    private final LinkPanel linksPanel;

    Graph(LinkPanel graphPanel) {
        this.linksPanel = graphPanel;
        linksPanel.graphPanel.setBackground(Color.WHITE);
    }
    
    
    //javax.swing.GroupLayout.ParallelGroup parallelGroupHorizontal,parallelGroupVertical;
    
    private final JLabel activateLable = new JLabel(
            "<html>Click on a <b>downloaded region</b> to activate speed chart</html>",
            new ImageIcon(MonitoredSeekableHttpFilePanel.class.getResource("activate.png")),
            SwingConstants.CENTER);
    
    private SimpleXYChartSupport support;
    RegionHandler currentlySelectedRegion = null;
    SeekableConnectionFile file = null;
    
    private void initValues(final Range arrayElement){  
        try {
            file = linksPanel.singleFileLinkUI.getVirtualFile().getConnectionFile();
        } catch (NullPointerException noe) {
            Main.getLOGGER().log(Level.SEVERE, "", noe);
            return;
        }
        UnsyncRangeArrayCopy ra = file.getRegionHandlers().tryToGetUnsynchronizedCopy();
        for (int i = 0; i < ra.size(); i++) {
            if(RangeUtils.contains(ra.get(i), arrayElement.ending(), arrayElement.ending())){
                currentlySelectedRegion = (RegionHandler)ra.get(i).getProperty();
                break;
            }
        }

        final Timer updateGraphTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(linksPanel.getExpansionState()!=LinkPanel.ExpansionState.FullyExpanded ||
                        file.getParent()==null || 
                        currentlySelectedRegion==null){
                    ((Timer)e.getSource()).stop();
                    return;
                }
                
                support.addValues(System.currentTimeMillis(), new long[]{
                    (long)currentlySelectedRegion.getThrottleStatistics().getDownloadSpeed_KiBps(),
                    (long)currentlySelectedRegion.getThrottleStatistics().getRequestSpeed_KiBps()
                });
            }
        });
        updateGraphTimer.start();
    }
    
    void initGraph(Range arrayElement){
        initGraph(arrayElement,false);
    }
    
    void initGraph(Range arrayElement,boolean findFirst){
        linksPanel.graphPanel.removeAll();
        JComponent toAdd = null;
        if(arrayElement==null){
            if(findFirst){
                try{
                    UnsyncRangeArrayCopy copy = linksPanel.singleFileLinkUI.getVirtualFile().getConnectionFile().getRegionHandlers().tryToGetUnsynchronizedCopy();
                    for (int i = 0; i < copy.size(); i++) {
                        RegionHandler rh = (RegionHandler)copy.get(i).getProperty();
                        if(rh.isAlive()){
                            arrayElement = copy.get(i);
                            initGraph(arrayElement,false);
                            return;
                        }
                    }
                }catch(Exception a){

                }
            }
            toAdd = new SelectAConnection();            
        }else {
            initValues(arrayElement);
            SimpleXYChartDescriptor descriptor =
                    SimpleXYChartDescriptor.decimal(0, 20, 20, 1d, true, 100);
            descriptor.addLineFillItems("Download Speed");
            descriptor.addLineFillItems("Request Speed");
            descriptor.setYAxisDescription("<html>Speed (KiB/s)</html>");
            support = ChartFactory.createSimpleXYChart(descriptor);
            ExposeChartsPackagePrivateAPI.changeXYBackgroundToWhite(support);
            toAdd = support.getChart();
            
            
        }
        
        
        javax.swing.GroupLayout layout = (javax.swing.GroupLayout)linksPanel.graphPanel.getLayout();

        int right, left;
        int top, bottom; right = left = top = bottom = 0;
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(left, left, left)
                .addComponent(toAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addGap(right, right, right))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(top, top, top)
                .addComponent(toAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                .addGap(bottom, bottom, bottom))
        );
        
        
    }
}
