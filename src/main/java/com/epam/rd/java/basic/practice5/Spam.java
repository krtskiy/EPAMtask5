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

        Spam spam = new Spam(new String[]{"@@@", "bbbbbbb"}, new int[]{499, 500});
        spam.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.severe(INTERRUPTED_MSG);
            Thread.currentThread().interrupt();
        }
        spam.stop();


        Spam spam2 = new Spam(new String[]{"a", "b"}, new int[]{999, 1000});
        spam2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.severe(INTERRUPTED_MSG);
            Thread.currentThread().interrupt();
        }
        spam2.stop();


    }

    public synchronized void start() {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Worker(messages[i], delays[i]);
        }
        for (Thread t : threads) {
            t.start();
        }
    }

    public void stop() {
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextLine()) {
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
    }

    private static class Worker extends Thread {

        private String message;
        private int delay;

        Worker(String message, int delay) {
            this.message = message;
            this.delay = delay;
        }

        @Override
        public void run() {
            while (true) {
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
