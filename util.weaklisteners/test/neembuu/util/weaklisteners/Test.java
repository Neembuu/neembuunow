/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.util.weaklisteners;

import java.util.Collections;
import java.util.EventListener;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
public class Test {
    
    public  static interface SomeListener extends EventListener {
        public void doSomethingWeird(String string);
    }
    
    private static final class ListableImpl {
        private final List<SomeListener>
                someListeners = Collections.synchronizedList(new Vector<SomeListener>());
        public void addSomeListener(SomeListener someListener){
            someListeners.add(WeakListeners.create(
                    SomeListener.class,someListener, this));
        }
        
        StackTraceElement[]stktrc;
        
        public void removeSomeListener(SomeListener someListener){
            // called on finalizer thread 
            // so nothing will print u now
            //JOptionPane.showInputDialog("removed"+someListener);
            System.out.println("removed = "+someListener);
            new Throwable().printStackTrace(System.out);
        }
        
        public void doSomethingSerious(){
            for(SomeListener sl : someListeners){
                sl.doSomethingWeird(Math.random()+"");
            }
        }
    }
    
    private static final class SomeL implements SomeListener {

        @Override
        public final void doSomethingWeird(String string) {
            new Throwable().printStackTrace(System.out);
            System.out.println("we did something "+string);
        }
        
    }

    public static void main(String[] args) {
        ListableImpl impl = new ListableImpl();
        
        impl.addSomeListener(new SomeL());
        impl.addSomeListener(new SomeL());
        impl.addSomeListener(new SomeL());
        impl.addSomeListener(new SomeL());
        impl.addSomeListener(new SomeL());
        
        impl.doSomethingSerious();
        System.out.println("gcing");
        System.gc();
        System.out.println("after gc");
        impl.doSomethingSerious();
        System.out.println("done");
        try{
            Thread.sleep(1000);
        }catch(Exception a){
            
        }
    }
}
