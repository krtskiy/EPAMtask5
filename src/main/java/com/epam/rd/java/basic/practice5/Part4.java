package com.epam.rd.java.basic.practice5;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.logging.Logger;

public class Part4 {

    private static int[] max = new int[4];
    private static int[] maxSingleThread = new int[4];
    private static int[][] inputArrInt = new int[4][100];
    private static Logger logger = Logger.getLogger(Part4.class.getName());
    private static final String INTERRUPTED_MSG = "Thread is interrupted";


    public static void main(final String[] args) {
        Part4HelperClass.write4by100MatrixToFile();

        long beforeWith4Threads = System.currentTimeMillis();
        System.out.println(Part4HelperClass.doWorkWith4Threads());
        long afterWith4Threads = System.currentTimeMillis();
        System.out.println(afterWith4Threads - beforeWith4Threads);

        long beforeSingleThread = System.currentTimeMillis();
        System.out.println(Part4HelperClass.findMax());
        long afterSingleThread = System.currentTimeMillis();
        System.out.println(afterSingleThread - beforeSingleThread);

    }

    private static class Part4HelperClass {

        static void write4by100MatrixToFile() {
            StringBuilder numbers = new StringBuilder();
            SecureRandom secRand = new SecureRandom();
            File inputData = new File("part4.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputData))) {
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < 100; i++) {
                        int rand = secRand.nextInt(899) + 100;
                        numbers.append(rand).append(" ");
                    }
                    numbers.append(System.lineSeparator());
                }
                writer.write(numbers.toString());
            } catch (IOException ex) {
                logger.severe(INTERRUPTED_MSG);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.severe(INTERRUPTED_MSG);
                Thread.currentThread().interrupt();
            }
            splitMatrixIntoArray();
        }

        private static void splitMatrixIntoArray() {
            String input = Demo.readFile("part4.txt");
            String[] inputArrStr = input.split(System.lineSeparator());
            String[][] inputArrStr2 = new String[inputArrStr.length][100];
            for (int i = 0; i < inputArrStr2.length; i++) {
                inputArrStr2[i] = inputArrStr[i].split("\\s");
            }
            for (int i = 0; i < inputArrInt.length; i++) {
                for (int j = 0; j < inputArrInt[i].length; j++) {
                    inputArrInt[i][j] = Integer.parseInt(inputArrStr2[i][j]);
                }
            }
        }

        private static int findMaxInArray(int[] arr) {
            int maxValue = arr[0];
            for (int i = 0; i < arr.length; i++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    logger.severe(INTERRUPTED_MSG);
                    Thread.currentThread().interrupt();
                }
                if (arr[i] > maxValue) {
                    maxValue = arr[i];
                }
            }
            return maxValue;
        }

        private static int findMax() {
            for (int i = 0; i < inputArrInt.length; i++) {
                maxSingleThread[i] = findMaxInArray(inputArrInt[i]);
            }
            int maxValue = maxSingleThread[0];
            for (int i = 1; i < maxSingleThread.length; i++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    logger.severe(INTERRUPTED_MSG);
                    Thread.currentThread().interrupt();
                }
                if (maxSingleThread[i] > maxValue) {
                    maxValue = maxSingleThread[i];
                }
            }
            return maxValue;
        }

        private static int doWorkWith4Threads() {
            Thread t = new Thread(new Runnable() { //NOSONAR
                @Override
                public void run() {
                    max[0] = findMaxInArray(inputArrInt[0]);
                }
            });

            Thread t1 = new Thread(new Runnable() { //NOSONAR
                @Override
                public void run() {
                    max[1] = findMaxInArray(inputArrInt[1]);
                }
            });

            Thread t2 = new Thread(new Runnable() { //NOSONAR
                @Override
                public void run() {
                    max[2] = findMaxInArray(inputArrInt[2]);
                }
            });

            Thread t3 = new Thread(new Runnable() { //NOSONAR
                @Override
                public void run() {
                    max[3] = findMaxInArray(inputArrInt[3]);
                }
            });

            t.start();
            t1.start();
            t2.start();
            t3.start();

            try {
                t.join();
                t1.join();
                t2.join();
                t3.join();
            } catch (InterruptedException e) {
                logger.severe(INTERRUPTED_MSG);
                Thread.currentThread().interrupt();
            }

            int maxValue = max[0];
            for (int i = 1; i < max.length; i++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    logger.severe(INTERRUPTED_MSG);
                    Thread.currentThread().interrupt();
                }
                if (max[i] > maxValue) {
                    maxValue = max[i];
                }
            }
            return maxValue;
        }

    }
}
