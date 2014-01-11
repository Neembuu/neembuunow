/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api;

import java.util.List;
import neembuu.release1.MountManager;

/**
 *
 * @author Shashank Tulsyan
 */
public interface LinkGroupProcessor {
    List<VirtualFile> canHandle(List<VirtualFile> sessions);

    void handle(List<VirtualFile> sessions,MountManager mountManager);
    
    void openSuitableFile(List<VirtualFile> sessions,MountManager mountManager);
}
