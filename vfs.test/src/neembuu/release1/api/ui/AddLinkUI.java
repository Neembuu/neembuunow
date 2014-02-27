/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api.ui;

/**
 *
 * @author Shashank Tulsyan
 */
public interface AddLinkUI {
    void addLinkProgressSet(String a);
    void addLinksPanelShow(boolean show);
    void addLinksPanelEnable(boolean enable);
    String getLinksText();
    void setLinksText(String a);
}
