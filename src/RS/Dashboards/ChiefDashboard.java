package RS.Dashboards;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChiefDashboard extends JFrame {
    private String chiefName;

    public ChiefDashboard(String chiefName) {
        this.chiefName = chiefName;
        setTitle("Chief Dashboard - Welcome " + chiefName);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Chief Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        centerPanel.setBackground(Color.WHITE);

        JButton menuBtn = new JButton("🍽 View Menu Items");
        JButton feedbackBtn = new JButton("💬 View Customer Feedback");
        JButton ordersBtn = new JButton("📋 View Orders");

        styleButton(menuBtn);
        styleButton(feedbackBtn);
        styleButton(ordersBtn);

        menuBtn.addActionListener(e ->
                showMessage("Menu Items", "1. Burger\n2. Pasta\n3. Salad\n4. Pizza"));

        feedbackBtn.addActionListener(e ->
                showMessage("Customer Feedback", "1. 'Great food!'\n2. 'Too salty'\n3. 'Loved the burger!'"));

        ordersBtn.addActionListener(e ->
                showMessage("Order List", "1. #101 - Burger x2\n2. #102 - Salad x1, Pasta x1"));

        centerPanel.add(menuBtn);
        centerPanel.add(feedbackBtn);
        centerPanel.add(ordersBtn);

        panel.add(centerPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel("Logged in as: Chef " + chiefName, SwingConstants.RIGHT);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 12));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
        panel.add(footer, BorderLayout.SOUTH);

        add(panel);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(new Color(46, 139, 87)); // SeaGreen
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(34, 120, 70));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(46, 139, 87));
            }
        });
    }

    private void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
