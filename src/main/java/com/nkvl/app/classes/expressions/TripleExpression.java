package com.nkvl.app.classes.expressions;

import static com.nkvl.app.classes.RandomExtended.getRandomStringElement;
import static com.nkvl.app.classes.RandomExtended.nextRangedInt;

public final class TripleExpression {
    private final String text;
    private final int[] allAnswers;
    private final int rightAnswer;

    public TripleExpression() {
        text = genRawExpression();
        rightAnswer = solveExpression();
        allAnswers = genAnswers();
    }

    public String getText() { return text; }
    public int[] getAnswers() { return allAnswers; }
    public int getRightAnswer() { return rightAnswer; }

    public static String genRawExpression() {
        int[] nums = new int[3];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = nextRangedInt(0, 10);
        }
        String[] symbols = new String[] { "+", "-" };
        return String.format("%d %s %d %s %d",
                nums[0], getRandomStringElement(symbols), nums[1], getRandomStringElement(symbols), nums[2]);
    }

    public static String genExpressionWithPositiveResult() {
        return "";
    }

    public static double eval(final String str) {
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

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

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
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    x = switch (func) {
                        case "sqrt" -> Math.sqrt(x);
                        case "sin" -> Math.sin(Math.toRadians(x));
                        case "cos" -> Math.cos(Math.toRadians(x));
                        case "tan" -> Math.tan(Math.toRadians(x));
                        default -> throw new RuntimeException("Unknown function: " + func);
                    };
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    private int solveExpression() { return 0; }

    private int[] genAnswers() { return new int[] {0}; }
}
