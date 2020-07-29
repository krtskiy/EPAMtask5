package com.epam.rd.java.basic.practice5;

public class Part1 {

    public static void main(String[] args) throws InterruptedException {
        Thread firstThread = new Thread(new FirstThread());
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
