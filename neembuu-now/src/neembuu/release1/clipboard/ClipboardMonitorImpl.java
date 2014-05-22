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

package neembuu.release1.clipboard;

import neembuu.release1.api.clipboardmonitor.ClipboardListener;
import neembuu.release1.api.clipboardmonitor.ClipboardMonitor;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import neembuu.util.Throwables;

/**
 *
 * @author Shashank Tulsyan
 */
public class ClipboardMonitorImpl implements ClipboardMonitor {
    private final LinkedList<ClipboardListener> listeners = new LinkedList<>();
    
    public final AtomicBoolean alive = new AtomicBoolean(false);
    private volatile Thread t = null;
    
    @Override
    public void startService(){
        t = Throwables.start(new Runnable(){
            @Override public void run() { runImpl(); }
        },ClipboardMonitorImpl.class.getName(),true);
    }
    
    @Override
    public void addListener(ClipboardListener cl){
        listeners.add(cl);
    }
    
    @Override
    public void removeListener(ClipboardListener cl){
        listeners.remove(cl);
    }
    
    private void runImpl(){
        if(!alive.compareAndSet(false, true)){
            return;
        }
        
        while(alive.get()){
            try{workImpl();}catch(Exception a){
                a.printStackTrace();
                trySleep(2000);
            }
            trySleep(200);
        }
    }

    private void trySleep(long dur){
        try{Thread.sleep(dur);}catch(InterruptedException a){/*ignore*/}
    }
    
    String old= null;
    
    private void workImpl()throws Exception{
        if(listeners.isEmpty())return;
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t;
        try{
            t = c.getContents(null);
        }catch(Exception a){
            return;
        }
        if(isIgnorable(t)) return;
        
        String para;
        try{para = (String)t.getTransferData(DataFlavor.stringFlavor);}
        catch(UnsupportedFlavorException ufe){return; }
        if(para.equalsIgnoreCase(old))return;
        old = para;
        List<String> links = Util.pullLinks(para);
        
        for (ClipboardListener cl : listeners) {
            cl.receive(links);
        }
    }
    
    private boolean isIgnorable(Transferable t){
        DataFlavor[] dfs = t.getTransferDataFlavors();
        for (DataFlavor dataFlavor : dfs) {
            if(dataFlavor.equals(DataFlavor.imageFlavor)) return true;
            /*if(dataFlavor.equals(DataFlavor.javaFileListFlavor)) return true;
            if(dataFlavor.equals(DataFlavor.javaSerializedObjectMimeType)) return true;*/
        }
        return false;
    }
    
    @Override
    public void stopService(){
        alive.set(false);
        if(t!=null){
            for (int i = 0; i < 3; i++) {
                if(t.isAlive()){
                    t.interrupt();
                    trySleep(300);
                }else { break ; }
            }
            
        }
    }

    
   
}
