/*
 *  Copyright (C) 2014 Shashank Tulsyan
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.release1.defaultImpl;

import java.util.logging.Level;
import neembuu.release1.Main;
import neembuu.release1.api.linkhandler.LinkHandler;
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
            assertTrue(instance.tryHandling(url).canHandle());
        }
        
        //Invalid
        for (String url : invalidUrls) {
            assertFalse(instance.tryHandling(url).canHandle());
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
