package com.nkvl.app;

import com.nkvl.app.bot.BrupBot;
import com.nkvl.app.classes.Storage;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class App {
    public static final BrupBot bot = new BrupBot();
    public static final Logger logger = Logger.getLogger("root");
    public static final Storage store = new Storage();

    public static void main(String[] args) {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
