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

package neembuu.release1.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import javax.swing.JPanel;

/**
 *
 * @author Shashank Tulsyan
 */
public final class Fonts {
    public static final Font MyriadPro = initFont("MYRIADPRO-REGULAR.ttf");
    public static final Font FuturaLight = initFont("Futura-Light.ttf");
    public static final Font Amper = initFont("hmampersand-regular-webfont.ttf");
    
    static java.awt.Font initFont(String fntName){
        try {
            //create the font to use. Specify the size!
            java.awt.Font customFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream("fonts/"+fntName)).deriveFont(10f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(customFont);
            return customFont;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        return new JPanel().getFont();
    }
}
