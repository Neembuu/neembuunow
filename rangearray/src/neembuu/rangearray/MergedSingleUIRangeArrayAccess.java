/*
 * Copyright (C) 2014 Shashank Tulsyan
 *
 * This program is free software: you can redistribute it and/or modify
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.rangearray;

/**
 *
 * @author Shashank Tulsyan
 */
public class MergedSingleUIRangeArrayAccess implements UIRangeArrayAccess{
    private final UIRangeArrayAccess uiraa;

    public MergedSingleUIRangeArrayAccess(UIRangeArrayAccess uiraa) {
        this.uiraa = uiraa;
    }

    @Override
    public void addRangeArrayListener(RangeArrayListener ral) {
        uiraa.addRangeArrayListener(ral);
    }

    @Override
    public void removeRangeArrayListener(RangeArrayListener ral) {
        uiraa.removeRangeArrayListener(ral);
    }

    @Override
    public long getFileSize() {
        return uiraa.getFileSize();
    }

    @Override
    public boolean isEmpty() {
        return uiraa.isEmpty();
    }

    @Override
    public Range getNext(Range element) {
        return uiraa.getNext(element);
    }

    @Override
    public Range getPrevious(Range element) {
        return uiraa.getPrevious(element);
    }

    @Override
    public Range getFirst() {
        return uiraa.getFirst();
    }

    @Override
    public Range getUnsynchronized(long index) {
        return uiraa.getUnsynchronized(index);
    }

    @Override
    public UnsyncRangeArrayCopy tryToGetUnsynchronizedCopy() {
        return uiraa.tryToGetUnsynchronizedCopy();
    }

    @Override
    public int currentModCount() {
        return uiraa.currentModCount();
    }
    
}
