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

package neembuu.release1.defaultImpl.external.local;

import neembuu.release1.defaultImpl.external.ELHEntry;

/**
 *
 * @author Shashank Tulsyan
 */
public class LocalExternalLinkHandlers {
    private ELHEntry[] handlers;
    private long creationTime;
    private String createdBy;
    private String hashingAlgorithm;

    public void setHandlers(ELHEntry[] handlers) {
        this.handlers = handlers;
    }

    public String getHashingAlgorithm() {
        return hashingAlgorithm;
    }
    
    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public void setHashingAlgorithm(String hashingAlgorithm) {
        this.hashingAlgorithm = hashingAlgorithm;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
}
