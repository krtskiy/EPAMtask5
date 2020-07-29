package com.epam.rd.java.basic.practice5;

import org.junit.Assert;
import org.junit.Test;

public class Part1Test {

    @Test
    public void should() {
        long before = System.currentTimeMillis();
        Part1.printThreadNameEvery300ms();
        long after = System.currentTimeMillis();
        Assert.assertNotEquals(0, after - before);
    }

}