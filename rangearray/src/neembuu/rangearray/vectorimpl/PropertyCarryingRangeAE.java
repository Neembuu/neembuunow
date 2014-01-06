/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.rangearray.vectorimpl;

import neembuu.rangearray.Range;

/**
 *
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
final class PropertyCarryingRangeAE<P> extends RangeArrayElement<P> {
    private volatile P property;

    /**
     * Constructs a new RangeArrayElement which carries no property.
     * Exceptions thrown by this can be checked here {@link #checkRange(long, long) }
     * @param start Starting (inclusive) index of range value
     * @param end Ending (inclusive) index of range value
     * @see #checkRange(long, long) 
     */
    public PropertyCarryingRangeAE(long start,long end, P property) {
        super(start,end,true,property);
        this.property = property;
    } 
    
    @Override
    @SuppressWarnings(value="unchecked")
    public final P getProperty() {
        /*if(property==null){
            return ((P)this);
        }*/
        return property;
    }
    
    /*package private*/final void setProperty(P property) {
        this.property = property;
    }

    @Override
    public final void copyPropertiesFrom(Range entry) {
        this.property = (P)entry.getProperty();
    }
    
}
