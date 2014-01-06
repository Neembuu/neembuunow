/*
 * Copyright (c) 2011 Shashank Tulsyan <shashaanktulsyan@gmail.com>. 
 * 
 * This is part of free software: you can redistribute it and/or modify
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
 * along with this.  If not, see <http ://www.gnu.org/licenses/>.
 */
package neembuu.rangearray;

/**
 *
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
public interface DissolvabilityRule<P> {
    /**
     * Specifies if the <pre>entryToCheckThatAlreadyExistsInThisArray</pre> has the same property as newEntry. <br/>
     * While making this comparison, {@link #start} and {@link #end} and not taken into
     * consideration.
     * Default implementation always returns true.
     * <b>Note :</b> Dissolvability (specified by {@link RangeArrayElement#dissolves(neembuu.common.RangeArrayElement)  } )
     * and Equality (specified by {@link Object#equals(java.lang.Object)  } )
     * are two different properties. Do not confuse between them.
     * For instance, if two RangeArrayElement dissolve it is not necessary that they
     * are equal. But if two RangeArrayElement are equal then they surely dissolve.
     * <br/>
     * <br/>
     * Correctly implementing this function by will help in saving memory.
     * <br/>
     * <i>To know how read this example : </i><br/>
     * <pre>
     * Index            :::   0     1     2   ...<br/>
     * Absolute index   ::: 0-->2 3-->5 6-->10 ...<br/>
     * Value stored     :::   a     b     c   ...<br/>
     * </pre>
     * <br/>
     * If now, an entry is added, such as : <br/>
     * Entry = 7--->17 Value=C  <br/>
     * If <b>dissolves</b> has been implemented, and it considers <b>c</b> to be <b>equal to C</b> <br/>
     * We will have after insertion  : <br/>
     * <pre>
     * Index            :::   0     1     2    ...<br/>
     * Absolute index   ::: 0-->2 3-->5 6-->17 ...<br/>
     * Value stored     :::   a     b     C    ...<br/>
     * </pre>
     * <br/>
     * If <b>dissolves</b> was not implemented <br/>
     * We will have after insertion  : <br/>
     * <pre>
     * Index            :::   0     1     2     3    ...<br/>
     * Absolute index   ::: 0-->2 3-->5 6-->6 7-->17 ...<br/>
     * Value stored     :::   a     b     c     C    ...<br/>
     * </pre>
     * <br/>
     * It can be clearly seen that 6-->6 could have been merged with 7-->17 and thus memory could be saved. <br/>
     * <br/><br/>
     * While adding new elements to the array
     * {@link #dissolves(neembuu.rangearray.vectorimpl.RangeArrayElement, neembuu.rangearray.vectorimpl.RangeArrayElement) } 
     * is always invoked on the new entry being added and the parameter
     * is an already existing element.
     * 
     * @param entryToCheckThatAlreadyExistsInThisArray The RangeArrayElement that has to be checked
     * @param newEntry New Entry being added to the range array
     * @return true if and only if the properties stored in the entryToCheck
     * is same as in this, such that this may be safely replaces by
     * entryToCheck and vice-versa. {@link #start} and {@link #end} and not taken into
     * consideration while evaluating the result. Also if the entry being checked is not
     * so the same type of this, dissolves should return false.
     */
    boolean dissolves(
            Range<P> newEntry,
            Range<P> entryToCheckThatAlreadyExistsInThisArray);
 
    DissolvabilityRule
            LINEAR_EXPANSIONS_ONLY = new LinearExpansionsOnlyDissolvabilityRule();
    
    DissolvabilityRule
            NEVER_DISSOLVE = new NeverDissolveDissolvabilityRule();
    
    DissolvabilityRule 
            COMPARE_PROPERTY_OBJECT = new ComparePropertyObjectDissolvabilityRule();
    
}
