/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.rangearray;

/**
 *
 * @author Shashank Tulsyan
 */
public interface UnsyncRangeArrayCopy<P> extends Iterable<Range<P>> {
    long creationModCount();
    Range<P> get(int i);
    int size();
}
