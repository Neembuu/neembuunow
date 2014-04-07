/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api.linkgroup;

import java.util.List;
import neembuu.diskmanager.Session;
import neembuu.release1.api.linkhandler.TrialLinkHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public interface LinkGroup {
    List<TrialLinkHandler> getAbsorbedLinks();
    String displayName();
    Session getSession();
}
