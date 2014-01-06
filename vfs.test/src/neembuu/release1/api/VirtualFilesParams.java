/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api;

import neembuu.diskmanager.DiskManager;
import neembuu.vfs.file.TroubleHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public final class VirtualFilesParams {
    private final Link link;
    private final LinkHandler linkHandler;
    private final DiskManager diskManager;
    private final TroubleHandler troubleHandler;
    private final String fileName;

    public VirtualFilesParams(Builder b) {
        this.link = b.link;
        this.linkHandler = b.linkHandler;
        this.diskManager = b.diskManager;
        this.troubleHandler = b.troubleHandler;
        this.fileName = b.fileName;
    }

    public Link getLink() {
        return link;
    }

    public LinkHandler getLinkHandler() {
        return linkHandler;
    }

    public DiskManager getDiskManager() {
        return diskManager;
    }

    public TroubleHandler getTroubleHandler() {
        return troubleHandler;
    }

    public String getFileName() {
        return fileName;
    }
    
    public static final class Builder {
        private Link link;
        private LinkHandler linkHandler;
        private DiskManager diskManager;
        private TroubleHandler troubleHandler;
        private String fileName;
        
        private Builder(){
            
        }
        
        public VirtualFilesParams build(){
            return new VirtualFilesParams(this);
        }
        
        public static Builder create(){
            return new Builder();
        }

        public Builder setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }
        
        public Builder setLink(Link link) {
            this.link = link;
            return this;
        }

        public Builder setLinkHandler(LinkHandler linkHandler) {
            this.linkHandler = linkHandler;
            return this;
        }

        public Builder setDiskManager(DiskManager diskManager) {
            this.diskManager = diskManager;
            return this;
        }

        public Builder setTroubleHandler(TroubleHandler troubleHandler) {
            this.troubleHandler = troubleHandler;
            return this;
        }

    }

}
