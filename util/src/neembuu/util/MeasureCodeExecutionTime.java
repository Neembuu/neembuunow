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
package neembuu.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Shashank Tulsyan
 */
public final class MeasureCodeExecutionTime {

    private static final ConcurrentLinkedQueue<Observation> ALL_OBSERVATIONS = new ConcurrentLinkedQueue<Observation>();
    private final Deque<Observation> observationsStack = new ArrayDeque<Observation>();
    private static volatile boolean enabled = false;
    private static final ThreadLocal<MeasureCodeExecutionTime> threadLocal = new ThreadLocal<MeasureCodeExecutionTime>() {
        @Override
        protected MeasureCodeExecutionTime initialValue() {
            return new MeasureCodeExecutionTime();
        }
    };

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean enable) {
        enabled = enable;
    }

    public static void startNew() {
        startNew(true);
    }
    
    public static void startNew(boolean storeStackTraceInfo) {
        if (!enabled) {
            return;
        }
        MeasureCodeExecutionTime s = threadLocal.get();
        Observation observation = new Observation();
        s.observationsStack.push(observation);
        if(storeStackTraceInfo){
            observation.startingStackTraceElement = Thread.currentThread().getStackTrace()[2]; // takes around 0.3 seconds
        }else {
            observation.startingStackTraceElement = null;
        }

        observation.startTime = System.currentTimeMillis();
    }

    public static Observation stop() {
        return stop(null);
    }

    public static Observation stop(String name) {
        if (!enabled) {
            return null;
        }
        long e = System.currentTimeMillis();
        MeasureCodeExecutionTime s = threadLocal.get();

        if (s.observationsStack.isEmpty()) {
            throw new IllegalStateException("No observation has been started yet.");
        }
        Observation observation = s.observationsStack.pop();

        observation.name = name;
        observation.endTime = e;
        if(observation.startingStackTraceElement!=null){
            observation.endingStackTraceElement = Thread.currentThread().getStackTrace()[2]; // takes around 0.3 seconds
        }
        
        ALL_OBSERVATIONS.add(observation);

        return observation;
    }

    public static Observation stopAndStartNew() {
        return stopAndStartNew(null,true);
    }

    public static Observation stopAndStartNew(String name) {
        return stopAndStartNew(name, true);
    }
    
    public static Observation stopAndStartNew(String name,boolean storeStackTraceInfo) {
        Observation o = stop(name);
        startNew(storeStackTraceInfo);
        return o;
    }

    public static void printAllObservations() {
        printAllObservations(false);
    }
    
    public static void printAllObservations(boolean shortView) {
        if (!enabled) {
            return;
        }
        double t = 0;
        System.out.println("+++++All observations++++");
        
        for (Observation o : ALL_OBSERVATIONS) {
            t += (o.getEndTime() - o.getStartTime());
            System.out.println(o.toString(shortView));
        }
        System.out.println("Total time = "+t+"ms");
        System.out.println("-----All observations----");
    }

    public static final class Observation {

        private long startTime;
        private long endTime;
        private String name;
        private StackTraceElement startingStackTraceElement;
        private StackTraceElement endingStackTraceElement;

        public long getStartTime() {
            return startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public StackTraceElement getStartingStackTraceElement() {
            return startingStackTraceElement;
        }

        public StackTraceElement getEndingStackTraceElement() {
            return endingStackTraceElement;
        }
        private static final java.text.SimpleDateFormat SDF = new java.text.SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss.SSS a   ");

        public String toString(boolean shortView) {
            StringBuilder sb = new StringBuilder(100);
            if (name != null) {
                sb.append(name);
            }
            if(shortView){
                sb.append("{Duration=");
                sb.append(Long.toString(endTime - startTime));
                sb.append("}");
            }else {
                sb.append("{\n\tStartTime=");
                sb.append(SDF.format(new java.util.Date(startTime)));
                sb.append("\n\tEndTime=");
                sb.append(SDF.format(new java.util.Date(endTime)));

                sb.append("\n\tDuration=");
                sb.append(Long.toString(endTime - startTime));
                sb.append("ms=");
                sb.append(Double.toString((endTime - startTime * 1d) / (1000d)));
                sb.append("sec=");
                sb.append(Double.toString((endTime - startTime * 1d) / (1000d * 60)));
                sb.append("mins");



                if(startingStackTraceElement==null){
                    sb.append("\n\t------No Stacktrace available----\n");
                }else {
                    sb.append("\n\tStarting=");
                    sb.append(startingStackTraceElement.toString());
                    sb.append("\n\tEnding=");
                    sb.append(endingStackTraceElement.toString());
                }



                sb.append("\n}");
            }
            return sb.toString();
        }
    }
}
