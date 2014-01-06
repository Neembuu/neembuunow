/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.vfs.readmanager;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import jpfm.operations.readwrite.AbstractRequestWrapper;
import jpfm.operations.readwrite.ReadRequest;
import jpfm.util.ProfileableReadRequest;
import jpfm.util.PropertyCarryingReadRequest;
import neembuu.config.GlobalTestSettings;

/**
 *
 * @author Shashank Tulsyan
 */
final class ProfileableReadRequestImpl 
        extends AbstractRequestWrapper 
        implements ProfileableReadRequest {

    private AtomicInteger traversedCount = new AtomicInteger(0);
    private final int requestCount ;
    private final long totalDataRequestedTillThisRequest;
    
    private final ConcurrentLinkedQueue properties = makePropertiesStore();
    
    private static ConcurrentLinkedQueue makePropertiesStore(){
        if(!GlobalTestSettings.getValue("STORE_PROFILEABLE_READ_REQUEST_PROPERTIES"))
            return null;
        return new ConcurrentLinkedQueue();
    }

    /*package private*/ ProfileableReadRequestImpl(
            ReadRequest readRequest,
            int requestCount,
            long numberOfBytesReadTillThisRequest){
        super(readRequest);
        this.requestCount = requestCount;
        this.totalDataRequestedTillThisRequest = numberOfBytesReadTillThisRequest;    
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
        return "Profileable{"+
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
