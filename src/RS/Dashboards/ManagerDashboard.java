package RS.Dashboards;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ManagerDashboard extends JFrame {
    private String managerName;

    public ManagerDashboard(String managerName) {
        this.managerName = managerName;
        setTitle("🍽️ Manager Dashboard - Welcome " + managerName);
        setSize(650, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(25, 20, 20)); // dark theme

        JLabel titleLabel = new JLabel(managerName + "'s Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(new Color(255, 204, 102)); // soft gold
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        centerPanel.setBackground(new Color(25, 20, 20));

        JButton addItemBtn = new JButton("➕ Add Menu Item");
        JButton updateItemBtn = new JButton("✏️ Update Menu Item");
        JButton deleteItemBtn = new JButton("❌ Delete Menu Item");
        JButton viewMenuBtn = new JButton("📋 View Menu");
        JButton billingBtn = new JButton("💰 View Billing Info");
        JButton transactionBtn = new JButton("📊 View Transactions");

        styleButton(addItemBtn);
        styleButton(updateItemBtn);
        styleButton(deleteItemBtn);
        styleButton(viewMenuBtn);
        styleButton(billingBtn);
        styleButton(transactionBtn);

        addItemBtn.addActionListener(e -> new RS.Services.EnterFood());
        updateItemBtn.addActionListener(e -> new RS.Services.UpdateFood());
        deleteItemBtn.addActionListener(e -> new RS.Services.DeleteFood());
        viewMenuBtn.addActionListener(e -> new RS.Services.ViewFood());
        billingBtn.addActionListener(e -> showMessage("Billing Info", "Today's total: $1,230\nMonthly: $22,000"));
        transactionBtn.addActionListener(e -> showMessage("Transactions", "Transaction log goes here."));

        centerPanel.add(addItemBtn);
        centerPanel.add(updateItemBtn);
        centerPanel.add(deleteItemBtn);
        centerPanel.add(viewMenuBtn);
        centerPanel.add(billingBtn);
        centerPanel.add(transactionBtn);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Footer panel with Back button and logged in label
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(25, 20, 20));

        JButton backBtn = new JButton("⬅ Back");
        styleButton(backBtn);
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        backBtn.addActionListener(e -> {
            new RS.Main.WelcomePage();  
            this.dispose();    
        });

        JLabel footer = new JLabel("👤 Logged in as: " + managerName, SwingConstants.RIGHT);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 12));
        footer.setForeground(Color.LIGHT_GRAY);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));

        footerPanel.add(backBtn, BorderLayout.WEST);
        footerPanel.add(footer, BorderLayout.EAST);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
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
