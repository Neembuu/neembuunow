/*
 *  Copyright (C) 2009-2010 Shashank Tulsyan
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
 *
 *
 *
 *  Linking this library statically or
 *  dynamically with other modules is making a combined work based on this library.
 *  Thus, the terms and conditions of the GNU General Public License cover the whole combination.
 *
 *
 *  As a special exception, the copyright holders of this library give you permission to
 *  link this library with independent modules to produce an executable, regardless of
 *  the license terms of these independent modules, and to copy and
 *  distribute the resulting executable under terms of your choice,
 *  provided that you also meet, for each linked independent module,
 *  the terms and conditions of the license of that module.
 *  An independent module is a module which is not derived from or based on this library.
 *  If you modify this library, you may extend this exception to your version of the library,
 *  but you are not obligated to do so. If you do not wish to do so,
 *  delete this exception statement from your version.
 */

/*
 * todo  :
 * implement orient --
 * if(super.getOrientation()==JProgressBar.HORIZONTAL)
 */

package neembuu.swing;

import neembuu.rangearray.ModificationType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import javax.swing.JProgressBar;
import javax.swing.ToolTipManager;
import neembuu.rangearray.Range;
import neembuu.rangearray.RangeArray;
import neembuu.rangearray.RangeArrayListener;
import neembuu.rangearray.RangeUtils;

/**
 *
 * @author Shashank Tulsyan
 */
public class RangeArrayProgressBar
        extends
            JProgressBar
        implements 
            MouseMotionListener,
            MouseListener,
            RangeArrayListener{

    
    private RangeArray array;
    private volatile long instantaneousMousePosition = -1;
    private int darknessAlpha = 100;

    private RangeArray darkRegion = null;

    public RangeArrayProgressBar(RangeArray array) {
       this(array,true);
    }

    /**
     * For dynamic plotting/painting of the RangeArray
     * the RangeArray should be an instance of
     * {@link ListenableRangeArray} or {@link RangeArrayProgressBar#repaintAround(neembuu.common.RangeArrayElement)  } invoked
     * as changes are made ( repaintAround is more efficient/faster than a simple repaint )
     * @param array the RangeArray that has to be plotted/painted
     */
    public RangeArrayProgressBar(final RangeArray array, boolean updateQuickly) {
        super(0,1);
        super.setValue(0);//we have overriden, so we cannot use this.setValue

        //don't want endless repaint() calls from calling setValue() within
        //paintComponent() method
        for(javax.swing.event.ChangeListener list : this.getChangeListeners()){
           removeChangeListener(list);
        }
        array.addRangeArrayListener(this);
        this.array = array;
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        ToolTipManager.sharedInstance().registerComponent(this);
    }

    public RangeArrayProgressBar() {
        this(null);
    }

    //public RangeArrayProgressBar(int/*enum/boolean instead ?*/ orient , final RangeArray array){}

    /*public void setRangeArray(RangeArray array){
       this.array.removeListener(this);//we don't want anymore updates
       if(array instanceof ListenableRangeArray ){
           ((ListenableRangeArray)array).addListener(this);
        }
        this.array = array;
    }*/



    protected final RangeArray getRangeArray(){
        return array;
    }

    @Override
    protected void paintComponent(Graphics componentGraphics) {
        Graphics2D g2 = (Graphics2D) componentGraphics;
        super.setValue(0);
        super.paintComponent(g2);
        if(array == null)return;
        if(array.size()==0)return;

        Area progressedRegionArea = new Area();
        Area currentMousePositionArea = defineClipArea(g2,progressedRegionArea);
        g2.setClip(progressedRegionArea);
        super.setValue(1);
        super.paintComponent(g2);
        if(currentMousePositionArea!=null){
            g2.setColor(new Color(
                    0,0,0,
                    darknessAlpha
            ));
            g2.fill(currentMousePositionArea);
        }
        super.setValue(0);
    }

    private Area defineClipArea(Graphics2D g2,Area progressedRegionArea){
        Dimension size = getSize();//componentGraphics.getClipBounds().getSize();

        Area currentMousePositionArea = null;
        //if(true)return;
        //set rescale constant
        double k;
        long fileSize ;
        if(array.size()==0){
            progressedRegionArea.add(new Area(new Rectangle2D.Double(0,0,getWidth(),getHeight())));
            return null;
        }

        if(array.getFileSize()==RangeArray.MAX_VALUE_SUPPORTED){
            fileSize=array.get(array.size()-1).ending();
        }
        else
            fileSize=array.getFileSize();

        k =
           size.getHeight()*size.getWidth()
            /
           (double)fileSize;

        int x1=0,x2=0; //(scaling ensures that this in an integer )
        double y1=0,y2=0;
        double txy;

        Range currentElement;
        boolean highlightAreaSet = false, setHighlightAreaInThisLoop = false;

        boolean customDarkRegionExists = darkRegion!=null ;

        Color veryFineRegionColor = new Color(
                Color.orange.getRed(),Color.orange.getGreen(),Color.orange.getBlue(),100
        );
        Color defaultColor = g2.getColor();

        for(int arrayIndex = 0;arrayIndex<array.size();arrayIndex++){
            currentElement = array.get(arrayIndex);

            long rangeArrayAbsolutePosition = instantaneousMousePosition;

            setHighlightAreaInThisLoop = false;
            if(!highlightAreaSet){
                if(currentElement.starting()<=rangeArrayAbsolutePosition
                            && rangeArrayAbsolutePosition<=currentElement.ending()){
                    if(currentMousePositionArea==null){
                        currentMousePositionArea = new Area();
                        setHighlightAreaInThisLoop = true;
                    }
                }
            }
            
            if(customDarkRegionExists)
                if(darkRegion.contains(currentElement))
                    setHighlightAreaInThisLoop = true;
            
            txy = currentElement.starting()*k;
            x1 = (int) Math.ceil(txy / getHeight());
            y1 = txy % getHeight();
            txy = currentElement.ending()*k;
            x2 = (int) Math.ceil(txy / getHeight());
            y2 = txy % getHeight();


            //optimize painting by skipping regions
            if(x2 < g2.getClipBounds().x)continue;
            if(x1 > g2.getClipBounds().x + g2.getClipBounds().width )break;//all required regions added


            //if(arrayIndex < 20000 || arrayIndex > 22000)continue;
            

            
            // single strip
            // mark it and then continue;
            if(x1==x2){
                if(array.size()>1000)
                if( ((double)RangeUtils.getSize(currentElement)/(double)fileSize) < 0.00001 ){
                    g2.setColor(veryFineRegionColor);
                    g2.fill(new Rectangle2D.Double(
                        x1,
                        y1,
                        1,
                        (y2-y1)
                    ));
                    g2.setColor(defaultColor);
                    continue;
                }//0.001 %
                //progressedRegionArea.add(new Area(new Line2D.Double(x1, y1, x1, y2)));
                progressedRegionArea.add(new Area(new Rectangle2D.Double(
                        x1,
                        y1,
                        1,
                        y2-y1
                )));
                if(setHighlightAreaInThisLoop){
                    
                    currentMousePositionArea.add(new Area(new Rectangle2D.Double(
                        x1,
                        y1,
                        1,
                        (y2-y1)
                    )));
                }
                highlightAreaSet = true;
                continue;
            }

            //first strip
            progressedRegionArea.add(new Area(new Rectangle2D.Double(
                    x1,
                    y1,
                    1,
                    getHeight()-y1
            )));
            if(setHighlightAreaInThisLoop){
                
                currentMousePositionArea.add(new Area(new Rectangle2D.Double(
                        x1,
                        y1,
                        1,
                        (getHeight()-y1)
                )));
            }


            if(x2>=x1+2){
                //middle rectangle
                progressedRegionArea.add(new Area(new Rectangle2D.Double(
                        x1+1,
                        0,
                        x2-x1-1,
                        getHeight()
                )));
                if(setHighlightAreaInThisLoop ){
                    
                    currentMousePositionArea.add(new Area(new Rectangle2D.Double(
                            x1+1,
                            0,
                            x2-x1-1,
                            getHeight()
                    )));
                }
            }

            //last strip
            progressedRegionArea.add(new Area(new Rectangle2D.Double(
                    x2,
                    0,
                    1,
                    y2
            )));
            if(setHighlightAreaInThisLoop){
                
                currentMousePositionArea.add(new Area(new Rectangle2D.Double(
                    x2,
                    0,
                    1,
                    y2
                )));
                highlightAreaSet = true;
                setHighlightAreaInThisLoop = false;
            }
        }
        return currentMousePositionArea;
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
        Range rangeArrayElement = array.get(rangeArrayAbsolutePosition);
        if(rangeArrayElement==null){
            return getStringForEmptyRangeArrayRegion(rangeArrayAbsolutePosition);
        }
        return getRangeArraElementToolTipText(rangeArrayElement,rangeArrayAbsolutePosition);
    }

    protected long getAbsolutePositionFor(MouseEvent event){
        double txy = event.getX()*getHeight()+event.getY();
        long rangeArrayAbsolutePosition = (long) Math.ceil(
                txy * array.getFileSize()
                /
                (getWidth()*getHeight())
        );return rangeArrayAbsolutePosition;
    }

    /**
     * Override this to change the string that is displayed in
     * unprogressed areas
     * @param absolutePosition
     * @param fileSize
     * @return
     */
    protected String getStringForEmptyRangeArrayRegion(long absolutePosition){
        return makeStringForEmptyRangeArrayRegion(absolutePosition, array.getFileSize() );
    }

    protected static final String makeStringForEmptyRangeArrayRegion(long absolutePosition, long fileSize){
        StringBuilder sb = new StringBuilder(100);
        sb.append("<html><body><u>");
        /*int numSpace = getNumberOfDigits(fileSize) - getNumberOfDigits(absolutePosition);
        for (int i = 0; i < numSpace; i++) {
            sb.append("&#32;");
        }*/
        sb.append(NumberFormat.getInstance().format((double)absolutePosition));
        sb.append("</u><p>");
        sb.append(NumberFormat.getInstance().format((double)fileSize));
        sb.append("</body></html>");
        return sb.toString();
    }

    /**
     *
     * @param absolutePosition
     * @param fileSize
     * @return
     */
    public String getRangeArraElementToolTipText(Range rae,long absolutePosition){
        return makeRangeArraElementToolTipText(rae, absolutePosition, array.getFileSize());
    }

    private static final String makeRangeArraElementToolTipText(Range rae,long absolutePosition, long fileSize){
        StringBuilder sb = new StringBuilder(100);
        sb.append("<html><body><u>");
        /*int numSpace = getNumberOfDigits(fileSize) - getNumberOfDigits(absolutePosition);
        for (int i = 0; i < numSpace; i++) {
            sb.append(' ');
        }*/
        sb.append(NumberFormat.getInstance().format((double)absolutePosition));
        sb.append("</u><p>");
        sb.append(NumberFormat.getInstance().format((double)fileSize));
        sb.append("<p>{");
        sb.append(NumberFormat.getInstance().format(rae.starting()));
        sb.append("-to->");
        sb.append(NumberFormat.getInstance().format(rae.ending()));
        sb.append("}<p>{");
        sb.append(NumberFormat.getInstance().format(RangeUtils.getSize(rae)));
        sb.append(" , ");
        sb.append( (float)(RangeUtils.getSize(rae)*100)/fileSize );
        sb.append("% }<p>");
        
        String x = null;
        if(x!=null){
            sb.append(x);
            sb.append("<p>");
        }
        sb.append("</body></html>");
        return sb.toString();
    }

    /*private final static int getNumberOfDigits(long num){
        int ret = 0;
        for(;num>0;ret++)num/=10;
        return ret;
    }*/

    /**
     * Use this to set the darkness of the darkened region. <p>
     * A region is darkened (or highlighted) when the mouse is over it or
     * such a request is made to this component.
     * @see #getDarknessAlpha() 
     * @param darknessAlpha alpha of darkened/highlighted region
     */
    public void setDarknessAlpha(int darknessAlpha) {
        this.darknessAlpha = darknessAlpha;
    }

    /**
     * Get the darkness of the darkened region.
     * The region is highlighted instead if the previous color was itself dark,
     * such that further darkening will not make any significant observable difference.
     * @see #setDarknessAlpha(int)
     * @return alpha of darkened/highlighted region
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
     * darkened (or highlighted) by setting the dark region.
     * @see #getActiveRegion()
     * @param darkRegion The range array specifying the region which has to be darkened (or highlighted)
     */
    public void setActiveRegion(RangeArray darkRegion) {
        if(darkRegion.getFileSize()>array.getFileSize())
            throw new IllegalArgumentException("Dark region extends beyond filesize of this range array.");
        this.darkRegion = darkRegion;
    }

    /**
     * @see #setActiveRegion(neembuu.common.RangeArray)
     * @return The range array specifying the region which has to be darkened (or highlighted)
     */
    public RangeArray getActiveRegion() {
        return darkRegion;
    }

    public void removeAllCustomDarkRegions(){
        darkRegion = null;
    }

    protected void handleMouseEvent(MouseEvent e){
        long newPosition;
        Range newPositionElement;
        Range instantaneousMousePositionElement = array.get(instantaneousMousePosition);
        if(e.getID()==MouseEvent.MOUSE_EXITED){
            newPosition=-1;
            newPositionElement = null;
        }else {
            newPositionElement = array.get((newPosition=getAbsolutePositionFor(e)));
        }

        if(instantaneousMousePositionElement!=newPositionElement){
            Rectangle newRepaintRegion = getBoundsForRangeArrayElement(newPositionElement);
            Rectangle oldRepaintRegion = getBoundsForRangeArrayElement(instantaneousMousePositionElement);
            //if old region was darkened, it must be undarkened as soon as it is left
            //so we must paint both old and new region
            if(newRepaintRegion!=null)repaint(newRepaintRegion);
            if(oldRepaintRegion!=null)repaint(oldRepaintRegion);
        }
        instantaneousMousePosition = newPosition;        
    }

    //------------ Mouse Motion Listener ----------

    public void repaintAround(Range region){
        Rectangle repaintRegion = getBoundsForRangeArrayElement(region);
        if(repaintRegion==null)return;
        repaint(repaintRegion);
    }

    public Rectangle getBoundsForRangeArrayElement(Range element){
        if(element==null)return null;
        long fileSize = array.getFileSize();

        // x*height + y      offset
        //--------------- =  --------
        // width * height    fileSize

        int x1 = (int)(
                    ((double)element.starting()/(double)fileSize)*getWidth()
                );
        if(x1>0)x1=x1-1; //not really requireds
        int x2 = (int)(
                    ((double)element.ending()/(double)fileSize)*getWidth()
                );
        x2=x2+1;//round off to larger integer
        return new Rectangle(
                x1,
                0,
                x2-x1+1,//one is added as x1 is offsetted by 1
                getHeight()
        );

    }

    @Override
    public void rangeArrayModified(
            long modificationResultStart,
            long modificationResultEnd,
            Range elementOperated,
            ModificationType modificationType,
            boolean removed,
            long modCount) {
        rangeArrayModified(elementOperated);
    }
    
    
    public void rangeArrayModified(Range regionModified) {
        /*RangeArrayElement newElementCreatedAfterModification =
                array.get(modifyingElement.starting());
        repaintAround(newElementCreatedAfterModification);*/
        if(regionModified==null)return;
        repaintAround(regionModified);
    }

    //++++++++++++ Mouse Listener +++++++++++++
    @Override
    public void mouseClicked(MouseEvent e) {repaint();}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) { handleMouseEvent(e);}
    //----------- Mouse Listener ----------------
}
