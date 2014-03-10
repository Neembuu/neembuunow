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
package neembuu.release1.ui.actions;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import neembuu.release1.Main;
import neembuu.release1.api.VirtualFile;
import neembuu.release1.api.ui.access.CloseActionUIA;
import neembuu.release1.api.ui.access.LowerControlsUIA;
import neembuu.release1.api.ui.actions.ReAddAction;
import neembuu.release1.ui.Colors;
import neembuu.release1.ui.TintedGreyScaledImage;
import neembuu.vfs.file.FileBeingDownloaded;


/**
 *
 * @author Shashank Tulsyan
 */
public class ReAddActionCallBackImpl implements ReAddAction.CallBack {

    private final CloseActionUIA uia;
    private final LowerControlsUIA lowerControlUIA;

    public ReAddActionCallBackImpl(CloseActionUIA uia, LowerControlsUIA lowerControlUIA) {
        this.uia = uia;
        this.lowerControlUIA = lowerControlUIA;
    }

    @Override
    public void doneCreation(VirtualFile vf) {
        FileBeingDownloaded file = vf.getFileUIAccess();

        uia.fileNameLabel().setText(vf.getConnectionFile().getName());
        updateFileSizeString(file);

        lowerControlUIA.progress().init(file);

        try {
            Path tempFile = Files.createTempFile(null, getFileExtension(file.getName()));
            java.io.File tempfile1 = tempFile.toFile();

            Icon clr = null, bw = null;
            try {
                Image clri = sun.awt.shell.ShellFolder.getShellFolder(tempfile1).getIcon(true);
                clr = new ImageIcon(clri);
            } catch (Exception a) {
                clr = javax.swing.filechooser.FileSystemView.getFileSystemView().getSystemIcon(tempfile1);
            }
            
            try{
                Files.delete(tempFile);
            }catch(Exception a){
                // creating a junk file : | 
                a.printStackTrace();
            }
            
            bw = TintedGreyScaledImage.getTintedImage(getBF(clr), Colors.TINTED_IMAGE, false);
            uia.openButton().setIcon_bw(bw);
            uia.openButton().setIcon_clr(clr);
        } catch (Exception a) {
            Main.getLOGGER().log(Level.INFO, "Could not find icon, using default", a);
        }
    }

    private void updateFileSizeString(FileBeingDownloaded fileBeingDownloaded) {
        double sz = fileBeingDownloaded.getFileSize();
        String suffix;
        if (sz < 1000) {
            suffix = " B";
        } else if (sz < 1000 * 1000) {
            suffix = " KB";
            sz /= 1024;
        } else if (sz < 1000 * 1000 * 1000) {
            suffix = " MB";
            sz /= 1024 * 1024;
        } else if (sz < 1000 * 1000 * 1000 * 1000) {
            suffix = " GB";
            sz /= 1024 * 1024 * 1024;
        } else {
            suffix = " TB";
            sz /= 1024 * 1024 * 1024 * 1024;
        }
        if (sz < 10) {
            sz = Math.round(sz * 100.0) / 100.0;
        } else if (sz < 100) {
            sz = Math.round(sz * 10.0) / 10.0;
        }
        uia.fileSizeLabel().setText(sz + " " + suffix);
    }

    private static BufferedImage getBF(Icon icon) {
        BufferedImage bi = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
        // paint the Icon to the BufferedImage.
        icon.paintIcon(null, g, 0, 0);
        return bi;
    }

    private static BufferedImage getBF(Image img) {
        BufferedImage bi = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        g.setColor(Color.WHITE);
        bi.getGraphics().drawImage(img, 0, 0, null);
        return bi;
    }
    
    private String getFileExtension(String fn){
        if(!fn.contains(".")){
            return ".";
        }
        int di = fn.lastIndexOf(".");
        if (di > 0) {
            return fn.substring(di);
        }
        return ".";
    }
}
