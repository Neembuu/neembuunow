/*
 *  Copyright (C) 2010 Shashank Tulsyan
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

package neembuu.util.logging;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *
 * @author Shashank Tulsyan
 */
public final class LightWeightLogger extends Logger {

    //final static BufferedWriter bw = new FinalBW();
    private static final LinkedBlockingQueue<L> lbq = new LinkedBlockingQueue<L>();
    private static final Flusher F = new Flusher();
    static AtomicInteger pendingRqs = new AtomicInteger(0);
    
    static AtomicLong totalTime = new AtomicLong(0);
    static AtomicLong totalCnt = new AtomicLong(0);
    
    static {
        if(false)staticinit();
        F.start();
    }
    
    private static void staticinit(){

    }
            
    private static final class L {
        private final LogRecord lr;
        private final LightWeightLogger lwl;

        public L(LogRecord lr, LightWeightLogger lwl) {
            this.lr = lr;
            this.lwl = lwl;
        }
    }
    
    /*package private*/ LightWeightLogger(String name) {
        super(name,Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).getResourceBundleName());
    }

    @Override
    public void log(LogRecord record) {
        pendingRqs.incrementAndGet();
        record.getSourceMethodName();
        if(!lbq.offer(new L(record, this))){
            throw new IllegalStateException("could not offer");
        }
    }
    
    private void superLog(LogRecord record){
        super.log(record);
    }

    private static final class Flusher extends Thread {
        public Flusher() {
            super("LightWeightLogger  flusher");
            setDaemon(true);
        }
        
        @Override
        public final void run()  {
            L l  = null;
            long s,e;
            try{
                while(true){
                    while((l = lbq.poll(1000, TimeUnit.MILLISECONDS))!=null){
                        s = System.currentTimeMillis();
                        try{
                            l.lwl.superLog(l.lr);
                        }catch(LogRecordContainingException lrce){
                            Logger.getGlobal().log(Level.SEVERE,"Following log message could not be written to destination file");
                            Logger.getGlobal().log(lrce.lr);
                        }catch(Exception a){
                            Logger.getGlobal().log(Level.SEVERE,"",a);
                        }
                        e = System.currentTimeMillis();

                        totalTime.addAndGet(e-s);
                        totalCnt.incrementAndGet();

                        pendingRqs.decrementAndGet();
                    }
                    if(exit()){
                        return;
                    }
                }
            }catch(InterruptedException ie){
                ie.printStackTrace(System.err);
            }
        }
        
        private boolean exit(){
            if(Thread.currentThread().isDaemon()){
                // daemon thread will exit on it's own
                // no need to check
                return false;
            } 
            ThreadGroup tg = Thread.currentThread().getThreadGroup();
            while(tg.getParent()!=null){
                tg = tg.getParent();
            }
            
            Thread[]lst = new Thread[tg.activeCount()];
            tg.enumerate(lst, true);

            for (int i = 0; i < lst.length; i++) {
                if(!lst[i].isDaemon()){
                    if(lst[i]!=Thread.currentThread()){
                        if(lst[i].getName().equals("DestroyJavaVM"))
                            continue;
                        return false;
                    }
                }
            }
            return true;
        }

    }

}

