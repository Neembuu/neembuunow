/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.session;

import neembuu.diskmanager.impl.DefaultNomenclature;
import neembuu.diskmanager.DiskManager;
import neembuu.diskmanager.DiskManagerParams;
import neembuu.diskmanager.DiskManagers;

/**
 *
 * @author Shashank Tulsyan
 */
public class TestResumeSession {

    DiskManager dm;
    public TestResumeSession() {
        DiskManagerParams dmp = DiskManagerParams.Builder.create()
                .setBaseStoragePath("C:\\Users\\SHASHA~1\\AppData\\Local\\Temp\\\\neembuu-release1")
                .setNomenclature(new DefaultNomenclature())
                .build();
        dm = DiskManagers.getDefaultManager(dmp);
    }
    
    
    
    public static void main(String[] args) {
        
        
    }
}
