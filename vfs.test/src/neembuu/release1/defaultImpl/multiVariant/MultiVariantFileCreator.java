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
package neembuu.release1.defaultImpl.multiVariant;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import jpfm.volume.vector.VectorDirectory;
import neembuu.release1.Main;
import neembuu.release1.api.file.NeembuuFile;
import neembuu.release1.api.file.OnlineFile;
import neembuu.release1.api.linkgroup.TrialLinkGroup;
import neembuu.release1.api.linkhandler.LinkHandler;
import neembuu.release1.api.linkhandler.LinkHandlerProviders;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.file.NeembuuFileCreator;
import neembuu.release1.api.ui.access.AddRemoveFromFileSystem;
import neembuu.release1.mountmanager.NeembuuFileWrapSCF;
import neembuu.vfs.file.SeekableConnectionFile;

/**
 *
 * @author Shashank Tulsyan
 */
public class MultiVariantFileCreator implements NeembuuFileCreator {

    private final TrialLinkGroup trialLinkGroup;
    private final AddRemoveFromFileSystem root;

    MultiVariantSession session = null;
    
    public MultiVariantFileCreator(TrialLinkGroup trialLinkGroup, AddRemoveFromFileSystem root) {
        this.trialLinkGroup = trialLinkGroup;
        this.root = root;

        
    }

    @Override
    public NeembuuFile create() throws Exception {
        if(session!=null){
            if(!session.isCompletelyClosed()){
                session.closeCompletely();
                throw new IllegalStateException("File not completely closed");
            }
        }
        
        List<NeembuuFile> variants = new ArrayList<>();
        VectorDirectory vd = checkLinks(variants);
        
        try {
            session = new MultiVariantSession(variants, vd, root);
        } catch (Exception a) {
            Main.getLOGGER().log(Level.INFO, "Could not handle splits", a);
        }
        
        return session;
    }
    
    private VectorDirectory checkLinks(List<NeembuuFile> connectionFiles)throws Exception{
        TrialLinkHandler trialLinkHandler = trialLinkGroup.getAbsorbedLinks().get(0);
        LinkHandler linkHandler = 
            LinkHandlerProviders.getHandler(trialLinkHandler);
        if(linkHandler==null){

            throw new Exception("It seems this website is not\n"
                    + "supported anymore by Neembuu now.");
        }
        
        VectorDirectory vd = new VectorDirectory(linkHandler.getGroupName(), null);

        
        if(linkHandler.getFiles().size() == 1) {
            Main.getLOGGER().info("MultiVariantFileCreator "+linkHandler+" has just one file when"
                    + " it was expected to have many. Using only first file.");
        }
        
        for (OnlineFile f  : linkHandler.getFiles()) {
            SeekableConnectionFile scf = root.create(f);
            vd.add(scf);
            connectionFiles.add(new NeembuuFileWrapSCF(scf, root, f.getPropertyProvider(),new String[]{vd.getName()}));
        }
        
        return vd;
    }

}
