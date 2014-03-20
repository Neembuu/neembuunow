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

package neembuu.release1.ui;

import neembuu.release1.ui.linkpanel.FakeLinkHandlerProvider;
import javax.swing.JFrame;
import neembuu.release1.api.linkhandler.LinkHandlerProviders;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.ui.linkcontainer.LinksContainer;
import neembuu.release1.ui.linkpanel.TestGenericLinkPanel;

/**
 *
 * @author Shashank Tulsyan
 */
public class TestLinkPanel {
    LinksContainer lc;
    final MainComponent mainComponent = new MainComponent() {
            @Override public JFrame getJFrame() { return frame; }
        };
    
    public TestLinkPanel() {
        MainPanel mp = new MainPanel(null,mainComponent, null);

        lc = new LinksContainer(mp, mp.getLinksPanel());
        
        frame.getContentPane().add(mp);
        
        TestGenericLinkPanel genericLinkPanel
                = new TestGenericLinkPanel(mp,lc,mainComponent);
        
        lc.addUI(genericLinkPanel.singleLink(), 0);
        //lc.addUI(genericLinkPanel.multiLink(), 0);
        lc.addUI(genericLinkPanel.multiVariantTypeLink(),0);
    }
    
    public static void main(String[] args) {        
        InitLookAndFeel.init();
        
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        
        LinkHandlerProviders.registerDefaultProvider(new FakeLinkHandlerProvider());
        TestLinkPanel linkPanel = new TestLinkPanel();
        frame.setVisible(true);
    }
    
    static JFrame frame;
}
