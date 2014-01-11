/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.defaultImpl;

import java.util.LinkedList;
import java.util.List;
import neembuu.release1.api.VirtualFile;
import neembuu.release1.api.VirtualFilesParams;
import neembuu.release1.api.VirtualFilesProvider;

/**
 *
 * @author Shashank Tulsyan
 */
public class OneToOneVirtualFileProvider implements VirtualFilesProvider {

    @Override
    public List<VirtualFile> create(VirtualFilesParams vfp) throws Exception{
        LinkedList<VirtualFile> files = new LinkedList<VirtualFile>();
        OneToOneVirtualFile svf = new OneToOneVirtualFile(vfp);
        files.add(svf);
        return files;
    }

}
