/*
 *  Copyright (C) 2009-2010 Shashank Tulsyan
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
 *
 *
 *
 *  Linking this library statically or
 *  dynamically with other modules is making a combined work based on this library.
 *  Thus, the terms and conditions of the GNU General Public License cover the whole combination.
 *
 *
 *  As a special exception, the copyright holders of this library give you permission to
 *  link this library with independent modules to produce an executable, regardless of
 *  the license terms of these independent modules, and to copy and
 *  distribute the resulting executable under terms of your choice,
 *  provided that you also meet, for each linked independent module,
 *  the terms and conditions of the license of that module.
 *  An independent module is a module which is not derived from or based on this library.
 *  If you modify this library, you may extend this exception to your version of the library,
 *  but you are not obligated to do so. If you do not wish to do so,
 *  delete this exception statement from your version.
 *
 * File : DynamicCPSPauser.java
 */


package neembuu.vfs.progresscontrol;

/**
 *
 * Generate appropriate pauses for a given CPS (characters per second)
 * @author Shashank Tulsyan
 */
class DynamicCPSPauser{ //package private
    // Conversions for milli and nano seconds
    private static final int MS_PER_SEC = 1000;
    private static final int NS_PER_SEC = 1000000000;
    private static final int NS_PER_MS  = NS_PER_SEC/MS_PER_SEC;

    public static final int INVALID_CPS = -1;

    public static void pauseInverse(
            final Object sleepOver,
            double throttleReciprocal, 
            int bytes){
        if(throttleReciprocal<=0)return;
        if(Double.isInfinite(throttleReciprocal))throw new IllegalArgumentException("Specifying infinite throttling" );
        long sleepMS = (long)((bytes*MS_PER_SEC)*throttleReciprocal);
        int  sleepNS = (int) ((bytes*MS_PER_SEC)*throttleReciprocal) % NS_PER_MS;
        if(sleepMS>3000){
            System.err.println("Throttle values exceeds timeout, better to terminate connection, than to leave a deadlock");
            throw new IllegalStateException("Throttle values exceeds timeout, better to terminate connection, than to leave a deadlock" );
        }
        try {
            synchronized (sleepOver){sleepOver.wait(sleepMS,sleepNS);}
            //steppedSleep(sleepMS, sleepNS, 200);
        } catch (InterruptedException ignored) {
            ignored.printStackTrace(System.err);
        }
    }
}
