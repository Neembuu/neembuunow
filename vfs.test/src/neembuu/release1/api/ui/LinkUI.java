/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api.ui;

import javax.swing.JComponent;
import neembuu.release1.api.VirtualFile;

/**
 *
 * @author Shashank Tulsyan
 */
public interface LinkUI {
    VirtualFile getVirtualFile();
    JComponent getJComponent();
    
    void initLinkUIContainer(LinkUIContainer luic);
    void uninitLinkUIContainer(LinkUIContainer luic);
    
    int getH(double f);
    int getMinH();
    
    //void deactivateOpenButton(boolean deactivate);
    
    void setContraintComponent(JComponent contraint);
    JComponent getContraintComponent();
}
