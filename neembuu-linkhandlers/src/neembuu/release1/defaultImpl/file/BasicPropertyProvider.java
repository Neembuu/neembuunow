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

package neembuu.release1.defaultImpl.file;

import java.util.HashMap;
import neembuu.release1.api.file.PropertyProvider;

/**
 *
 * @author Shashank Tulsyan
 */
public final class BasicPropertyProvider implements PropertyProvider{
    private final HashMap map = new HashMap();
    
    public long putLongPropertyValue(LongProperty k, long property) {
        Object v = map.put(k,property); if(v==null){return PROPERTY_NOT_FOUND;}
        return (long)v;
    }
    
    public boolean putBooleanPropertyValue(BooleanProperty k, boolean property) {
        Object v = map.put(k,property); if(v==null){return false;}
        return (boolean)v;
    }
    
    public String putStringPropertyValue(StringProperty k, String property) {
        Object v = map.put(k,property);
        return (String)v;
    }
    
    
    
    @Override
    public long getLongPropertyValue(LongProperty k) {
        Object v = map.get(k); if(v==null){return PROPERTY_NOT_FOUND;}
        return (long)v;
    }

    @Override
    public String getStringPropertyValue(StringProperty k) {
        Object v = map.get(k);
        return (String)v;
    }

    @Override
    public boolean getBooleanPropertyValue(BooleanProperty k) {
        Object v = map.get(k); if(v==null){return false;}
        return (boolean)v;
    }
    
}
