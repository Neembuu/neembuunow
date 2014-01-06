/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 *
 * @author Shashank Tulsyan
 */
public class UtilFx extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    XYChart.Series seriesSupplySpeed;
    XYChart.Series seriesWatchingSpeed;
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Neembuu");
        final NumberAxis timeAxis = new NumberAxis(1, 31, 1);
        final NumberAxis speedAxis = new NumberAxis();
        final AreaChart<Number,Number> ac = new AreaChart<Number,Number>(timeAxis,speedAxis);
        ac.setAnimated(false);
        ac.setTitle("Demand and Supply Speed");
 
        seriesSupplySpeed= new XYChart.Series();
        seriesSupplySpeed.setName("Supply/Download Speed");
        //seriesSupplySpeed.getData().add(new XYChart.Data(1, 4));
        
        seriesWatchingSpeed = new XYChart.Series();
        seriesWatchingSpeed.setName("Watching Speed");
        //seriesWatchingSpeed.getData().add(new XYChart.Data(1, 20));
        
        generateGraph();
        
        Scene scene  = new Scene(ac,800,600);
        ac.getData().addAll(seriesSupplySpeed, seriesWatchingSpeed);
        stage.setScene(scene);
        stage.show();
    }
    
    void generateGraph(){
        for (int i = 0; i < 100; i++) {
            seriesSupplySpeed.getData().add(new XYChart.Data<>(i, 0));
            seriesWatchingSpeed.getData().add(new XYChart.Data<>(i, 0));
        }
        
        new Thread(){
            @Override
            public void run() {
                int i = seriesSupplySpeed.getData().size() - 1;
                XYChart.Data<Integer,Integer> s_,s_1;
                while(true) {
                    for (int j = 0; j < seriesSupplySpeed.getData().size() - 1; j++) {
                        s_ = (XYChart.Data<Integer,Integer>)seriesSupplySpeed.getData().get(j);
                        s_1 = (XYChart.Data<Integer,Integer>)seriesSupplySpeed.getData().get(j+1);
                        
                        s_.YValueProperty().set(s_1.getYValue());
                        
                        s_ = (XYChart.Data<Integer,Integer>)seriesWatchingSpeed.getData().get(j);
                        s_1 = (XYChart.Data<Integer,Integer>)seriesWatchingSpeed.getData().get(j+1);
                        
                        s_.YValueProperty().set(s_1.getYValue());
                    }
                    
                    s_ = (XYChart.Data<Integer,Integer>)seriesSupplySpeed.getData().get(i);
                    s_1 = (XYChart.Data<Integer,Integer>)seriesWatchingSpeed.getData().get(i);
                    
                    s_.YValueProperty().set((int)(100*Math.random()));
                    s_1.YValueProperty().set((int)(100*Math.random()));
                    
                    try {
                        Thread.sleep(300);
                    } catch (Exception e) {
                    }

                }
            }
        }.start();
    }
    
}
