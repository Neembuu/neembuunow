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
package neembuu.swing;

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
    
    public static TintedGreyScaledImage make(Object clz,String src,boolean monoColor){
        return TintedGreyScaledImage.make(clz.getClass().getResource(src),monoColor);
    }
    public static TintedGreyScaledImage make(URL src,boolean monoColor){
        return new TintedGreyScaledImage(src,monoColor);
    }
    
    public static BufferedImage getTintedBufferedImage(BufferedImage src, Color tintingColor,boolean monoColor) {
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

        return bi;
    }
    
    public static ImageIcon getTintedImage(BufferedImage src, Color tintingColor,boolean monoColor) {
        return new ImageIcon(getTintedBufferedImage(src, tintingColor, monoColor));
    }

    static int tintColor(int src, Color tinting_color,boolean monoColor,float averageLum) {
        Color sC = new Color(src);
        int R = sC.getRed(), G = sC.getGreen(), B = sC.getBlue();
        float lumi = (0.2989f * R + 0.5870f * G + 0.1140f * B);

        float newLumi = 0;
        
        float[] hsl = new float[3];
        HSLColor.fromRGB(tinting_color, hsl);
        newLumi = lumi/255;
        if(monoColor){
            //rescale luminsence according to max value
            //not everywhere same color is used as anti-alised edges will be spoiled otherwise
            if(lumi<=averageLum){
                newLumi = hsl[2];//*lumi/averageLum;
            }
        }
        hsl[2] = newLumi;
        return HSLColor.toRGB(hsl);
    }

    static GraphicsConfiguration getGraphicsConfiguration() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }

}
