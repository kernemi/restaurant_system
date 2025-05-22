package RS.Dashboards;

import RS.Services.BrowseMenuPage;
import RS.Services.ViewFeedbackService;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ChiefDashboard extends JFrame {
    private String chiefName;
    private int chiefId;
    private Image bgImage;

    public ChiefDashboard(int chiefId,String chiefName) {
        this.chiefName = chiefName;
        this.chiefId = chiefId;
        setTitle("Chief Dashboard - Welcome " + chiefName);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            bgImage = ImageIO.read(getClass().getResource("/RS/UI/bg.png"));
            bgImage = blurImage((BufferedImage) bgImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        background.setLayout(null);
        initUI(background);

        setContentPane(background);
        setVisible(true);
    }

    public static BufferedImage blurImage(BufferedImage image) {
        float[] matrix = new float[25];
        for (int i = 0; i < 25; i++) matrix[i] = 1.0f / 25.0f;
        Kernel kernel = new Kernel(5, 5, matrix);
        ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        return convolve.filter(image, null);
    }

    private void initUI(JPanel background) {
        int panelWidth = 600;
        int panelHeight = 400;

        JPanel centerPanel = new JPanel(new BorderLayout(20, 0));
        centerPanel.setSize(panelWidth, panelHeight);
        background.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int x = (background.getWidth() - panelWidth) / 2;
                int y = (background.getHeight() - panelHeight) / 2;
                centerPanel.setLocation(x, y);
            }
        });
        centerPanel.setOpaque(false);

        // Left panel for image
        JLabel imageLabel = new JLabel();
        try {
            Image img = ImageIO.read(getClass().getResource("/RS/UI/chief.png"));
            Image scaledImg = img.getScaledInstance(250, 300, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImg));
        } catch (IOException e) {
            imageLabel.setText("Image not found");
        }

        JPanel imagePanel = new JPanel();
        imagePanel.setOpaque(false);
        imagePanel.add(imageLabel);

        // Right panel with buttons
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Hi, Chef " + chiefName);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(240, 217, 181));
        rightPanel.add(titleLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        String[] actions = {
            "🍽 View Menu Items",
            "💬 View Feedback",
            "Back to entryPage"
        };

        for (String action : actions) {
            JButton btn = new RoundedButton(action);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(230, 45));
            btn.addActionListener(e -> {
                dispose();
                switch (action) {
                    case "🍽 View Menu Items" -> new BrowseMenuPage(chiefId,chiefName,"chief");
                    case "💬 View Feedback" -> new ViewFeedbackService(chiefId,chiefName);
                    case "Back to entryPage" -> new RS.Main.WelcomePage();
                }
            });
            rightPanel.add(Box.createRigidArea(new Dimension(0, 9)));
            rightPanel.add(btn);
        }

        centerPanel.add(imagePanel, BorderLayout.WEST);
        centerPanel.add(rightPanel, BorderLayout.CENTER);
        background.add(centerPanel);
    }

    private void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
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

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent evt) {
                    setFont(new Font("SansSerif", Font.BOLD, 20));
                }

                @Override
                public void mouseExited(MouseEvent evt) {
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
