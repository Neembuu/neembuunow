/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.newlink;

import neembuu.release1.api.LinkHandler;
import neembuu.release1.api.Link;

/**
 *
 * @author Shashank Tulsyan
 */
public class LinksImpl implements Link{

    private final LinkHandler f;
    private final String link;

    public LinksImpl(LinkHandler f, String link) {
        this.f = f;
        this.link = link;
    }
    
    @Override
    public LinkHandler getLinkHandler() {
        return f;
    }

    @Override
    public String getLink() {
        return link;
    }
    
}
