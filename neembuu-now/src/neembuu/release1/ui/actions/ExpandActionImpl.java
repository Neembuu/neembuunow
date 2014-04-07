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

package neembuu.release1.ui.actions;

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
    
    public enum Mode {
        SingleLinkType,
        MultiVariantType,
        SplitLinkType
    }
    
    private final Mode mode;
    
    public ExpandActionImpl(ExpandActionUIA ui, Mode mode) {
        this.ui = ui;
        this.mode = mode;
    }

    @Override
    public void actionPerformed() {
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
        ui.setVisibleVariantProgress(false);
        ui.setVisibleProgress(false);
        
        ui.graphPanel().setVisible(false);{
            ui.initGraph(false);
        }
        ui.hiddenStatsPane().setVisible(false);
        ui.connectionControlPane().setVisible(false);
        
        ui.getHeight().setValue(ui.ht_smallest());
        
        state = 1;
    }
    
    private void setToSemiExpanded(){
        showAppropriateProgressRegions();

        ui.graphPanel().setVisible(false);{
            ui.initGraph(false);
        }
        ui.hiddenStatsPane().setVisible(false);
        ui.connectionControlPane().setVisible(false);
        
        ui.getHeight().setValue(ui.ht_medium());
        
        state = 2;
    }
    
    private void setToFullyExpanded(){
        showAppropriateProgressRegions();
        
        ui.initGraph(true);
        ui.graphPanel().setVisible(true);
        ui.hiddenStatsPane().setVisible(true);
        ui.connectionControlPane().setVisible(true);
        
        ui.getHeight().setValue(ui.ht_tallest());
                
        state = 3;
    }
    
    private void showAppropriateProgressRegions(){
        switch (mode) {
            case SingleLinkType:
                ui.setVisibleVariantProgress(false);
                ui.setVisibleProgress(true);
                break;
            case SplitLinkType:
                ui.setVisibleVariantProgress(true);
                ui.setVisibleProgress(true);
                ui.initVariants();
                break;
            case MultiVariantType:
                ui.setVisibleVariantProgress(true);
                ui.setVisibleProgress(false);
                ui.initVariants();
                break;
            default: throw new AssertionError();
        }
    }
    
}
