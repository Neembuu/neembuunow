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

package neembuu.util;

import static neembuu.util.Throwables.printStartingThrowable;
import static neembuu.util.Throwables.start;

/**
 *
 * @author Shashank Tulsyan
 */
public class ThrowablesTest {
    public static void main(String... ignored) {
        try {
            method();
        } catch (Throwable t) {
            System.err.println("\nThrown in " + Thread.currentThread());
            t.printStackTrace();
            printStartingThrowable();
        }

        /*start(new Runnable() {
            @Override
            public void run() {
                try {
                    method();
                } catch (Throwable t) {
                    System.err.println("\nThrown in " + Thread.currentThread());
                    t.printStackTrace();
                    printStartingThrowable();
                }
            }
        }, "Test thread", false);*/
        System.err.println("--");
         m1();

    }
    
    private static void m1(){
        m2();
    }
    
    private static void m2(){
        start(new Runnable() {
            @Override
            public void run() {
                try {Thread.sleep(5*1000);}catch(Exception a){}
                method();
            }
         },null,false);
    }

    private static void method() {
        throw new UnsupportedOperationException();
    }
}
