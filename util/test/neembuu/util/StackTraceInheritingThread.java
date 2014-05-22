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

/**
 *
 * @author Shashank Tulsyan
 */
public class StackTraceInheritingThread {

    private final Runnable r;

    private volatile Thread th = null;
    private String title;
    private boolean daemon;

    private InheritedStackTrace ist;

    private static final ThreadLocal<InheritedStackTrace> tl = new ThreadLocal<InheritedStackTrace>();

    public StackTraceInheritingThread(Runnable r) {
        this.r = r;
    }

    private final class StackTraceInheritingUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            if (ist != null) {
                e.addSuppressed(ist);
            }
            e.printStackTrace(System.err);
        }

    }

    public StackTraceInheritingThread setName(String nm) {
        this.title = nm;
        return this;
    }

    public StackTraceInheritingThread setDaemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    public Thread start() {
        if (th != null) {
            throw new IllegalStateException("Already started");
        }

        th = new Thread(new Runnable() {
            @Override
            public void run() {
                tl.set(ist);
                r.run();
            }
        }, title);
        th.setUncaughtExceptionHandler(new StackTraceInheritingUncaughtExceptionHandler());
        if (daemon) {
            th.setDaemon(true);
        }
        ist = new InheritedStackTrace();
        th.start();
        return th;
    }

    public static Throwable getInheritedStackTrace() {
        return tl.get();
    }
    
    public static void printInheritedStackTrace() {
        Throwable throwable = getInheritedStackTrace();
        if (throwable == null) {
            return;
        }
        throwable.printStackTrace();
    }

    public static StackTraceInheritingThread make(Runnable r1) {
        return new StackTraceInheritingThread(r1);
    }

    private static final class InheritedStackTrace extends Exception {

    }

    public static void main(String[] args) {
        StackTraceInheritingThread.make(new Runnable() {

            @Override
            public void run() {
                System.out.println("heelo");
                throw new RuntimeException();
            }
        }).setName("ExperimentalThread").start();
    }
}
