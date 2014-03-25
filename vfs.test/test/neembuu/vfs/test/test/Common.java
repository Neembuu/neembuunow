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

import neembuu.rangearray.Range;
import neembuu.rangearray.RangeArray;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import jpfm.util.ContentPeek;
import neembuu.rangearray.RangeUtils;
import static junit.framework.Assert.*;
import neembuu.vfs.test.Main;
import neembuu.vfs.test.MountManagerService;

/**
 *
 * @author Shashank Tulsyan
 */
public final class Common {
    
    public static  final int KB = //1000;
                          1024;
    public static  final int MB = KB*KB;
    public static  final int allowance = 5*KB;
    
    public static  void emptyTemporarySplitStorageDirectory()throws IOException{
        File storageDirectory =new File("j:\\neembuu\\heap\\test120k.rmvb_neembuu_download_data");
        
        File[]files = storageDirectory.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            file.delete();            
        }
    }
    public static boolean show_StartNeembuuMessage(){
        //JOptionPane.showMessageDialog(null, "Start Neembuu and then press ok");
        JFrame fr = new JFrame("Test");
        final String[]options={"Cascade mount", "Direct Mount"};
        int se = JOptionPane.showOptionDialog(fr, "Start Neembuu and then press ok", "Choose", 
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE , null,options ,options[0]);
        return (se == 0);
    }
    
    public static  void downloadFollowingRegionsBeforeTesting(
            SeekableByteChannel /*FileChannel*/ fc_real_file,
            RangeArray<Range> regions)throws IOException{
        downloadFollowingRegionsBeforeTesting(fc_real_file, regions, "test120k.rmvb");
    }
    
    public static  void downloadFollowingRegionsBeforeTesting(
            SeekableByteChannel /*FileChannel*/ fc_real_file,
            RangeArray<Range> regions,
            String filename) throws IOException{
        File storageDirectory =new File("j:\\neembuu\\heap\\"
                + filename
                + "_neembuu_download_data");
        
        for(Range element : regions){
            System.out.println(element.starting()+"-->"+element.ending());
            File region = new File(storageDirectory, Math.random()+"_0x"+Long.toHexString(element.starting())+".partial");
            region.createNewFile();
            FileChannel fc_split_file = new FileOutputStream(region).getChannel();
            
            ByteBuffer contents;
            fc_real_file.position(element.starting());
            int contentRead = fc_real_file.read((contents=ByteBuffer.allocateDirect((int)RangeUtils.getSize(element))));
            assertEquals(contentRead, RangeUtils.getSize(element));
            contents.rewind();
            int contentWritten=fc_split_file.write(contents);
            assertEquals(contentWritten, contentRead);
            fc_split_file.force(true);
            fc_split_file.close();
        }
    }
    
    
    public static SeekableByteChannel createNew_FC_Virtual_File(boolean cascade)throws Exception{
        if(!cascade)
            return createNew_FC_Virtual_File("test120k.rmvb");
        else {
            Main.main(new String[]{"cascadeMount"});
            MountManagerService mm = Main.mountManagerService;
            return mm.get("test120k.rmvb");
        }
    }
    
    public static  FileChannel createNew_FC_Virtual_File(String n){
        FileChannel fc_virtual_file = null;
        try{
            fc_virtual_file = new FileInputStream("j:\\neembuu\\virtual\\monitored.nbvfs\\"+n).getChannel();
            //c1 = AsynchronousFileChannel.open(Paths.get("j:\\neembuu\\virtual\\monitored.nbvfs\\test120k.http.rmvb"));
        }catch(Exception e){
            fc_virtual_file = null;
        }
        
        return fc_virtual_file;
    }
    
    public static FileChannel createNew_FC_Real_File(){
        return createNew_FC_Real_File("test120k.rmvb");
    }
    
    public static  FileChannel createNew_FC_Real_File(String n){
        FileChannel fc_real_file = null;
        try{
            fc_real_file = new FileInputStream("j:\\neembuu\\realfiles\\"+n).getChannel();
            //fc2 = AsynchronousFileChannel.open(Paths.get("j:\\neembuu\\virtual\\monitored.nbvfs\\test120k.http.rmvb"));
        }catch(Exception e){
            fc_real_file = null;
        }return fc_real_file;
    }
    
    public static  void printContentPeek(ByteBuffer bb_tocheck,ByteBuffer  bb_actual){
        System.out.println("bbactual+{"+ContentPeek.generatePeekString(bb_actual)+"}");
        System.out.println("bbtocheck+{"+ContentPeek.generatePeekString(bb_tocheck)+"}");
    }
    
    public static  void checkBuffers(ByteBuffer toCheck, ByteBuffer actual){
        toCheck = (ByteBuffer)toCheck.position(0);
        actual = (ByteBuffer)actual.position(0);
        for (int i = 0; i < toCheck.capacity(); i++) {
            if(toCheck.get()!=actual.get()){
                System.out.println("matches till="+(i-1));
                assertTrue(false);
            }
        }        
    }
    
    public static  String region(long start, int size){
        return "{"+start+"-->"+(start-1+size)+"}";
    }
    
    
    public static  void test_start_end(
                SeekableByteChannel /*FileChannel*/ fc_virtual_file,
                SeekableByteChannel /*FileChannel*/ fc_real_file,
                long start,
                long end
            )throws IOException{
        String testname = sun.reflect.Reflection.getCallerClass(2).getSimpleName();
        System.out.println(testname+"={"+start+"-->"+end+"}");
        int size = (int)(end - start +1);
        ByteBuffer bb_toCheck, bb_actual;
        fc_virtual_file.position(start);
        fc_virtual_file.read((bb_toCheck=ByteBuffer.allocateDirect(size)));
        fc_real_file.position(start);
        fc_real_file.read((bb_actual=ByteBuffer.allocateDirect(size)));

        printContentPeek(bb_toCheck, bb_actual);
        checkBuffers(bb_toCheck, bb_actual);
    }
    
    public static  void test_start_size(
                SeekableByteChannel /*FileChannel*/ fc_virtual_file,
                SeekableByteChannel /*FileChannel*/ fc_real_file,
                long start,
                int size
            )throws IOException{    
        String testname = sun.reflect.Reflection.getCallerClass(2).getSimpleName(); // takes around 40 microsecs
        System.out.println(testname+"="+region(0,size));
        ByteBuffer bb_toCheck, bb_actual;
        fc_virtual_file.position(start);
        fc_virtual_file.read((bb_toCheck=ByteBuffer.allocateDirect(size)));
        fc_real_file.position(start);
        fc_real_file.read((bb_actual=ByteBuffer.allocateDirect(size)));

        printContentPeek(bb_toCheck, bb_actual);
        checkBuffers(bb_toCheck, bb_actual);
    }
}
