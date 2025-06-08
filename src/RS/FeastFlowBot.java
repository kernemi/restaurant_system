package RS;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class FeastFlowBot extends TelegramLongPollingBot {

    private final String BOT_TOKEN = "your_token_here";
    private final String BOT_USERNAME = "your_bot_username_here"; 
    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (!message.getChat().isUserChat()) return; 
            String text = message.getText().trim();

            String response;

            switch (text) {
                case "/start":
                    response = "Welcome! to the feastflow restaurant bot. Use /menu, /price, or /location.";
                    break;
                case "/menu":
                    response = "🍽 Today's Menu:\n- Burger\n- Pizza\n- Salad\n- Sandwiches\n- Wraps";
                    break;
                case "/price":
                    response = "💲 Prices:\n- Burger: $12\n- Pizza: $15\n- Salad: $8\n- Sandwiches: $10\n- Wraps: $16";
                    break;
                case "/location":
                    response = "📍 We are located at: AASTU, Addis Ababa.";
                    break;
                default:
                    response = "You asked: " + text + ". Here's your answer from the bot!";
            }

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId().toString());
            sendMessage.setText(response);

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new FeastFlowBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
