/*
 *  Copyright (C) 2010 Shashank Tulsyan
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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

/**
 * Copy pasted portions ProgressBarPainter of NimbusLookAndFeel
 * with dependencies. Provides scope of optimization by caching gradient,
 * because of which significant performance improvement is observed (not measured yet).
 * @author Shashank Tulsyan
 */
/*package private*/ final class NimbusProgressGradient
        extends JComponent{

    public static final Color pink = new Color(200, 105, 138, 255);
    public static final Color nimbusOrange = new Color(191,98,4, 255);

    private NimbusProgressGradient(){
        
    }
    

    int w = 20;

    public static BufferedImage getUnprogressedStrip(Color background, Color unprogressedBase, int height){
        NimbusProgressGradient gradient = new NimbusProgressGradient();
        return gradient.generateUnprogressedStrip(background, unprogressedBase, height);
    }

    public static BufferedImage getProgressedStrip(Color background, Color baseColor, int height){
        NimbusProgressGradient gradient = new NimbusProgressGradient();
        return 
            gradient.generateProgressedStrip(background, baseColor, height);
    }

    public BufferedImage generateUnprogressedStrip(Color background,Color unprogressedBase, int height){
        BufferedImage image = GraphicsEnvironment
                    .getLocalGraphicsEnvironment().getDefaultScreenDevice()
                    .getDefaultConfiguration().createCompatibleImage(w, height);
        Graphics2D gd = (Graphics2D)image.getGraphics();

        gd.setColor(background);
        gd.fillRect(0, 0, w, height);
        prepare(w, height, new Insets(5, 5, 5, 5));
        paintBackgroundEnabled(gd,unprogressedBase);
        gd.dispose();
        return image.getSubimage(w/2, 0, 1, height);
    }

    public BufferedImage generateProgressedStrip(Color background, Color baseColor, int height){
        BufferedImage image = GraphicsEnvironment
                    .getLocalGraphicsEnvironment().getDefaultScreenDevice()
                    .getDefaultConfiguration().createCompatibleImage(w, height);
        Graphics2D gd = (Graphics2D)image.getGraphics();
        gd.setColor(background);
        gd.fillRect(0, 0, w, height);
        prepare(w, height, new Insets(5, 5, 5, 5));
        paintForegroundEnabledAndFinished(gd, baseColor);
        gd.dispose();
        return image.getSubimage(w/2, 0, 1, height);
    }

    BufferedImage unprog = null;
    BufferedImage prog = null;

    @Override
    protected void paintComponent(Graphics g) {

        unprog = generateUnprogressedStrip(getBackground(), nimbusBlueGrey, getHeight());
        prog = generateProgressedStrip(getBackground(), nimbusBlueGrey,getHeight());

        g.drawImage(unprog, 0, 0, getWidth(),getHeight(), null);
        g.drawImage(prog, 0, 0, getWidth()/2,getHeight(), null);

//        long startTime = System.nanoTime();
//        Paint p = new TexturePaint(prog, new Rectangle2D.Double(0, 0, getWidth(), getHeight()) );
//        Paint oldPaint =  ((Graphics2D)g).getPaint();
//        ((Graphics2D)g).setPaint(p);
//        /*for (int i = 0; i < 100000; i++) {
//            ((Graphics2D)g).fillRect(getWidth()/2, 0, getWidth()/4,getHeight()/2);
//        }*/
//
//        long endTime = System.nanoTime();
//        System.out.println("Time taken="+(endTime-startTime)/1000000);
//
//        ((Graphics2D)g).setPaint(oldPaint);
//        startTime = System.nanoTime();
//        for (int i = 0; i < 1000000; i++) {
//            g.setClip(new Rectangle2D.Double(0, 0, getWidth(), getHeight()/2) );
//            g.drawImage(prog, getWidth()/2, 0, getWidth()/4,getHeight(),null);
//        }
//        endTime = System.nanoTime();
//        System.out.println("Time taken="+(endTime-startTime)/1000000);



        g.setClip((int)(getWidth()*0.67), 0, 1, (int)(getHeight()*0.67));
        g.drawImage(prog, (int)(getWidth()*0.67), 0, 1, getHeight(), null);
        //paintComponent2D((Graphics2D)g);
    }

    protected void paintComponent2D(Graphics2D g) {
        //g.drawRect(0, 0, getWidth(), getHeight());
        prepare(500, getHeight(), new Insets(5, 0, 5, 0));
        paintBackgroundEnabled(g,nimbusBlueGrey);
        prepare(250, getHeight());
        paintForegroundEnabledAndFinished(g,nimbusOrange);
    }

    protected void paintComponent2D_1(Graphics2D g) {
        prepare(getWidth(), getHeight());
        //g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color colors[]={
          new Color(200, 105, 138, 255),
          new Color(255, 255, 255, 255),
          new Color(10, 205, 138, 255),
          new Color(0, 0, 0, 255),
        };

        int n = colors.length+1;
        paintBackgroundEnabled(g,nimbusBlueGrey);
        prepare(getWidth()/n, getHeight());

        for (int i = 0; i < n-1; i++) {
            paintForegroundEnabledAndFinished((Graphics2D)g.create(getWidth()*i/n-9*i,0,getWidth()/n,getHeight()),colors[i]);
        }
        
        //paintForegroundEnabledAndFinished((Graphics2D)g.create(0,0,getWidth()/n,getHeight()),"nimbusFocus" );
        //paintForegroundEnabledAndFinished((Graphics2D)g.create(getWidth()/n-8,0,getWidth()/n,getHeight()),"nimbusSelectionBackground");
    }

    private Rectangle2D rect = new Rectangle2D.Float(0, 0, 0, 0);

    private void paintBackgroundEnabled(Graphics2D g,Color unprogressedBase) {
        rect = decodeRect1();
        g.setPaint(decodeGradient1(rect,unprogressedBase));
        g.fill(rect);
        rect = decodeRect2();
        g.setPaint(decodeGradient2(rect,unprogressedBase));
        g.fill(rect);
    }

    private Rectangle2D decodeRect1() {
        rect.setRect(decodeX(0.4f), //x
                         decodeY(0.4f), //y
                         decodeX(2.6f) - decodeX(0.4f), //width
                         decodeY(2.6f) - decodeY(0.4f)); //height
        return rect;
    }

    private Rectangle2D decodeRect2() {
        rect.setRect(decodeX(0.6f), //x
                         decodeY(0.6f), //y
                         decodeX(2.4f) - decodeX(0.6f), //width
                         decodeY(2.4f) - decodeY(0.6f)); //height
        return rect;
    }

    private void paintForegroundEnabledAndFinished(Graphics2D g,Color baseColor) {
        path = decodePath2();
        g.setPaint(getColor(17,baseColor));
        g.fill(path);
        rect = decodeRect1();
        g.setPaint(decodeGradient5(rect,baseColor));
        g.fill(rect);
        rect = decodeRect2();
        g.setPaint(decodeGradient6(rect,baseColor));
        g.fill(rect);

        /*rect = decodeRect2();
        g.setPaint(getColor(17,baseColor));
        g.fill(rect);
        g.setPaint(decodeGradient5(rect,baseColor));
        g.fill(rect);
        g.setPaint(decodeGradient6(rect,baseColor));
        g.fill(rect);*/
    }

    private Path2D path = new Path2D.Float();

    private Path2D decodePath2() {
        path.reset();
        path.moveTo(decodeX(0.5466667f), decodeY(0.12666667f));
        path.curveTo(decodeAnchorX(0.54666668176651f, -2.000000000000001f), decodeAnchorY(0.12666666507720947f, 0.0f), decodeAnchorX(0.12000000476837158f, 0.0f), decodeAnchorY(0.6066666841506958f, -1.9999999999999998f), decodeX(0.120000005f), decodeY(0.6066667f));
        path.lineTo(decodeX(0.120000005f), decodeY(2.4266667f));
        path.curveTo(decodeAnchorX(0.12000000476837158f, 0.0f), decodeAnchorY(2.426666736602783f, 2.0f), decodeAnchorX(0.5800000429153442f, -2.0f), decodeAnchorY(2.879999876022339f, 0.0f), decodeX(0.58000004f), decodeY(2.8799999f));
        path.lineTo(decodeX(2.4f), decodeY(2.8733335f));
        path.curveTo(decodeAnchorX(2.4000000953674316f, 1.9709292441265305f), decodeAnchorY(2.87333345413208f, 0.019857039365145823f), decodeAnchorX(2.866666793823242f, -0.03333333333333499f), decodeAnchorY(2.433333158493042f, 1.9333333333333869f), decodeX(2.8666668f), decodeY(2.4333332f));
        path.lineTo(decodeX(2.8733335f), decodeY(1.9407407f));
        path.lineTo(decodeX(2.8666668f), decodeY(1.1814815f));
        path.lineTo(decodeX(2.8666668f), decodeY(0.6066667f));
        path.curveTo(decodeAnchorX(2.866666793823242f, 0.0042173304174148996f), decodeAnchorY(0.6066666841506958f, -1.9503377583381705f), decodeAnchorX(2.4599997997283936f, 1.9659460194139413f), decodeAnchorY(0.13333334028720856f, 0.017122267221350018f), decodeX(2.4599998f), decodeY(0.13333334f));
        path.lineTo(decodeX(0.5466667f), decodeY(0.12666667f));
        path.closePath();
        return path;
    }
    
    protected final float decodeX(float x) {
        if (x >= 0 && x <= 1) {
            return x * leftWidth;
        } else if (x > 1 && x < 2) {
            return ((x-1) * centerWidth) + leftWidth;
        } else if (x >= 2 && x <= 3) {
            return ((x-2) * rightWidth) + leftWidth + centerWidth;
        } else {
            throw new IllegalArgumentException("Invalid x");
        }
    }

    /**
     * Decodes and returns a float value representing the actual pixel location for
     * the given encoded y value.
     *
     * @param y an encoded y value (0...1, or 1...2, or 2...3)
     * @return the decoded y value
     * @throws IllegalArgumentException
     *      if {@code y < 0} or {@code y > 3}
     */
    protected final float decodeY(float y) {
        if (y >= 0 && y <= 1) {
            return y * topHeight;
        } else if (y > 1 && y < 2) {
            return ((y-1) * centerHeight) + topHeight;
        } else if (y >= 2 && y <= 3) {
            return ((y-2) * bottomHeight) + topHeight + centerHeight;
        } else {
            throw new IllegalArgumentException("Invalid y");
        }
    }

    protected final float decodeAnchorX(float x, float dx) {
        if (x >= 0 && x <= 1) {
            return decodeX(x) + (dx * leftScale);
        } else if (x > 1 && x < 2) {
            return decodeX(x) + (dx * centerHScale);
        } else if (x >= 2 && x <= 3) {
            return decodeX(x) + (dx * rightScale);
        } else {
            throw new IllegalArgumentException("Invalid x");
        }
    }

    /**
     * Decodes and returns a float value representing the actual pixel location for
     * the anchor point given the encoded Y value of the control point, and the offset
     * distance to the anchor from that control point.
     *
     * @param y an encoded y value of the bezier control point (0...1, or 1...2, or 2...3)
     * @param dy the offset distance to the anchor from the control point y
     * @return the decoded y position of the control point
     * @throws IllegalArgumentException
     *      if {@code y < 0} or {@code y > 3}
     */
    protected final float decodeAnchorY(float y, float dy) {
        if (y >= 0 && y <= 1) {
            return decodeY(y) + (dy * topScale);
        } else if (y > 1 && y < 2) {
            return decodeY(y) + (dy * centerVScale);
        } else if (y >= 2 && y <= 3) {
            return decodeY(y) + (dy * bottomScale);
        } else {
            throw new IllegalArgumentException("Invalid y");
        }
    }

    private Color darkenColor(Color color){
        return new Color(
                Math.abs(darkenBy(color.getRed(),0.5f)-50),
                Math.abs(darkenBy(color.getGreen(),0.5f)-50),
                Math.abs(darkenBy(color.getBlue(),0.5f)-50),
                255
            );
    }
    private int darkenBy(int v, float factor){
        int ret = (int)(v*factor);
        if(v>=255)return 255;
        return v;
    }


    /**
     * PaintContext, which holds a lot of the state needed for cache hinting and x/y value decoding
     * The data contained within the context is typically only computed once and reused over
     * multiple paint calls, whereas the other values (w, h, f, leftWidth, etc) are recomputed
     * for each call to paint.
     *
     * This field is retrieved from subclasses on each paint operation. It is up
     * to the subclass to compute and cache the PaintContext over multiple calls.
     */
    private PaintContext ctx;
    protected static class PaintContext {
        protected static enum CacheMode {
            NO_CACHING, FIXED_SIZES, NINE_SQUARE_SCALE
        }

        private static Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);

        private Insets stretchingInsets;
        private Dimension canvasSize;
        private boolean inverted;
        private CacheMode cacheMode;
        private double maxHorizontalScaleFactor;
        private double maxVerticalScaleFactor;

        private float a; // insets.left
        private float b; // canvasSize.width - insets.right
        private float c; // insets.top
        private float d; // canvasSize.height - insets.bottom;
        private float aPercent; // only used if inverted == true
        private float bPercent; // only used if inverted == true
        private float cPercent; // only used if inverted == true
        private float dPercent; // only used if inverted == true

        /**
         * Creates a new PaintContext which does not attempt to cache or scale any cached
         * images.
         *
         * @param insets The stretching insets. May be null. If null, then assumed to be 0, 0, 0, 0.
         * @param canvasSize The size of the canvas used when encoding the various x/y values. May be null.
         *                   If null, then it is assumed that there are no encoded values, and any calls
         *                   to one of the "decode" methods will return the passed in value.
         * @param inverted Whether to "invert" the meaning of the 9-square grid and stretching insets
         */
        public PaintContext(Insets insets, Dimension canvasSize, boolean inverted) {
            this(insets, canvasSize, inverted, null, 1, 1);
        }

        /**
         * Creates a new PaintContext.
         *
         * @param insets The stretching insets. May be null. If null, then assumed to be 0, 0, 0, 0.
         * @param canvasSize The size of the canvas used when encoding the various x/y values. May be null.
         *                   If null, then it is assumed that there are no encoded values, and any calls
         *                   to one of the "decode" methods will return the passed in value.
         * @param inverted Whether to "invert" the meaning of the 9-square grid and stretching insets
         * @param cacheMode A hint as to which caching mode to use. If null, then set to no caching.
         * @param maxH The maximium scale in the horizontal direction to use before punting and redrawing from scratch.
         *             For example, if maxH is 2, then we will attempt to scale any cached images up to 2x the canvas
         *             width before redrawing from scratch. Reasonable maxH values may improve painting performance.
         *             If set too high, then you may get poor looking graphics at higher zoom levels. Must be >= 1.
         * @param maxV The maximium scale in the vertical direction to use before punting and redrawing from scratch.
         *             For example, if maxV is 2, then we will attempt to scale any cached images up to 2x the canvas
         *             height before redrawing from scratch. Reasonable maxV values may improve painting performance.
         *             If set too high, then you may get poor looking graphics at higher zoom levels. Must be >= 1.
         */
        public PaintContext(Insets insets, Dimension canvasSize, boolean inverted,
                            CacheMode cacheMode, double maxH, double maxV) {
            if (maxH < 1 || maxH < 1) {
                throw new IllegalArgumentException("Both maxH and maxV must be >= 1");
            }

            this.stretchingInsets = insets == null ? EMPTY_INSETS : insets;
            this.canvasSize = canvasSize;
            this.inverted = inverted;
            this.cacheMode = cacheMode == null ? CacheMode.NO_CACHING : cacheMode;
            this.maxHorizontalScaleFactor = maxH;
            this.maxVerticalScaleFactor = maxV;

            if (canvasSize != null) {
                a = stretchingInsets.left;
                b = canvasSize.width - stretchingInsets.right;
                c = stretchingInsets.top;
                d = canvasSize.height - stretchingInsets.bottom;
                this.canvasSize = canvasSize;
                this.inverted = inverted;
                if (inverted) {
                    float available = canvasSize.width - (b - a);
                    aPercent = available > 0f ? a / available : 0f;
                    bPercent = available > 0f ? b / available : 0f;
                    available = canvasSize.height - (d - c);
                    cPercent = available > 0f ? c / available : 0f;
                    dPercent = available > 0f ? d / available : 0f;
                }
            }
        }
    }

    private float f;
    
    private float leftWidth;
    /**
     * The height of the top section. Recomputed on each call to paint.
     */
    private float topHeight;
    /**
     * The width of the center section. Recomputed on each call to paint.
     */
    private float centerWidth;
    /**
     * The height of the center section. Recomputed on each call to paint.
     */
    private float centerHeight;
    /**
     * The width of the right section. Recomputed on each call to paint.
     */
    private float rightWidth;
    /**
     * The height of the bottom section. Recomputed on each call to paint.
     */
    private float bottomHeight;
    /**
     * The scaling factor to use for the left section. Recomputed on each call to paint.
     */
    private float leftScale;
    /**
     * The scaling factor to use for the top section. Recomputed on each call to paint.
     */
    private float topScale;
    /**
     * The scaling factor to use for the center section, in the horizontal
     * direction. Recomputed on each call to paint.
     */
    private float centerHScale;
    /**
     * The scaling factor to use for the center section, in the vertical
     * direction. Recomputed on each call to paint.
     */
    private float centerVScale;
    /**
     * The scaling factor to use for the right section. Recomputed on each call to paint.
     */
    private float rightScale;
    /**
     * The scaling factor to use for the bottom section. Recomputed on each call to paint.
     */
    private float bottomScale;

    
    public void prepare(float w, float h) {
        prepare(w, h, new Insets(5, 5, 5, 5));
    }
    
    public void prepare(float w, float h, Insets insets) {
    

        if(ctx==null){
            ctx = new PaintContext(
                insets,
                new Dimension((int)w,(int)h),
                false);
        }


        //if no PaintContext has been specified, reset the values and bail
        //also bail if the canvasSize was not set (since decoding will not work)
        if (ctx == null || ctx.canvasSize == null) {
            f = 1f;
            leftWidth = centerWidth = rightWidth = 0f;
            topHeight = centerHeight = bottomHeight = 0f;
            leftScale = centerHScale = rightScale = 0f;
            topScale = centerVScale = bottomScale = 0f;
            return;
        }

        //calculate the scaling factor, and the sizes for the various 9-square sections
        Number scale = (Number)UIManager.get("scale");
        f = scale == null ? 1f : scale.floatValue();

        if (ctx.inverted) {
            centerWidth = (ctx.b - ctx.a) * f;
            float availableSpace = w - centerWidth;
            leftWidth = availableSpace * ctx.aPercent;
            rightWidth = availableSpace * ctx.bPercent;
            centerHeight = (ctx.d - ctx.c) * f;
            availableSpace = h - centerHeight;
            topHeight = availableSpace * ctx.cPercent;
            bottomHeight = availableSpace * ctx.dPercent;
        } else {
            leftWidth = ctx.a * f;
            rightWidth = (float)(ctx.canvasSize.getWidth() - ctx.b) * f;
            centerWidth = w - leftWidth - rightWidth;
            topHeight = ctx.c * f;
            bottomHeight = (float)(ctx.canvasSize.getHeight() - ctx.d) * f;
            centerHeight = h - topHeight - bottomHeight;
        }

        leftScale = ctx.a == 0f ? 0f : leftWidth / ctx.a;
        centerHScale = (ctx.b - ctx.a) == 0f ? 0f : centerWidth / (ctx.b - ctx.a);
        rightScale = (ctx.canvasSize.width - ctx.b) == 0f ? 0f : rightWidth / (ctx.canvasSize.width - ctx.b);
        topScale = ctx.c == 0f ? 0f : topHeight / ctx.c;
        centerVScale = (ctx.d - ctx.c) == 0f ? 0f : centerHeight / (ctx.d - ctx.c);
        bottomScale = (ctx.canvasSize.height - ctx.d) == 0f ? 0f : bottomHeight / (ctx.canvasSize.height - ctx.d);
    }

    private Paint decodeGradient1(Shape s,Color unprogressedBase) {
        Rectangle2D bounds = s.getBounds2D();
        float x = (float)bounds.getX();
        float y = (float)bounds.getY();
        float w = (float)bounds.getWidth();
        float h = (float)bounds.getHeight();
        return decodeGradient((0.5f * w) + x, (0.0f * h) + y, (0.5f * w) + x, (1.0f * h) + y,
                new float[] { 0.0f,0.5f,1.0f },
                new Color[] { getColor1(unprogressedBase),
                            decodeColor(getColor1(unprogressedBase),getColor2(unprogressedBase),0.5f),
                            getColor2(unprogressedBase)});
    }

    private Paint decodeGradient2(Shape s,Color unprogressedBase) {
        Rectangle2D bounds = s.getBounds2D();
        float x = (float)bounds.getX();
        float y = (float)bounds.getY();
        float w = (float)bounds.getWidth();
        float h = (float)bounds.getHeight();
        return decodeGradient((0.5f * w) + x, (0.0f * h) + y, (0.5f * w) + x, (1.0f * h) + y,
                new float[] { 0.038709678f,0.05967742f,0.08064516f,0.23709677f,0.3935484f,0.41612905f,0.43870968f,0.67419356f,0.90967745f,0.91451615f,0.91935486f },
                new Color[] { getColor3(unprogressedBase),
                            decodeColor(getColor3(unprogressedBase),getColor4(unprogressedBase),0.5f),
                            getColor4(unprogressedBase),
                            decodeColor(getColor4(unprogressedBase),getColor5(unprogressedBase),0.5f),
                            getColor5(unprogressedBase),
                            decodeColor(getColor5(unprogressedBase),getColor6(unprogressedBase),0.5f),
                            getColor6(unprogressedBase),
                            decodeColor(getColor6(unprogressedBase),getColor7(unprogressedBase),0.5f),
                            getColor7(unprogressedBase),
                            decodeColor(getColor7(unprogressedBase),getColor8(unprogressedBase),0.5f),
                            getColor8(unprogressedBase)});
    }

    private Paint decodeGradient5(Shape s, Color baseColor) {
        Rectangle2D bounds = s.getBounds2D();
        float x = (float)bounds.getX();
        float y = (float)bounds.getY();
        float w = (float)bounds.getWidth();
        float h = (float)bounds.getHeight();
        return decodeGradient((0.5f * w) + x, (0.0f * h) + y, (0.5f * w) + x, (1.0f * h) + y,
                new float[] { 0.038709678f,0.05483871f,0.07096774f,0.28064516f,0.4903226f,0.6967742f,0.9032258f,0.9241935f,0.9451613f },
                new Color[] { getColor(18,baseColor),
                            decodeColor(getColor(18,baseColor),getColor(19,baseColor),0.5f),
                            getColor(19,baseColor),
                            decodeColor(getColor(19,baseColor),getColor(20,baseColor),0.5f),
                            getColor(20,baseColor),
                            decodeColor(getColor(20,baseColor),getColor(21,baseColor),0.5f),

                            getColor(21,baseColor),
                            decodeColor(getColor(21,baseColor),getColor(22,baseColor),0.5f),
                            getColor(22,baseColor)});
    }

    private Color getColor(int index,Color baseColor){
        switch(index){
            case 17: return decodeColor(baseColor, 0.0f, 0.0f, 0.0f, -156);
            case 18: return decodeColor(baseColor, -0.015796512f, 0.02094239f, -0.15294117f, 0);
            case 19: return decodeColor(baseColor, -0.004321605f, 0.02094239f, -0.0745098f, 0);
            case 20: return decodeColor(baseColor, -0.008021399f, 0.02094239f, -0.10196078f, 0);
            case 21: return decodeColor(baseColor, -0.011706904f, -0.1790576f, -0.02352941f, 0);
            case 22: return decodeColor(baseColor, -0.048691254f, 0.02094239f, -0.3019608f, 0);
            case 23: return decodeColor(baseColor, 0.003940329f, -0.7375322f, 0.17647058f, 0);
            case 24: return decodeColor(baseColor, 0.005506739f, -0.46764207f, 0.109803915f, 0);
            case 25: return decodeColor(baseColor, 0.0042127445f, -0.18595415f, 0.04705882f, 0);
            case 26: return decodeColor(baseColor, 0.0047626942f, 0.02094239f, 0.0039215684f, 0);
            case 27: return decodeColor(baseColor, 0.0047626942f, -0.15147138f, 0.1607843f, 0);
            case 28: return decodeColor(baseColor, 0.010665476f, -0.27317524f, 0.25098038f, 0);
        }return null;

    }

    private Paint decodeGradient6(Shape s,Color baseColor) {
        Rectangle2D bounds = s.getBounds2D();
        float x = (float)bounds.getX();
        float y = (float)bounds.getY();
        float w = (float)bounds.getWidth();
        float h = (float)bounds.getHeight();
        return decodeGradient((0.5f * w) + x, (0.0f * h) + y, (0.5f * w) + x, (1.0f * h) + y,
                new float[] { 0.038709678f,0.061290324f,0.08387097f,0.27258065f,0.46129033f,0.4903226f,0.5193548f,0.71774197f,0.91612905f,0.92419356f,0.93225807f },
                new Color[] { getColor(23,baseColor),
                            decodeColor(getColor(23,baseColor),getColor(24,baseColor),0.5f),
                            getColor(24,baseColor),
                            decodeColor(getColor(24,baseColor),getColor(25,baseColor),0.5f),
                            getColor(25,baseColor),
                            decodeColor(getColor(25,baseColor),getColor(26,baseColor),0.5f),
                            getColor(26,baseColor),
                            decodeColor(getColor(26,baseColor),getColor(27,baseColor),0.5f),
                            getColor(27,baseColor),
                            decodeColor(getColor(27,baseColor),getColor(28,baseColor),0.5f),
                            getColor(28,baseColor)});
    }

    protected final LinearGradientPaint decodeGradient(float x1, float y1, float x2, float y2, float[] midpoints, Color[] colors) {

        if (x1 == x2 && y1 == y2) {
            y2 += .00001f;
        }
        return new LinearGradientPaint(x1, y1, x2, y2, midpoints, colors);
    }

    public static void main(String[] args) {

        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception lookandfeelexception) {
            
        }

        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        NimbusProgressGradient gradient = new NimbusProgressGradient();
        gradient.setBounds(10, 10, 500, 100);
        JProgressBar jpb = new JProgressBar(0,10);
        jpb.setValue(5);
        jpb.setBounds(520, 10, 500, 100);

        jf.getContentPane().setLayout(null);
        jf.getContentPane().add(gradient);
        jf.getContentPane().add(jpb);
        jf.pack();
        jf.setSize(new Dimension(700,140));
        jf.setVisible(true);
    }


    static final Color nimbusBlueGrey = new Color(169,176,190,255);
    
    private final Color getColor1(Color base){
        /*c=nimbusBlueGrey*/
        return decodeColor(base, 0.0f, -0.04845735f, -0.17647058f, 0);
    }
    private final Color getColor2(Color base){
        return decodeColor(base, 0.0f, -0.061345987f, -0.027450979f, 0);
    }
    private final Color getColor3(Color base){
        return decodeColor(base, 0.0f, -0.110526316f, 0.25490195f, 0);
    }
    private final Color getColor4(Color base){
        return decodeColor(base, 0.0f, -0.097921275f, 0.18823528f, 0);
    }
    private final Color getColor5(Color base){
        return decodeColor(base, 0.0138888955f, -0.0925083f, 0.12549019f, 0);
    }
    private final Color getColor6(Color base){
        return decodeColor(base, 0.0f, -0.08222443f, 0.086274505f, 0);
    }
    private final Color getColor7(Color base){
        return decodeColor(base, 0.0f, -0.08477524f, 0.16862744f, 0);
    }
    private final Color getColor8(Color base){
        return decodeColor(base, 0.0f, -0.086996906f, 0.25490195f, 0);
    }



    protected final Color decodeColor(Color baseColor, float hOffset, float sOffset,
                                      float bOffset, int aOffset) {
        return new DerivedColor(baseColor, hOffset, sOffset, bOffset, aOffset);
    }

    

    protected final Color decodeColor(String key, float hOffset, float sOffset,
                                      float bOffset, int aOffset) {

        return null;

        /*if (UIManager.getLookAndFeel() instanceof NimbusLookAndFeel){
            NimbusLookAndFeel laf = (NimbusLookAndFeel) UIManager.getLookAndFeel();
            Color ret = laf.getDerivedColor(key, hOffset, sOffset, bOffset, aOffset, true);
            System.out.println(key+"="+ret);
            return ret;
        } else {
            // can not give a right answer as painter sould not be used outside
            // of nimbus laf but do the best we can
            return Color.getHSBColor(hOffset,sOffset,bOffset);
        }*/
    }

    protected final Color decodeColor(Color color1, Color color2,
                                      float midPoint) {
        return new Color(deriveARGB(color1, color2, midPoint));
    }

    static int deriveARGB(Color color1, Color color2, float midPoint) {
        int r = color1.getRed() +
                Math.round((color2.getRed() - color1.getRed()) * midPoint);
        int g = color1.getGreen() +
                Math.round((color2.getGreen() - color1.getGreen()) * midPoint);
        int b = color1.getBlue() +
                Math.round((color2.getBlue() - color1.getBlue()) * midPoint);
        int a = color1.getAlpha() +
                Math.round((color2.getAlpha() - color1.getAlpha()) * midPoint);
        return ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8) |
                (b & 0xFF);
    }


    final class DerivedColor extends Color {
        private final Color baseColor;
        private final float hOffset, sOffset, bOffset;
        private final int aOffset;
        private int argbValue;

        DerivedColor(Color basColor, float hOffset, float sOffset, float bOffset, int aOffset) {
            super(0);
            this.baseColor = basColor;
            this.hOffset = hOffset;
            this.sOffset = sOffset;
            this.bOffset = bOffset;
            this.aOffset = aOffset;

            rederiveColor();
        }

        public Color getBaseColor() {
            return baseColor;
        }

        public float getHueOffset() {
            return hOffset;
        }

        public float getSaturationOffset() {
            return sOffset;
        }

        public float getBrightnessOffset() {
            return bOffset;
        }

        public int getAlphaOffset() {
            return aOffset;
        }

        /**
         * Recalculate the derived color from the UIManager parent color and offsets
         */
        public void rederiveColor() {
            Color src = baseColor; // UIManager.getColor(uiDefaultParentName);
            if (src != null) {
                float[] tmp = Color.RGBtoHSB(src.getRed(), src.getGreen(), src.getBlue(), null);
                // apply offsets
                tmp[0] = clamp(tmp[0] + hOffset);
                tmp[1] = clamp(tmp[1] + sOffset);
                tmp[2] = clamp(tmp[2] + bOffset);
                int alpha = clamp(src.getAlpha() + aOffset);
                argbValue = (Color.HSBtoRGB(tmp[0], tmp[1], tmp[2]) & 0xFFFFFF) | (alpha << 24);
            } else {
                float[] tmp = new float[3];
                tmp[0] = clamp(hOffset);
                tmp[1] = clamp(sOffset);
                tmp[2] = clamp(bOffset);
                int alpha = clamp(aOffset);
                argbValue = (Color.HSBtoRGB(tmp[0], tmp[1], tmp[2]) & 0xFFFFFF) | (alpha << 24);
            }
        }

        /**
         * Returns the RGB value representing the color in the default sRGB {@link java.awt.image.ColorModel}. (Bits 24-31
         * are alpha, 16-23 are red, 8-15 are green, 0-7 are blue).
         *
         * @return the RGB value of the color in the default sRGB <code>ColorModel</code>.
         * @see java.awt.image.ColorModel#getRGBdefault
         * @see #getRed
         * @see #getGreen
         * @see #getBlue
         * @since JDK1.0
         */
        @Override public int getRGB() {
            return argbValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DerivedColor)) return false;
            DerivedColor that = (DerivedColor) o;
            if (aOffset != that.aOffset) return false;
            if (Float.compare(that.bOffset, bOffset) != 0) return false;
            if (Float.compare(that.hOffset, hOffset) != 0) return false;
            if (Float.compare(that.sOffset, sOffset) != 0) return false;
            if (!baseColor.equals(that.baseColor)) return false;
            return true;
        }

        @Override
        public int hashCode() {
            int result = baseColor.hashCode();//uiDefaultParentName.hashCode();
            result = 31 * result + hOffset != +0.0f ?
                    Float.floatToIntBits(hOffset) : 0;
            result = 31 * result + sOffset != +0.0f ?
                    Float.floatToIntBits(sOffset) : 0;
            result = 31 * result + bOffset != +0.0f ?
                    Float.floatToIntBits(bOffset) : 0;
            result = 31 * result + aOffset;
            return result;
        }

        private float clamp(float value) {
            if (value < 0) {
                value = 0;
            } else if (value > 1) {
                value = 1;
            }
            return value;
        }

        private int clamp(int value) {
            if (value < 0) {
                value = 0;
            } else if (value > 255) {
                value = 255;
            }
            return value;
        }

        /**
         * Returns a string representation of this <code>Color</code>. This method
         * is intended to be used only for debugging purposes. The content and
         * format of the returned string might vary between implementations. The
         * returned string might be empty but cannot be <code>null</code>.
         *
         * @return a String representation of this <code>Color</code>.
         */
        @Override
        public String toString() {
            Color src = baseColor;//UIManager.getColor(uiDefaultParentName);
            String s = "DerivedColor(color=" + getRed() + "," + getGreen() + "," + getBlue() +
                    " parent=" + baseColor/*uiDefaultParentName*/ +
                    " offsets=" + getHueOffset() + "," + getSaturationOffset() + ","
                    + getBrightnessOffset() + "," + getAlphaOffset();
            return src == null ? s : s + " pColor=" + src.getRed() + "," + src.getGreen() + "," + src.getBlue();
        }
    }

}