package com.nkvl.app.bot;

import com.nkvl.app.App;
import com.nkvl.app.classes.ConfigurationReader;
import com.nkvl.app.switchers.CallbackQuerySwitcher;
import com.nkvl.app.switchers.TextMessageSwitcher;
import org.apache.log4j.Level;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class BrupBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        try {
            // CallbackQuery case
            if (update.hasCallbackQuery()) {
                CallbackQuerySwitcher.send(update);
            // Text message case
            } else if (update.getMessage().hasText()) {
                TextMessageSwitcher.send(update);
            }
        } catch (TelegramApiException ex) {
            App.logger.log(Level.ERROR, ex.getMessage());
        }

    }

    @Override
    public String getBotToken() {
        return ConfigurationReader.getPropertyValue("BotToken").replace("__DOUBLE_POINT__", ":");
    }

    @Override
    public String getBotUsername() {
        return ConfigurationReader.getPropertyValue("BotUsername");
    }
}
