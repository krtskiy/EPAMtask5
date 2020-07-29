package com.epam.rd.java.basic.practice5;

public class Part3 {

    private int counter;

    private int counter2;

    public static void main(final String[] args) throws InterruptedException {
        System.out.println("~~~~~ Not synchronized ~~~~~");
        Part3 comp = new Part3();
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

    private synchronized boolean compareTwoCounters() {
        return counter == counter2;
    }

    public void compare() throws InterruptedException {
        Thread compareThread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    System.out.println(counter == counter2);
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

        Thread compareThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    System.out.println(counter == counter2);
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
        compareThread1.join();
        compareThread2.join();
        System.out.println(counter);
        System.out.println(counter2);

    }

    public void compareSync() throws InterruptedException {
        Thread compareThread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    System.out.println(compareTwoCounters());
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
        });

        Thread compareThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    System.out.println(compareTwoCounters());
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
        });

        compareThread1.start();
        compareThread2.start();
        compareThread1.join();
        compareThread2.join();
        System.out.println(counter);
        System.out.println(counter2);


    }

}
