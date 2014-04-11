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

package neembuu.release1;

import java.util.List;
import neembuu.release1.api.clipboardmonitor.ClipboardListener;
import neembuu.release1.clipboard.ClipboardMonitorImpl;

/**
 *
 * @author Shashank Tulsyan
 */
public class ClipboardTest {
     public static void main(String[] args) throws Exception{
        ClipboardMonitorImpl cmi = new ClipboardMonitorImpl();
        cmi.addListener(new ClipboardListener() {

            @Override
            public void receive(List<String> urls) {
                System.out.println("++");
                for (String string : urls) {
                    System.out.println(string);
                }System.out.println("--");
            }
        });
        cmi.startService();
        Thread.sleep(100000);
    }
}
