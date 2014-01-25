/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class DownloadModeChangeImpl implements ChangeDownloadModeAction {
    private ChangeDownloadModeUIA ui;
    
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
    
    @Override
    public boolean isAutoCompleteEnabled() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
