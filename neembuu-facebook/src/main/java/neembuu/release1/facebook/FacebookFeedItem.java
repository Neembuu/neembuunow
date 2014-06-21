/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.facebook;

/**
 * Facebook Feed Item.
 * @author davidepastore
 */
public class FacebookFeedItem implements FeedItem{
    
    private String text;
    
    private String url;
    
    private String imageURL;
    
    private String authorName;

    public String text() {
        return text;
    }

    public String url() {
        return url;
    }

    public String imageURL() {
        return imageURL;
    }
    
    public String authorName(){
        return authorName;
    }
    
    /**
     * Set the text.
     * @param newText The new text.
     */
    public void setText(String newText){
        this.text = newText;
    }
    
    /**
     * Set the video url.
     * @param newUrl The new video url.
     */
    public void setUrl(String newUrl){
        this.url = newUrl;
    }
    
    /**
     * Set the image url of the video.
     * @param newImageUrl The new image url of the video.
     */
    public void setImageUrl(String newImageUrl){
        this.imageURL = newImageUrl;
    }
    
    /**
     * Set the author name.
     * @param newAuthorName The new author name. 
     */
    public void setAuthorName(String newAuthorName){
        this.authorName = newAuthorName;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nText: %s\nUrl: %s\nImageUrl: %s", authorName, text, url, imageURL);
    }
    
    
    
    
}
