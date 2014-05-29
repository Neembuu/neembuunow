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

package neembuu.release1.defaultImpl.external;

/**
 *
 * @author Shashank Tulsyan
 */
public class ELH_Export {
    private final ELHEntry[] handlers;
    private final long creationTime = System.currentTimeMillis();
    private final String createdBy;
    private final String hashingAlgorithm;

    public ELH_Export(ELHEntry[] handlers, String createdBy, String hashingAlgorithm) {
        this.handlers = handlers;
        this.createdBy = createdBy;
        this.hashingAlgorithm = hashingAlgorithm;
    }

    public ELHEntry[] getHandlers() {
        return handlers;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getHashingAlgorithm() {
        return hashingAlgorithm;
    }
    
}
