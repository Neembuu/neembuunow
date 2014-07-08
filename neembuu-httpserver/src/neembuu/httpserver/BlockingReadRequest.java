/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.httpserver;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import jpfm.JPfmError;
import jpfm.operations.AlreadyCompleteException;
import jpfm.operations.readwrite.Completer;
import jpfm.operations.readwrite.ReadRequest;

/**
 *
 * @author Shashank Tulsyan
 */
public class BlockingReadRequest implements ReadRequest{
    private final ByteBuffer byteBuffer;
    private final long fileOffset;

    private final AtomicBoolean completed = new AtomicBoolean(false);
    private final long creationTime = System.currentTimeMillis();
    private long completionTime = creationTime;

    private int completionResult;
    private JPfmError completionError;

    private Completer completehandler;
    
    private final Object lock = new Object();
    
    public BlockingReadRequest(ByteBuffer byteBuffer, long fileOffset) {
        this.byteBuffer = byteBuffer;
        this.fileOffset = fileOffset;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public long getFileOffset() {
        return fileOffset;
    }

    public void complete(JPfmError error, int actualRead, Completer completer) throws IllegalArgumentException, IllegalStateException {
        if(completer!=this.completehandler){
            throw new IllegalArgumentException("Expected completer="+this.completehandler+" obtained="+completer);
        }
        if(!completed.compareAndSet(/*expect*/false,/*update*/ true)){
            //we were expecting false, but it is true. implying it is already complete, throw an exception
            throw new AlreadyCompleteException();
        }
        synchronized(lock){lock.notifyAll();}
        completionResult = actualRead;
        completionError = error;
    }
    
    public int read(){
        while(!completed.get()){
            synchronized (lock){
                try{lock.wait(1000);}catch(InterruptedException ie){}
            }
        }
        return completionResult;
    }

    public boolean isCompleted() {
        return completed.get();
    }

    public void handleUnexpectedCompletion(Exception exception) {
        complete(JPfmError.FAILED, 0, null);
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getCompletionTime() {
        return completionTime;
    }

    public int getCompletionResult() {
        if(!isCompleted()){
            throw new IllegalStateException("not completed yet");
        }
        return completionResult;
    }


    public JPfmError getCompletionError() {
        if(!isCompleted()){
            throw new IllegalStateException("not completed yet");
        }
        return completionError;
    }

    public JPfmError getError() {
        return getCompletionError();
    }

    public void setCompleter(Completer completehandler) {
        this.completehandler = completehandler;
    }

    public Completer getCompleter() {
        return completehandler;
    }

    public boolean canComplete(Completer completehandler) {
        return true;
    }

    public void complete(JPfmError error) throws IllegalArgumentException, IllegalStateException {
        
        complete(error,getByteBuffer().capacity(),null);
    }

}
