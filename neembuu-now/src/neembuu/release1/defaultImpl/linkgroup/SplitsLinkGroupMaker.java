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

package neembuu.release1.defaultImpl.linkgroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import neembuu.diskmanager.DiskManager;
import neembuu.diskmanager.MakeSession;
import neembuu.diskmanager.Session;
import neembuu.release1.api.linkgroup.LinkGroup;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.linkgroup.TrialLinkGroup;
import neembuu.release1.api.linkgroup.LinkGroupMaker;
import neembuu.release1.api.linkhandler.LinkHandlerProviders;
import neembuu.release1.defaultImpl.linkgroup.SplitsTrialLinkGroup.SplitPart;

/**
 *
 * @author Shashank Tulsyan
 */
public class SplitsLinkGroupMaker implements LinkGroupMaker{

    private static final String Type = "split";
    
    @Override
    public TrialLinkGroup tryMaking(TrialLinkHandler tlh) {
        return tryHandle(tlh);
    }
    
    static SplitsTrialLinkGroup tryHandle(TrialLinkHandler tlh){
        SplitsTrialLinkGroup result = new SplitsTrialLinkGroup();
        if( tlh.containsMultipleLinks() ){
            return null;
        }
        String n = tlh.tempDisplayName();
        try {
            SplitPart sp = new SplitPart();
            // video.001
            //      4321 
            if(n.charAt(n.length()-4)!='.')return null;
            result.name = n.substring(0,n.length() - 4);
            int idx = result.name.lastIndexOf("/");
            if(idx>0 && (idx+1+1) < result.name.length() ){ //there should be atleast 1 character
                // keep only filename into picture and ignore rest
                result.name = result.name.substring(idx+1);
            }
            sp.splitName = n;
            sp.index = Integer.parseInt(n.substring(n.length() - 3));
            result.splitParts.add(sp);
            sp.th = tlh;
        } catch (Exception a) {
            //a.printStackTrace(System.out);
            return null;
        }
        return result;
    }

    @Override public LinkGroup make(TrialLinkGroup tlg, DiskManager dm) throws Exception {
        if(!(tlg instanceof SplitsTrialLinkGroup)) return null;
        
        SplitsTrialLinkGroup slg = ((SplitsTrialLinkGroup)tlg);
        if(!slg.complete()){throw new IllegalStateException("Incomplete splits");}
        MakeSession ms = dm.makeSession(Type);
        
        return make(ms, slg);
    }
    
    private LinkGroup make(MakeSession ms,SplitsTrialLinkGroup slg)throws Exception{
        SeekableByteChannel sbc = ms.metaData();

        // ignore inefficient code below.
        // the point of using channels was not because the data is huge.
        // it is there just to allow the most flexible type of data storage.
        try{
            final byte[]newLine={'\n'};/*ByteBuffer bb = new */
            sbc.write(ByteBuffer.wrap(Integer.toString(slg.getAbsorbedLinks().size()).getBytes()));
            sbc.write(ByteBuffer.wrap(newLine)); // inefficient :P no problem :P
            
            for (TrialLinkHandler tlh : slg.getAbsorbedLinks()) {
                WBC wbc = new WBC(sbc);
                sbc.write(ByteBuffer.wrap(tlh.getReferenceLinkString().getBytes()));
                sbc.write(ByteBuffer.wrap(newLine));
            }
        }catch(Exception a){ ms.cancel(); throw a;}
        
        Session s = Utils.make(ms,false);
        
        BasicLinkGroup blg = new BasicLinkGroup(slg.getAbsorbedLinks(), slg.tempDisplayName(), s);
        return blg;
    }

    @Override
    public LinkGroup restore(Session s) throws Exception {
        if(!s.getType().equalsIgnoreCase(Type)) return null;
        
        SeekableByteChannel sbc = s.openMetaData();
        sbc.position(0);
        
        BufferedReader br = ChannelInputStream.make(sbc);

        int expectedListSize = Integer.parseInt(br.readLine()); /*bbSz.getInt();*/
        sbc.position(sbc.position() + 1);//+1 for newline character
        
        final ArrayList<String> urls = new ArrayList<>();
        String t;
        while( (t=br.readLine())!=null ){
            t=t.trim(); if(t.length()==0) continue;
            urls.add(t);
        }
        
        if(urls.size()!=expectedListSize){
            throw new IllegalStateException("Expected number of links = "+expectedListSize+
                    " found="+urls.size());
        }
        
        if(urls.isEmpty()){
            throw new IllegalStateException("No links in session");
        }
        
        List<TrialLinkHandler> tlhs = new ArrayList<>();
        for (String url : urls) {
            TrialLinkHandler tlh = LinkHandlerProviders.getWhichCanHandleOrDefault(url);
            tlhs.add(tlh);
        }
        
        TrialLinkGroup tlg = null;
        for (TrialLinkHandler trialLinkHandler : tlhs) {
            if(tlg==null){
                tlg = tryHandle(trialLinkHandler);
            }else { 
                tlg.absorb(trialLinkHandler);
            }
        } 
        if(!tlg.complete()){
            throw new IllegalStateException("Some links missing");
        }

        BasicLinkGroup blg = new BasicLinkGroup(tlg.getAbsorbedLinks(), tlg.tempDisplayName(), s);
        return blg;
    }    
    
    
    @Override
    public LinkGroup modify(Session s,TrialLinkGroup tlg, DiskManager dm) throws Exception {
        if(!s.getType().equalsIgnoreCase(Type)) return null;
        if(!(tlg instanceof SplitsTrialLinkGroup)) return null;
        SplitsTrialLinkGroup slg = ((SplitsTrialLinkGroup)tlg);
        
        MakeSession ms = dm.modifySession(s);
        
        return make(ms, slg);
    }
    
     private static final class WBC implements WritableByteChannel {
        private final WritableByteChannel wbc_parent;
        public WBC(WritableByteChannel src) { this.wbc_parent = src; }
        @Override public int write(ByteBuffer src) throws IOException {
            String toString = new String(src.array());
            if( toString.contains("\n") || toString.contains("\r")){
                throw new IllegalStateException("Cannot handle the trial link");
            }return wbc_parent.write(src);
        }
        @Override public boolean isOpen() { return true;}
        @Override public void close() throws IOException {}
    };
}
