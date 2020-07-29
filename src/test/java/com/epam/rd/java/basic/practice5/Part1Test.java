package com.epam.rd.java.basic.practice5;

import org.junit.Test;

public class Part1Test {

    @Test(timeout = 3000)
    public void shouldPrintEveryThreadNameNoLongerThanThreeSeconds() {
        Part1.printThreadNameEvery300ms();
    }

}