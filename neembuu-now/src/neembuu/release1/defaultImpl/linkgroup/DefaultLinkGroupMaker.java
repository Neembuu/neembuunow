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

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import neembuu.diskmanager.DiskManager;
import neembuu.diskmanager.MakeSession;
import neembuu.diskmanager.Session;
import neembuu.release1.api.linkgroup.LinkGroup;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.linkgroup.TrialLinkGroup;
import neembuu.release1.api.linkgroup.LinkGroupMaker;
import neembuu.release1.api.linkhandler.LinkHandlerProviders;

/**
 *
 * @author Shashank Tulsyan
 */
public class DefaultLinkGroupMaker implements LinkGroupMaker {
    
    private static final String Type = "default";
    
    @Override
    public TrialLinkGroup tryMaking(TrialLinkHandler tlh){
        DefaultLinkGroup result = new DefaultLinkGroup(tlh);
        return result;
    }

    @Override
    public LinkGroup make(TrialLinkGroup tlg, DiskManager dm) throws Exception {
        if(!(tlg instanceof DefaultLinkGroup)) return null;
        
        DefaultLinkGroup slg = ((DefaultLinkGroup)tlg);
        if(slg.getAbsorbedLinks().size()>1)
            throw new IllegalStateException("contains more links. expected=1, found="+slg.getAbsorbedLinks().size());
        
        MakeSession ms = dm.makeSession(Type);
        return make(ms, slg);
    }
    
    private LinkGroup make(MakeSession ms,DefaultLinkGroup slg)throws Exception{
        try{
            SeekableByteChannel sbc = ms.metaData();
            TrialLinkHandler tlh = slg.getAbsorbedLinks().get(0);
            ByteBuffer bb =ByteBuffer.wrap(tlh.getReferenceLinkString().getBytes());
            sbc.write(bb);
        }catch(Exception a){ ms.cancel();/*closes sbc as well*/ throw a;}
        
        Session s = Utils.make(ms,false);
        
        BasicLinkGroup blg = new BasicLinkGroup(slg.getAbsorbedLinks(), slg.tempDisplayName(), s);
        return blg;
    }

    @Override
    public LinkGroup restore(Session s) throws Exception {
        if(!s.getType().equalsIgnoreCase(Type)){ return null; }
        
        SeekableByteChannel sbc = s.openMetaData();
        sbc.position(0);
        
        ByteBuffer bb =ByteBuffer.allocate((int)(sbc.size()));
        int r = sbc.read(bb);
        if(r!=sbc.size()){
            throw new IllegalStateException("Channel didn\'t read full file in one go.");
        }
        String url = new String(bb.array());
        TrialLinkHandler tlh = null;
        try{
            tlh = LinkHandlerProviders.getWhichCanHandleOrDefault(url);
            if(tlh==null || tlh.canHandle()==false){
                throw new IllegalStateException("Data could not be restored by anyone.");
            }
        }catch(Exception a){ throw a;}
        TrialLinkGroup tlg = tryMaking(tlh);
        String dp = Utils.restoreDisplayName(s);
        if(dp==null) { dp=tlg.tempDisplayName(); }
        BasicLinkGroup blg = new BasicLinkGroup(tlg.getAbsorbedLinks(), dp, s);
        return blg;
    }

    @Override
    public LinkGroup modify(Session s,TrialLinkGroup tlg, DiskManager dm) throws Exception {
        if(!s.getType().equalsIgnoreCase(Type)) return null;
        if(!(tlg instanceof DefaultLinkGroup)) return null;
        
        DefaultLinkGroup slg = ((DefaultLinkGroup)tlg);
        if(slg.getAbsorbedLinks().size()>1)
            throw new IllegalStateException("contains more links. expected=1, found="+slg.getAbsorbedLinks().size());
        
        MakeSession ms = dm.modifySession(s);
        return make(ms, slg);
    }
   
}
