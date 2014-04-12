/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.captcha;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import neembuu.release1.httpclient.NHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author davidepastore
 */
public class ImagePanel extends JPanel{

    private BufferedImage image;
    
    public ImagePanel(){
        
    }
    
    public ImagePanel(URL imageFileUrl, HttpContext httpContext){
        try {                
            HttpClient httpClient = NHttpClient.getInstance();
            HttpGet httpGet = new HttpGet(imageFileUrl.toURI());
            HttpResponse httpresponse = httpClient.execute(httpGet, httpContext);
            byte[] imageInByte = EntityUtils.toByteArray(httpresponse.getEntity());
            InputStream in = new ByteArrayInputStream(imageInByte);
            
            /*
            System.out.println("Trying to fetch image from: " + imageFileUrl.toURI());
            System.out.println("Image in byte length: " + imageInByte.length);
            */
            
            image = ImageIO.read(in);
            //image = ImageIO.read(imageFileUrl);
            
            
//            System.out.println("Image: " + image);
            
        } catch (Exception ex) {
//            NULogger.getLogger().log(Level.INFO, "ImagePanel exception: {0}", ex.getMessage());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //int x = this.getWidth()/4;
        g.drawImage(image, 10, 10, null); // see javadoc for more info on the parameters            
    }
}
