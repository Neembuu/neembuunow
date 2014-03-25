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

package neembuu.vfs.test.test;

import javax.swing.JOptionPane;
import neembuu.vfs.test.Main;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Shashank Tulsyan
 */
public final class BombardWithManyConnectionRequests {

    private static final int KB = 1000;//1024;
    private static final int MB = 1000000;//1024*1024;

    @BeforeClass
    public static void initalize() throws Exception{
        Main.main(new String[]{/*"cascadeMount"*/});
        //JOptionPane.showMessageDialog(null, "Start" );
    }
    
    @Test
    public void bomardTest() throws  Exception{
        Thread[]t = new Thread[10];
        for (int i = 0; i < t.length; i++) {
            t[i] =new ReadThread(
                    (long)(Math.random()*10*MB), 100*KB, 5/*0=>infinite*/,true);
            t[i].start();
        }
        
        for (int i = 0; i < t.length; i++) {
            t[i].join();
        }
//        Thread l  = new ReadThread(MB, 50*KB, 5/*0=>infinite*/);
//        Thread l2 = new ReadThread(MB+700*KB, 50*KB, 9/*0=>infinite*/);
//        l.start(); l2.start();
//        l.join();
//        l2.join();
    }
    
    

}
