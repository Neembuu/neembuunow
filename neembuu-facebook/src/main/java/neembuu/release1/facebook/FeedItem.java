/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.facebook;

/**
 * The feed item.
 * @author davidepastore
 */
public interface FeedItem {
    
    /**
     * Get the text.
     * @return Returns the text.
     */
    public String text();
    
    /**
     * Get the url of the video.
     * @return Returns the video url.
     */
    public String url();
    
    /**
     * Get the image url of the video.
     * @return Returns the image url of the video.
     */
    public String imageURL(); // or image object
    
    /**
     * Returns the author name.
     * @return Returns the author name.
     */
    public String authorName();
}
