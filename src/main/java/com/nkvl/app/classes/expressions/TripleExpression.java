package com.nkvl.app.classes.expressions;

import com.nkvl.app.classes.Storage;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.nkvl.app.classes.RandomExtended.nextRangedInt;

public final class TripleExpression {
    private final String text;
    private final int[] allAnswers;
    private int rightAnswer;
    private final static int ACCURACY_RANGE = 5;
    private final static int EXPRESSION_DEFAULT_QUANTITY = 10;

    public static void main(String[] args) {
        System.out.println(new TripleExpression().toString());
    }

    public TripleExpression() {
        rightAnswer = 0;
        text = genExpressionWithPositiveResult();
        allAnswers = genAnswers();
    }

    public String getText() { return text; }
    public int[] getAnswers() { return allAnswers; }
    public int getRightAnswer() { return rightAnswer; }
    public static int getExpressionDefaultQuantity() { return EXPRESSION_DEFAULT_QUANTITY; }

    private static String genRawExpression() {
        int[] nums = new int[3];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = nextRangedInt(0, 10);
        }

        String[] symbols = new String[] { "+", "-" };

        String sym1 = symbols[nextRangedInt(-1, 2)];
        String sym2 = sym1.equals("+") ? "-" : "+";
        return String.format("%d %s %d %s %d",
                nums[0], sym1, nums[1], sym2, nums[2]);
    }

    private String genExpressionWithPositiveResult() {
        while (true) {
            String resStr = genRawExpression();
            double resDouble = evalExpression(resStr);
            if (resDouble > 0) {
                rightAnswer = (int) resDouble;
                return resStr;
            }
        }
    }

    private static double evalExpression(final String str) {
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

    private int[] genAnswers() {
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

    private int genFakeAnswer(int trueAnswer) {
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

    public static int checkErrorsOfStorageVaultAndResTable(TripleExpression[] tArray, List<Integer> resTableContent) {
        int cnt = 0;
        for (int i = 0; i < tArray.length; i++) {
            if (tArray[i].getRightAnswer() != resTableContent.get(i))
                cnt++;
        }
        return cnt;
    }

    public String toString() {
        return String.format("%s = %d (%s)", text, rightAnswer, Arrays.toString(allAnswers));
    }
}
