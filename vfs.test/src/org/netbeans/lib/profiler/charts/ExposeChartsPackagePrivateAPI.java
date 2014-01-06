/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
