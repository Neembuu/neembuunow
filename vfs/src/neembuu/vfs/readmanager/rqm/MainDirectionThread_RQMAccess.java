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

package neembuu.vfs.readmanager.rqm;

import java.util.List;
import java.util.logging.Logger;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.vfs.file.DownloadConstrainHandler;
import neembuu.vfs.file.TroubleHandler;
import neembuu.vfs.readmanager.RegionHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public interface MainDirectionThread_RQMAccess {
    List<DownloadConstrainHandler> constraintedWith();
    Logger logger();
    String provider_getName();
    
    long lastExternalRequestTime();
    
    boolean autoCompleteEnabled();
    
    int myDch_index();
    
    TroubleHandler provider_getTroubleHandler();

    UnsyncRangeArrayCopy<RegionHandler> handlers_tryToGetUnsynchronizedCopy();

    void notifyDownloadComplete();
    
    boolean message_requiredConnectionAtZero();
}
