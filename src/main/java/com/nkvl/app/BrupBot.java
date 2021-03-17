package com.nkvl.app;

import com.nkvl.app.switchers.CallbackQuerySwitcher;
import com.nkvl.app.switchers.TextMessageSwitcher;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class BrupBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        try {
            // Text message case
            if (update.getMessage().hasText()) {
                TextMessageSwitcher.send(update);
            // CallbackQuery case
            } else if (update.hasCallbackQuery()) {
                CallbackQuerySwitcher.send(update);
            }
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
