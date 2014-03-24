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
package neembuu.release1.mountmanager;

import javax.swing.JOptionPane;
import neembuu.release1.Application;
import neembuu.release1.api.IndefiniteTask;
import neembuu.release1.api.ui.IndefiniteTaskUI;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.pismo.InstallerCallbackListener;
import neembuu.release1.ui.InstallPermissionAndProgress;

/**
 *
 * @author Shashank Tulsyan
 */
public class ICBL implements InstallerCallbackListener {

    private final MainComponent mainComponent;
    private final IndefiniteTaskUI indefiniteTaskUI;

    public ICBL(MainComponent mainComponent, IndefiniteTaskUI indefiniteTaskUI) {
        this.mainComponent = mainComponent;
        this.indefiniteTaskUI = indefiniteTaskUI;
    }
    
    private IndefiniteTask installationProgress = null;
    private IndefiniteTask installationTakingTooLong = null;

    @Override
    public void informUserAboutInstallation() {
        InstallPermissionAndProgress.showMessage(mainComponent);
        installationProgress = indefiniteTaskUI.showIndefiniteProgress("Installing PismoFileMount");
    }

    @Override
    public void installationTakingTooLong(int c) {
        if (c < 60) {
            if (installationTakingTooLong != null) {
                installationTakingTooLong.done();
            }
            installationTakingTooLong = indefiniteTaskUI.showIndefiniteProgress("Installation is taking longer than usual : " + c + "seconds ellapsed");
        } else if (c > 60 && c < 90) {
            if (installationTakingTooLong != null) {
                installationTakingTooLong.done();
            }
            installationTakingTooLong = indefiniteTaskUI.showIndefiniteProgress("Neembuu will quitting if it takes longer than 2mins to install this");
        } else if (c > 90) {
            JOptionPane.showMessageDialog(mainComponent.getJFrame(), "Installation of Pismo failed or stuck.", "Application will exit", JOptionPane.ERROR_MESSAGE);
            System.exit(c);
        }
    }

    @Override
    public void installationSuccessful() {
        if (installationTakingTooLong != null) {
            installationTakingTooLong.done();
        }
        if (installationProgress != null) {
            installationProgress.done();
        }
    }

    @Override
    public void installationFailed() {
        java.io.File logFilePath = Application.getResource("install_logs.txt");
        String logfileText = "The log file mentions the exact reason.";
        try {
            java.awt.Desktop.getDesktop().open(logFilePath);
        } catch (Exception a) {
            logfileText = logfileText + "\n" + logFilePath.getName();
        }
        JOptionPane.showMessageDialog(mainComponent.getJFrame(),
                "Your sandboxing or antivirus environment \n"
                + "might be preventing installation.\n"
                + logfileText,
                "Installation failed. Neembuu will exit", JOptionPane.ERROR_MESSAGE);
        System.exit(-1);
    }
}
