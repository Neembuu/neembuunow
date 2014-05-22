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

package neembuu.release1;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import neembuu.release1.ui.Colors;

/**
 *
 * @author Shashank Tulsyan
 */
public class BlueTingeVLC {
    public static void main(String[] args)  throws Exception {
        BufferedImage ii = ImageIO.read(new File("F:\\neembuu\\modules\\neembuu-now\\src\\neembuu\\release1\\ui\\images\\vlc.png").toURI().toURL());
        //ii.getImage().
        BufferedImage ri =  neembuu.swing.TintedGreyScaledImage.getTintedBufferedImage(
                ii, 
                Colors.hsl(140, 50, 100, Colors.HSLSource.MSEXCEL), 
                //     hsl(140, 85, 212, Colors.HSLSource.MSEXCEL);
                false);
        
        ImageIO.write(ri, "png", new File("F:\\neembuu\\pictures\\screenshots\\blue_vlc3.png"));
        
    }
   
}
