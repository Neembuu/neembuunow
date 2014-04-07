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
package neembuu.diskmanager.impl.session;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import neembuu.diskmanager.DiskManagerParams;
import neembuu.diskmanager.FindPreviousSessionCallback;
import neembuu.diskmanager.NioUtils;
import neembuu.diskmanager.Nomenclature.SessionName;
import neembuu.diskmanager.Session;

/**
 *
 * @author Shashank Tulsyan
 */
public final class RestoreSessionThread extends Thread {

    private final FindPreviousSessionCallback callback;
    private final List<Session> sessions = new ArrayList<Session>();

    private Exception exception;
    
    private final Object lock;
    private final DiskManagerParams dmp;

    public RestoreSessionThread(FindPreviousSessionCallback callback, Object lock, DiskManagerParams dmp) {
        super("RestoreSessionThread@"+callback.toString());
        this.callback = callback;
        this.lock = lock;
        this.dmp = dmp;
    }

    @Override
    public void run() {
        Exception to_ret_exception; boolean done;
        synchronized (lock) {
            String p = dmp.getBaseStoragePath();
            try (DirectoryStream<Path> d = Files.newDirectoryStream(Paths.get(p))) {
                for (Path path : d) {
                    if (Files.isDirectory(path)) {
                        handleDirectory(path);
                    } else if (Files.isRegularFile(path)) {
                        callback.foundResource(path);
                    } else {
                        Exception ex = new Exception("Unknown filesystem artifact->" + path);
                        addException(ex);
                    }
                }
                to_ret_exception = exception; done = true;
            } catch (Exception a) {
                addException(a);
                to_ret_exception = a; done = false;
            }
        }
        callback.done(to_ret_exception, done, sessions);
    }

    private void handleDirectory(Path p) {
        SessionName si = dmp.getNomenclature().getSessionName(p.getFileName().toString());

        DefaultSession ds = new DefaultSession(si.getType(), p, dmp);
        FindPreviousSessionCallback.Action l = callback.foundSession(ds);

        if (l == FindPreviousSessionCallback.Action.Delete) {
            try {
                NioUtils.deleteDirectory(p);//Files.delete(p);
            } catch (Exception a) {
                addException(a);
            }
        }
        if (l == FindPreviousSessionCallback.Action.Keep) {
            sessions.add(ds);
        }
    }
    
    private void addException(Exception ex){
        if(exception==null){ exception = ex;}
        else { exception.addSuppressed(ex); } 
    }
}
