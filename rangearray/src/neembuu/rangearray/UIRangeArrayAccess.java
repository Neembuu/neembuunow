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
