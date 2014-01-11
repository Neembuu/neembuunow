/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.defaultImpl;

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
        String url = "http://www.youtube.com/watch?v=N0fPuYR3I_k"; //Normal
        String url1 = "http://youtu.be/N0fPuYR3I_k"; //Minified
        String url2 = "http://www.youtube.com/watch?v=iwGFalTRHDA&feature=related"; //With other parameters
        String url3 = "http://youtu.be/t-ZRX8984sc";
        String url4 = "https://www.youtube.com/watch?v=N0fPuYR3I_k"; //Https
        
        String invalidUrl = "http://www.youtuabe.com/watch?v=N0fPuYR3I_k";
        String invalidUrl1 = "http://www.youtube.com/watch?v=N0fPuYR3I_k&&asds";
        String invalidUrl2 = "http://www.youtube.it/watch?v=N0fPuYR3I_k";
        
        YoutubeLinkHandlerProvider instance = new YoutubeLinkHandlerProvider();
        
        //Valid
        assertTrue(instance.canHandle(url));
        assertTrue(instance.canHandle(url1));
        assertTrue(instance.canHandle(url2));
        assertTrue(instance.canHandle(url3));
        assertTrue(instance.canHandle(url4));
        
        
        //Invalid
        assertFalse(instance.canHandle(invalidUrl));
        assertFalse(instance.canHandle(invalidUrl1));
        assertFalse(instance.canHandle(invalidUrl2));
    }

    /**
     * Test of getLinkHandler method, of class YoutubeLinkHandlerProvider.
     */
    @Test
    public void testGetLinkHandler() {
        System.out.println("getLinkHandler");
        String url = "";
        YoutubeLinkHandlerProvider instance = new YoutubeLinkHandlerProvider();
        LinkHandler expResult = null;
        LinkHandler result = instance.getLinkHandler(url);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
