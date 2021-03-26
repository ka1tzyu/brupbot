package com.nkvl.app.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public final class BotMethods {
    public static SendMessage makeMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(chatId+"");
        return message;
    }
}
