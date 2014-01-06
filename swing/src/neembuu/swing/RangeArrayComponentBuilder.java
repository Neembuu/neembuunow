/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
