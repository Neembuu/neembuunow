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

package neembuu.release1.settings;

import neembuu.release1.api.settings.Settings;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.app.Application;
import neembuu.release1.api.log.LoggerUtil;

/**
 *
 * @author Shashank Tulsyan
 */
public class SettingsImpl implements Settings {
    
    public static Settings I(){
        return new SettingsImpl();
    }
    
    private final static Logger l = LoggerUtil.getLogger(SettingsImpl.class.getName());

    private SettingsImpl() {
    }
    
    @Override
    public SeekableByteChannel getResource(String ... name) throws IOException {
        return getResource(name, WRITE,READ,CREATE);
    }
    
    @Override
    public SeekableByteChannel getResource(String[]name, OpenOption ... options) throws IOException {
        String[]r=new String[name.length+1];
        System.arraycopy(name, 0, r, 1, name.length);
        r[0]="settings";
        Path p = Application.getResource(r);
        Files.createDirectories(p.getParent());
        return FileChannel.open(p, options);
    }
    
    @Override
    public String get(String ... name){
        return get(10240,name);//10KB
    }
    
    @Override
    public boolean getBoolean(String ... name){
        String s = get(name);//10KB
        if(s==null)return false;
        try{
            return Boolean.parseBoolean(s);
        }catch(Exception a){
            
        }return false;
    }
    
    private String get(long sizeLimit,String ... name){
        try(SeekableByteChannel sbc = getResource(name)){
            long sz = sbc.size();
            if(sz < sizeLimit) {// 10KB
                ByteBuffer bb = ByteBuffer.allocate((int)sz);
                int r = sbc.read(bb);
                if(r<sz){
                    throw new UnsupportedOperationException("read did not succeed, req="+sz+" f="+r);
                }
                String value = new String(bb.array());
                return value;
            }else {
                throw new UnsupportedOperationException("Very long string data, size="+sizeLimit);
            }
        }catch(Exception a){
            l.log(Level.INFO, "could not get resource="+name, a);
        }
        
        return null;
    }
    
    @Override
    public long getLong(String ... name){
        String s = get(name);//10KB
        if(s==null)return 0;
        try{
            return Long.parseLong(s);
        }catch(Exception a){
            
        }return 0;
    }
    
    @Override
    public boolean setLong(long v, String ... name){
        return set(Long.toString(v), name);
    }
    
    @Override
    public boolean setBoolean(boolean v, String ... name){
        return set(Boolean.toString(v), name);
    }
    
    @Override
    public boolean set(String strV, String ... name){
        try(SeekableByteChannel s = getResource(name,WRITE,CREATE,TRUNCATE_EXISTING)){
            ByteBuffer bb = ByteBuffer.wrap(strV.getBytes());
            int w = s.write(bb);
            if(w<bb.capacity()){
                throw new UnsupportedOperationException("underlying channel writting data partially"
                        + " expected_write_size="+bb.capacity()+" actually_write_size="+w);
            }
            return true;
        }catch(IOException ioe){
            
        }
        return false;
        
    }
}
