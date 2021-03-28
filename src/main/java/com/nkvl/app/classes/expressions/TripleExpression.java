package com.nkvl.app.classes.expressions;


import com.nkvl.app.classes.Storage;

import static com.nkvl.app.classes.AdvancedRandom.nextRangedInt;

public final class TripleExpression extends Expression {
    static {
        ACCURACY_RANGE = 5;
    }
    private final static int EXPRESSION_DEFAULT_QUANTITY = 40;


    public static void main(String[] args) {
        System.out.println(new TripleExpression().toString());
    }

    public TripleExpression() {
        rightAnswer = 0;
        text = genExpressionWithPositiveResult();
        allAnswers = genAnswers();
    }

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

    public static int checkErrorsOfStorageVaultAndResTable(long id, Storage store) {
        int cnt = 0;
        for (int i = 0; i < store.tripleExpTable.get(id).length; i++) {
            if (store.tripleExpTable.get(id)[i].getRightAnswer() != store.resTable.get(id).getResultsList().get(i))
                cnt++;
        }
        return cnt;
    }

    public static int getExpressionDefaultQuantity() { return EXPRESSION_DEFAULT_QUANTITY; }
}
