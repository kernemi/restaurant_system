package RS.Chat;

import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.*;

public class RMIServerGUI extends JFrame {
    private final JTextArea logArea;

    public RMIServerGUI() {
        setTitle("RSBot RMI Server");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBackground(new Color(60, 30, 10));
        logArea.setForeground(new Color(255, 215, 0));
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        add(new JScrollPane(logArea), BorderLayout.CENTER);

        setVisible(true);
        startRMIServer();
    }

    private void startRMIServer() {
        new Thread(() -> {
            try {
                Registry registry = LocateRegistry.createRegistry(1099);
                RemoteChatServiceImpl chatService = new RemoteChatServiceImpl();
                registry.rebind("ChatService", chatService);
                log("RMI ChatBot server is running on port 1099...");
            } catch (Exception e) {
                log("Failed to start RMI server: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    private void log(String msg) {
        SwingUtilities.invokeLater(() -> logArea.append(msg + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RMIServerGUI::new);
    }
}
