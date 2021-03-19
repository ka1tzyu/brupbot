package com.nkvl.app.switchers;

import com.nkvl.app.App;
import com.nkvl.app.classes.Wasted;
import com.nkvl.app.database.DBSpecies;
import com.nkvl.app.keyboards.Buttons;
import com.nkvl.app.keyboards.Inline;
import org.apache.log4j.Level;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.nkvl.app.App.logger;

public final class TextMessageSwitcher {
    public static void send(Update update) throws TelegramApiException {
        String text = update.getMessage().getText();

        SendMessage answer = new SendMessage();
        answer.enableMarkdown(true);
        answer.setChatId(update.getMessage().getChatId().toString());

        switch (text) {
            // Команды
            case "/start" -> {
                if (DBSpecies.isUserExist(update.getMessage().getChatId()))
                    answer.setText("А я вас знаю!");
                else {
                    DBSpecies.createUser(
                            update.getMessage().getChatId(),
                            update.getMessage().getFrom().getFirstName() + " " +
                                    (update.getMessage().getFrom().getLastName() == null ?
                                            "" : update.getMessage().getFrom().getLastName()),
                            update.getMessage().getFrom().getUserName());
                    answer.setText("Добро пожаловать в Brup!");
                }
                Buttons.set(answer, "main");
            }
            case "/so" -> answer.setText("Ну?...");
            case "/st" -> answer.setText(DBSpecies.getStatByDate(1, "2021-03-18"));
            // Меню
            case "Испытания" -> {
                answer.setText(String.format("Открытие [[%s]]", text));
                Buttons.set(answer, "chall");
            }
            case "Мои данные" -> {
                answer.setText(String.format("Открытие [[%s]]", text));
                Buttons.set(answer, "mydata");
            }
            case "Назад" -> {
                answer.setText("Открытие [[Главное меню]]");
                Buttons.set(answer, "main");
            }
            case "Назад в [Мои данные]" -> {
                answer.setText("Открытие [[Мои данные]]");
                Buttons.set(answer, "mydata");
            }
            case "Назад в [Испытания]" -> {
                answer.setText("Открытие [[Испытания]]");
                Buttons.set(answer, "chall");
            }
            // Другие сообщения
            case "Статистика" -> {
                answer.setText("Статистическое сообщение...");
                Buttons.set(answer, "mdback");
            }
            case "Профиль" -> {
                answer.setText(String.format(
                        """
                                Имя: %s
                                Ник: @%s
                                Время: %s
                                Медали:
                                    Обычный режим: **%s**
                                    Усложнённый режим: **%s**
                                Рекорды:
                                    Обычный режим: %s
                                    Усложнённый режим: %s    
                                """,
                        update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName(),
                        update.getMessage().getFrom().getUserName(),
                        Wasted.transferFromSeconds(Integer.parseInt(DBSpecies.getUserValue(update.getMessage().getChatId(), "time"))),
                        DBSpecies.getUserMedValue(update.getMessage().getChatId(), "emed"),
                        DBSpecies.getUserMedValue(update.getMessage().getChatId(), "hmed"),
                        Wasted.transferFromSeconds(Integer.parseInt(DBSpecies.getUserMedValue(update.getMessage().getChatId(), "emx"))),
                        Wasted.transferFromSeconds(Integer.parseInt(DBSpecies.getUserMedValue(update.getMessage().getChatId(), "hmx")))
                ));
                Buttons.set(answer, "mdback");
            }
            case "Обычный режим" -> {
                answer.setText("Вы готовы начать игру?");
                logger.log(Level.INFO, "Easy mode entered!");
                answer.setReplyMarkup(Inline.get("start_e"));
            }
            case "Усложнённый режим" -> {
                answer.setText("Усложнённый режим...");
                Buttons.set(answer, "chback");
            }
        }
        App.bot.execute(answer);
    }
}


