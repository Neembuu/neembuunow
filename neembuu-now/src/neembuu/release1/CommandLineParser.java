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

package neembuu.release1;

/**
 *
 * @author Shashank Tulsyan
 */
public class CommandLineParser {
    
    public interface Callback {
        void defaultMain(String[]args);
        void lazyUI(boolean lazyUI);
        void flashGotMain(String[]args);
    }
    
    private final Callback c;
    private final String[]args;

    public CommandLineParser(Callback c, String[] args) {
        this.c = c;
        this.args = args;
    }

    public void parse(){
        if(args==null || args.length==0){
            c.defaultMain(args);
            return;
        }
        
        if(contains("--lazyUI")>=0){
            c.lazyUI(true);
        }
        
        int fgidex = contains("--flashgot");
        if(fgidex>=0){
            if(fgidex + 1 < args.length){
                String[]newArgs = new String[args.length - fgidex - 1];
                System.arraycopy(args, fgidex+1, newArgs, 0, newArgs.length);
                c.flashGotMain(newArgs);
                return;//do not proceed with normal main
            }
        }
        
        c.defaultMain(args);
    }

    private int contains(String chk){
        for (int i = 0; i < args.length; i++) {
            if(args[i].equalsIgnoreCase(chk))return i;
        }
        return -1;
    }
}