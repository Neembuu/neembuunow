/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import jpfm.JPfmError;
import jpfm.operations.AlreadyCompleteException;
import jpfm.operations.readwrite.ReadRequest;
import neembuu.release1.api.IndefiniteTask;
import neembuu.release1.api.ui.IndefiniteTaskUI;
import neembuu.release1.api.ui.MainComponent;
import neembuu.vfs.connection.NewConnectionParams;
import neembuu.vfs.file.TroubleHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public final class UnprofessionalTroubleHandler implements TroubleHandler{

    private final MainComponent mainComponent;
    private final IndefiniteTaskUI indefiniteTaskUI;

    public UnprofessionalTroubleHandler(MainComponent mainComponent, IndefiniteTaskUI indefiniteTaskUI) {
        this.mainComponent = mainComponent;
        this.indefiniteTaskUI = indefiniteTaskUI;
    }
    
    @Override
    public void cannotCreateANewConnection(NewConnectionParams ncp, int numberOfRetries) {
        JOptionPane.showMessageDialog(mainComponent.getJFrame(), ncp.toString(), "Internet problem : retries "+numberOfRetries+" times",JOptionPane.ERROR_MESSAGE);
    }

    private IndefiniteTask buffering = null;
    
    @Override
    public void readRequestsPendingSinceALongTime(List<ReadRequest> pendingReadRequest, long atleastMillisec) {
        List<ReadRequest> pendingSince = findPendingSince(pendingReadRequest, 1000);
        
        if(!pendingSince.isEmpty()){
            if(buffering!=null){
                buffering = indefiniteTaskUI.showIndefiniteProgress("Buffering");
            }
        }else {
            if(buffering!=null){
                buffering.done();
            }
        }
        List<ReadRequest> seriousPendingReadRequest = findPendingSince(pendingReadRequest, DEFAULT_PENDING_ATLEAST_FOR_MILLISECONDS);
        if(!seriousPendingReadRequest.isEmpty()){
            seriouslyPendingSinceALongTime(seriousPendingReadRequest);
        }
        
    }

    @Override
    public long preferredCheckingInterval() {
        return 1000;
    }

    @Override
    public long pendingAtleastFor() {
        return 5000;
    }
    
    private List<ReadRequest> findPendingSince(List<ReadRequest> pendingReadRequest, long checlAtleast){
        List<ReadRequest> pendingSince = new ArrayList<ReadRequest>();
        for (ReadRequest rr : pendingReadRequest) {
            if(System.currentTimeMillis() - 
                    rr.getCreationTime() > checlAtleast){
                pendingSince.add(rr);
            }
        }
        return pendingSince;
    }
    
    private long lastPopupShowTime = System.currentTimeMillis();
    
    private void seriouslyPendingSinceALongTime(List<ReadRequest> pendingReadRequest){
        String message = "Press okay to continue trying.\n"
                + "Cancel to forecefully complete the requests.\n"
                + "Following requests pending since a long time.\n";
        for (ReadRequest rr : pendingReadRequest) {
            message+=rr.toString()+"\n";
        }
       
        if(System.currentTimeMillis() - lastPopupShowTime < DEFAULT_CHECKING_INTERAL_MILLISECONDS){
            return;
        }else {
            lastPopupShowTime = System.currentTimeMillis();
        }
        
        /*int ret = JOptionPane.showConfirmDialog(nui.getFrame(), 
                message, 
                "Watch as you download might be inefficient",JOptionPane.OK_CANCEL_OPTION);*/
        int ret = JOptionPane.CANCEL_OPTION;
        RuntimeException re = new RuntimeException("User forced completion of requests");
        if(ret==JOptionPane.CANCEL_OPTION){
            for (ReadRequest rr : pendingReadRequest) {
                try{
                    rr.complete(JPfmError.SUCCESS);
                }catch(AlreadyCompleteException ace){
                    ace.printStackTrace(System.err);
                }
            }
        }
    }
    
    
}
