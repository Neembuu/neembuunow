/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.vfs.file;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import jpfm.fs.ReadOnlyRawFileData;
import jpfm.operations.readwrite.ReadRequest;
import jpfm.operations.readwrite.SimpleReadRequest;

/**
 *
 * @author Shashank Tulsyan
 */
public class MonitorHttpFileCascade implements ReadOnlyRawFileData {

    private final MonitoredHttpFile httpfile;
    private final int suggestedDataGlimpseSize;
    private final AtomicBoolean open = new AtomicBoolean(false);

    public MonitorHttpFileCascade(MonitoredHttpFile basicRealFile, int suggestedDataGlimpseSize) {
        this.httpfile = basicRealFile;
        this.suggestedDataGlimpseSize = suggestedDataGlimpseSize;
        //opne the files, thsi will start the threads.
        basicRealFile.open();
        open.set(true);
    }

    @Override
    public ByteBuffer getDataGlimpse() {
        checkOpen();
        ByteBuffer buffer;
        if (suggestedDataGlimpseSize == 0) {
            buffer = ByteBuffer.allocate(10);
        } else {
            buffer =
                    ByteBuffer.allocate(suggestedDataGlimpseSize);
        }
        SimpleReadRequest srr = new SimpleReadRequest(buffer, 0);
        try {
            read(srr);
        } catch (Exception any) {
            return buffer;
        }
        while (!srr.isCompleted()) {
            try {
                Thread.sleep(100);
            } catch (Exception any) {
            }
        }
        return buffer;
    }

    @Override
    public String getName() {
        checkOpen();
        return httpfile.getName();
    }

    @Override
    public void read(ReadRequest readRequest) throws Exception {
        checkOpen();
        httpfile.read(readRequest);
    }

    @Override
    public long getFileSize() {
        checkOpen();
        return httpfile.getFileSize();
    }

    @Override
    public boolean isOpen() {
        return open.get();
    }

    private void checkOpen() {
        if (!isOpen()) {
            throw new IllegalStateException("This ReadOnlyRawFileData has been closed, open a new one");
        }
    }

    @Override
    public void close() {
        checkOpen();
        httpfile.cascadeReferenceCount.decrementAndGet();
    }
}