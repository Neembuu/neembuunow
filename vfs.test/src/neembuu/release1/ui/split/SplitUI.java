/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui.split;

import neembuu.release1.api.VirtualFile;
import neembuu.release1.ui.UIAccess;
import neembuu.release1.ui.actions.LinkActionsImpl;

/**
 *
 * @author Shashank Tulsyan
 */
public class SplitUI {
    VirtualFile vf;
    UIAccess uia;
    
    LinkActionsImpl la;
    
    
    public void init(){
        // for 001
        //la.setClose(null);//todo
        
        
        // for 002, 003 ...
        //la.setClose(null);
        ///la.setDelete(null);
        //uia.
        
        
        uia.rightControlsPanel().getCrossBtn().setVisible(false);
        uia.rightControlsPanel().getCrossBtn().setVisible(false);
    }
}
