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
package neembuu.release1.open;

import neembuu.release1.api.open.Open;
import neembuu.release1.api.open.OpenerAccess;

/**
 *
 * @author Shashank Tulsyan
 */
public class OpenImpl implements Open {

    private final Process p;
    private final String path;
    private final OpenerAccess openerA;

    public OpenImpl(Process p, String path,OpenerAccess openerA) {
        this.p = p; this.path = path; this.openerA = openerA;
    }

    public String getPath() {
        return path;
    }
    
    @Override
    public boolean isOpen() {
        try {
            p.exitValue();
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public void close() {
        try {
            p.destroy();
        } catch (Exception a) {
        }
        Open o = openerA.openHandles_remove(path);
        if (o != this && o != null) {
            new Exception("closed file but removed wrong handle, therefore killing it").printStackTrace();
            o.close();
        }
    }

    @Override
    public void closeAll() {
        close();
    }
}
