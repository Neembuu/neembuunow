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
package neembuu.release1.app;

/**
 *
 * @author Shashank Tulsyan
 */
    public class ThreadTest {

        public void executeNonBlocking() {
            final StackTraceElement[] callerStackTrace = new Throwable().getStackTrace();
            new Throwable().printStackTrace();
            Thread t = new Thread("executeNonBlocking") {
                @Override public void run() {
                    // What would be a good way to 
                    // append callerStackTrace to the stack
                    // trace of this thread
                    System.out.println("inside");
                    new Throwable().printStackTrace();
                }
            };
            t.start();
        }

        public static void main(String[] args) {
            new ThreadTest().executeNonBlocking();
        }
    }
