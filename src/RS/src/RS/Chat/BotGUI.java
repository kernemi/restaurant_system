import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BotGUI {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField;
    private Chatbot chatbot;

    public BotGUI() {
        chatbot = new Chatbot();

        frame = new JFrame("ChatBotAssitant");
        chatArea = new JTextArea();
        inputField = new JTextField();

        chatArea.setEditable(false);
        inputField.addActionListener(e -> {
            String user = inputField.getText();
            chatArea.append("You: " + user + "\n");
            String response = chatbot.getResponse(user);
            chatArea.append("Bot: " + response + "\n\n");
            inputField.setText("");
        });

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        frame.add(inputField, BorderLayout.SOUTH);
        frame.setSize(400, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
