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
    
    public String text();
    
    public String url();
    
    public String imageURL(); // or image object
}
