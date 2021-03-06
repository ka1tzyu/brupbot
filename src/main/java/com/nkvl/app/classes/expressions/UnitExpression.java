package com.nkvl.app.classes.expressions;

import com.nkvl.app.classes.AdvancedRandom;
import com.nkvl.app.classes.Storage;

import java.util.*;

import static com.nkvl.app.classes.AdvancedRandom.nextRangedInt;

public final class UnitExpression extends Expression {
    static {
        ACCURACY_RANGE = 5;
    }
    private final static int EXPRESSION_DEFAULT_QUANTITY = 30;


    private final static String[] SYMBOLS = new String[] { "△", "▲", "▧", "▣", "▢", "◉", "◯", "◇", "◈" };
    private final static String[] MATH_SYMBOLS = new String[] { "+", "-", "*" };

    public static void main(String[] args) {
        System.out.println(new UnitExpression().toSymvolic(genUnitTable()));
        System.out.println(UnitExpression.getExpressionDefaultQuantity());
    }

    public UnitExpression() {
        rightAnswer = 0;
        text = genExpressionWithPositiveResult();
        allAnswers = genAnswers();
    }

    public static Dictionary<String, String> genUnitTable() {
        String tmpSymbols = "";
        String tmpMathSymbols = "";
        while (true) {
            if (!(tmpSymbols.length() >= 3)) {
                String chr = AdvancedRandom.getRandomStringElement(SYMBOLS);
                if (!tmpSymbols.contains(chr)) {
                    tmpSymbols += chr;
                }
            } else {
                break;
            }
        }
        while (true) {
            if (!(tmpMathSymbols.length() >= 3)) {
                String chr = AdvancedRandom.getRandomStringElement(MATH_SYMBOLS);
                if (!tmpMathSymbols.contains(chr)) {
                    tmpMathSymbols += chr;
                }
            } else {
                break;
            }
        }

        Dictionary<String, String> tmpDict = new Hashtable<>();
        String[] tmpSymbolsArray = tmpSymbols.split("");
        String[] tmpMathSymbolsArray = tmpMathSymbols.split("");

        for (int i = 0; i < 3; i++)
            tmpDict.put(tmpSymbolsArray[i], tmpMathSymbolsArray[i]);

        return tmpDict;
    }

    private static String genRawExpression() {
        int[] nums = new int[2];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = nextRangedInt(0, 10);
        }

        return String.format("%d %s %d",
                nums[0], AdvancedRandom.getRandomStringElement(MATH_SYMBOLS), nums[1]);
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

    public String toSymvolic(Dictionary<String, String> syms) {
        String tmp = text;
        Enumeration<String> tmpEnum = syms.keys();
        while (tmpEnum.hasMoreElements()) {
            String key = tmpEnum.nextElement();
            tmp = tmp.replace(syms.get(key), key);
        }
        return tmp;
    }

    public static int checkErrorsOfStorageVaultAndResTable(long id, Storage store) {
        int cnt = 0;
        for (int i = 0; i < store.unitExpTable.get(id).length; i++) {
            if (store.unitExpTable.get(id)[i].getRightAnswer() != store.resTable.get(id).getResultsList().get(i))
                cnt++;
        }
        return cnt;
    }

    public static String unitSymbolsPrettyTable(Dictionary<String, String> syms) {
        StringBuilder tmp = new StringBuilder();
        tmp.append("Ваша таблица:\n\n");
        Enumeration<String> tmpEnum = syms.keys();
        while (tmpEnum.hasMoreElements()) {
            String key = tmpEnum.nextElement();
            tmp.append(String.format("%s  это  %s\n", key, syms.get(key)));
        }
        return tmp.toString();
    }

    public static String getSymbols() {
        return String.join(", ", SYMBOLS);
    }

    public static int getExpressionDefaultQuantity() { return EXPRESSION_DEFAULT_QUANTITY; }
}
