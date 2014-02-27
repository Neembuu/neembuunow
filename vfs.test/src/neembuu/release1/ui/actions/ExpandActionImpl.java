/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui.actions;

import java.awt.event.ActionEvent;
import neembuu.release1.api.ui.ExpansionState;
import neembuu.release1.api.ui.access.ExpandActionUIA;
import neembuu.release1.api.ui.actions.ExpandAction;

/**
 *
 * @author Shashank Tulsyan
 */
public class ExpandActionImpl implements ExpandAction{
    private int state = 1;    
    private final ExpandActionUIA ui;

    public ExpandActionImpl(ExpandActionUIA ui) {
        this.ui = ui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        expandContractPressed();
    }
   
    @Override
    public ExpansionState getExpansionState(){
        int s = this.state%3;
        switch (s) {
            case 0:
                return ExpansionState.FullyExpanded;
            case 1:
                return ExpansionState.Contracted;
            case 2:
                return ExpansionState.SemiExpanded;
            default:
                return ExpansionState.Contracted;
        }
    }
    
    @Override
    public void setExpansionState(ExpansionState es){
        switch (es) {
            case FullyExpanded: setToFullyExpanded(); break;
            case Contracted: setToContracted(); break;
            case SemiExpanded: setToSemiExpanded(); break;
            default:
                throw new AssertionError();
        }
    }
    
    private void expandContractPressed(){        
        if(state%3==0){
            setToContracted();
        }else if(state%3==1){
            setToSemiExpanded();
        }else {
            setToFullyExpanded();
        }
    }
    
    private void setToContracted(){
        ui.sizeAndProgressPane().setVisible(false);
        ui.graphPanel().setVisible(false);{
            ui.initGraph(false);
        }
        ui.hiddenStatsPane().setVisible(false);
        ui.connectionControlPane().setVisible(false);
        
        ui.getHeight().setValue(ui.ht_smallest());
        
        state = 1;
    }
    
    private void setToSemiExpanded(){
        ui.sizeAndProgressPane().setVisible(true);
        ui.graphPanel().setVisible(false);{
            ui.initGraph(false);
        }
        ui.hiddenStatsPane().setVisible(false);
        ui.connectionControlPane().setVisible(false);
        
        ui.getHeight().setValue(ui.ht_medium());
        
        state = 2;
    }
    
    private void setToFullyExpanded(){
        ui.sizeAndProgressPane().setVisible(true);
        ui.initGraph(true);
        ui.graphPanel().setVisible(true);
        ui.hiddenStatsPane().setVisible(true);
        ui.connectionControlPane().setVisible(true);
        
        ui.getHeight().setValue(ui.ht_tallest());
        
        state = 3;
    }
}
