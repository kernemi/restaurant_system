package RS.Chat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.*;

public class RMIClientGUI extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    private RemoteChatService chatService;

    public RMIClientGUI() {
        setTitle("RS ChatBot Client");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color darkBrown = new Color(60, 30, 10);
        Color gold = new Color(255, 215, 0);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(darkBrown);
        chatArea.setForeground(gold);
        chatArea.setFont(new Font("Serif", Font.PLAIN, 16));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        inputField = new JTextField();
        inputField.setBackground(darkBrown);
        inputField.setForeground(gold);
        inputField.setCaretColor(gold);

        sendButton = new JButton("Send");
        sendButton.setBackground(darkBrown);
        sendButton.setForeground(gold);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(darkBrown);
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(new JScrollPane(chatArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(this::sendMessage);
        inputField.addActionListener(this::sendMessage);

        connectToServer();
    }

    private void connectToServer() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            chatService = (RemoteChatService) registry.lookup("ChatService");
            appendMessage("Connected to RSBot. How can I help you today?");
        } catch (Exception e) {
            appendMessage("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendMessage(ActionEvent e) {
        String msg = inputField.getText().trim();
        if (!msg.isEmpty()) {
            appendMessage("You: " + msg);
            inputField.setText("");
            try {
                String reply = chatService.sendMessage(msg);
                appendMessage("RSBot: " + reply);
            } catch (Exception ex) {
                appendMessage("Error: " + ex.getMessage());
            }
        }
    }

    private void appendMessage(String msg) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(msg + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RMIClientGUI client = new RMIClientGUI();
            client.setVisible(true);
        });
    }
}
