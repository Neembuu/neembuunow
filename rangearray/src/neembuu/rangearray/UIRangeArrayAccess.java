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
package neembuu.rangearray;

/**
 * Special functions to safely access range array without risking deadlocks
 * and render gui services.
 * Do not use RangeArray directly for anything other than neembuu internals,
 * or a project entirely different.
 * @author Shashank Tuslyan
 */
public interface UIRangeArrayAccess<P> extends UnsynchronizedAccess{
    void addRangeArrayListener(RangeArrayListener ral);
    void removeRangeArrayListener(RangeArrayListener ral);
    public long getFileSize();
    public boolean isEmpty();
    public Range<P> getNext(Range<P> element);
    public Range<P> getPrevious(Range<P> element);
    public Range<P> getFirst();
    /**
     * For mouse event listener
     * @param index the absolute index where the region is required
     * @return region which contains the given absolute index
     */
    public Range<P> getUnsynchronized(long index);
}
