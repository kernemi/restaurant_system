public import java.util.*;

public class FAQHandler {
    private Map<String, List<String>> faqMap;

    public FAQHandler() {
        faqMap = new HashMap<>();
        loadFAQs();
    }

    private void loadFAQs() {
        faqMap.put("what is your name", List.of("I'm ChatBot!"));
        faqMap.put("how are you", List.of("I'm great, thanks for asking!"));
        faqMap.put("what do you do", List.of("I answer FAQs. Try me!"));
    }

    public List<String> getAnswers(String question) {
        return faqMap.getOrDefault(question.toLowerCase(), List.of("Sorry, I don’t know that yet."));
    }
}
 
