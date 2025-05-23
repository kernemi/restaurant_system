import java.util.List;

public class Chatbot {
    private FAQHandler handler;

    public Chatbot() {
        handler = new FAQHandler();
    }

    public String getResponse(String userInput) {
        List<String> responses = handler.getAnswers(userInput);
        return responses.get(0); // Only return the first answer
    }
}
