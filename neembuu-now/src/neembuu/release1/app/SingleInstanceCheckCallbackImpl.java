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

import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.ui.mc.MainComponentImpl;

/**
 *
 * @author Shashank Tulsyan
 */
public class SingleInstanceCheckCallbackImpl implements SingleInstanceCheckCallback {
    private final DirectoryWatcherService watcherService;
        
    public SingleInstanceCheckCallbackImpl(DirectoryWatcherService watcherService) {
        this.watcherService = watcherService;
    }

    @Override
    public void alreadyRunning(long timeSince) {
        if (true) {
            return;
        }
        MainComponent mc = new MainComponentImpl(new javax.swing.JFrame());
        boolean x = mc.newMessage().setTitle("An instance is already running")
                .setMessage("Opening two instances of neembuu might result\n"
                        + "in undesirable behavior.\n"
                        + "This instance of neembuu will close in a few seconds.\n"
                        + "\n"
                        + "If you really want to run this instance,\n"
                        + "press No to keep this instance running.")
                .setTimeout(10000)
                .ask();
        if (x) {
            System.exit(0);
        } else {
            System.out.println("User chose to run another instance, and so shall it be.");
            while (true) {
                System.out.println("working in infinite loop");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public void attemptedToRun(long time) {
        watcherService.forceRescan(time);
    }

    @Override
    public boolean solelyRunning(long time) {
        System.out.println("solely running @ "+time);
        return true;
    }

}
