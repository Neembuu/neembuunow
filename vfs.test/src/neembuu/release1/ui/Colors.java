/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.ui;

import java.awt.Color;

/**
 *
 * @author Shashank Tulsyan
 */
public final class Colors {

    public static Color BORDER          = hsl(35, 72, 184, HSLSource.MSEXCEL), 
            TINTED_IMAGE                = hsl(36, 73, 206, HSLSource.MSEXCEL),
            BUTTON_TINT                 = hsl(36, 73, 216, HSLSource.MSEXCEL),
            PROGRESS_BAR_BACKGROUND     = hsl(35, 69, 209, HSLSource.MSEXCEL),
            NIMBUS_BASE                 = hsl(35, 100, 135, HSLSource.MSEXCEL), 
            TEXT_BACKGROUND             = hsl(35, 100, 254, HSLSource.MSEXCEL), 
            PROGRESS_DOWNLOAD_LESS_MODE = hsl(35, 72, 120, HSLSource.MSEXCEL), 
            OVERLAY                     = hsl(36, 71, 232, 0.3f, HSLSource.MSEXCEL),
            //SIZ9_POST_BACKGROUND        = hsl(31, 73, 248, HSLSource.MSEXCEL),
            
            CONTROL_ICONS               = hsl(140, 255, 58, HSLSource.MSEXCEL), 
            PROGRESS_BAR_FILL_ACTIVE    = hsl(140, 209, 105, HSLSource.MSEXCEL),
            PROGRESS_BAR_FILL_BUFFER   = hsl(140, 85, 212, HSLSource.MSEXCEL);
    
            
    

    private static Color rgb(int r, int g, int b) {
        return new Color(r, g, b);
    }
    
    private static Color hsl(int h, int s, int l,float alpha, HSLSource hSLSource){
        int base = hSLSource.getBase();
        return new Color(HSLColor.toRGB((float)h/base, (float)s/base, (float)l/base,alpha),true);
    }
    
    private static Color hsl(int h, int s, int l,HSLSource hSLSource){
        int base = hSLSource.getBase();
        return new Color(HSLColor.toRGB((float)h/base, (float)s/base, (float)l/base));
    }
    
    public static enum HSLSource {
        MSPAINT(240),
        MSEXCEL(255);
        private int base;

        private HSLSource(int base) {
            this.base = base;
        }

        public int getBase() {
            return base;
        }
        
    }
    
    /**
     * This class interfaces with colors in terms of Hue, Saturation and
     * Luminance.
     * <P>
     * This is copied from Rob Camick's class here:
     * <br>http://tips4java.wordpress.com/2009/07/05/hsl-color/
     *
     * @see <a href="http://tips4java.wordpress.com/2009/07/05/hsl-color/">HSL
     * Color</a>
     */
    public static class HSLColor {

        /**
         * Convert a RGB Color to it corresponding HSL values.
         *
         * @param color the color to convert.
         * @param dest the optional array to store the result in.
         * @return an array containing the 3 HSL values.
         */
        public static float[] fromRGB(Color color, float[] dest) {
            //  Get RGB values in the range 0 - 1

            float r = (color.getRed()) / 255f;
            float g = (color.getGreen()) / 255f;
            float b = (color.getBlue()) / 255f;

		//	Minimum and Maximum RGB values are used in the HSL calculations
            float min = Math.min(r, Math.min(g, b));
            float max = Math.max(r, Math.max(g, b));

		//  Calculate the Hue
            float h = 0;

            if (max == min) {
                h = 0;
            } else if (max == r) {
                h = (((g - b) / (max - min) / 6f) + 1) % 1;
            } else if (max == g) {
                h = ((b - r) / (max - min) / 6f) + 1f / 3f;
            } else if (max == b) {
                h = ((r - g) / (max - min) / 6f) + 2f / 3f;
            }

		//  Calculate the Luminance
            float l = (max + min) / 2;

		//  Calculate the Saturation
            float s = 0;

            if (max == min) {
                s = 0;
            } else if (l <= .5f) {
                s = (max - min) / (max + min);
            } else {
                s = (max - min) / (2 - max - min);
            }

            if (dest == null) {
                dest = new float[3];
            }
            dest[0] = h;
            dest[1] = s;
            dest[2] = l;

            return dest;
        }

        /**
         * Convert HSL values to a RGB Color with a default alpha value of 1. H
         * (Hue) is specified as degrees in the range 0 - 1. S (Saturation) is
         * specified as a percentage in the range 0 - 1. L (Luminance) is
         * specified as a percentage in the range 0 - 1.
         *
         * @param hsl an array containing the 3 HSL values
         */
        public static int toRGB(float[] hsl) {
            return toRGB(hsl, 1.0f);
        }

        /**
         * Convert HSL values to a RGB Color. H (Hue) is specified as degrees in
         * the range 0 - 1. S (Saturation) is specified as a percentage in the
         * range 0 - 1. L (Luminance) is specified as a percentage in the range
         * 0 - 1.
         *
         * @param hsl an array containing the 3 HSL values
         * @param alpha the alpha value between 0 - 1
         */
        public static int toRGB(float[] hsl, float alpha) {
            return toRGB(hsl[0], hsl[1], hsl[2], alpha);
        }

        /**
         * Convert HSL values to a RGB Color with a default alpha value of 1.
         *
         * @param h Hue is specified as degrees in the range 0 - 1.
         * @param s Saturation is specified as a percentage in the range 0 - 1.
         * @param l Luminance is specified as a percentage in the range 0 - 1.
         */
        public static int toRGB(float h, float s, float l) {
            return toRGB(h, s, l, 1.0f);
        }
        
        public static int toRGBA(float h, float s, float l, float a) {
            return toRGB(h, s, l, a);
        }

        /**
         * Convert HSL values to a RGB Color.
         *
         * @param h Hue is specified as degrees in the range 0 - 1.
         * @param s Saturation is specified as a percentage in the range 0 - 1.
         * @param l Luminance is specified as a percentage in the range 0 - 1.
         * @param alpha the alpha value between 0 - 1
         */
        public static int toRGB(float h, float s, float l, float alpha) {
            if (s < 0.0f || s > 1.0f) {
                String message = "Color parameter outside of expected range - Saturation (" + s + ")";
                throw new IllegalArgumentException(message);
            }

            if (l < 0.0f || l > 1.0f) {
                String message = "Color parameter outside of expected range - Luminance (" + l + ")";
                throw new IllegalArgumentException(message);
            }

            if (alpha < 0.0f || alpha > 1.0f) {
                String message = "Color parameter outside of expected range - Alpha (" + alpha + ")";
                throw new IllegalArgumentException(message);
            }

            float q = 0;

            if (l < 0.5) {
                q = l * (1 + s);
            } else {
                q = (l + s) - (s * l);
            }

            float p = 2 * l - q;

            int r = (int) (255 * Math.max(0, HueToRGB(p, q, h + (1.0f / 3.0f))));
            int g = (int) (255 * Math.max(0, HueToRGB(p, q, h)));
            int b = (int) (255 * Math.max(0, HueToRGB(p, q, h - (1.0f / 3.0f))));

            int alphaInt = (int) (255 * alpha);

            return (alphaInt << 24) + (r << 16) + (g << 8) + (b);
        }

        private static float HueToRGB(float p, float q, float h) {
            if (h < 0) {
                h += 1;
            }

            if (h > 1) {
                h -= 1;
            }

            if (6 * h < 1) {
                return p + ((q - p) * 6 * h);
            }

            if (2 * h < 1) {
                return q;
            }

            if (3 * h < 2) {
                return p + ((q - p) * 6 * ((2.0f / 3.0f) - h));
            }

            return p;
        }
    }
}
