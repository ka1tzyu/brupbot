package com.nkvl.test;

import com.nkvl.app.classes.expressions.TripleExpression;

public final class ExpressionTest {
    public static void main(String[] args) {

    }

    private static double solveByParser(int repeats) {
        long start = System.nanoTime();
        String[] arr = new String[repeats];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = TripleExpression.genRawExpression();
        }
        for (String s : arr) {
            TripleExpression.eval(s);
        }
        long end = System.nanoTime();
        return ((end / 1000000000.0) - (start / 1000000000.0));
    }

    private static void genTest(int repeats) {
        long start = System.nanoTime();
        String[] arr = new String[repeats];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = TripleExpression.genRawExpression();
        }
        long end = System.nanoTime();

        System.out.println((end / 1000000000.0) - (start / 1000000000.0));
    }
}
