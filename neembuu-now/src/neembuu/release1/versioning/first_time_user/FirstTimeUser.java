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

package neembuu.release1.versioning.first_time_user;

import neembuu.release1.api.ui.AddLinkUI;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.Message;
import neembuu.release1.settings.OnlineSettingImpl;
import neembuu.release1.settings.SettingsImpl;

/**
 *
 * @author Shashank Tulsyan
 */
public class FirstTimeUser {
    private final MainComponent mc;
    
    private final int maximumNotificationCount = 4;
    
    private final AddLinkUI alui;
    
    private final int timeout = 20000;

    FirstTimeUser(AddLinkUI alui,MainComponent mc) {
        this.alui = alui; this.mc = mc;
    }
    
    public static void handleUser(AddLinkUI alui,MainComponent mc){
        final FirstTimeUser hu =  new FirstTimeUser(alui,mc);
        new Thread(){
            @Override public void run() {
                hu.handle();
            }
        }.start();
    }
    
    public void handle(){
        boolean userSawDemo = SettingsImpl.I().getBoolean("user","saw_demo");
        if(userSawDemo)return;
        long demoNotificationCount = SettingsImpl.I().getLong("user","demo_notification_count");
        if(demoNotificationCount>=maximumNotificationCount)return;
        boolean showUserDemo = askUser();
        demoNotificationCount++;
        SettingsImpl.I().setLong(demoNotificationCount,"user","demo_notification_count");
        if(!showUserDemo)return;
        
        showDemo();
        
        SettingsImpl.I().setBoolean(true,"user","saw_demo");
    }
    
    private boolean askUser(){
        return mc.newMessage()
            .setTitle("It seems you are a new user")
            .setMessage("Would you like to see a \n"
                    + "automatic demo of Neembuu Now ?\n\n"
                    + "This message is shown "+maximumNotificationCount
                                + " times to aid  first time users.\n"
                    + "After that, this message will stop automatically appearing")
            .ask();
    }
    
    private void showDemo(){
        String sampleLink = initSamplelink();
        
        final String stm = "\n\n\nThis demo will automatically proceed in "+(timeout/1000)+ " seconds.\n"
                        + "OR If you are impatient you may click the \"OK\" button to quickly proceed to next step.";
        
        mc.newMessage().setTimeout(timeout).setPreferredLocation(Message.PreferredLocation.Aside)
                .setTitle("How to use NeembuuNow (Automatic demo running 1/3 )")
                .setMessage("Step1) Copy a link of video you want to watch from\n"
                        + "the browser and then click on the blue plus to add links"+stm)
                .show();
        alui.addLinksPanelShow(true);
        mc.newMessage().setTimeout(timeout).setPreferredLocation(Message.PreferredLocation.Aside)
                .setTitle("How to use NeembuuNow (Automatic demo running 2/3 )")
                .setMessage("Step2) NeembuuNow scans the clipboard for links and "
                        + "it may automatically paste the link for you.\n\n"
                        + "An advanced user might like to edit links at this stage "+stm)
                .show();
        if(alui.getLinksText()==null
                || alui.getLinksText().length()==0){
            alui.setLinksText(sampleLink);
        }
        
        mc.newMessage().setTimeout(timeout).setPreferredLocation(Message.PreferredLocation.Aside)
                .setTitle("How to use NeembuuNow (Automatic demo running 3/3 )")
                .setMessage("Step3) Click on \"Add and open these files\" button"+stm)
                .show();
        
        alui.clickAddLinksButton(true);
        
        
        mc.newMessage().setTimeout(timeout).setPreferredLocation(Message.PreferredLocation.Aside)
                .setTitle("How to use NeembuuNow (Automatic demo running)")
                .setMessage("The video will be opened automatically opened\n"
                        + "in VLC media player in a short while"+stm)
                .show();        
    }
    
    private String initSamplelink(){
        String samplelink = OnlineSettingImpl.get("samplelink", "version.xml");
        System.out.println("obtained sample link = "+samplelink);
        if(samplelink!=null){
            SettingsImpl.I().set(samplelink,"samplelink");
        }
        samplelink = SettingsImpl.I().get("samplelink");
        return samplelink;
    }
}
