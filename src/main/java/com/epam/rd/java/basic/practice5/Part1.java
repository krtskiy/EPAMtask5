package com.epam.rd.java.basic.practice5;

import java.util.logging.Logger;

public class Part1 {
    private static Logger logger = Logger.getLogger(Part1.class.getName());
    private static final String INTERRUPTED_MSG = "Thread is interrupted";

    public static void main(String[] args) {
        Thread firstThread = new Thread(new FirstThread());
        SecondThread secondThread = new SecondThread();
        firstThread.start();
        try {
            firstThread.join();
        } catch (InterruptedException e) {
            logger.severe(INTERRUPTED_MSG);
            Thread.currentThread().interrupt();
        }
        secondThread.start();
        try {
            secondThread.join();
        } catch (InterruptedException e) {
            logger.severe(INTERRUPTED_MSG);
            Thread.currentThread().interrupt();
        }
    }

    static void printThreadNameEvery300ms() {
        long before = System.currentTimeMillis();
        while (true) {
            long after = System.currentTimeMillis();
            if (after - before >= 2000) break;
            try {
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                logger.severe(INTERRUPTED_MSG);
                Thread.currentThread().interrupt();
            }
        }
    }

}

class FirstThread implements Runnable {
    @Override
    public void run() {
        Part1.printThreadNameEvery300ms();
    }
}

class SecondThread extends Thread {
    @Override
    public void run() {
        Part1.printThreadNameEvery300ms();
    }
}
