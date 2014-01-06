/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.vfs.progresscontrol;

import neembuu.vfs.connection.NewConnectionParams;

/**
 *
 * @author Shashank Tulsyan
 */
public interface ThrottleFactory<T extends Throttle> {
    T createNewThrottle();
    
    public final static class General implements ThrottleFactory {

        public static final General SINGLETON = new General();

        private General() {
        }
        @Override
        public Throttle createNewThrottle() {
            GeneralThrottle gt = new GeneralThrottle();
            return gt;
        }
    }
}
