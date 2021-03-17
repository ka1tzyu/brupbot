package com.nkvl.app.switchers;

import com.nkvl.app.App;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public final class CallbackQuerySwitcher {
    public static void send(Update update) throws TelegramApiException {
        String data = update.getCallbackQuery().getData();

        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(update.getCallbackQuery().getId());

        switch (data) {
            case "1" -> answer.setText("11");
            case "2" -> answer.setCallbackQueryId("22");
        }

        App.bot.execute(answer);
    }
}
