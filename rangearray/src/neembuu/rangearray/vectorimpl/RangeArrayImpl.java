/*
 *  Copyright (C) 2009-2010 Shashank Tulsyan
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
 *
 *
 * 
 *  Linking this library statically or
 *  dynamically with other modules is making a combined work based on this library.
 *  Thus, the terms and conditions of the GNU General Public License cover the whole combination.
 *
 *
 *  As a special exception, the copyright holders of this library give you permission to
 *  link this library with independent modules to produce an executable, regardless of
 *  the license terms of these independent modules, and to copy and
 *  distribute the resulting executable under terms of your choice,
 *  provided that you also meet, for each linked independent module,
 *  the terms and conditions of the license of that module.
 *  An independent module is a module which is not derived from or based on this library.
 *  If you modify this library, you may extend this exception to your version of the library,
 *  but you are not obligated to do so. If you do not wish to do so,
 *  delete this exception statement from your version.
 */

/*
 *
 * todo : add checks in indexOf for entries which are
 * so small that C+2 ... thingy might cross boundery on a side
 * this c+2 thing has special appearance in case 6,7,8
 * ^^^ somehow it's working. The indexOf algo is weird to uses <= heavily,
 * maybe that does tha magic. beware !
 *
 * todo : the last index is accessible, the fake one that is, and
 * is also used in RangeArrayJComponent, do something << no probs at RangeArrayJComponents so i think i fixed this
 *
 * add "modified around" statement : we added a listener, and we send it bounds of region updated
 *
 * Iterator may not be thread safe. Check if changeInNumberOfElements works
 * Ensure Thread safety everywhere and also remove synchronized where possible .....doing
 *
 * todo :
 * use factory.entriesNeverDissolve()
 *
 * Implement mechanism for dissolvability resolution
 *
 * test ShiftedRangeArrayElement
 */

package neembuu.rangearray.vectorimpl;

import java.util.Arrays;
import neembuu.rangearray.RangeArrayElementRejectedByFilterException;
import neembuu.rangearray.RangeArray;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;
import neembuu.rangearray.DissolvabilityRule;
import neembuu.rangearray.ModificationType;
import neembuu.rangearray.NeverDissolveDissolvabilityRule;
import neembuu.rangearray.RangeArrayListener;
import neembuu.rangearray.Range;
import neembuu.rangearray.RangeArrayElementFilter;
import neembuu.rangearray.RangeArrayParams;
import neembuu.rangearray.RangeArrayUtils;
import neembuu.rangearray.RangeUtils;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.rangearray.UnsynchronizedAccess;
import neembuu.util.weaklisteners.WeakListeners;

/**
 * RangeArray is like a {@link java.util.Vector } (Vector can be thought of as an expandable array)
 * who's maximum capacity is 9223372036854775798 elements (If only positive indices are used then  almost half of {@link Long#MAX_VALUE } ).<br/>
 * Capacity of a normal integer array on a 32-bit system, is less than 4294967296 (4294967296 is lesser than 9223372036854775798 ).<br/>
 * Also an integer array of such size would require around 16GB of RAM.<br/>
 * RangeArray can store such large number of values by grouping indices with same value.
 * (Which means RangeArrray will also not work if same value doesn't repeat itself sufficient number of times) <br/>
 * <br/>
 * Example :<br/>
 * <pre>
 * array index ::: 0 1 2 3 4 5 ...<br/>
 * value stored :: a a a b b c ...<br/>
 * Would be saved as :<br/>
 * <br/>
 * Index            :::   0     1     2   ...<br/>
 * Absolute index   ::: 0-->2 3-->4 5-->5 ...<br/> 
 * Value stored     :::   a     b     c   ...<br/>
 * </pre><br/>
 * <u>Note</u> :<ul>
 * <li> RangeArray indices are always inclusive, both starting and ending.
 * Example 5-->5 means only single 5, 6-->8 means 6,7&8
 * <li> In the above example, indices of the sort 0-->2 are called <b>absolute index</b>
 * <li> Range Values are stored in an array/Vector, indices of that array are simply called <b>index</b>
 * </ul>
 * <br/>
 * RangeArray carries only {@link RangeArrayElement }s.
 * An {@link RangeArrayElement } does not carry any property.
 * It must be extended in order to store property in it. <br/>
 * An example implementation of {@link RangeArrayElement } may be found here {@link ObjectRangeArrayElement } <br/>
 * <br/>
 * <br/>
 * <i>Not Important : </i><br/>
 * Bitmaping is not a good option always as it reduces resolution.<br/>
 * EXAMPLE : File systems often use 1-bit to store information about region of the disk (of size let's say 512kilobytes).<br/>
 * So if that 1-bit is 0 it could mean the region being represented is FREE for use,
 * if that 1-bit is 1 it would mean that the region being represented is NOT FREE .
 * But this method cannot tell us upto what extend that region of disk is USED UP or FREE.
 * It could mean that only 1byte is used up, or, the entire region of 512KB has been used up entirely. <br/>
 * This way resolution is reduced by bitmap approach.<br/>
 * Even so, bitmap consumes very little space and resolution high is not required is most cases.
 * Use of RangeArray in such case is not a good choice.<br/>
 * However, during a <a href=http://neembuu.sf.net/wiki/index.php/File_abstraction>File abstraction operation </a>
 * the download order and pattern can be very random. The progress information in such a case
 * would be more efficiently and losslessly stored in a RangeArray.
 * It will also be quickly accessible and changes can be much more easily observed.
 * This fact can be best appreciated by seeing an example (follow the File abstraction link given above)
 * <br/>
 * <br/>
 * @author Shashank Tulsyan
 * @param <Region<P>> The Type of RangeArrayElement that is to be stored in this RangeArray
 */
public class RangeArrayImpl<P>
        implements //RangeArrayElementFilter<C>, 
            RangeArray<P>,DissolvabilityRule<P>,
            RangeArrayElementFilter<P> {
    private static final boolean DEBUG=false;//to remove, use logger instead
    
    private final DissolvabilityRule<P>[]dissolvabilityRules;
    private final RangeArrayElementFilter<P>[]rangeArrayElementFilters;
    private final List<RangeArrayListener> rangeArrayListeners = new LinkedList<RangeArrayListener>();
    
    private volatile long fileSize;
    public static final long DEFAULT_FILE_SIZE = MAX_VALUE_SUPPORTED;
    public final boolean entriesNeverDissolve;

    public static final int DEFAULT_CAPACITY_INCREMENT = 10;
    //@GuardedBy(value="modLock")
    final UnsynchronizedVector<RangeArrayElement<P>>store; //package private
    private final Object modLock = new Object();
    private final boolean carriesProperty;
    private final RangeArrayParams<P> params;
    
    @Override
    public final Object getModLock(){return modLock;}
    
    /**
     * The number of times this list has been <i>structurally modified</i>.
     * Structural modifications are those that change the size of the
     * list, or otherwise perturb it in such a fashion that iterations in
     * progress may yield incorrect results.
     *
     * <p>This field is used by the iterator and list iterator implementation
     * returned by the {@code iterator} and {@code listIterator} methods.
     * If the value of this field changes unexpectedly, the iterator (or list
     * iterator) will throw a {@code ConcurrentModificationException} in
     * response to the {@code next}, {@code remove}, {@code previous},
     * {@code set} or {@code add} operations.  This provides
     * <i>fail-fast</i> behavior, rather than non-deterministic behavior in
     * the face of concurrent modification during iteration.
     *
     * <p><b>Use of this field by subclasses is optional.</b> If a subclass
     * wishes to provide fail-fast iterators (and list iterators), then it
     * merely has to increment this field in its {@code add(int, E)} and
     * {@code remove(int)} methods (and any other methods that it overrides
     * that result in structural modifications to the list).  A single call to
     * {@code add(int, E)} or {@code remove(int)} must add no more than
     * one to this field, or the iterators (and list iterators) will throw
     * bogus {@code ConcurrentModificationExceptions}.  If an implementation
     * does not wish to provide fail-fast iterators, this field may be
     * ignored.
     */
    protected transient int modCount = 0;

    /**
     * @see RangeArray#RangeArray(int, neembuu.common.RangeArrayElementFactory) 
     */
    private RangeArrayImpl(boolean carriesProperty,RangeArrayParams<P> params){
        this(DEFAULT_CAPACITY_INCREMENT,new DissolvabilityRule[0],carriesProperty,params);
    }

    public RangeArrayImpl(RangeArrayParams<P> params) {
        this.params = params;
        this.dissolvabilityRules = params.getDissolvabilityRules();
        this.rangeArrayElementFilters = params.getRangeArrayElementFilters();
        
        if(params.getRangeArrayListeners()!=null){
            if(params.getRangeArrayListeners().length>0)
                rangeArrayListeners.addAll(Arrays.asList(params.getRangeArrayListeners()));
        }
        
        this.entriesNeverDissolve = params.getEntriesNeverDissolve();
        if(params.getContents()!=null){
            this.store = checkCollection(params.getContents());
        }else  {
            this.store = new UnsynchronizedVector<RangeArrayElement<P>>(DEFAULT_CAPACITY_INCREMENT);
            store.add(new RangeArrayElement<P>(true));
            store.add(new RangeArrayElement<P>(false));
        }
        this.carriesProperty = params.carriesProperty();
        this.fileSize = params.getFileSize();
        
    }

    private UnsynchronizedVector checkCollection(
            List<Range<P>> s){
        UnsynchronizedVector tmpStore = new UnsynchronizedVector(s);
        tmpStore.insertElementAt(new RangeArrayElement<P>(true), 0);
        tmpStore.add(new RangeArrayElement<P>(false));
                
        s = tmpStore;
        // check fake entries
        /*
        if(s.size()<2)throw new IllegalArgumentException("Store does not contain fake entry "+
                (RangeArray.MIN_VALUE_SUPPORTED-3)+"-->"+(RangeArray.MIN_VALUE_SUPPORTED-3)+
                "and "+
                (RangeArray.MAX_VALUE_SUPPORTED+3)+"-->"+(RangeArray.MAX_VALUE_SUPPORTED+3)
            );
        
        if(s.get(0).starting()==s.get(0).ending()
                && s.get(0).ending()==RangeArray.MIN_VALUE_SUPPORTED-3){
            // okay
        }else throw new IllegalArgumentException("Store does not contain fake entry "+
                (RangeArray.MIN_VALUE_SUPPORTED-3)+"-->"+(RangeArray.MIN_VALUE_SUPPORTED-3)+
                ". Contains "+s.get(0)+" instead");
        
        if(s.get(s.size()-1).starting()==s.get(s.size()-1).ending()
                && s.get(s.size()-1).ending()==RangeArray.MAX_VALUE_SUPPORTED+3){
            // okay
        }else throw new IllegalArgumentException("Store does not contain fake entry "+
                (RangeArray.MAX_VALUE_SUPPORTED+3)+"-->"+(RangeArray.MAX_VALUE_SUPPORTED+3));
        */
        long lastEndOffset = MIN_VALUE_SUPPORTED-3;
        for(int i =1; i<s.size()-1;i++){
            Range<P> element = s.get(i);
            RangeUtils.checkRange(element.starting(),element.ending());
            
            if(lastEndOffset > element.starting()){
                throw new IllegalArgumentException("Elements in store are over laping");
            }
        }
        return tmpStore;
        /*final StackTraceElement methodCaller = Thread.currentThread().getStackTrace()[2]; //t.getStackTrace()[1];
        if(methodCaller.getClassName().compareTo("neembuu.common.RangeArrayTests")!=0){
            throw new IllegalStateException("This is used internally for test only. Attempting call from "+methodCaller.getClassName());
        }*/
    }

    @SuppressWarnings(value="unchecked")
    private RangeArrayImpl(int capacityIncrement,UnsynchronizedVector uv,boolean carriesProperty){
        if(capacityIncrement<0)
            throw new IllegalArgumentException("Illegal Capacity increment="+capacityIncrement);
        store=uv;
        dissolvabilityRules = new DissolvabilityRule[0];
        entriesNeverDissolve = setEntriesNeverDissolve(dissolvabilityRules);
        this.fileSize=DEFAULT_FILE_SIZE;
        this.carriesProperty = carriesProperty;
        rangeArrayElementFilters=null;
        params = null;
    }
    
    /**
     *
     * @see RangeArray#RangeArray(int, java.lang.Class, long)
     */
    private RangeArrayImpl(int capacityIncrement,DissolvabilityRule<P>[]dissolvabilityRules,boolean carriesProperty,RangeArrayParams<P> params){
        this(capacityIncrement,dissolvabilityRules,DEFAULT_FILE_SIZE,carriesProperty,params);
    }
    /**
     * Instead of making a separate factory class, implementors
     * can also pass an object of the class of the elements being added.</p>
     * But in such a case they must ensure that the RangeArrayElement implementation
     * that they create, constain constructors as in the following example as :
     * <pre>
     * class CustomRangeArrayElement extends RangeArrayElement {
     *      public CustomRangeArrayElement(){
     *          super();
     *          //.... anything more if required
     *      }
     *      public CustomRangeArrayElement(long s,long e){
     *          super(s,e);
     *          //.... anything more if required
     *      }
     *      //other constructors if any
     *
     * //....
     * }
     * </pre>
     * And we would make a RangeArray Object as :
     * <pre>
     * RangeArray<CustomRangeArrayElement> rangeArray = new RangeArray<CustomRangeArrayElement>(
     *          RangeArray.DEFAULT_CAPACITY_INCREMENT,
     *          CustomRangeArrayElement.class
     *      );
     * </pre>
     * In the above example fileSize is set to, {@link RangeArray#DEFAULT_FILE_SIZE }
     * @param capacityIncrement the amount by which the capacity is
     *                              increased when the vector(if this implementation uses one) overflows
     * @param elementClass The object of the class of the element that will be added to this
     * @param fileSize The size of the abstract file (if any) being represented by this
     * @throws  NoSuchMethodException These exception are thrown only during failed invocation
     *      of {@link Constructor#newInstance(java.lang.Object[]) } by this constructor.
     * @throws  InstantiationException
     * @throws  IllegalAccessException
     * @throws  InvocationTargetException
     */
    @SuppressWarnings(value="unchecked")
    private RangeArrayImpl(
            int capacityIncrement,
            DissolvabilityRule<P>[]dissolvabilityRules,
            long fileSize,
            boolean carriesProperty,
            RangeArrayParams<P> params){
        if(capacityIncrement<0)
            throw new IllegalArgumentException("Illegal Capacity increment="+capacityIncrement);
        this.dissolvabilityRules = dissolvabilityRules;
        entriesNeverDissolve = setEntriesNeverDissolve(dissolvabilityRules);
        store=new UnsynchronizedVector<RangeArrayElement<P>>(0,capacityIncrement);
        store.add(new RangeArrayElement<P>(true));
        store.add(new RangeArrayElement<P>(false));
        this.fileSize=fileSize;
        rangeArrayElementFilters=null;
        this.carriesProperty = carriesProperty;
        this.params = params;
    }

    @Override
    public final boolean doesCarryProperty(){
        return carriesProperty;
    }
    
    private static boolean setEntriesNeverDissolve(DissolvabilityRule[]dissolvabilityRules){
        if(dissolvabilityRules==null)return false;
        if(dissolvabilityRules.length==0)return false;
        if(dissolvabilityRules[0] instanceof NeverDissolveDissolvabilityRule)
            return true;
        return false;
    }
    
    private int length(){
        return store.size();
    }
    private int[]indexOf(Range<P> sc){
        int[]ret={0,0};
        long[]x={sc.starting(),sc.ending()};
        int mid,lb,ub;int numberOfItr = 0;int effectivelength = length()*2;
        boolean[]done_x={false,false};
        boolean mide,pullupperbound,pulllowerbound;//is mid even?
        //long ar address 0     1       2       3       4       5
        //values          A---->B       C------>D       E------>F
        //
        //if mid is even C+1 ---> D+1  i.start C+1<=start<=D+1 ===> x1=mid
        //if mid is odd  B+2 ---> C    i.start B+2<=start<=C   ===> x1=mid
        //
        //if mid is even C-1 ---> D-1  i.start C-1<=start<=D-1 ===> x2=mid
        //if mid is odd  D   ---> E-2  i.start   D<=start<=E-2 ===> x2=mid
        //




        if(DEBUG)System.err.println("                  inside indexOf");
        boolean cs1,cs2;
        for(int i=0;i<2;i++){
            lb=0;ub=length()*2;
            do{
                numberOfItr++;
                if(numberOfItr > 2*effectivelength){
                    if(numberOfItr > 3*effectivelength){
                        // cannot happen unless package private or private functions have been misused
                        throw new RuntimeException("Stuck in an infinite loop in int[]indexOf(Range<P> sc) due to malformed RangeArray");
                    }
                    System.err.println("Stuck in an infinite loop in int[]indexOf(Range<P> sc). indexOf[sc={"+sc.starting()+"--->"+sc.ending()+"}]=");
                }
                mid=(lb+ub)/2;
                mide=mid%2==0; //is mid even ?
                if(DEBUG)System.err.println();
                if(DEBUG)System.err.println("               mid="+mid+" "+getOff(mid)+" "+getOff(mid+1)+" i="+i+ "done i="+done_x[i]);
                //printState();
                cs1=cs2=pulllowerbound=pullupperbound=false;
                if(i==0){
                    if(mide){//mid is even
                        if(getOff(mid)+1 <= x[0])cs1=true;//C+1<=start
                        if(x[0]<=getOff(mid+1)+1)cs2=true;//start<=D+1

                        if(cs1 && cs2){ret[0]=mid;done_x[0]=true;}
                        else if(!cs1 && cs2){pulllowerbound=false;pullupperbound=true;}
                        else if(cs1 && !cs2){pulllowerbound=true; pullupperbound=false;}
                    }else{//mid is odd
                        if(getOff(mid)+2 <= x[0])cs1=true;//B+2<=start
                        if(x[0]<=getOff(mid+1))  cs2=true;//start<=C

                        if(cs1 && cs2){ret[0]=mid;done_x[0]=true;}
                        else if(!cs1 && cs2){pulllowerbound=false;pullupperbound=true;}
                        else if(cs1 && !cs2){pulllowerbound=true; pullupperbound=false;}
                    }
                }else{//i==1
                    if(mide){
                        if(getOff(mid)-1 <= x[1])cs1=true;//C-1<=start
                        if(x[1]<=getOff(mid+1)-1)cs2=true;//start<=D-1

                        if(cs1 && cs2){ret[1]=mid;done_x[1]=true;}
                        else if(!cs1 && cs2){pulllowerbound=false;pullupperbound=true;}
                        else if(cs1 && !cs2){pulllowerbound=true; pullupperbound=false;}
                    }else{
                        if(getOff(mid)   <= x[1])cs1=true;//D<=start
                        if(x[1]<=getOff(mid+1)-2)cs2=true;//start<=E-2

                        if(cs1 && cs2){ret[1]=mid;done_x[1]=true;}
                        else if(!cs1 && cs2){pulllowerbound=false;pullupperbound=true;}
                        else if(cs1 && !cs2){pulllowerbound=true; pullupperbound=false;}
                    }
                }
                if(DEBUG)System.err.println("              "+cs1+" "+cs2+" "+pullupperbound+" "+pulllowerbound);
                if(pullupperbound)
                    ub=mid-1
                            ;
                if(pulllowerbound)
                    lb=mid+1
                            ;
            }while(lb<=ub && !done_x[i]);
        }if(DEBUG)System.err.println("                done indexOf "+x[0]+" "+x[1]);
        if(DEBUG)System.err.println("ret="+ret[0]+" "+ret[1]);
        return(ret);
    }

    private void twoNewElements(int i){
        insertXnewElements(i,2);
    }
    /**
     * index i is wrt to getOff(]; so to get equivalent storage point on other arrays, we use i/2
     * same is case with all other operations on data arrays
     */
    private final void newElement(int i){
        insertXnewElements(i,1);
    }
    /**
     * 
     * @param i
     * @param x
     */
    private final void insertXnewElements(int i,int x){
        modCount ++;
        if(DEBUG)System.err.println("i="+i);
        if(DEBUG)System.err.println("x="+x);
        for (int j = 0; j < x; j++) {
            //store.insertElementAt((C)factory.newInstance(), i/2+1);
            if(carriesProperty)
                store.insertElementAt(new PropertyCarryingRangeAE<P>(0,0,null), i/2+1);
            else
                store.insertElementAt(new RangeArrayElement<P>(0,0), i/2+1);
        }
    }

    /**
     * Remove elements from <b>startIndex</b> to <b>endIndex</b> (these indices are wrt getOff(]) inclusive of start and start
     * @param startIndex
     * @param endIndex
     */
    private void removeElements(int startIndex,int endIndex){
        modCount ++;
        store.removeRange(startIndex/2, (endIndex/2)+1);
    }
    
    /**
     * Makes and returns a new copy of this RangeArray
     * @return A new copy of this RangeArray 
     */
    @Override
    public final RangeArray<P> copy(){
        UnsynchronizedVector<Range<P>> cs;
        synchronized(modLock){
            cs = (UnsynchronizedVector)store.clone();
        }
        RangeArrayImpl<P> ret = new RangeArrayImpl(
                this.store.capacityIncrement,
                cs,
                this.carriesProperty);
        return ret;
    }

    @Override
    public final String toString() {
        return RangeArrayUtils.toString(this);
    }   
    
    
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
    @Override
    public final void removeElement(long start, long end){
        addElement(new RangeArrayElement<P>(start,end), true);
    }
    
    /**
     * Adds an entry in this RangeArray.
     * Removes an entry if it is an antagonist entry ( {@link RangeArrayElement#isAnAntagonistEntry() } )
     * @param newEntry The new entry which is to be added to this RangeArray
     * @see #removeElement(neembuu.common.RangeArrayElement)
     * @see RangeArrayElement#isAnAntagonistEntry()
     * @throws RangeArrayElementRejectedByFilterException If file size was set to some value which is lesser than
     * {@link #MAX_VALUE_SUPPORTED } and the element being added is greater than this value
     */
    @Override
    public final Range<P> addElement(long start, long end, P property){
        //TODO: Why is there NOT a synchronized modlock here ?
        if(!carriesProperty)throw new UnsupportedOperationException("This particular range array does not carry property just as this was requested during creation");
        return addElement(new PropertyCarryingRangeAE<P>(start,end,property),false);
    }
    
    private Range<P> addElement(
            RangeArrayElement<P> newEntry,
            boolean isAnAntagonistEntry){
        // indexes of x1 & x2
        
        
        if(!canBeAnElementOfThis(newEntry))throw new RangeArrayElementRejectedByFilterException();
        if(isAnAntagonistEntry){
            removeElement_(newEntry.starting(), newEntry.ending());
            return null;
        }
        int[]ind;
        ind=indexOf(newEntry);
        int x1=ind[0],x2=ind[1];

        //xie=is xi even ?, where i=1,2
        boolean
                x1e=x1%2==0,
                x2e=x2%2==0;

        //indexes are equal or not
        boolean eq=x1==x2;

        //Our entry can dissolve (defined 100-200 lines below) with already present entry or not, dissolve x1=dx1
        boolean dx1=x1e?dissolves(newEntry,store.elementAt(x1/2)):false
                ,dx2=x2e?dissolves(newEntry,store.elementAt(x2/2)):false;        

        if(DEBUG){
            System.err.println("Adding entry");
            System.err.print("x1="+x1);
            System.err.print(" x2="+x2);
            System.err.print(" x1e="+x1e);
            System.err.print(" x2e="+x2e);
            System.err.print(" eq="+eq);
            System.err.print(" dx1="+dx1);
            System.err.println(" dx2="+dx2);
        }

        return findCaseAndAdd(newEntry.starting(), newEntry.ending(), x1, x2, dx1, dx2,newEntry, false);
        //return newEntry;
    }

    private void removeElement_(long start,long end){
        synchronized(modLock){
            RangeArrayElement<P> entryToRemove =
                    (RangeArrayElement<P>)new RangeArrayElement(start,end);
            int[]ind;
            ind=indexOf(entryToRemove);
            int x1=ind[0],x2=ind[1];

            //xie=is xi even ?, where i=1,2
            boolean
                    x1e=x1%2==0,
                    x2e=x2%2==0;

            //indexes are equal or not
            boolean eq=x1==x2;

            //Our entry can dissolve (defined 100-200 lines below) with already present entry or not, dissolve x1=dx1
            boolean dx1=false
                    ,dx2=false;

            //we want to remove entries, first we will ensure a NEW range value is created such as
            //toRemoveEntry.start---->toRemoveEntry.end
            //by explicitly specifying that this entries does not dissolve
            //then we will just delete this entry using some simple remove function in java.util.Vector

            findCaseAndAdd(start, end, x1, x2, dx1, dx2, entryToRemove, true);

            for (int j = 0; j < store.size(); j++) {
                if(store.get(j).equalsIgnoreProperty(entryToRemove) ){
                    store.remove(j);
                    return;
                }
            }
        }
    }

    private final Range<P> findCaseAndAdd(
                long s,
                long e,
                int x1,
                int x2,
                boolean dx1,
                boolean dx2,
                RangeArrayElement<P> newEntry,
                boolean caseOfDeletion
            ){

        

        //xie=is xi even ?, where i=1,2
        boolean
            x1e=x1%2==0,
            x2e=x2%2==0;
        //indexes are equal or not
        boolean eq=x1==x2;
        if(eq){
            //Case 1: new entry gets dissolved into an existing entry, completely or breaks the array
            if(x1e){
                //Graphically:
                //x1=x2; both are=2 (say)
                //array index           0       1       2 x1,x2 3       4       5
                //Offset value          A------>B       C------>D       E------>F
                //
                //
                //Meaning of this representation : (this shall not be repeated in the comments)
                //-----------------------------------------------------------------------------
                //offset means, distance (in terms of bytes) from the origin of the file, origin of the file is first readable byte.
                //  for example, if a file be Start{abcd}start then at offset=0 we get 'a' and at offset=2 we get 'c'.
                //getOff(] is a 1-dim array, in which even entries always represent a starting offset
                //  and odd entries represent ending offset
                //0 1 ..5 are indeces of this array
                //A------>B means from offset A to offset B (for example this could present an entry from 1024th offset of a file to 2048th offset, the state of the offset could be for instace = downloaded by torrent downloader, and not yet verified)
                //
//>>>>>>>>>>>>>>>>A------>B is ALWAYS INCLUSIVE OF A AND B. This means, when i say 2--->5, i mean 2,3,4,5 and NOT 2,3,4<<<<<<<<<<<<<<<<
                //
                //{2 x1,x2 3} means that x1, and x2 are both numerically equal to 2,
                //  this means that the entry represented by x1 and x2 lie between 2 and 3
                //  for example , let starting offset(start)=5 and start=7 , C=3 and D=10, then it'start easy to understand that 5--->7 lies inside (of is subset of) 3----->10
                //
//>>>>>>>>>>>>>>>>Since we use a one dim array instead of a two dimensional one (one storing starting offset and other storing ending offset),
//>>>>>>>>>>>>>>>>We ALWAYS REPRESENT THE ENTRY BY THE LOWER INDEX<<<<<<<<<<<<<<<<<<<<<<<<<<
                //example [0]=A----->[1]=B (just like above). To represent this entry, instead of saying from 0 to 1, we say only 0. It means it is a range, and includes offsets [0]=A to [1]=B
                //another example, getOff(x1] represents the starting offset of (x1)/2 th entry(notive that x1 is always even).
                //  getOff(x1+1] represents the ending offset of entry at (x1)/2  th entry
                if(dx1){
                    //dissolved completely so making this entry is not required :)
                    //this case may never arise :(
                    //this case means :
                    //1) the entry range (start=startingoffset to start=endingoffset) already exists
                    //2) the entry exists as a sub-entry (lies inside an existing entry) or as an entire entry
                    //Graphically:
                    //x1=x2; both are=2 (say)
                    //array index           0       1       2-x1,x2-3       4       5
                    //Offset value          A------>B       C------>D       E------>F

                    //not modified at all
                    //modifiedAround(,);
                    return store.elementAt(x1/2);
                }else{
                    //our new insertion broke the array
                    //Graphically (solution only):
                    //index                 0       1       x1=2    3       First New Entry=4       5     Second New Entry=6        7       8       9
                    //Offset value          A------>B       C----->s-1      s---------------------->e       e+1-------------------->D       E------>F

                    //adds two extra entries after x1+1, none of the entries are modified,
                    //just 2 empty spaces added, so that we get something like shown above
                    //The entries which came after x1, in the orginal array, are moved forward
                    twoNewElements(x1);

                    //we first modify later entry first, and then modify the previous entry. Because the later entry uses a value from the previous entry. Just check the order below

                    modifyEntry(x1+4/*=6*/,e+1,getOff(x1+1)/* =D */,store.elementAt(x1/2));//request frequency is distributed amongst broken elements
                    modifyEntry(x1/*=2*/,getOff(x1)/*=C*/,s-1,store.elementAt(x1/2));//the greater part of the ditribution is kept here

                    modifyEntry(x1+2/*=4*/,s,e,newEntry);

                    //modified around C-->D
                    modifiedAround(getOff(x1)/*=C*/,getOff(x1+1)/* =D */,newEntry,ModificationType.CS1,caseOfDeletion,currentModCount());
                    return store.elementAt((x1+2)/2);
                }
            }else{//!x1e
                //Case 2: a new insertion at x1 (or x2), x1 and x2 are ODD, and also equal.
                // This means, that x1, x2 lie between 2 entires, and thus don't exist. So, they must be added.
                //Graphically:
                //x1=x2; both are=1 (say)
                //array index           0       1-x1,x2-2       3       4       5
                //Offset value          A------>B       C------>D       E------>F

                //we make a new element between 1 and 3 in the above example
                //an entry is always represented by the even/lower index, that'start why we make newElement at (x1 -1)
                newElement(x1-1);
                //we get, a empty and we fill it, using command below
                modifyEntry(x1+1,s,e,newEntry,ModificationType.CS2);
                //we get
                //Graphically:
                //x1=x2; both are=1 (say)
                //array index           0       1       (x1+1)=2     3       4       5      6       7
                //Offset value          A------>B       s----------->e       C------>D      E------>F


                // start>=B+1,start<=C-1 ; if this condition is not satisfied, then it would be different case, handled below (not the immediate one)z

                //modified around s-->e
                modifiedAround(s,e,newEntry,ModificationType.CS2,caseOfDeletion,currentModCount());
                return store.elementAt((x1+1)/2);
            }
        }

        // From now onwards since x1 != x2 means that the entry being added, may include the alread existing entry.
        //      Thus the array may shorten.
        // There are cases when the array length will increase, but that'start not case 3:

        //Case 3: New insertion, but we have space. (maybe more than needed)
        // this case means, that there is atleast 1 entry in between x1 and x2, there may be several entries between x1 and x2
        //original====
        //array index           0       1   x1  2       3       4       5   x2  6       7
        //Offset value          A------>B       C------>D       E------>F       G------>H
        // x1=1 and x2=5 . This means start(statring offset) lies between B and C and start lies between D and E

        //Solution==
        //array index           0       1       x1+1=2  3       4       5
        //Offset value          A------>B       s------>e       G------>H

        // This new entry ate all entries that lied inside it.
        // Those entires may be same (properties like downloader,etc match) and incomplatible,
        // but we don't worry about that, because this new value overrides the previous one
        if(!x1e&&!x2e){
            // We remove all entries from (x1+3) to (x2-1) inclusive of both these limits.
            // Thus we are left with one space, and rest all is deleted.
            // We use this one space that we left. We put out entry in this place.
            // for this example
            //      because x1+3=4 and x2-1=4 , thus the single entry 4 to 5 is deleted
            //      yet again here also we use only even indices for refering
            //      and yet again, the parameters of removeElements are inclusive
            modifyEntry(x1+1,s,e,newEntry);
            Range toRet = store.elementAt((x1+1)/2);
            if(x2-x1>=4){//we have atleast one extra space
                removeElements(x1+3, x2-1);
            }
            //modified around s-->e
            modifiedAround(s,e,newEntry,ModificationType.CS3,caseOfDeletion,currentModCount());
            return toRet;
        }
        //case 9: upper breaks, lower breaks, these two breaks, and new entry adds up as a single entry
        // so similar to case 3: and so specified just after it
        // But here there is a catch
        // We also have the condition !dx1 and !dx2 which means,
        //  although the entires x1 and x2 lie inside existing entires, they don't dissolve inside them
        //  What does this mean? It means, the downloader which downloaded from C to D didn't download start (starting offset) to start
        //      And, the downloader which downloaded from E to F didn't download start to start.
        //      This may seem odd, since x1 to D and E to x2 have already been downloaded by some downloader.
        //      But the data downloaded may be corrupt, so they have been re-downloaded.
        //          The downloader may not be the same downloader.
        // So dissolve means, that the entry lies inside and also the properties (like downloader) match.
        // Othwise they don't dissolve (like coldwater and hotwater) or are immisible (like oil and water)
        // Later, there are cases where entries dissovle partially or fully.
        //
        //original ex1====
        //array index           0       1       2   x1  3       4   x2  5       6       7
        //Offset value          A------>B       C------>D       E------>F       G------>H
        //x1=2 and x2=4
        //Solution ex1===
        //array index           0       1       2       3       4       5       6       7       8       9
        //Offset value          A------>B       C---->s-1       s------>e       e+1---->F       G------>H
        //
        //original example2====
        //array index           0       1       2   x1  3       4       5       6   x2  7       8       9
        //Offset value          A------>B       C------>D       E------>F       G------>H       I------>J
        //x1=2 and x2=6
        //Solution ex2===
        //array index           0       1       2       3       4       5       6       7       8       9
        //Offset value          A------>B       C---->s-1       s------>e       e+1---->H       G------>H
        //
        //original example3====
        //array index           0       1       2   x1  3       4       5       6       7       8   x2  9       10      11
        //Offset value          A------>B       C------>D       E------>F       G------>H       I------>J       K------>L
        //x1=2 and x2=8
        //.....................................................................^^delete^^^.....
        //Solution ex3===
        //array index           0       1       2       3       4       5       6       7       8       9
        //Offset value          A------>B       C---->s-1       s------>e       e+1---->J       K------>L

        //we first modify,resize and then add. Because after we resize the array, we aren't sure which element is where
        if(x1e&&x2e&&!dx1&&!dx2){
            modifyEntry(x1,getOff(x1)/*original starting value remains*/,s-1,store.elementAt(x1/2));
            modifyEntry(x2,e+1,getOff(x2+1)/*original ending value remains*/,store.elementAt(x2/2));
            
            //case explained in example 1
            if(x2-x1==2){
                newElement(x1);
                modifyEntry(x1+2,s,e,newEntry);
                Range toRet = store.elementAt((x1+2)/2);; 
                //modified around s-->e
                modifiedAround(s,e,newEntry,ModificationType.CS9,caseOfDeletion,currentModCount());
                return toRet;
            }

            //case explained in example 2
            //as we know, the entry before and after this one, have been already modified
            if(x2-x1==4){
                modifyEntry(x1+2,s,e,newEntry);
                Range toRet = store.elementAt((x1+2)/2);; 
                //modified around s-->e
                modifiedAround(s,e,newEntry,ModificationType.CS9,caseOfDeletion,currentModCount());
                return toRet;
            }
            //case explained in example 2
            //we make our entry at x1+2, so we start removing from x1+4
            //the x2 th entry must not be deleted, so the lower limit is x2-2
            //we modify x1+2 and our entry exists there.
            ////// already done >> modifyEntry(x1+2,start,start,entryToRemove);
            //^^^ i don't know what above says but it is wrong
            //since case of example 2 solved already

            //case explained in example 3
            if(x2-x1>=6){
                modifyEntry(x1+2,s,e,newEntry);
                Range toRet = store.elementAt((x1+2)/2);; 
                removeElements(x1+4, x2-2);
                //modified around s-->e
                modifiedAround(s,e,newEntry,ModificationType.CS9,caseOfDeletion,currentModCount());
                return toRet;
            }
            //this will never be called
            //modified around s-->e
            modifiedAround(s,e,newEntry,ModificationType.CS9,caseOfDeletion,currentModCount());
            return newEntry;
        }
        //Case 4: An entry expands ahead
        //  (may break original,
        //      that effectively meaning this is not an expansion)

        //original example1====
        //array index           0       1       2   x1  3       4       5       6       7       8       9   x2  10      11
        //Offset value          A------>B       C------>D       E------>F       G------>H       I------>J       K------>L
        //x1=2 is even and x2(=9) is thus odd (because as of case 3== if(!x1e&&!x2e)) means both x1 and x2 aren't odd together
        //so if one is odd, other must be even, if not then the function should have stopped at case 3 itself.
        //Solution ex1 (case when x1 dissolves in  C--->D )===
        //array index           0       1       2       3       4       5
        //Offset value          A------>B       C------>e       K------>L
        //
        //Solution of example 1 (case when x1 doesn not dissolve in C-->D.)
        //array index           0       1       2       3       4       5       6       7
        //Offset value          A------>B       C---->s-1       s------>e       K------>L

        //example 2====
        //array index           0       1       2   x1  3   x2  4       5       6       7
        //Offset value          A------>B       C------>D       E------>F       G------>H
        //solution:::
        //array index           0       1       2       3       4       5       6       7       8       9
        //Offset value          A------>B       C---->s-1       s------>e       E------>F       G------>H
        //EXPANDS ahead means that an exisiting entry'start upper limit chnages to a greater value
        //  example
        //      entry=10-->15 new entry 12---->17 the original entry expands to 10---->17
        // But there are cases when the newentry is immissible in an existing entry, this breaks, and result is
        // 10-->11 and 12--->17 according to the above example.
        //This is called BREAK.
        //
        //There may also be entries in the way (between x1 and x2 like shown in the example above), but that makes no difference, since
        //whether missible or immisible, they will be absorbed (also defined above, absorbed means, they are removed, because they are a subset of the newentry itself)
        if(!x2e){
            //case for dissolve
            if(dx1){
                //case like 10--->15 and new dissolvable entry 12--->17 result = 10--->17
                if(x2-x1==1){
                    modifyEntry(x1,getOff(x1),e,store.elementAt(x1/2));
                    //modified around C-->e
                    modifiedAround(getOff(x1),e,newEntry,ModificationType.CS4,caseOfDeletion,currentModCount());
                    return store.elementAt((x1)/2);
                }//x2-x1>1
                //we have few extra places, and we need to remove them
                modifyEntry(x1,getOff(x1),e,store.elementAt(x1/2));
                Range toRet = store.elementAt((x1)/2);; 
                removeElements(x1+2, x2-1);
                //modified around C-->e
                modifiedAround(getOff(x1),e,newEntry,ModificationType.CS4,caseOfDeletion,currentModCount());
                //getoff still works because elements are
                //removed after x1, elements before x1 are unaffected
                return toRet;
            }//the new entry does not dissolve
            modifyEntry(x1,getOff(x1),s-1,store.elementAt(x1/2));
            if(x2-x1==1){
                //instead of deleting places, we need to add one place because there isn't enough place
                newElement(x1);
                modifyEntry(x1+2,s,e,newEntry);
                //modified around s-->e
                modifiedAround(s,e,newEntry,ModificationType.CS4,caseOfDeletion,currentModCount());
                return store.elementAt((x1+2)/2);
            }
            //new entry does not dissolve, but we have extra space, and we may need to delete a few
            modifyEntry(x1+2,s,e,newEntry);
            Range toRet = store.elementAt((x1+2)/2);
            if(x2-x1>=5){//we have extra elements
                removeElements(x1+4, x2-1);
            }
            //modified around s-->e
            modifiedAround(s,e,newEntry,ModificationType.CS4,caseOfDeletion,currentModCount());
            return toRet;
        }

        //Case 5: Same as case 4, but an entry expands behine (to a lower index) instead
        //original example1====
        //array index           0       1       2       3   x1  4       5       6       7       8   x2  9       10      11
        //Offset value          A------>B       C------>D       E------>F       G------>H       I------>J       K------>L
        //x1=3 is odd and x2(=8) is thus even (because as of case 3== if(!x1e&&!x2e)) means both x1 and x2 aren't odd together
        //so if one is odd, other must be even, if not then the function should have stopped at case 3 itself.
        //Solution ex1 (case when x1 dissolves in  C--->D )===
        //array index           0       1       2       3       4       5      6       7
        //Offset value          A------>B       C------>D       s------>J      K------>L
        //
        //Solution of example 1 (case when x1 doesn not dissolve in C-->D.)
        //array index           0       1       2       3       4       5       6       7       8       9
        //Offset value          A------>B       C------>D       s------>e       s+1---->J       K------>L

        //example 2====
        //array index           0       1       2       3   x1  4   x2  5       6       7
        //Offset value          A------>B       C------>D       E------>F       G------>H
        //solution:::
        //array index           0       1       2       3       4       5       6       7       8       9
        //Offset value          A------>B       C------>D       s------>e       s+1---->F       G------>H
        //
        //to solve case 5, we make substitutions in case 4, because these 2 cases are symmetrical
        //case 4 <-> case 5
        //x1 dissolved <-> x2 dissovled
        //x1+2 <-> x2-2   in general x1+n <-> x2-n
        //x2-1 <-> x1+1   in general x2-m <-> x1+m
        //delete x1+2 <-> delete x2-2
        //insert x1+2 <-> insert x2-2
        if(!x1e){
            //case when entry dissolves
            //case for dissolve
            if(dx2){
                //case like 10--->15 and new dissolvable entry 7--->12 result = 7--->15
                if(x2-x1==1){
                    modifyEntry(x2,s,getOff(x2+1),newEntry);
                    //modified around s-->J
                    modifiedAround(s,getOff(x2+1)/*J*/,newEntry,ModificationType.CS5_1,caseOfDeletion,currentModCount());
                    return store.elementAt((x2)/2);
                }//x2-x1>1
                //we have few extra places, and we need to remove them
                modifyEntry(x2,s,getOff(x2+1),newEntry);
                Range toRet = store.elementAt((x2)/2); 
                long offset_x2p1 = getOff(x2+1); // remove elements will change the value that we obtain
                //so we save this value
                removeElements(x1+1,x2-2);//x1+2, x2-1 <-> x1+1,x2-2
                //modified around s-->J
                modifiedAround(s,offset_x2p1,newEntry,ModificationType.CS5_2,caseOfDeletion,currentModCount());
                return toRet;
            }//the new entry does not dissolve
            modifyEntry(x2,e+1,getOff(x2+1),store.elementAt(x2/2));
            if(x2-x1==1){
                //instead of deleting places, we need to add one place because there isn't enough place
                newElement(x1-1);
                modifyEntry(x2,s,e,newEntry);
                Range toRet = store.elementAt((x2)/2); 
                //modified around s-->e
                modifiedAround(s,e,newEntry,ModificationType.CS5_3,caseOfDeletion,currentModCount());
                return toRet;
            }
            //new entry does not dissolve, but we have extra space, and we may need to delete a few

            modifyEntry(x2-2,s,e,newEntry);//x1+2 <-> x2-2
            Range toRet = store.elementAt((x2-2)/2);
            if(x2-x1>=5){//we have extra elements
                removeElements(x1+1, x2-4);//x1+4, x2-1 <-> x1+1,x2-4
            }
            //modified around s-->e
            modifiedAround(s,e,newEntry,ModificationType.CS5_4,caseOfDeletion,currentModCount());//we just have extra spaces, new entry is still undissolvable
            return toRet;
        }
        //Case 6: An relatively lower entry expands (ahead), while a upper entry breaks
        //case 7: or vice-versa
        //case 8: upper and lower expand and merge

        //these cases take place when x1 ans x2 both are even together
        //there are 3 sub-cases, there
        //original example1 (case 6 ==> newentry dissolves in C-->D but is immisible in E-->F, case 7 is just reverse of this, and case 8 is when entry dissolves in both C-->D and E--->F)====
        //array index           0       1       2   x1  3       4   x2  5       6       7       8       9       10      11
        //Offset value          A------>B       C------>D       E------>F       G------>H       I------>J       K------>L
        //Solution::::for case 6
        //array index           0       1       2       3       4       5       6       7       8       9       10      11
        //Offset value          A------>B       C------>e       e+1---->F       G------>H       I------>J       K------>L
        //Solution::::for case 7
        //array index           0       1       2       3       4       5       6       7       8       9       10      11
        //Offset value          A------>B       C---->s-1       s------>F       G------>H       I------>J       K------>L
        //Solution::::for case 8
        //array index           0       1       2       3       4       5       6       7       8       9
        //Offset value          A------>B       C------>F       G------>H       I------>J       K------>L

        //original example2
        //array index           0       1       2   x1  3       4       5       6       7       8   x2  9       10      11
        //Offset value          A------>B       C------>D       E------>F       G------>H       I------>J       K------>L
        //Solution::::for case 6
        //array index           0       1       2       3       4       5       6       7
        //Offset value          A------>B       C------>e       e+1---->J       K------>L
        //Solution::::for case 7
        //array index           0       1       2       3       4       5       6       7
        //Offset value          A------>B       C---->s-1       s------>J       K------>L
        //Solution::::for case 8
        //array index           0       1       2       3       4       5
        //Offset value          A------>B       C------>J       K------>L
        if(x1e && x2e){
            if(dx1){
                if(dx2){
                    //case 8 is here
                    modifyEntry(x1,getOff(x1),getOff(x2+1),null);
                    Range toRet = store.elementAt((x1)/2);; 
                    long offset_c = getOff(x1),offset_j = getOff(x2+1);
                    removeElements(x1+2,x2);
                    //modified around C-->J
                    modifiedAround(offset_c,offset_j,newEntry,ModificationType.CS8,caseOfDeletion,currentModCount());//we just have extra spaces, new entry is still undissolvable
                    return toRet;
                }
                //case 6

                long offset_c=getOff(x1),offset_f=getOff(x2+1);
                modifyEntry(x1,getOff(x1),e,null);//expanding upper entry
                Range toRet = store.elementAt((x1)/2);; 
                modifyEntry(x2,e+1,getOff(x2+1),store.elementAt(x2/2));//breaking lower entry
                removeElements(x1+2,x2-2);
                //modified around C-->F
                modifiedAround(offset_c,offset_f,newEntry,ModificationType.CS6,caseOfDeletion,currentModCount());//we just have extra spaces, new entry is still undissolvable
                return toRet;
            }if(dx2){
                //case 7 i.start. dx2 && !dx1
                long offset_c=getOff(x1),offset_f=getOff(x2+1);
                modifyEntry(x1,getOff(x1),s-1,store.elementAt(x1/2));//breaking upper entry
                modifyEntry(x2,s,getOff(x2+1),null);//expanding lower entry
                Range toRet = store.elementAt((x2)/2);; 
                removeElements(x1+2,x2-2);
                //modified around C-->F
                modifiedAround(offset_c,offset_f,newEntry,ModificationType.CS7,caseOfDeletion,currentModCount());//we just have extra spaces, new entry is still undissolvable
                return toRet; 
            }
        }
        return newEntry;
    }
    /**
     * Modify entry at i th index (wrt getOff(]) and place following values
     */
    private final void modifyEntry(int i,long s,long e,Range<P> copyPropertiesFrom){
        try{
            if(copyPropertiesFrom!=null){
                store.elementAt(i/2).copyPropertiesFrom(copyPropertiesFrom);
            }
            store.elementAt(i/2).setStarting(s);
            store.elementAt(i/2).setEnding(e);
        }catch(ArrayIndexOutOfBoundsException a){
            a.printStackTrace();
        }
    }

    private void modifyEntry(int i,long s,long e,RangeArrayElement<P> copyPropertiesFrom, ModificationType hint){
        try{
            if(copyPropertiesFrom!=null){
                if(hint==ModificationType.CS2){
                    store.setElementAt(copyPropertiesFrom, i/2);
                }else if(entriesNeverDissolve){
                    store.setElementAt(copyPropertiesFrom, i/2);
                }else{
                    store.elementAt(i/2).copyPropertiesFrom(copyPropertiesFrom);
                }
            }
            store.elementAt(i/2).setStarting(s);
            store.elementAt(i/2).setEnding(e);
        }catch(ArrayIndexOutOfBoundsException a){
            a.printStackTrace();
        }
    }
    private long getOff(int i){
        if(i%2==0){
            return store.elementAt(i/2).starting();
        }
        return store.elementAt(i/2).ending();
    }

    @SuppressWarnings(value="unchecked")
    public final RangeArray subRange(RangeArrayElement bounds){
        synchronized(modLock){
            int lower_index = this.indexOf(bounds.starting());
            if(lower_index < 0)lower_index = 0;
            if(lower_index >= this.size()*2-1){return null;}
            if(lower_index % 2 !=0 )lower_index++;
                lower_index /= 2;

            int higher_index = this.indexOf(bounds.ending());
            if(higher_index < 0)higher_index = 0;
            if(higher_index >= this.size()*2-1){return null;}
            if(higher_index % 2 !=0 )higher_index++;
                higher_index /= 2;

            RangeArrayImpl rangeArray = new RangeArrayImpl(this.carriesProperty,params);
            rangeArray.add(this.get(lower_index));
            if(lower_index == higher_index){
                return rangeArray;
            }
            System.arraycopy(
                    store.elementData,
                    lower_index+2/*+1 shift because of fake entry, +1 shift because first element already copied*/,
                    rangeArray.store.elementData,
                    2/*one fake entry, and one already copied*/,
                    higher_index - lower_index + 1);

            return rangeArray;
        }
    }

    public final BitSet getNewBitSetView(int blockSize){
        RangeArrayElement bounds = new RangeArrayElement(
                this.get(0).starting(), this.getFileSize());
        return getNewBitSetView(bounds, blockSize);
    }

    public final BitSet getNewBitSetView(Range bounds, int blockSize){
        if(RangeUtils.getSize(bounds)%blockSize!=0)throw new IllegalArgumentException("bounds.getSize()/blockSize should be and integer");
        BitSet bitSet = new BitSet((int)(RangeUtils.getSize(bounds)/blockSize));

        for (int j = 0; j < bitSet.length(); j++) {
            bitSet.set(j,containsCompletely(j, blockSize));
        }
        return bitSet;
    }

    /**
     * Used for making BitSet view out of a RangeArray
     * @param n the number of the piece, couting starts from 0
     * @elementSize Size of the bittorrent piece or block, whichever is required
     * @return true if and only if the region n*pieceSize--->(n+1)*pieceSize-1 exists fully
     */
    @Override
    public final boolean containsCompletely(int n, long elementSize){
        return containsCompletely(n, elementSize, true);
    }

    /**
     * Same as {@link #containsCompletely(int, long) }
     * @param dissovabilityComplex Is true implies that entries which have the same value
     * might also not dissolve because they are intrinsically undissolvable. In such a case
     * size of each entry has to be added to check is the given region exists as sum of more than
     * one entry
     * @return true if and only if the region n*pieceSize--->(n+1)*pieceSize-1 exists fully in
     * a single entry or multiple entries (dissovabilityComplex set to true)
     */
    @Override
    public final boolean containsCompletely(int n, long elementSize, boolean dissovabilityComplex){
        synchronized (modLock){
            int lower_index = this.indexOf(n*elementSize);
            if(lower_index < 0){return false;}
            if(lower_index >= this.size()*2-1){return false;}
            if(lower_index % 2 !=0 ){return false;}
            lower_index /= 2;

            int higher_index = this.indexOf((n+1)*elementSize - 1);
            if(higher_index < 0)return false;
            if(higher_index >= this.size()*2-1)return false;
            if(higher_index % 2 !=0 )return false;
            higher_index /= 2;

            if(lower_index == higher_index)return true;

            //System.err.println("rae(low)="+rangeArray.get(lower_index));
            //System.err.println("rae(high)="+rangeArray.get(higher_index));
            if(dissovabilityComplex)return false;

            long totalsize = 0;
            totalsize+= this.get(lower_index).ending() - n*elementSize/*inclusive therefor +1*/ + 1;
            totalsize+= (n+1)*elementSize /*exclusive therefore no +1*/ - this.get(higher_index).starting() ;
            for (int idx = lower_index + 1; idx < this.size() && idx < higher_index ; idx++) {
                totalsize+=
                        RangeUtils.getSize(this.get(idx));
            }

            //special check for last piece
            if((n+1)*elementSize > getFileSize()){
                //new elementSize
                elementSize = getFileSize() - n*elementSize;
            }

            //System.err.println(totalsize);
            return (totalsize == elementSize);
        }
    }

    /**
     * Returns -1 or size()*2-1 if not found.
     * If found between two different entries returns an odd value.<br/>
     * If found inside a entry returns an even value, which is twice the index at which it was found.<br/>
     * Example :<br/>
     * <br/>
     * <pre>
     * neembuu.common.RangeArray@c20e24{
     *  index=0 -100->-91 10
     *  index=1 0->1 2
     *  index=2 100->109 10
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
    @Override
    public final int indexOf(long absoluteIndex){
        int index = getIndexOf(absoluteIndex);
        //if(index < 0){throw new ArrayIndexOutOfBoundsException("not found,value="+index); }
        //if(index<0){ index = index+2;}
        /*else*/ index = index - 2; //two fake entries
        return index;
    }

    /**
     * this is same as indexOf but indexing system counts fake entries.
     * @param absoluteIndex
     * @return 
     */
    private final int getIndexOf(long absoluteIndex){
        /*C tmp=(C)factory.newInstance(absoluteIndex,absoluteIndex);
        int[]ind=indexOf(tmp);
        if(ind[0]%2!=0)return null;
        return store.elementAt((ind[0]/2));*/


        int mid,lb,ub;
        int index = -1;
        boolean mide,pullupperbound,pulllowerbound;//is mid even?
        //long ar address 0     1       2       3       4       5
        //values          A---->B       C------>D       E------>F
        //
        //if mid is even C<=start<=D ===> x1=mid
        //if mid is odd  B<start<C   ===> x1=mid

        boolean cs1,cs2; int numberOfItr = 0; int length = length()*2;
        lb=0;ub=/*size()*2;*/length()*2;//starting and ending fake entries inaccessible/accessible
        do{
            mid=(lb+ub)/2;
            mide=mid%2==0; //is mid even ?
            cs1=cs2=pulllowerbound=pullupperbound=false;
            if(numberOfItr > length){
                if(numberOfItr > 2*length){
                    // cannot happen unless package private or private functions have been misused
                    throw new RuntimeException("Stuck in an infinite loop in getIndexOf(long ) due to malformed RangeArray");
                }
                System.err.println("Stuck in an infinite loop in getIndexOf(long ). getOff[mid="+mid+"]="+getOff(mid));
            }numberOfItr++;
            if(mide){//mid is even
                if(getOff(mid) <= absoluteIndex)cs1=true;//C<=start
                if(absoluteIndex<=getOff(mid+1))cs2=true;//start<=D

                if(cs1 && cs2){index=mid;}
                else if(!cs1 && cs2){pulllowerbound=false;pullupperbound=true;}
                else if(cs1 && !cs2){pulllowerbound=true; pullupperbound=false;}
            }else{//mid is odd
                if(getOff(mid) < absoluteIndex)cs1=true;//B<=start
                if(absoluteIndex<getOff(mid+1))cs2=true;//start<=C

                if(cs1 && cs2){index=mid;}
                else if(!cs1 && cs2){pulllowerbound=false;pullupperbound=true;}
                else if(cs1 && !cs2){pulllowerbound=true; pullupperbound=false;}
            }


            if(pullupperbound)
                ub=mid-1;
            if(pulllowerbound)
                lb=mid+1;
        }while(lb<=ub && index==-1);

        return index;
        /*if(index%2==0)
            return index/2;
        return (((-1)*index/2)-1);*/
    }

    /**
     * Returns the RangeArrayElement at the specified position in this.
     *
     * <pre>
     *     0           1
     * 123--->212 4212--->11343
     *  propertyA  propertyB
     * </pre>
     *
     * Value at index 0 is propertyA and at 1 is propertyB <br/>
     * Value at absoluteIndex 123,124,125..... and 212 is propertyA. <br/>
     * Value at absoluteIndex 4212,4213.... and 11343  is propertyB. <br/>
     * @param absoluteIndex The absolute RangeArray index
     * @return the RangeArrayElement at the specified position in this list.
     * @see RangeArray#RangeArray(int, neembuu.common.RangeArrayElementFactory)
     */
    @Override
    public Range<P> get(long absoluteIndex){
        synchronized (modLock){
            int index = getIndexOf(absoluteIndex);
            if(index < 2 || index >= store.size()*2-1 )//do not allow access to fake entries
                return null;
            if(index%2==1)return null;//odd means found between 2 different entries
            return store.get(index/2);
        }
    }
    /**
     * Same as RangeArray#get(int) but allow access to fake entries.
     * The index system in both is same. <br/>
     * In RangeArray#get_checkFakeAsWell(int index) , at index=-1 and index=RangeArray#size()+1
     * return values instead of throwing ArrayIndexOutOfBoundsException.<br/>
     * @see #get(int)
     * @param index
     * @return  
     */
    public Range<P> get_checkFakeAsWell(int index){
        if(index<1 || index>=store.size()+1)//allow access to fake entries
            throw new ArrayIndexOutOfBoundsException(index);
        return store.get(index+1);
    }

    /**
     * Returns the RangeArrayElement at the specified position in this.
     *
     * <pre>
     *     0           1
     * 123--->212 4212--->11343
     *  propertyA  propertyB
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
    @Override
    public Range<P> get(int index){
        if(index<0 || index>=size())//do not allow fake entries
            throw new ArrayIndexOutOfBoundsException(index);
        return store.get(index+1);
    }

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
    @Override
    public final long getFileSize() {
        return fileSize;
    }

    /**
     * Set the file size value
     * @param fileSize The size of this file at the moment ( this value can be changed anytime )
     * @throws ArrayIndexOutOfBoundsException If the new value is lesser than the biggest value already present in this
     */
    @Override
    public void setFileSize(long fileSize) throws ArrayIndexOutOfBoundsException{
        if(store.get(store.size()-2).ending()>fileSize)
            throw new ArrayIndexOutOfBoundsException(
                    "New file size is lesser than already present values. Current file size="
                    +this.fileSize+" attempting to set file size ="+fileSize
            );
        
        if(this.fileSize==fileSize)return;
        
        this.fileSize = fileSize;
        announceToListeners(
            0,
            fileSize,
            null,
            ModificationType.FILE_SIZE_CHANGED,
            false,
            store.fileSizeChanged());
        
    }


    @Override
    public final int size(){
        return (length()-2);
    }

    @Override
    public long absoluteSize(){
        long ret ;
        ret = store.get(store.size()-1).ending()  +1 ;
        if(store.get(1).starting() < 0 ){
            ret += store.get(1).starting();
        }
        return ret;
    }

    @Override
    public final boolean isEmpty(){
        return length()<=2;
    }

    @Override
    public final void clear(){
        store.removeRange(1,length()-1);
    }

    @Override
    public final boolean add(Range<P> newEntry){
        if(contains(newEntry))return false;
        addElement(newEntry.starting(),newEntry.ending(),newEntry.getProperty());
        return true;
    }

    @Override
    public final boolean addAll(Collection<? extends Range<P>> collection){
        boolean ret=false;
        for ( Range<P> entry : collection){
            if(add(entry))ret=true;
        }
        return ret;
    }

    /**
     * Merges this RangeArray with another<br/>.
     * Uses System.arraycopy if possible (hence faster), otherwise uses addAll.
     * @see #addAll(java.util.Collection) 
     * @param mergeWith
     */
    @Override
    public final void merge(RangeArray<P> mergeWith_){
        if(!(mergeWith_ instanceof RangeArrayImpl))
            throw new UnsupportedOperationException("parameter "+mergeWith_
                    + " not an instance of "+RangeArrayImpl.class);
        RangeArrayImpl mergeWith = (RangeArrayImpl)mergeWith_;
        
        if(mergeWith.isEmpty())return;
        boolean simplyArrayCopy = false;
        if(isEmpty())simplyArrayCopy = true;
        int startIndex = this.getIndexOf(mergeWith.get(0).starting());//index taking fake entries into consideration
        int lastIndex = this.getIndexOf(mergeWith.getLastElement().ending());//index taking fake entries into consideration
        if(startIndex == lastIndex && lastIndex < 0)simplyArrayCopy = true;

        if(simplyArrayCopy){
            if(startIndex == Integer.MIN_VALUE){
                //means not found, the search doesnot consider
                startIndex = this.store.elementCount;
            }
            else startIndex *= -1;
            synchronized(modLock){
                //ensure capacity
                store.ensureCapacity(store.size() + mergeWith.size());
                //shift
                if(store.size() - startIndex - 1 > 0)
                    System.arraycopy(
                        store.elementData,
                        startIndex+1,
                        store.elementData,
                        startIndex+1+mergeWith.size(),
                        store.size() - startIndex - 1
                    );
                //copy entries
                System.arraycopy(
                    mergeWith.store.elementData,
                    1,//first one is just a fake entry
                    store.elementData,
                    startIndex+1,
                    mergeWith.size()
                );

                //inform store about this change
                this.store.elementCount+=mergeWith.size();
            }
        }else {
            addAll(mergeWith);
        }
        
    }
    
    
    
    

    @Override
    @SuppressWarnings(value="unchecked")
    public final boolean contains(Object o){
        if(!(o instanceof RangeArrayElement))return false;
        Range<P> entryToCheck = (Range<P>)o;

        int startIndex=indexOf(entryToCheck.starting());
        int endIndex=indexOf(entryToCheck.ending());

        //System.err.println("contains index="+startIndex+ " "+ endIndex);
        
        return (
                (startIndex>=0 && endIndex>=0) //lower bound condition
                &&
                (startIndex<this.size()*2-1 && endIndex<this.size()*2-1)//upper bound condition
                &&
                (startIndex%2==0 && endIndex%2==0 ) //odd means occurs in between of existing entries
        );
    }

    @SuppressWarnings(value="unchecked")
    @Override
    public final int containsAt(Object o){
        if(!(o instanceof RangeArrayElement))
            throw new IllegalArgumentException(o+" is not an instance of "+RangeArrayElement.class);
        Range<P> newEntry = (Range<P>)o;
        int[]ind=indexOf(newEntry);
        if(ind[0]%2==0){
            if(store.get(ind[0]/2).contains(newEntry))
                return (ind[0]/2 -1);
        }return -1;
    }

    @Override
    public final boolean containsPartially(Range<P> toCheck){
        int[]ind=indexOf(toCheck);
        if(ind[0]%2==0)return true;
        else {
            if(ind[0]%2==0)return true;
            if(ind[0]>ind[1])return true;
        }return false;
    }

    @Override
    public final boolean containsAll(Collection<?> collection){
        for(int j=1; j<store.size()-1 ;j++){
            Range<P> entry = (Range<P>) store.get(j);
            if(!contains(entry))
                return false;
        }
        return true;
    }

    @Override
    @SuppressWarnings(value="unchecked")
    public final Iterator<Range<P>> iterator() {
        class RA_Iterator implements Iterator<Range<P>>{
            int index;
            int expectedModCount = modCount;

            public RA_Iterator() {
                index=1;
            }

            @Override
            public boolean hasNext() {
                return (index < store.size() -1 );
            }

            @Override
            public Range<P> next() {
                checkForComodification();
                if(!hasNext())throw new NoSuchElementException();
                Range<P> ret = store.get(index);
                index++;
                return ret;
            }

            @Override
            public void remove() {
                store.remove(index);
            }

            final void checkForComodification() {
                if(modCount != expectedModCount)
                    throw new ConcurrentModificationException();
            }
        }

        return new RA_Iterator();
    }

    /**
     * Returns an iterator which has it 's first and last element
     * shifted (using RangeArrayElement#getShiftedTo(long)) such that 
     * this can be used to iterate exactly over the bounds.
     * This can be used for reading, such as in PartialFileFS.
     * The region for which iterator has to be provided is <b>copied</b> in
     * a thread safe manner, and then an iterator is constructed around it.
     * Changes at individual elements are reflected in the copies,
     * but changes such as removal of an element from this rangearray cannot be observed
     * in the copy.
     * @see RangeArrayElement#getShiftedTo(long) 
     * @param bounds The region for which the iterator is required
     * @throws UnsupportedOperationException It is possible that the implementing class does not chose to properly implement this method
     */
    public final Iterator<Range<P>> iteratorOver(final RangeArrayElement bounds){
        int[]x = getIndexPair(bounds);
        if(x==null)return null;
        int lastElementIndex=x[1]+1,firstElementIndex=x[0]+1;

        RangeArrayElement[] regionData = new RangeArrayElement[
                    /*actualIndex*/lastElementIndex - /*actualIndex*/firstElementIndex+1];
            /*synchronized(System.err){
                System.err.println"---");
                System.err.println(this.store.size());
                System.err.println(firstElementIndex);
                System.err.println(regionData.length);
                System.err.println("---");
            }*/
            System.arraycopy(
                    this.store.elementData,
                    /*actualIndex*/firstElementIndex,
                    regionData,
                    0,
                    regionData.length);
        if(regionData[0].starting() < bounds.starting() ){
            regionData[0] = regionData[0];//.getShiftedTo(bounds.starting(), regionData[0].ending());
        }
        if(regionData[regionData.length-1].ending() < bounds.ending() ){
            regionData[regionData.length-1] =
                    regionData[regionData.length-1]/*.getShiftedTo(
                        bounds.ending(), regionData[regionData.length-1].ending()
                    )*/;
        }
        //return new ShiftedIterator<Range<P>>(regionData);
        throw new UnsupportedOperationException();

    }

    @Override
    public final int[] getIndexPair(long boundsStart, long boundsEnd){
        return getIndexPair(new RangeArrayElement(boundsStart,boundsEnd));
    }
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
     *      RangeArrayElement nextRAE = rangeArray.get(j);
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
    public final int[] getIndexPair(RangeArrayElement bounds){
        if(this.isEmpty())return new int[]{-1,-1};
        synchronized(modLock){
            //array index           0       1       2   x1  3       4       5       6       7       8       9  x2   10      11
            //Offset value          A------>B       C------>D       E------>F       G------>H       I------>J       K------>L

            // If x1 represents startIndex, RangeArrayElement at x1 is shifted
            // If x2 represents endIndex, There exists on RangeArrayElement at x2


            //all indixes below are non-virtual array indexes
            //they include fake entries
            //to avoid fake entries we add or subtract at places

            /*if(size()==1){
                //     * Index                       -1                      0      
                //* Offset value   -infinity----> -infinity          1000------>1200
                //                    to find index pair of (500->600)
                //                or  to find index pair of (500-------->1100)
                //                or  to find index pair of (500---------------------->1600)
                if(bounds.starting() < this.get(0).starting() ){
                    if(bounds.ending() < this.get(0).starting()){
                        //to find index pair of (500->600) in above example
                        return new int[]{-1,-1};
                    }
                    //to find index pair of (500-------->1100) in above example
                    //or  to find index pair of (500---------------------->1600)
                    return new int[]{-1,0};// there is only one entry
                }
                //     * Index                       -1                      0      
                //* Offset value   -infinity----> -infinity          1000------>1200
                //                              to find index pair of (1100---------------->1600)
                //                                        or  to find index pair of (1500-->1600)
                return new int[]{0,0};// there is only one entry
                // there is nothing else that can be done
            }*/
            
            //special case
            if(bounds.starting() < this.get(0).starting() ){
                if(bounds.ending() < this.get(0).starting()){
                    //to find index pair of (500->600) in above example
                    return new int[]{-1,-1};
                }
            }

            //getBounds gives twice the value at which index was found, read it's javadoc for more info
            int firstElementIndex/*twiceIndex*/  = this.getIndexOf(bounds.starting());
            if(firstElementIndex/*twiceIndex*/ <= 1 ){ // the first entry is fake, so if index lies here, it means there is no element there
                //firstElementIndex/*twiceIndex*/ = 2 ;// 2/2 =1 , we skipped 0 because it is a fake entry
            }else if(firstElementIndex/*twiceIndex*/ > length()*2 - 2 - 1 ){ // -2 to ignore fake entries, actually unlike indexes at the lower end, indexes at the upper end never point to upper fake entries
                // this also happens if there is only one entry
                // of checking for that
                // another -1 because counting is exclusive
                return null; // the region in which we wish to iterate is beyond what exists in the accessible array
            }
            if(firstElementIndex/*twiceIndex*/%2==-1){
                firstElementIndex = -2; // this is the only possible case
                // because fake entry is negative infity, our firstElementIndex 
                // cannot have an index smaller index of negative infinity.
            }
            else if(firstElementIndex/*twiceIndex*/%2==1){// region found but between 2 entires, something like  :
                //array index           0       1   x1  2       3       4       5       6       7       8       9       10      11
                //Offset value          A------>B       C------>D       E------>F       G------>H       I------>J       K------>L
                firstElementIndex/*twiceIndex*/--; // will definitely cause error if negative rae entries are allowed
            } firstElementIndex/=2;//changed to actualIndex


            int lastElementIndex/*twiceIndex*/ = this.getIndexOf(bounds.ending());
            if(lastElementIndex/*twiceIndex*/ <= 1 ){
                lastElementIndex/*twiceIndex*/ = 2; // array.copy is end is exclusive
            }else if(lastElementIndex/*twiceIndex*/ >= length()*2 - 2 - 1 ){
                lastElementIndex/*twiceIndex*/ = length()*2 - 4;
            }
            if(lastElementIndex/*twiceIndex*/%2==1){// region found but between 2 entires, something like  :
                //array index           0       1       2       3       4       5       6       7       8       9  x2   10      11
                //Offset value          A------>B       C------>D       E------>F       G------>H       I------>J       K------>L
                //lastElementIndex/*twiceIndex*/+;
            } lastElementIndex/=2;//changed to actualIndex


            return new int[]{firstElementIndex-1,lastElementIndex-1};
            //size = /*inclusive*/last - /*inclusive*/start + 1
        }
    }

    @Override
    public final int currentModCount(){
        return store.getModCount();
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
    public Range<P> getFirst() {
        return store.get(1);
    }

    @Override
    public final UnsyncRangeArrayCopy<P> tryToGetUnsynchronizedCopy() {
        return store.tryToGetUnsynchronizedCopy(modLock);
    }
    
    @Override
    public final Range getUnsynchronized(long index) {
        return store.getUnsynchronized(index);
    }

    static final class ShiftedIterator<C extends RangeArrayElement> implements Iterator<C>{
        private final RangeArrayElement[]regionData;
        private int index = 0;

        public ShiftedIterator(RangeArrayElement[] regionData) {
            this.regionData = regionData;
        }

        @Override
        public boolean hasNext() {
            return index < regionData.length;
        }

        @Override
        @SuppressWarnings(value="unchecked")
        public C next() {
            return (C)regionData[index++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported as this is a copy.");
        }

    }

    @Override
    public final Iterator<Range<P>> absoluteIterator(){
        return new AbsoluteIterator<P>(this);
    }

    protected static final class AbsoluteIterator<P> implements Iterator<Range<P>>{
        long absoluteindex;
        RangeArrayImpl<P> rangeArray;

        public AbsoluteIterator(RangeArrayImpl<P> rangeArray) {
            absoluteindex = rangeArray.store.get(1).starting(); this.rangeArray = rangeArray;
        }

        @Override
        public boolean hasNext() {
            return (
                    absoluteindex >= rangeArray.store.get(1).starting()
                    &&
                    absoluteindex <= rangeArray.store.get(rangeArray.store.size()-2).ending()
            );
        }

        @Override
        public final Range<P> next() {
            checkForComodification();
            if(!hasNext())throw new NoSuchElementException();
            Range<P> ret = rangeArray.get(absoluteindex);
            absoluteindex++;
            return ret;
        }

        public final long getPosition() {
            return absoluteindex-1;
        }

        @Override
        public final void remove() {
            //remove(absoluteindex);
            rangeArray.removeElement(absoluteindex-1, absoluteindex-1);
        }

        final void checkForComodification() {
            //There is no change in this no matter what.
            //Absolute index is always safe

            //if (modCount != expectedModCount)
            //throw new ConcurrentModificationException();
        }
    }

    @Override
    @SuppressWarnings(value="unchecked")
    public final boolean remove(Object o) {
        Range<P> toRemove = (Range<P>)o;
        boolean ret = containsPartially(toRemove);
        removeElement(toRemove.starting(), toRemove.ending());
        return ret;
    }

    @Override
    public final boolean removeAll(Collection<?> c) {
        Iterator<?> i=c.iterator();
        boolean ret=false;
        while( i.hasNext() ){
            if(remove(i.next()))
                ret=true;
        }
        return ret;
    }

    @Override
    @SuppressWarnings(value="unchecked")
    public final boolean retainAll(Collection<?> c) {
        Iterator i=c.iterator();
        boolean ret=false;
        RangeArrayImpl<P> newArray=new RangeArrayImpl<P>(4,this.dissolvabilityRules,this.getFileSize(),this.carriesProperty,params);
        

        while( i.hasNext() ){
            Range<P> toRetain = (Range<P>) i.next();
            int loc =  containsAt(toRetain);
            if(loc > 0 && loc < store.size() -1 ){
                RangeArrayElement<P> elementWithProperties = store.get(loc);
                newArray.addElement(elementWithProperties,false);
            }
        }
        synchronized(modLock){
            if(true)throw new UnsupportedOperationException("unsupported for sake of immutable unsyc array reference");
            //this.store=newArray.store;
        }
        return ret;
    }

    @Override
    public Object[] toArray() {
        Object[]a=new Object[this.size()];
        System.arraycopy(
                    this.store.elementData,
                    1, // 1st is fake
                    a,
                    0,
                    this.size());
        return a;
            
    }

    /**
     * Use this is painting graphical components and other things,
     * so that unnecessary locking on the rangearray is not required.
     */
    @Override
    public final UnsynchronizedAccess getUnSynchronizedArrayAccess(){
        return this;
    }
    
    
    
    @Override
    @SuppressWarnings(value="unchecked")
    public <T> T[] toArray(T[] a) {
        synchronized(modLock){ // or a atomic lock
            if (a.length < this.size())
                a = (T[])java.lang.reflect.Array.newInstance(
                                    a.getClass().getComponentType(), this.size());
            System.arraycopy(
                    this.store.elementData,
                    1, // 1st is fake
                    a,
                    0,
                    this.size());
            
            if (a.length > this.size())
                a[this.size()] = null;

            return a;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof RangeArrayImpl))return false;
        RangeArrayImpl toCompare = (RangeArrayImpl)obj;
        return notEqualsGetReason(toCompare)==null;
    }

    public String notEqualsGetReason(RangeArrayImpl toCompare) {
        if(toCompare.size()!=this.size()){
            return "size not equal";
        }
        for (int i = 0; i < this.size(); i++) {
            if(!this.get(i).equals(toCompare.get(i))){
                return "failed at="+toCompare.get(i)+" "+this.get(i);
            }
        }
        return null;
    }



    @Override
    @SuppressWarnings(value="unchecked")
    public final boolean canBeAnElementOfThis(Range<P> element) {
        if (element.ending() > fileSize) {
            return false;
        }
        if(rangeArrayElementFilters==null)return true;
        if(rangeArrayElementFilters.length>0){
            for(RangeArrayElementFilter filter : rangeArrayElementFilters){
                if(!filter.canBeAnElementOfThis(element))return false;
            }
            
        }return true;
    }

    /**
     * We can also perform parallel summing ... but that is a java7
     * thing
     * @param bounds null means sum over entire region
     * @return
     */
    @Override
    public long getElementsSum(long start, long end){
        RangeArrayElement bounds = new RangeArrayElement(start,end,null);
        synchronized(modLock){
            int lower_index,higher_index;
            /*if(bounds==null){
                lower_index = 0;
                higher_index = this.size()-1;
            }else{*/
                lower_index = this.indexOf(bounds.starting());
                if(lower_index < 0){return 0;}
                if(lower_index >= this.size()*2-1){return 0;}
                if(lower_index % 2 !=0 ){lower_index++;}
                lower_index /= 2;

                higher_index = this.indexOf(bounds.ending());
                if(higher_index < 0)return 0;//cannot happen
                if(higher_index >= this.size()*2-1){higher_index = this.size()*2-2;}
                if(higher_index % 2 !=0 ){higher_index--;}
                higher_index /= 2;
            //}
            long sum = 0;
            for (int i = lower_index; i <= higher_index; i++) {
                sum+=RangeUtils.getSize(this.get(i));
            }

            return sum;
        }
    }

    //+++++++++++++EXTRA UTILITY METHODS++++++++++++++++++

    /**
     *
     * @param toFindIndexOfEntry The entry whoes index is to be found
     * @return -1 is not found, else the index of the entry
     */
    @Override
    public int getIndexOf(Range<P> toFindIndexOfEntry){
        int index=-1;
        int lowerBound=0, upperBound=size();
        do{
            int mid = (lowerBound + upperBound) /2;
            int compareValue = 
                    RangeUtils.compare(get(mid),toFindIndexOfEntry);
                    //get(mid).compareTo(toFindIndexOfEntry);
            if(compareValue==0){
                index = mid; break;
            }
            if(compareValue<0){
                lowerBound = mid+1;
            }
            else{
                upperBound = mid-1;
            }

        }while(index==-1 && upperBound>=lowerBound );

        return index; 
    }
    /*public int getIndexOf(long absoluteIndex){
        C toFindIndexOfEntry=(C)factory.newInstance(absoluteIndex,absoluteIndex);
        return getIndexOf(toFindIndexOfEntry);
    }*/

    public long totalBytesInEntries(){
        long total = 0;
        for(Range ele : this){
            total += RangeUtils.getSize(ele);
        }
        return total;
    }
    /**
     * Reduces the size of the array (if an array is used) used to store elements.
     * See {@link Vector#trimToSize() }
     */
    public void trimToSize(){
        store.trimToSize();
    }

    @Override
    public Range<P> getLastElement(){
        return store.get(store.size()-2);//-1 for size cannot be accessed, and another because last one is fake
    }
    //-------------EXTRA UTILITY METHODS------------------

    private void modifiedAround(
            long modificationResultStart,
            long modificationResultEnd,
            Range elementOperated, 
            ModificationType modificationType,
            boolean caseOfDeletion,
            long modCount){
        if(DEBUG){
            System.err.println("modificationResultStart="+modificationResultStart+
            " modificationResultEnd="+modificationResultEnd+
            " elementOperated="+elementOperated+
            " modificationType="+modificationType+
            " caseOfDeletion="+caseOfDeletion+
            " modCount="+modCount);
            System.err.println(this);
        }
        announceToListeners(
            modificationResultStart, 
            modificationResultEnd, 
            elementOperated, 
            modificationType, 
            caseOfDeletion,
            modCount);
    }
    
    /**
     * RangeArrayElement which can expand, i.e. increase the value of it's end
     * should use this to ensure it doesn't conflict with existing ones.
     * @see #expandVeryCarefully(neembuu.common.RangeArray, long)
     * @param rangeArray The array containing this element
     * @param desiredNewEnd desired value of end for this
     */
    @Override
    public final long expandCarefully(Range<P> rae_,final long desiredNewEnd){
        RangeArrayElement rae = (RangeArrayElement)rae_;
        final long oldending = rae.ending();
        if(desiredNewEnd < oldending){
            throw new IllegalArgumentException("Attempting contraction instead of expansion.\n" +
                    "For new end = "+desiredNewEnd +"\nElement = "+this);
        }
        if(desiredNewEnd == oldending )return desiredNewEnd;
        RangeArrayElement next = null;
        long modCount_toret; long actualNewEnd = desiredNewEnd;
        long endingOfNext = desiredNewEnd;
        long startingOfNext = desiredNewEnd;
        synchronized(modLock){
            int myindex = indexOf(rae.starting())/2;
            if(myindex + 1 < size() )
                next = (RangeArrayElement)get(myindex+1);
            if(next !=null ){
                endingOfNext = next.ending();
                startingOfNext = next.starting();
                
                if(actualNewEnd >= startingOfNext){
                    actualNewEnd = startingOfNext - 1;
                }
            } // else no entry exists beyond this, so obviously we can expand as much as we like till file size
            // if file size is hit, we shall expand filesize jpfm cannot report this on Windows.
            rae.setEnding(actualNewEnd);
            modCount_toret = currentModCount();
        }
        // ending has been set, (safe) The other thread (AWT dispatch thread) will see the new ending, even though this function has not returned.
        if(actualNewEnd < startingOfNext){
            announceToListeners(
                oldending,
                desiredNewEnd,
                rae_,
                ModificationType.ELEMENT_INTRINSICALLY_EXPANDED,
                false,
                modCount_toret);
        }else {
            announceToListeners(
                rae_.starting(),
                endingOfNext,
                rae_,
                ModificationType.EXPANDING_ELEMENT_COLLIDED,
                false,
                modCount_toret);
        }
        return /*desiredNewEnd*/actualNewEnd;
    }

    @Override
    public final boolean rangeObjectsImplementedAsImmutable() {
        return false;
    }

    @Override
    public final Range<P> setProperty(Range<P> range, P Property) {
        if(!(range instanceof RangeArrayElement))
            throw new IllegalArgumentException(range+" is not an element of this");
        //assume contains although that not might be the case
        //that does not conflict with the design choices....although ugly
        ((PropertyCarryingRangeAE)range).setProperty(Property);
        return range;
    }
    
    @Override
    public final void addRangeArrayListener(RangeArrayListener ral){
        synchronized (rangeArrayListeners){
            rangeArrayListeners.add(WeakListeners.create(RangeArrayListener.class, ral, this));
        }
    }
    
    @Override
    public final void removeRangeArrayListener(RangeArrayListener ral){
        synchronized (rangeArrayListeners){
            rangeArrayListeners.remove(ral);
        }
    }

    @Override
    public DissolvabilityRule<P>[] getDissolvabilityRules() {
        return dissolvabilityRules;
    }
        
    final void announceToListeners(
            long modificationResultStart,
            long modificationResultEnd,
            Range elementOperated, 
            ModificationType modificationType,
            boolean removed,
            long modCount){
        //synchronized (rangeArrayListeners){
            for (RangeArrayListener listener : rangeArrayListeners){
                listener.rangeArrayModified(
                        modificationResultStart, 
                        modificationResultEnd, 
                        elementOperated, 
                        modificationType,
                        removed,
                        modCount);
            }
        //}
     }

    @Override
    public final boolean dissolves(Range<P> newEntry, Range<P> entryToCheckThatAlreadyExistsInThisArray) {
        for(DissolvabilityRule<P> rule : dissolvabilityRules){
            if(!rule.dissolves(newEntry, entryToCheckThatAlreadyExistsInThisArray))return false;
        }return true;
    }

 }