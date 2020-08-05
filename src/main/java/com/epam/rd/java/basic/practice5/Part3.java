package com.epam.rd.java.basic.practice5;

import java.util.logging.Logger;

public class Part3 {

    private int numberOfThreads; //NOSONAR
    private int numberOfIterations; //NOSONAR
    private int counter;
    private int counter2;
    private static Logger logger = Logger.getLogger(Part3.class.getName());
    private static final String INTERRUPTED_MSG = "Thread is interrupted";

    public Part3(int numberOfThreads, int numberOfIterations) {
        this.numberOfThreads = numberOfThreads;
        this.numberOfIterations = numberOfIterations;
    }

    public static void main(final String[] args) {
        Part3 comp = new Part3(1, 1);
        comp.compare();
        Part3 comp2 = new Part3(4, 1);
        comp2.compareSync();
    }

    private void compNotSync() {
        for (int i = 0; i < 5; i++) { //NOSONAR
            System.out.println(counter == counter2);
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

    public void compare() {
        Runnable run1 = this::compNotSync;
        Thread compareThread1 = new Thread(run1);
        Runnable run2 = this::compNotSync;

        Thread compareThread2 = new Thread(run2);

        compareThread1.start();
        compareThread2.start();
        try {
            compareThread1.join();
            compareThread2.join();
        } catch (InterruptedException e) {
            logger.severe(INTERRUPTED_MSG);
            Thread.currentThread().interrupt();

        }

    }

    private synchronized void compSync() {
        for (int i = 0; i < 5; i++) { //NOSONAR
            System.out.println(counter == counter2);
            counter++;
            try {
                Thread.sleep(100); //NOSONAR
            } catch (InterruptedException e) {
                logger.severe(INTERRUPTED_MSG);
                Thread.currentThread().interrupt();
            }
            counter2++;
        }
    }

    public void compareSync() {
        Thread compareThread1 = new Thread(new Runnable() { //NOSONAR
            @Override
            public void run() {
                compSync();
            }
        });

        Thread compareThread2 = new Thread(new Runnable() { //NOSONAR
            @Override
            public void run() {
                compSync();
            }
        });

        compareThread1.start();
        compareThread2.start();
        try {
            compareThread1.join();
            compareThread2.join();
        } catch (InterruptedException e) {
            logger.severe(INTERRUPTED_MSG);
            Thread.currentThread().interrupt();

        }
    }


}
