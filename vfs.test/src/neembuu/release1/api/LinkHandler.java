/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api;

import java.util.List;

/**
 *
 * @author Shashank Tulsyan
 */
public interface LinkHandler {
    List<File> getFiles();
    String getGroupName();
    long getGroupSize();
    boolean foundName();
    boolean foundSize();
}
