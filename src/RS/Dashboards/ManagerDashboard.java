package RS.Dashboards;

import RS.UI.CustomStyle;
import java.awt.*;
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

        CustomStyle.styleButton(addItemBtn);
        CustomStyle.styleButton(updateItemBtn);
        CustomStyle.styleButton(deleteItemBtn);
        CustomStyle.styleButton(viewMenuBtn);
        CustomStyle.styleButton(billingBtn);
        CustomStyle.styleButton(transactionBtn);

        addItemBtn.addActionListener(e -> new RS.Services.EnterFood());
        updateItemBtn.addActionListener(e -> new RS.Services.UpdateFood());
        deleteItemBtn.addActionListener(e -> new RS.Services.DeleteFood());
        viewMenuBtn.addActionListener(e -> new RS.Services.ViewFood());
        billingBtn.addActionListener(e -> CustomStyle.showCustomMessage(this,"Billing Info", "Today's total: $1,230\nMonthly: $22,000"));
        transactionBtn.addActionListener(e -> CustomStyle.showCustomMessage(this,"Transactions", "Transaction log goes here."));

        centerPanel.add(addItemBtn);
        centerPanel.add(updateItemBtn);
        centerPanel.add(deleteItemBtn);
        centerPanel.add(viewMenuBtn);
        centerPanel.add(billingBtn);
        centerPanel.add(transactionBtn);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(25, 20, 20));

        JButton backBtn = new JButton("⬅ Back");
        CustomStyle.styleButton(backBtn);
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
}
