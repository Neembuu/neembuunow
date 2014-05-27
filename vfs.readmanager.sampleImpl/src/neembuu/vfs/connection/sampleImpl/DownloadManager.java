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
package neembuu.vfs.connection.sampleImpl;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.vfs.connection.NewConnectionParams;
import neembuu.vfs.connection.NewConnectionProvider;

/**
 * There is one download manager instance for every pending download job.
 * A download manager is responsible for providing new connections for that
 * download.
 * This is neembuu's test download manager. To be replaced by jdownloader's
 * superior implementation.
 * @author Shashank Tulsyan
 */
public final class DownloadManager implements NewConnectionProvider {

    /**
     * simple direct download http links only
     */
    private final String link;
    private static final Logger LOGGER = Logger.getLogger(DownloadManager.class.getName());;
    // for debuggin race condition in connection creation which results in
    // multiple connection creation for overlapping or exactly same region.
    private final ConcurrentLinkedQueue<NewConnection> connection_list = new ConcurrentLinkedQueue<NewConnection>();

    private long averageConnectionCreationTime() {
        int i = 0;
        long totalTime = 0;
        for (NewConnection connection : connection_list) {
            if(connection.succeededInCreation()){
                totalTime += connection.timeTakenForCreation();
                i++;
            }
        }
        if (i == 0) {
            return 0;//creation time is unknown
        }
        return ((totalTime) / i);
    }

    /**
     * 
     * @param simpleHttpDirectDownloadLink simple direct download http links only
     */
    public DownloadManager(String simpleHttpDirectDownloadLink) {
        this.link = simpleHttpDirectDownloadLink;
    }

    public String getDownloadLink() {
        return link;
    }

    private void connectionsRequested() {
        if(true)return;
        String message = "++++++++++ConnectionsRequested+++++++++\n";
        for (NewConnection e : connection_list) {
            message = message + e.getConnectionParams().toString() + "\n";
        }
        message = message + "---------ConnectionsRequested--------\n";
        
        LOGGER.info(message);
    }

    @Override
    public final long estimateCreationTime(long offset) {
        return averageConnectionCreationTime();
    }

    @Override
    public final String getSourceDescription() {
        return "sampleImpl{"+link+"}";
    }
    
    @Override
    public final void provideNewConnection(
            final NewConnectionParams cp) {
        cp.getDownloadThreadLogger().log(Level.SEVERE, "executing in new thread="+cp.getOffset());
        class StartNewConnectionThread extends Thread {

            StartNewConnectionThread() {
                super("StartNewConnectionThread{" + cp + "}"
                        +"{"+cp.getReadRequestState().getFileName()+ "}");
            }

            @Override
            public final void run() {
                try {
                    NewConnection c = new NewConnection(
                            DownloadManager.this,
                            cp);
                    connection_list.add(c);
                    //new RangeArrayElement(connectionParams.getOffset(), connectionParams.getOffset()) );
                    connectionsRequested();
                    c.connectAndSupply();
                } catch (Exception e) {
                    LOGGER.log(Level.INFO, "Problem in new connection ", e);
                }
            }
        }

        new StartNewConnectionThread().start();



    }
}
