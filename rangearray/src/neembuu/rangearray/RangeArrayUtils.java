/*
 * Copyright (c) 2011 Shashank Tulsyan <shashaanktulsyan@gmail.com>. 
 * 
 * This is part of free software: you can redistribute it and/or modify
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
 * along with this.  If not, see <http ://www.gnu.org/licenses/>.
 */
package neembuu.rangearray;

import java.util.Iterator;

/**
 *
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
public final class RangeArrayUtils {
    
    public static Range add(RangeArray rangeArray,long start,int size){
        return rangeArray.addElement(start, start+size-1,null);
    }
    
    public static String toString(RangeArray rangeArray) {
        StringBuilder br=new StringBuilder(100);
        //br.append(rangeArrayDisplayName);
        br.append("{\n");
        Iterator<Range> it = rangeArray.iterator();
        int j=0;
        while(it.hasNext()){
            //long start,long start,DownloadManager.Downloader d,boolean ver
            Range next = it.next();
            br.append(" index=");
            br.append(j);
            br.append(" ");
            br.append(RangeUtils.toString(next));
            br.append(" size=");
            br.append( (next.ending() - next.starting() +1 ) );
            br.append("\n");
            j++;
        }br.append("}");
        return br.toString();
    }
}
