package com.epam.rd.java.basic.practice5;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.logging.Logger;

public class Part5 {

    private static final File FILE_PART5 = new File("part5.txt");
    private static Logger logger = Logger.getLogger(Part5.class.getName());
    private static final String ERROR_MSG = "Something went wrong!";
    private Thread[] threads = new Thread[10];

    public static void main(final String[] args) throws InterruptedException {
        Part5 part5 = new Part5();
        part5.writeIntoFileWith10Threads();

        StringBuilder sb = new StringBuilder();
        try (RandomAccessFile raf = new RandomAccessFile(FILE_PART5, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                sb.append((char)raf.read());
            }
        } catch (IOException e) {
            logger.severe(ERROR_MSG);
        }
        System.out.println(sb);
    }

    public void writeIntoFileWith10Threads() throws InterruptedException {
        try {
            Files.deleteIfExists(FILE_PART5.toPath());
        } catch (IOException e) {
            logger.severe(ERROR_MSG);
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Writer(i);
        }
        for (Thread t : threads) {
            t.start();
            t.join();
        }
    }

    private static class Writer extends Thread {

        private int counter;
        private static long pos;

        public Writer(int counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            try (RandomAccessFile raf = new RandomAccessFile(FILE_PART5, "rw")) {
                for (int i = 0; i < 20; i++) {
                    raf.seek(pos++);
                    raf.write(("" + counter).getBytes());
                    Thread.sleep(1);
                    if (i == 19) {
                        raf.write(System.lineSeparator().getBytes());
                        pos += 2;
                    }
                }
            } catch (IOException | InterruptedException e) {
                logger.severe(ERROR_MSG);
                Thread.currentThread().interrupt();
            }
        }
    }
}

