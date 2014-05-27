/*
 * Copyright (C) 2014 Shashank Tulsyan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
import neembuu.rangearray.Range;
import neembuu.rangearray.UIRangeArrayAccess;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.release1.ui.Colors;
import neembuu.swing.TintedGreyScaledImage;
import neembuu.vfs.file.MinimumFileInfo;

/**
 *
 * @author Shashank Tulsyan
 */
public class Utils {
    public static String getFileExtension(String fn){
        if(!fn.contains(".")){
            return ".";
        }
        int di = fn.lastIndexOf(".");
        if (di > 0) {
            return fn.substring(di);
        }
        return ".";
    }
    
    private static BufferedImage imageToBufferedImage(Image img) {
        BufferedImage bi = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        g.setColor(Color.WHITE);
        bi.getGraphics().drawImage(img, 0, 0, null);
        return bi;
    }
    
    public static String makeFileSizeString(MinimumFileInfo fileInfo) {
        double sz = fileInfo.getFileSize();
        String suffix;
        if (sz < 1000) {
            suffix = " B";
        } else if (sz < 1000000) {
            suffix = " KB";
            sz /= 1024;
        } else if (sz < 1000000000) {
            suffix = " MB";
            sz /= 1024 * 1024;
        } else if (sz < 1000000000000L) {
            suffix = " GB";
            sz /= 1024 * 1024 * 1024;
        } else {
            suffix = " TB";
            sz /= 1024 * 1024 * 1024 * 1024;
        }
        String toRet = "";
        if (sz < 10) {
            toRet += Math.round(sz * 100.0) / 100.0;
        } else if (sz < 100) {
            toRet += Math.round(sz * 10.0) / 10.0;
        } else if (sz < 1000){
            toRet += ((int)sz);
        }
        return toRet + " " + suffix;
    }
    
    private static BufferedImage iconToBufferedImage(Icon icon) {
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
    
    public static TintedIcons makeTintedIconsForFile(String fileName){
        try {
            Path tempFile = Files.createTempFile(null, Utils.getFileExtension(fileName));
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
            
            bw = TintedGreyScaledImage.getTintedImage(Utils.iconToBufferedImage(clr), Colors.TINTED_IMAGE, false);
            return new TintedIcons(bw, clr);
        } catch (Exception a) {
            LoggerUtil.L().log(Level.INFO, "Could not find icon, using default", a);
        } return null;
    }
    
    public static final class TintedIcons {
        public final Icon unicoloredIcon,coloredIcon;
        public TintedIcons(Icon bw, Icon clr) { this.unicoloredIcon = bw; this.coloredIcon = clr; }
    }
    
    public static Range getClosestRange(Range initial,UIRangeArrayAccess regions) {
        UnsyncRangeArrayCopy unsyncFncCopy = regions.tryToGetUnsynchronizedCopy();

        if (initial == null) {
            return regions.getFirst();
        }
        long ending = initial.ending();
        initial = regions.getUnsynchronized(initial.ending());

        if (initial != null) {
            return initial;
        }
        if (unsyncFncCopy.size() == 0) {
            return null;
        }
        Range closest = unsyncFncCopy.get(0);
        long dmin = ending - closest.ending();
        for (int i = 0; i < unsyncFncCopy.size(); i++) {
            Range range = unsyncFncCopy.get(i);
            long d = ending - range.ending();
            if (d < 0) {
                break;
            }
            if (d < dmin) {
                dmin = d;
                closest = range;
            }
        }
        return closest;
    }
}
