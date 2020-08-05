package com.epam.rd.java.basic.practice5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Demo {

    private static Logger logger = Logger.getLogger(Demo.class.getName());
    private static final String INTERRUPTED_MSG = "Thread is interrupted";


    public static void main(String[] args) {
        System.out.println("~~~~~~~~~~ PART1 ~~~~~~~~~~");
        Part1.main(null);
        System.out.println("~~~~~~~~~~ PART2 ~~~~~~~~~~");
        Part2.main(null);
        System.out.println("~~~~~~~~~~ PART3 ~~~~~~~~~~");
        Part3.main(null);
        System.out.println("~~~~~~~~~~ PART4 ~~~~~~~~~~");
        Part4.main(null);
        System.out.println("~~~~~~~~~~ PART5 ~~~~~~~~~~");
        Part5.main(null);
    }

    public static String readFile(String path) {
        StringBuilder result = new StringBuilder();
        File file = new File(path);
        try {
            Scanner scan = new Scanner(file, "cp1251");
            while (scan.hasNextLine()) {
                result.append(scan.nextLine()).append(System.lineSeparator());
            }
            scan.close();
            return result.toString().trim();
        } catch (FileNotFoundException e) {
            logger.severe(INTERRUPTED_MSG);
        }
        return result.toString();
    }

}
