package neembuu.release1.facebook;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Post;
import com.restfb.types.User;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;

/**
 * Handle Facebook behavior.
 * @author davidepastore
 */
public class Facebook {
    
    /**
     * Get your token from here: https://developers.facebook.com/tools/explorer
     */
    private static final String MY_ACCESS_TOKEN = "INSERT YOUR TOKEN HERE";
    
    private static final FacebookClient facebookClient = new DefaultFacebookClient(MY_ACCESS_TOKEN);
    
    
    /**
     * The feed limit.
     */
    private static final int FEED_LIMIT = 100;
    
    /**
     * Non-instantiable.
     */
    private Facebook(){
        
    }
    
    /**
     * Experiments with Facebook REST API services.
     * @param args 
     */
    public static void main(String[] args) {
        out.println("User name: " + getUser().getName());
        List<FeedItem> feedItems = getMyHomeItemList();
        
        for (FeedItem feedItem : feedItems) {
            System.out.println(feedItem);
        }
    }
    
    /**
     * Get user info.
     * @return Returns info about the user.
     */
    public static String getUserInfo(){
        return getUser().toString();
    }
    
    
    /**
     * Get a User instance.
     * @return Returns the user.
     */
    public static User getUser(){
        return facebookClient.fetchObject("me", User.class);
    }
    
    
    /**
     * Get a list of item of my wall.
     * @return Returns a list of item.
     */
    public static List<FeedItem> getMyWallItemList(){
        List<FeedItem> feedItems = new ArrayList<FeedItem>();

        Connection<Post> filteredFeed = facebookClient.fetchConnection("me/feed", Post.class,
                //Parameter.with("limit", FEED_LIMIT),
                Parameter.with("type", "link"));
        
        out.println("Filtered feed count: " + filteredFeed.getData().size());
        
        List<Post> youtubePost = new ArrayList<Post>();
        
        //Get only feed relating to youtube
        String link;
        for (Post postFeed : filteredFeed.getData()) {
            link = postFeed.getLink();
            if(link != null && link.matches("https?://(www.youtube.com/watch\\?v=|youtu.be/)([\\w\\-\\_]*)(&(amp;)?[\\w\\?=]*)?")){
                youtubePost.add(postFeed);
            }
        }

        //Populates with youtube video
        FacebookFeedItem feedItem;
        for (Post postFeed : youtubePost) {
            //out.println("Post:" + postFeed);
            //out.println("Link:" + postFeed.getLink());
            
            //Set values
            feedItem = new FacebookFeedItem();
            feedItem.setText(postFeed.getMessage());
            feedItem.setUrl(postFeed.getLink());
            feedItem.setImageUrl(postFeed.getPicture());
            
            feedItems.add(feedItem);
        }
        
        return feedItems;
    }
    
    
    /**
     * Get a list of item of my home.
     * @return Returns a list of item.
     */
    public static List<FeedItem> getMyHomeItemList(){
        List<FeedItem> feedItems = new ArrayList<FeedItem>();

        Connection<Post> filteredFeed = facebookClient.fetchConnection("me/home", Post.class,
                Parameter.with("limit", FEED_LIMIT),
                Parameter.with("type", "link"));
        
        out.println("Filtered feed count: " + filteredFeed.getData().size());
        
        List<Post> youtubePost = new ArrayList<Post>();
        
        //Get only feed relating to youtube
        String link;
        for (Post postFeed : filteredFeed.getData()) {
            link = postFeed.getLink();
            if(link != null && link.matches("https?://(www.youtube.com/watch\\?v=|youtu.be/)([\\w\\-\\_]*)(&(amp;)?[\\w\\?=]*)?")){
                youtubePost.add(postFeed);
            }
        }

        //Populates with youtube video
        FacebookFeedItem feedItem;
        for (Post postFeed : youtubePost) {
            //out.println("Post:" + postFeed);
            //out.println("Link:" + postFeed.getLink());
            
            //Set values
            feedItem = new FacebookFeedItem();
            feedItem.setText(postFeed.getMessage());
            feedItem.setUrl(postFeed.getLink());
            feedItem.setImageUrl(postFeed.getPicture());
            
            //Add to the list
            feedItems.add(feedItem);
        }
        
        return feedItems;
    }
    
    
}
