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

/**
 *
 * @author Shashank Tulsyan
 */
public interface TransientConnectionListener {
    /**
     * @param state a string describing the situation.
     * No restrictions are there as to how this will look.
     * This could be for example "captcha is being decoded"
     * or "password is being filled." etc.
     */
    void describeState(String state);
    
    /**
     * @param numberOfretries Number of attempts to start this new connection made so far.
     */
    void reportNumberOfRetriesMadeSoFar(int numberOfretries);
    
    /**
     * As soon as the connection is created this event should be fired.
     * This should be called before the "minimum of bytes that must be downloaded"
     * ( {@link NewConnectionParams.Builder#setMinimumSizeRequired(int) })
     * has not been downloaded yet. <br/>
     * The field storing state for {@link Connection#isAlive()} must be set
     * to true before calling {@link #successful(null)}, otherwise multiple 
     * connections for the same region might be made because of race condition.
     * @see NewConnectionParams.Builder#setMinimumSizeRequired(int) 
     * @param c must not be null.
     */
    void successful(Connection c,NewConnectionParams ncp);
    
    /**
     * This function might be changed in future.
     * @param reason 
     */
    void failed(Throwable reason,NewConnectionParams ncp);
    
    /**
     * Called only when connection was successfully created but later
     * got terminated.
     * @param reason can be null
     */
    //void aborted(Throwable reason);
}
