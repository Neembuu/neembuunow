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

import jpfm.annotations.NonBlocking;
import neembuu.vfs.connection.checks.SeekingAbility;

/**
 * This is used to create a new connection.
 * A new connection is not requested when some other connection has already
 * downloaded data in that region, it is always used to get new data. 
 * This should simply make new connections and not bother checking 
 * if a connection in the given offset already exists.<br/>
 * If new connection cannot be created, then report failure
 * by calling {@link TransientConnectionListener#failed(java.lang.Throwable) }
 * obtaining TransientConnectionListener by calling
 * {@link NewConnectionParams#getTransientConnectionListener()}
 * @author Shashank Tulsyan
 */
public interface NewConnectionProvider {
    
    /**
     * The connection should be made in a new separate thread if
     * made using blocking Socket ( java.net.Socket ) . <br/>
     * All connections made by this channel would lie in a separate
     * single thread, if made asynchronously using SocketChannel 
     * ( <tt>java.nio.channels.SocketChannel</tt>). <br/>
     * But in either case this method should be non-blocking,
     * that is, should immediately return after invocation.
     * @param connectionParams use Connection manager params build
     * @see NewConnectionParams.Builder
     */
    @NonBlocking
    public void provideNewConnection(NewConnectionParams connectionParams);
    
    /**
     * @param offset
     * @return milliseconds that will take for creating a new connection
     * at the given offset. For infinite time returns Integer.MAX_VALUE .
     */
    public long estimateCreationTime(long offset);
    
    /**
     * @return A String describing the the source,
     * not necessarily a url, can be torrent id.
     */
    public String getSourceDescription();
    
    public SeekingAbility seekingAbility();
}
