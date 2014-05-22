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

import java.awt.Color;
import neembuu.swing.HSLColor;

/**
 *
 * @author Shashank Tulsyan
 */
public final class Colors {

    public static Color BORDER          = hsl(35, 72, 184, HSLSource.MSEXCEL), 
            TINTED_IMAGE                = hsl(36, 73, 206, HSLSource.MSEXCEL),
            BUTTON_TINT                 = hsl(36, 73, 216, HSLSource.MSEXCEL),
            PROGRESS_BAR_BACKGROUND     = hsl(35, 69, 209, HSLSource.MSEXCEL),
            NIMBUS_BASE                 = hsl(35, 100, 135, HSLSource.MSEXCEL), 
            TEXT_BACKGROUND             = hsl(35, 100, 254, HSLSource.MSEXCEL), 
            PROGRESS_DOWNLOAD_LESS_MODE = hsl(35, 72, 120, HSLSource.MSEXCEL), 
            OVERLAY                     = hsl(36, 71, 232, 0.3f, HSLSource.MSEXCEL),
            //SIZ9_POST_BACKGROUND        = hsl(31, 73, 248, HSLSource.MSEXCEL),
            
            CONTROL_ICONS               = hsl(140, 255, 58, HSLSource.MSEXCEL), 
            PROGRESS_BAR_FILL_ACTIVE    = hsl(140, 209, 105, HSLSource.MSEXCEL),
            PROGRESS_BAR_FILL_BUFFER   = hsl(140, 85, 212, HSLSource.MSEXCEL);
    
            
    

    private static Color rgb(int r, int g, int b) {
        return new Color(r, g, b);
    }
    
    private static Color hsl(int h, int s, int l,float alpha, HSLSource hSLSource){
        int base = hSLSource.getBase();
        return new Color(HSLColor.toRGB((float)h/base, (float)s/base, (float)l/base,alpha),true);
    }
    
    public static Color hsl(int h, int s, int l,HSLSource hSLSource){
        int base = hSLSource.getBase();
        return new Color(HSLColor.toRGB((float)h/base, (float)s/base, (float)l/base));
    }
    
    public static enum HSLSource {
        MSPAINT(240),
        MSEXCEL(255);
        private int base;

        private HSLSource(int base) {
            this.base = base;
        }

        public int getBase() {
            return base;
        }
        
    }
    
    
}
