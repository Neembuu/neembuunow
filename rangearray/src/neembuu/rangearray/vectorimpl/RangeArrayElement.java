/*
 * Copyright 2009-2010 Shashank Tulsyan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * File : RangeArrayElement.java
 * Author : Shashank Tulsyan
 */

//todo: implement isSubcomponentOf
// todo : change name of isAnta...

package neembuu.rangearray.vectorimpl;

import neembuu.rangearray.Range;
import neembuu.rangearray.RangeArray;
import neembuu.rangearray.RangeUtils;

/**
 * Only RangeArrayElements can be added in {@link RangeArray} .
 * This default implementation carries no values.
 * It just carries the range (specified by {@link #start} and {@link #end} )
 * over which the value (if any) stored by this RangeArrayElement is applicable <br/>.
 * See the example implementation {@link ObjectRangeArrayElement }
 * @param E The type of elements in this
 * @author Shashank Tulsyan
 * @see ObjectRangeArrayElement
 * @see RangeArray
 */
class RangeArrayElement<P>
        implements Range<P>, Comparable<RangeArrayElement> {
    private volatile long start=0;
    private volatile long end=0;//why volatile ?? because some implementation of RangeArrayElement expand by themselves, this expansion can occur without other threads knowing

    protected RangeArrayElement(long start, long end, boolean unchecked, P property) {
        if(!unchecked){RangeUtils.checkRange(start, end);}
        this.start = start; this.end = end;
        //this.property = property;
    }

    /**
     * Constructs a new RangeArrayElement which carries no property.
     * Exceptions thrown by this can be checked here {@link #checkRange(long, long) }
     * @param start Starting (inclusive) index of range value
     * @param end Ending (inclusive) index of range value
     * @see #checkRange(long, long) 
     */
    public RangeArrayElement(long start,long end, P property) {
        this(start,end,true,property);
    }
    
    public RangeArrayElement(long start,long end) {
        this(start,end,true,null);
    }

    RangeArrayElement(boolean isFakeStarting){
        if(isFakeStarting){
            this.start = RangeArrayImpl.MIN_VALUE_SUPPORTED-3; this.end = RangeArrayImpl.MIN_VALUE_SUPPORTED-3;
        }else{
            this.start = RangeArrayImpl.MAX_VALUE_SUPPORTED+3; this.end = RangeArrayImpl.MAX_VALUE_SUPPORTED+3;
        }
    }

    public boolean contains(Range subcomponent){
        return (
                subcomponent.ending()<=ending()
                && subcomponent.starting()>=starting()
        );
    }
    //public abstract boolean isSubcomponentOf(RangeArrayElement component);
    /**
     *
     * @return Starting/first value/offset/address of this component
     */
    @Override
    public final long starting(){
        return start;
    }
    /**
     * In case of {@link neembuu.vfs.readmanager.ReadHandler} ending is the last offset till 
     * which data has been already downloaded and is available for reading. For other cases 
     * this simply signifies the end limit of that quantity.
     * Ending is always inclusive. Size of this region is (ending-starting + 1).
     * @return Ending/last value/offset/address of this component
     */
    @Override
    public final long ending(){
        return end;
    }
    /**
     * @param start Starting value/offset/address of this component
     */
    /*package private*/ final void setStarting(long start)  {
        //if(start>end)throw new IndexOutOfBoundsException("End value cannot be smaller than start value. start="+start+" end="+end);
        this.start=start;
    }
    /**
     * @param end Ending/last value/offset/address of this component
     */
    /*package private*/ final void setEnding(long end) {
        //if(end<start)throw new IndexOutOfBoundsException("End value cannot be smaller than start value. start="+start+" end="+end);
        this.end=end;
    }
    
    /**
     * Compares two RangeArrayElement values for equality.
     * In default implementation, it simply returns
     * {@link RangeArrayElement#equalsIgnoreProperty(java.lang.Object)  }
     * @see Object#equals(java.lang.Object)
     * @see RangeArrayElement#equalsIgnoreProperty(java.lang.Object) 
     */
    @Override
    //@SuppressWarnings(value={"method not checking type of its parameter"})
    public boolean equals(Object obj) {
        return equalsIgnoreProperty(obj);
    }
    @Override
    public int hashCode() {
        return (int)starting();
    }
    @Override
    public int compareTo(RangeArrayElement o) {
        if(o.ending()>ending())return -1;//this is smaller
        if(starting()>o.starting())return 1;//this is gr8er
        return 0;
    }

    /**
     * A convinient method to check if 2 range value are equal<br/>
     * <code>a-->b equalsIgnoreProperty c-->d</code> returns <code>true</code> <br/>
     * If and only if <code>a==c && c==d </code>
     * For null and Objects that are not instance of RangeArrayElement false is always returned.
     * Property carried by this are not taken into consideration.
     * @param obj The object that has to be checked
     * @return <code>a-->b equalsIgnoreProperty c-->d</code> returns <code>true</code> <br/>
     *         If and only if <code>a==c && c==d </code> ; false otherwise
     */
    public final boolean equalsIgnoreProperty(Object obj){
        if(obj==null)return false;
        if(!(obj  instanceof RangeArrayElement))return false;
        return (
            ((RangeArrayElement)obj).starting()==starting()
                && ((RangeArrayElement)obj).ending()==ending()
        );
        
    }
    
    public boolean dissolves(RangeArrayElement entryToCheckThatAlreadyExistsInThisArray){
        return true;
    }

    /**
     * This method is invoked while moving RangeArrayElement entries. Classes that extend RangeArrayElement
     * should override this method at any cost.
     * If this is not done the property stored (if any) will not be copied from
     * entries being deleted (because of merging, expansion, contraction, absorbtion ...)
     * Default implementation does nothing.
     *  {@link #start} and {@link #end} are not supposed to be copied
     * Only those properties that are required and were defined by the
     * implementing class should be copied.
     * @param entry Properties(if any) should be copied from <b>entry</b> into this instance of RangeArrayElement .
     */
    public void copyPropertiesFrom(Range entry){
        
    }

    /**
     * A convenient method to check if given range if supported by RangeArray
     * @param start Starting (inclusive) index of range value
     * @param end Ending (inclusive) index of range value
     * @throws ArrayIndexOutOfBoundsException If start or end are beyond allowed value
     * @throws IllegalArgumentException If start>end
     */
    public final void checkRange(long start,long end){
        if(start>end)throw new IllegalArgumentException("Starting of range entry cannot be greater than ending ");
        if(start<RangeArrayImpl.MIN_VALUE_SUPPORTED)
            throw new ArrayIndexOutOfBoundsException(
                    "Starting of range entry cannot be smaller than minimum supported value  "+RangeArrayImpl.MIN_VALUE_SUPPORTED
            );
        if(end>RangeArrayImpl.MAX_VALUE_SUPPORTED)
            throw new ArrayIndexOutOfBoundsException(
                    "Ending of range entry cannot be greater than maximum supported value  "+RangeArrayImpl.MAX_VALUE_SUPPORTED
            );
    }

    @Override
    public String toString() {
        return starting()+"->"+ending();
    }

    /**
     * Antagonist entries are removed instead of being added. <br/>
     * All implementations of this class should override this method.
     * An entry is added in {@link RangeArray#addElement(neembuu.common.RangeArrayElement) }
     * by using {@link RangeArray#addElement(neembuu.common.RangeArrayElement) } .
     * But, if that entry's <b>isAnAntagonistEntry()</b> returns <b>true</b> it is removed instead of being added. <br/>
     * Antagonist entries can be thought of as Anti-Entries. <br/>
     * Another way of removing entries is simply using
     * {@link RangeArray#removeElement(long, long) } . This function and it's feature is provided just for convinience.
     * @return default implementation always returns false
     */
    public boolean isAnAntagonistEntry(){
        return false;
    }
    /**
     * @return The size of this RangeElement, which is end-start+1
     */
    public long getSize(){
        return(ending()-starting()+1);
    }

    /**
     * Utility to check shift
     * @param newStart
     */
    protected final void checkShiftRequest(long newStart,long newEnd){
        if(newStart > newEnd){
            throw new IllegalArgumentException(
                "Starting of range cannot be bigger than ending. For newStarting = "+newStart +
                " newEnding point = "+newEnd
            );
        }
        if(
                (newStart<starting() || newStart>ending())
                ||
                (newEnd<starting() || newEnd>ending())

                )
            throw new IllegalArgumentException(
                "Trying to shift element beyond it\'s region of existence." +
                "\nFor element = "+this+
                "\nShift point = "+newStart+"-->"+newEnd
            );
    }

    public String getTooltipString(){
        return null;
    }
    
    public boolean isShifted(){return false;}

    @Override
    public P getProperty() {
        return null;
    }
    
}
