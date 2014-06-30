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

import neembuu.release1.app.Application;
import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import neembuu.release1.mountmanager.MountManager;
import jpfm.fs.FSUtils;
import neembuu.diskmanager.DiskManager;
import neembuu.diskmanager.DiskManagerParams;
import neembuu.diskmanager.DiskManagers;
import neembuu.release1.api.clipboardmonitor.ClipboardMonitor;
import neembuu.release1.api.linkgroup.LinkGroupMakers;
import neembuu.release1.api.linkhandler.LinkHandlerProviders;
import neembuu.release1.api.open.Openers;
import neembuu.release1.clipboard.AddLinksFromClipboardImpl;
import neembuu.release1.clipboard.ClipboardMonitorImpl;
import neembuu.release1.defaultImpl.linkgroup.DefaultLinkGroupMaker;
import neembuu.release1.defaultImpl.linkgroup.SplitsLinkGroupMaker;
import neembuu.release1.defaultImpl.linkhandler.DirectLinkHandlerProvider;
import neembuu.release1.defaultImpl.log.LoggerServiceProviderImpl;
import neembuu.release1.defaultImpl.restore_previous.RestorePreviousSessionImpl;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.release1.api.settings.Settings;
import neembuu.release1.app.RunningStateListener;
import neembuu.release1.app.DirectoryWatcherService;
import neembuu.release1.app.DirectoryWatcherServiceImpl;
import neembuu.release1.app.EnsureSingleInstance;
import neembuu.release1.app.FlashGotDownloadCommand;
import neembuu.release1.app.MainCommandsListener;
import neembuu.release1.app.RunAttemptListener;
import neembuu.release1.app.SingleInstanceCheckCallbackImpl;
import neembuu.release1.defaultImpl.external.ExternalLinkHandlersProvider;
import neembuu.release1.open.OpenerImpl;
import neembuu.release1.settings.SettingsImpl;
import neembuu.release1.ui.InitLookAndFeel;
import neembuu.release1.ui.NeembuuUI;
import neembuu.release1.ui.systray.SysTrayImpl;
import neembuu.release1.versioning.CheckUpdate;
import neembuu.release1.versioning.first_time_user.FirstTimeUser;
import neembuu.util.Throwables;
import neembuu.vfs.file.TroubleHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public final class Main {

    private final NeembuuUI nui;
    private final TroubleHandler troubleHandler;
    private final MountManager mountManager;
    private final DiskManager diskManager;
    private final ClipboardMonitor clipboardMonitor;
    private final Settings settings;
    private final SysTrayImpl sysTray;
    private final SingleInstanceCheckCallbackImpl sicci;
    
    private static boolean lazyUI = false;
    
    public Main() {
        settings = SettingsImpl.I(this);//safe
        sysTray = new SysTrayImpl();
        this.nui = new NeembuuUI(settings,sysTray,lazyUI);
        //Application.setMainComponent(new NonUIMainComponent());
        Application.setMainComponent(nui.getMainComponent());
        
        initLogger();
        
        troubleHandler = new UnprofessionalTroubleHandler(nui.getMainComponent(),nui.getIndefiniteTaskUI());

        
        String basePath = Application.getResource(Application.Resource.TempStorage)
                    .toAbsolutePath().toString();
        
        diskManager = DiskManagers.getDefaultManager(new DiskManagerParams.Builder()
            .setBaseStoragePath(basePath)
            .useDefaultNomenclatureAndLoggerCreator()
            .build()
        );
        
        sicci = ensureSingleInstance();
        
        LinkGroupMakers.initDiskManager(diskManager);
        
        mountManager = new MountManager(
                nui.getMainComponent(),
                nui.getIndefiniteTaskUI(),
                nui.getMainUIA(),
                troubleHandler,
                diskManager
        );
        
        clipboardMonitor = new ClipboardMonitorImpl();
    }
    
    private SingleInstanceCheckCallbackImpl ensureSingleInstance(){
        SingleInstanceCheckCallbackImpl sicci1 = new SingleInstanceCheckCallbackImpl();
        EnsureSingleInstance esi = new EnsureSingleInstance(sicci1);
        final CountDownLatch cdl = new CountDownLatch(1);
        sicci1.addAlreadyRunningListener(new RunningStateListener() {
            @Override public void alreadyRunning(long timeSince) {
                cdl.countDown();
            }@Override public boolean solelyRunning(long timeSince) {
                cdl.countDown(); return true;
            }
        });
        esi.startService();
        try{
            boolean ensure = cdl.await(12, TimeUnit.SECONDS);
            if(!ensure)LoggerUtil.L().info("Could not ensure no instance already running");
        }catch(Exception a){}
        return sicci1;
    }
    
    private void initCommandsMonitor(){
        MainCommandsListener  mcl = new MainCommandsListener();
        FlashGotDownloadCommand fgdc = new FlashGotDownloadCommand(
                nui.getLinkGroupUICreator(),nui.getMainComponent(),
                nui.getIndefiniteTaskUI_withTrayNotification());
        mcl.register(fgdc.defaultExtension(), fgdc);
        final DirectoryWatcherService dws = new DirectoryWatcherServiceImpl(mcl);
        dws.startService();
        dws.forceRescan(System.currentTimeMillis());
        
        sicci.addRunAttemptListener(new RunAttemptListener() {
            @Override public void attemptedToRun(long time) {
                Throwables.start(new Runnable() {@Override public void run() {
                    try{Thread.sleep(2000); nui.getMainComponent().getJFrame().setAlwaysOnTop(false);}
                    catch(Exception a){throw new RuntimeException(a);}
                }});
                nui.getMainComponent().getJFrame().setAlwaysOnTop(true);
                nui.getMainComponent().getJFrame().setVisible(true);
                nui.getMainComponent().getJFrame().requestFocus();
            }});
        sicci.addRunAttemptListener(new RunAttemptListener() {
            @Override public void attemptedToRun(long time) {
                dws.forceRescan(time);
            }});
    }
    
    private void initialize(CallbackFromTestCode c){
        clipboardMonitor.startService();
        sysTray.initTray();
        nui.initialize(mountManager);
        mountManager.initialize();
        if(c!=null){c.callback(); /*this will initialize youtube handlers and other stuff*/}
        initLinkHandlerProviders();
        initLinkGroupMakers();
        initOpener();
        
        restorePreviousSession();
        initCommandsMonitor();
        
        AddLinksFromClipboardImpl.createAndStart(nui.getAddLinkUI(), clipboardMonitor);
        CheckUpdate.checkLater(nui.getMainComponent());
        FirstTimeUser.handleUser(nui.getAddLinkUI(),nui.getMainComponent(),settings);
    }
    
    private void restorePreviousSession(){
        nui.getIndefiniteTaskUI();
        RestorePreviousSessionImpl rpsi = new RestorePreviousSessionImpl(diskManager, 
                nui.getLinkGroupUICreator(),nui.getAddLinkUI(),nui.getIndefiniteTaskUI());
        rpsi.checkAndRestoreFromPrevious();
    }
    
    private void initOpener(){
        OpenerImpl defaultOpener = new OpenerImpl(nui.getMainComponent());
        Openers.setOpener(defaultOpener);
        nui.initOpenerA(defaultOpener.getOpenerAccess());
    }
    
    private void initLinkGroupMakers(){
        //Registering Link Group makers
        LinkGroupMakers.registerDefaultMaker(new DefaultLinkGroupMaker());
        LinkGroupMakers.registerMaker(new SplitsLinkGroupMaker());
    }
    
    private void initLinkHandlerProviders(){
        // move out of this jar, postponded to next version
        LinkHandlerProviders.registerProvider(new neembuu.release1.externalImpl.linkhandler.DailymotionLinkHandlerProvider());
        LinkHandlerProviders.registerProvider(new neembuu.release1.externalImpl.linkhandler.VimeoLinkHandlerProvider());
        LinkHandlerProviders.registerProvider(new neembuu.release1.externalImpl.linkhandler.YoutubeLinkHandlerProvider());
        LinkHandlerProviders.registerProvider(new ExternalLinkHandlersProvider(nui.getIndefiniteTaskUI()));
        //DefaultLinkHandler is the default handler
        LinkHandlerProviders.registerDefaultProvider(new DirectLinkHandlerProvider());
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

    public MountManager getMountManager() {
        return mountManager;
    }
        
    private void initLogger(){
        try{
            if(Application.getRuntime() != Application.Runtime.Development){
                java.io.File stdout = Application.getResource(Application.Resource.Logs, "std.out.txt").toFile();
                System.setOut(new PrintStream(stdout));
                System.setErr(new PrintStream(stdout));
            }
        }catch(Exception a){
            a.printStackTrace();
        }
        //return neembuu.release1.log.LoggerUtil.getLogger("Main");
        //return neembuu.util.logging.LoggerUtil.getLogger();
        
        //initialize global logger service
        LoggerUtil.setServiceProvider(new LoggerServiceProviderImpl());
        //return Logger.getLogger(Main.class.getName());
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
        if(args!=null && args.length>0){ Main.lazyUI = args[0].equalsIgnoreCase("--lazyUI");}
        main(args,null);
    }
    
    public static void main(String[] args, CallbackFromTestCode c) {
        InitLookAndFeel.init();
        Main m = get();
        m.initialize(c);
    }
    
    public interface CallbackFromTestCode {
        void callback();
    }

}
