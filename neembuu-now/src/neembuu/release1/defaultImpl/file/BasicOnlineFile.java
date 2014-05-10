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

import java.util.logging.Level;
import jpfm.util.UniversallyValidFileName;
import neembuu.release1.api.file.OnlineFile;
import neembuu.release1.api.file.PropertyProvider;
import neembuu.release1.defaultImpl.linkhandler.BasicLinkHandler;
import neembuu.vfs.connection.NewConnectionProvider;
import neembuu.vfs.connection.jdimpl.JD_DownloadManager;

/**
 *
 * @author Shashank Tulsyan
 */
public final class BasicOnlineFile implements OnlineFile {
    private final NewConnectionProvider ncp;
    private final String name;
    private final long size;
    
    private final BasicPropertyProvider bpp;

    BasicOnlineFile(NewConnectionProvider ncp, String name, long size,BasicPropertyProvider bpp) {
        if(ncp==null){
            throw new IllegalStateException("Null NewConnectionProvider ");
        }if(name==null){
            throw new IllegalStateException("File name not set");
        }if(size<=0){
            throw new IllegalStateException("File size not set, size="+size);
        }
        this.bpp = bpp;
        this.ncp = ncp;
        this.name = name;
        this.size = size;
    }

    @Override public NewConnectionProvider getConnectionProvider() { 
        return ncp; }

    @Override public String getName() { return name; }

    @Override public long getFileSize() {  return size; }
    
    @Override public PropertyProvider getPropertyProvider() { return bpp; }
    
    public static final class Builder {
        private NewConnectionProvider ncp = null;
        private String name;
        private long size;
        final BasicPropertyProvider bpp = new BasicPropertyProvider();
        
        private final BasicLinkHandler.Builder builder;

        public Builder(BasicLinkHandler.Builder builder) {
            this.builder = builder;
        }
        
        public Builder putLongPropertyValue(PropertyProvider.LongProperty k, long property) {
            bpp.putLongPropertyValue(k, property);return this;
        }

        public Builder putStringPropertyValue(PropertyProvider.StringProperty k, String property) {
            bpp.putStringPropertyValue(k, property);return this;
        }
        
        public static Builder create(){
            return new Builder(null);
        }
        public static Builder create(BasicLinkHandler.Builder builder){
            return new Builder(builder);
        }

        public Builder setNewConnectionProvider(NewConnectionProvider ncp) {
            if(this.ncp!=null){throw new IllegalStateException("ncp already initialized to="+this.ncp);}
            this.ncp = ncp; return this;
        }
        
        public Builder setUrl(String url) {
            return setNewConnectionProvider(
                    new JD_DownloadManager(url)
                    //new DownloadManager(url)
            );
        }

        public Builder setName(String name) {
            // we must normalize name
            if(!UniversallyValidFileName.isUniversallyValidFileName(name)){
                neembuu.release1.Main.getLOGGER().log(Level.SEVERE, "fileName need to be normalized {0}", name);
                name = UniversallyValidFileName.makeUniversallyValidFileName(name);
                neembuu.release1.Main.getLOGGER().log(Level.SEVERE, "fileName after normalization {0}", name);
            }
            this.name = name; return this;
        }

        public Builder setSize(long size) {
            this.size = size;return this;
        }
        
        public BasicOnlineFile build(){
            BasicOnlineFile bof = new BasicOnlineFile(ncp, name, size,bpp);
            if(builder!=null){ builder.addFile(bof); }
            return bof;
        }
        
        public BasicLinkHandler.Builder next(){
            BasicOnlineFile bof = new BasicOnlineFile(ncp, name, size,bpp);
            if(builder!=null){ builder.addFile(bof); return builder; }
            else throw new NullPointerException("Next can be only called when using nested Builder");
        }
        
    }

}