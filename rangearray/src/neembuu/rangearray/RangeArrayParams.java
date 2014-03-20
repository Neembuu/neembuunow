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
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
public final class RangeArrayParams<P> {
    
    private final DissolvabilityRule<P>[] dissolvabilityRules;
    private final RangeArrayElementFilter<P>[] rangeArrayElementFilters;
    private final RangeArrayListener[] rangeArrayListeners;

    private final long fileSize;
    private final List<Range<P>> contents;
    private final boolean carriesProperty;

    private RangeArrayParams(DissolvabilityRule<P>[] dissolvabilityRules, RangeArrayElementFilter<P>[] rangeArrayElementFilters, RangeArrayListener[] rangeArrayListeners, long fileSize, List<Range<P>> contents, boolean carriesProperty) {
        this.dissolvabilityRules = dissolvabilityRules;
        this.rangeArrayElementFilters = rangeArrayElementFilters;
        this.rangeArrayListeners = rangeArrayListeners;
        this.fileSize = fileSize;
        this.contents = contents;
        this.carriesProperty = carriesProperty;
    }

    public List<Range<P>> getContents() {
        return contents;
    }

    public DissolvabilityRule<P>[] getDissolvabilityRules() {
        return dissolvabilityRules;
    }

    public boolean getEntriesNeverDissolve() {
        if(dissolvabilityRules==null)return false;
        if(dissolvabilityRules.length==0)return false;
        if(dissolvabilityRules[0] instanceof NeverDissolveDissolvabilityRule)
            return true;
        return false;
    }

    public long getFileSize() {
        return fileSize;
    }

    public RangeArrayElementFilter<P>[] getRangeArrayElementFilters() {
        return rangeArrayElementFilters;
    }

    public RangeArrayListener[] getRangeArrayListeners() {
        return rangeArrayListeners;
    }

    public final boolean carriesProperty() {
        return carriesProperty;
    }
    
    public static final class Builder<P> {
        final LinkedList<DissolvabilityRule<P>> dissolvabilityRules = new LinkedList<DissolvabilityRule<P>>();
        final LinkedList<RangeArrayElementFilter<P>> rangeArrayElementFilters = new LinkedList<RangeArrayElementFilter<P>>();
        final LinkedList<RangeArrayListener> rangeArrayListeners = new LinkedList<RangeArrayListener>();
        
        long fileSize = RangeArray.MAX_VALUE_SUPPORTED;
        List<Range<P>> contents = null;
        boolean carriesProperty = true;
        
        public static <PQ> Builder<PQ> create(){
            return new Builder<PQ>();
        }

        public Builder<P> setContents(List<Range<P>> contents) {
            this.contents = contents;
            return this;
        }
        
        public Builder<P> add(long start, long end){
            if(contents==null)contents = new LinkedList<Range<P>>();
            contents.add(RangeUtils.wrapAsARange(start, end));
            return this;
        }

        public Builder<P> setEntriesNeverDissolve() {
            if(!dissolvabilityRules.isEmpty()){
                throw new IllegalArgumentException("All existing dissolvability rules are being violated");
            }
            dissolvabilityRules.add(DissolvabilityRule.NEVER_DISSOLVE);
            return this;
        }

        public Builder<P> setFileSize(long fileSize) {
            if(fileSize<=0)
                throw new IllegalArgumentException("Negative or zero filesize");
            this.fileSize = fileSize;
            return this;
        }
        
        public Builder<P> setDoesNotCarryProperty() {
            carriesProperty=false;
            return this;
        }
        
        public Builder<P> setDoesCarryProperty() {
            carriesProperty=true;
            return this;
        }
        
        public Builder<P> addDissolvabilityRule(DissolvabilityRule<P> newRule){
            synchronized (dissolvabilityRules){
                if(dissolvabilityRules.contains(DissolvabilityRule.NEVER_DISSOLVE))
                    throw new IllegalArgumentException("Already contains "+DissolvabilityRule.NEVER_DISSOLVE);
                if(dissolvabilityRules.contains(newRule))return this;
                dissolvabilityRules.add(newRule);
            }
            return this;
        }
        
        public Builder<P> addRangeArrayElementFilter(RangeArrayElementFilter<P> newFilter){
            synchronized (rangeArrayElementFilters){
                if(rangeArrayElementFilters.contains(newFilter))return this;
                rangeArrayElementFilters.add(newFilter);
            }
            return this;
        }
        
        public Builder<P> addRangeArrayListener(RangeArrayListener ral){
            synchronized (rangeArrayListeners){
                if(rangeArrayListeners.contains(ral))return this;
                rangeArrayListeners.add(ral);
            }
            return this;
        }
        
        public RangeArrayParams<P> build(){
            return new RangeArrayParams<P>(
                dissolvabilityRules.isEmpty()?new DissolvabilityRule[0]:dissolvabilityRules.toArray(new DissolvabilityRule[dissolvabilityRules.size()]), 
                rangeArrayElementFilters.isEmpty()?null:rangeArrayElementFilters.toArray(new RangeArrayElementFilter[rangeArrayElementFilters.size()]), 
                rangeArrayListeners.isEmpty()?null:rangeArrayListeners.toArray(new RangeArrayListener[rangeArrayListeners.size()]), 
                fileSize,contents,carriesProperty);
        }
    }
}
