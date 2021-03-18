package com.nkvl.app;

import org.apache.log4j.PropertyConfigurator;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * TODO: expressions, logs, docs, stats
 * */

public class App {
    public static final BrupBot bot = new BrupBot();

    public static void main(String[] args) {
        PropertyConfigurator.configure("src\\main\\resources\\log4j.properties");

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
