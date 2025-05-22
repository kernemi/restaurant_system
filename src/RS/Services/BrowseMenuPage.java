package RS.Services;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class BrowseMenuPage extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private String userName;
    private final int userId;
    private String role;

    public BrowseMenuPage(int userId, String userName, String role) {
        this.userId = userId;
        this.userName = userName;
        this.role = role;
        setTitle("📋 Restaurant Menu");
        setSize(700, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top horizontal category bar with custom styled buttons
        JPanel categoryBar = new JPanel(new GridLayout(1, 4, 10, 0));
        categoryBar.setBackground(Color.BLACK);
        categoryBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] categories = {"Breakfast", "Lunch", "Desserts", "Drinks"};
        for (String category : categories) {
            RoundedButton button = new RoundedButton(category);
            button.addActionListener(new CategoryButtonListener());
            categoryBar.add(button);
        }

        mainPanel.add(categoryBar, BorderLayout.NORTH);

        // Card panel to switch between category images
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createImagePanel("breakfast.jpg"), "Breakfast");
        cardPanel.add(createImagePanel("lunch.jpg"), "Lunch");
        cardPanel.add(createImagePanel("dessert.jpg"), "Desserts");
        cardPanel.add(createImagePanel("drinks.jpg"), "Drinks");

        mainPanel.add(cardPanel, BorderLayout.CENTER);

        
        RoundedButton backButton = new RoundedButton("⬅ Back to Dashboard");
        backButton.addActionListener(e -> {
            dispose();
            switch (role.toLowerCase()) {
                case "customer":
                    new RS.Dashboards.CustomerDashboard(userId,userName);
                    break;
                // case "manager":
                //     new RS.Dashboards.ManagerDashboard(userId,userName);
                //     break;
                case "chief":
                    new RS.Dashboards.ChiefDashboard(userId,userName);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Unknown role: " + role);
                    break;
            }
        });

        mainPanel.add(backButton, BorderLayout.SOUTH);


        add(mainPanel);
        setVisible(true);
    }

    private JPanel createImagePanel(String imageName) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel();

        try {
            Image img = ImageIO.read(getClass().getResource("/RS/UI/" + imageName));
            Image scaledImg = img.getScaledInstance(700, 500, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImg));
        } catch (Exception ex) {
            System.out.println("❌ Could not load image: " + imageName);
            ex.printStackTrace();
        }

        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);
        return panel;
    }


    private class CategoryButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String category = ((JButton) e.getSource()).getText();
            cardLayout.show(cardPanel, category);
        }
    }

    // Reused RoundedButton class from CustomerDashboard
    static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setFont(new Font("SansSerif", Font.BOLD, 14));
            setFocusPainted(false);
            setForeground(Color.WHITE);
            setBackground(new Color(100, 60, 30));
            setOpaque(false);
            setContentAreaFilled(false);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent evt) {
                    setFont(new Font("SansSerif", Font.BOLD, 16));
                }

                @Override
                public void mouseExited(MouseEvent evt) {
                    setFont(new Font("SansSerif", Font.BOLD, 14));
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g);
            g2.dispose();
        }
    }
}
