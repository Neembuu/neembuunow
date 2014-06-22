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
            out.println(feedItem);
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
        Connection<Post> filteredFeed = facebookClient.fetchConnection("me/feed", Post.class,
                //Parameter.with("limit", FEED_LIMIT),
                Parameter.with("type", "link"));
        
        out.println("Filtered feed count: " + filteredFeed.getData().size());
        
        List<Post> youtubePost = getFilteredPostList(filteredFeed);
        
        return getFeedItemFromPost(youtubePost);
    }
    
    
    /**
     * Get a list of item of my home.
     * @return Returns a list of item.
     */
    public static List<FeedItem> getMyHomeItemList(){
        Connection<Post> filteredFeed = facebookClient.fetchConnection("me/home", Post.class,
                Parameter.with("limit", FEED_LIMIT),
                Parameter.with("type", "link"));
        
        out.println("Filtered feed count: " + filteredFeed.getData().size());
        
        List<Post> youtubePost = getFilteredPostList(filteredFeed);

        return getFeedItemFromPost(youtubePost);
    }
    
    /**
     * Get a list of Post with a video.
     * @param feedList The feed list.
     * @return Returns a list of Post with a video.
     */
    private static List<Post> getFilteredPostList(Connection<Post> feedList){
        List<Post> youtubePost = new ArrayList<Post>();
        
        String link;
        for (Post postFeed : feedList.getData()) {
            link = postFeed.getLink();
            if(isVideoLink(link)){
                youtubePost.add(postFeed);
            }
            //out.println(postFeed);
        }
        return youtubePost;
    }
    
    
    /**
     * Check if the given link is or not a video link.
     * @param link The link of the post.
     * @return Returns true if the link is a video, false otherwise.
     */
    private static boolean isVideoLink(String link){
        //Get only feed relating to youtube
        return link != null && link.matches("https?://(www.youtube.com/watch\\?v=|youtu.be/)([\\w\\-\\_]*)(&(amp;)?[\\w\\?=]*)?");
    }
    
    
    /**
     * Convert a list of Post to a list of FeedItem. 
     * @param posts The Post list.
     * @return Returns a list of FeedItem.
     */
    private static List<FeedItem> getFeedItemFromPost(List<Post> posts){
        List<FeedItem> feedItems = new ArrayList<FeedItem>();
        FacebookFeedItem feedItem;
        for (Post postFeed : posts) {
            //out.println("Post:" + postFeed);
            //out.println("Link:" + postFeed.getLink());
            
            //Set values
            feedItem = new FacebookFeedItem();
            feedItem.setAuthorName(postFeed.getFrom().getName());
            feedItem.setText(postFeed.getMessage());
            feedItem.setUrl(postFeed.getLink());
            feedItem.setImageUrl(postFeed.getPicture());
            
            feedItems.add(feedItem);
        }
        
        return feedItems;
    }
    
    
}
