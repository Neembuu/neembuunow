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

package neembuu;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import neembuu.util.MeasureCodeExecutionTime;

/**
 *
 * @author Shashank Tulsyan
 */
public class TestReadSpeed {
    public static void main(String[] args) throws IOException{
        Path baseDir = Paths.get("C:\\Users\\Shashank Tulsyan\\.neembuu-now\\release1\\NeembuuVirtualFolder\\");
        FileChannel fc
                = FileChannel.open(baseDir.resolve("video-389803-h264_high.mp4"),StandardOpenOption.READ);
        
        ByteBuffer bb = ByteBuffer.allocateDirect(10);
        MeasureCodeExecutionTime.setEnabled(true);
        MeasureCodeExecutionTime.startNew();
        int step = 0;
        for (int i = 0; i < 1024*1024; i++) {
            fc.read(bb);
            bb.clear();
            int newstep = i/1024;
            if(newstep> step){
                MeasureCodeExecutionTime.stopAndStartNew("step"+step);
                MeasureCodeExecutionTime.printAllObservations();
                step = newstep;
                System.out.println("step no="+step);
            }
        }
        MeasureCodeExecutionTime.stop("Reading time");
        MeasureCodeExecutionTime.printAllObservations();
        fc.close();
    }
}
