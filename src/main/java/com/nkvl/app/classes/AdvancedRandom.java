package com.nkvl.app.classes;

import java.util.Random;

public final class AdvancedRandom {
    public static int nextRangedInt(int first, int last) {
        Random r = new Random();
        while (true) {
            int tmp = r.nextInt(last);
            if (tmp > first)
                return tmp;
        }
    }

    public static String getRandomStringElement(String[] array) {
        int r = new Random().nextInt(array.length);
        return array[r];
    }
}
