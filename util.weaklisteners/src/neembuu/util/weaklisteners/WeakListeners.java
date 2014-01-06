/*
 * Copyright (c) 2011 Shashank Tulsyan <shashaanktulsyan@gmail.com>. 
 * 
 * This is part of free software: you can redistribute it and/or modify
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
 * along with this.  If not, see <http ://www.gnu.org/licenses/>.
 */
package neembuu.util.weaklisteners;

import java.util.EventListener;

/**
 * Refer http://bits.netbeans.org/dev/javadoc/org-openide-util/org/openide/util/WeakListeners.html 
 */
public final class WeakListeners {

    
//    public class Observer implements SomeListener {
//      private void registerTo(Source source) {
//          source.addSomeListener((SomeListener)WeakListeners.create (
//                  SomeListener.class, this, source));
//      }
//
//      public void someEventHappened(SomeEvent e) {
//          doSomething();
//      }
//   }
//   source is something that contains removeListener    
    
    public static <T extends EventListener> T create(Class<T> lType, T l, Object source) {
        return org.openide.util.WeakListeners.create(lType, l, source);
    }

    public static <T extends EventListener> T create(Class<T> lType, Class<? super T> apiType, T l, Object source) {
        //compiled code
        return org.openide.util.WeakListeners.create(lType, apiType, l, source);
    }
    
}
