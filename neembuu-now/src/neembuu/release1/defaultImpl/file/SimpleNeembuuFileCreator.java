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

package neembuu.release1.defaultImpl.file;

import neembuu.release1.api.file.NeembuuFile;
import neembuu.release1.api.linkhandler.LinkHandler;
import neembuu.release1.api.linkhandler.LinkHandlerProviders;
import neembuu.release1.api.file.NeembuuFileCreator;
import neembuu.release1.api.linkgroup.LinkGroup;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.release1.api.ui.access.MinimalistFileSystem;

/**
 *
 * @author Shashank Tulsyan
 */
public class SimpleNeembuuFileCreator implements NeembuuFileCreator{

    private final LinkGroup linkGroup;
    private final MinimalistFileSystem minimalistFileSystem;

    public SimpleNeembuuFileCreator(LinkGroup linkGroup, 
            MinimalistFileSystem minimalistFileSystem) {
        this.linkGroup = linkGroup;
        this.minimalistFileSystem = minimalistFileSystem;
    }
    
    
    
    @Override
    public NeembuuFile create() throws Exception{
        LinkHandler linkHandler = 
            LinkHandlerProviders.getHandler(linkGroup.getAbsorbedLinks().get(0));
        if(linkHandler==null){

            throw new Exception("It seems this website is not\n"
                    + "supported anymore by Neembuu now.");
        }
        
        if(linkHandler.getFiles().size() > 1) {
            LoggerUtil.getLogger().info("LinkHandler "+linkHandler+" has more than one file when"
                    + " it was expected to have only one. Using only first file.");
        }
        
        neembuu.release1.api.file.OnlineFile f = linkHandler.getFiles().get(0);
        
        NeembuuFileWrapSCF fileWrapSCF = new NeembuuFileWrapSCF(
                minimalistFileSystem.create(f,linkGroup.getSession()), 
                minimalistFileSystem,linkGroup.getSession());
        
        return fileWrapSCF;
    }
    
}
