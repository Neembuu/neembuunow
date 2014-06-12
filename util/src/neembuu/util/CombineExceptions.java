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

package neembuu.util;

import java.util.ArrayList;

/**
 *
 * @author Shashank Tulsyan
 */
public final class CombineExceptions {
    public static Exception combine(ArrayList<Exception> exceptions){
        for(Exception a : exceptions){
            System.out.println("ex="+a);
        }
        if(exceptions.isEmpty())return null;
        if(exceptions.size()==1){
            return exceptions.get(0);
        }
        Class clz = null;
        for(Exception a : exceptions){
            if(clz==null){
                clz = a.getClass();
            }
            if(clz != a.getClass()){
                return new CombinedException(exceptions);
            }
        }
        
        // homo
        Exception a = exceptions.get(0);
        for (int i = 1; i < exceptions.size(); i++) {
            a.addSuppressed(exceptions.get(i));
        }
        
        return a;
    }
    
}
