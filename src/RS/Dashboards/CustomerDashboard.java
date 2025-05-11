package RS.Dashboards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomerDashboard extends JFrame {
    private String customerName;

    public CustomerDashboard(String customerName) {
        this.customerName = customerName;
        setTitle("Customer Dashboard - Welcome " + customerName);
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Customer Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        centerPanel.setBackground(Color.WHITE);

        JButton browseMenuBtn = new JButton("📋 Browse Menu");
        JButton feedbackBtn = new JButton("💬 Give Feedback");
        JButton orderBtn = new JButton("🛒 Place Order");
        JButton chatbotBtn = new JButton("🤖 Chat with Assistant");

        styleButton(browseMenuBtn);
        styleButton(feedbackBtn);
        styleButton(orderBtn);
        styleButton(chatbotBtn);

        browseMenuBtn.addActionListener(e -> showMessage("Menu", "Pizza\nBurger\nPasta\nDrinks..."));
        feedbackBtn.addActionListener(e -> giveFeedback());
        orderBtn.addActionListener(e -> showMessage("Place Order", "Redirecting to order page..."));
        chatbotBtn.addActionListener(e -> showMessage("Chatbot", "How can I assist you today?"));

        centerPanel.add(browseMenuBtn);
        centerPanel.add(feedbackBtn);
        centerPanel.add(orderBtn);
        centerPanel.add(chatbotBtn);

        panel.add(centerPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel("Logged in as: " + customerName, SwingConstants.RIGHT);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 12));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
        panel.add(footer, BorderLayout.SOUTH);

        add(panel);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(new Color(46, 204, 113));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(39, 174, 96));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(46, 204, 113));
            }
        });
    }

    private void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void giveFeedback() {
        String feedback = JOptionPane.showInputDialog(this, "Please write your feedback:");
        if (feedback != null && !feedback.trim().isEmpty()) {
            // Here you can save feedback to DB if needed
            JOptionPane.showMessageDialog(this, "Thank you for your feedback!");
        } else {
            JOptionPane.showMessageDialog(this, "No feedback given.");
        }
    }
}
