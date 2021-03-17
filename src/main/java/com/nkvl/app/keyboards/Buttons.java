package com.nkvl.app.keyboards;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Buttons {
    public static void set(SendMessage sendMessage, String pattern) {
        Buttons.set(sendMessage, pattern, false);
    }
    public static void set(SendMessage sendMessage, String pattern, boolean onetime) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(onetime);

        List<KeyboardRow> keyboard = new ArrayList<>();

        switch (pattern) {
            case "main" -> {
                KeyboardRow row1 = new KeyboardRow();
                row1.add(new KeyboardButton("Испытания"));
                KeyboardRow row2 = new KeyboardRow();
                row2.add(new KeyboardButton("Мои данные"));

                keyboard.add(row1);
                keyboard.add(row2);
            }
            case "mback" -> {
                KeyboardRow row1 = new KeyboardRow();
                row1.add(new KeyboardButton("Назад"));

                keyboard.add(row1);
            }
            case "mdback" -> {
                KeyboardRow row1 = new KeyboardRow();
                row1.add(new KeyboardButton("Назад в [Мои данные]"));

                keyboard.add(row1);
            }
            case "chback" -> {
                KeyboardRow row1 = new KeyboardRow();
                row1.add(new KeyboardButton("Назад в [Испытания]"));

                keyboard.add(row1);
            }
            case "mydata" -> {
                KeyboardRow row1 = new KeyboardRow();
                row1.add(new KeyboardButton("Профиль"));
                KeyboardRow row2 = new KeyboardRow();
                row2.add(new KeyboardButton("Статистика"));
                KeyboardRow row3 = new KeyboardRow();
                row3.add(new KeyboardButton("Назад"));

                keyboard.add(row1);
                keyboard.add(row2);
                keyboard.add(row3);
            }
            case "chall" -> {
                KeyboardRow row1 = new KeyboardRow();
                row1.add(new KeyboardButton("Обычный режим"));
                KeyboardRow row2 = new KeyboardRow();
                row2.add(new KeyboardButton("Усложнённый режим"));
                KeyboardRow row3 = new KeyboardRow();
                row3.add(new KeyboardButton("Назад"));

                keyboard.add(row1);
                keyboard.add(row2);
                keyboard.add(row3);
            }
        }

        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }
}
