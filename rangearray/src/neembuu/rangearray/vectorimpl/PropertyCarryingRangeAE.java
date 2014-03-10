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
