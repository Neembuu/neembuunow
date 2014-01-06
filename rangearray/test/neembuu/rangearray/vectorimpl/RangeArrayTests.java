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

package neembuu.rangearray.vectorimpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import junit.framework.Assert;
import neembuu.rangearray.DissolvabilityRule;
import neembuu.rangearray.Range;
import neembuu.rangearray.RangeArray;
import neembuu.rangearray.RangeArrayFactory;
import neembuu.rangearray.RangeArrayParams;
import org.junit.Test;

/**
 *
 * @author Shashank Tulsyan
 */
public final class RangeArrayTests {
    private static final RangeArray RANGE_ARRAY = 
            RangeArrayFactory.newDefaultRangeArray(null);

    static {
        RANGE_ARRAY.addElement(10, 100,null);
        RANGE_ARRAY.addElement(1000, 1000+10,null);
        RANGE_ARRAY.addElement(1710000, 1710000+100,null);
    }
    
    @Test
    public void getIndexPairTest(){
        int index[]=RANGE_ARRAY.getIndexPair(1000000,1000000+50*1000);
        index=RANGE_ARRAY.getIndexPair(1001,1000000+50*1000);
        for (int i = 0; i < index.length; i++) {
            System.out.println(index[i]);
        }
    }
    
    @Test
    public void getIndexPairFullTest()throws IOException{
        long s,e;
        System.out.println(RANGE_ARRAY);
        s=0;e=1;
        System.out.println("indexOf "+s+"-->"+e+" ="+_(RANGE_ARRAY.getIndexPair(s, e)));
        
        System.out.println(RANGE_ARRAY);
        s=0;e=9;
        System.out.println("indexOf "+s+"-->"+e+" ="+_(RANGE_ARRAY.getIndexPair(s, e)));
        
        System.out.println(RANGE_ARRAY);
        s=0;e=10;
        System.out.println("indexOf "+s+"-->"+e+" ="+_(RANGE_ARRAY.getIndexPair(s, e)));
        
        System.out.println(RANGE_ARRAY);
        s=0;e=11;
        System.out.println("indexOf "+s+"-->"+e+" ="+_(RANGE_ARRAY.getIndexPair(s, e)));
        
        System.out.println(RANGE_ARRAY);
        s=9;e=15;
        System.out.println("indexOf "+s+"-->"+e+" ="+_(RANGE_ARRAY.getIndexPair(s, e)));
        
        System.out.println(RANGE_ARRAY);
        s=10;e=15;
        System.out.println("indexOf "+s+"-->"+e+" ="+_(RANGE_ARRAY.getIndexPair(s, e)));
        
        System.out.println(RANGE_ARRAY);
        s=15;e=1000;
        System.out.println("indexOf "+s+"-->"+e+" ="+_(RANGE_ARRAY.getIndexPair(s, e)));
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            System.out.println(RANGE_ARRAY);
            System.out.print("s?");s = Long.parseLong(br.readLine()); 
            System.out.print("e?");e = Long.parseLong(br.readLine()); 
            System.out.println("indexOf "+s+"-->"+e+" ="+_(RANGE_ARRAY.getIndexPair(s, e)));
        }
    }

    public static void main(String[] args)throws IOException {
        RangeArrayTests l = new RangeArrayTests();
        l.getIndexPairFullTest();
    }
    
    private String _(int[]l){
        return "("+l[0]+","+l[1]+")";
    }
        
    //@Test
    public void testIndexOf(){
        //RANGE_ARRAY.removeElement(RangeArray.MIN_VALUE_SUPPORTED, RangeArray.MAX_VALUE_SUPPORTED);

//        neembuu.common.ListenableRangeArray@1a80aea{
//         index=0 SeekableHttpChannel{0->382975 ,authl=382975 isAlive=true} 382976
//         index=1 SeekableHttpChannel{524288->576511 ,authl=576511 isAlive=false} 52224
//         index=2 SeekableHttpChannel{8992042->9158953 ,authl=9158953 isAlive=true} 166912
//         index=3 SeekableHttpChannel{9158180->9371171 ,authl=9371171 isAlive=true} 212992
//         index=4 SeekableHttpChannel{23604727->23620424 ,authl=23620424 isAlive=false} 15698
//        }

        // creating a malformed rangearray
        UnsynchronizedVector<Range<Integer>> arrayElements
                = new UnsynchronizedVector<Range<Integer>>();
        //arrayElements.add(new RangeArrayElement(RangeArray.MIN_VALUE_SUPPORTED-3, RangeArray.MIN_VALUE_SUPPORTED-3,-1));
        arrayElements.add(new RangeArrayElement(0,382975,0));
        arrayElements.add(new RangeArrayElement(524288,576511,1 ));
        arrayElements.add(new RangeArrayElement(8992042,9158953,2));
        // malformed entry here
        arrayElements.add(new RangeArrayElement(9158180,9371171,3));
        arrayElements.add(new RangeArrayElement(23604727,23620424,4));
        //arrayElements.add(new RangeArrayElement(RangeArray.MAX_VALUE_SUPPORTED+3, RangeArray.MAX_VALUE_SUPPORTED+3,-1));

        RangeArray<Integer> rangeArray =
                RangeArrayFactory.newDefaultRangeArray(new RangeArrayParams.Builder<Integer>()
                    .addDissolvabilityRule(DissolvabilityRule.COMPARE_PROPERTY_OBJECT)
                    .setContents(arrayElements)
                    .build());

        System.out.println(rangeArray);
        System.out.print("index of bad entry = ");
        RangeArrayElement<Integer> element = new RangeArrayElement<Integer>(8992042,9158179,2);
        System.out.println("element="+element);
        RuntimeException re = null;
        try{
            System.out.println(rangeArray.indexOf(8992042));
            System.out.println(rangeArray.indexOf(9158179));
            System.out.println(rangeArray.indexOf(9158953));
        }catch(RuntimeException rek){
            re = rek;
            re.printStackTrace(System.err);
            return;
        }
        Assert.assertNotNull(re);
    }


    //@Test
    public static void testIteratorOver()throws IOException{
        RangeArray<Integer> p = 
            RangeArrayFactory.newDefaultRangeArray(new RangeArrayParams.Builder<Integer>()
                .addDissolvabilityRule(DissolvabilityRule.COMPARE_PROPERTY_OBJECT)
                .build());
        //p.add(new RangeArrayElement(0,100,1));
        p.add(new RangeArrayElement(150,200,1));
        p.add(new RangeArrayElement(250,300,1));
        p.add(new RangeArrayElement(550,700,1));
        p.add(new RangeArrayElement(850,900,1));
        /*p.add(new RangeArrayElement(1150,1200,1));
        p.add(new RangeArrayElement(1250,1500,1));
        p.add(new RangeArrayElement(1650,2200,1));
        p.add(new RangeArrayElement(2500,2600,1));
        p.add(new RangeArrayElement(2700,2800,1));*/
        System.out.println(p);

        for(;;){
            java.io.BufferedReader r=new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
            String x;
            System.out.println("order=start end ");
            x=r.readLine();

            long s=Long.parseLong(x.substring(0,x.indexOf(" ")));
            x=x.substring(x.indexOf(" ")+1,x.length());

            long e=Long.parseLong(x);
            System.out.println("++++++++done-iterating+++++++++++");
            int[]indices=p.getIndexPair(s,e);
            System.out.println("indexPair="+indices[0]+" "+indices[1]);
            for (int i = indices[0]; i <= indices[1]; i++) {
                if(i==-1){System.out.println("-infinity--->-infinity");}
                else System.out.println(p.get(i));
            }

            /*Iterator<RangeArrayElement<Integer>> it =p.iteratorOver(
                    new RangeArrayElement(s,e));
                while (it.hasNext()) {
                    System.out.println(it.next());
                }*/
            System.out.println("--------done-iterating----------");


        }
    }



    //@Test
    public final void mainTest()throws Exception{
        //testIteratorOver();
        /*RangeArray<RangeArrayElement<Integer>> rangeArray
                = new RangeArray<RangeArrayElement<Integer>>(
                    5,
                    RangeArrayElement.RangeArrayElementFactory
                 );*/
        RangeArray<Integer> rangeArray
                = RangeArrayFactory.newDefaultRangeArray(new RangeArrayParams.Builder<Integer>()
                    .addDissolvabilityRule(DissolvabilityRule.COMPARE_PROPERTY_OBJECT)
                    .build());
        java.io.BufferedReader r=new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        System.out.println(rangeArray);
        System.out.println("order=start end property");
        System.out.println("Example tpying the following : ");
        System.out.println("0 100 1");
        System.out.println("200 300 2");
        System.out.println("makes the range array as ");
        rangeArray.addElement(0,100,1);
        rangeArray.addElement(200,300,2);
        System.out.println(rangeArray);
        System.out.println("50 250 anythingHereWhichIsNotANumber");
        System.out.println("delete the entry makes the range array as ");
        rangeArray.removeElement(50,250);
        System.out.println(rangeArray);
        System.out.println("-------------------------------------------------");
        boolean remove=false;
        int cnt = 0;
        for(String x;;cnt++){
            if(cnt==10){
                System.out.println("iterating over 400->1400");
                if(true)throw new UnsupportedOperationException("iteratorOver not available");
                /*Iterator<Range<Integer>> it =rangeArray.iteratorOver(
                        new RangeArrayElement(400,1400));
                while (it.hasNext()) {
                    System.out.println(it.next());
                }*/
                System.out.println("--------done-iterating----------");
            }
            System.out.println("order=start end property");
            x=r.readLine();

            long s=Long.parseLong(x.substring(0,x.indexOf(" ")));
            x=x.substring(x.indexOf(" ")+1,x.length());

            long e=Long.parseLong(x.substring(0,x.indexOf(" ")));
            x=x.substring(x.indexOf(" ")+1,x.length());

            //if(x.substring(0,x.indexOf(" ")).trim().equals("0"))da=Downloader.TorrentDownloader;
            int val=0;
            try{
                val=Integer.parseInt(x);
            }catch(Exception e1){
                val=0;remove=true;
            }


            RangeArrayElement<Integer> ax=null;
            try{
                ax=new RangeArrayElement<Integer>(s, e, new Integer(val));
            }catch(Exception sa){
                sa.printStackTrace();
                System.out.println("Try again");
                continue;
            }
            ax.setEnding(e);ax.setStarting(s);
            if(!remove){
                rangeArray.addElement(ax.starting(),ax.ending(),ax.getProperty());
                System.out.println("added");
            }
            else{
                rangeArray.removeElement(ax.starting(),ax.ending());
                System.out.println("removed");
                remove=false;
            }

            System.out.println(rangeArray.get((long)100));

            System.out.println(rangeArray);

            //System.out.println(rangeArray.contains(new RangeArrayElement(1028,1122)));
        }
    }

}
