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

package org.netbeans.lib.profiler.charts;

import com.sun.tools.visualvm.charts.SimpleXYChartSupport;
import com.sun.tools.visualvm.charts.xy.XYBackground;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import neembuu.vfs.test.graphprovider.SpeedGraphJFluid;
import org.netbeans.lib.profiler.charts.xy.synchronous.SynchronousXYChart;

/**
 *
 * @author Shashank Tulsyan
 */
public class ExposeChartsPackagePrivateAPI {
    
    public static SynchronousXYChart findSynchronousXYChart(Container k, int depth){
        for(Component c : k.getComponents()){
            if(c instanceof SynchronousXYChart)return (SynchronousXYChart)c;
            
            if(c instanceof Container){
                SynchronousXYChart chart = findSynchronousXYChart((Container)c,depth+1);
                if(chart!=null)return chart;
            }
        }return null;
    }
    
    public static void changeXYBackgroundToWhite(SimpleXYChartSupport support){
        org.netbeans.lib.profiler.charts.xy.synchronous.SynchronousXYChart
                sxyc = findSynchronousXYChart(support.getChart(), 0);
        
        if(sxyc==null){
            new NullPointerException().printStackTrace(System.err);
            // could not find, so quitting
            return;
        }
        
        XYBackground oldBackGround = null;
        ChartDecorator whiteBackground = new ChartDecorator() {
            @Override public void paint(Graphics2D g, Rectangle dirtyArea, ChartContext context) {
                g.setPaint(Color.WHITE);
                g.fill(dirtyArea);
            }
        };
        ArrayList<ChartDecorator> chartDecorators = (ArrayList<ChartDecorator>)ExposeChartsPackagePrivateAPI.getPreDecorators(sxyc);
        for (int i = 0; i < chartDecorators.size(); i++) {
            if(chartDecorators.get(i) instanceof XYBackground){
                oldBackGround = (XYBackground)chartDecorators.get(i);
                chartDecorators.set(i,whiteBackground);
            }
        }
        
    }
    
    public static List<ChartDecorator>
        getPreDecorators(ChartComponent cc){
        return cc.getPreDecorators();
    }
        
    public static List<ChartDecorator>
        getPostDecorators(ChartComponent cc){
        return cc.getPostDecorators();
    }
}
