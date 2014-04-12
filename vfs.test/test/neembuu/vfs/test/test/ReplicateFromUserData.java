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

package neembuu.vfs.test.test;

import org.junit.Test;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import neembuu.rangearray.RangeArrayFactory;
import neembuu.rangearray.RangeArrayParams;
import org.junit.BeforeClass;
import static junit.framework.Assert.*;
import static neembuu.vfs.test.test.Common.*;
import neembuu.vfs.test.test.RequestsList.Request;
/**
 *
 * @author Shashank Tulsyan
 */
public final class ReplicateFromUserData {
    
    private static SeekableByteChannel /*FileChannel*/ fc_virtual_file=null;//fc_virtual_file = null;
    private static SeekableByteChannel /*FileChannel*/ fc_real_file=null;//fc_real_file = null;
    
    @BeforeClass
    public static void initalize() throws Exception{
        String fileName = "bigbuckbunny(200v+64a)kbps.mkv";
        
        
        //1397223376463	25002850	25003446
        //1397223377144	25007543	25007702
        //1397223377166	25002439	25002974
        //1397223377377	24999810	25001365
        
        emptyTemporarySplitStorageDirectory(fileName);        
        fc_real_file = createNew_FC_Real_File(fileName);
        downloadFollowingRegionsBeforeTesting(
            fc_real_file,
            RangeArrayFactory.newDefaultRangeArray(
                new RangeArrayParams.Builder()
                    .build()
            )
        );
        
        boolean cascade = show_StartNeembuuMessage();
        fc_virtual_file = createNew_FC_Virtual_File(cascade,fileName);
        
        
        assertNotNull(fc_virtual_file);
        assertNotNull(fc_real_file);

    }
    
    @Test
    public void test() throws Exception{
        final ArrayList<Request> requests = RequestsList.createList("f:\\listOfRequests.txt");

        
        int total = requests.size();
        int i =1;
        for (Request request : requests) {
            System.out.println("i="+i+"/"+total); i++;
            test_start_end(
                fc_virtual_file, 
                fc_real_file, 
                request.start, 
                request.end);
        }
        
        /*Thread[]t = new Thread[10];
        for (int i = 0; i < t.length; i++) {
            t[i] =new ReadThread(
                    (long)(Math.random()*10*MB), 100*KB, 5,true);
            t[i].start();
        }
        
        for (int i = 0; i < t.length; i++) {
            t[i].join();
        }*/
        

    }
    
    private void trySleep(long r) {
        if(r<=0)return;
        try{Thread.sleep(r);}
        catch(InterruptedException a){}
    }
    
}

