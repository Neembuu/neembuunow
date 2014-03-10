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
