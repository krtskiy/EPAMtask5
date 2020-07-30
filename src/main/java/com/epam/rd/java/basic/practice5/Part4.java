package com.epam.rd.java.basic.practice5;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.logging.Logger;

public class Part4 {

    private static int[][] matrixOfNumbers = new int[4][100];
    private static int[] maxNumberAtEveryLine = new int[4];
    private static Logger logger = Logger.getLogger(Part4.class.getName());
    private static final String INTERRUPTED_MSG = "Thread is interrupted";


    public static void main(final String[] args) {
        Part4HelperClass.write4by100MatrixToFileAndArray();

        long beforeMultiThread = System.currentTimeMillis();
        System.out.println(123);     //here should be parallelize search results
        long afterMultiThread = System.currentTimeMillis();
        System.out.println(afterMultiThread - beforeMultiThread);

        long beforeSingleThread = System.currentTimeMillis();
        System.out.println(Part4HelperClass.findMax());
        long afterSingleThread = System.currentTimeMillis();
        System.out.println(afterSingleThread - beforeSingleThread);
    }

    private static class Part4HelperClass {

        // writes 4x100 matrix to the file and helper array
        static void write4by100MatrixToFileAndArray() {
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

        // 1 parsing integers from file to 2d array
        private static int[][] parseStringIntoArray() {
            String input = Demo.readFile("part4.txt");
            String[] inputArrStr = input.split(" \\s*");
            int index = 0;
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 100; i++) {
                    matrixOfNumbers[j][i] = Integer.parseInt(inputArrStr[index++]);
                }
            }
            return matrixOfNumbers;
        }

        // 2 finds the maximum value of every line of 2d array
        private static int findMaxAtEveryLine(int[][] matrix, int index) {
            parseStringIntoArray();
            int maxValue = matrix[index][0];
            for (int i = 0; i < matrix[index].length; i++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    logger.severe(INTERRUPTED_MSG);
                    Thread.currentThread().interrupt();
                }
                if (matrix[index][i] > maxValue) {
                    maxValue = matrix[index][i];
                }
            }
            return maxValue;
        }

        // 3 finds the biggest value of the maximum values of each line in 2d array
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

        // 4 returns the maximum value in 4x100 matrix
        static int findMax() {
            for (int i = 0; i < matrixOfNumbers.length; i++) {
                maxNumberAtEveryLine[i] = findMaxAtEveryLine(matrixOfNumbers, i);
            }
            return findMaxInArray(maxNumberAtEveryLine);
        }

    }
}
