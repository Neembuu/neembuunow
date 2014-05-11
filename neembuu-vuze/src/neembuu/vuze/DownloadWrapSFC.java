/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.vuze;

import java.nio.ByteBuffer;
import jpfm.DirectoryStream;
import jpfm.JPfmError;
import jpfm.operations.readwrite.ReadRequest;
import jpfm.util.ReadUtils;
import jpfm.volume.BasicCascadableAbstractFile;
import org.gudy.azureus2.plugins.disk.DiskManagerChannel;
import org.gudy.azureus2.plugins.disk.DiskManagerEvent;
import org.gudy.azureus2.plugins.disk.DiskManagerFileInfo;
import org.gudy.azureus2.plugins.disk.DiskManagerListener;
import org.gudy.azureus2.plugins.disk.DiskManagerRequest;
import org.gudy.azureus2.plugins.download.Download;
import org.gudy.azureus2.plugins.download.DownloadException;

/**
 *
 * @author Shashank Tulsyan
 */
public class DownloadWrapSFC extends BasicCascadableAbstractFile{
    private final Download d;
    
    private final DiskManagerFileInfo dmfi;
    
    private DiskManagerChannel dmc ;
    
    private Exception lastException;

    public DownloadWrapSFC(Download d, int i) {
        super(null,0,null);
        this.d = d;
        dmfi = d.getDiskManagerFileInfo(i);
        
        super.name = dmfi.getFile().getName();
        super.fileSize = dmfi.getLength();
    }

    @Override public void open() {
        if(dmc!=null){
            dmc.destroy();
        }
        try{
            dmc = dmfi.createChannel();
        }catch(DownloadException de){
            lastException = de;
            de.printStackTrace();
            dmc = null;
        }
    }

    public void setParent(DirectoryStream parent) {
        this.parent = parent;
    }
    
    @Override public void close() {
        if(dmc!=null){
            dmc.destroy();
        }
    }

    @Override public void read(final ReadRequest rq) throws Exception {
        if(dmc == null) {
            hasFailed(lastException, rq);
            return;
        }
        
        final DiskManagerRequest dmr = dmc.createRequest();
        dmr.setType(DiskManagerRequest.REQUEST_READ);
        dmr.setOffset(rq.getFileOffset());
        dmr.setLength(rq.getByteBuffer().capacity());
        dmr.addListener(new DiskManagerListener() {
            @Override public void eventOccurred(DiskManagerEvent event) {
                if(hasNotSucceeded(event, rq)){
                    return;
                }
                
                ByteBuffer dwnl = event.getBuffer().toByteBuffer();
                
                //if(dwnl.)
                
                dwnl.rewind();//copy from the beginning
                rq.getByteBuffer().put(dwnl);
                dwnl.rewind();
                //rq.getByteBuffer().flip();
                
                if(rq.getByteBuffer().remaining()>0){
                    System.out.println("remaing="+rq.getByteBuffer().remaining());
                }else { 
                    rq.complete(JPfmError.SUCCESS);
                }
                //System.out.println(new String(rq.getByteBuffer().array()));
            }
        });
        new Thread(){
            public void run(){
                dmr.run();
            }
        }.start();
        
    }
    
    private boolean hasNotSucceeded(DiskManagerEvent event, ReadRequest rq){
        boolean failed = hasFailed(event.getFailure(), rq);
        if(failed)return true;
        if(event.getType()==DiskManagerEvent.EVENT_TYPE_FAILED){
            return true;
        }else if(event.getType()==DiskManagerEvent.EVENT_TYPE_BLOCKED){
            return true;
        }
        
        if(event.getBuffer()==null)return true;
        
        return false;
    }
    
    private boolean hasFailed(Throwable f, ReadRequest rq){
        if(f!=null){
            ReadUtils.handleUnexpectedCompletion(rq, lastException);
            lastException = null;
            return true;
        }return false;
    }
    
}
