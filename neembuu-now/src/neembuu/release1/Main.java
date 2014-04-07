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

package neembuu.release1;

import neembuu.release1.mountmanager.MountManager;
import java.util.logging.Logger;
import jpfm.fs.FSUtils;
import neembuu.diskmanager.DiskManager;
import neembuu.diskmanager.DiskManagerParams;
import neembuu.diskmanager.DiskManagers;
import neembuu.release1.api.linkgroup.LinkGroupMakers;
import neembuu.release1.api.linkhandler.LinkHandlerProviders;
import neembuu.release1.defaultImpl.linkgroup.DefaultLinkGroupMaker;
import neembuu.release1.defaultImpl.linkgroup.SplitsLinkGroupMaker;
import neembuu.release1.defaultImpl.linkhandler.YoutubeLinkHandlerProvider;
import neembuu.release1.defaultImpl.linkhandler.DirectLinkHandlerProvider;
import neembuu.release1.defaultImpl.restore_previous.RestorePreviousSessionImpl;
import neembuu.release1.open.Opener;
import neembuu.release1.ui.InitLookAndFeel;
import neembuu.release1.ui.NeembuuUI;
import neembuu.vfs.file.TroubleHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public final class Main {

    private final NeembuuUI nui;
    private final Logger logger = initLogger();
    private final TroubleHandler troubleHandler;
    private final MountManager mountManager;
    private final DiskManager diskManager;
    
    public Main() {
        this.nui = new NeembuuUI();
        Application.setMainComponent(nui.getMainComponent());
        troubleHandler = new UnprofessionalTroubleHandler(nui.getMainComponent(),nui.getIndefiniteTaskUI());

        String basePath = Application.getResource(Application.Resource.TempStorage)
                    .toAbsolutePath().toString();
        
        diskManager = DiskManagers.getDefaultManager(new DiskManagerParams.Builder()
            .setBaseStoragePath(basePath)
            .useDefaultNomenclatureAndLoggerCreate()
            .build()
        );
        
        LinkGroupMakers.initDiskManager(diskManager);
        
        mountManager = new MountManager(
                nui.getMainComponent(),
                nui.getIndefiniteTaskUI(),
                nui.getMainUIA(),
                troubleHandler,
                diskManager
        );
    }
    
    private void initialize(){
        nui.initialize(this);
        mountManager.initialize();
        
        // move out of this jar
        LinkHandlerProviders.registerProvider(new YoutubeLinkHandlerProvider());
        
        //DefaultLinkHandler is the default handler
        LinkHandlerProviders.registerDefaultProvider(new DirectLinkHandlerProvider());
        
        //Registering Link Group makers
        LinkGroupMakers.registerDefaultMaker(new DefaultLinkGroupMaker());
        LinkGroupMakers.registerMaker(new SplitsLinkGroupMaker());
        
        Opener.I.initMainComponent(nui.getMainComponent());
        
        RestorePreviousSessionImpl rpsi = new RestorePreviousSessionImpl(diskManager, nui.getLinkGroupUICreator(),nui);
        rpsi.checkAndRestoreFromPrevious();
    }

    public TroubleHandler getTroubleHandler() {
        return troubleHandler;
    }

    public DiskManager getDiskManager() {
        return diskManager;
    }
    
    public NeembuuUI getNui() {
        return nui;
    }

    public static Logger getLOGGER() {
        return get().logger;
    }
    

    public MountManager getMountManager() {
        return mountManager;
    }
    
    private Logger initLogger(){
        //return neembuu.release1.log.LoggerUtil.getLogger("Main");
        return neembuu.util.logging.LoggerUtil.getLogger();
    }
    
    private static Main get() {
        return InstHolder.m;
    }
    
    public static void printPendingRequests(){
        FSUtils.printIncompleteOperationsBasic(get().mountManager.getFileSystem());
    }
    
    private static class InstHolder {
        private static Main m = new Main();
    }
    
    public static void main(String[] args) {
        InitLookAndFeel.init();
        get().initialize();
    }

}
