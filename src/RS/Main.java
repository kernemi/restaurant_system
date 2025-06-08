package RS;

import RS.Auth.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(WelcomePage::new);
    }

    public static class WelcomePage extends JFrame {
        private Image bgImage, logoImage;

        public WelcomePage() {
            setTitle("FeastFlow - Restaurant Management");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(700, 600);
            setLocationRelativeTo(null);

            try {
                bgImage = ImageIO.read(getClass().getResource("UI/bg2.png")); 
                logoImage = ImageIO.read(getClass().getResource("UI/l.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            bgImage = blurImage((BufferedImage) bgImage);

            JPanel background = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (bgImage != null) {
                        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                    }
                }
            };
            background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
            background.setOpaque(false);
            setContentPane(background);

            JLabel title = new JLabel("FeastFlow", SwingConstants.CENTER);
            title.setFont(new Font("Serif", Font.BOLD, 50));
            title.setForeground(new Color(255, 200, 150)); // Light golden color
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            title.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));
            background.add(title);

            if (logoImage != null) {
                JLabel logoLabel = new JLabel(new ImageIcon(logoImage.getScaledInstance(200, 100, Image.SCALE_SMOOTH)));
                logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                background.add(logoLabel);
            }

            JLabel subtitle = new JLabel("Login / Sign up as:", SwingConstants.CENTER);
            subtitle.setFont(new Font("SansSerif", Font.PLAIN, 22));
            subtitle.setForeground(Color.WHITE); // White text for the subtitle
            subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            subtitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
            background.add(subtitle);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setOpaque(false);
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            String[] roles = {"Owner", "Manager", "Chef", "Customer"};
            for (String role : roles) {
                JButton btn = new RoundedButton(role);
                btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                btn.setMaximumSize(new Dimension(250, 50));
                btn.addActionListener(e -> {
                    dispose();
                    switch (role) {
                        case "Owner" -> new OwnerAuth();
                        case "Manager" -> new ManagerAuth();
                        case "Chef" -> new ChiefAuth();
                        case "Customer" -> new CustomerAuth();
                    }
                });
                buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                buttonPanel.add(btn);
            }

            background.add(buttonPanel);
            setVisible(true);
        }

        public static BufferedImage blurImage(BufferedImage image) {
            float[] matrix = new float[25]; // Use a larger matrix for stronger blur effect
            for (int i = 0; i < 25; i++) {
                matrix[i] = 1.0f / 25.0f; // Each value contributes equally to the blur
            }
            Kernel kernel = new Kernel(5, 5, matrix); // 5x5 kernel for stronger blur
            ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            return convolve.filter(image, null);
        }
    }

    static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setFont(new Font("SansSerif", Font.BOLD, 18));
            setFocusPainted(false);
            setForeground(Color.WHITE);
            setBackground(new Color(100, 60, 30));
            setOpaque(false);
            setContentAreaFilled(false);
            setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    setFont(new Font("SansSerif", Font.BOLD, 20));
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    setFont(new Font("SansSerif", Font.BOLD, 18));
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
