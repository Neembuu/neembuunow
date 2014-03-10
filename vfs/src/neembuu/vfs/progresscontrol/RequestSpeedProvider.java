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
package neembuu.vfs.progresscontrol;

import java.util.EventListener;

/**
 *
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
public interface RequestSpeedProvider extends EventListener{
        /**
     * This is used for <ol>
     * <li>Calculating how much throttling must be done.</li>
     * <li>GUI </li></ol>
     * Just like {@link #getDownloadSpeed() } this must be an "instantaneous average" value,
     * but with a smaller time period than download speed as this is more fluctuating parameter
     * than download speed.
     * <br/><br/>
     * <u>Very important</u><br/>
     * Unlike download speed, request speed cannot be accurately known.
     * There are cases when it is lesser, equal to or greater than actual request speed.
     * Actual request speed is, the instantaneous bitrate of the video being watched.
     * All high quality low size and skillfully encoded video files will invariably have variable bitrate.
     * This means that data required for storing a given length of video varies according to video complexity
     * throughout the file.<br/>
     * Observed request speed is greater than actual request speed, when the the video player requests
     * a large amount of data (even though it not actually required : examples of media players following
     * this behavior : windows media player ) and would not request data for a long time and then suddenly
     * request data again.<br/>
     * Observed request speed is lesser than actual request speed, when the download speed is lesser than
     * the required speed, and thus the video player has to buffer. This was a request speed very marginally
     * greater than download speed is observed. No matter however small the difference is, if request speed
     * is greater than download speed, and {@link #requestDownloadGap() } is zero, it means that user will
     * have a poor "watch as you download" experience. The real value of this can be obtained from
     * the media player. This is only possible if neembuu vlcj media player is used.
     * The interfaces for this will soon be provided.
     * @return the request speed in kilobytes per second according to some appropriate model 
     * (example-instantaneous average, exponential smoothing)
     */
    double getRequestSpeed_KiBps(); 
}
