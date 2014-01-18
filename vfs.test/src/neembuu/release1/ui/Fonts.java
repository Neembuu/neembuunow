/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
            java.awt.Font customFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, LinkPanel.class.getResourceAsStream("fonts/"+fntName)).deriveFont(10f);
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
