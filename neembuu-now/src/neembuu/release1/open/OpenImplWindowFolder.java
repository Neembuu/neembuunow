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

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import neembuu.release1.api.open.OpenerAccess;

/**
 *
 * @author Shashank Tulsyan
 */
public class OpenImplWindowFolder extends OpenImpl {

    public OpenImplWindowFolder(Process p, String path, OpenerAccess openerA) {
        super(p, path, openerA);
    }

    private boolean findWindowApproachWorked(String folderName) {
        return findWindowApproachWorked(folderName, 0, 5);
    }

    private void printWindowName(WinDef.HWND hwnd) {
        try {
            char[] c = new char[255];
            int len = User32.INSTANCE.GetWindowText(hwnd, c, 250);
            String s = new String(c, 0, len);
            System.out.println(s);
        } catch (Exception a) {
            a.printStackTrace();
        }
    }

    private boolean findWindowApproachWorked(String folderName, int depth, int maxDepth) {
        System.out.println("using Find window approach");
        try {
            int error;
            WinDef.HWND hWnd = User32.INSTANCE.FindWindow(null, folderName);
            error = Kernel32.INSTANCE.GetLastError();
            if (error != 0) {
                return false;
            }

            User32.INSTANCE.PostMessage(hWnd, User32.WM_CLOSE, null, null);
            error = Kernel32.INSTANCE.GetLastError();
            if (error != 0) {
                return false;
            }

                // close other instances of the window
            // there might be more than 1 open window
            // we assume maximum 5 such windows exist.
            if (depth < maxDepth) {
                findWindowApproachWorked(folderName, depth + 1, maxDepth);
            }

            return true;

        } catch (Exception a) {
            a.printStackTrace();
        }
        return false;
    }

    private boolean enumerateWindowApproachWorked(final String folderName) {
        final AtomicInteger result = new AtomicInteger(-1);
        try {
            System.out.println("Sending WIN_API request WM_CLOSE request to all windows with the name " + folderName);
            User32.INSTANCE.EnumWindows(new WinUser.WNDENUMPROC() {
                @Override
                public boolean callback(WinDef.HWND hwnd, Pointer pointer) {
                    char[] c = new char[255];
                    User32.INSTANCE.GetWindowText(hwnd, c, 250);
                    String s = new String(c).trim();
                    if (s.equals(folderName)) {
                        System.out.println("Sending WM_Close to ->" + folderName);
                        User32.INSTANCE.PostMessage(hwnd, User32.WM_CLOSE, null, null);
                        result.set(Kernel32.INSTANCE.GetLastError());
                        System.out.println(Kernel32.INSTANCE.GetLastError());
                        //return false;
                    }
                    return true;
                }
            }, Pointer.NULL);
        } catch (Exception a) {
            a.printStackTrace();
        }

        return result.get() == 0;
    }

    @Override
    public void close() {
        final String folderName = Paths.get(getPath()).getFileName().toString();

        if (!findWindowApproachWorked(folderName) || true) {
            System.out.println("tried find window approach");
            if (!enumerateWindowApproachWorked(folderName)) {
                System.out.println("tried enumerate approach could not close the window :( ");
            }
        }

        super.close();
    }

    @Override
    public void closeAll() {
        final String folderName = Paths.get(getPath()).getFileName().toString();
        if (!enumerateWindowApproachWorked(folderName)) {
            System.out.println("tried enumerate approach could not close the window :( ");
        }
        super.close();
    }

}
