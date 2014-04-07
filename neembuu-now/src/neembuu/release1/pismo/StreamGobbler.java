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

package neembuu.release1.pismo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import neembuu.release1.api.ui.Message;

/**
 *
 * @author Shashank Tulsyan
 */
public final class StreamGobbler extends Thread {
    private final InputStream is;
    private final String type;
    private final Message m;
    private final StringBuilder sb = new StringBuilder();

    public StreamGobbler(InputStream is, String type,Message m) {
        this.is = is; this.type = type; this.m = m;
    }

    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null){
                System.out.println(type + "> " + line);
                sb.append(line); sb.append("\n");
                m.setMessage(sb.toString());
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    public String getAsString(){
        return sb.toString();
    }
}