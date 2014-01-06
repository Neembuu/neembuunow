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

import neembuu.rangearray.ModificationType;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import javax.swing.ToolTipManager;
import neembuu.rangearray.Range;
import neembuu.rangearray.RangeArray;
import neembuu.rangearray.RangeArrayListener;
import neembuu.rangearray.RangeUtils;
import neembuu.rangearray.UIRangeArrayAccess;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.rangearray.vectorimpl.RangeArrayImpl;
import neembuu.util.logging.LoggerUtil;



/**
 *
 * @author Shashank Tulsyan
 */
public class RangeArrayComponent
        extends
            JProgressBar
        implements
            MouseMotionListener,
            MouseListener,
            RangeArrayListener{


    private final UIRangeArrayAccess array; // why final ?
    private UnsyncRangeArrayCopy recentArrayCopy = null; // non-volatile since accessed only by AWTDispatchThread
    //private final RangeArray.UnsynchronizedAccess unSynchronizedArrayAccess;
    private volatile long instantaneousMousePosition = -1; //volatile because can be update
    private volatile long instantaneousSelectedMousePosition = -1; //volatile because can be update
    private int darknessAlpha = 100;
    private RangeArrayElementColorProvider
            arrayElementColorProvider;
    private Range selectedRegion = null;
    private RangeArray activeRegion = null;
    protected static final Color STANDARD_FILLER_COLOR =
            new Color(191,98,4,255);
    private final LinkedList<RangeSelectedListener> listeners
            = new LinkedList<RangeSelectedListener>();
    private static final Logger LOGGER = LoggerUtil.getLogger();
    private final RangeArrayElementToolTipTextProvider toolTipTextProvider;
    private Color unprogressedBaseColor;
    
    private volatile boolean updateQuickly;
    
    public RangeArrayComponent(
            UIRangeArrayAccess array,
            RangeArrayElementColorProvider arrayElementColorProvider) {
       this(array,arrayElementColorProvider,true);
    }

    public RangeArrayComponent(
            final UIRangeArrayAccess array,
            final RangeArrayElementColorProvider arrayElementColorProvider,
            boolean updateQuickly){
       this(array,arrayElementColorProvider,updateQuickly,new RangeArrayElementToolTipTextProvider.Default());
    }
    
    /**
     * For dynamic plotting/painting of the RangeArray
     * the RangeArray should be an instance of
     * {@link RangeArray} or {@link RangeArrayComponent#repaintAround(neembuu.common.RangeArrayElement)  } invoked
     * as changes are made ( repaintAround is more efficient/faster than a simple repaint )
     * @param array the RangeArray that has to be plotted/painted
     */
    public RangeArrayComponent(
            final UIRangeArrayAccess array,
            final RangeArrayElementColorProvider arrayElementColorProvider,
            boolean updateQuickly,
            RangeArrayElementToolTipTextProvider toolTipTextProvider) {
        this.arrayElementColorProvider = arrayElementColorProvider;
        this.toolTipTextProvider = toolTipTextProvider;
        if(updateQuickly)array.addRangeArrayListener(this);
        this.updateQuickly = updateQuickly;
        this.array = array;
        recentArrayCopy=array.tryToGetUnsynchronizedCopy();
        //unSynchronizedArrayAccess = array.getUnSynchronizedArrayAccess();
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        if(toolTipTextProvider!=null)
            ToolTipManager.sharedInstance().registerComponent(this);
    }

    public RangeArrayComponent(UIRangeArrayAccess rangeArray) {
        this(rangeArray,false);
    }

    public RangeArrayComponent(UIRangeArrayAccess rangeArray, boolean updateQuickly) {

        this(rangeArray,new RangeArrayElementColorProvider() {

            @Override
            public Color getColor(Color defaultColor, Range element, SelectionState selectionState) {
                //return new Color((int)element.starting());
                //return new Color((int)Math.random()*255,(int)Math.random()*255,(int)Math.random()*255);
                switch(selectionState){
                    case LIST:return lightenColor(STANDARD_FILLER_COLOR,0.9f);
                    case MOUSE_OVER:return lightenColor(STANDARD_FILLER_COLOR,0.8f);
                    case NONE:return STANDARD_FILLER_COLOR;
                    case SELECTED:return lightenColor(STANDARD_FILLER_COLOR,0.7f);
                }
                return STANDARD_FILLER_COLOR;
            }

        },updateQuickly);
    }

    public final RangeArrayElementColorProvider getArrayElementColorProvider() {
        return arrayElementColorProvider;
    }

    public synchronized void setArrayElementColorProvider(RangeArrayElementColorProvider arrayElementColorProvider) {
        this.arrayElementColorProvider = arrayElementColorProvider;
    }

    protected final UIRangeArrayAccess getRangeArray(){
        return array;
    }

    private Color color(int rgb,float alphaPercent){
        int r = rgb/(256*256);
        rgb %= 256*256;
        int g = rgb/256;
        rgb %= 256;
        int b = rgb;
        return new Color(r,g,b,(int)(256*(1-alphaPercent)));
    }

    private BufferedImage unprogressedstrip = null;
    private final Map<Color,BufferedImage> cachedStrips = new HashMap<Color,BufferedImage>();

    private BufferedImage getGradientImage(Color baseColor, int height, Color backgroundColor){
        BufferedImage gradientImage = cachedStrips.get(baseColor);
        if(gradientImage==null || gradientImage.getHeight()!=height ){
            gradientImage = NimbusProgressGradient.getProgressedStrip(
                    backgroundColor,
                    baseColor,
                    height);
            cachedStrips.put(baseColor, gradientImage);
        }
        
        return gradientImage;
    }
    
    
    @Override
    protected void paintComponent(Graphics componentGraphics) {
        //long startTime = System.nanoTime();
        Graphics2D g2 = (Graphics2D) componentGraphics;
        Rectangle originalPaintingBounds = g2.getClipBounds();

        Color baseColor;// = new Color(115, 164, 129, 255);
        baseColor = NimbusProgressGradient.nimbusOrange;
        Color unprogressedBaseColor_use = NimbusProgressGradient.nimbusBlueGrey;
        try {
            unprogressedBaseColor_use = getUnprogressedBaseColor();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        
        if (unprogressedstrip == null ||
            unprogressedstrip.getHeight() != getHeight()) {
            unprogressedstrip = NimbusProgressGradient.getUnprogressedStrip(
                    getBackground(),unprogressedBaseColor_use ,getHeight()); //update and keep copy of strip of correct size
                        // earlier strip will be gced
        }

        g2.drawImage(unprogressedstrip, 0, 0, getWidth(), getHeight(), null);
        //super.setValue(0);
        //super.paintComponent(g2.create());

        if(array == null)return;
        if(array.isEmpty())return;
        
        
        
        //painting might be slow according to the complexity of the gradient and the detail
        //that we wish to apply. We should not lock the collection throughtout this.
        //Also sometimes during new connection creation a lock is held on the range array 
        // for a long time (bug). Bugs such as this, should at no costs make the gui non-responsive.
        recentArrayCopy = array.tryToGetUnsynchronizedCopy();

        
        boolean customDarkRegionExists = activeRegion!=null ;
        
        BufferedImage currentprogressedstrip = null;

        RangeArrayElementColorProvider.SelectionState selectionState;
        
        int numberOfRegionsPainted = 0;
        
        for(int arrayIndex = 0;arrayIndex<recentArrayCopy.size();arrayIndex++){
            Range currentElement = recentArrayCopy.get(arrayIndex);
            Strips strips = new Strips(currentElement, getSize().getHeight(), getSize().getWidth(),getFileSize());
            //optimize painting by skipping regions
            if(strips.x2 < originalPaintingBounds.x)continue;
            if(strips.x1 > originalPaintingBounds.x + originalPaintingBounds.width )break;//all required regions added
            
            selectionState = RangeArrayElementColorProvider.SelectionState.NONE;
            
            if(currentElement.starting()<=instantaneousMousePosition
                        && instantaneousMousePosition<=currentElement.ending()){
                selectionState = RangeArrayElementColorProvider.SelectionState.MOUSE_OVER;
            }
            if(currentElement.starting()<=instantaneousSelectedMousePosition
                            && instantaneousSelectedMousePosition<=currentElement.ending()){
                selectionState = RangeArrayElementColorProvider.SelectionState.SELECTED;
            }

            if(customDarkRegionExists)
                if(activeRegion.contains(currentElement))
                    selectionState = RangeArrayElementColorProvider.SelectionState.LIST;
            
            currentprogressedstrip = getGradientImage(
                    arrayElementColorProvider.getColor(STANDARD_FILLER_COLOR, currentElement, selectionState)
                    , getHeight(), getBackground());
            
            paintStrip(g2, strips, currentprogressedstrip,baseColor, originalPaintingBounds.width);
            
            numberOfRegionsPainted++;
        }

        //long endTime = System.nanoTime();
        //System.out.println("time="+(endTime-startTime)+"  time per region="+((endTime-startTime)/numberOfRegionsPainted)+" regionsrepainted="+numberOfRegionsPainted+ " totalregions="+array.size());
    }
    
    private long getFileSize(){
        long fileSize;
        if(array.getFileSize()==RangeArrayImpl.MAX_VALUE_SUPPORTED){
            
            fileSize=  recentArrayCopy.get(recentArrayCopy.size()-1).ending();
                    //array.get(array.size()-1).ending(); locking not safe
        }
        else
            fileSize=array.getFileSize();
        return fileSize;
    }
    
    private static final class Strips{
        final int x1,x2;//(scaling ensures that this in an integer )
        final double y1,y2;

        Strips(Range currentElement,double height,double width,long fileSize){
            //set rescale constant
            double k;
            k =
               height*width
                /
               (double)fileSize;
            double txy;
            
            txy = currentElement.starting()*k;
            x1 = (int) Math.ceil(txy / height);
            y1 = txy % height;
            txy = currentElement.ending()*k;
            x2 = (int) Math.ceil(txy / height);
            y2 = txy % height;
        }
        
        Strips(int x1, double y1, int x2, double y2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }
    }
    
    private void paintStrip(final Graphics2D g2,final Strips strips
            ,final BufferedImage currentprogressedstrip,final Color baseColor,
            final int originalPaintingBounds_width){
        paintStrip(g2, strips.x1, strips.y1, strips.x2, strips.y2, currentprogressedstrip, baseColor, originalPaintingBounds_width);
    }
    
    private void paintStrip(final Graphics2D g2,final int x1,final double y1,final int x2,final double y2
            ,final BufferedImage currentprogressedstrip,final Color baseColor,final int originalPaintingBounds_width){

        // only a single strip updated in a possibly thick region
        if(originalPaintingBounds_width==1){
            // update of such kind is done only at the end of the strip, not 
            // at it's begining
            g2.setClip(new Rectangle2D.Double(
                x2,
                x1==x2?y1:0,//chose between a intrinc single strip and last strip of a think region
                1,
                (int)y2
            ));
            g2.drawImage(currentprogressedstrip,x2,0,1, getHeight(), null);
            return;
        }
        
        // intrinsic single strip, single by shape
        // mark it and then continue;
        if(x1==x2){
            g2.setClip(new Rectangle2D.Double(
                x1,
                y1,
                1,
                (y2-y1)
            ));

            g2.drawImage(currentprogressedstrip, x1, 0, 1, getHeight(), null);
            g2.setColor(baseColor);
            return;
        }

        //first strip

        g2.setClip(new Rectangle2D.Double(
            x1,
            y1,
            1,
            (getHeight()-y1)
        ));
        g2.drawImage(currentprogressedstrip,x1,0,1, getHeight(), null);

        if(x2>=x1+2){
            //middle rectangle
            g2.setClip(
                    x1+1,
                    0,
                    x2-x1-1,
                    getHeight()
            );
            g2.drawImage(currentprogressedstrip,
                    x1+1,
                    0,
                    x2-x1-1,
                    getHeight(),null
            );
        }

        //last strip
        g2.setClip(new Rectangle2D.Double(
                x2,
                0,
                1,
                (int)y2
        ));
        g2.drawImage(currentprogressedstrip,x2,0,1, getHeight(), null);
    }
    
    public static Color lightenColor(Color color, float f){
        return lightenColor(color, f, f, f);
    }

    private static Color lightenColor(Color color, float r,float g, float b){
            return new Color(
                lightenBy(color.getRed(),r),
                lightenBy(color.getGreen(),g),
                lightenBy(color.getBlue(),b),
                color.getAlpha()
            );
    }

    private static int lightenBy(int v, float factor){
        int ret = (int)(v*factor);
        if(ret>=255)return 255;
        return ret;
    }

    @Override
    public final void setValue(int n) {
        throw new UnsupportedOperationException(
                "Please modify the RangeArray instead");
    }

    @Override
    public String getToolTipText(MouseEvent event) {
        if(event==null)return null;

        long rangeArrayAbsolutePosition = getAbsolutePositionFor(event);
        Range rangeArrayElement = 
                array.getUnsynchronized(rangeArrayAbsolutePosition);
                //array.get(rangeArrayAbsolutePosition); //locking not safe
        if(toolTipTextProvider!=null){
            RangeArrayElementColorProvider.SelectionState selectionState = 
                    RangeArrayElementColorProvider.SelectionState.NONE;
            if(rangeArrayElement==null){
                selectionState = RangeArrayElementColorProvider.SelectionState.NONE;
            } else if(rangeArrayElement.starting()<=instantaneousSelectedMousePosition
                            && instantaneousSelectedMousePosition<=rangeArrayElement.ending()){
                selectionState = RangeArrayElementColorProvider.SelectionState.SELECTED;
            }
            
            return toolTipTextProvider.getToolTipText(rangeArrayElement, rangeArrayAbsolutePosition, getFileSize(), selectionState);
        }
        
        return null;
    }

    protected long getAbsolutePositionFor(MouseEvent event){
        double txy = event.getX()*getHeight()+event.getY();
        long rangeArrayAbsolutePosition = (long) Math.ceil(
                txy * getFileSize()
                /
                (getWidth()*getHeight())
        );return rangeArrayAbsolutePosition;
    }

    /**
     * Use this to set the darkness of the darkened region. <p>
     * A region is darkened (or highlighted) when the mouse is over it or
     * such a request is made to this component.
     * @see #getDarknessAlpha()
     * @param darknessAlpha alpha of active region
     */
    public void setDarknessAlpha(int darknessAlpha) {
        this.darknessAlpha = darknessAlpha;
    }

    /**
     * Get the darkness of the darkened region.
     * The region is highlighted instead if the previous color was itself dark,
     * such that further darkening will not make any significant observable difference.
     * @see #setDarknessAlpha(int)
     * @see #setActiveRegion(activeRegion)
     * @return alpha of active region
     */
    public int getDarknessAlpha() {
        return darknessAlpha;
    }

    //++++++++++++ Mouse Motion Listener +++++++++

    @Override
    public void mouseDragged(MouseEvent e) {
        handleMouseEvent(e);
        return;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        handleMouseEvent(e);
        return;
    }

    /**
     * This can be used by a JTable which tabulates the RangeArrayElements.
     * The RangeArrayElement which is selected in the JTable, can be
     * darkened (or highlighted) by setting the active region.
     * Only one continuous region can be active at a time.
     * Active only implies that it will be painted differently,
     * such that it appears selected or active. The effect produced
     * will be the same as one observed when the mouse is over that region.
     * @see #getActiveRegion()
     * @param activeRegion The range array specifying the region which has to be darkened (or highlighted)
     * to show as if it is active or focus of user's mouse input or interest
     */
    public void setActiveRegion(RangeArray activeRegion) {
        if(activeRegion.getFileSize()>getFileSize())
            throw new IllegalArgumentException("Dark region extends beyond filesize of this range array.");
        this.activeRegion = activeRegion;
    }

    /**
     * @see #setActiveRegion(neembuu.common.RangeArray)
     * @return The range array specifying the region which has to be darkened (or highlighted)
     */
    public RangeArray getActiveRegion() {
        return activeRegion;
    }

    public void removeAllCustomDarkRegions(){
        activeRegion = null;
    }

    protected void handleMouseEvent(MouseEvent e){
        final long newPosition;
        final Range newPositionElement;
        if(e.getID()==MouseEvent.MOUSE_EXITED){
            newPosition=-1;
            newPositionElement = null;
        } else {
            Range newPositionElement_; long newPosition_;
            try{
                newPositionElement_ = 
                        array.getUnsynchronized((newPosition_=getAbsolutePositionFor(e)));
                        //array.get((newPosition=getAbsolutePositionFor(e)));
            }catch(NullPointerException npe){
                // array updating too fast .... better not provide access to it.
                newPositionElement_ = null; newPosition_ = -1;
            }
            newPositionElement = newPositionElement_;
            newPosition = newPosition_;
        }


        if(e.getID()==MouseEvent.MOUSE_CLICKED) {
            rangeSelect(newPositionElement, newPosition);
        }

        repaint();
        /*if(instantaneousMousePositionElement!=null){
        //if(!instantaneousMousePositionElement.equalsIgnoreProperty(newPositionElement)){
            Rectangle newRepaintRegion = getBoundsForRangeArrayElement(newPositionElement);
            //Rectangle oldRepaintRegion = getBoundsForRangeArrayElement(instantaneousMousePositionElement);
            //if old region was darkened, it must be undarkened as soon as it is left
            //so we must paint both old and new region
            if(newRepaintRegion!=null)repaint(newRepaintRegion);
            //if(oldRepaintRegion!=null)repaint(oldRepaintRegion);
        }
        if(newPositionElement!=null){
        //if(!instantaneousMousePositionElement.equalsIgnoreProperty(newPositionElement)){
            Rectangle newRepaintRegion = getBoundsForRangeArrayElement(newPositionElement);
            //if old region was darkened, it must be undarkened as soon as it is left
            //so we must paint both old and new region
            if(newRepaintRegion!=null)repaint(newRepaintRegion);
        }*/
        instantaneousMousePosition = newPosition;
    }

    public void selectRange(final Range newPositionElement){
        Range previouslySelectedRegion = selectedRegion;
        rangeSelect(newPositionElement, newPositionElement.starting());
        if(previouslySelectedRegion!=null){
            repaintAround(previouslySelectedRegion.starting(), previouslySelectedRegion.ending());
            repaintAround(newPositionElement.starting(), newPositionElement.ending());
        }
    }
    
    private void rangeSelect(final Range newPositionElement,final long newPosition){
        selectedRegion = newPositionElement;
        if(newPositionElement!=null)
            instantaneousSelectedMousePosition = newPosition;
        else
            instantaneousSelectedMousePosition = -1;
        //if(newPositionElement!=null)
            //new Thread(RangeArrayComponent.class.getName()+":line577"){

                //@Override
                //public void run() {
                    for (RangeSelectedListener listener : listeners) {
                        listener.rangeSelected(newPositionElement);
                    }
              //  }

            //}.start();
    }
    
    //------------ Mouse Motion Listener ----------

    public void repaintAround(long start,long end){
        Rectangle repaintRegion = getBoundsForRange(start,end);
        if(start==end)return;//if(repaintRegion==null)return;
        repaint(repaintRegion);
    }
    
    
    
    public final Range getRangeForBounds(Rectangle bounds){
        long start=0, end=getFileSize();
        // x*height + y      offset
        //--------------- =  --------
        // width * height    fileSize
        double c =
                (double)getFileSize()
                /
                (getSize().getHeight()*getSize().getWidth())
           ;
        
        start = (long)(Math.ceil(
                    c*(bounds.x*getSize().getHeight() + bounds.y)
                ));
        end = (long)(Math.ceil(
                    c*((bounds.x+bounds.width)*getSize().getHeight() + bounds.y)
                ));
        return RangeUtils.wrapAsARange(start, end);
    }

    public final Rectangle getBoundsForRange(long start,long end){
        long fileSize = getFileSize();

        // x*height + y      offset
        //--------------- =  --------
        // width * height    fileSize

        int x1 = (int)(
                    ((double)start/(double)fileSize)*getWidth()
                );
        if(x1>0)x1=x1-1; //not really required
        int x2 = (int)(
                    ((double)end/(double)fileSize)*getWidth()
                );
        x2=x2+1;//round off to larger integer
        return new Rectangle(
                x1,
                0,
                x2-x1+1,//one is added as x1 is offsetted by 1
                getHeight()
        );

    }

    public boolean isUpdateQuickly() {
        return updateQuickly;
    }

    public void setUpdateQuickly(boolean updateQuickly) {
        this.updateQuickly = updateQuickly;
    }

    @Override
    public void rangeArrayModified(
            long modificationResultStart, 
            long modificationResultEnd, 
            Range elementOperated, 
            ModificationType modificationType,
            boolean removed,
            long modCount) {
        if(!updateQuickly){
            return;
        }
        // TODO :
        // ensure buffered repainting as current implementation is
        // really worthlessly cpu consuming
        /*RangeArrayElement newElementCreatedAfterModification =
                array.get(modifyingElement.starting());
        repaintAround(newElementCreatedAfterModification);*/
        if(modificationType == ModificationType.FILE_SIZE_CHANGED){
            // the entire figure gets rescaled so entire surface needs
            // to be redrawn
            repaint();
            return ;
        }
        if(elementOperated==null)return;
        //synchronized(pendingPaintRegions){ pendingPaintRegions.add(new PendingPaintRegion(modificationResultStart, modificationResultEnd, elementOperated, modificationType, modCount));}
        repaintAround(modificationResultStart,modificationResultEnd);
    }
    /*private static final class PendingPaintRegion{
        final long modificationResultStart;
        final long modificationResultEnd;
        final Range elementOperated;
        final ModificationType modificationType;
        final long modCount;

        public PendingPaintRegion(long modificationResultStart, long modificationResultEnd, Range elementOperated, ModificationType modificationType, long modCount) {
            this.modificationResultStart = modificationResultStart;
            this.modificationResultEnd = modificationResultEnd;
            this.elementOperated = elementOperated;
            this.modificationType = modificationType;
            this.modCount = modCount;
        }
    }*/
    
    // <editor-fold defaultstate="collapsed" desc="+++++++Mouse Listener+++++++">
    @Override
    public void mouseClicked(MouseEvent e) {
        handleMouseEvent(e);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        handleMouseEvent(e);
    }

    // mouse listener </editor-fold>

    public void addRangeSelectedListener(RangeSelectedListener listener){
        synchronized(listeners){
            listeners.add(listener);
        }
    }

    public void removeRangeSelectedListener(RangeSelectedListener listener){
        synchronized(listeners){
            listeners.remove(listener);
        }
    }

    public Color getUnprogressedBaseColor() {
        return unprogressedBaseColor;
    }

    public void setUnprogressedBaseColor(Color unprogressedBaseColor) {
        this.unprogressedBaseColor = unprogressedBaseColor;
    }

}