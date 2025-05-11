package RS.Dashboards;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ManagerDashboard extends JFrame {
    private String managerName;

    public ManagerDashboard(String managerName) {
        this.managerName = managerName;
        setTitle("Manager Dashboard - Welcome " + managerName);
        setSize(650, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Manager Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        centerPanel.setBackground(Color.WHITE);

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

        addItemBtn.addActionListener(e -> showMessage("Add Item", "Feature to add menu items."));
        updateItemBtn.addActionListener(e -> showMessage("Update Item", "Feature to update menu items."));
        deleteItemBtn.addActionListener(e -> showMessage("Delete Item", "Feature to delete menu items."));
        viewMenuBtn.addActionListener(e -> showMessage("View Menu", "Feature to display current menu."));
        billingBtn.addActionListener(e -> showMessage("Billing Info", "Today's total: $1,230\nMonthly: $22,000"));
        transactionBtn.addActionListener(e -> showMessage("Transactions", "Transaction log goes here."));

        centerPanel.add(addItemBtn);
        centerPanel.add(updateItemBtn);
        centerPanel.add(deleteItemBtn);
        centerPanel.add(viewMenuBtn);
        centerPanel.add(billingBtn);
        centerPanel.add(transactionBtn);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel("Logged in as: " + managerName, SwingConstants.RIGHT);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 12));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
        mainPanel.add(footer, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        button.setBackground(new Color(40, 167, 69)); // greenish
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(30, 140, 60));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(40, 167, 69));
            }
        });
    }

    private void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
