package com.nkvl.app;

import com.nkvl.app.bot.BrupBot;
import com.nkvl.app.classes.PathResolve;
import com.nkvl.app.classes.Storage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public final class App {
    public static final BrupBot bot = new BrupBot();
    public static final Logger logger = Logger.getLogger("root");
    public static final Storage store = new Storage();
    public static Boolean isDebug = false;

    public static void main(String[] args) {
        try {
            isDebug = args[0].equals("-debug");
        } catch (ArrayIndexOutOfBoundsException ex) {
            isDebug = false;
        }

        PropertyConfigurator.configure(PathResolve.getPathTo("log.conf"));

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException ex) {
            logger.log(Level.ERROR, ex.getMessage());
        }
    }
}
