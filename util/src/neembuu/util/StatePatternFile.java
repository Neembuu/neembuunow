/*
 *  Copyright (C) 2009-2010 Shashank Tulsyan
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
 *
 *
 *
 *  Linking this library statically or
 *  dynamically with other modules is making a combined work based on this library.
 *  Thus, the terms and conditions of the GNU General Public License cover the whole combination.
 *
 *
 *  As a special exception, the copyright holders of this library give you permission to
 *  link this library with independent modules to produce an executable, regardless of
 *  the license terms of these independent modules, and to copy and
 *  distribute the resulting executable under terms of your choice,
 *  provided that you also meet, for each linked independent module,
 *  the terms and conditions of the license of that module.
 *  An independent module is a module which is not derived from or based on this library.
 *  If you modify this library, you may extend this exception to your version of the library,
 *  but you are not obligated to do so. If you do not wish to do so,
 *  delete this exception statement from your version.
 *
 *  File : StatePatternFile.java
 *  Author : Shashank Tulsyan
 */
package neembuu.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import neembuu.rangearray.RangeArray;
import neembuu.rangearray.RangeArrayFactory;
import neembuu.rangearray.RangeArrayParams;

/**
 *
 * @author Shashank Tulsyan
 */
public final class StatePatternFile {
    public static void createStatePatternFile(RangeArray array, FileChannel fc, String rangeArrayName) throws IOException {
        fc.write(ByteBuffer.wrap(array.toString().getBytes()));
    }

    @SuppressWarnings(value="unchecked")
    public static RangeArray createRangeArrayFrom(String spfPath) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(spfPath));
        String line=null;
        RangeArray array = 
                RangeArrayFactory.newDefaultRangeArray(new RangeArrayParams.Builder().build());
                //new RangeArray(10000);//it is like that :P ...
        // these things tend to be very big
        // okay,
        // todo : reduce this
        int spid = -1; String start, end; final String space = " ";
        while((line=br.readLine())!=null){
            spid = line.indexOf("->");
            if(spid==-1)continue;
            start = line.substring(0, spid);
            start = start.substring(start.lastIndexOf(space)+1);
            end = line.substring(spid);
            end = end.substring(2,end.indexOf(space));
            
            array.addElement(Long.parseLong(start),Long.parseLong(end),null);
        }
        
        //array.trimToSize();//??
        return array;
    }

    /*public static void main(String[] args) throws IOException{
        createRangeArrayFrom("J:\\neembuu\\patterns\\deathnote_avi_audio_extract.requeststate");
    }*/
}
