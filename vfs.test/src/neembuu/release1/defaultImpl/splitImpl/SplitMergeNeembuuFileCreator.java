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
package neembuu.release1.defaultImpl.splitImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import neembuu.release1.Main;
import neembuu.release1.api.file.NeembuuFile;
import neembuu.release1.api.linkgroup.TrialLinkGroup;
import neembuu.release1.api.linkhandler.LinkHandler;
import neembuu.release1.api.linkhandler.LinkHandlerProviders;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.file.NeembuuFileCreator;
import neembuu.release1.api.ui.access.MinimalistFileSystem;
import neembuu.vfs.file.SeekableConnectionFile;

/**
 *
 * @author Shashank Tulsyan
 */
public class SplitMergeNeembuuFileCreator implements NeembuuFileCreator {

    private final TrialLinkGroup trialLinkGroup;
    private final MinimalistFileSystem root;

    SplitGroupSession splitGroupSession = null;
    
    public SplitMergeNeembuuFileCreator(TrialLinkGroup trialLinkGroup, MinimalistFileSystem root) {
        this.trialLinkGroup = trialLinkGroup;
        this.root = root;
    }
    
    
    
    @Override
    public NeembuuFile create() throws Exception {
        if(splitGroupSession!=null){
            if(!splitGroupSession.isCompletelyClosed()){
                splitGroupSession.closeCompletely();
                throw new IllegalStateException("File not completely closed");
            }
        }
        
        List<SeekableConnectionFile> splits = new ArrayList<SeekableConnectionFile>();
        checkLinks(splits);
        
        try {
            SplitGroupProcessor splitGroupProcessor = new SplitGroupProcessor();
            splitGroupSession = splitGroupProcessor.handle(splits, root);
        } catch (Exception a) {
            Main.getLOGGER().log(Level.INFO, "Could not handle splits", a);
        }
        
        return splitGroupSession;
    }
    
    private void checkLinks(List<SeekableConnectionFile> connectionFiles)throws Exception{
        for (TrialLinkHandler trialLinkHandler : trialLinkGroup.getAbsorbedLinks()) {
            SeekableConnectionFile scf = checkLink(trialLinkHandler);
            connectionFiles.add(scf);
        }
    }
    
    private SeekableConnectionFile checkLink(TrialLinkHandler trialLinkHandler)throws Exception{
        LinkHandler linkHandler = 
            LinkHandlerProviders.getHandler(trialLinkHandler);
        if(linkHandler==null){

            throw new Exception("It seems this website is not\n"
                    + "supported anymore by Neembuu now.");
        }
        
        if(linkHandler.getFiles().size() > 1) {
            Main.getLOGGER().info("LinkHandler "+linkHandler+" has more than one file when"
                    + " it was expected to have only one. Using only first file.");
        }
        
        neembuu.release1.api.file.OnlineFile f = linkHandler.getFiles().get(0);
        
        return root.create(f);
    }

}
