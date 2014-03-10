/*
 *  Copyright (C) 2014 Shashank Tulsyan
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

package neembuu.release1.ui.actions;

import java.awt.event.ActionEvent;
import neembuu.release1.api.VirtualFile;
import neembuu.release1.api.ui.access.ChangeDownloadModeUIA;
import neembuu.release1.api.ui.actions.ChangeDownloadModeAction;
import neembuu.release1.ui.Colors;
import neembuu.vfs.file.SeekableConnectionFile;

/**
 *
 * @author Shashank Tulsyan
 */
public class ChangeDownloadModeActionImpl implements ChangeDownloadModeAction {
    private final ChangeDownloadModeUIA ui;
    
    private VirtualFile vf;
    
    private final String downloadFullFileToolTip = "<html>"
                + "<b>Download entire file mode</b><br/>"
                + "In this mode Neembuu tried to download<br/>"
                + "the entire file without slowing the download speed.<br/>"
                + "</html>";
    
    private final String downloadMinimumToolTip = "<html>"
                + "<b>Download Minimum Mode</b><br/>"
                + "In this mode Neembuu limits the download speed.<br/>"
                + "This mode is useful for people with limited internet<br/>"
                + "usage plans."
                + "</html>";

    public ChangeDownloadModeActionImpl(ChangeDownloadModeUIA ui) {
        this.ui = ui;
    }
    
    @Override
    public boolean isAutoCompleteEnabled() {
        return vf.getConnectionFile().isAutoCompleteEnabled();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ui.changeDownloadModeButton().getModel().isSelected()){
            ui.changeDownloadModeButton().setBackground(Colors.TINTED_IMAGE);
            ui.changeDownloadModeButton().setText("Download Minimum");
            ui.changeDownloadModeButton().setToolTipText(downloadMinimumToolTip);
        }else {
            ui.changeDownloadModeButton().setText("Download Full File");
            ui.changeDownloadModeButton().setBackground(Colors.PROGRESS_BAR_FILL_BUFFER);
            ui.changeDownloadModeButton().setToolTipText(downloadFullFileToolTip);
        }
    
        SeekableConnectionFile file = vf.getConnectionFile();
        file.setAutoCompleteEnabled(!file.isAutoCompleteEnabled());
        
        ui.repaintProgressBar();// the color of progress bar change this needs to be notified.
    }
    
}
