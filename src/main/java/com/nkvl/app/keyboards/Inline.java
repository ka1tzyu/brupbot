package com.nkvl.app.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Inline {
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
        }
        markupKeyboard.setKeyboard(buttons);
        return markupKeyboard;
    }
    public static InlineKeyboardMarkup expGet(int[] expArr) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> brow1 = new ArrayList<>();

        InlineKeyboardButton btn1 = new InlineKeyboardButton();
        btn1.setText(expArr[0] + "");
        btn1.setCallbackData("!e" + expArr[0]);

        InlineKeyboardButton btn2 = new InlineKeyboardButton();
        btn2.setText(expArr[1] + "");
        btn2.setCallbackData("!e" + expArr[1]);

        InlineKeyboardButton btn3 = new InlineKeyboardButton();
        btn3.setText(expArr[2] + "");
        btn3.setCallbackData("!e" + expArr[2]);

        brow1.add(btn1);
        brow1.add(btn2);
        brow1.add(btn3);
        buttons.add(brow1);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);

        return markupKeyboard;
    }
}
