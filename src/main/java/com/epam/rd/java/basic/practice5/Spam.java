package com.epam.rd.java.basic.practice5;

import java.util.Scanner;
import java.util.logging.Logger;

public class Spam {

    private static Logger logger = Logger.getLogger(Spam.class.getName());
    private static final String INTERRUPTED_MSG = "Thread is interrupted";
    private Thread[] threads;
    private String[] messages;
    private int[] delays;

    public Spam(final String[] messages, final int[] delays) {
        this.threads = new Thread[messages.length];
        this.messages = messages;
        this.delays = delays;

    }

    public static void main(String[] args) {
        Spam spam = new Spam(new String[]{"aaaa", "bbbb"}, new int[]{250, 500});
        spam.start();
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextLine()) {
            spam.stop();
        }
    }

    public void start() {
        long before = System.currentTimeMillis();

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Worker(messages[i], delays[i]);
        }
        for (Thread t : threads) {
            t.start();
            long after = System.currentTimeMillis();
            System.out.println(Thread.activeCount() + " threads created after " + (after - before) + " ms");
        }
    }

    public void stop() {
        Worker.running = false;
        for (Thread t : threads) {
            t.interrupt();
            try {
                t.join();
            } catch (InterruptedException e) {
                logger.severe(INTERRUPTED_MSG);
                Thread.currentThread().interrupt();
            }
        }
    }

    private static class Worker extends Thread {

        private String message;
        private int delay;
        private static volatile boolean running = true;

        Worker(String message, int delay) {
            this.message = message;
            this.delay = delay;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    System.out.println(message);
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

    }

}
