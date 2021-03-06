package com.epam.rd.java.basic.practice5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        spam.stop();
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
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        try {
            bf.readLine(); //NOSONAR
        } catch (IOException e) {
            logger.severe(INTERRUPTED_MSG);
        }
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