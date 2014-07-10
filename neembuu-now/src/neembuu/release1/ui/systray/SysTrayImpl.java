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

package neembuu.release1.ui.systray;

import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.release1.api.ui.MinimalistMessage;
import neembuu.release1.api.ui.systray.SysTray;
import neembuu.util.Throwables;

/**
 *
 * @author Shashank Tulsyan
 */
public class SysTrayImpl implements SysTray {
    private PopupMenu popup;
    private TrayIcon trayIcon;
    private volatile Callback defaultAction = null;
    
    public SysTrayImpl() {
    }

    @Override final public MinimalistMessage newMessage() {
        return new SysTrayMessage(trayIcon);
    }
    
    @Override final public Removable setDefaultAction(final Callback c){
        Callback defaultAction_copy = defaultAction;
        if(defaultAction_copy!=null)throw new IllegalStateException("Already initialized with "+defaultAction_copy);
        defaultAction = c;
        return new Removable() {
            @Override public void remove() {
                Callback defaultAction_copy = defaultAction;
                if(defaultAction_copy==c){
                    defaultAction = null;
                }
            }
        };
    }
    
    public final void initTray() {
        popup = new PopupMenu();
        trayIcon = new TrayIcon(
            new ImageIcon(SysTrayImpl.class.getResource("/neembuu/release1/ui/images/7_ls_small_transparent_16x16.png")).getImage(),
            "NeembuuNow",popup);
        trayIcon.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                Callback defaultAction_copy = defaultAction;
                if(defaultAction_copy!=null){
                    defaultAction_copy.actionPerformed();
                }
            }
        });
        Throwables.start(new Runnable() {
            @Override public void run() {
                long startTime = System.currentTimeMillis();
                boolean done = false;
                while(!done){
                    try {
                        SystemTray.getSystemTray().add(trayIcon);
                        done = true;
                    } catch (Exception e) {
                        LoggerUtil.L().log(Level.SEVERE,"Could not make system tray",e);
                    } try { Thread.sleep(1000); } catch (Exception e) {}
                    if(System.currentTimeMillis()-startTime > 20000){
                        done=true;
                    }
                }
            }
        }, "System tray init", true);
    }

    @Override
    public Action newAction() {
        return new SysTrayAction(popup);
    }

}
