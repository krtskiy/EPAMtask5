package com.epam.rd.java.basic.practice5;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class Part2 {

    private static final InputStream SYS_DEFAULT_IN = System.in;
    private static Logger logger = Logger.getLogger(Part2.class.getName());
    private static final String INTERRUPTED_MSG = "Thread is interrupted";

    public static void main(final String[] args) {
        System.setIn(new CustomInputStream(System.lineSeparator().getBytes()));
        Thread t = new Thread(new Runnable() { //NOSONAR
            @Override
            public void run() {
                Spam.main(null);
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            logger.severe(INTERRUPTED_MSG);
            Thread.currentThread().interrupt();
        }
        System.setIn(SYS_DEFAULT_IN);
    }

}

class CustomInputStream extends InputStream { //NOSONAR

    private byte[] buf;
    private int pos;
    private int count;

    public CustomInputStream(byte[] buf) {
        this.buf = buf;
        this.pos = 0;
        this.count = buf.length;
    }

    private static Logger logger = Logger.getLogger(CustomInputStream.class.getName());
    private static final String INTERRUPTED_MSG = "Thread is interrupted";

    @Override
    public int read() throws IOException {
        if (pos == 0) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                logger.severe(INTERRUPTED_MSG);
                Thread.currentThread().interrupt();
            }
        }
        return (pos < count) ? (buf[pos++] & 0xff) : -1;
    }
}
