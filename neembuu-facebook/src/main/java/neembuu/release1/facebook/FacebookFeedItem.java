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

    public String text() {
        return text;
    }

    public String url() {
        return url;
    }

    public String imageURL() {
        return imageURL;
    }
    
    public void setText(String newText){
        this.text = newText;
    }
    
    public void setUrl(String newUrl){
        this.url = newUrl;
    }
    
    public void setImageUrl(String newImageUrl){
        this.imageURL = newImageUrl;
    }

    @Override
    public String toString() {
        return String.format("Text: %s\nUrl: %s\nImageUrl: %s", text, url, imageURL);
    }
    
    
    
    
}
