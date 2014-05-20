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
package neembuu.release1.defaultImpl.linkhandler;

import java.util.LinkedList;
import java.util.List;
import neembuu.release1.api.file.OnlineFile;
import neembuu.release1.api.linkhandler.LinkHandler;
import neembuu.release1.defaultImpl.file.BasicOnlineFile;
import neembuu.vfs.connection.NewConnectionProvider;
import neembuu.vfs.connection.jdimpl.JD_DownloadManager;

/**
 *
 * @author Shashank Tulsyan
 */
public final class BasicLinkHandler implements LinkHandler {

    private String groupName;
    private final List<OnlineFile> files = new LinkedList<OnlineFile>();

    @Override public List<OnlineFile> getFiles() { return files; }
    @Override public String getGroupName() { return groupName; }
    @Override public boolean foundName() { return groupName != null; }

    public static final class Builder {

        BasicLinkHandler linkHandler = new BasicLinkHandler();

        public static Builder create() {
            return new Builder();
        }
        
        public List<OnlineFile> getFiles() { return linkHandler.files; }

        public Builder addFile(BasicOnlineFile bof) {
            linkHandler.files.add(bof); return this;
        }
        
        public BasicOnlineFile.Builder createFile(){
            return BasicOnlineFile.Builder.create(this);
        }
        
        public Builder addFile(NewConnectionProvider ncp, String name, long size) {
            linkHandler.files.add(createFile().setName(name).setNewConnectionProvider(ncp).setSize(size).build()); return this;
        }

        public Builder addFile(String url, String name, long size) {
            return addFile(
                    new JD_DownloadManager(url)
                    //new DownloadManager(url)
                    , name, size);
        }

        public Builder setGroupName(String groupName) {
            linkHandler.groupName = groupName; return this;
        }

        public BasicLinkHandler build() {
            if(linkHandler.files.isEmpty()){
                throw new IllegalArgumentException("No files initialized from reference link");
            }if (linkHandler.groupName == null) {
                throw new IllegalArgumentException("File groupName unknown");
            }if (linkHandler.groupName.length() == 0) {
                throw new IllegalArgumentException("File name unknown : groupName.length() == 0");
            }
            return linkHandler;
        }

    }

}
