package RS.Dashboards;

import RS.Services.ViewIngredients;
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
        panel.setBackground(new Color(25, 20, 20)); // dark brown background

        JLabel titleLabel = new JLabel("Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(new Color(255, 204, 102)); // gold text
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        centerPanel.setBackground(new Color(25, 20, 20));

        JButton profitBtn = new JButton("📈 View Profit Summary");
        JButton inputBtn = new JButton("📦 View Item Inputs");
        JButton manageBtn = new JButton("⚙️ Manage System");

        styleButton(profitBtn);
        styleButton(inputBtn);
        styleButton(manageBtn);

        profitBtn.addActionListener(e -> showMessage("Profit Summary", "Total profit this month: $5,000"));
        inputBtn.addActionListener(e -> new ViewIngredients());
        manageBtn.addActionListener(e -> showMessage("System Management", "Redirecting to management panel..."));

        centerPanel.add(profitBtn);
        centerPanel.add(inputBtn);
        centerPanel.add(manageBtn);

        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(25, 20, 20));

        JButton backBtn = new JButton("⬅ Back");
        styleButton(backBtn);
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        backBtn.addActionListener(e -> {
            new RS.Main.WelcomePage();
            this.dispose();
        });

        JLabel footer = new JLabel("👤 Logged in as: " + ownerName, SwingConstants.RIGHT);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 12));
        footer.setForeground(Color.LIGHT_GRAY);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));

        footerPanel.add(backBtn, BorderLayout.WEST);
        footerPanel.add(footer, BorderLayout.EAST);

        panel.add(footerPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        button.setBackground(new Color(255, 204, 102)); 
        button.setForeground(new Color(30, 20, 10));    
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(new Color(120, 90, 40))); 
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 220, 130));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 204, 102));
            }
        });
    }

    private void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
