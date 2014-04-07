package neembuu.release1.ui.linkpanel;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import neembuu.release1.api.file.OnlineFile;
import neembuu.release1.api.linkhandler.LinkHandler;
import neembuu.release1.api.linkhandler.LinkHandlerProvider;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.defaultImpl.file.BasicOnlineFile;
import neembuu.release1.defaultImpl.DummyMultiVariantLinkHandler;
import neembuu.release1.defaultImpl.DummyMultiVariantTrialLinkHandler;
import neembuu.release1.defaultImpl.LinkOrganizerImplTest;
import neembuu.release1.defaultImpl.LinkOrganizerImplTest.TrialLinkHandlerDummy;

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

/**
 *
 * @author Shashank Tulsyan
 */
public class FakeLinkHandlerProvider implements LinkHandlerProvider {

    public FakeLinkHandlerProvider() {
    }
    
    @Override
    public TrialLinkHandler tryHandling(String url) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LinkHandler getLinkHandler(TrialLinkHandler trialLinkHandler) throws Exception {
        if(trialLinkHandler instanceof TrialLinkHandlerDummy){
            return new FakeLinkHandler(trialLinkHandler);
        }else if(trialLinkHandler instanceof DummyMultiVariantTrialLinkHandler){
            return new DummyMultiVariantLinkHandler();
        }
        return null;
    }
    
    public static final class FakeLinkHandler implements LinkHandler{
        private final TrialLinkHandlerDummy tlh;

        public FakeLinkHandler(TrialLinkHandler tlh) {
            this.tlh = (TrialLinkHandlerDummy)tlh;
        }
        
        @Override
        public List<OnlineFile> getFiles() {
            LinkedList<OnlineFile> ofs = new LinkedList<OnlineFile>();
            Collections.singletonList(
            ofs.add(BasicOnlineFile.Builder.create()
                    .setName(tlh.tempDisplayName())
                    .setSize(getSize())
                    .setNewConnectionProvider(new FakeConnectionProvider(tlh.getFileSource(), tlh.getFakeSpeedTarget_inKiBps()))
                    .build()) 
            );
            return ofs;
        }

        @Override public String getGroupName() { return tlh.tempDisplayName();}
        private long getSize() { 
            long sz = 1;
            try {
                sz = new java.io.File(tlh.getFileSource()).length();
            } catch (Exception a) {
                a.printStackTrace();
            }
            return sz; 
        }
        @Override public boolean foundName() { return true;}        
    }
    
}
