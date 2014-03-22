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
package neembuu.release1.defaultImpl.splitImpl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import neembuu.vfs.file.RequestPatternListener;
import neembuu.vfs.file.SeekableConnectionFile;

/**
 *
 * @author Shashank Tulsyan
 */
public class AggregateRequestPatternListener implements RequestPatternListener {
    private final long offset;
    private final SplitGroupSession.Holder h;
    public AggregateRequestPatternListener(long offset, SplitGroupSession.Holder h) {
        this.offset = offset; this.h = h;
    }

    @Override
    public void requested(long requestStarting, long requestEnding) {
        requestStarting += offset;
        requestEnding += offset;
        h.requestPatternListener.requested(requestStarting, requestEnding);
    }
    
    public static void initializeAggregateRequestListener(
            final List<SeekableConnectionFile> connectionFiles,
            SplitGroupSession.Holder h) {
        Collections.sort(connectionFiles, new Comparator<SeekableConnectionFile>() {
            @Override
            public int compare(SeekableConnectionFile o1, SeekableConnectionFile o2) {
                return o1.getDownloadConstrainHandler().index() - o2.getDownloadConstrainHandler().index();
            }
        });
        long[] cummulativeFileSizes = new long[connectionFiles.size()];
        int i = 0;
        for (SeekableConnectionFile file : connectionFiles) {
            if (i == 0) {
                cummulativeFileSizes[i] = file.getFileSize();
                file.addRequestPatternListener(new AggregateRequestPatternListener(0,h));
            } else {
                cummulativeFileSizes[i] = cummulativeFileSizes[i - 1] + file.getFileSize();
                file.addRequestPatternListener(new AggregateRequestPatternListener(
                    cummulativeFileSizes[i-1],h));
            }
        }
    }
}
