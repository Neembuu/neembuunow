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

package neembuu.diskmanager.impl;

import org.junit.Assert;

/**
 *
 * @author Shashank Tulsyan
 */
public class TestDefaultNomenclature {
    public static void main(String[] args) {
        DefaultNomenclature tn = new DefaultNomenclature();

        
        long o = 11221; int base = 0;
        for (int i = 0; i < 10; i++) {
            base = (int)Math.pow(10,i);
            String baseAsHex = Long.toString(base, 16);
            String x = tn.getRegionStorage().makeName(o, base);
            long offset = tn.getRegionStorage().getStartingOffset(x);
            System.out.println(offset+ " " +base+" "+baseAsHex +"="+x);
            Assert.assertEquals(o,offset);
        }
        
        for (int i = 0; i < 10; i++) {
            System.out.println(tn.getLogStorage().makeForRegion("DownloadThread", o, base));
            System.out.println(tn.getRegionStorage().makeName(o, base));
        }
        
        System.out.println(tn.getFileStorage().makeName("test120k.rmvb"));
        
        System.out.println(tn.getLogStorage().make("ReadQueueManagerThread"));
        
        
        
    }
}
