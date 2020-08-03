package com.epam.rd.java.basic.practice5;

import java.util.Scanner;

public class Spam {

    private Thread[] threads;
    private String[] messages;
    private int[] delays;

    public Spam(final String[] messages, final int[] delays) {
        this.threads = new Thread[messages.length];
        this.messages = messages;
        this.delays = delays;

    }

    public static void main(String[] args) {
        Spam spam = new Spam(new String[]{"@@@", "bbbbbbb"}, new int[]{332, 999});
        spam.start();
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextLine()) {
            spam.stop();
        }
    }

    public void start() {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Worker(messages[i], delays[i]);
        }
        for (Thread t : threads) {
            t.start();
        }
    }

    public void stop() {
        Worker.running = false;
        for (Thread t : threads) {
            t.interrupt();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
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

        public Worker() {
        }

        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(delay);
                    System.out.println(message);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

    }

}
