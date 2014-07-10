/*
 * Copyright (C) 2014 Shashank Tulsyan
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

package neembuu.release1.ui.linkpanel;

import java.util.logging.Level;
import neembuu.vfs.connection.NewConnectionParams;
import neembuu.vfs.connection.NewConnectionProvider;
import neembuu.vfs.connection.checks.CanSeek;
import neembuu.vfs.connection.checks.SeekingAbility;

/**
 *
 * @author Shashank Tulsyan
 */
public class FakeConnectionProvider implements NewConnectionProvider{

    private final String srcFile;
    private final double fakeSpeedTarget_inKiBps;

    public FakeConnectionProvider(String srcFile, double fakeSpeedTarget_inKiBps) {
        this.srcFile = srcFile; this.fakeSpeedTarget_inKiBps = fakeSpeedTarget_inKiBps;
    }
    
    @Override
    public void provideNewConnection(final NewConnectionParams cp) {
                cp.getDownloadThreadLogger().log(Level.SEVERE, "executing in new thread="+cp.getOffset());
        class StartNewConnectionThread extends Thread {

            StartNewConnectionThread() {
                super("FakeConnectionThread{" + cp + "}"
                        +"{"+cp.getReadRequestState().getFileName()+ "}");
            }

            @Override public final void run() {
                try {
                    FakeConnection c = new FakeConnection(srcFile, fakeSpeedTarget_inKiBps, cp);
                    c.connectAndSupply();
                } catch (Exception e) {  e.printStackTrace(); }
            }
        }

        new StartNewConnectionThread().start();
    }

    @Override
    public SeekingAbility seekingAbility() {
        return new SeekingAbility() {
            @Override public CanSeek get() {
                return CanSeek.YES;
            }

            @Override public void removeListener(SeekingAbility.Listener l) {
                throw new UnsupportedOperationException("Not supported yet."); 
            }
            
            @Override public void addListener(SeekingAbility.Listener l) {
                throw new UnsupportedOperationException("Not supported yet."); 
            }
        };
    }

    @Override public long estimateCreationTime(long offset) {
        return 100;
    }

    @Override public String getSourceDescription() {
        return "FakeConnectionProvider@"+fakeSpeedTarget_inKiBps+"{"+srcFile+"}"; }

    @Override public String toString() {
        return "FakeConnectionProvider@"+fakeSpeedTarget_inKiBps+"{"+srcFile+"}"; }

}
