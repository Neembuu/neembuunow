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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Shashank Tulsyan
 */
public class CombinedException extends Exception{
    private final ArrayList<Exception> exceptions;

    public CombinedException(ArrayList<Exception> exceptions) {
        super(makeMessage(exceptions));
        if(exceptions.isEmpty())throw new IllegalArgumentException("Exceptions list is empty");
        this.exceptions = exceptions;
    }
    
    private static String makeMessage(ArrayList<Exception> exceptions){
        String combinedMessage = "";
        for (int i = 0; i < exceptions.size(); i++) {
            combinedMessage += MessageFromException.make(exceptions.get(i));
            if(i<exceptions.size()-1)combinedMessage+="\n";
        }
        return combinedMessage;
    }
    
    public final Class mostCommonCause(){
        List<Class> t = new ArrayList<>();
        for (Exception exception : exceptions) {
            t.add(exception.getClass());
        }
        return mostCommon(t);
    }
    
    private static <T> T mostCommon(List<T> list) {
        Map<T, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Entry<T, Integer> max = null;

        for (Entry<T, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return max.getKey();
    }
    
}
