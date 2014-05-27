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
package neembuu.release1.ui.actions;

import java.util.logging.Level;
import neembuu.release1.Main;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.release1.api.open.OpenerAccess;

/**
 *
 * @author Shashank Tulsyan
 */
public class CloseNeembuuActionImpl {

    private final Main main;
    private final OpenerAccess openerA;

    public CloseNeembuuActionImpl(Main main, OpenerAccess openerA) {
        this.main = main;
        this.openerA = openerA;
    }

    public void actionPerformed() {
        Thread killIfHungThread = new Thread("Closed if hung") {
            @Override
            public void run() {
                try {
                    Thread.sleep(7000);
                } catch (Exception a) {
                }
                System.exit(-1);
            }

        };
        killIfHungThread.start();

        try {
            if (main.getMountManager().getMount() != null) {
                main.getMountManager().getMount().unMount();
            }
        } catch (Exception a) {
            LoggerUtil.L().log(Level.INFO, " ", a);
        }

        try {
            LoggerUtil.L().log(Level.INFO, " Closing all open files");
            openerA.closeAll();
        } catch (Exception a) {
            LoggerUtil.L().log(Level.INFO, " ", a);
        }

        System.exit(0);
    }
}
