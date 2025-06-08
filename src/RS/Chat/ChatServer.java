package RS.Chat;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import javax.swing.*;

public class ChatServer extends JFrame {
    private final JTextArea chatArea;
    private final JTextField inputField;
    private final JButton sendButton;

    private ServerSocket serverSocket;
    private final ExecutorService pool = Executors.newCachedThreadPool();

    public ChatServer(int port) {
        setTitle("RSchatBot(Restaurant Assistant)");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        new Thread(() -> startServer(port)).start();

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());
    }

    private void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            appendMessage("RSbot started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                appendMessage("Customer connected: " + clientSocket.getInetAddress());
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            appendMessage("Server error: " + e.getMessage());
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
            appendMessage("RSbot: " + msg);
            inputField.setText("");
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private BufferedWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            } catch (IOException e) {
                appendMessage("Error setting up customer: " + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    appendMessage("customer: " + message);
                    String response = answer(message);
                    out.write(response);
                    out.newLine();
                    out.flush();
                    appendMessage("RSbot: " + response);
                }
            } catch (IOException e) {
                appendMessage("Customer disconnected: " + socket.getInetAddress());
            } finally {
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
        }

        private String answer(String msg) {
            msg = msg.toLowerCase();

            if (msg.contains("menu"))
                return "You can browse the menu from the dashboard. We offer a variety of delicious meals and snacks.";
            
            if (msg.contains("price"))
                return "Prices vary by item. Meals start from $5 USD. We have combo offers too!";
            
            if (msg.contains("location") || msg.contains("located"))
                return "We are located at: AASTU, Addis Ababa";
            
            if (msg.contains("hi") || msg.contains("hello"))
                return "Hello, our king customer how can i asist you today ";

            if (msg.contains("open") || msg.contains("time") || msg.contains("hours"))
                return "We’re open every day from 8 AM to 10 PM.";

            if (msg.contains("who are you") || msg.contains("your name"))
                return "I'm RSBot, your virtual assistant for FeastFlow Restaurant Management System.";

            if (msg.contains("owner"))
                return "The restaurant is owned and managed by Professor Kernemi.";

            if (msg.contains("help") || msg.contains("assist"))
                return "I can help you with information about the menu, prices, opening hours, location, or how to place an order.";

            if (msg.contains("contact"))
                return "You can reach us at +1-555-123-4567 or email support@feastflow.com.";

            if (msg.contains("services"))
                return "We offer dine-in, takeout, and delivery services.";

            if (msg.contains("thank") || msg.contains("thanks"))
                return "You're welcome! Happy to assist ";

            return "Sorry, I didn't understand that. Try asking about menu, price, location, time, help, or contact.";
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatServer server = new ChatServer(12345);
            server.setVisible(true);
        });
    }
}
