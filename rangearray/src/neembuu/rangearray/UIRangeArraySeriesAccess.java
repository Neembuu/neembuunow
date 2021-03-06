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

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Shashank Tulsyan
 */
final class UIRangeArraySeriesAccess<P> implements UIRangeArrayAccess<P> {
    private final UIRangeArrayAccess<P>[]r;
    private final List<RangeArrayListener> rangeArrayListeners = new LinkedList<RangeArrayListener>();
    private final AtomicInteger atomicModCount = new AtomicInteger(0);
    private final PropertyRescaler<P> propertyRescaler;
    
    public UIRangeArraySeriesAccess(UIRangeArrayAccess<P>[] r,PropertyRescaler<P> propertyRescaler) {
        this.r = r; this.propertyRescaler = propertyRescaler;
        for (int i = 0; i < r.length; i++) {
            System.out.println("r[i]="+r[i]);
            r[i].addRangeArrayListener(new I_th_RAListener(this, i));
        }
    }
    
    public UIRangeArraySeriesAccess(List<UIRangeArrayAccess<P>> rangeArrays,PropertyRescaler<P> propertyRescaler) {
        this(rangeArrays.toArray(new UIRangeArrayAccess[rangeArrays.size()]),propertyRescaler);
    }

    @Override
    public void addRangeArrayListener(RangeArrayListener ral) {
        synchronized (rangeArrayListeners){
            //rangeArrayListeners.add(WeakListeners.create(RangeArrayListener.class, ral, this));
            rangeArrayListeners.add(ral);
        }
    }
    
    @Override
    public void removeRangeArrayListener(RangeArrayListener ral) {
        synchronized (rangeArrayListeners){
            rangeArrayListeners.remove(ral);
        }
    }

    @Override
    public long getFileSize() {
        long totalSize = 0;
        for (int i = 0; i < r.length; i++) {
            totalSize += r[i].getFileSize();
        }return totalSize;
    }

    @Override
    public Range<P> getFirst() {
        Range<P> first = null; long offset = 0;
        for (int i = 0; i < r.length; i++) {
            try{
                first = r[i].getFirst();
                if(first!=null){
                    first = rescaledRange(first, offset,propertyRescaler);
                    break;
                }
            }catch(Exception a){}
            offset += r[i].getFileSize();
        }
        if(first==null){ throw new ArrayIndexOutOfBoundsException("Empty"); }
        return first;
    }

    @Override
    public final Range<P> getNext(Range<P> element) {
        UnsyncRangeArrayCopy rs = tryToGetUnsynchronizedCopy();
        for(int i=rs.size()-1;i>=0;i--){
            int val = RangeUtils.compare(rs.get(i), element);
            if(val==0)return rs.get(i+1);
            else if(val<0)return null;
        }return null;
    }

    @Override
    public final Range<P> getPrevious(Range<P> element) {
        UnsyncRangeArrayCopy rs = tryToGetUnsynchronizedCopy();
        for(int i=rs.size()-1;i>=0;i--){
            int val = RangeUtils.compare(rs.get(i), element);
            if(val==0)return rs.get(i-1);
            else if(val<0)return null;
        }return null;
    }

    @Override
    public Range<P> getUnsynchronized(long absoluteIndex) {
        UnsyncRangeArrayCopy<Range> unsyncFncCopy = tryToGetUnsynchronizedCopy();
        
        for(Range range : unsyncFncCopy){
            if (range.starting() <= absoluteIndex && absoluteIndex <= range.ending()) {
                return range;
            }
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < r.length; i++) {
            if(!r[i].isEmpty())return false;
        } return true;
    }

    @Override
    public int currentModCount() {
        return atomicModCount.get();
    }

    
    volatile Lst unsyncCopy = null; //cache
    
    @Override
    public UnsyncRangeArrayCopy tryToGetUnsynchronizedCopy() {
        long currentModCount = atomicModCount.get();
        if(unsyncCopy!=null)
            if(unsyncCopy.creationModCount == currentModCount)
                return unsyncCopy;
        
        Lst newunsyncCpy=getNewCpy();
        unsyncCopy = newunsyncCpy; // update cache
        return newunsyncCpy;
    }
    
    private Lst getNewCpy(){
        long creationModCount = atomicModCount.get();
        UnsyncRangeArrayCopy[]unsyncArrays = new UnsyncRangeArrayCopy[r.length];
        for (int i = 0; i < unsyncArrays.length; i++) {
            unsyncArrays[i] = r[i].tryToGetUnsynchronizedCopy();
        }
        
        return new Lst(unsyncArrays, creationModCount,propertyRescaler,r);
    }
    
    private static final class Lst<P> implements UnsyncRangeArrayCopy<P> {

        private final UnsyncRangeArrayCopy[]unsyncArrays;
        private final UIRangeArrayAccess ra[];
        final long creationModCount;
        private final PropertyRescaler<P> propertyRescaler;

        public Lst(UnsyncRangeArrayCopy[] unsyncArrays, long creationModCount,PropertyRescaler<P> propertyRescaler,UIRangeArrayAccess ra[]) {
            this.unsyncArrays = unsyncArrays;
            this.creationModCount = creationModCount;
            this.propertyRescaler = propertyRescaler;
            this.ra = ra;
        }

        @Override
        public final long creationModCount() {
            return creationModCount;
        }

        @Override
        public final int size() {
            int size = 0;
            for (int i = 0; i < unsyncArrays.length; i++) {
                size+=unsyncArrays[i].size();
            }
            return size;
        }

        @Override
        public Iterator<Range<P>> iterator() {
            return new Itr();
        }

        @Override
        public Range get(int index) {
            Range r = tryGet(index);
            if(r==null)
                throw new ArrayIndexOutOfBoundsException("Not found in any individual range array");
            return r;
        }

        private Range tryGet(int index){
            long fileSpaceOffset = 0;
            int arrayOffset = 0;
            for (int i = 0; i < unsyncArrays.length; i++) {
                if(arrayOffset + unsyncArrays[i].size() > index){
                    return rescaledRange(unsyncArrays[i].get(index - arrayOffset),fileSpaceOffset,propertyRescaler);
                }
                arrayOffset+=unsyncArrays[i].size();
                fileSpaceOffset+=ra[i].getFileSize();
            } return null;
        }
        
        private final class Itr<P> implements Iterator<Range<P>> {
            int index = 0;
            Range<P> next = null;
            
            @Override
            public boolean hasNext() {
                next = tryGet(index);
                return next!=null;
            }

            @Override
            public Range<P> next() {
                Range<P> toret = (Range<P>)next;
                if(next==null)next = get(index);
                index++;
                return toret;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported.");
            }
        }
        
        private static final class RangeImpl<P> implements Range<P> {
            private final long starting, ending;
            private final P property;

            private RangeImpl(long starting, long ending, P property) {
                this.starting = starting;
                this.ending = ending;
                this.property = property;
            }
            
            @Override
            public long starting() {
                return starting;
            }

            @Override
            public long ending() {
                return ending;
            }

            @Override
            public P getProperty() {
                return property;
            }
            
        }
    }
    
    private static class I_th_RAListener implements RangeArrayListener {
        private final WeakReference<UIRangeArraySeriesAccess> this_reference;
        private final int index;

        public I_th_RAListener(UIRangeArraySeriesAccess _THIS, int index) {
            this.this_reference = new WeakReference(_THIS);
            this.index = index;
        }
        
        private static  boolean l = false;
        
        @Override
        public void rangeArrayModified(long modificationResultStart, long modificationResultEnd, Range elementOperated, ModificationType modificationType, boolean removed, long modCount) {
            final UIRangeArraySeriesAccess _THIS = this_reference.get();
            if(!l){ new Throwable().printStackTrace(); l = true;}
            if(!l)System.out.println("ind="+index+" mod="+(+modificationResultStart)+"->"
                    + (modificationResultEnd)+ " "+_THIS);
            if(_THIS==null)return;
            _THIS.atomicModCount.incrementAndGet();
            if(_THIS.rangeArrayListeners.isEmpty())return;
            
            long relativePosition = 0;
            for (int i = 0; i < index; i++) {
                relativePosition += _THIS.r[i].getFileSize();
            }
            
            if(_THIS.rangeArrayListeners.size() == 1){
                RangeArrayListener ral = (RangeArrayListener)_THIS.rangeArrayListeners.get(0);
                if(ral==null)return;
                ral.rangeArrayModified(
                        relativePosition+modificationResultStart,
                        relativePosition+modificationResultEnd, 
                        elementOperated, 
                        modificationType, 
                        removed, 
                        modCount);
                return;
            }
            
            LinkedList<RangeArrayListener> ll = new LinkedList<RangeArrayListener>();
            synchronized (_THIS.rangeArrayListeners){
                Collections.copy(ll, _THIS.rangeArrayListeners);
            }
            for(RangeArrayListener r: ll){
                r.rangeArrayModified(
                        relativePosition+modificationResultStart, 
                        relativePosition+modificationResultEnd, 
                        elementOperated, 
                        modificationType, 
                        removed, 
                        _THIS.atomicModCount.get());
            }
        }
        
    }
    
    private static Range rescaledRange(Range orginal, long offset,PropertyRescaler propertyRescaler){
        Object rescaledProperty = propertyRescaler.rescale(orginal.getProperty(), offset);
        return new Lst.RangeImpl(offset+orginal.starting(), offset+orginal.ending(), rescaledProperty);
    }
}
