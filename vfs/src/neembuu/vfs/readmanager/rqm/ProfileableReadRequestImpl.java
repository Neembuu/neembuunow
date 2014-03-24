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

package neembuu.vfs.readmanager.rqm;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import jpfm.operations.readwrite.AbstractRequestWrapper;
import jpfm.operations.readwrite.ReadRequest;
import jpfm.util.ProfileableReadRequest;
import jpfm.util.PropertyCarryingReadRequest;
import neembuu.config.GlobalTestSettings;
import neembuu.vfs.readmanager.TotalFileReadStatistics;

/**
 *
 * @author Shashank Tulsyan
 */
final class ProfileableReadRequestImpl 
        extends AbstractRequestWrapper 
        implements ProfileableReadRequest {

    private final AtomicInteger traversedCount = new AtomicInteger(0);
    private final int requestCount ;
    private final long totalDataRequestedTillThisRequest;
    
    private final ConcurrentLinkedQueue properties = makePropertiesStore();
    
    private static ConcurrentLinkedQueue makePropertiesStore(){
        if(!GlobalTestSettings.getValue("STORE_PROFILEABLE_READ_REQUEST_PROPERTIES"))
            return null;
        return new ConcurrentLinkedQueue();
    }

    /*package private*/ ProfileableReadRequestImpl(
            ReadRequest readRequest,TotalFileReadStatistics tfrs){
        super(readRequest);
        this.requestCount = tfrs.totalNumberOfRequestsMade();
        this.totalDataRequestedTillThisRequest = tfrs.totalDataRequestedSoFar();
        
        if(readRequest instanceof SpecialReadRequest){
            addProperty(ReadRequestSplitType.SPECIAL_REQUEST);
        }
    }

    @Override
    public final long getTotalDataRequestedTillThisRequest() {
        return totalDataRequestedTillThisRequest;
    }

    @Override
    public final AtomicInteger getTraversedCount(){
        return traversedCount;
    }

    @Override
    public final int getRequestCount() {
        return requestCount;
    }

    @Override
    @SuppressWarnings(value="unchecked")
    public final PropertyCarryingReadRequest addProperty(Object property){
        if(properties==null){
            //Logger.getLogger(ProfileableReadRequests.class.getName()).info("Attempting to add property when adding property is not supported");
            return this;
        }
        properties.add(property);return this;
    }
    
    @Override
    @SuppressWarnings(value="unchecked")
    public final boolean removeProperty(Object property){
        if(properties==null)return false;
        return properties.remove(property);
    }
    
    @Override
    public final Object[] getProperties(){
        if(properties==null){
            return null;
        }
        return properties.toArray();
    }

    @Override
    public String toString() {
        return "Profileable{"
                + rr.getFileOffset()
                + "->" + (rr.getFileOffset()+rr.getByteBuffer().capacity()-1)
                + "}{"+
                getOriginalReadRequest().getClass().getSimpleName()
                + "}{"+getOriginalReadRequest().toString()+"}"+properties();
    }

    private String properties(){
        if(properties==null)return "";
        StringBuilder k = new StringBuilder(100);
        for(Object l : properties){
            k.append("{");k.append(l);k.append("}");
        }return k.toString();
        
    }

}
