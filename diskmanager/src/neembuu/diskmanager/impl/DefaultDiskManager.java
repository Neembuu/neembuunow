/*
 * Copyright (c) 2011 Shashank Tulsyan <shashaanktulsyan@gmail.com>. 
 * 
 * This is part of free software: you can redistribute it and/or modify
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
 * along with this.  If not, see <http ://www.gnu.org/licenses/>.
 */
package neembuu.diskmanager.impl;

import java.io.IOException;
import neembuu.diskmanager.DiskManager;
import neembuu.diskmanager.DiskManagerParams;
import neembuu.diskmanager.FindPreviousSessionCallback;
import neembuu.diskmanager.MakeSession;
import neembuu.diskmanager.Session;
import neembuu.diskmanager.impl.session.DefaultSession;
import neembuu.diskmanager.impl.session.MakeSessionImpl;
import neembuu.diskmanager.impl.session.RestoreSessionThread;

/**
 *
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
public final class DefaultDiskManager implements DiskManager{

    private final DiskManagerParams dmp;
    
    private final Object lock = new Object();
    
    

    public DefaultDiskManager(DiskManagerParams dmp) {
        this.dmp = dmp;
    }

    @Override public void findPreviousSessions(FindPreviousSessionCallback callback) {
        synchronized (lock){
            RestoreSessionThread rst = new RestoreSessionThread(callback, lock, dmp);
            rst.start();
        }
    }

    @Override public MakeSession makeSession(String type) {
        MakeSessionImpl msi = new MakeSessionImpl(type, lock, dmp);
        return msi;
    }

    @Override
    public Session createTestSession() throws IOException {
        MakeSessionImpl msi = new MakeSessionImpl("test", lock, dmp);
        return msi.createTestSession();
    }

    @Override
    public MakeSession modifySession(Session source) {
        if(!(source instanceof DefaultSession)){
            throw new IllegalArgumentException("source is not a recognizable session");
        }
        MakeSessionImpl ms = new MakeSessionImpl((DefaultSession)source, lock, dmp);
        return ms;
    }

}
