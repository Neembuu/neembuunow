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
import org.junit.BeforeClass;
import org.junit.Test;
/**
 *
 * @author Shashank Tulsyan
 */
public class Simple2ConLockTest {
    private static final int KB = 1000;//1024;
    private static final int MB = 1000000;//1024*1024;

    @BeforeClass
    public static void initalize() throws Exception{
        JOptionPane.showMessageDialog(null, "Start" );
    }
    
    @Test
    public void testSimple2conlock() throws  Exception{
        Thread l  = new ReadThread(MB,        50*KB, 20/*0=>infinite*/,false,25);
        Thread l2 = new ReadThread(MB+710*KB, 50*KB,  9/*0=>infinite*/,false,25);
        l2.start();
        //Thread.sleep(1000);
        l2.join();
        l.start(); 
        l.join();
        
    }
}
