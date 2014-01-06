/*
 * Copyright 2009-2010 Shashank Tulsyan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * File:   ThrottledRealFile.java
 * Author: Shashank Tulsyan
 */
package neembuu.vfs.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import jpfm.DirectoryStream;
import jpfm.SystemUtils;
import jpfm.annotations.MightBeBlocking;
import jpfm.annotations.NonBlocking;
import jpfm.operations.readwrite.ReadRequest;

/**
 *
 * @author Shashank Tulsyan
 */
public class ThrottledRealFile
        extends MonitoredRealFile {

    private int cps;
    public static final int INVALID_CPS = DynamicCPSPauser.INVALID_CPS;
    private final ConcurrentLinkedQueue<ReadRequest> pendingReadRequests = new ConcurrentLinkedQueue<ReadRequest>();
    private final Object lock = new Object();
    private ReadThread readThread = null;
    private long totalRequest = 0;
    private long totalTime = 0;
    private static final boolean showGraph = true;

    @Override
    public synchronized void open() {
        readThread = new ReadThread(super.getName());
        this.readThread.start();
        super.open();
    }

    private final class ReadThread extends Thread {

        public ReadThread(String filename) {
            super("ThrottledRead@" + filename);
        }

        @Override
        public void run() {
            ConcurrentLinkedQueue<ReadRequest> stack = new ConcurrentLinkedQueue<ReadRequest>();
            ReadRequest previous = null;

            // if cascading is allowed the definition
            // of isopen changes 
            // from
            // isOpen()
            // to
            // isOpen() || isOpenByCascading()

            for (; isOpen() || isOpenByCascading();) { //service till pending call present of this file is open
                // intution suggests that close() on file will be called only when there are no pending requests.
                // but let's not make any assumptions.
                Iterator<ReadRequest> it = pendingReadRequests.iterator();
                while (it.hasNext()) {
                    ReadRequest read = it.next();
                    stack.add(read);
                    if (read.isCompleted()) {
                        it.remove();
                        continue;
                    }
                    if (stack.peek().isCompleted()) {
                        if (previous == null) {
                            previous = stack.poll();
                        }

                        totalRequest += stack.peek().getByteBuffer().capacity();
                        totalTime += stack.peek().getCreationTime() - previous.getCompletionTime();



                        /*setRequestSpeed(
                        (stack.peek().getByteBuffer().capacity())
                        /
                        (
                        (stack.peek().getCreationTime() - previous.getCompletionTime())
                         * 1.024d
                        )
                        );*/
                        if (showGraph) {
                            setRequestSpeed(totalRequest / (totalTime * 1.024d));
                            setSupplySpeed(
                                    (previous.getByteBuffer().capacity())
                                    / ((previous.getCompletionTime() - previous.getCreationTime())
                                    * 1.024d));
                        }
                        previous = null;
                    }
                    //if(cps != DynamicCPSPauser.INVALID_CPS)System.out.println("pausing 61");
                    if (isOpen() || isOpenByCascading()) {
                        //pause only if file is open
                        //otherwise just hurriedly complete all pending requests
                        DynamicCPSPauser.pause(cps, read.getByteBuffer().capacity());
                    }
                    //if(read.isCompleted()){it.remove();continue;}
                    try {
                        superRead(read);
                        //object has been send once, it can be removed now
                        //we send requests only once.
                        it.remove();
                        continue;
                    } catch (Exception any) {
                        any.printStackTrace();
                        if (!read.isCompleted()) {
                            read.handleUnexpectedCompletion(any);
                        }
                    }
                }

                synchronized (lock) {
                    try {
                        while (pendingReadRequests.size() == 0
                                && (isOpen() || isOpenByCascading())) {
                            lock.wait();
                        }
                    } catch (InterruptedException exception) {
                    }
                }
            }
        }
    }
    private final PrintWriter supplySpeedList;
    private final PrintWriter requestSpeedList;

    public void setSupplySpeed(double supplySpeed) {
        supplySpeedList.print(supplySpeed);
        supplySpeedList.print(" ");
        supplySpeedList.println(new Date(System.currentTimeMillis()));
        supplySpeedList.flush();
        getFilePanel().setSupplySpeed(supplySpeed);
    }

    public void setRequestSpeed(double requestSpeed) {
        requestSpeedList.print(requestSpeed);
        requestSpeedList.print(" ");
        requestSpeedList.println(new Date(System.currentTimeMillis()));
        requestSpeedList.flush();
        getFilePanel().setRequestSpeed(requestSpeed);
    }

    public ThrottledRealFile(String srcfile, int cps, DirectoryStream fileContainer) throws IOException {
        super(srcfile, fileContainer);
        this.cps = cps;
        //readThread = new ReadThread(srcfile);
        //this.readThread.start();

        if (SystemUtils.IS_OS_WINDOWS) {
            supplySpeedList = new PrintWriter("j:\\neembuu\\graphs\\" + getName() + ".supplySpeed");
        } else if (SystemUtils.IS_OS_LINUX) {
            supplySpeedList = new PrintWriter("/media/j/neembuu/graphs/" + getName() + ".supplySpeed");
        } else // assume mac
        {
            supplySpeedList = new PrintWriter("/Volumes/MIDS/neembuu/graphs/" + getName() + ".supplySpeed");
        }

        if (SystemUtils.IS_OS_WINDOWS) {
            requestSpeedList = new PrintWriter("j:\\neembuu\\graphs\\" + getName() + ".requestSpeed");
        } else if (SystemUtils.IS_OS_LINUX) {
            requestSpeedList = new PrintWriter("/media/j/neembuu/graphs/" + getName() + ".requestSpeed");
        } else // assume mac
        {
            requestSpeedList = new PrintWriter("/Volumes/MIDS/neembuu/graphs/" + getName() + ".requestSpeed");
        }
    }

    @NonBlocking
    @Override
    public void read(ReadRequest read) throws Exception {
        //synchronized (this) {
            /*if (readThread == null) {
        readThread = new ReadThread("ThrottledFile@"+super.getName()); System.out.println("starting new thread");
        readThread.start();
        isOpen = true;
        }*/
        //}
        pendingReadRequests.add(read);
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    @MightBeBlocking
    private void superRead(ReadRequest read) throws Exception {
        super.read(read);
    }

//    @Blocking(getReasonWhyItDoesNotMatter="not used")
//    public int read(long fileOffset, ByteBuffer buffer) {
//        DynamicCPSPauser.pause(cps,buffer.capacity());
//        return super.read(fileOffset, buffer);
//    }
    protected void setCps(int cps) {
        this.cps = cps;
    }

    @Override
    public synchronized void close() {
        if (this.readThread != null) {
            while (this.readThread.isAlive()) {
                synchronized (lock) {
                    lock.notifyAll(); // to ensure that the
                    //thread if not waiting and while it was waiting
                    //the file was closed
                    //we want the thread to move again.
                }
            }
        }
        this.readThread = null;
        super.close();
    }

    public final void printPendingRequests(java.io.PrintStream pst) {
        pst.println("++++++++ Pending requests on " + this + " +++++++++ ");
        Iterator<ReadRequest> it = pendingReadRequests.iterator();
        while (it.hasNext()) {
            pst.println(it.next());
        }
        pst.println("-------- Pending requests on " + this + " --------- ");
    }
    /*@Override
    public String toString() {
    return super.toString();
    }*/

    private static final class DynamicCPSPauser {
        // Conversions for milli and nano seconds

        private static final int MS_PER_SEC = 1000;
        private static final int NS_PER_SEC = 1000000000;
        private static final int NS_PER_MS = NS_PER_SEC / MS_PER_SEC;
        public static final int INVALID_CPS = -1;

        public static void pauseInverse(
                final Object sleepOver,
                double throttleReciprocal,
                int bytes) {
            if (throttleReciprocal <= 0) {
                return;
            }
            if (Double.isInfinite(throttleReciprocal)) {
                throw new IllegalArgumentException("Specifying infinite throttling");
            }
            long sleepMS = (long) ((bytes * MS_PER_SEC) * throttleReciprocal);
            int sleepNS = (int) ((bytes * MS_PER_SEC) * throttleReciprocal) % NS_PER_MS;
            if (sleepMS > 3000) {
                System.err.println("Throttle values exceeds timeout, better to terminate connection, than to leave a deadlock");
                throw new IllegalStateException("Throttle values exceeds timeout, better to terminate connection, than to leave a deadlock");
            }
            try {
                synchronized (sleepOver) {
                    sleepOver.wait(sleepMS, sleepNS);
                }
                //steppedSleep(sleepMS, sleepNS, 200);
            } catch (InterruptedException ignored) {
                ignored.printStackTrace(System.err);
            }
        }

        private static void steppedSleep(
                long sleepMS,
                int sleepNS,
                int atomicMSSleepTime) throws InterruptedException {
            long totalSleep = 0;
            Thread.sleep(0, sleepNS);
            while (totalSleep < sleepMS) {
                Thread.sleep(Math.min(atomicMSSleepTime, sleepMS - atomicMSSleepTime));
                totalSleep += atomicMSSleepTime;
            }
        }

        /**
         * Pause for an appropriate time according to the number of bytes being transferred.
         * If CPS < 0 simply returns
         * @param bytes number of bytes being transferred
         */
        public static void pause(long CPS, int bytes) {
            if (CPS == 0) {
                throw new IllegalArgumentException("CPS has to be greater than or lesser than 0. This exception will kill this connection.");
            }
            if (CPS < 0) {
                return;
            }
            long sleepMS = (bytes * MS_PER_SEC) / CPS;
            int sleepNS = (int) ((bytes * MS_PER_SEC) / CPS) % NS_PER_MS;
            try {
                Thread.sleep(sleepMS, sleepNS);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
