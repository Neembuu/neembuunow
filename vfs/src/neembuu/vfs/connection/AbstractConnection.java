/*
 * Copyright (C) 2011 Shashank Tulsyan
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

package neembuu.vfs.connection;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.util.Throwables;
import neembuu.vfs.connection.checks.ContentSampleListener;
import net.jcip.annotations.GuardedBy;

/**
 * 1) <b>Adjusted offset</b> : Sometimes (and yes it does happen), a new connection
 * is requested at offset fileSize-1. In such cases many servers refuse connection,
 * so this internally adjusts requested offset to make sure at least 1024 bytes
 * are downloaded. Use {@link #getAdjustedOffset() }. <br/>
 * 2) <b>Connection creation time</b> : It records connection creation time.
 * When a new connection is requested very close to an existing one, there 
 * are 2 options - either create a new connection, or allow the previous one to reach there.
 * By default, whichever takes lesser time is chosen, slightly being biased to prevent excessive
 * creation of new connection. Connection creation time is thus an essential parameter. <br/>
 * 3) Be sure to message to Transient connection listener about failure. Success messages are 
 * already handled.
 * @author Shashank Tulsyan
 */
public abstract class AbstractConnection extends OutputStream
        implements Connection {

    //private final AtomicBoolean isAlive = new AtomicBoolean(false);
    @GuardedBy("this")
    private volatile boolean succeededInCreation = false;
    @GuardedBy("this")
    private volatile boolean alreadyKilled_or_died = false;
    
    protected final NewConnectionParams cp;
    
    private final long creationInitiationTime = System.currentTimeMillis();
    private volatile long creationCompletionTime = creationInitiationTime;
    
    protected final Logger downloadThreadLogger;
    private int adjustment = 0;
    private final AtomicBoolean connectCalled = new AtomicBoolean(false);
    
    public static final int MINIMUM_GAP_FROM_END = 2*1024;
    
    private volatile ContentSampleListener csl = null;
    
    public AbstractConnection(final NewConnectionParams cp) {
        this.cp = cp; cp.getDownloadDataChannel().setAutoHandleThrottlingEnabled(true);
        downloadThreadLogger = cp.getDownloadThreadLogger();
    }

    public final void setContentSampleListener(ContentSampleListener csl){
        if(this.csl !=null){
            throw new IllegalStateException("Already have "+this.csl+ " trying to add "+csl);
        }this.csl = csl;
    }

    final protected ContentSampleListener getContentSampleListener() {
        return csl;
    }
    
    @Override
    public final NewConnectionParams getConnectionParams() { // package private
        return cp;
    }
    
    public final boolean succeededInCreation(){
        return succeededInCreation;
    }

    @Override
    public final long getCreationTime() {
        return creationCompletionTime;
    }

    @Override
    public final long timeTakenForCreation() {
        if(!succeededInCreation)
            throw new IllegalStateException("Connection not created yet");
        return (creationCompletionTime-creationInitiationTime);
    }
    
    /**
     * The connection must be created from the {@link AbstractConnection#getAdjustedOffset() }.
     * cp.getOffset will give the actual offset at which request was made.
     * But, sometimes, servers don't like requests at offset close to filesize-1.
     * In that case we download a little further away from end, which is why
     * {@link AbstractConnection#getAdjustedOffset() } must be used
     * @throws IOException 
     */
    protected abstract void connectAndSupplyImpl() throws  IOException;
    protected abstract void abortImpl();
    
    protected final long getAdjustedOffset(){
        return cp.getOffset() - adjustment;
    }
    
    protected final void disableAdjustMent(){
        if(true)throw new UnsupportedOperationException("disabled");
        adjustment = 0;
    }
    
    public final void connectAndSupply() throws  IOException { // package private
        if(!connectCalled.compareAndSet(false, true)){
            throw new IllegalStateException("Already called once");
        }
        
        if(cp.getOffset()>=cp.getReadRequestState().fileSize()){
            throw new IllegalArgumentException();
        }
        if(cp.getOffset()!=0){
            if(cp.getOffset()>cp.getReadRequestState().fileSize()-MINIMUM_GAP_FROM_END){
                long adjustedOffset = (int)(cp.getOffset()-MINIMUM_GAP_FROM_END);
                if(adjustedOffset<0)adjustedOffset=0;
                adjustment = (int)(cp.getOffset()-adjustedOffset);
            }
            if(adjustment!=0){
                downloadThreadLogger.log(Level.SEVERE, "adjustment="+adjustment+" requestedOffset="+cp.getOffset());
            }
        }

        connectAndSupplyImpl();
        
        try{
            setAsDead();
        }catch(Exception a){
            Throwables.addStartingThrowableAsSuppressed(a);
            downloadThreadLogger.log(Level.WARNING,"", a);
        }
    }
    
    private void setAsDead(){
        synchronized(this){
            alreadyKilled_or_died = true;
        }cp.getThrottle().markDead(cp);
    }

    @Override
    public final synchronized boolean isAlive() {
        if(alreadyKilled_or_died){
            return false;
        }
        return (succeededInCreation);
        
    }

    @Override
    public final void abort() {
        downloadThreadLogger.log(Level.INFO, "abort", new Throwable());
        synchronized (this){
            if(alreadyKilled_or_died)throw new IllegalStateException("Connection already dead");
            alreadyKilled_or_died = true;
        }
        // single thread has enter here, thread safe hence
        abortImpl();
    }
    
    @Override
    public final void write(int b) throws IOException {
        if(true)throw new IllegalStateException("unsupported");
        checkAndSetAlive();
        cp.getDownloadDataChannel().write(ByteBuffer.wrap(new byte[]{(byte)b}));
    }

    @Override
    public final void write(byte[] b) throws IOException {
        if(true)throw new IllegalStateException("unsupported");
        checkAndSetAlive();
        cp.getDownloadDataChannel().write(ByteBuffer.wrap(b));
    }

    @Override
    public final void write(byte[] b, int off, int len) throws IOException {
        checkAndSetAlive();
        if(adjustment>0){
            if(len>adjustment){
                off+=adjustment;
                len-=adjustment;
                adjustment = 0;
                
                byte[]b2 = new byte[len];
                System.arraycopy(b, off, b2, 0, len);
                b=b2; off=0; len=b.length;
                
            }else {adjustment-=len;return;}
        }
        cp.getDownloadDataChannel().write(ByteBuffer.wrap(b, off, len));
    }
    
    private void checkAndSetAlive(){
        boolean report = false;
        boolean kill_later = false;
        synchronized (this){
            if(alreadyKilled_or_died){
                kill_later = true;
            }
            if(!succeededInCreation){
                succeededInCreation = true;
                this.creationCompletionTime = System.currentTimeMillis();
                report = true;
            }
        }
        
        if(report){
            cp.getThrottle().initialize(cp);
            cp.getTransientConnectionListener().successful(this,cp);
        }
        
        if(kill_later)
            throw new IllegalStateException(cp+"Connection killed by user: attempting to write in a dead or forcefully killed connection.");
    }

    @Override
    public final String toString() {
        return this.getClass()+"{"+cp+" alreadyKilled_or_died="+alreadyKilled_or_died+"}"+
                (succeededInCreation ? ("{time taken for creation=" + timeTakenForCreation()+"}") : (""));
    }
    
}

