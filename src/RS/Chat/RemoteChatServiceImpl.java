package RS.Chat;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class RemoteChatServiceImpl extends UnicastRemoteObject implements RemoteChatService {

    protected RemoteChatServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String sendMessage(String message) throws RemoteException {
        message = message.toLowerCase();

        if (message.contains("menu"))
            return "You can browse the menu from the dashboard. We offer a variety of delicious meals and snacks.";

        if (message.contains("price"))
            return "Prices vary by item. Meals start from $5 USD. We have combo offers too!";

        if (message.contains("location") || message.contains("located"))
            return "We are located at: AASTU, Addis Ababa";

        if (message.contains("hi") || message.contains("hello"))
            return "Hello, our king customer. How can I assist you today?";

        if (message.contains("open") || message.contains("time") || message.contains("hours"))
            return "We’re open every day from 8 AM to 10 PM.";

        if (message.contains("who are you") || message.contains("your name"))
            return "I'm RSBot, your virtual assistant for FeastFlow Restaurant Management System.";

        if (message.contains("owner"))
            return "The restaurant is owned and managed by Professor Kernemi.";

        if (message.contains("help") || message.contains("assist"))
            return "I can help you with info about the menu, prices, opening hours, location, or placing an order.";

        if (message.contains("contact"))
            return "You can reach us at +1-555-123-4567 or email support@feastflow.com.";

        if (message.contains("services"))
            return "We offer dine-in, takeout, and delivery services.";

        if (message.contains("thank") || message.contains("thanks"))
            return "You're welcome! Happy to assist.";

        return "Sorry, I didn't understand that. Try asking about menu, price, location, time, help, or contact.";
    }
}
