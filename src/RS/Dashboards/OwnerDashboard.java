package RS.Dashboards;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class OwnerDashboard extends JFrame {
    private final String ownerName;

    public OwnerDashboard(String ownerName) {
        this.ownerName = ownerName;
        setTitle("Owner Dashboard - Welcome " + ownerName);
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

        JLabel titleLabel = new JLabel("Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        centerPanel.setBackground(Color.WHITE);

        JButton profitBtn = new JButton("📈 View Profit Summary");
        JButton inputBtn = new JButton("📦 View Item Inputs");
        JButton manageBtn = new JButton("⚙️ Manage System");

        styleButton(profitBtn);
        styleButton(inputBtn);
        styleButton(manageBtn);

        profitBtn.addActionListener(e -> showMessage("Profit Summary", "Total profit this month: $5,000"));
        inputBtn.addActionListener(e -> showMessage("Item Inputs", "Flour: 100kg\nSugar: 50kg\nOil: 30L"));
        manageBtn.addActionListener(e -> showMessage("System Management", "Redirecting to management panel..."));

        centerPanel.add(profitBtn);
        centerPanel.add(inputBtn);
        centerPanel.add(manageBtn);

        panel.add(centerPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel("Logged in as: " + ownerName, SwingConstants.RIGHT);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 12));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
        panel.add(footer, BorderLayout.SOUTH);

        add(panel);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 100, 220));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 123, 255));
            }
        });
    }

    private void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
