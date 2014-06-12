package neembuu.util;

/**
 * Frequently we face a problem while debugging.
 * Sometimes a thread ends throwing an exception.
 * And the reason of that issue is the caller/starter of the thread.
 * The caller send incorrect parameter or called the thread without initializing something.
 * In order to find from where the particular thread was called, 
 * it takes a little extra effort, as the stacktrace is useless.
 * 
 * All threads started using this utility class, maintain a copy of the stack trace of the caller 
 * thread as well. Apart from this, this providers utility methods to  allow printing of 
 * inherited stack trace .
 * 
 * http://stackoverflow.com/questions/23802871/append-stacktrace-of-caller-thread-into-the-new-thread-being-created-for-easier
 * @author Peter Lawrey
 * @author Shashank Tulsyan
 */
public enum Throwables {

    ;

    private static final InheritableThreadLocal<Throwable> STARTING_THREAD = new InheritableThreadLocal<Throwable>();

    private static void set(Throwable t) {
        STARTING_THREAD.set(t);
    }

    public static void addStartingThrowableAsSuppressed(Exception a){
        Throwable c = STARTING_THREAD.get();
        if(c!=null){
            a.addSuppressed(c);
        }
    }
    
    public static Throwable getStartingThrowable() {
        return STARTING_THREAD.get();
    }

    public static void printStartingThrowable() {
        Throwable throwable = getStartingThrowable();
        if (throwable == null) {
            return;
        }
        throwable.printStackTrace();
    }

    public static Thread start(final Runnable run) {
        return start(run,null);
    }
    
    public static Thread start(final Runnable run, String name) {
        return start(run,name,false);
    }
    
    public static Thread start(final Runnable run, String name, boolean daemon) {
        final Throwable tmp = new Throwable("Started here");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                set(tmp);
                run.run();
            }
        };
        final Thread t;
        if(name==null) t= new Thread(r);
        else t= new Thread(r,name);
        
        t.setUncaughtExceptionHandler(new StackTraceInheritingUncaughtExceptionHandler());
        t.setDaemon(daemon);
        t.start();
        return t;
    }

    private static final class StackTraceInheritingUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            Throwable ist = getStartingThrowable();
            if (ist != null) {
                e.addSuppressed(ist);
            }
            e.printStackTrace(System.err);
        }

    }
    
    /**
     * Not used. Earlier it was thought that usage of this would increase speed.
     * However since stacktrace elements are not resolved when the Excetion object
     * is created, this optimization is useless.
     */
    private static void makeThrowable(Throwable t,int depth){
        try{
            sun.misc.JavaLangAccess jla = sun.misc.SharedSecrets.getJavaLangAccess();
            int d = jla.getStackTraceDepth(t);
            if(d < depth){
                depth = d;
            }
            StackTraceElement[]ste=new StackTraceElement[depth];
            for (int i = 0; i < depth; i++) {
                ste[i]=jla.getStackTraceElement(t, i);
            }
            t.setStackTrace(ste);
        }catch(Exception a){
            
        }
    }
}
