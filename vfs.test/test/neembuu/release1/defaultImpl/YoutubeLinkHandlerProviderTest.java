/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.defaultImpl;

import java.util.logging.Level;
import neembuu.release1.Main;
import neembuu.release1.api.LinkHandler;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author davidepastore
 */
public class YoutubeLinkHandlerProviderTest {
    
    public YoutubeLinkHandlerProviderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of canHandle method, of class YoutubeLinkHandlerProvider.
     */
    @Test
    public void testCanHandle() {
        System.out.println("canHandle");
        
        //Valid
        String urls[] = {
            "http://www.youtube.com/watch?v=N0fPuYR3I_k", //Normal
            "http://youtu.be/N0fPuYR3I_k", //Minified
            "http://www.youtube.com/watch?v=iwGFalTRHDA&feature=related", //With other parameters
            "http://youtu.be/t-ZRX8984sc", //With a special char: "-"
            "https://www.youtube.com/watch?v=N0fPuYR3I_k" //Https
        }; 
        
        
        //Invalid
        String invalidUrls[] = {
            "http://www.youtuabe.com/watch?v=N0fPuYR3I_k",
            "http://www.youtube.com/watch?v=N0fPuYR3I_k&&asds",
            "http://www.youtube.it/watch?v=N0fPuYR3I_k"
        };
        
        YoutubeLinkHandlerProvider instance = new YoutubeLinkHandlerProvider();
        
        //Valid
        for (String url : urls) {
            assertTrue(instance.tryHandling(url));
        }
        
        //Invalid
        for (String url : invalidUrls) {
            assertFalse(instance.tryHandling(url));
        }
    }

    /**
     * Test of getLinkHandler method, of class YoutubeLinkHandlerProvider.
     */
    @Test
    public void testGetLinkHandler() {
        System.out.println("getLinkHandler");
        
        String urls[] = {
            "http://www.youtube.com/watch?v=N0fPuYR3I_k", //Normal
            "http://youtu.be/N0fPuYR3I_k", //Minified
            "http://www.youtube.com/watch?v=iwGFalTRHDA&feature=related", //With other parameters
            "http://youtu.be/t-ZRX8984sc", //With a special char: "-"
            "https://www.youtube.com/watch?v=N0fPuYR3I_k" //Https
        }; 
        
        YoutubeLinkHandlerProvider fnasp = new YoutubeLinkHandlerProvider();
        
        for (String singleUrl : urls) {
            LinkHandler fnas = fnasp.getLinkHandler(singleUrl);
        
//            Main.getLOGGER().log(Level.INFO, "Added={0} {1} l={2}", new Object[]{fnas.getGroupName(), fnas.getGroupSize(), singleUrl});

            assertTrue(singleUrl + " foundSize() = " + fnas.foundSize(), fnas.foundSize());
        }
        
    }
    
}
