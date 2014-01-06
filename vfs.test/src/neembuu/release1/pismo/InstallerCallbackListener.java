/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.pismo;

/**
 *
 * @author Shashank Tulsyan
 */
public interface InstallerCallbackListener {
    void informUserAboutInstallation();
    void installationTakingTooLong(int c);
    void installationSuccessful();
    void installationFailed();
}
