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
import neembuu.rangearray.UIRangeArrayAccess;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.release1.Main;
import neembuu.release1.api.ui.ExpansionState;
import neembuu.release1.api.ui.Graph;
import neembuu.release1.api.ui.access.GraphUIA;
import neembuu.vfs.readmanager.RegionHandler;
import neembuu.vfs.test.MonitoredSeekableHttpFilePanel;
import org.netbeans.lib.profiler.charts.ExposeChartsPackagePrivateAPI;

/**
 *
 * @author Shashank Tulsyan
 */
public final class GraphImpl implements Graph{
    
    //private final LinkPanel linksPanel;
    
    private final GraphUIA ui;

    public GraphImpl(GraphUIA ui) {
        this.ui = ui;
        ui.graphPanel().setBackground(Color.WHITE);
    }
    
    
    //javax.swing.GroupLayout.ParallelGroup parallelGroupHorizontal,parallelGroupVertical;
    
    private final JLabel activateLable = new JLabel(
            "<html>Click on a <b>downloaded region</b> to activate speed chart</html>",
            new ImageIcon(MonitoredSeekableHttpFilePanel.class.getResource("activate.png")),
            SwingConstants.CENTER);
    
    private SimpleXYChartSupport support;
    RegionHandler currentlySelectedRegion = null;
    
    private void initValues(final Range arrayElement){  
        if(regionHandlers == null){ 
            Main.getLOGGER().log(Level.SEVERE, "Not initialized");
            return;
        }
        UnsyncRangeArrayCopy ra = regionHandlers.tryToGetUnsynchronizedCopy();
        for (int i = 0; i < ra.size(); i++) {
            if(RangeUtils.contains(ra.get(i), arrayElement.ending(), arrayElement.ending())){
                currentlySelectedRegion = (RegionHandler)ra.get(i).getProperty();
                break;
            }
        }

        final Timer updateGraphTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ui.getExpansionState()!=ExpansionState.FullyExpanded ||
                        /*file.getParent()==null || */
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

    @Override
    public void init(UIRangeArrayAccess regionHandlers) {
        this.regionHandlers = regionHandlers;
    }
    
    private volatile UIRangeArrayAccess regionHandlers = null;
    
    @Override
    public void initGraph(Range arrayElement,boolean findFirst){
        ui.graphPanel().removeAll();
        JComponent toAdd = null;
        if(arrayElement==null){
            if(findFirst){
                try{
                    UnsyncRangeArrayCopy copy = regionHandlers.tryToGetUnsynchronizedCopy();
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
        
        
        javax.swing.GroupLayout layout = (javax.swing.GroupLayout)ui.graphPanel().getLayout();

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
