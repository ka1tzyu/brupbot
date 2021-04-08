package com.nkvl.app.bot;

import com.nkvl.app.classes.ConfigurationReader;
import com.nkvl.app.switchers.CallbackQuerySwitcher;
import com.nkvl.app.switchers.TextMessageSwitcher;
import org.apache.log4j.Level;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

import static com.nkvl.app.App.logger;


public final class BrupBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        try {
            // CallbackQuery case
            if (update.hasCallbackQuery()) {
                logger.log(Level.INFO, String.format("User [%d] sent callback data [\"%s\"]",
                        update.getCallbackQuery().getFrom().getId(),
                        update.getCallbackQuery().getData()));
                CallbackQuerySwitcher.send(update);
            // Text message case
            } else if (update.hasMessage() && update.getMessage().hasText()) {
                logger.log(Level.INFO, String.format("User [%d] sent message [\"%s\"]",
                        update.getMessage().getChatId(),
                        update.getMessage().getText()));
                TextMessageSwitcher.send(update);
            }
        } catch (TelegramApiException | IOException ex) {
            ex.printStackTrace();
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
