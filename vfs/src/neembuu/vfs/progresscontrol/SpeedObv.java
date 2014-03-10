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

/**
 *
 * @author Shashank Tulsyan
 */
final class SpeedObv {
    private volatile long offset = 0;
    private volatile long initialOffset = 0;
    private volatile long initialTime = 0;
    
    // non volatile because we assume that progressed is called from
    // one thread only
    private int i = 0;
    private final int l;
    private final long[]x;
    private final long[]t;
    private int to_skip = 0;
    private int skipped = 0;
    private int skip_change_wait_counter ;
    
    private volatile double speed_Bps = 0;
    
    private final int minimum_cycle_interval = 3000;
    private final int maximum_cycle_interval = 9000;
    
    public SpeedObv() {this(100,0);}
    
    public SpeedObv(int l, long offset) {
        this.l = l; 
        x = new long[l];
        t = new long[l];
        reset(0);
        skip_change_wait_counter = l;
    }

    public void reset(){
        reset(offset);
    }
    
    public void reset(long offset){
        this.offset = offset; this.initialOffset = offset;
        initialTime = System.currentTimeMillis();
    }
    
    public final int getL() {
        return l;
    }

    public final double getSpeed_Bps() {
        return speed_Bps;
    }
    
    public final double getSpeed_KiBps() {
        return speed_Bps/1024d;
    }
    
    public final double getSpeed_MiBps() {
        return speed_Bps/(1024d*1024d);
    }
    
    public void setThisAsInitialPointForAverage() {
        initialOffset = offset;
        initialTime = System.currentTimeMillis();
    }
    
    public double getAverage_Bps(){
        long a_dt = System.currentTimeMillis()-initialTime;
        long a_dx = ((offset - initialOffset)*1000);
        if(a_dt<0)
            System.out.println("dt<0");
        if(a_dx<0)
            System.out.println("dx<0");
        
        if(a_dt!=0)
            return
                    a_dx
                    /
                    a_dt;
        return 0;
    }
    
    public double getAverage_KiBps(){
        return getAverage_Bps()/1024;
    }
    
    public double getAverage_MiBps(){
        return getAverage_Bps()/(1024*1024);
    }
    
    public void progressed(int dx){
        offset += dx;
        
        if(to_skip!=0){
            if(skipped%to_skip!=0){skipped++;return;}
        }
        skipped++;
        
        x[i%l] = offset;
        t[i%l] = System.currentTimeMillis();
        
        double downloadSpeedEntry = 1;
        if(i<1){
            downloadSpeedEntry = 1;
        }
        else if(i < l){
            long dt = t[i]-t[0];
            if(dt==0)dt=1;
            downloadSpeedEntry = 
                    (x[i] - x[0])
                    /
                    dt
                    ;
        }else {
            long dt = (t[i%l] - t[(i+1)%l]);
            downloadSpeedEntry = 
                    (x[i%l] - x[(i+1)%l])
                    /
                    dt
                    ;
            if(skip_change_wait_counter>0)skip_change_wait_counter--;
            
            if(dt < minimum_cycle_interval/*ms*/){
                
                if(skip_change_wait_counter==0){
                    if(to_skip==0)to_skip=1;
                    else to_skip*=2;
                    //System.out.println("skip incr to = "+to_skip);

                    skip_change_wait_counter = l;
                }
            }else if(dt > maximum_cycle_interval/*ms*/){
                if(skip_change_wait_counter==0){
                    if(to_skip!=0){
                        if(to_skip==1)to_skip=0;
                        else to_skip/=2;
                      //  System.out.println("skip dec to = "+to_skip);

                        skip_change_wait_counter = l;
                    }
                }
            }
        }
        
        speed_Bps = downloadSpeedEntry*1000;
        i++;
    }

}
