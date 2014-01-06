/*
 *  Copyright (C) 2010 Shashank Tulsyan
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

package neembuu.vfs.test.test;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
//import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import javax.swing.JOptionPane;
import jpfm.util.ContentPeek;
import org.junit.BeforeClass;
import org.junit.Test;
import static junit.framework.Assert.*;

/**
 *
 * @author Shashank Tulsyan
 */
public final class BoundaryConditions {
    //private static AsynchronousFileChannel fc1 = null;
    //private static AsynchronousFileChannel fc2 = null;

    private static FileChannel fc1 = null;
    private static FileChannel fc2 = null;
    
    private static final int KB = 1000;//1024;
    private static final int MB = 100000;//1024*1024;

    @BeforeClass
    public static void initalize() throws Exception{
        
        
        File storageDirectory =new File("j:\\neembuu\\heap\\test120k.http.rmvb_neembuu_download_data" );
        
        File[]files = storageDirectory.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            file.delete();            
        }
        
        /*RangeArray<RangeArrayElement> array = new RangeArray<RangeArrayElement>();
        array.add(0L, 1*MB);
        array.add(3L*MB, 1*MB);
        array.add(5L*MB, 1*MB);
        array.add(7L*MB, 1*MB);
        
        /*array.add(0L, 10*KB);
        array.add(30L*KB, 10*KB);
        array.add(50L*KB, 10*KB);
        array.add(70L*KB, 10*KB);*/
        
        /*PartialFileCreator creator = new PartialFileCreator(
                array, storageDirectory , 
                new File("j:\\neembuu\\realfiles\\test120k.rmvb" ) );
        creator.create();*/
        
        
        JOptionPane.showMessageDialog(null, "Start" );
        
        

        try{
            fc1 = new FileInputStream("j:\\neembuu\\virtual\\monitored.nbvfs\\test120k.http.rmvb").getChannel();
            //c1 = AsynchronousFileChannel.open(Paths.get("j:\\neembuu\\virtual\\monitored.nbvfs\\test120k.http.rmvb"));
        }catch(Exception e){
            fc1 = null;
        }

        try{
            fc2 = new FileInputStream("j:\\neembuu\\realfiles\\test120k.rmvb").getChannel();
            //fc2 = AsynchronousFileChannel.open(Paths.get("j:\\neembuu\\virtual\\monitored.nbvfs\\test120k.http.rmvb"));
        }catch(Exception e){
            fc2 = null;
        }
        
        assertNotNull(fc1);
        assertNotNull(fc2);


        ByteBuffer region1,region11,region2,region3,region4;
        // creates region such as
        // * Index                     0                               1               2
        // * Offset value          0------>10239---10240-->2K-1    3k----->4k-1    5k----->6k-1

    }

    // <editor-fold defaultstate="collapsed" desc="all split cases diagram">
    /*
     * For a range array
     * neembuu.common.RangeArray{
     * index=0 0->49
     * index=1 251->300
     *  ....
     * }
     * and symbolically
     * Index                     0               1               2               3               4
     * Offset value          A------>B       C------>D       E------>F       G------>H       I------>J
     *
     * A=0, B=49, C=251 and D=300 .... similarly others
     *
     * s = starting of request
     * e = ending of request (inclusive)
     * x1 = indices[0] and x2 = indices[1]
     *
     * x1!=x2 means that a request spans over more than one read handler
     *
     * All possible cases for starting
     * -------------------------------
     * Problem i_th_splitRequestStart = s & i =1
     * Index                     0               1               2               3               4
     * Offset value          A------>B       C---s-->D       E------>F       G------>H       I------>J
     *                                           |______....
     * Solution (case_start_1)
     * Index                     0               1               2               3               4
     * Offset value          A------>B       C---s-->D       E------>F       G------>H       I------>J
     *                                           |__________|___....   (s---->E-1)
     *
     * Problem i_th_splitRequestStart = s & i =1
     * Index                     0               1               2               3               4
     * Offset value          A------>B       C------>D  s    E------>F       G------>H       I------>J
     *                                                  |______....
     * Solution (case_start_2)
     * Index                     0               1               2               3               4
     * Offset value          A------>B       C------>D  s    E------>F       G------>H       I------>J
     *                                                  |____|__....   (s---->E-1)
     *
     * All possible cases for intermediate (case_interm_1)
     * ------------------------------------
     * i_th_splitRequestStart = E & indices[1] > i > indices[0]
     * requestCurrentStart = E
     * Solution
     * Index                     0               1               2               3               4
     * Offset value          A------>B       C------>D       E------>F       G------>H       I------>J
     *                                                       |______________| (E--->G-1)
     * Connection might be dead. In that case the channel will automatically try to make a new connection.
     *
     * All possible cases for ending
     * -----------------------------
     * i_th_splitRequestStart = e & i = indices[1]
     * Solution (case_end_1)
     * Index                     0               1               2               3               4
     * Offset value          A------>B       C------>D       E------>F       G---e-->H       I------>J
     *                                                                       |___|  (from G to end )
     * Index                     0               1               2               3               4
     * Offset value          A------>B       C------>D       E------>F       G------>H    e  I------>J
     *                                                                       |____________| (from G to e which is lying outside H)
     */
    // </editor-fold>

    private static final int allowance = 5*KB;
    private static final int requestSize = (int)(MB*0.4);

    public static void printContentPeek(ByteBuffer bb_tocheck,ByteBuffer  bb_actual){
        System.out.println("bbactual+{"+ContentPeek.generatePeekString(bb_actual)+"}");
        System.out.println("bbtocheck+{"+ContentPeek.generatePeekString(bb_tocheck)+"}");
    }
    
    private String region(long start, int size){
        return start+"-->"+(start-1+size);
    }
    
    //@Test
    public void connectionResumeCheck() throws  Exception {
        // connection should be resumed   
        System.out.println("connectionResumeCheck");
        ByteBuffer region11;
        fc1.position(0);
        fc1.read((region11=ByteBuffer.allocateDirect(MB)));
        JOptionPane.showMessageDialog(null, "Try resume" );
        fc1.position(MB);
        fc1.read((region11=ByteBuffer.allocateDirect(MB)));
        ByteBuffer  bb_actual = ByteBuffer.allocateDirect(MB);
        fc2.position(MB);
        fc2.read(bb_actual);
        checkBuffers(region11, bb_actual);
    }

    @Test
    public void linearRead() throws Exception{
        System.out.println("linearRead="+region(0,requestSize));
        ByteBuffer bb_toCheck, bb_actual;
        fc1.position(0);
        fc1.read((bb_toCheck=ByteBuffer.allocateDirect(requestSize)));
        fc2.position(0);
        fc2.read((bb_actual=ByteBuffer.allocateDirect(requestSize)));

        printContentPeek(bb_toCheck, bb_actual);
        checkBuffers(bb_toCheck, bb_actual);
    }

    @Test
    public void case_start_1_sameend() throws Exception{
        // * Index                     0                               1               2
        // * Offset value          0------>10239---10240-->2K-1    3k----->4k-1    5k----->6k-1
        System.out.println("case_start_1_sameend "+region(MB-allowance,requestSize));
        ByteBuffer bb_toCheck, bb_actual;
        fc1.position(MB-allowance);
        fc1.read((bb_toCheck=ByteBuffer.allocateDirect(requestSize)));
        fc2.position(MB-allowance);
        fc2.read((bb_actual=ByteBuffer.allocateDirect(requestSize)));

        printContentPeek(bb_toCheck, bb_actual);
        checkBuffers(bb_toCheck, bb_actual);
    }

    @Test
    public void case_start_2_sameend() throws Exception{
        // * Index                     0                               1               2
        // * Offset value          0------>10239---10240-->2K-1    3k----->4k-1    5k----->6k-1
        System.out.println("case_start_2_sameend "+region(MB+allowance,requestSize));
        ByteBuffer bb_toCheck, bb_actual;
        fc1.position(MB+allowance);
        fc1.read((bb_toCheck=ByteBuffer.allocateDirect(requestSize)));
        fc2.position(MB+allowance);
        fc2.read((bb_actual=ByteBuffer.allocateDirect(requestSize)));

        printContentPeek(bb_toCheck, bb_actual);
        checkBuffers(bb_toCheck, bb_actual);
    }

    @Test
    public void case_start_2_end() throws Exception{
        System.out.println("case_start_2_end "+region(MB+allowance,MB*2));
        ByteBuffer bb_toCheck, bb_actual;
        fc1.position(MB+allowance);
        fc1.read((bb_toCheck=ByteBuffer.allocateDirect(MB*2)));
        fc2.position(MB+allowance);
        fc2.read((bb_actual=ByteBuffer.allocateDirect(MB*2)));

        printContentPeek(bb_toCheck, bb_actual);
        checkBuffers(bb_toCheck, bb_actual);
    }

    @Test
    public void case_start_1_end() throws Exception{
        // * Index                     0                               1               2
        // * Offset value          0------>10239---10240-->2K-1    3k----->4k-1    5k----->6k-1
        System.out.println("case_start_1_end "+region(MB-allowance, MB*2 + 2*allowance));
        ByteBuffer bb_toCheck, bb_actual;
        int start = MB-allowance;
        int size = //30719 - start +1; 
            MB*2 + 2*allowance;
        fc1.position(start);
        fc1.read((bb_toCheck=ByteBuffer.allocateDirect(size)));
        fc2.position(start);
        fc2.read((bb_actual=ByteBuffer.allocateDirect(size)));


        printContentPeek(bb_toCheck, bb_actual);
        checkBuffers(bb_toCheck, bb_actual);
    }

    @Test
    public void case_start_singleinter_end() throws Exception{
        System.out.println("case_start_singleinter_end "+region(MB-allowance, MB*4 + 2*allowance));
        // * Index                     0                               1               2
        // * Offset value          0------>10239---10240-->2K-1    3k----->4k-1    5k----->6k-1
        ByteBuffer bb_toCheck, bb_actual;
        fc1.position(MB-allowance);
        fc1.read((bb_toCheck=ByteBuffer.allocateDirect(MB*4 + 2*allowance)));
        fc2.position(MB-allowance);
        fc2.read((bb_actual=ByteBuffer.allocateDirect(MB*4 + 2*allowance)));

        printContentPeek(bb_toCheck, bb_actual);
        checkBuffers(bb_toCheck, bb_actual);
    }

    @Test
    public void case_start_multipleinter_end_inside() throws Exception{
        // * Index                     0                               1               2             3
        // * Offset value          0------>10239---10240-->2K-1    3k----->4k-1    5k----->6k-1   7k----->8k-1
        //                              |_____________________________________________________________|
        System.out.println("case_start_multipleinter_end_inside "+region(MB-allowance,MB*7 + 2*allowance));
        ByteBuffer bb_toCheck, bb_actual;
        fc1.position(MB-allowance);
        fc1.read((bb_toCheck=ByteBuffer.allocateDirect(MB*7 + 2*allowance)));
        fc2.position(MB-allowance);
        fc2.read((bb_actual=ByteBuffer.allocateDirect(MB*7 + 2*allowance)));

        printContentPeek(bb_toCheck, bb_actual);
        checkBuffers(bb_toCheck, bb_actual);
    }

    @Test
    public void case_start_multipleinter_end_outside() throws Exception{
        // * Index                     0                               1               2             3
        // * Offset value          0------>10239---10240-->2K-1    3k----->4k-1    5k----->6k-1   7k----->8k-1
        //                              |__________________________________________________________________________|
        System.out.println("case_start_multipleinter_end_outside "+region(MB-allowance, MB*9 + 2*allowance));
        ByteBuffer bb_toCheck, bb_actual;
        fc1.position(MB-allowance);
        fc1.read((bb_toCheck=ByteBuffer.allocateDirect(MB*9 + 2*allowance))); // a
        fc2.position(MB-allowance);
        fc2.read((bb_actual =ByteBuffer.allocateDirect(MB*9 + 2*allowance)));

        printContentPeek(bb_toCheck, bb_actual);
        checkBuffers(bb_toCheck, bb_actual);
    }

    
    public static void checkBuffers(ByteBuffer toCheck, ByteBuffer actual){
        toCheck = (ByteBuffer)toCheck.position(0);
        actual = (ByteBuffer)actual.position(0);
        for (int i = 0; i < toCheck.capacity(); i++) {
            if(toCheck.get()!=actual.get()){
                System.out.println("matches till="+(i-1));
                assertTrue(false);
            }
        }        
    }
}
