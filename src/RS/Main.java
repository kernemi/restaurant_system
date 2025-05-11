package RS;

import RS.Auth.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(WelcomePage::new);
    }

    public static class WelcomePage extends JFrame {
        private Image bgImage;

        public WelcomePage() {
            setTitle("Restaurant Management System");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 500);
            setLocationRelativeTo(null);

            // Load background image
            try {
                bgImage = ImageIO.read(getClass().getResource("UI/restaurant.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Custom background panel
            JPanel background = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (bgImage != null) {
                        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                    }
                }
            };
            background.setLayout(new BorderLayout());
            setContentPane(background);

            // Bold title
            JLabel title = new JLabel("Welcome to Addis Restaurant APP", SwingConstants.CENTER);
            title.setFont(new Font("Arial", Font.BOLD, 45));
            title.setForeground(Color.BLACK);
            title.setBorder(BorderFactory.createEmptyBorder(40, 10, 20, 10));
            background.add(title, BorderLayout.NORTH);

            // Horizontal role buttons (now placed below the title)
            JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 20, 100));
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 270, 10)); // Adjust border to move buttons below title

            String[] roles = {"Owner", "Manager", "Chief", "Customer"};
            for (String role : roles) {
                JButton btn = new RoundedButton(role);
                btn.setPreferredSize(new Dimension(50, 40)); // Adjust button size to be thinner
                btn.addActionListener(e -> {
                    dispose();
                    switch (role) {
                        case "Owner" -> new OwnerAuth();
                        case "Manager" -> new ManagerAuth();
                        case "Chief" -> new ChiefAuth();
                        case "Customer" -> new CustomerAuth();
                    }
                });
                buttonPanel.add(btn);
            }

            background.add(buttonPanel, BorderLayout.CENTER); // Place buttons below title
            setVisible(true);
        }
    }

    // Rounded button with custom look
    static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setFont(new Font("Arial", Font.BOLD, 18));
            setFocusPainted(false);
            setBackground(Color.BLACK);
            setForeground(Color.WHITE);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            // Add mouse listener for hover effect
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    setFont(new Font("Arial", Font.BOLD, 22));  // Make font bold on hover
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    setFont(new Font("Arial", Font.BOLD, 18));  // Revert to original font size
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
