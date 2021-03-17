package com.nkvl.app.switchers;

import com.nkvl.app.App;
import com.nkvl.app.database.DBSpecies;
import com.nkvl.app.keyboards.Buttons;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public final class TextMessageSwitcher {
    public static void send(Update update) throws TelegramApiException {
        String text = update.getMessage().getText();

        SendMessage answer = new SendMessage();
        answer.enableMarkdown(true);
        answer.setChatId(update.getMessage().getChatId().toString());

        switch (text) {
            // Команды
            case "/start" -> {
                DBSpecies.createUser(
                        update.getMessage().getChatId(),
                        update.getMessage().getFrom().getFirstName(),
                        update.getMessage().getFrom().getUserName());
                answer.setText("Добро пожаловать в Brup!");
                Buttons.set(answer, "main");
            }
            case "/so" -> answer.setText("Ну?...");
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
                answer.setText("Профильное сообщение...");
                Buttons.set(answer, "mdback");
            }
            case "Обычный режим" -> {
                answer.setText("Обычный режим...");
                Buttons.set(answer, "chback");
            }
            case "Усложнённый режим" -> {
                answer.setText("Усложнённый режим...");
                Buttons.set(answer, "chback");
            }
        }

        App.bot.execute(answer);
    }
}
