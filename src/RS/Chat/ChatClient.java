package RS.Chat;

import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class ChatClient extends JFrame {
    private final JTextArea chatArea;
    private final JTextField inputField;
    private final JButton sendButton;

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public ChatClient(String serverIP, int serverPort) {
        setTitle("Welcome to ChatBot Assistant");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Color darkBrown = new Color(60, 30, 10);
        Color gold = new Color(255, 215, 0);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(darkBrown);
        chatArea.setForeground(gold);
        chatArea.setFont(new Font("Serif", Font.PLAIN, 16));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBackground(darkBrown);

        inputField = new JTextField();
        inputField.setBackground(darkBrown);
        inputField.setForeground(gold);
        inputField.setCaretColor(gold);

        sendButton = new JButton("Send");
        sendButton.setBackground(darkBrown);
        sendButton.setForeground(gold);
        sendButton.setFocusPainted(false);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBackground(darkBrown);
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        new Thread(() -> connectToServer(serverIP, serverPort)).start();

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());
    }

    private void connectToServer(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            appendMessage("Connected.Welcome what can I help you today?");

            String msg;
            while ((msg = in.readLine()) != null) {
                appendMessage("RSbot: " + msg);
            }
        } catch (IOException e) {
            appendMessage("Connection error: " + e.getMessage());
        }
    }

    private synchronized void appendMessage(String msg) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(msg + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }

    private void sendMessage() {
        String msg = inputField.getText().trim();
        if (!msg.isEmpty()) {
            try {
                out.write(msg);
                out.newLine();
                out.flush();
                appendMessage("You: " + msg);
                inputField.setText("");
            } catch (IOException e) {
                appendMessage("Failed to send message.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatClient client = new ChatClient("127.0.0.1", 12345);
            client.setVisible(true);
        });
    }
}
