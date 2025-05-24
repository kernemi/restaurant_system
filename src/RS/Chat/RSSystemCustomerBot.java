//it is not working so it isnot integrated
package RS.Chat;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class RSSystemCustomerBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "RSSystemCustomerBot";
    }

    @Override
    public String getBotToken() {
        return "YOUR_ACTUAL_BOT_TOKEN_HERE";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            String response;

            switch (text.toLowerCase()) {
                case "/start", "/help" -> response = """
                        🙋 Welcome to RS System Bot!
                        Use these commands:
                        /menu 🍝
                        /price 💰
                        /contact ☎️
                        /location 📍
                        """;

                case "/menu" -> response = """
                        🍽️ Available Dishes:
                        - Pizza
                        - Pasta
                        - Salad
                        - Dessert
                        """;

                case "/price" -> response = """
                        💰 Prices:
                        Meals: 120–350 ETB
                        Desserts: 60–150 ETB
                        Drinks: 40–90 ETB
                        """;

                case "/contact" -> response = """
                        ☎️ Contact Us:
                        Phone: +251-912-345678
                        Email: support@rssystem.com
                        IG: @rs_restaurant
                        """;

                case "/location" -> response = """
                        📍 We are located at:
                        Bole, Addis Ababa
                        Near Friendship Mall
                        """;

                default -> response = "❓ Unknown command. Try /help";
            }

            try {
                execute(new SendMessage(chatId, response));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
