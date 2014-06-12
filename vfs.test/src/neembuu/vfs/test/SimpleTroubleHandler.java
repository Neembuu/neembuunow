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
package neembuu.vfs.test;

import java.util.List;
import javax.swing.JOptionPane;
import jpfm.JPfmError;
import jpfm.operations.AlreadyCompleteException;
import jpfm.operations.readwrite.ReadRequest;
import neembuu.vfs.connection.NewConnectionParams;
import neembuu.vfs.file.TroubleHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public final class SimpleTroubleHandler implements TroubleHandler{

    private final FrameProvider fp;

    public SimpleTroubleHandler(FrameProvider fp) {
        this.fp = fp;
    }
    
    @Override
    public void cannotCreateANewConnection(NewConnectionParams ncp, int numberOfRetries,Throwable reason) {
        JOptionPane.showMessageDialog(fp.getJFrame(), ncp.toString()+"\n"+reason.getMessage(),
                "Internet problem : retries "+numberOfRetries+" times",JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void readRequestsPendingSinceALongTime(List<ReadRequest> pendingReadRequest, long atleastMillisec) {
        String message = "Press okay to continue trying.\n"
                + "Cancel to forecefully complete the requests.\n"
                + "Following requests pending since a long time.\n";
        for (ReadRequest rr : pendingReadRequest) {
            message+=rr.toString()+"\n";
        }
        int ret = JOptionPane.showConfirmDialog(fp.getJFrame() , 
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

    @Override
    public long preferredCheckingInterval() {
        return DEFAULT_CHECKING_INTERAL_MILLISECONDS;
    }

    @Override
    public long pendingAtleastFor() {
        return DEFAULT_PENDING_ATLEAST_FOR_MILLISECONDS;
    }
    
}
