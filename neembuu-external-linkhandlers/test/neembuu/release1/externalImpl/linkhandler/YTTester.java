/*
 * Copyright (C) 2014 davidepastore
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

package neembuu.release1.externalImpl.linkhandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.api.file.OnlineFile;
import neembuu.release1.api.linkhandler.LinkHandler;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.log.LoggerServiceProvider;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.util.Throwables;
import neembuu.vfs.connection.Connection;
import neembuu.vfs.connection.NewConnectionParams;
import neembuu.vfs.connection.TransientConnectionListener;
import neembuu.vfs.progresscontrol.Throttle;
import neembuu.vfs.progresscontrol.ThrottleState;
import neembuu.vfs.progresscontrol.ThrottleStatistics;
import neembuu.vfs.readmanager.ConnectionControls;
import neembuu.vfs.readmanager.DownloadDataChannel;
import neembuu.vfs.readmanager.ReadRequestState;
import neembuu.vfs.readmanager.TotalFileReadStatistics;

/**
 * Check 3 things:
 * (1)link is connectable or not
 * (2) the file size
 * (3) seeking works or not
 * @author davidepastore
 */
public class YTTester {
    public static void main(String[] args) throws Exception {
        final Logger logger = Logger.getLogger("log");
        LoggerUtil.setServiceProvider(new LoggerServiceProvider() {
            @Override public Logger getLogger(String name, boolean console, boolean file) {
                //return Logger.getLogger(name);
                return logger;
            }
        });
        
        
        SaveVideoYoutubeLinkHandlerProvider ylhp = new SaveVideoYoutubeLinkHandlerProvider();
        
        TrialLinkHandler tlh = ylhp.tryHandling("http://www.youtube.com/watch?v=sFymchGbG1w");
        
        LinkHandler lh = ylhp.getLinkHandler(tlh);
        
        for (OnlineFile file : lh.getFiles()) {
            // the logger that you are passing here
            // you may passa  different logger which logs nothing
            /// if the logs of other classes five you trouble.
            /// for now leave it as it is .
            test(logger, 0, file); // A test would succeed only if seeking is also working..
            // but the problem since we are writing to the System.out
            // both connections would write togtether and we will not know
            // which data is coming from which connection.
            // sometimes youtube ignores the Seek request and gives data from 0 file location
            // ok, questions please ??? yeah, my problem was: I want to test using the UI
            // of Neembuu...in other words I'd like to get the logger content of youtubelinkshandler
            // from the Neembuu Now central application.
            // did you test the logger using this method?
            
            // 2 things (1) using central application to do testing will slow you down very badly
            // (2) the logs are kept in different files. let me show u 
            // (3) btw, this youtube link handler is not connected with the central application
            //     so for a few days you cannot do that kidn of testing.
            ///    ok, do this, wait
            
            //test(logger, (long)(Math.random()*1024*1024), file); // if we want to see if seek works
        }    
    }
    
    private static void test(final Logger logger, final long offset, OnlineFile file){
            file.getConnectionProvider().provideNewConnection(new NewConnectionParams.Builder()
                    .setDownloadDataChannel(new DownloadDataChannel() {
                        @Override public boolean isAutoHandleThrottlingEnabled() {return true; /*of no use during testing*/}
                        @Override public void setAutoHandleThrottlingEnabled(boolean ate) {}
                        @Override public int write(ByteBuffer src) throws IOException {
                            System.out.println(new String(src.array())); // the downloaded data comes here
                            return src.limit();
                        }
                        @Override public boolean isOpen() {return true;} // are you there ? y
                        @Override public void close() throws IOException {}
                    }).setOffset(offset)
                    .setDownloadThreadLogger(logger)
                    .setTransientConnectionListener(new TransientConnectionListener() {
                        @Override public void describeState(String state) { logger.info("state="+state); }
                        @Override public void reportNumberOfRetriesMadeSoFar(int numberOfretries) {
                            logger.info("retries so far ="+numberOfretries);
                        }
                        @Override public void successful(final Connection c, NewConnectionParams ncp) {
                            logger.log(Level.INFO, "success making new connection {0}",c);
                            Throwables.start(new Runnable() {
                                @Override public void run() {
                                    try{Thread.sleep(2000);}catch(Exception a){}
                                    c.abort();
                                }
                            }, "Kill connection after sometime to avoid flooding standard output");
                        }
                        @Override public void failed(Throwable reason, NewConnectionParams ncp) {
                            logger.log(Level.SEVERE, "failed making new connection",reason);
                        }
                    })
                    .setThrottle(new Throttle() { // it basically controls speed, so that downloading 
                        // is not done where there is no read request.
                        // it also kills connections which are not required
                        // we don't need throttling in our test code
                        @Override public void initialize(NewConnectionParams cp) { }
                        @Override public void markDead(NewConnectionParams cp) { }
                        @Override public NewConnectionParams getConnectionParams() { throw new NullPointerException() ;}
                        @Override public double getWaitIntervalPerByte() { return 0; }
                        @Override public void wait(double waitIntervalPerByte, int numberOfBytesDownloaded) { }
                        @Override public long lastRequestTime() { return System.currentTimeMillis(); }
                        @Override public boolean pendingRequestsPresentOutside() { return false; }
                        @Override public long requestDownloadGap() { return 0; }
                        @Override public boolean requestsPresentAlongThisConnection() { return true; }
                        @Override public long closestAliveRegionEnding() { return 0; }
                        @Override public double averageDownloadSpeed_Bps() {return 0;}
                        @Override public double getDownloadSpeed_KiBps() { return 0; }
                        @Override public double getRequestSpeed_KiBps() { return 0; }
                        @Override public ThrottleState getThrottleState() { return ThrottleState.NOT_THROTTLING;  }
                        @Override public void downloadProgressed(long start, long oldEnd, long dx) {} 
                        @Override public void requestCreated(long downloadedRegionStart, long downloadedRegionEnd, long requestFileOffset, int requestSize, long creationTime) {                }
                        @Override public void requestCompleted(long downloadedRegionStart, long downloadedRegionEnd, long requestFileOffset, int requestSize, long completionTime) {}
                    })
                    .setReadRequestState(new ReadRequestState() {
                        // totally dummy code, shorten it later for simplyfying as i did above
                        @Override
                        public long starting() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public long ending() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public long authorityLimit() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public long fileSize() {
                            return Long.MAX_VALUE ; // either manually specify the size, or put junk value 
                        }

                        @Override
                        public String getFileName() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public TotalFileReadStatistics getTotalFileReadStatistics() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public boolean isAlive() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public ThrottleStatistics getThrottleStatistics() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public int numberOfPendingRequests() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public boolean hasPendingRequests() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public String tryGettingPendingRequestsList() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public boolean isMainDirectionOfDownload() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public ConnectionControls getConnectionControls() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    })
                    .build()
            );
    }
}
