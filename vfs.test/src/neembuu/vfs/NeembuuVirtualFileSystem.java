/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.vfs;

import jpfm.DirectoryStream;
import jpfm.fs.SimpleReadOnlyFileSystem;
import jpfm.operations.Read;

/**
 *
 * @author Shashank Tulsyan
 */
public final class NeembuuVirtualFileSystem extends SimpleReadOnlyFileSystem {

    public NeembuuVirtualFileSystem(DirectoryStream rootDirectory) {
        super(rootDirectory);
    }

    @Override
    public final void read(final Read read) throws Exception {
        //System.out.println(read);
        super.read(read);
    }

    public final DirectoryStream getRootDirectoryStream() {
        return rootDirectoryStream;
    }

}
