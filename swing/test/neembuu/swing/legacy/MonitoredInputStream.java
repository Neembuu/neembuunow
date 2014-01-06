/*
 *  Copyright (C) 2009-2010 Shashank Tulsyan
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
 *
 *
 *
 *  Linking this library statically or
 *  dynamically with other modules is making a combined work based on this library.
 *  Thus, the terms and conditions of the GNU General Public License cover the whole combination.
 *
 *
 *  As a special exception, the copyright holders of this library give you permission to
 *  link this library with independent modules to produce an executable, regardless of
 *  the license terms of these independent modules, and to copy and
 *  distribute the resulting executable under terms of your choice,
 *  provided that you also meet, for each linked independent module,
 *  the terms and conditions of the license of that module.
 *  An independent module is a module which is not derived from or based on this library.
 *  If you modify this library, you may extend this exception to your version of the library,
 *  but you are not obligated to do so. If you do not wish to do so,
 *  delete this exception statement from your version.
 */

package neembuu.swing.legacy;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import neembuu.rangearray.RangeArray;
import neembuu.rangearray.RangeArrayFactory;
import neembuu.rangearray.RangeArrayParams;
import neembuu.rangearray.vectorimpl.RangeArrayImpl;
import neembuu.swing.RangeArrayProgressBar;

/**
 *
 * @author Shashank Tulsyan
 */
public class MonitoredInputStream extends InputStream{
    private final InputStream source;
    private long requestedOffset ;
    private long totalSize;
    private final RangeArray rangeArray;
    private final JPanel panel;
    private final RangeArrayProgressBar progressBar;
    private final JLabel instantaneousSpeedLabel;
    private final JLabel averageSpeedLabel;
    private long time=0;//assuming single thread condition,
    private final long startTime;
    //each thread should otherwise have it's own,private transient long that is
    private boolean ended = false;

    public MonitoredInputStream(final InputStream source) {
        this(source,RangeArrayImpl.MAX_VALUE_SUPPORTED);
    }

    public MonitoredInputStream(final InputStream source, long totalSize) {
        this.source = source;
        this.totalSize = totalSize;requestedOffset = 0;
        rangeArray = RangeArrayFactory.newDefaultRangeArray(null);
        rangeArray.setFileSize(totalSize);

        progressBar = new RangeArrayProgressBar(rangeArray,true);
        progressBar.setPreferredSize(new Dimension(400, 70));
        progressBar.setMaximumSize(new Dimension(400, 70));
        progressBar.setMinimumSize(new Dimension(400, 70));
        /*panel = new JPanel(new BorderLayout());*/panel = new JPanel(new FlowLayout());
        

        instantaneousSpeedLabel = new JLabel("Instantaneous Speed : 0kB/sec");
        instantaneousSpeedLabel.setPreferredSize(new Dimension(600, 40));
        instantaneousSpeedLabel.setMaximumSize(new Dimension(600, 40));
        instantaneousSpeedLabel.setMinimumSize(new Dimension(600, 40));
        

        averageSpeedLabel = new JLabel("Average Speed : 0kB/sec");
        averageSpeedLabel.setPreferredSize(new Dimension(600, 40));
        averageSpeedLabel.setMaximumSize(new Dimension(600, 40));
        averageSpeedLabel.setMinimumSize(new Dimension(600, 40));
        
        JButton stopButton = new JButton("Force Stop");
        stopButton.setPreferredSize(new Dimension(170, 40));
        stopButton.setMaximumSize(new Dimension(170, 40));
        stopButton.setMinimumSize(new Dimension(170, 40));
        stopButton.addActionListener(new ActionListener() {
            @Override
            public final void actionPerformed(ActionEvent e) {
                ended = true;
            }
        });

        panel.add(progressBar);
        panel.add(stopButton);
        panel.add(averageSpeedLabel);
        panel.add(instantaneousSpeedLabel);
        

        JFrame fr = new JFrame("Monitored ="+source.toString());
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setContentPane(panel);
        fr.pack();
        fr.setVisible(true);

        startTime = System.currentTimeMillis();
    }

    public long getStreamPosition() {
        return requestedOffset;
    }

    public long getTotalSize() {
        if(totalSize>=RangeArrayImpl.MAX_VALUE_SUPPORTED)
            throw new IllegalStateException("Content length is dynamic");
        return totalSize;
    }

    


    @Override
    public int read() throws IOException {
        if(ended)return -1;
        rangeArray.addElement(requestedOffset, requestedOffset, null);
        requestedOffset++;
        //System.out.println(requestedOffset/(1024));
        return source.read();
    }

    @Override
    public int read(byte[]b) throws IOException {
        if(ended)return -1;
        long newTime = System.currentTimeMillis();
        instantaneousSpeedLabel.setText("Instantaneous Speed : " +   (
                    b.length
                    /
                    ((newTime - time)*1.024)
                )+" kB/sec");
        
        averageSpeedLabel.setText("Average Speed : " +   (
                    requestedOffset
                    /
                    ((newTime - startTime)*1.024)
                )+" kB/sec");
        //System.out.println(requestedOffset/(1024));
        int read = source.read(b);
        rangeArray.addElement(requestedOffset, requestedOffset+read-1, null);
        requestedOffset+=read;
        time = newTime;
        return read;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if(ended)return -1;
        //System.out.println(requestedOffset/(1024));
        int read = source.read(b,off,len);
        rangeArray.addElement(requestedOffset, requestedOffset+read-1, null);
        requestedOffset+=read;
        return read;
    }

    @Override
    public void close() throws IOException {
        source.close();// it is important that we close streams and free resources
        super.close();
    }



}
