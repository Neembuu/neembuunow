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

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.Message;
import neembuu.release1.ui.mc.MainComponentImpl;
import neembuu.util.Throwables;

/**
 *
 * @author Shashank Tulsyan
 */
public class SingleInstanceCheckCallbackImpl implements SingleInstanceCheckCallback {

    private final Logger l = LoggerUtil.getLogger(SingleInstanceCheckCallback.class.getName());
    
    private final LinkedList<RunAttemptListener> runListeners = new LinkedList<>();
    private final LinkedList<RunningStateListener> alreadyListeners = new LinkedList<>();
    
    public SingleInstanceCheckCallbackImpl() {
    }
        
    @Override
    public void alreadyRunning(final long timeSince) {
        /*if (true) { return;}*/
        MainComponent mc = new MainComponentImpl(new javax.swing.JFrame());
        Object[]options={"Yes","No"};
        Object response = mc.newMessage().setTitle("An instance is already running")
                .setMessage("Opening two instances of neembuu might result\n"
                        + "in undesirable behavior.\n"
                        + "This instance of neembuu will close in a few seconds.\n"
                        + "\n"
                        + "If you really want to run this instance,\n"
                        + "press No to keep this instance running.")
                .setTimeout(10000)
                .setPreferredLocation(Message.PreferredLocation.OnTopOfAll)
                .ask(options,0);
        if (response==options[0] || response!=options[1]) {
            System.exit(0);
        } else {
            l.info("User chose to run another instance, and so shall it be.");
            
            forwardAlreadRunningNotification(timeSince,true);
            
            throw new RuntimeException("User chose to keep running");
            /*while (true) {
                l.info("working in infinite loop");
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                }
            }*/
        }
        
        
    }
    
    private void forwardAlreadRunningNotification(final long timeSince,
            final boolean alreadyRunning){
        final RunningStateListener[]arls;
        synchronized (alreadyListeners){
            arls = alreadyListeners.toArray(new RunningStateListener[alreadyListeners.size()]);
        }
        
        Throwables.start(new Runnable() { @Override public void run() {
            for (RunningStateListener arl : arls) {try{
                if(alreadyRunning){
                    arl.alreadyRunning(timeSince);
                }else {
                    arl.solelyRunning(timeSince);
                }
            }catch(Exception a){l.log(Level.INFO,"could not notify",a);}}
        }}, "Already running since notification", true);
    }

    @Override
    public void attemptedToRun(final long time) {
        final RunAttemptListener[]rals;
        synchronized (runListeners){
            rals = runListeners.toArray(new RunAttemptListener[runListeners.size()]);
        }
        
        Throwables.start(new Runnable() { @Override public void run() {
            for (RunAttemptListener ral : rals) {try{
                ral.attemptedToRun(time);
            }catch(Exception a){l.log(Level.INFO,"could not notify",a);}}
        }}, "Attempted to run notification thread", true);
        
    }

    @Override
    public boolean solelyRunning(long time) {
        l.log(Level.INFO, "solely running @ {0}", time);
        
        forwardAlreadRunningNotification(time, false);
        
        return true;
    }

    @Override
    public void addRunAttemptListener(RunAttemptListener ral) {
        synchronized (runListeners){
            runListeners.add(ral);
        }
    }

    @Override
    public void addAlreadyRunningListener(RunningStateListener arl) {
        synchronized (runListeners){
            alreadyListeners.add(arl);
        }
    }

}
