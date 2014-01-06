/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.util.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shashank Tulsyan
 */
public class HtmlFormatterTest {
        public static void main(String[] args) throws Exception{
        Logger l = LoggerUtil.getLightWeightHtmlLogger(
                "l", "j:\\neembuu\\heap\\test120k.rmvb_neembuu_download_data\\",1024*100);
        
        l.log(Level.SEVERE, "Handlers	{\n"+
            "[0] BasicRegionHandler{0-->193535 ,authl=193535 isAlive=true ,size=193536 ,isMain=false}\n"+
            "[1] BasicRegionHandler{524288-->526335 ,authl=526335 isAlive=true ,size=2048 ,isMain=false}\n}",
                new Throwable());
        l.log(Level.SEVERE, "Handlers	{\n"+
            "[0] BasicRegionHandler{0-->193535 ,authl=193535 isAlive=true ,size=193536 ,isMain=false}\n"+
            "[1] BasicRegionHandler{524288-->526335 ,authl=526335 isAlive=true ,size=2048 ,isMain=false}\n}",
                new Throwable());
        
        //if(true)return;
        
        long start = System.currentTimeMillis();
        int len = 100000;
        for (int i = 0; i < len; i++) {
            //l.log(Level.SEVERE,"hello");//,new Throwable());
            l.log(Level.SEVERE,"hello asdasdas dbasd,msa" ,new Throwable());
            //l.log(Level.SEVERE,"hello2");//,new Throwable());*/
        }
        long end = System.currentTimeMillis();
        
        System.out.println("speed="+ ((end*1d-start)/len));
        //0.142 millisec normal
        //0.2371 with throwable
        
        //0.013 millisec normal as low as 0.007
        //0.0185 millisec others
    }
}
