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

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
public interface RangeArray<P> extends Collection<Range<P>>, UIRangeArrayAccess<P> {

    Iterator<Range<P>> absoluteIterator();

    long absoluteSize();

    /**
     * Adds an entry in this RangeArray.
     * Removes an entry if it is an antagonist entry ( {@link RangeArrayElement#isAnAntagonistEntry() } )
     * @param newEntry The new entry which is to be added to this RangeArray
     * @see #removeElement(neembuu.common.RangeArrayElement)
     * @see RangeArrayElement#isAnAntagonistEntry()
     * @throws RangeArrayElementRejectedByFilterException If file size was set to some value which is lesser than
     * {@link #MAX_VALUE_SUPPORTED } and the element being added is greater than this value
     */
    Range<P> addElement(long start, long end, P property);
    
    Range<P> setProperty(Range<P> range, P Property);
    
    boolean rangeObjectsImplementedAsImmutable();

    @SuppressWarnings(value = "unchecked")
    int containsAt(Object o);

    /**
     * Used for making BitSet view out of a RangeArray
     * @param n the number of the piece, couting starts from 0
     * @elementSize Size of the bittorrent piece or block, whichever is required
     * @return true if and only if the region n*pieceSize--->(n+1)*pieceSize-1 exists fully
     */
    boolean containsCompletely(int n, long elementSize);

    /**
     * Same as {@link #containsCompletely(int, long) }
     * @param dissovabilityComplex Is true implies that entries which have the same value
     * might also not dissolve because they are intrinsically undissolvable. In such a case
     * size of each entry has to be added to check is the given region exists as sum of more than
     * one entry
     * @return true if and only if the region n*pieceSize--->(n+1)*pieceSize-1 exists fully in
     * a single entry or multiple entries (dissovabilityComplex set to true)
     */
    boolean containsCompletely(int n, long elementSize, boolean dissovabilityComplex);

    boolean containsPartially(Range<P> toCheck);

    /**
     * Makes and returns a new copy of this RangeArray
     * @return A new copy of this RangeArray
     */
    @SuppressWarnings(value = "unchecked")
    RangeArray<P> copy();

    /**
     * RangeArrayElement which can expand, i.e. increase the value of it's end
     * should use this to ensure it doesn't conflict with existing ones.
     * @see #expandVeryCarefully(neembuu.common.RangeArray, long)
     * @param rangeArray The array containing this element
     * @param newEnd desired value of end for this
     */
    long expandCarefully(Range<P> region, long newEnd);

    /**
     * Returns the RangeArrayElement at the specified position in this.
     *
     * <pre>
     * 0           1
     * 123--->212 4212--->11343
     * propertyA  propertyB
     * </pre>
     *
     * Value at index 0 is propertyA and at 1 is propertyB <br/>
     * Value at absoluteIndex 123,124,125..... and 212 is propertyA. <br/>
     * Value at absoluteIndex 4212,4213.... and 11343  is propertyB. <br/>
     * @param absoluteIndex The absolute RangeArray index
     * @return the RangeArrayElement at the specified position in this list.
     * @see RangeArray#RangeArray(int, neembuu.common.RangeArrayElementFactory)
     */
    Range<P> get(long absoluteIndex);

    /**
     * Returns the RangeArrayElement at the specified position in this.
     *
     * <pre>
     * 0           1
     * 123--->212 4212--->11343
     * propertyA  propertyB
     * </pre>
     *
     * Value at index 0 is propertyA and at 1 is propertyB <br/>
     * Value at absoluteIndex 123,124,125..... and 212 is propertyA. <br/>
     * Value at absoluteIndex 4212,4213.... and 11343  is propertyB. <br/>
     * @param index The index
     * @return the RangeArrayElement at the specified position in this list.
     * @see RangeArray#RangeArray(int, neembuu.common.RangeArrayElementFactory)
     * @throws ArrayIndexOutOfBoundsException when array index is negative or greater than size
     */
    Range<P> get(int index);

    /**
     * We can also perform parallel summing ... but that is a java7
     * thing
     * @param bounds null means sum over entire region
     * @return
     */
    long getElementsSum(long start, long end);

    /**
     * Get's the size of file this RangeArray is being used to represent.
     * This value is the maximum allowed accessible absoluteindex (for absoulte index see {@link #get(long) } )
     * of this RangeArray.
     * </p>
     * This operation is used for files generally, but may be used to limit the capacity
     * of a RangeArray. Default value is {@link RangeArray#MAX_VALUE_SUPPORTED }
     * </p>
     * Negative values are allowed since the domain of range array exists on either side of zero.
     * @return file size limit
     */
    long getFileSize();

    /**
     *
     * @param toFindIndexOfEntry The entry whoes index is to be found
     * @return -1 is not found, else the index of the entry
     */
    int getIndexOf(Range<P> toFindIndexOfEntry);

        /**
     * For a system as shown below,
     * and <code>bounds = new RangeArrayElement(x1,x2) </code>
     * <pre>
     * Index                     0               1               2               3               4               5
     * Offset value          A------>B       C--x1-->D       E------>F       G------>H       I---x2->J       K------>L
     * </pre>
     * Where x1 lies between C and D, and similarly x2 lies between I and J,
     * returns <code>new int[]{1,4} </code>.
     * <br/>
     * Pay attention to the fact that both indices are inclusive.
     * So in  for loop for this would be something like :
     * <pre>
     * int[]indices=rangeArray.getIndexPair(bounds);
     * for(int j=indices[0]; j&#60;=indices[1] ; j++){
     * RangeArrayElement nextRAE = rangeArray.get(j);
     * }
     * </pre>
     * <br/><br/>
     * <u>Special case</u>  :
     * When the element who's index pair is being searched lies prior to all existing entries: <br/>
     * <small>(If the request lies beyond all existing entries, the second value in the
     * index pair array is equal to length() -1 to avoid array index out of bounds exception.
     * This is for convenience at the cost of loss of symmetry in api )</small>
     * <pre>
     * Index                        0                     1
     * Offset value          1000------>1200      5000---->6000
     * </pre>
     * And index pair of <code>new RangeArrayElement(500,600)</code> is required,
     * then the array would be
     * a negative value will be returned by as if there was a fake entry before zero at minus one.
     * <pre>
     * Index                       -1                      0                    1
     * Offset value   -infinity----> -infinity       1000------>1200      5000---->6000
     * </pre>
     * That is returned value would be </code>new int[]{-1,-1}<code> for <ul>
     * <li>Empty array <</li>>
     * <li><code>new RangeArrayElement(500,600)</code> region prior and not touching or overlapping</li>
     * </ul>
     * The  returned value would be </code>new int[]{-1,0}<code> for <ul>
     * <li><code>new RangeArrayElement(500,1100)</code> region starting before ending inside</li>
     * <li><code>new RangeArrayElement(500,1600)</code> region starting before extending beyond</li>
     * </ul>
     * The returned value would be </code>new int[]{0,0}<code> for <ul>
     * <li><code>new RangeArrayElement(1100,1600)</code> region inside and extending </li>
     * <li><code>new RangeArrayElement(1100,1110)</code> region inside</li>
     * <li><code>new RangeArrayElement(1600,2000)</code> region outside</li>
     * </ul>
     * <br/><br/>
     *
     * <small> <u>Not important : </u>
     * {@link #getIndexPair(neembuu.common.RangeArrayElement) }
     * is different from {@link #indexOf(neembuu.common.RangeArrayElement) }
     * The latter does not use exact index of logic, it is
     * designed specially for insertion algorithms. It has special boundary
     * conditions which do not match the boundary conditions and constraints
     * of {@link #getIndexPair(neembuu.common.RangeArrayElement) }</small>
     * @param bounds
     * @return the indices of elements closest to the requested bounds.
     */
    int[] getIndexPair(long boundsStart, long boundsEnd);



    Range<P> getLastElement();

    Object getModLock();

    /**
     * Use this is painting graphical components and other things,
     * so that unnecessary locking on the rangearray is not required.
     */
    UnsynchronizedAccess getUnSynchronizedArrayAccess();

    /**
     * Returns -1 or size()*2-1 if not found.
     * If found between two different entries returns an odd value.<br/>
     * If found inside a entry returns an even value, which is twice the index at which it was found.<br/>
     * Example :<br/>
     * <br/>
     * <pre>
     * neembuu.common.RangeArray@c20e24{
     * index=0 -100->-91 10
     * index=1 0->1 2
     * index=2 100->109 10
     * }
     * indexOf(-1000)=-1   // not found
     * indexOf(-1)=1       // this means not found, and exists between index [1/2] = 0 and ]1/2[ = 1
     * indexOf(-100)=0     // found at index 0/2 = 0
     * indexOf(0)=2        // found at index 2/2 = 1
     * indexOf(100)=4      // found at index 4/2 = 2
     * indexOf(109)=4      // found at index 4/2 = 2
     * indexOf(111)=5      // this means not found, and exists between index [5/2] = 2  and ]5/2[ = 3 (if 3 existed)
     * </pre>
     * <br/>
     * <br/>
     *
     * @see #contains(java.lang.Object) Refer to the source code of RangeArray#contains(java.lang.Object)
     * @param absoluteIndex
     * @return twice the actual index of given absoluteIndex
     */
    int indexOf(long absoluteIndex);

    /**
     * Merges this RangeArray with another<br/>.
     * Uses System.arraycopy if possible (hence faster), otherwise uses addAll.
     * @see #addAll(java.util.Collection)
     * @param mergeWith
     */
    void merge(RangeArray<P> mergeWith);

    /**
     * Removes an entry, whatever it'start value maybe, (even if it exists fully, partially or not all)
     * from this RangeArray, specified by given value of <b>start</b> and <b>end</b>.
     * If an entry with a given property has to be removed, it should be done by ADDING
     * an entry with the correct property using {@link #addElement(neembuu.common.RangeArrayElement) } , this
     * new entry will replace the old entry.<br/>
     * <br/>
     * Throws exceptions, details can be read here {@link RangeArrayElement#checkRange(long, long) }
     * <br/>
     * This function is equivalent to adding a value in this RangeArray
     * whoes {@link RangeArrayElement#isAnAntagonistEntry() } returns true;
     * @param start The starting value of entry which is to be removed from this RangeArray
     * @param end The ending value of entry which is to be removed from this RangeArray
     * @see #addElement(neembuu.common.RangeArrayElement)
     */
    void removeElement(long start, long end);

    /**
     * Set the file size value
     * @param fileSize The size of this file at the moment ( this value can be changed anytime )
     * @throws ArrayIndexOutOfBoundsException If the new value is lesser than the biggest value already present in this
     */
    void setFileSize(long fileSize) throws ArrayIndexOutOfBoundsException;
    
    
    @Override
    void addRangeArrayListener(RangeArrayListener ral);
    
    @Override
    void removeRangeArrayListener(RangeArrayListener ral);
    
    DissolvabilityRule<P>[] getDissolvabilityRules();
    
    boolean doesCarryProperty();
    
    long MIN_VALUE_SUPPORTED = (Long.MIN_VALUE/2)+4;
    long MAX_VALUE_SUPPORTED = (Long.MAX_VALUE/2)-4;

}
