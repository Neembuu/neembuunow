/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui;

import javax.swing.JComponent;
import neembuu.release1.MountManager;
import neembuu.release1.api.LinkUI;
import neembuu.release1.api.LinkUIContainer;
import neembuu.release1.api.VirtualFile;

/**
 *
 * @author Shashank Tulsyan
 */
public final class SingleFileLinkUI implements LinkUI{
    
    private final LinkPanel lp;
    
    private LinkUIContainer luic;
    
    private VirtualFile virtualFile;
    
    private final NeembuuUI neembuuUI;
    private final MountManager mountManager;
    
    private JComponent contraint;

    public SingleFileLinkUI(NeembuuUI neembuuUI, MountManager mountManager) {
        lp = new LinkPanel(this);
        this.neembuuUI = neembuuUI;
        this.mountManager = mountManager;
        
    }
    
    public void init(VirtualFile virtualFile){
        this.virtualFile = virtualFile;
        lp.setFile();
    }

    @Override
    public void deactivateOpenButton(boolean deactivate) {
        //lp.fileIconPanel.openButton.setEnabled(!deactivate);
        //lp.fileIconPanel.openButton.setClickable(!deactivate);
        lp.fileIconPanel.openButton.setVisible(!deactivate);
    }

    @Override
    public VirtualFile getVirtualFile() {
        return virtualFile;
        //return lp.vf;
    }

    @Override
    public JComponent getJComponent() {
        return lp;
    }

    @Override
    public void setContraintComponent(JComponent contraint) {
        this.contraint = contraint;
    }

    @Override
    public JComponent getContraintComponent() {
        return contraint;
    }

    @Override
    public void initLinkUIContainer(LinkUIContainer luic) {
        if(this.virtualFile == null){
            throw new IllegalStateException("First call init() on SimpleFileLinkUI");
        }
        this.luic = luic;
    }

    @Override
    public void uninitLinkUIContainer(LinkUIContainer luic) {
        if(this.luic == luic){
            this.luic = null;
        }
    }

    @Override
    public int getH(double f) {
        return lp.getH(f);
    }

    @Override
    public int getMinH() {
        return lp.getMinH();
    }
    
    final LinkUIContainer getLinkUIContainer(){
        return luic;
    }

    final NeembuuUI getNeembuuUI() {
        return neembuuUI;
    }

    final MountManager getMountManager() {
        return mountManager;
    }
    
}
