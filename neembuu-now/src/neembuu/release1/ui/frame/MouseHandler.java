/*
 * Copyright (C) 2014 Shashank Tulsyan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.release1.ui.frame;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Shashank Tulsyan
 */
public class MouseHandler implements  MouseListener,MouseMotionListener  {

    private final ComponentInterface component;
    
    private Point p_initial = null;
    private Point p_previous_absolute = null;
    
    private MouseLocation mouseLocation = MouseLocation.C;

    public MouseHandler(ComponentInterface component) {
        this.component = component;
    }
    
    @Override
    public void mouseDragged(MouseEvent mouseevent) {
        if(mouseevent.isConsumed())return;
        final Point p_final = mouseevent.getLocationOnScreen();    //global location of mouse pointer
        int dw = p_final.x - p_previous_absolute.x;
        int dh = p_final.y - p_previous_absolute.y;
        
        Rectangle wp = component.getBounds();
        
        switch (mouseLocation) {
            case NW:
                carefulSetBounds(wp.x+dw, wp.y+dh, wp.width-dw, wp.height-dh,wp);break;
            case W:
                carefulSetBounds(wp.x+dw, wp.y, wp.width-dw, wp.height,wp);break;
            case SW:
                carefulSetBounds(wp.x+dw, wp.y, wp.width-dw, wp.height+dh,wp);break;
            case S:
                carefulSetBounds(wp.x, wp.y, wp.width, wp.height+dh,wp);break;
            case SE:
                carefulSetBounds(wp.x, wp.y, wp.width+dw, wp.height+dh,wp);break;
            case E:
                carefulSetBounds(wp.x, wp.y, wp.width+dw, wp.height,wp);break;
            case NE:
                carefulSetBounds(wp.x, wp.y + dh, wp.width+dw, wp.height-dh,wp);break;
            case N:
                carefulSetBounds(wp.x, wp.y + dh, wp.width, wp.height-dh,wp);break;
            case C:
                component.setLocation(-p_initial.x + p_final.x, -p_initial.y + p_final.y);
                break;    
            default:
                throw new AssertionError();
        }
        p_previous_absolute = p_final;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println(e);
        int mx = e.getX(), my=e.getY();
        int h = component.getHeight(), w=component.getWidth();
        System.out.println("w="+w+" h="+h);
        
        final int xa=8,yb=8;
        int a = 8; int b  = 8;
        
        if(component.resizable()){
            if(mx>xa && mx<w-xa && my > yb && my < h-yb ){
                if(mouseLocation!=MouseLocation.C){
                    mouseLocation=MouseLocation.C; 
                    component.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }if(atCornerBorder(mx, my, a, b, MouseLocation.NW) ){
                if(mouseLocation!=MouseLocation.NW){mouseLocation=MouseLocation.NW; component.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));}
            }if(mx<xa && my > b && my < h-b){
                if(mouseLocation!=MouseLocation.W){mouseLocation=MouseLocation.W; component.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));}
            }if(atCornerBorder(mx, my, a, h-b, MouseLocation.SW) ){
                if(mouseLocation!=MouseLocation.SW){mouseLocation=MouseLocation.SW; component.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));}
            }if(mx>a && mx<w-a && my >= h-yb){
                if(mouseLocation!=MouseLocation.S){mouseLocation=MouseLocation.S; component.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));}
            }if(atCornerBorder(mx, my, w-a, h-b, MouseLocation.SE)){
                if(mouseLocation!=MouseLocation.SE){mouseLocation=MouseLocation.SE; component.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));}
            }if(mx>=w-xa && my > b && my < h-b){
                if(mouseLocation!=MouseLocation.E){mouseLocation=MouseLocation.E; component.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));}
            }if(atCornerBorder(mx, my, w-a, b, MouseLocation.NE)){
                if(mouseLocation!=MouseLocation.NE){mouseLocation=MouseLocation.NE; component.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));}
            }if(mx>a && mx<w-a && my < yb ){
                if(mouseLocation!=MouseLocation.N){mouseLocation=MouseLocation.N; component.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));}
            }
        }
        //System.out.println("("+mx+","+my+")"+" "+h+" "+w+" " +mouseLocation);
    }
    
    private void carefulSetBounds(int x, int y, int w, int h, Rectangle previousDimension){
        if(!component.resizable())return;
        if(w<component.minimumWidth()){w=previousDimension.width;x=previousDimension.x;}
        if(h<component.minimumHeight()){h=previousDimension.height;y=previousDimension.y;}
        
        component.setBounds(x, y, w, h);
    }
    
    private boolean atCornerBorder(
            int x, int y, int x_limit, int y_limit, MouseLocation checkOfCorner
            ){        
        
        boolean atCorner = false;
        int a = 10, b = 10;
        
        switch (checkOfCorner) {
            case NW:
                atCorner = x<a && y <b; break;
            case SW:
                atCorner = x<a && y >=component.getHeight()-b; break;
            case SE:
                atCorner = x>=component.getWidth()-a && y >=component.getHeight()-b; break;
            case NE:
                atCorner = x>=component.getWidth()-a && y <b; break;
            default:
                throw new AssertionError();
        }
        
        return atCorner;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //component.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.isConsumed())return;
        p_initial = e.getPoint();
        p_previous_absolute = e.getLocationOnScreen();
        
        /*component.setVisible(false);
        component.setVisible(true);*/
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        /*if(mouseEvent.isConsumed())return;
        final Dimension screendim = Toolkit.getDefaultToolkit ().getScreenSize();
        final int x= component.getLocation_x(),y=component.getLocation_y();
        final int ht = component.getHeight(), wd=component.getWidth();
        final int swd = screendim.width, sht = screendim.height;*/
        
        /*component.setVisible(false);
        component.setVisible(true);*/
    }

    enum MouseLocation {
        N,S, // |
        W,E, // -
        NW,SE, // \
        NE,SW, // /
        C
    }
    
    public MouseHandler normalize(final int x_offset,final int y_offset){
        return new MouseHandler(component){

            @Override public void mouseClicked(MouseEvent e) {
                e.translatePoint(x_offset, y_offset);
                MouseHandler.this.mouseClicked(e); 
            }

            @Override public void mouseDragged(MouseEvent e) {
                e.translatePoint(x_offset, y_offset);
                MouseHandler.this.mouseDragged(e); 
            }

            @Override public void mouseEntered(MouseEvent e) {
                e.translatePoint(x_offset, y_offset);
                MouseHandler.this.mouseEntered(e); 
            }

            @Override public void mouseExited(MouseEvent e) {
                e.translatePoint(x_offset, y_offset);
                MouseHandler.this.mouseExited(e); 
            }

            @Override public void mouseMoved(MouseEvent e) {
                e.translatePoint(x_offset, y_offset);
                MouseHandler.this.mouseMoved(e); 
            }

            @Override public void mousePressed(MouseEvent e) {
                e.translatePoint(x_offset, y_offset);
                MouseHandler.this.mousePressed(e); 
            }

            @Override public void mouseReleased(MouseEvent e) {
                e.translatePoint(x_offset, y_offset);
                MouseHandler.this.mouseReleased(e); 
            }
            
        };
    }
}
