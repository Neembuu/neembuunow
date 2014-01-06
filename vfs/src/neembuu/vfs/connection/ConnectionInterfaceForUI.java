package neembuu.vfs.connection;

/**
 *
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
public interface ConnectionInterfaceForUI 
        /*extends ProgressProvider, DownloadSpeedProvider, RequestSpeedProvider*/{
    
    boolean isAlive();
    
    long timeTakenForCreation();
    
    long getCreationTime();
    
}
