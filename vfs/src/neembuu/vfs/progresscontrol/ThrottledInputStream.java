package neembuu.vfs.progresscontrol;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Shashank Tulsyan
 */
public final class ThrottledInputStream extends InputStream {

    private final InputStream inputStream;
    private final Throttle throttle;

    public ThrottledInputStream(InputStream inputStream, Throttle throttle) {
        this.inputStream = inputStream;
        this.throttle = throttle;
    }

    @Override
    public int available() throws IOException {
        return inputStream.available();
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }

    @Override
    public synchronized void mark(int readlimit) {
        inputStream.mark(readlimit);
    }

    @Override
    public boolean markSupported() {
        return inputStream.markSupported();
    }

    @Override
    public int read(byte[] b) throws IOException {
        double waitIntervalperbyte = throttle.getWaitIntervalPerByte();
        throttle.wait(waitIntervalperbyte, b.length);
        return inputStream.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        double waitIntervalperbyte = throttle.getWaitIntervalPerByte();
        throttle.wait(waitIntervalperbyte, b.length);
        return inputStream.read(b, off, len);
    }

    @Override
    public synchronized void reset() throws IOException {
        inputStream.reset();
    }

    @Override
    public long skip(long n) throws IOException {
        return inputStream.skip(n);
    }

    @Override
    public int read() throws IOException { //never invoked
        double waitIntervalperbyte = throttle.getWaitIntervalPerByte();
        throttle.wait(waitIntervalperbyte, 1);
        return inputStream.read();
    }
}
