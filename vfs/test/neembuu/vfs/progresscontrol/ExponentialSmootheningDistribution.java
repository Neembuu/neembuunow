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

import net.jcip.annotations.NotThreadSafe;

/**
 *
 * @author Shashank Tulsyan
 */
@NotThreadSafe
public final class ExponentialSmootheningDistribution {

    private final double[][] s;
    private final double[] wgt_exp;
    //private final double[] wgt_t;
    private final double a;//alpha

    public ExponentialSmootheningDistribution(int n) {
        this(n, 0.4d);
    }

    public ExponentialSmootheningDistribution(int n, double alpha) {
        s = new double[n][1];
        // 0 speed entry, 1 time entry
        wgt_exp = new double[n];
        //wgt_t   = new double[n];
        a = alpha;//2.0/(n+1.0);
    }

    public void makeEntry(int entryNumber, double speedValue, double timeInstance) {
        s[entryNumber % s.length][0] = speedValue;
        //s[entryNumber % s.length][1] = timeInstance;
    }

    public int getSize() {
        return s.length;
    }

    private void assignExponentialWeights(int n_local) {
        final double f = 1.0 / (1.0 - Math.pow(1.0 - a, n_local));
        wgt_exp[0] = a * f;

        for (int i = 1; i < n_local; i++) {
            wgt_exp[i] = a * Math.pow(1 - a, i) * f;
        }
    }
    
    public double estimate(long totalCnt) {
        double answer = 0;

        if (totalCnt == 0) {
            return Double.NaN;
        }else if (totalCnt == 1) {
            return s[0][0];
        }else if (totalCnt == 1) {
            return (s[0][0]+s[1][0])/2;
        }

        // reset
        
        int iteration_count = s.length;

        if (totalCnt <= s.length) {
            iteration_count = (int)totalCnt;
            assignExponentialWeights(iteration_count); // after this 
        }
         
        for (int i = 0; i < iteration_count; i++) {
            
            answer += s[(int)(totalCnt - i - 1) % s.length][0]  // speed term
                * wgt_exp[i]  // exponential smootheing weigtage (Sum(wi)=1 , normalized)
                    ;
        }        
        return answer;
    }
    
    /*private void assignTimeWeights(long totalCnt, int iteration_count){
        
        double totalTime = 0;
        int s_index = 0,s_1_index = 0;
        
        for (int i = 0; i < iteration_count; i++) {
            
            s_index = (int)((totalCnt - i - 1)%s.length);
            s_1_index = (int)((totalCnt - i - 1)%s.length);
            
            // assuming uniform request rate
            wgt_t[i] = s[s_index][1] - s[s_1_index][1];
            
            totalTime+=wgt_t[i];
            
        }
        
        for (int i = 0; i < iteration_count; i++) {
            wgt_t[i]/=totalTime;
            
            System.out.println("time weight["+i+"'="+wgt_t[i]);
        }
        
        
    }*/
    
    public static void main(String[] args) throws java.io.IOException{
        ExponentialSmootheningDistribution f = new ExponentialSmootheningDistribution(5);
        for (int i = 0; i < 100; i++) {
            System.out.println("Forcast="+f.estimate(i));
            f.s[i%f.s.length][0]=Integer.parseInt(new java.io.BufferedReader(new java.io.InputStreamReader(System.in)).readLine());
            //f.s[i%f.s.length][1]=System.nanoTime();
        }
    }
}
