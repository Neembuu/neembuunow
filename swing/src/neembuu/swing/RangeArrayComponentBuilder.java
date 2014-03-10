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
package neembuu.swing;

import java.awt.Color;
import neembuu.rangearray.UIRangeArrayAccess;

/**
 *
 * @author Shashank Tulsyan
 */
public final class RangeArrayComponentBuilder {
    private UIRangeArrayAccess array;
    private RangeArrayElementColorProvider arrayElementColorProvider;
    private boolean updateQuickly;
    private RangeArrayElementToolTipTextProvider toolTipTextProvider;
    
    private Color unprogressedBaseColor;
    
    public static RangeArrayComponentBuilder create(){
        return new RangeArrayComponentBuilder();
    }
    
    public RangeArrayComponent build(){
        RangeArrayComponent rac = new RangeArrayComponent(array, arrayElementColorProvider, updateQuickly, toolTipTextProvider);
        rac.setUnprogressedBaseColor(unprogressedBaseColor);
        return rac;
    }

    public RangeArrayComponentBuilder setArray(UIRangeArrayAccess array) {
        this.array = array;
        return this;
    }

    public RangeArrayComponentBuilder setArrayElementColorProvider(RangeArrayElementColorProvider arrayElementColorProvider) {
        this.arrayElementColorProvider = arrayElementColorProvider;
        return this;
    }

    public RangeArrayComponentBuilder setUpdateQuickly(boolean updateQuickly) {
        this.updateQuickly = updateQuickly;
        return this;
    }

    public RangeArrayComponentBuilder setToolTipTextProvider(RangeArrayElementToolTipTextProvider toolTipTextProvider) {
        this.toolTipTextProvider = toolTipTextProvider;
        return this;
    }

    public RangeArrayComponentBuilder setUnprogressedBaseColor(Color unprogressedBaseColor) {
        this.unprogressedBaseColor = unprogressedBaseColor;
        return this;
    }
    
    
    
}
