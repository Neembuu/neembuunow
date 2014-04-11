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

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import neembuu.release1.api.clipboardmonitor.ClipboardListener;
import neembuu.release1.api.clipboardmonitor.ClipboardMonitor;
import neembuu.release1.api.ui.AddLinkUI;

/**
 *
 * @author Shashank Tulsyan
 */
public class AddLinksFromClipboardImpl {
    private final AddLinkUI addLinkUI;
    private final ClipboardMonitor cmi;

    
    public AddLinksFromClipboardImpl(AddLinkUI addLinkUI, ClipboardMonitor cmi) {
        this.addLinkUI = addLinkUI;
        this.cmi = cmi;
        
        cmi.addListener(new ClipboardListener() {
            @Override public void receive(List<String> urls) {
                receiveImpl(urls);
            }
        });
    }

    private void receiveImpl(List<String> urls) {
        addLinkUI.getLock().lockForAWhile(1000);
        try{
            work(urls);
        }catch(Exception a){
            a.printStackTrace();
        }
        addLinkUI.getLock().lock(false);
    }
    
    private void work(List<String> urls)throws Exception{
        String o = addLinkUI.getLinksText();
        boolean empty = false;
        if(o==null || o.trim().length() == 0){
            empty = true;
            o="";
        }
        String[]l = o.split("\n");
        List<String> oldLinks = Arrays.asList(l);
        
        Iterator<String> it = urls.iterator();
        WHILE_LOOP:
        while(it.hasNext()){
            String newUrl = it.next();
            FOR_LOOP:
            for (String oldLink : oldLinks) {
                if(oldLink.equals(newUrl)){
                    it.remove();
                    continue WHILE_LOOP;
                }
            }
        }
        
        
        if(urls.isEmpty())return;
        
        int i =0;
        for (String link : urls) {
            if(i==0 && empty){
                o = link;
            } else { 
                o = o + "\n" + link;
            }
            i++;
        }
        
        addLinkUI.setLinksText(o);
    }
        
}
