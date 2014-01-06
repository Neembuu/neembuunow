/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1;

import java.io.File;

/**
 *
 * @author Shashank Tulsyan
 */
public class Application {
    public static File getResource(final String relative) {
        return new File(Application.getHome(), relative);
    }
    
    public static String getHome() {
        return Environment.getRoot().toString();
        //return Application.getRoot(Application.class);
    }
}
