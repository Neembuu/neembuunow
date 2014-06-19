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

import java.awt.TrayIcon;
import neembuu.release1.api.ui.MinimalistMessage;

/**
 *
 * @author Shashank Tulsyan
 */
public class SysTrayMessage implements MinimalistMessage {

    private final TrayIcon i;
    
    private TrayIcon.MessageType mt = TrayIcon.MessageType.NONE;
    private String title = "Neembuu Now";
    private String message;

    public SysTrayMessage(TrayIcon i) {
        this.i = i;
    }
    
    @Override
    public MinimalistMessage error() {
        mt = TrayIcon.MessageType.ERROR;
        return this;
    }
    
    @Override
    public MinimalistMessage warning() {
        mt = TrayIcon.MessageType.WARNING;
        return this;
    }

    @Override
    public MinimalistMessage info() {
        mt = TrayIcon.MessageType.INFO;
        return this;
    }

    @Override
    public MinimalistMessage setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public MinimalistMessage setMessage(String message) {
        this.message = message;
        return this;
    }
    
    @Override
    public void show() {
        if(i==null)return;
        i.displayMessage(title, message, mt);
    }

}
