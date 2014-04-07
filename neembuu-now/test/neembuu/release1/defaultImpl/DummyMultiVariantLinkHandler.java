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

package neembuu.release1.defaultImpl;

import neembuu.release1.defaultImpl.file.BasicOnlineFile;
import java.util.ArrayList;
import java.util.List;
import neembuu.release1.ui.linkpanel.FakeConnectionProvider;
import neembuu.release1.api.file.OnlineFile;
import neembuu.release1.api.file.PropertyProvider;
import neembuu.release1.api.linkhandler.LinkHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public class DummyMultiVariantLinkHandler implements LinkHandler{
    private final List<OnlineFile> ofs = new ArrayList<OnlineFile>();
    
    public DummyMultiVariantLinkHandler() {
        ofs.add(make("BigBuckBunny_320x180.mp4",500,"320x180"));
        ofs.add(make("BigBuckBunny_640x360.m4v",1000,"640,360"));
        ofs.add(make("big_buck_bunny_480p_stereo.avi",1500,"480p"));
        ofs.add(make("big_buck_bunny_720p_stereo.avi",3000,"720p"));       
    }
    
    private OnlineFile make(String name, double speed,String q){
        String fullPath = "J:\\neembuu\\realfiles\\BigBuckBunny\\"+name;
        long size = getFileSize(fullPath);
        return BasicOnlineFile.Builder.create()
            .putStringPropertyValue(PropertyProvider.StringProperty.VARIANT_DESCRIPTION, q)
            .putLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS, 596*1000)
            .setName(name).setSize(size)
            .setNewConnectionProvider(new FakeConnectionProvider(fullPath,speed)).build();
    }
    
    private long getFileSize(String pth) { 
        long sz = 1;
        try {
            sz = new java.io.File(pth).length();
        } catch (Exception a) {
            a.printStackTrace();
        }
        return sz; 
    }
    
    @Override
    public List<OnlineFile> getFiles() {
        return ofs;
    }

    @Override
    public String getGroupName() {
        return "Big buck bunny";
    }

    @Override
    public boolean foundName() {
        return true;
    }    
}
