package com.epam.rd.java.basic.practice5;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;

public class Part4 {

    private static int[][] matrixOfNumbers = new int[4][100];
    private static int[] maxNumberAtEveryLine = new int[4];

    public static void main(final String[] args) {
        Part4FileWriter.write4by100Matrix();
        for (int i = 0; i < matrixOfNumbers.length; i++) {
            maxNumberAtEveryLine[i] = findMaxAtEveryLine(matrixOfNumbers, i);
        }
        System.out.println(findMaxInArray(maxNumberAtEveryLine));
    }

    private static int findMaxAtEveryLine(int[][] matrix, int index) {
        int maxValue = matrix[index][0];
        for (int i = 0; i < matrix[index].length; i++) {
            if (matrix[index][i] > maxValue) {
                maxValue = matrix[index][i];
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
        return maxValue;
    }

    private static int findMaxInArray(int[] arr) {
        int maxValue = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > maxValue) {
                maxValue = arr[i];
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
        return maxValue;
    }


    private static class Part4FileWriter {

        static void write4by100Matrix() {
            StringBuilder numbers = new StringBuilder();
            SecureRandom secRand = new SecureRandom();
            File inputData = new File("part4.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputData))) {
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < 100; i++) {
                        int rand = secRand.nextInt(899) + 100;
                        numbers.append(rand).append(" ");
                        matrixOfNumbers[j][i] = rand;
                    }
                    numbers.append(System.lineSeparator());
                }
                writer.write(numbers.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
