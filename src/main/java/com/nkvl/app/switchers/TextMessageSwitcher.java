package com.nkvl.app.switchers;

import com.nkvl.app.App;
import com.nkvl.app.classes.Chart;
import com.nkvl.app.classes.Wasted;
import com.nkvl.app.classes.expressions.UnitExpression;
import com.nkvl.app.database.DBSpecies;
import com.nkvl.app.keyboards.Buttons;
import com.nkvl.app.keyboards.Inline;
import org.apache.log4j.Level;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

import static com.nkvl.app.App.logger;

public final class TextMessageSwitcher {
    public static void send(Update update) throws TelegramApiException, IOException {
        String text = update.getMessage().getText();

        SendMessage answer = new SendMessage();
        answer.enableHtml(true);
        answer.setChatId(update.getMessage().getChatId().toString());

        switch (text) {
            // Команды
            case "/start" -> {
                if (DBSpecies.isUserExist(update.getMessage().getChatId()))
                    answer.setText("<i>Снова здравствуйте!</i>");
                else {
                    DBSpecies.createUser(
                            update.getMessage().getChatId(),
                            update.getMessage().getFrom().getFirstName() +
                                    (update.getMessage().getFrom().getLastName() == null ?
                                            "" : " " + update.getMessage().getFrom().getLastName()),
                            update.getMessage().getFrom().getUserName() == null ? "anon" :
                                update.getMessage().getFrom().getUserName());
                    answer.setText("<i>Добро пожаловать в Brup!</i>\nВаш аккаунт успешно зарегистрирован.");
                }
                Buttons.set(answer, "main");
            }
            case "/test" -> {
                answer.setText("Tested to logger.");
            }
            case "/send_symbols" -> answer.setText(UnitExpression.getSymbols());
            // Меню
            case "Испытания" -> {
                answer.setText(String.format("Открытие [%s]", text));
                Buttons.set(answer, "chall");
            }
            case "Мои данные" -> {
                answer.setText(String.format("Открытие [%s]", text));
                Buttons.set(answer, "mydata");
            }
            case "Назад" -> {
                answer.setText("Открытие [Главное меню]");
                Buttons.set(answer, "main");
            }
            case "Назад в [Мои данные]" -> {
                answer.setText("Открытие [Мои данные]");
                Buttons.set(answer, "mydata");
            }
            case "Назад в [Испытания]" -> {
                answer.setText("Открытие [Испытания]");
                Buttons.set(answer, "chall");
            }
            // Другие сообщения
            case "Статистика" -> {
                answer.setText("Выберите один из вариантов:");
                answer.setReplyMarkup(Inline.get("stat"));
            }
            case "Профиль" -> {
                answer.setText(String.format(
                        """
                        <i><b>Имя:</b></i> <i>%s</i>
                        <i><b>Ник:</b></i> <i>@%s</i>
                        <i><b>Время:</b></i> <i>%s</i>
                        <i><b>Медали:</b></i>
                            <i><b>Обычный режим:</b></i> <i>%s</i>
                            <i><b>Усложнённый режим:</b></i> <i>%s</i>
                        <i><b>Рекорды:</b></i>
                            <i><b>Обычный режим:</b></i> <i>%s</i>
                            <i><b>Усложнённый режим:</b></i> <i>%s</i>    
                        """,
                        update.getMessage().getFrom().getFirstName() +
                                (update.getMessage().getFrom().getLastName() == null ?
                                        "" : " " + update.getMessage().getFrom().getLastName()),
                        update.getMessage().getFrom().getUserName() == null ? "anon" :
                            update.getMessage().getFrom().getUserName(),
                        Wasted.transferFromSeconds(Integer.parseInt(DBSpecies.getUserValue(
                                update.getMessage().getChatId(), "time"))),
                        DBSpecies.getUserMedValue(update.getMessage().getChatId(), "emed"),
                        DBSpecies.getUserMedValue(update.getMessage().getChatId(), "hmed"),
                        Wasted.transferFromSeconds(Integer.parseInt(DBSpecies.getUserMedValue(
                                update.getMessage().getChatId(), "emx"))),
                        Wasted.transferFromSeconds(Integer.parseInt(DBSpecies.getUserMedValue(
                                update.getMessage().getChatId(), "hmx")))
                ));
                Buttons.set(answer, "mdback");
            }
            case "Обычный режим" -> {
                answer.setText("<i>Вы готовы начать игру?</i>");
                logger.log(Level.INFO, String.format("User [%d] is preparing for easy game",
                        update.getMessage().getChatId()));
                answer.setReplyMarkup(Inline.get("start_easy_confirm"));
            }
            case "Помощь" -> {
                answer.setText("Выберите вопрос:");
                answer.setReplyMarkup(Inline.get("help"));
            }
            case "Усложнённый режим" -> {
                answer.setText("<i>Вы готовы начать игру?\nПосле подтверждения вам будет показана символьная таблица, " +
                        "старайтесь не смотреть на неё во время игры.</i>");
                logger.log(Level.INFO, String.format("User [%d] is preparing for hard game",
                        update.getMessage().getChatId()));
                answer.setReplyMarkup(Inline.get("start_hard_confirm"));
            }
        }
        App.bot.execute(answer);
    }
}


