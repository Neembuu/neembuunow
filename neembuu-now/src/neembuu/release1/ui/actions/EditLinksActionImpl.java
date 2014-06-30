/*
 * Copyright (C) 2014 Shashank Tulsyan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.release1.ui.actions;

import java.awt.Dialog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import neembuu.release1.api.linkgroup.LinkGroup;
import neembuu.release1.api.linkgroup.LinkGroupMakers;
import neembuu.release1.api.linkgroup.LinkGrouperResults;
import neembuu.release1.api.linkgroup.TrialLinkGroup;
import neembuu.release1.api.linkhandler.LinkHandlerProviders;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.ui.LinkGroupUICreator;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.access.RemoveFromUI;
import neembuu.release1.api.ui.actions.EditLinksAction;
import neembuu.release1.defaultImpl.linkgroup.LinkGrouperImpl;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.release1.api.ui.Message;
import neembuu.release1.ui.actions.EditLinksActionImpl.Actions;
import neembuu.release1.ui.linkpanel.EditLinksPanel;
import neembuu.util.Throwables;

/**
 *
 * @author Shashank Tulsyan
 */
public class EditLinksActionImpl implements EditLinksAction{
    
    private final LinkGroupUICreator linkGroupUICreator;
    private final LinkGroup linkGroup;
    private final MainComponent mainComponent;
    private final RemoveFromUI removeFromUI;
    
    private final static Logger l = LoggerUtil.getLogger(EditLinksActionImpl.class.getName());

    public EditLinksActionImpl(LinkGroupUICreator linkGroupUICreator, LinkGroup linkGroup, MainComponent mainComponent, RemoveFromUI removeFromUI) {
        this.linkGroupUICreator = linkGroupUICreator;
        this.linkGroup = linkGroup;
        this.mainComponent = mainComponent;
        this.removeFromUI = removeFromUI;
    }

    @Override public void actionPerformed() {
        Object[]options = {"Yes","No"};
        Object response = mainComponent.newMessage().setTitle("Are you sure?")
                .setEmotion(Message.Emotion.EXPERT)
                .setTimeout(10000)
                .setMessage("Editing links is an advanced option.\n"
                        + "Please proceed only if you know what you are doing.")
                .ask(options,1);
        if(response!=options[0])return;
        
        String links = constructLinkParagraph();
        
        JDialog jd = new JDialog(mainComponent.getJFrame(),"Edit links",Dialog.ModalityType.APPLICATION_MODAL);
        
        EditLinksPanel elp = new EditLinksPanel();
        elp.initAction(new ActionsImpl(elp,jd));
        elp.ui().textArea().setText(links);
        
        jd.setSize(600,250);
        jd.getContentPane().add(elp);
        jd.show();
    }
    
    private String constructLinkParagraph(){
        List<TrialLinkHandler> tlhs =  linkGroup.getAbsorbedLinks();
        StringBuilder sb = new StringBuilder();
        int i =0;
        for (TrialLinkHandler tlh : tlhs) {
            if(i>0)sb.append('\n'); i++;
            sb.append(tlh.getReferenceLinkString());
            
        }
        return sb.toString();
    }
    
    public void saveImpl(EditLinksPanel elp){
        Object[]options = {"Yes","No"};
        Object response = mainComponent.newMessage().setTitle("Are you sure?")
                .setMessage("To change links this file will be \n"
                        + "closed and opened again.\n"
                        + "If there is any issue in the \n"
                        + "process the buffered data will be deleted.")
                .setTimeout(10000)
                .ask(options,1);
        if(response!=options[0])return;
        
        String[]links = elp.ui().textArea().getText().split("\n");
        
        List<TrialLinkHandler> tlhs = new ArrayList<>();
        
        for (String link : links) {
            TrialLinkHandler tlh = LinkHandlerProviders.getWhichCanHandleOrDefault(link);
            tlhs.add(tlh);
        }
        LinkGrouperImpl grouperImpl = new LinkGrouperImpl();
        LinkGrouperResults lgr = grouperImpl.group(tlhs);
        
        if(!lgr.incomplete_linkPackages().isEmpty()){
            l.severe("++Some links could not be grouped.++");
            for (TrialLinkGroup tlg : lgr.incomplete_linkPackages()) {
                l.severe(tlg.toString());
            }
            l.severe("------------------------------------");
            failed("Some links could not be grouped.");return;
        }
        
        if(lgr.complete_linkPackages().size()> 1){
            failed("Links cannot be grouped. Files are not mergable.");return;
        }
        
        TrialLinkGroup tlg = lgr.complete_linkPackages().get(0);

        try{
            LinkGroup newLinkGroup = LinkGroupMakers.modify(linkGroup.getSession(), tlg);
            removeFromUI.remove();
            linkGroupUICreator.createUIFor(Collections.singletonList(newLinkGroup),false);
        }catch(Exception a){
            l.log(Level.SEVERE,"Editing links failed",a);
            failed(a.getMessage());
        }
        
    }
    
    private void failed(String reason){
        mainComponent.newMessage().error()
                .setTitle("Editing links failed")
                .setMessage(reason).show();
    }
    
    private void emptyCacheImpl(){
        Object[]options = {"Yes","No"};
        Object selected = mainComponent.newMessage().error()
                .setEmotion(Message.Emotion.EXPERT)
                .setTitle("Are you sure?")
                .setTimeout(10000)
                .setMessage("This will delete all downloaded data.\n"
                        + "This option is useful if previously corrupted\n"
                        + "data was downloadeded, and needs to be cleaned.")
                .ask(options,1);
        if(selected!=options[0])return;
        Throwables.start(new Runnable() {
            @Override public void run() {
                try{linkGroup.getSession().clearCachedFileData();}
                catch(Exception a){throw new RuntimeException(a);}
            }
        }, "Clearing cache",true);
        
    }
    
    final class ActionsImpl implements Actions {
        private final EditLinksPanel elp;private final JDialog jd;
        ActionsImpl(EditLinksPanel elp, JDialog jd) {this.elp = elp;this.jd= jd;}
        @Override public void save() { saveImpl(elp); cancel(); }
        @Override public void cancel() { jd.setVisible(false); jd.dispose(); }
        @Override public void emptyCache(){ emptyCacheImpl(); }
    }
    
    public static interface Actions {
        void save(); void cancel(); void emptyCache();
    }
}
