/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.vfs.test;

import java.util.List;
import javax.swing.JOptionPane;
import jpfm.JPfmError;
import jpfm.mount.Mount;
import jpfm.operations.AlreadyCompleteException;
import jpfm.operations.readwrite.ReadRequest;
import neembuu.vfs.connection.NewConnectionParams;
import neembuu.vfs.file.TroubleHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public final class SimpleTroubleHandler implements TroubleHandler{

    private final MonitorFrame mf;

    public SimpleTroubleHandler(MonitorFrame mf) {
        this.mf = mf;
    }
    
    @Override
    public void cannotCreateANewConnection(NewConnectionParams ncp, int numberOfRetries) {
        JOptionPane.showMessageDialog(mf.frame, ncp.toString(), "Internet problem : retries "+numberOfRetries+" times",JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void readRequestsPendingSinceALongTime(List<ReadRequest> pendingReadRequest, long atleastMillisec) {
        String message = "Press okay to continue trying.\n"
                + "Cancel to forecefully complete the requests.\n"
                + "Following requests pending since a long time.\n";
        for (ReadRequest rr : pendingReadRequest) {
            message+=rr.toString()+"\n";
        }
        int ret = JOptionPane.showConfirmDialog(mf.frame, 
                message, 
                "Watch as you download might be inefficient",JOptionPane.OK_CANCEL_OPTION);
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
