package com.nkvl.app;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class BrupBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        sendMessage(update.getMessage().getChatId().toString(), message);
    }

    public synchronized void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return "1738877744:AAHC2ETK1knQNYE1jNuI9fhxqRhRFRwYMT4";
    }

    @Override
    public String getBotUsername() {
        return "brupbot";
    }
}
