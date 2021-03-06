package com.nkvl.app.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public final class Inline {
    public static InlineKeyboardMarkup get(String pattern) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();

        switch (pattern) {
            case "start_easy_confirm" -> {
                List<InlineKeyboardButton> brow1 = new ArrayList<>();

                InlineKeyboardButton btn1 = new InlineKeyboardButton();
                btn1.setText("Начать");
                btn1.setCallbackData("start_e");

                brow1.add(btn1);
                buttons.add(brow1);
            }
            case "start_hard_confirm" -> {
                List<InlineKeyboardButton> brow1 = new ArrayList<>();

                InlineKeyboardButton btn1 = new InlineKeyboardButton();
                btn1.setText("Начать");
                btn1.setCallbackData("start_h");

                brow1.add(btn1);
                buttons.add(brow1);
            }
            case "help" -> {
                List<InlineKeyboardButton> brow1 = new ArrayList<>();
                List<InlineKeyboardButton> brow2 = new ArrayList<>();

                InlineKeyboardButton btn1 = new InlineKeyboardButton();
                btn1.setText("Как работает бот");
                btn1.setCallbackData("help:how_it_works");

                InlineKeyboardButton btn2 = new InlineKeyboardButton();
                btn2.setText("О медалях");
                btn2.setCallbackData("help:about_medals");

                brow1.add(btn1);
                brow2.add(btn2);

                buttons.add(brow1);
                buttons.add(brow2);
            }
            case "stat" -> {
                List<InlineKeyboardButton> brow1 = new ArrayList<>();
                List<InlineKeyboardButton> brow2 = new ArrayList<>();

                InlineKeyboardButton btn1 = new InlineKeyboardButton();
                btn1.setText("Обычный режим");
                btn1.setCallbackData("stat/e");

                InlineKeyboardButton btn2 = new InlineKeyboardButton();
                btn2.setText("Усложненный режим");
                btn2.setCallbackData("stat/h");

                brow1.add(btn1);
                brow2.add(btn2);

                buttons.add(brow1);
                buttons.add(brow2);
            }
            default -> {
                if (pattern.contains("stat/")) {
                    List<InlineKeyboardButton> brow1 = new ArrayList<>();
                    List<InlineKeyboardButton> brow2 = new ArrayList<>();
                    List<InlineKeyboardButton> brow3 = new ArrayList<>();

                    InlineKeyboardButton btn1 = new InlineKeyboardButton();
                    btn1.setText("Последние 30 результатов");
                    btn1.setCallbackData(pattern+":last30");

                    InlineKeyboardButton btn2 = new InlineKeyboardButton();
                    btn2.setText("Последние 100 результатов");
                    btn2.setCallbackData(pattern+":last100");

                    InlineKeyboardButton btn3 = new InlineKeyboardButton();
                    btn3.setText("Все результаты (до 200)");
                    btn3.setCallbackData(pattern+":all");

                    brow1.add(btn1);
                    brow2.add(btn2);
                    brow3.add(btn3);

                    buttons.add(brow1);
                    buttons.add(brow2);
                    buttons.add(brow3);
                }
            }
        }
        markupKeyboard.setKeyboard(buttons);
        return markupKeyboard;
    }
    public static InlineKeyboardMarkup expGet(int[] expArr, String specialKey) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> brow1 = new ArrayList<>();

        InlineKeyboardButton btn1 = new InlineKeyboardButton();
        btn1.setText(expArr[0] + "");
        btn1.setCallbackData(specialKey + expArr[0]);

        InlineKeyboardButton btn2 = new InlineKeyboardButton();
        btn2.setText(expArr[1] + "");
        btn2.setCallbackData(specialKey + expArr[1]);

        InlineKeyboardButton btn3 = new InlineKeyboardButton();
        btn3.setText(expArr[2] + "");
        btn3.setCallbackData(specialKey + expArr[2]);

        brow1.add(btn1);
        brow1.add(btn2);
        brow1.add(btn3);
        buttons.add(brow1);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);

        return markupKeyboard;
    }

    public static InlineKeyboardMarkup tripleExpGet(int[] expArr) {
        return expGet(expArr, "!e");
    }

    public static InlineKeyboardMarkup unitExpGet(int[] expArr) {
        return expGet(expArr, "!h");
    }
}
