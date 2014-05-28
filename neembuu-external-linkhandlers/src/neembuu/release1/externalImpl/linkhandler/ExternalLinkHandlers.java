/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.externalImpl.linkhandler;

import neembuu.release1.defaultImpl.external.ExternalLinkHandlerEntry;

/**
 *
 * @author Shashank Tulsyan
 */
public class ExternalLinkHandlers {
    private final ExternalLinkHandlerEntry[] handlers;
    private final long creationTime = System.currentTimeMillis();
    private final String createdBy;

    public ExternalLinkHandlers(ExternalLinkHandlerEntry[] handlers, String createdBy) {
        this.handlers = handlers;
        this.createdBy = createdBy;
    }
    

    public ExternalLinkHandlerEntry[] getHandlers() {
        return handlers;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }
    
    
}
