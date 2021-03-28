package com.nkvl.app.classes.expressions;

import java.util.Arrays;
import java.util.Random;

import static com.nkvl.app.classes.AdvancedRandom.nextRangedInt;

public class Expression {
    protected String text;
    protected int[] allAnswers;
    protected int rightAnswer;
    protected static int ACCURACY_RANGE;
    protected static int EXPRESSION_DEFAULT_QUANTITY;

    public String getText() { return text; }
    public int[] getAnswers() { return allAnswers; }
    public int getRightAnswer() { return rightAnswer; }
    public static int getExpressionDefaultQuantity() { return EXPRESSION_DEFAULT_QUANTITY; }

    protected static double evalExpression(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }
                return x;
            }
        }.parse();
    }

    protected int[] genAnswers() {
        int[] result = new int[] { 0, 0, 0 };
        int rPos = nextRangedInt(-1, 3);
        result[rPos] = rightAnswer;

        for (int i = 0; i < result.length; i++) {
            if (result[i] == 0) {
                while (true) {
                    int r = genFakeAnswer(rightAnswer);
                    if (Arrays.stream(result).noneMatch(x -> x == r)) {
                        result[i] = r;
                        break;
                    }
                }
            }
        }

        return result;
    }

    protected int genFakeAnswer(int trueAnswer) {
        Random r = new Random();
        if (r.nextBoolean() && r.nextBoolean()) {
            for (int i = 1; i < ACCURACY_RANGE; i++) {
                if (r.nextBoolean() && r.nextBoolean() && (trueAnswer - i) > 0) {
                    return trueAnswer - i;
                }
            }
        } else {
            for (int i = 1; i < ACCURACY_RANGE; i++) {
                if (r.nextBoolean() && r.nextBoolean() && (trueAnswer + i) > 0) {
                    return trueAnswer + i;
                }
            }
        }
        return trueAnswer + 1;
    }

    public String toString() {
        return String.format("%s = %d (%s)", text, rightAnswer, Arrays.toString(allAnswers));
    }
}
