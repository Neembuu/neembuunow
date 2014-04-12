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
package neembuu.vfs.test.test;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shashank Tulsyan
 */
public class RequestsList {

    public static final class Request {
        public long time = 0;
        public long wait = 0;
        public long start = 0;
        public long capacity = 0;
        public long end = 0;

        @Override
        public String toString() {
            return "t="+time+" w="+wait+" s="+start+" e="+end+" c="+capacity;
        }
        
    }

    public static ArrayList<Request> createList(String fileName) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(fileName), Charset.defaultCharset());

        ArrayList<Request> r = new ArrayList<>();

        
        long t = 0;
        for (String line : lines) {
            Request rr = tryLine(line,t);
            if (rr != null) {
                if(t==0){rr.wait = 0;}
                t = rr.time;
                r.add(rr);
            }
        }

        return r;
    }

    private static Request tryLine(String line, long timeOffset) {
        try {
            Request r = new Request();
            String[] t = line.split("\t");
            r.time =  Long.parseLong( t[0] );
            r.wait =  r.time - timeOffset;
            r.start =  Long.parseLong( t[1] );
            r.end =  Long.parseLong( t[2] );
            r.capacity = r.end - r.start + 1;
            return r;
        } catch (Exception a) {
            return null;
        }
    }
    
    
    public static void main(String[] args) throws Exception{
        ArrayList<Request> rl = createList("f:\\listOfRequests.txt");
        for (Request request : rl) {
            System.out.println(request);
        }
    }
}
