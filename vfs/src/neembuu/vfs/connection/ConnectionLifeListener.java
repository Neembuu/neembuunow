package neembuu.vfs.connection;

/**
 *
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
public interface ConnectionLifeListener {
    void created(ConnectionInterfaceForUI cifui);
    void died(ConnectionInterfaceForUI cifui);
}
