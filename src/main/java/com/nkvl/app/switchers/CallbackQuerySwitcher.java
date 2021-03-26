package com.nkvl.app.switchers;

import com.nkvl.app.App;
import com.nkvl.app.bot.BotMethods;
import com.nkvl.app.classes.Wasted;
import com.nkvl.app.classes.expressions.TripleExpression;
import com.nkvl.app.database.DBSpecies;
import com.nkvl.app.keyboards.Inline;
import org.apache.log4j.Level;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public final class CallbackQuerySwitcher {
    public static void send(Update update) throws TelegramApiException {
        String data = update.getCallbackQuery().getData();

        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(update.getCallbackQuery().getId());

        SendMessage message = new SendMessage();
        message.setChatId(update.getCallbackQuery().getFrom().getId().toString());

        long tmpId = update.getCallbackQuery().getFrom().getId();

        App.bot.execute(answer);

        switch (data) {
            case "start_e" -> {
                App.store.genExpressionSession(tmpId);

                TripleExpression te = App.store.expTable.get(tmpId)[App.store.resTable.get(tmpId).getExpPos()];
                SendMessage message2 = BotMethods.makeMessage(tmpId, "•••••••   " + te.getText() + " =");
                message2.setReplyMarkup(Inline.expGet(te.getAnswers()));
                App.bot.execute(message2);
            }
            default -> {
                if (data.contains("!e")) {
                    if (App.store.resTable.get(tmpId).getExpPos() ==
                            TripleExpression.getExpressionDefaultQuantity() - 1) {
                        int result = Integer.parseInt(data.replace("!e", ""));
                        App.store.resTable.get(tmpId).newResult(result);

                        App.store.resTable.get(tmpId).setEnd(System.nanoTime());

                        App.logger.log(Level.INFO, String.format("User [%d] finished easy game", tmpId));

                        String resultStr = "<i><b>Результаты испытания:</b></i>\n";
                        int errors = TripleExpression.checkErrorsOfStorageVaultAndResTable(
                                App.store.expTable.get(tmpId), App.store.resTable.get(tmpId).getResultsList());
                        int resultSeconds = App.store.resTable.get(tmpId).geTotalTime();
                        resultStr += "\nКоличество задач/ошибок - "
                                + TripleExpression.getExpressionDefaultQuantity() + "/" + errors;

                        resultStr += "\nВремя - " + Wasted.transferFromSeconds(resultSeconds);
                        if (errors > 0) {
                            resultStr += "\n\n<i><b>(Этот результат не будет засчитан, " +
                                    "так как у вас были ошибки)</b></i>";
                        } else {
                            int cur = Integer.parseInt(DBSpecies.getUserMedValue(tmpId, "emx"));
                            if (cur < resultSeconds)
                                DBSpecies.updateUserMed(tmpId, "emx", resultSeconds);
                                if (resultSeconds >= 120) {
                                    DBSpecies.updateUserMed(tmpId, "emed", "бронза");
                                } else if (resultSeconds >= 90) {
                                    DBSpecies.updateUserMed(tmpId, "emed", "серебро");
                                } else if (resultSeconds <= 60) {
                                    DBSpecies.updateUserMed(tmpId, "emed", "золото");
                                }
                                DBSpecies.updateStatValue(tmpId, resultSeconds);
                        }

                        DBSpecies.updateUser(tmpId, "time",
                                (Integer.parseInt(DBSpecies.getUserValue(tmpId, "time")) + resultSeconds));

                        SendMessage message2 = BotMethods.makeMessage(tmpId, resultStr);
                        message2.enableHtml(true);

                        App.bot.execute(message2);
                        return;
                    }
                    int result = Integer.parseInt(data.replace("!e", ""));
                    App.store.resTable.get(tmpId).newResult(result);

                    TripleExpression te = App.store.expTable.get(tmpId)[App.store.resTable.get(tmpId).getExpPos()];
                    SendMessage message2 = BotMethods.makeMessage(tmpId, "•••••••   " + te.getText() + " =");
                    message2.setReplyMarkup(Inline.expGet(te.getAnswers()));
                    App.bot.execute(message2);
                }
            }
        }
    }
}
