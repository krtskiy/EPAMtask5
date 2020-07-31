package com.epam.rd.java.basic.practice5;

import java.util.logging.Logger;

public class Part3 {

    private int numberOfThreads;
    private int numberOfIterations;
    private int counter;
    private int counter2;
    private static Logger logger = Logger.getLogger(Part3.class.getName());
    private static final String INTERRUPTED_MSG = "Thread is interrupted";

    public Part3(int numberOfThreads, int numberOfIterations) {
        this.numberOfThreads = numberOfThreads;
        this.numberOfIterations = numberOfIterations;
    }

    public static void main(final String[] args) {
        Part3 comp = new Part3(1,1);
        System.out.println("~~~~~ Not synchronized ~~~~~");
        comp.compare();
        Part3 comp2 = new Part3(4,1);
        System.out.println("~~~~~ Synchronized ~~~~~");
        comp2.compareSync();

    }

    private synchronized void incrementCounter() {
        this.counter++;
    }

    private synchronized void incrementCounter2() {
        this.counter2++;
    }


    public void compare() {
        Thread compareThread1 = new Thread(new Runnable() { //NOSONAR
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    System.out.println(Integer.compare(counter, counter2));
                    counter++;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        logger.severe(INTERRUPTED_MSG);
                        Thread.currentThread().interrupt();
                    }
                    counter2++;
                }
            }
        });

        Thread compareThread2 = new Thread(new Runnable() { //NOSONAR
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    System.out.println(Integer.compare(counter, counter2));
                    counter++;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        logger.severe(INTERRUPTED_MSG);
                        Thread.currentThread().interrupt();
                    }
                    counter2++;
                }
            }
        });

        compareThread1.start();
        compareThread2.start();
        try {
            compareThread1.join();
        } catch (InterruptedException e) {
            logger.severe(INTERRUPTED_MSG);
            Thread.currentThread().interrupt();

        }
        try {
            compareThread2.join();
        } catch (InterruptedException e) {
            logger.severe(INTERRUPTED_MSG);
            Thread.currentThread().interrupt();

        }

    }

    private synchronized void comp() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Integer.compare(counter, counter2));
            incrementCounter();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.severe(INTERRUPTED_MSG);
                Thread.currentThread().interrupt();
            }
            incrementCounter2();
        }
    }

    public void compareSync() {
        Thread compareThread1 = new Thread(new Runnable() { //NOSONAR
            @Override
            public void run() {
                comp();
            }
        });

        Thread compareThread2 = new Thread(new Runnable() { //NOSONAR
            @Override
            public void run() {
                comp();
            }
        });

        compareThread1.start();
        compareThread2.start();
        try {
            compareThread1.join();
        } catch (InterruptedException e) {
            logger.severe(INTERRUPTED_MSG);
            Thread.currentThread().interrupt();

        }
        try {
            compareThread2.join();
        } catch (InterruptedException e) {
            logger.severe(INTERRUPTED_MSG);
            Thread.currentThread().interrupt();

        }


    }


}
