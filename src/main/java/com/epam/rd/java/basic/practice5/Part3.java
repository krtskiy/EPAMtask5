package com.epam.rd.java.basic.practice5;

public class Part3 {

    private int counter;

    private int counter2;

    public static void main(final String[] args) {
        Part3 comp = new Part3();
        System.out.println("~~~~~ Not synchronized ~~~~~");
        comp.compare();
        System.out.println("~~~~~ Synchronized ~~~~~");
        comp.compareSync();

    }

    private synchronized void incrementCounter() {
        this.counter++;
    }

    private synchronized void incrementCounter2() {
        this.counter2++;
    }

    private synchronized void comp() {
        for (int i = 0; i < 3; i++) {
            System.out.println(Integer.compare(counter, counter2));
            incrementCounter();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            incrementCounter2();
        }
    }

    private void compare() {
        Thread compareThread1 = new Thread(new Runnable() { //NOSONAR
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    System.out.println(Integer.compare(counter, counter2));
                    counter++;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                    counter2++;
                }
            }
        });

        Thread compareThread2 = new Thread(new Runnable() { //NOSONAR
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    System.out.println(Integer.compare(counter, counter2));
                    counter++;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
            e.printStackTrace();
            Thread.currentThread().interrupt();

        }
        try {
            compareThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();

        }

    }

    private void compareSync() {
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
            e.printStackTrace();
            Thread.currentThread().interrupt();

        }
        try {
            compareThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();

        }


    }


}
