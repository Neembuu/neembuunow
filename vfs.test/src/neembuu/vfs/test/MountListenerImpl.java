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

package neembuu.vfs.test;

import java.awt.Desktop;
import java.io.File;
import javax.swing.JOptionPane;
import jpfm.FormatterEvent;
import jpfm.MountListener;

/**
 *
 * @author Shashank Tulsyan
 */
public class MountListenerImpl implements MountListener{

    private final String mountLocation;
    private final FrameProvider fp;

    public MountListenerImpl(String mountLocation, FrameProvider fp) {
        this.mountLocation = mountLocation;
        this.fp = fp;
    }
    
    @Override
    public void eventOccurred(FormatterEvent event) {
               if(event.getEventType()==FormatterEvent.EVENT.SUCCESSFULLY_MOUNTED){
            try{
                Desktop.getDesktop().open(new File(mountLocation));
            }catch(Exception ioe){
                JOptionPane.showMessageDialog(fp.getJFrame(),
                        "Could not open mount location "+mountLocation+"\n"
                        + "This happens in platforms that do no support java.awt.Desktop.getDesktop.open()"
                        + "\n Reasons : "
                            +ioe.getMessage()
                        ,"Could not open mount location"
                        ,JOptionPane.ERROR_MESSAGE);
                        //,GlobalTestSettings.ONION_EMOTIONS.I_AM_DEAD);
            }
        }
    }
    
}
