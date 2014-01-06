package neembuu.vfs.test.graphprovider;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import neembuu.util.logging.LoggerUtil;
/*import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;*/

/**
 *
 * @author Shashank Tulsyan
 */
public class SpeedGraphs 
        extends JPanel  {

//    /** Time series for supply speed. */
//    private TimeSeries supplySpeed;
//    /** Time series for free memory. */
//    private TimeSeries requestSpeed;
//
//    JFreeChart chart;
//    public ChartPanel chartPanel;
//
//    private static final Logger LOGGER = LoggerUtil.getLogger();
//
//    /**
//     * Creates a new application.
//     *
//     * @param maxAge the maximum age (in milliseconds).
//     */
//    public SpeedGraphs(int maxAge) {
//        super(new BorderLayout());
//// create two series that automatically discard data more than 30
//// seconds old...
//        this.supplySpeed = new TimeSeries("Supply Speed");//, Millisecond.class);
//        this.supplySpeed.setMaximumItemAge(maxAge);
//        this.requestSpeed = new TimeSeries("Watching Speed");//, Millisecond.class);
//        this.requestSpeed.setMaximumItemAge(maxAge);
//        TimeSeriesCollection dataset = new TimeSeriesCollection();
//        dataset.addSeries(this.supplySpeed);
//        dataset.addSeries(this.requestSpeed);
//        DateAxis domain = new DateAxis("Time");
//        NumberAxis range = new NumberAxis("Speed (KB/sec)");
//        domain.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
//        range.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
//        domain.setLabelFont(new Font("SansSerif", Font.PLAIN, 14));
//        range.setLabelFont(new Font("SansSerif", Font.PLAIN, 14));
//        XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
//        renderer.setSeriesPaint(0, Color.red);
//        renderer.setSeriesPaint(1, Color.green);
//        /*renderer.setStroke(new BasicStroke(3f, BasicStroke.CAP_BUTT,
//                BasicStroke.JOIN_BEVEL));*/
//        renderer.setBaseStroke(new BasicStroke(3f, BasicStroke.CAP_BUTT,
//                BasicStroke.JOIN_BEVEL));
//        renderer.setSeriesStroke(0,new BasicStroke(3f, BasicStroke.CAP_BUTT,
//                BasicStroke.JOIN_BEVEL));
//        renderer.setSeriesStroke(1,new BasicStroke(3f, BasicStroke.CAP_BUTT,
//                BasicStroke.JOIN_BEVEL));
//        XYPlot plot = new XYPlot(dataset, domain, range, renderer);
//        plot.setBackgroundPaint(Color.lightGray);
//        plot.setDomainGridlinePaint(Color.white);
//        plot.setRangeGridlinePaint(Color.white);
//        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
//        domain.setAutoRange(true);
//        domain.setLowerMargin(0.0);
//        domain.setUpperMargin(0.0);
//        domain.setTickLabelsVisible(true);
//        range.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//
//        chart = new JFreeChart("Speed graph",
//                new Font("SansSerif", Font.BOLD, 24), plot, true);
//        chart.setBackgroundPaint(Color.white);
//        chartPanel = new ChartPanel(chart);
//        chartPanel.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createEmptyBorder(4, 4, 4, 4),
//                BorderFactory.createLineBorder(Color.black)));
//        add(chartPanel);
//    }
//
//    static boolean logged = false;
//
//    @Override
//    public void setLayout(LayoutManager mgr) {
//        if(logged)return;
//        LOGGER.log(Level.INFO, "trying to set laymount manage to {0}", mgr);
//        logged = true;
//        //super.setLayout(mgr);
//    }
//
//    public void downloadSpeedChanged(double downloadSpeedInKiBps) {
//        addSupplySpeedObservation(downloadSpeedInKiBps);
//    }
//
//    /**
//     * Adds an observation to the total memory time series.
//     *
//     * @param y the total memory used.
//     */
//    public final void addSupplySpeedObservation(double y) {
//        try{
//            this.supplySpeed.addOrUpdate(new Millisecond(), y);
//        }catch(Exception any){
//            any.printStackTrace();
//        }
//    }
//
//    /**
//     * Adds an observation to the free memory time series.
//     *
//     * @param y the free memory.
//     */
//    
//    
//    public void requestSpeedChanged(double requestSpeedInKiBps) {
//        addRequestSpeedObservation(requestSpeedInKiBps);
//    }
//    
//    public final void addRequestSpeedObservation(double y) {
//        try{
//            this.requestSpeed.addOrUpdate(new Millisecond(), y);
//        }catch(Exception any){
//            //LOGGER.log(Level.INFO,"",any);
//        }
//    }
//
//    /**
//     * The data generator.
//     */
//    class DataGenerator extends Timer implements ActionListener {
//
//        /**
//         * Constructor.
//         *
//         * @param interval the interval (in milliseconds)
//         */
//        DataGenerator(int interval) {
//            super(interval, null);
//            addActionListener(this);
//        }
//
//        /**
//         * Adds a new free/total memory reading to the dataset.
//         *
//         * @param event the action event.
//        CHAPTER 10. DYNAMIC CHARTS 76
//         */
//        public void actionPerformed(ActionEvent event) {
//            long f = Runtime.getRuntime().freeMemory();
//            long t = Runtime.getRuntime().totalMemory();
//            addSupplySpeedObservation(t);
//            addRequestSpeedObservation(f);
//        }
//    }
//
//    /**
//     * Entry point for the sample application.
//     *
//     * @param args ignored.
//     */
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Memory Usage Demo");
//        SpeedGraphs panel = new SpeedGraphs(30*1000*120);
//        frame.getContentPane().add(panel, BorderLayout.CENTER);
//        frame.setBounds(200, 120, 600, 280);
//        frame.setVisible(true);
//        panel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
//        panel.new DataGenerator(100).start();
//        frame.addWindowListener(new WindowAdapter() {
//
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });
//    }

}
