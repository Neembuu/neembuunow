/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.ui;

import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Shashank Tulsyan
 */
public class TintedGreyScaledImage {
            //Color.BLUE; //new Color(196,189,151);
    private final URL src;
    private final boolean monoColor;
    private BufferedImage bi = null;
    private ImageIcon baseImage = null;

    public TintedGreyScaledImage(URL src, boolean monoColor) {
        this.src = src;
        this.monoColor = monoColor;
    }

    public ImageIcon getBaseImage() {
        if(baseImage==null){
            baseImage = new ImageIcon(src);   
        }
        return baseImage;
    }

    public ImageIcon getTintedImage(Color tintingColor) {
        if(bi==null){
            try{
                bi = ImageIO.read(src);
            }catch(Exception a){
                a.printStackTrace();
                return getBaseImage();
            }
        }
        return getTintedImage(bi,tintingColor,monoColor);
    }
    
    public static TintedGreyScaledImage make(String src,boolean monoColor){
        return new TintedGreyScaledImage(TintedGreyScaledImage.class.getResource(src),monoColor);
    }
    
    static ImageIcon getTintedImage(BufferedImage src, Color tintingColor,boolean monoColor) {
        BufferedImage bi = getGraphicsConfiguration().createCompatibleImage(src.getWidth(), src.getHeight());

        
        float sumLum = 0; int obv = 0; float lumi;
        if(monoColor){
            for (int x = 0; x < src.getWidth(); x++) {
                for (int y = 0; y < src.getHeight(); y++) {
                    Color sC = new Color(src.getRGB(x, y));
                    int R = sC.getRed(), G = sC.getGreen(), B = sC.getBlue();
                    lumi = (0.2989f * R + 0.5870f * G + 0.1140f * B);
                    if(lumi<250){
                        sumLum += lumi; obv++;
                    }
                }
            }
            System.out.println("aver="+(sumLum/obv));
        }
        
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                bi.setRGB(x, y, tintColor(src.getRGB(x, y), tintingColor,monoColor,(sumLum/obv)));
            }
        }

        return new ImageIcon(bi);
    }

    static int tintColor(int src, Color tinting_color,boolean monoColor,float averageLum) {
        Color sC = new Color(src);
        int R = sC.getRed(), G = sC.getGreen(), B = sC.getBlue();
        float lumi = (0.2989f * R + 0.5870f * G + 0.1140f * B);

        float newLumi = 0;
        
        float[] hsl = new float[3];
        Colors.HSLColor.fromRGB(tinting_color, hsl);
        newLumi = lumi/255;
        if(monoColor){
            //rescale luminsence according to max value
            //not everywhere same color is used as anti-alised edges will be spoiled otherwise
            if(lumi<=averageLum){
                newLumi = hsl[2];//*lumi/averageLum;
            }
        }
        hsl[2] = newLumi;
        return Colors.HSLColor.toRGB(hsl);
    }

    static GraphicsConfiguration getGraphicsConfiguration() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }

}
