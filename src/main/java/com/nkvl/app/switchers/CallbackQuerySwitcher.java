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
        long tmpId = update.getCallbackQuery().getFrom().getId();

        AnswerCallbackQuery callbackQueryAnswer = new AnswerCallbackQuery();
        callbackQueryAnswer.setCallbackQueryId(update.getCallbackQuery().getId());

        SendMessage message = new SendMessage();
        message.setChatId(update.getCallbackQuery().getFrom().getId().toString());

        App.bot.execute(callbackQueryAnswer);

        switch (data) {
            case "start_e" -> {
                App.store.genExpressionSession(tmpId);

                TripleExpression tripleExpressionInstance =
                        App.store.expTable.get(tmpId)[App.store.resTable.get(tmpId).getExpPos()];
                SendMessage message2 = BotMethods.makeMessage(tmpId, "•••••••   "
                        + tripleExpressionInstance.getText() + " =");
                message2.setReplyMarkup(Inline.expGet(tripleExpressionInstance.getAnswers()));
                App.bot.execute(message2);
            }
            case "start_h" -> {
                SendMessage message2 = BotMethods.makeMessage(tmpId, "В ближайшем будущем...");
                App.bot.execute(message2);
            }
            default -> {
                if (data.contains("!e")) {
                    if (!(App.store.resTable.get(tmpId).getExpPos() ==
                            TripleExpression.getExpressionDefaultQuantity() - 1)) {
                        int result = Integer.parseInt(data.replace("!e", ""));
                        App.store.resTable.get(tmpId).newResult(result);

                        TripleExpression tripleExpressionInstance =
                                App.store.expTable.get(tmpId)[App.store.resTable.get(tmpId).getExpPos()];
                        SendMessage message2 = BotMethods.makeMessage(tmpId, "•••••••   "
                                + tripleExpressionInstance.getText() + " =");
                        message2.setReplyMarkup(Inline.expGet(tripleExpressionInstance.getAnswers()));
                        App.bot.execute(message2);
                    } else {
                        int result = Integer.parseInt(data.replace("!e", ""));
                        App.store.resTable.get(tmpId).newResult(result);

                        App.store.resTable.get(tmpId).setEnd(System.nanoTime());

                        int errors = TripleExpression.checkErrorsOfStorageVaultAndResTable(tmpId, App.store);
                        int resultSeconds = App.store.resTable.get(tmpId).geTotalTime();

                        String resultString = String.format(
                                """
                                <i><b>Результаты испытания:</b></i>
                                Количество задач/ошибок - %s
                                Время - %s
                                
                                """,
                                TripleExpression.getExpressionDefaultQuantity() + "/" + errors,
                                Wasted.transferFromSeconds(resultSeconds));

                        if (errors > 0) {
                            resultString += "\n<i><b>(Этот результат не будет засчитан, " +
                                    "так как у вас были ошибки)</b></i>";
                        } else {
                            int currentRecord = Integer.parseInt(DBSpecies.getUserMedValue(tmpId, "emx"));
                            if (currentRecord < resultSeconds)
                                if (resultSeconds >= 120) {
                                    DBSpecies.updateUserMed(tmpId, "emed", "бронза");
                                } else if (resultSeconds >= 90) {
                                    DBSpecies.updateUserMed(tmpId, "emed", "серебро");
                                } else if (resultSeconds <= 60) {
                                    DBSpecies.updateUserMed(tmpId, "emed", "золото");
                                }
                                DBSpecies.updateUserMed(tmpId, "emx", resultSeconds);
                                DBSpecies.updateStatValue(tmpId, resultSeconds);
                        }

                        App.logger.log(Level.INFO, String.format(
                                "User [%d] was finished easy game with results(t/a/e) %d/%d/%d",
                                tmpId, resultSeconds, TripleExpression.getExpressionDefaultQuantity(), errors));

                        DBSpecies.updateUser(tmpId, "time",
                                (Integer.parseInt(DBSpecies.getUserValue(tmpId, "time")) + resultSeconds));

                        SendMessage message2 = BotMethods.makeMessage(tmpId, resultString);
                        message2.enableHtml(true);

                        App.bot.execute(message2);
                    }
                }
            }
        }
    }
}
