package com.epam.rd.java.basic.practice5;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.logging.Logger;

public class Part4 {

    private static int[][] inputArrInt = new int[4][100];
    private static Logger logger = Logger.getLogger(Part4.class.getName());
    private static final String INTERRUPTED_MSG = "Thread is interrupted";


    public static void main(final String[] args) {
        Part4HelperClass.write4by100MatrixToFile();

        long beforeMultiThread = System.currentTimeMillis();
        System.out.println(4);     //here should be parallelize search results
        long afterMultiThread = System.currentTimeMillis();
        System.out.println(afterMultiThread - beforeMultiThread);

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
            splitMatrixIntoArray();
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
            int maxValue = findMaxInArray(inputArrInt[0]);
            for (int i = 0; i < inputArrInt.length; i++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    logger.severe(INTERRUPTED_MSG);
                    Thread.currentThread().interrupt();
                }
                if (findMaxInArray(inputArrInt[i]) > maxValue) {
                    maxValue = findMaxInArray(inputArrInt[i]);
                }
            }
            return maxValue;
        }

    }
}
