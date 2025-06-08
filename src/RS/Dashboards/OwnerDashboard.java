package RS.Dashboards;

import RS.Services.ViewIngredients;
import RS.UI.CustomStyle;
import java.awt.*;
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

        CustomStyle.styleButton(profitBtn);
        CustomStyle.styleButton(inputBtn);
        CustomStyle.styleButton(manageBtn);

        profitBtn.addActionListener(e -> CustomStyle.showCustomMessage(this, "Profit Summary", "Total profit this month: $5,000"));
        inputBtn.addActionListener(e -> new ViewIngredients());
        manageBtn.addActionListener(e -> new RS.Services.ViewStaffInfo());

        centerPanel.add(profitBtn);
        centerPanel.add(inputBtn);
        centerPanel.add(manageBtn);

        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(25, 20, 20));

        JButton backBtn = new JButton("⬅ Back");
        CustomStyle.styleButton(backBtn);
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
}
