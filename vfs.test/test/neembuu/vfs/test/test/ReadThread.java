/*
 * Copyright (C) 2011 Shashank Tulsyan
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
package neembuu.vfs.test.test;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
/**
 *
 * @author Shashank Tulsyan
 */
public final class ReadThread extends Thread {

    private FileChannel fc1;
    private FileChannel fc2;
    private final long readPosition;
    private final int readCapacity;
    private final int readTimes;
    private final boolean verify;
    private final long speed;

    public ReadThread(long readPosition, int readCapacity, int readTimes,boolean verify, long speed) {
        this.readPosition = readPosition;
        this.readCapacity = readCapacity;
        this.readTimes = readTimes;
        this.verify = verify;
        this.speed = speed;
    }
    
    public ReadThread(long readPosition, int readCapacity, int readTimes,boolean verify) {
        this(readPosition,readCapacity,readTimes,verify,0);
    }
    

    @Override
    public void run() {
        try {
            System.out.println("running");
            fc1 = new FileInputStream("j:\\neembuu\\virtual\\monitored.nbvfs\\test120k.rmvb").getChannel();
            fc1.position(readPosition);
            if(verify){
                fc2 = new FileInputStream("j:\\neembuu\\realfiles\\test120k.rmvb").getChannel();
                fc2.position(readPosition);
            }
            for (int i = 1; i != readTimes; i++) {
                ByteBuffer fromVirtual_toCheck = ByteBuffer.allocateDirect(readCapacity);
                fc1.read(fromVirtual_toCheck);
                if(verify){
                    ByteBuffer fromRealFile = ByteBuffer.allocateDirect(readCapacity);
                    fc2.read(fromRealFile);
                    BoundaryConditions.checkBuffers(fromVirtual_toCheck,fromRealFile);
                }
                if(speed>0){
                    System.out.println("sleeping="+(readCapacity/(speed)));
                    Thread.sleep(readCapacity/(speed/*KiloB/sec*/));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}