package com.nkvl.app.switchers;

import com.nkvl.app.App;
import com.nkvl.app.bot.BotMethods;
import com.nkvl.app.classes.Wasted;
import com.nkvl.app.classes.expressions.TripleExpression;
import com.nkvl.app.classes.expressions.UnitExpression;
import com.nkvl.app.database.DBSpecies;
import com.nkvl.app.keyboards.Inline;
import org.apache.log4j.Level;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;


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
                App.store.genTripleExpressionSession(tmpId);

                TripleExpression tripleExpressionInstance =
                        App.store.tripleExpTable.get(tmpId)[App.store.resTable.get(tmpId).getExpPos()];
                SendMessage message2 = BotMethods.makeMessage(tmpId, "•••••••   "
                        + tripleExpressionInstance.getText() + " =");
                message2.setReplyMarkup(Inline.tripleExpGet(tripleExpressionInstance.getAnswers()));
                App.bot.execute(message2);
            }
            case "start_h" -> {
                App.store.genUnitExpressionSession(tmpId);

                SendMessage tableMessage = BotMethods.makeMessage(tmpId,
                        UnitExpression.unitSymbolsPrettyTable(App.store.unitSymbolsTable.get(tmpId)));

                App.bot.execute(tableMessage);

                UnitExpression unitExpressionInstance =
                        App.store.unitExpTable.get(tmpId)[App.store.resTable.get(tmpId).getExpPos()];
                SendMessage message2 = BotMethods.makeMessage(tmpId, "•••••••   "
                        + unitExpressionInstance.toSymvolic(App.store.unitSymbolsTable.get(tmpId)) + " = ");
                message2.setReplyMarkup(Inline.unitExpGet(unitExpressionInstance.getAnswers()));
                App.bot.execute(message2);
            }
            default -> {
                if (data.contains("!e")) {
                    if (!(App.store.resTable.get(tmpId).getExpPos() ==
                            TripleExpression.getExpressionDefaultQuantity() - 1)) {
                        int result = Integer.parseInt(data.replace("!e", ""));
                        App.store.resTable.get(tmpId).newResult(result);

                        TripleExpression tripleExpressionInstance =
                                App.store.tripleExpTable.get(tmpId)[App.store.resTable.get(tmpId).getExpPos()];
                        SendMessage message2 = BotMethods.makeMessage(tmpId, "•••••••   "
                                + tripleExpressionInstance.getText() + " =");
                        message2.setReplyMarkup(Inline.unitExpGet(tripleExpressionInstance.getAnswers()));
                        App.bot.execute(message2);
                    } else {
                        int result = Integer.parseInt(data.replace("!e", ""));
                        App.store.resTable.get(tmpId).newResult(result);

                        App.store.resTable.get(tmpId).setEnd(System.nanoTime());

                        int errors = TripleExpression.checkErrorsOfStorageVaultAndResTable(tmpId, App.store);
                        int resultSeconds = App.store.resTable.get(tmpId).getTotalTime();

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
                                if (resultSeconds <= 60) {
                                    DBSpecies.updateUserMed(tmpId, "emed", "золото");
                                } else if (resultSeconds <= 90) {
                                    DBSpecies.updateUserMed(tmpId, "emed", "серебро");
                                } else if (resultSeconds <= 120) {
                                    DBSpecies.updateUserMed(tmpId, "emed", "бронза");
                                } else {
                                    DBSpecies.updateUserMed(tmpId, "emed", "дерево");
                                }
                                DBSpecies.updateUserMed(tmpId, "emx", resultSeconds);
                                DBSpecies.updateStatValue(tmpId, resultSeconds, "easy");
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
                } else if (data.contains("!h")) {
                    if (!(App.store.resTable.get(tmpId).getExpPos() ==
                            UnitExpression.getExpressionDefaultQuantity() - 1)) {
                        int result = Integer.parseInt(data.replace("!h", ""));
                        App.store.resTable.get(tmpId).newResult(result);

                        UnitExpression unitExpressionInstance =
                                App.store.unitExpTable.get(tmpId)[App.store.resTable.get(tmpId).getExpPos()];
                        SendMessage message2 = BotMethods.makeMessage(tmpId, "•••••••   "
                                + unitExpressionInstance.toSymvolic(App.store.unitSymbolsTable.get(tmpId)) + " =");
                        message2.setReplyMarkup(Inline.unitExpGet(unitExpressionInstance.getAnswers()));
                        App.bot.execute(message2);
                    } else {
                        int result = Integer.parseInt(data.replace("!h", ""));
                        App.store.resTable.get(tmpId).newResult(result);

                        App.store.resTable.get(tmpId).setEnd(System.nanoTime());

                        int errors = UnitExpression.checkErrorsOfStorageVaultAndResTable(tmpId, App.store);
                        int resultSeconds = App.store.resTable.get(tmpId).getTotalTime();

                        String resultString = String.format(
                                """
                                <i><b>Результаты испытания:</b></i>
                                Количество задач/ошибок - %s
                                Время - %s
                                
                                """,
                                UnitExpression.getExpressionDefaultQuantity() + "/" + errors,
                                Wasted.transferFromSeconds(resultSeconds));

                        if (errors > 0) {
                            resultString += "\n<i><b>(Этот результат не будет засчитан, " +
                                    "так как у вас были ошибки)</b></i>";
                        } else {
                            int currentRecord = Integer.parseInt(DBSpecies.getUserMedValue(tmpId, "hmx"));
                            if (currentRecord < resultSeconds)
                                if (resultSeconds <= 60) {
                                    DBSpecies.updateUserMed(tmpId, "hmed", "золото");
                                } else if (resultSeconds <= 90) {
                                    DBSpecies.updateUserMed(tmpId, "hmed", "серебро");
                                } else if (resultSeconds <= 120) {
                                    DBSpecies.updateUserMed(tmpId, "hmed", "бронза");
                                } else {
                                    DBSpecies.updateUserMed(tmpId, "hmed", "дерево");
                                }
                            DBSpecies.updateUserMed(tmpId, "hmx", resultSeconds);
                            DBSpecies.updateStatValue(tmpId, resultSeconds, "hard");
                        }

                        App.logger.log(Level.INFO, String.format(
                                "User [%d] was finished hard game with results(t/a/e) %d/%d/%d",
                                tmpId, resultSeconds, UnitExpression.getExpressionDefaultQuantity(), errors));

                        DBSpecies.updateUser(tmpId, "time",
                                (Integer.parseInt(DBSpecies.getUserValue(tmpId, "time")) + resultSeconds));

                        SendMessage message2 = BotMethods.makeMessage(tmpId, resultString);
                        message2.enableHtml(true);

                        App.bot.execute(message2);
                    }
                } else if (data.contains("help:")) {
                    String text;
                    switch (data.replace("help:", "")) {
                        case "how_it_works" -> text = "Этот бот создан по мотивам техники развития мозга Рюты " +
                                "Кавашимы. Принцип работы " +
                                "состоит в решении простых примеров на скорость с невероятным желанием " +
                                "побить предыдущий рекорд";
                        case "about_medals" -> text = """
                                Всего есть три медали.

                                1. <b>Золотая</b> <i>(1 мин)</i>: результат, которого достигают люди, хорошо умеющие считать в уме или на счетах. Способности к счету — великолепные.
                                2. <b>Серебряная</b> <i>(1 мин 30 с)</i>: результат, которого можно достичь, если как следует сконцентрироваться на задаче. В умении считать вы мало кому уступаете. Есть определенный талант к счету.
                                3. <b>Бронзовая</b> <i>(2 мин)</i>: результат, доступный каждому при должном старании. Уровень способностей к счету — хороший.

                                Деревянная медаль присуждается для всех результатов ниже.""";
                        default -> text = "";
                    }
                    EditMessageText editedMessage = new EditMessageText();
                    editedMessage.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                    editedMessage.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    editedMessage.setText(text);
                    editedMessage.setReplyMarkup(Inline.get("help"));
                    editedMessage.enableHtml(true);
                    try {
                        App.bot.execute(editedMessage);
                    } catch (TelegramApiRequestException ex) {
                        App.logger.log(Level.WARN, String.format("Message [%d] dublicates new edited text",
                                update.getCallbackQuery().getMessage().getMessageId()));
                    }

                }
            }
        }
    }
}
