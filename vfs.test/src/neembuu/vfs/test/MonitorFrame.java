/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.vfs.test;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import jpfm.DirectoryStream;
import jpfm.FileAttributesProvider;
import jpfm.FileType;
import jpfm.FormatterEvent;
import jpfm.JPfm;
import jpfm.MachineUtils;
import jpfm.mount.MountFlags;
import jpfm.SystemUtils;
import jpfm.MountListener;
import jpfm.UnmountException;
import jpfm.VolumeVisibility;
import jpfm.fs.FSUtils;
//import jpfm.fs.splitfs.CascadableSplitFS;
import jpfm.mount.Mount;
import jpfm.mount.MountParams.ParamType;
import jpfm.mount.MountParamsBuilder;
import jpfm.mount.Mounts;
import jpfm.volume.CommonFileAttributesProvider;
import jpfm.volume.VeryBigFile;
import jpfm.volume.vector.VectorDirectory;
import jpfm.volume.vector.VectorNode;
import jpfm.volume.vector.VectorRootDirectory;
import neembuu.config.GlobalTestSettings;
import neembuu.util.logging.LoggerUtil;
import neembuu.vfs.NeembuuVirtualFileSystem;
import neembuu.vfs.file.ConstrainUtility;
import neembuu.vfs.file.MonitoredHttpFile;
import neembuu.vfs.file.TroubleHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public class MonitorFrame
        extends
            Thread
        implements
            MountListener,
            ActionListener{
    private String[]filesToMount;
    private String mountLocation;
    private String heapLocation;

    //NeembuuVirtualFileSystem_old fileSystem;
    private NeembuuVirtualFileSystem fileSystem;
    private final VectorRootDirectory volume = new VectorRootDirectory(10, 3,CommonFileAttributesProvider.DEFAULT);

    Mount mount;
    final JPfm.Manager jpfmManager;

    static JFrame frame;
    JPanel content;

    protected static MonitorFrame SINGLETON ;
    private static final Logger LOGGER = LoggerUtil.getLogger();
    public static final boolean DEBUG = true;
    
    private final TroubleHandler troubleHandler;

    public MonitorFrame(final String mountLocation,final String heapLocation, final String[] filesToMount) {
        super("MonitorFrameThread");
        troubleHandler = new SimpleTroubleHandler(this);
        JPfm.Manager manager = null;
        
        try{
            manager =  JPfm.setDefaultManager(
                /*new JPfm.DefaultManager.LibraryLoader() {
                    @Override
                    public boolean loadLibrary(Logger logger) {
                        try {
                            if(SystemUtils.IS_OS_WINDOWS){
                                if(MachineUtils.is64Bit(MachineUtils.getRuntimeSystemArchitecture())){
                                    System.load("f:\\neembuu\\nbvfs_native\\jpfm\\VS_project_files\\x64\\Release\\jpfm.dll");
                                    logger.info("using f:\\neembuu\\nbvfs_native\\jpfm\\VS_project_files\\x64\\Release\\jpfm.dll");
                                }else {
                                    System.load("f:\\neembuu\\nbvfs_native\\jpfm\\VS_project_files\\Release\\jpfm.dll");
                                    logger.info("using f:\\neembuu\\nbvfs_native\\jpfm\\VS_project_files\\Release\\jpfm.dll");
                                }
                            }
                            else{ 
                                System.load("/Volumes/Files and Workspace/neembuu/nbvfs_native/jpfm_mac/jpfm_xcode/build/Debug/libjpfm.dylib");
                            }
                            return true;
                        } catch (Exception e) {
                            return false;
                        }
                    }
                },
                true*/
            );
        }catch(Exception a){
            javax.swing.JOptionPane.showMessageDialog(frame,
                    "Could not initialize Java pismo file mount\n"+
                    "Pismo file mount might not be installed.\n"+
                    "Actual error message="+a.getMessage(),
                    "Error in initialize JPfm",
                    javax.swing.JOptionPane.ERROR_MESSAGE
                    ,GlobalTestSettings.ONION_EMOTIONS.I_AM_DEAD);
            System.exit(-1);
        }
    
        jpfmManager=manager;

        this.filesToMount = filesToMount;
        this.heapLocation = heapLocation;
        this.mountLocation = mountLocation;

        createVolume();
        this.start();

        class CreateGuiThread extends Thread{

            public CreateGuiThread() {
                super("CreateGuiThread");
            }

            @Override
            public void run() {
                createGUI();
            }
        }

        new CreateGuiThread().start();
    }
    
    private void createVolume() {
        fileSystem = new NeembuuVirtualFileSystem(volume);        
        final VectorDirectory realfiles = new VectorDirectory("realfiles", volume);

        if(SystemUtils.IS_OS_WINDOWS){
            VeryBigFile veryBigFile = new VeryBigFile(volume);
            volume.add(veryBigFile);
        }

        //<editor-fold defaultstate="collapsed" desc="Using asynchronous files">
        /*try{
         * AbstractFile wrapedRealFile
         * = new DispatchReadRequestsInOtherThread.Wrapper(
         * new BasicRealFile_PreJava7(new java.io.File("j:\\Videos\\YouTube - Johnson_s Baby Hair Oil - Champi.flv")),
         * RequestHandlingApproach.BLOCKING,
         * volume,
         * "Wrapped Johnson_s Baby Hair Oil - Champi.flv");
         * volume.add(new MonitoredAbstractFile(wrapedRealFile, volume));
         * 
         * 
         * AbstractFile ayscRealFile
         * = new BasicRealFile(
         * Paths.get("j:\\Videos\\YouTube - Johnson_s Baby Hair Oil - Champi.flv"),
         * volume);
         * volume.add(new MonitoredAbstractFile(ayscRealFile, volume));
         * }catch(Exception a){
         * LOGGER.log(Level.INFO," ",a);
         * }*/
        //</editor-fold>

        for(String s : filesToMount){
            if(s.startsWith("http://") || s.startsWith("https://")){
                System.out.println("Adding file="+s);
                try{
                    MonitoredHttpFile mhttpfile = 
                        new MonitoredHttpFile_Builder_Test()
                            .setFileSize(-1)
                            .setStoragePath(heapLocation)
                            .setParent(volume)
                            .setUrl(s)
                            .setFileName(s.substring(s.lastIndexOf('/')+1))
                            .setTroubleHandler(troubleHandler)
                            .build();
                    decideDirectory(mhttpfile);                    
                }catch(Exception a){
                    LOGGER.log(Level.INFO," ",a);
                }
            }else {
                File ff = new File(s);
                if(!ff.exists()){
                    LOGGER.log(Level.INFO, "ignoring : file not found={0}", s);
                    continue;
                }
                try{
                    ThrottledRealFile file1 = new ThrottledRealFile(ff.getAbsolutePath(),ThrottledRealFile.INVALID_CPS,realfiles);
                    realfiles.add(file1);
                }catch(Exception a){
                    LOGGER.log(Level.INFO," ",a);
                }
            }
        }
        
        try{
            for(FileAttributesProvider fap : volume){
                if(fap instanceof DirectoryStream){
                    ConstrainUtility.constrain((DirectoryStream)fap);
                }
            }
        }catch(Exception a){
            a.printStackTrace(System.err);
        }
        
        String basicPath = null;
        if(SystemUtils.IS_OS_WINDOWS){
            basicPath = "J:\\neembuu\\realfiles\\";
        }else if(SystemUtils.IS_OS_LINUX){
            basicPath = "/media/j/neembuu/realfiles/";
        }else {// assume mac, as mac may be marked as either
            //mac or darwin
            basicPath = "/Volumes/MIDS/neembuu/realfiles/";
        }

        try{
            File[]files = new File(basicPath).listFiles();
            for (int i = 0; i < files.length; i++) {
                String nextPath = files[i].getAbsolutePath();
                try{
                    ThrottledRealFile file1 = new ThrottledRealFile(nextPath,ThrottledRealFile.INVALID_CPS,realfiles);
                    realfiles.add(file1);
                }catch(Exception e){
                    LOGGER.log(Level.FINE,"could not put file",e);
                }
            }
        }catch(Exception any){
            LOGGER.log(Level.FINE,"could not put file",any);
        }
        
        if(realfiles.size()>0){
            volume.add(realfiles);
        }
    }

    private void decideDirectory(MonitoredHttpFile mhttpfile){
        try{
            int indx = -1;
            String n = mhttpfile.getName();
            indx = Integer.parseInt(n.substring(n.length()-3));
            n = n.substring(0,n.length()-4);
            VectorNode suitableParent = findNode(n);
            suitableParent.add(mhttpfile);
            mhttpfile.setParent(suitableParent);
            mhttpfile.getDownloadConstrainHandler().setIndex(indx);
            return;
        }catch(Exception a){
            
        }
        volume.add(mhttpfile);
    }
    
    private VectorNode findNode(String name){
        for(FileAttributesProvider fap : volume){
            if(fap.getName().equals(name) && fap.getFileType()==FileType.FOLDER){
                return (VectorNode)fap;
            }
        }
        VectorDirectory vd = new VectorDirectory(name, volume);
        volume.add(vd);
        return vd;
    }

    public void createGUI(){
        //frame = new JFrame("Monitored Neembuu Virtual Volume (containing real files) ");
        frame.addWindowListener(new CloseHandler());
        content = new JPanel(new FlowLayout());
        JButton n = new JButton("Unmount");
        n.setActionCommand("unmount");
        n.addActionListener(this);
        n.setBounds(10, 10, 300, 100);
        
        JButton showOpenId = new JButton("Show open ids");
        showOpenId.setActionCommand("showOpenId");
        showOpenId.addActionListener(this);
        showOpenId.setBounds(10, 230, 300, 100);
        content.add(showOpenId);
        content.add(n);
        JButton printPendingOps = new JButton("Print pending ops");
        printPendingOps.setActionCommand("printPendingOps");
        printPendingOps.addActionListener(this);
        content.add(printPendingOps);
        JButton addFile = new JButton("Add files");
        addFile.setActionCommand("addFile");
        addFile.addActionListener(this);
        content.add(addFile);
        JButton gcChecker = new JButton("GC Check");
        gcChecker.setActionCommand("gcChecker");
        gcChecker.addActionListener(this);
        content.add(gcChecker);
        addToCon((DirectoryStream)volume,false);
        content.setPreferredSize(new Dimension(600,5000));
        JScrollPane scrollPane = new JScrollPane(content);
        frame.setContentPane(scrollPane);
        frame.setPreferredSize(new Dimension(650,600+100));
        frame.pack();
        frame.setVisible(true);
    }

    private void addToCon(DirectoryStream ds,boolean inner){
        for(FileAttributesProvider ff : ds){
            if( ff instanceof MonitoredHttpFile )
                content.add( ((MonitoredHttpFile)ff).getFilePanel());
            else if(ff instanceof MonitoredRealFile && !inner)
                content.add( ((MonitoredRealFile)ff).getFilePanel());
            else if(ff instanceof MonitoredAbstractFile && !inner){
                content.add( ((MonitoredAbstractFile)ff).getFilePanel());
            }else {
                LOGGER.log(Level.INFO, "cannot add {0} to display frame ", ff);
                if(ff instanceof DirectoryStream){
                    addToCon((DirectoryStream)ff,true);
                }
            }
        }
    }
    
    public static void main(String[]args) throws Exception {
        
        if(args.length>0){
            System.out.println("usage:");
            System.out.println("java -jar neembuu-vfs-test.jar --proxy host port username password");
            System.out.println("example:");
            System.out.println("java -jar neembuu-vfs-test.jar --proxy 10.1.1.10 80 itlib65 jobhiterapasswordhai");
            System.out.println("");
            if("--proxy".equals(args[0])){
                String host = args[1];
                int port = Integer.parseInt(args[2]);
                String userName = args[3];
                String password = args[4];
                
                
                System.out.println("Setting the following : ");
                System.out.println("host="+host);
                System.out.println("port="+port);
                System.out.println("userName="+userName);
                System.out.println("password="+password);
                
                GlobalTestSettings.setGlobalProxySettings(
                        new GlobalTestSettings.ProxySettings(userName, password, host, port));
            }
        }
        
        System.err.println(SystemUtils.OS_ARCH);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception lookandfeelexception) {
            LOGGER.log(Level.INFO," ",lookandfeelexception);
        }
        frame = new JFrame("Monitored Neembuu Virtual Volume (containing real files) ");
        frame.setMaximumSize(new Dimension(428,380+100));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        final String[]filesToMount;
        String mountLocation;
        String heapLocation;
        
        if(SystemUtils.IS_OS_WINDOWS){
            mountLocation = "j:\\neembuu\\virtual\\monitored.nbvfs";
            heapLocation = "J:\\neembuu\\heap\\";
        }else if(SystemUtils.IS_OS_LINUX){
            mountLocation = "/media/j/neembuu/virtual/monitored14/";
            heapLocation  = "/media/j/neembuu/heap/";
        }else {
            mountLocation = "/Volumes/MIDS/neembuu/virtual/monitored/" ;
            heapLocation = "/Volumes/MIDS/neembuu/heap/";
        }
        
        try{
            File neembuu_test_vfs_jar = new File(
                    MonitorFrame.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            File runtime_directory = neembuu_test_vfs_jar.getParentFile();
            
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream(new File(runtime_directory, ".properties"));
            properties.load(fis);
            fis.close();
            
            String mountlocationProperty = properties.getProperty("neembuu.vfs.test.MonitorFrame.mountLocation");
            File mountlocfile = new File(mountlocationProperty);
            if(mountlocfile.exists()){
                mountlocfile.delete();// to ensure something is not already mounted on it
                if(SystemUtils.IS_OS_WINDOWS)
                    mountlocfile.createNewFile();
                else
                    mountlocfile.mkdir();
            }
            
            mountLocation = mountlocationProperty;
            heapLocation  = properties.getProperty("neembuu.vfs.test.MonitorFrame.heapLocation");
        }catch(Exception a){
            a.printStackTrace();
        }
        
        boolean askUser = false;
        try{
            if(!new File(mountLocation).exists()){
                askUser = true;
            }
        }catch(Exception any){
            askUser = true;
        }

        if(askUser){
            javax.swing.JOptionPane.showMessageDialog(frame,
                "Please first choose a mount location (perferably a file).",
                "This app is for experimental purpose, may not be very user friendly",
                javax.swing.JOptionPane.PLAIN_MESSAGE
            );
            String path = System.getProperty("user.dir");
            javax.swing.JFileChooser fileChooser =
                    new javax.swing.JFileChooser(path);
            fileChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);
            int retVal = fileChooser.showOpenDialog(frame);
            if(retVal == javax.swing.JFileChooser.APPROVE_OPTION){
                mountLocation = fileChooser.getSelectedFile().getAbsoluteFile().getPath();
            }else {
                System.err.println("User did not select a mount location");
                System.exit(-1);
            }

            javax.swing.JOptionPane.showMessageDialog(frame,
                "Choose a folder which we can use to temporarily\n" +
                "store the downloaded data.  \n",
                "This app is for experimental purpose, may not be very user friendly",
                javax.swing.JOptionPane.PLAIN_MESSAGE
            );

            fileChooser = new javax.swing.JFileChooser(path);
            fileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
            retVal = fileChooser.showOpenDialog(frame);
            if(retVal == javax.swing.JFileChooser.APPROVE_OPTION){
                heapLocation = fileChooser.getSelectedFile().getAbsoluteFile().getPath();//throws exception
            }else {
                System.err.println("User did not select a directory");
                System.exit(-1);
            }
        }

        try{
            File neembuu_test_vfs_jar = new File(
                    MonitorFrame.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            File runtime_directory = neembuu_test_vfs_jar.getParentFile();

            Properties saveProperties = new Properties();
            saveProperties.setProperty("neembuu.vfs.test.MonitorFrame.mountLocation", mountLocation);
            saveProperties.setProperty("neembuu.vfs.test.MonitorFrame.heapLocation", heapLocation);

            File saveLocation = new File(runtime_directory, ".properties");
            saveLocation.createNewFile();
            FileOutputStream fos 
                    = new FileOutputStream(saveLocation);
            System.out.println("savelocation="+saveLocation);
            saveProperties.store(fos,null);

            fos.flush(); fos.close();
        }catch(Exception a){
            a.printStackTrace();
        }
        
        if(GlobalTestSettings.IS_RUNNING_FROM_JAR){
            filesToMount = showAddFilesDialog();
        }else{
            System.setProperty("neembuu.vfs.test.MoniorFrame.resumepolicy", 
                "emptyDirectory");
                //"resumeFromPreviousState");
            
            if(SystemUtils.IS_OS_WINDOWS){
                    filesToMount=new String[]{
                        //"http://installer.jdownloader.org/test120k.rmvb.001",
                        "http://update0.jdownloader.org/test120k.rmvb",
                        //"http://update3.jdownloader.org/speed.avi"
                        //"http://update3.jdownloader.org/testFiles/thedivide-tlr1_h1080p.mov",
                        //"http://installer.jdownloader.org/test/Downloads.7zip",
                        //"http://update3.jdownloader.org/testFiles/battleship-tlr2_h1080p.mov"
                        /*"http://localhost:8080/LocalFileServer-war/servlet/FileServer?"
                            + "totalFileSpeedLimit=280&"
                            + "mode=constantAverageSpeed"
                            //+ "mode=constantFlow"
                            + "&newConnectionTimemillisec=500&file=s.001",
                        "http://localhost:8080/LocalFileServer-war/servlet/FileServer?"
                            + "totalFileSpeedLimit=280&"
                            + "mode=constantAverageSpeed"
                            //+ "mode=constantFlow"
                            + "&newConnectionTimemillisec=500&file=s.002",
                        "http://localhost:8080/LocalFileServer-war/servlet/FileServer?"
                            + "totalFileSpeedLimit=280&"
                            + "mode=constantAverageSpeed"
                            //+ "mode=constantFlow"
                            + "&newConnectionTimemillisec=500&file=s.003",*/
                        //"http://199.91.152.86/f4c9jdmng4ng/yjqmrextmry/FMP%21Fumoffu_02_www.cooldbz.com.mkv",
                        /*"http://localhost:8080/LocalFileServer-war/servlet/FileServer?"
                            + "totalFileSpeedLimit=395&"
                            //+ "mode=constantAverageSpeed"
                            + "mode=constantFlow"
                            + "&newConnectionTimemillisec=500&file=LesChevaliersduCielHDPromo.mp4",*/
                        /*"http://localhost:8080/LocalFileServer-war/servlet/FileServer?"
                            + "totalFileSpeedLimit="
                            + "50"
                            + "&"
                            + "mode=constantAverageSpeed"
                            //+ "mode=constantFlow"
                            + "&newConnectionTimemillisec=1&file=test120k.rmvb",*/
                        
                        /*"http://localhost:8080/LocalFileServer-war/servlet/FileServer?"
                            + "totalFileSpeedLimit="
                            + "50"
                            + "&"
                            + "mode=constantAverageSpeed"
                            //+ "mode=constantFlow"
                            + "&newConnectionTimemillisec=1&file=test120k.rmvb.001",
                        "http://localhost:8080/LocalFileServer-war/servlet/FileServer?"
                            + "totalFileSpeedLimit="
                            + "50"
                            + "&"
                            + "mode=constantAverageSpeed"
                            //+ "mode=constantFlow"
                            + "&newConnectionTimemillisec=1&file=test120k.rmvb.002",
                        "http://localhost:8080/LocalFileServer-war/servlet/FileServer?"
                            + "totalFileSpeedLimit="
                            + "50"
                            + "&"
                            + "mode=constantAverageSpeed"
                            //+ "mode=constantFlow"
                            + "&newConnectionTimemillisec=1&file=test120k.rmvb.003",
                        "http://localhost:8080/LocalFileServer-war/servlet/FileServer?"
                            + "totalFileSpeedLimit="
                            + "50"
                            + "&"
                            + "mode=constantAverageSpeed"
                            //+ "mode=constantFlow"
                            + "&newConnectionTimemillisec=1&file=test120k.rmvb.004",*/
                    };
                //SeekableHttpChannel.DEFAULT_HTTP_PORT = 8080;
            }else if(SystemUtils.IS_OS_LINUX) {
                filesToMount=new String[]{
                    //"http://update0.jdownloader.org/test120k.rmvb",
                    "http://neembuu.sourceforge.net/test120k.rmvb",
                    "/media/j/Videos/Requiem_for_a_Duel.mkv"
                };
            }else { // mac or something else
                System.out.println("someother os, probably mac :"+ SystemUtils.OS_NAME);
                filesToMount=new String[]{
                    "http://update0.jdownloader.org/test120k.rmvb",
                    "/Volumes/MIDS/Videos/Requiem_for_a_Duel.mkv"
                };
            }
        }

        final String ml = mountLocation;
        final String hl = heapLocation;
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                MonitorFrame frame = new MonitorFrame(ml,hl,filesToMount);
                SINGLETON = frame;
            }
        });
    }
    
    private static String[]showAddFilesDialog(){
        final LinkedList<String> filetoMountList = new LinkedList<String>();

        AddTestLinks atl = new AddTestLinks(frame, true, filetoMountList);
        atl.setVisible(true);

        String[]filesToMount_ = (String[])filetoMountList.toArray(new String[]{});
        return filesToMount_;
    }

    @Override
    public void eventOccurred(FormatterEvent event) {
        if(event.getEventType()==FormatterEvent.EVENT.SUCCESSFULLY_MOUNTED){
            try{
                Desktop.getDesktop().open(new File(mountLocation));
            }catch(Exception ioe){
                JOptionPane.showMessageDialog(frame, 
                        "Could not open mount location "+mountLocation+"\n"
                        + "This happens in platforms that do no support java.awt.Desktop.getDesktop.open()"
                        + "\n Reasons : "
                            +ioe.getMessage()
                        ,"Could not open mount location"
                        ,JOptionPane.ERROR_MESSAGE
                        ,GlobalTestSettings.ONION_EMOTIONS.I_AM_DEAD);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(e.getActionCommand().equalsIgnoreCase("unmount")){
                Thread [] t = new Thread[mount.getThreadGroup(jpfmManager).activeCount()];
                mount.getThreadGroup(jpfmManager).enumerate(t);
                System.out.println("++thread list++");
                for (int i = 0; i < t.length; i++) {
                    Thread thread = t[i];
                    System.out.println(thread);
                }System.out.println("--thread list--");
                mount.unMount();
                System.out.println("unmounted");
            }
            if(e.getActionCommand().equalsIgnoreCase("showOpenId")){
                Iterator<FileAttributesProvider> it = volume.iterator();
                System.out.println("++open id of all++");
                while(it.hasNext()){
                    FileAttributesProvider  next = it.next();
                    System.out.print(next.getName());
                    System.out.print(" ");
                    System.out.println(next.getFileDescriptor());
                    System.out.print(" ");
                    System.out.print(" class="+next.getClass());
                }
                System.out.println("--open id of all--");
            }
            if(e.getActionCommand().equals("printPendingOps")){
                FSUtils.printIncompleteOperationsBasic(fileSystem);
                return;
            }
            if(e.getActionCommand().equals("gcChecker")){
                System.out.println("NumberOfReadInstancesInMemory="+jpfm.operations.ReadImpl.numberOfReadInstancesInMemory);
                return;
            }
            if(e.getActionCommand().equals("addFile")){
                String[]newFilesToMount = showAddFilesDialog();
                for(String s:newFilesToMount){
                    frame.setVisible(false);
                    if(s.startsWith("http://") || s.startsWith("https://")){
                        System.out.println("Adding file="+s);
                        try{
                            MonitoredHttpFile mhttpfile = 
                                new MonitoredHttpFile_Builder_Test()
                                    .setFileSize(-1)
                                    .setStoragePath(heapLocation)
                                    .setParent(volume)
                                    .setUrl(s)
                                    .setTroubleHandler(troubleHandler)
                                    .setFileName(s.substring(s.lastIndexOf('/')+1))
                                    .build();

                            volume.add(mhttpfile);
                            content.add(mhttpfile.getFilePanel());
                        }catch(Exception a){
                            LOGGER.log(Level.INFO," ",a);
                        }
                    }
                }
                
                frame.setVisible(true);
                
                return;
            }
        }catch (UnmountException u){
            LOGGER.log(Level.INFO," ",u);
        }
    }

    class CloseHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            try{
                if(mount!=null)
                    mount.unMount();
            }
            catch(Exception a){
                LOGGER.log(Level.INFO," ",a);
            }System.exit(0);
        }

    }

    @Override
    public void run() {
        final MountFlags mountFlags = new MountFlags.Builder()
                .setForceUnbuffered()
                //.setWorldRead()
                //.setSystemVisible()
                .build();
        try{
            mount = Mounts.mount(new MountParamsBuilder()
                    .set(ParamType.LISTENER, this)
                    .set(ParamType.MOUNT_LOCATION, mountLocation)//.toString())
                    .set(ParamType.FILE_SYSTEM, fileSystem)
                    .set(ParamType.EXIT_ON_UNMOUNT, false)
                    .set(ParamType.VOLUME_VISIBILITY, VolumeVisibility.GLOBAL)
                    .set(ParamType.MOUNT_FLAGS, mountFlags)
                    .build());
        }catch(Exception any){
            LOGGER.log(Level.INFO," ",any);
            System.exit(1);
        }
    }



}