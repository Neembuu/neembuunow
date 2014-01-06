package neembuu.vfs.progresscontrol;

import info.monitorenter.gui.chart.TracePoint2D;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Shashank Tulsyan
 */
public class GeneralThrottleTestMeasurement extends OutputStream{
    
    private final ControlThrottle controlThrottle;
    
    volatile double sleepPerByteDownloaded = 0;
    volatile double requestSpeed_Bps = 30*1024;
    volatile boolean throttleEnabled = true;
    
    SpeedObv downloadSpeedObv = new SpeedObv();
    long lstTime = System.currentTimeMillis();
    
    float a = 0;
    boolean dir = true; // under throttle
    int dc = downloadSpeedObv.getL();
    int buffSize = 0;
    
    public GeneralThrottleTestMeasurement() throws Exception{
        controlThrottle = new ControlThrottle(this);
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                controlThrottle.setVisible(true);
            }
        });
        
         DefaultHttpClient myClient = new DefaultHttpClient();
         myClient.execute(new HttpGet(
                 //"http://update0.jdownloader.org/test120k.rmvb"
                 
                 "http://localhost:8080/LocalFileServer-war/servlet/FileServer?"
                    + "totalFileSpeedLimit=8000&"
                 //  + "mode=constantAverageSpeed"
                 + "mode=constantFlow"
                    + "&newConnectionTimemillisec=1&file=wal.avi"

                 )
             ).getEntity().writeTo(this);
        
    }
    
    @Override
    public void write(int b) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        write(b);
    }
    
    private boolean firstObv = false;
    
    /*double ift = 0;
    int ind = 0;*/
    
    @Override
    public void write(byte[] b) throws IOException {
        if(!firstObv){
            new Throwable().printStackTrace();
            firstObv = true;
        }
        if(buffSize!=b.length){
            buffSize = b.length;
            //System.out.println("buffchanged to "+buffSize);
        }
        
        downloadSpeedObv.progressed(b.length);
        
        //long i = System.currentTimeMillis();
        controlThrottle.trace.addPoint(
            new TracePoint2D(
                    System.currentTimeMillis(),
                    downloadSpeedObv.getSpeed_KiBps() ));
        
        controlThrottle.smallDownloadSpeedLabel.setText(downloadSpeedObv.getSpeed_KiBps()+ "KiBps");

        controlThrottle.averageSpeed.setText(downloadSpeedObv.getAverage_KiBps()+" KBps");
        
        /*long f = System.currentTimeMillis();
        ift*=ind;
        ift+=(f-i);
        ind++;
        ift/=ind;
        System.out.println("tog="+ift);*/
        
        calculateSleepInterval();
        try{
            if(throttleEnabled){
                if(sleepPerByteDownloaded>0){
                    double millis = sleepPerByteDownloaded*b.length*1000;
                    int nanos = ((int)(millis*1000000))%1000000;
                    //if(millis>2){
                        Thread.sleep(
                                (int)(millis),nanos);
                    //}
                }
            }
        }catch(Exception a){

        }
    }
    
    double calculateSleepInterval(){
        /*System.out.println("obt="+(System.currentTimeMillis()-lstTime)
                +" desired="+(sleepPerByteDownloaded*buffSize*1000)
            );
        System.out.println("sl="+sleepPerByteDownloaded+" bf="+buffSize+" r="+requestSpeed_Bps+" a="+a);
         
         */
        lstTime = System.currentTimeMillis();
        
        double r = requestSpeed_Bps;
        double d = downloadSpeedObv.getSpeed_Bps();
        
        if(!throttleEnabled){return 0;}
        if(d<r){
            if(a<0.99f){
                if(dir==false){
                    if(dc>0)dc--;
                    else { 
                        dir = true; 
                        dc = /*dc_reset*/downloadSpeedObv.getL();
                        controlThrottle.directionLable.setText("under throttle");
                    }
                }else dc = /*dc_reset*/downloadSpeedObv.getL();
                
                if(dir==true){
                    a+=0.00004f*r*r/(d*d);
                    if(a>0.99f)a=0.99f;
                    if(controlThrottle!=null)
                        controlThrottle.a_value.setText(""+(a*100));
                }
            }
        }else if(d>r){
            if(a>0.0f){
                if(dir==true){
                    if(dc>0)dc--;
                    else { 
                        dir = false; 
                        dc = /*dc_reset*/downloadSpeedObv.getL();
                        controlThrottle.directionLable.setText("over throttle");
                    }
                }else dc = /*dc_reset*/downloadSpeedObv.getL();
                
                
                if(dir==false){
                    a-=0.00004f*d*d/(r*r);
                    if(a<0.0f)a=0.0f;
                    controlThrottle.a_value.setText(""+(a*100));
                }
            }else a=0f;
        }
        
        sleepPerByteDownloaded = (1/r)*(1-a);
        //if(sleepPerByteDownloaded<0.1){return sleepPerByteDownloaded=0;}
        return sleepPerByteDownloaded;
    }
    
    
    public static void main(String[] args) throws Exception{
        new GeneralThrottleTestMeasurement();
    }
    
}
