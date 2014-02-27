/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.defaultImpl.single;

import java.util.List;
import neembuu.release1.api.LinkPackage;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.VirtualFile;
import neembuu.release1.api.ui.ExpandableUI;
import neembuu.release1.ui.SingleFileLinkUI;

/**
 *
 * @author Shashank Tulsyan
 */
public final class SingleLinkPackage implements LinkPackage{

    private List<VirtualFile> files;
    private RealFileProvider realFileProvider;

    private SingleFileLinkUI singleFileLinkUI;
    
    public SingleLinkPackage() {       
        
    }
    
    @Override
    public List<VirtualFile> getVirtualFiles() {
        return files;
    }

    @Override
    public RealFileProvider getRealFileProvider() {
        return realFileProvider;
    }

    @Override
    public ExpandableUI getExpandableUI() {
        return singleFileLinkUI;
    }
    
}
