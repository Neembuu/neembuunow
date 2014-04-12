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
package neembuu.vfs.readmanager.rqm;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import jpfm.operations.readwrite.ReadRequest;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.vfs.file.DownloadConstrainHandler;
import neembuu.vfs.file.TroubleHandler;
import neembuu.vfs.readmanager.RegionHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public class MainDirectionThread implements Runnable {
    private volatile boolean complete = false;
    private volatile long lastInternalRequestTime = 0;
    private volatile long mrs = -1, mre = -1;// most recently request start and end
    
    private RegionHandler mdrh = null;// main direction read handler
    
    private int retry = 0;
    private long lastCheckTime = 0;
    private boolean filesPriorToMeCompleted = true;
    private int externalMainCnt = 0;
    
    private static final int DEAD = 0, STARTING = 1, WORKING = 2, STOPPING = 3;
    private final AtomicInteger thread_state = new AtomicInteger(DEAD);
    private final Object sleepLock = new Object();
    private final MainDirectionThread_RQMAccess rQMAccess;

    public MainDirectionThread(MainDirectionThread_RQMAccess rQMAccess) {
        this.rQMAccess = rQMAccess;
    }

    boolean hasAMainConnection(){ return mdrh != null; }

    public boolean isComplete() { return complete; }
    
    void reportMostRecentRequest(ReadRequest read){
        lastInternalRequestTime = read.getCreationTime();
        mrs = read.getFileOffset();
        mre = read.getFileOffset() + read.getByteBuffer().capacity() - 1;
    }
    
    void open() {
        rQMAccess.logger().log(Level.INFO, "opening MainDirectionThread " + s_(), new Throwable());
        synchronized (sleepLock) {
            sleepLock.notifyAll();
        }

        if (thread_state.compareAndSet(DEAD, STARTING)) {
            retry = 0;
            startThread();
        } else {
            if (retry > 10) {
                throw new IllegalStateException("Incorrectly opening. Current state = " + s_());
            }
            try {
                Thread.sleep(150);
            } catch (Exception a) {

            }
            open();
            retry++;
        }
    }

    void close() {
        rQMAccess.logger().log(Level.INFO, "closing MainDirectionThread " + s_(), new Throwable());
        synchronized (sleepLock) {
            sleepLock.notifyAll();
        }

        if (thread_state.compareAndSet(WORKING, STOPPING)) {
            retry = 0;
        } else {
            if (thread_state.get() == DEAD) {
                throw new IllegalStateException("Already dead");
            }
            if (retry > 3) {
                throw new IllegalStateException("Incorrectly closing. Current state = " + s_());
            }
            try {
                Thread.sleep(150);
            } catch (Exception a) {

            }
            close();
            retry++;
        }
    }

    private void startThread() {
        Thread t = new Thread(
                this,
                MainDirectionThread.class.getSimpleName() + "{" + rQMAccess.provider_getName() + "}");
        t.setDaemon(true);
        t.start();
    }

    @Override
    public void run() {
        rQMAccess.logger().info("MainDirectionThread just started " + s_());
        if (!thread_state.compareAndSet(STARTING, WORKING)) {
            throw new IllegalStateException("Thread was not set to starting");
        }

        while (!thread_state.compareAndSet(STOPPING, DEAD)) {
            try {
                IF:
                if (!rQMAccess.autoCompleteEnabled()) {
                    changeMainDirc(906, null);
                } else if (lastInternalRequestTime > rQMAccess.lastExternalRequestTime() ) {
                    if (!complete) {
                        iterateAndFindInternalMain(false);
                    } else {
                        checkExternal();
                        if (externalMainCnt == 0) {
                            rQMAccess.logger().info("Completed but No external main");
                        }
                        if (filesPriorToMeCompleted) {
                            rQMAccess.logger().info("Completed and prior files completed");
                            break IF;
                        } else {
                            rQMAccess.logger().info("Completed but a prior file has not completed yet");
                        }
                    }
                } else {
                    changeMainDirc(945, null);
                    if (!complete) {
                        checkExternal();
                        if (filesPriorToMeCompleted && externalMainCnt == 0) {
                            rQMAccess.logger().info("Prior files completed and not even 1 external main, finding main here");
                            iterateAndFindInternalMain(true);
                            break IF;
                        } else {
                            rQMAccess.logger().info("A prior file has not completed yet");
                        }
                    }
                }
                long sle = 0;
                if (!complete) {
                    sle = 150;
                } else {
                    sle = 1000;
                }
                synchronized (sleepLock) {
                    sleepLock.wait(sle);
                }
                kickStuckConnections();
                findRequestsPendingSinceALongTime();
                checkAndSetComplete_2();
            } catch (Exception a) {
                rQMAccess.logger().log(Level.SEVERE, "", a);
            }
        }
        changeMainDirc(939, null);
        rQMAccess.logger().info("MainDirectionThread dead " + s_());
    }

    private void checkExternal() {
        int ind = 0;
        externalMainCnt = 0;
        filesPriorToMeCompleted = true;
        FOR:
        for (DownloadConstrainHandler dch : rQMAccess.constraintedWith() ) {
            if (dch.hasAMainConnection()) {
                externalMainCnt++;
            }
        }
        boolean dch_in_is_first_split = true;
        FOR:
        for (DownloadConstrainHandler dch : rQMAccess.constraintedWith() ) {
            if (dch.index() < rQMAccess.myDch_index()) {
                dch_in_is_first_split = false;
                break;
            }
        }
        if (dch_in_is_first_split) {
            filesPriorToMeCompleted = true;
            return;
        }

        FOR:
        for (DownloadConstrainHandler dch : rQMAccess.constraintedWith() ) {
            if (dch.index() < rQMAccess.myDch_index()) {
                if (!dch.isComplete()) {
                    //if(!dch.hasAMainConnection()){
                    filesPriorToMeCompleted = false;
                    rQMAccess.logger().info("File not completed " + dch);
                    break FOR;
                    //}
                }
            }
            ind++;
        }
    }
    
    private void kickStuckConnections(){
        if(true)return;
        UnsyncRangeArrayCopy<RegionHandler> uh
                = rQMAccess.handlers_tryToGetUnsynchronizedCopy();
        long atlestMillisec = Math.min(
                TroubleHandler.DEFAULT_PENDING_ATLEAST_FOR_MILLISECONDS,
                rQMAccess.provider_getTroubleHandler().pendingAtleastFor());
        LinkedList<ReadRequest> pendingRrs = new LinkedList<ReadRequest>();
        // concept of main should be in favour of those connection that are starving
        for (int i = 0; i < uh.size(); i++) {
            RegionHandler rh = uh.get(i).getProperty();
            if (rh!=null &&  rh.hasPendingRequests()) {// when state is resumed, older strips have null RegionHandler (maybe)
                rh.fillInOperationsPendingSince(pendingRrs, atlestMillisec);
            }
        }
    }

    private void findRequestsPendingSinceALongTime() {
        if (System.currentTimeMillis() < lastCheckTime
                + Math.min(
                        TroubleHandler.DEFAULT_CHECKING_INTERAL_MILLISECONDS,
                        rQMAccess.provider_getTroubleHandler().preferredCheckingInterval())  ) {
            return;
        }
        lastCheckTime = System.currentTimeMillis();
        UnsyncRangeArrayCopy<RegionHandler> uh
                = rQMAccess.handlers_tryToGetUnsynchronizedCopy();
        long atlestMillisec = Math.min(
                TroubleHandler.DEFAULT_PENDING_ATLEAST_FOR_MILLISECONDS,
                rQMAccess.provider_getTroubleHandler().pendingAtleastFor());
        LinkedList<ReadRequest> pendingRrs = new LinkedList<ReadRequest>();
        // concept of main should be in favour of those connection that are starving
        for (int i = 0; i < uh.size(); i++) {
            RegionHandler rh = uh.get(i).getProperty();
            if (rh!=null &&  rh.hasPendingRequests()) {// when state is resumed, older strips have null RegionHandler (maybe)
                rh.fillInOperationsPendingSince(pendingRrs, atlestMillisec);
            }
        }
        if (!pendingRrs.isEmpty()) {
            rQMAccess.provider_getTroubleHandler().readRequestsPendingSinceALongTime(pendingRrs, atlestMillisec);
        }
    }

    private void iterateAndFindInternalMain(boolean priorCompleted) {
        UnsyncRangeArrayCopy<RegionHandler> uh
                = rQMAccess.handlers_tryToGetUnsynchronizedCopy();
            //Logic is 
        //where the most recent requests are present that is the main connection.
        //is that connection has collided we try the next one.
        //if the request start and end don't lie in the same connection.
        //it means it is a complex splitted request, don't set main conn in this case
        // to prevent starvation of splits
        // UPDATE : we are checking only ending now. Because that is a more common case
        // UPDATE : we are now checking starting as well, and comparing against authority limit
        RegionHandler found = null;
        boolean reachedEnd = false;

        // concept of main should be in favour of those connection that are starving
        for (int i = 0; i < uh.size(); i++) {
            RegionHandler rh = uh.get(i).getProperty();
            if (rh == null) {
                continue;
            }

            if (uh.size() > i + 1 && uh.get(i).ending() + 1 == uh.get(i + 1).starting()) {
                // collided
            } else if (i == uh.size() - 1 && rh.ending() >= uh.get(i).getProperty().fileSize() - 1) {
                //collided
            } else if (rh.hasPendingRequests()) {
                found = rh;
                changeMainDirc(962, found);
                return;
            }
        }

        OUTER_LOOP:
        for (int i = 0; i < uh.size(); i++) {
            if (mrs <= uh.get(i).getProperty().authorityLimit() + 1 && mre >= uh.get(i).starting()) {
                INNER_LOOP:
                for (int j = i; j < uh.size(); j++) {
                    if (uh.size() > j + 1 && uh.get(j).ending() + 1 == uh.get(j + 1).starting()) {
                        // collided
                    } else if (j == uh.size() - 1 && uh.get(j).ending() >= uh.get(j).getProperty().fileSize() - 1) {
                        reachedEnd = true;
                    } else {
                        found = uh.get(j).getProperty();
                        if (found.ending() + 1 >= found.fileSize()) {
                            reachedEnd = true;
                            found = null;
                            break OUTER_LOOP;
                        }
                        break OUTER_LOOP;
                    }
                }
            }
        }

        if (reachedEnd || priorCompleted) {
            found = checkAndSetComplete_1();
        }

        changeMainDirc(998, found);
    }

    private RegionHandler checkAndSetComplete_1() {
        UnsyncRangeArrayCopy<RegionHandler> uh
                = rQMAccess.handlers_tryToGetUnsynchronizedCopy();
        RegionHandler found = null;

            // The first main connection that we make should 
        // start from zero. If there is no connection at zero
        // we cannot set it to main. So the first thing to
        // ensure is that a region must exist at zero.
        if (uh.size() == 0 || uh.get(0).starting() > 0) {
                // the problem is there is no connection starting from offset 0
            // we cannot create one, we can only set main

                // read request must be atleast along 1 of them files
            // handleConstrainedRQMs does this for us
            // one the file will sense this flag and do the appropriate
            rQMAccess.logger().log(Level.INFO, "We require a connection from offset 0");
            if (!rQMAccess.message_requiredConnectionAtZero()) {
                rQMAccess.logger().log(Level.INFO, "Request for connection was already send");
            }
            return null; // we shall wait for zero region to be created.
            // then we can set it as main :) using the code below
        }

        OUTER_LOOP:
        for (int i = 0; i < uh.size(); i++) {
            INNER_LOOP:
            for (int j = i; j < uh.size(); j++) {
                if (uh.size() > j + 1 && uh.get(j).ending() + 1 == uh.get(j + 1).starting()) {
                    // collided
                } else if (j == uh.size() - 1 && uh.get(j).ending() >= uh.get(j).getProperty().fileSize() - 1) {
                    //collided
                } else {
                    // setting first gap as main, we are filling gaps now
                    found = uh.get(j).getProperty();
                    rQMAccess.logger().log(Level.INFO, "MainDirectionThread filling gap " + found);
                    if (found.ending() + 1 >= found.fileSize()) {
                        rQMAccess.logger().log(Level.INFO, "Looks like entire file downloaded cannot set main");
                        found = null;
                        complete();
                    }
                    break OUTER_LOOP;
                }
            }
        }
        return found;
    }

    private void checkAndSetComplete_2() {
        UnsyncRangeArrayCopy<RegionHandler> uh
                = rQMAccess.handlers_tryToGetUnsynchronizedCopy();
        int i = 0;
        if (uh.size() == 0 || uh.get(0).starting() > 0) {
            return;//there must be an entry starting from zero
        }
        OUTER_LOOP:
        /*for (int i = 0; i < uh.size(); i++) {
         INNER_LOOP:*/
        for (int j = i; j < uh.size(); j++) {
            if (uh.size() > j + 1 && uh.get(j).ending() + 1 == uh.get(j + 1).starting()) {
                // collided
            } else if (j == uh.size() - 1 && uh.get(j).ending() >= uh.get(j).getProperty().fileSize() - 1) {
                //collided
                rQMAccess.logger().log(Level.INFO, "Looks like entire file downloaded cannot set main");
                complete();
            } else {
                break OUTER_LOOP;
            }
        }
        //}
    }

    private void complete() {
        if (!complete) {
            rQMAccess.logger().log(Level.INFO, "File Completed");
            complete = true;
            rQMAccess.notifyDownloadComplete();
        }
        changeMainDirc(1146, null);
    }

    private void changeMainDirc(int l, RegionHandler rh) {
        if (rh != mdrh) {
            rQMAccess.logger().info("changing main to " + rh + " l=" + l);
        }

        if (mdrh != null) {
            mdrh.setMainDirectionOfDownload(false);
        }
        mdrh = rh;
        if (rh != null) {
            rh.setMainDirectionOfDownload(true);
        }
    }

    private String s_() {
        switch (thread_state.get()) {
            case 0:
                return "DEAD";
            case 1:
                return "STARTING";
            case 2:
                return "WORKING";
            case 3:
                return "STOPPING";
            default:
                throw new AssertionError();
        }
    }
}
