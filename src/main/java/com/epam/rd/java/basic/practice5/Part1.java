package com.epam.rd.java.basic.practice5;

public class Part1 {

    public static void main(String[] args) throws InterruptedException {
        Thread firstThread = new Thread(new Runnable() {
            @Override
            public void run() {
                printThreadNameEvery300ms();
            }
        });

        SecondThread secondThread = new SecondThread();
        firstThread.start();
        firstThread.join();
        secondThread.start();
    }

    static void printThreadNameEvery300ms() {
        long before = System.currentTimeMillis();
        while (true) {
            long after = System.currentTimeMillis();
            if (after - before >= 2000) break;
            try {
                Thread.sleep(300);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

}

class SecondThread extends Thread {
    @Override
    public void run() {
        Part1.printThreadNameEvery300ms();
    }
}
