package neembuu.vfs.progresscontrol;

import java.util.EventListener;

/**
 *
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
public interface DownloadSpeedProvider extends EventListener{
    /**
     * Usage<br/>
     * 1) GUI
     * 2) calculating estimated time for download to reach a certain point
     * @return speed in Kilo Bytes per second
     */
    double getDownloadSpeed_KiBps(); 
}
