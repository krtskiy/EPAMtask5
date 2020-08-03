package com.epam.rd.java.basic.practice5;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Logger;

public class Part2 {

    private static final InputStream SYS_DEFAULT_IN = System.in;
    private static Logger logger = Logger.getLogger(Part3.class.getName());
    private static final String INTERRUPTED_MSG = "Thread is interrupted";

    public static void main(final String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Spam.main(null);
            }
        });
        t.start();
        System.setIn(new ByteArrayInputStream(System.lineSeparator().getBytes()));
        try {
            t.join();
        } catch (InterruptedException e) {
            logger.severe(INTERRUPTED_MSG);
            Thread.currentThread().interrupt();
        }
        System.setIn(SYS_DEFAULT_IN);
    }

}
