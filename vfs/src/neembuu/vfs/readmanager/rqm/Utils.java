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

package neembuu.vfs.readmanager.rqm;

import java.util.Iterator;
import neembuu.rangearray.Range;
import neembuu.vfs.readmanager.RegionHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public class Utils {
    static String handlersToString(Iterator<Range<RegionHandler>> it){
        //RangeArray rangeArray = handlers;
        StringBuilder br=new StringBuilder(100);
        //br.append(rangeArrayDisplayName);
        br.append("{\n");
        //Iterator<Range> it; = rangeArray.iterator();
        int j=0;
        while(it.hasNext()){
            //long start,long start,DownloadManager.Downloader d,boolean ver
            Range next = it.next();
            br.append("[");br.append(j);br.append("] ");
            br.append(regionHandlerToString(next));
            br.append("\n");
            j++;
        }br.append("}");
        return br.toString();
    }
    
    private static String regionHandlerToString(Range range){
        if(range.getProperty() instanceof RegionHandler){
            RegionHandler rh = (RegionHandler)range.getProperty();
            String basic = rh.getClass().getSimpleName()+"{"+
                    rh.starting()+"-->"+rh.ending()+
                    " ,authl="+rh.authorityLimit()+" isAlive="+rh.isAlive()+ 
                    " ,size="+(rh.ending() - rh.starting() +1 )
                    +" ,isMain="+rh.isMainDirectionOfDownload()
                    + "}";
            
            String[]pendingRq = rh.getPendingOperationsAsString();
            for (int i = 0; i < pendingRq.length; i++) {
                basic = basic +"\n\t\t" + pendingRq[i];
            }
            return basic;
        }
        return range.getProperty().getClass().getSimpleName()+"{"+range.starting()+"-->"+range.ending()+"}";
    }
}
