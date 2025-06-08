package RS.Auth;

import RS.DBconnection;
import RS.Main;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;
import java.sql.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class OwnerAuth extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Image Bgimage;

    public OwnerAuth() {
        setTitle("FeastFlow - Restaurant Management");
        setSize(700, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        try {
            Bgimage = ImageIO.read(getClass().getResource("/RS/UI/bg.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bgimage = blurImage((BufferedImage) Bgimage);

        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (Bgimage != null) {
                    g.drawImage(Bgimage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        background.setLayout(null);
        initUI(background);

        setContentPane(background);
        setVisible(true);
    }

    private void initUI(JPanel background) {
        int panelWidth = 500;
        int panelHeight = 300;

        JPanel centerPanel = new JPanel(null); 
        centerPanel.setBounds(
            (getWidth() - panelWidth) / 2,
            (getHeight() - panelHeight) / 2,
            panelWidth,
            panelHeight
        );
        centerPanel.setOpaque(false); // transparent so background shows

        Color cream = new Color(240, 217, 181);
        Color darkBrown = new Color(33, 20, 10);
        Font titleFont = new Font("Serif", Font.BOLD, 28);
        Font labelFont = new Font("Serif", Font.BOLD, 20);

        JLabel headerLabel = new JLabel("Owner Login");
        headerLabel.setBounds(170, 0, 300, 40);
        headerLabel.setFont(titleFont);
        headerLabel.setForeground(cream);
        centerPanel.add(headerLabel);

        JLabel userLabel = new JLabel("👤 Username:");
        userLabel.setBounds(20, 90, 140, 30);
        userLabel.setFont(labelFont);
        userLabel.setForeground(cream);
        centerPanel.add(userLabel);

        JLabel passLabel = new JLabel("🔒 Password:");
        passLabel.setBounds(20, 140, 140, 30);
        passLabel.setFont(labelFont);
        passLabel.setForeground(cream);
        centerPanel.add(passLabel);

        usernameField = new JTextField("your username");
        usernameField.setForeground(Color.GRAY);
        addPlaceholderBehavior(usernameField, "your username");

        usernameField.setBounds(160, 90, 260, 35);
        styleInput(usernameField, cream, darkBrown);
        centerPanel.add(usernameField);

        passwordField = new JPasswordField("your password");
        passwordField.setEchoChar((char) 0); 
        passwordField.setForeground(Color.GRAY);
        addPlaceholderBehavior(passwordField, "your password");

        passwordField.setBounds(160, 140, 260, 35);
        styleInput(passwordField, cream, darkBrown);
        centerPanel.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(100, 200, 100, 40);
        styleButton(loginBtn, new Color(25, 42, 86));
        loginBtn.addActionListener(e -> login());
        centerPanel.add(loginBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(220, 200, 100, 40);
        styleButton(backBtn, new Color(139, 0, 0));
        backBtn.addActionListener(e -> {
            this.dispose();
            new Main.WelcomePage().setVisible(true);
        });
        centerPanel.add(backBtn);

        background.add(centerPanel);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                centerPanel.setBounds(
                    (getWidth() - panelWidth) / 2,
                    (getHeight() - panelHeight) / 2,
                    panelWidth,
                    panelHeight
                );
            }
        });
    }


    private void styleInput(JTextField field, Color bg, Color fg) {
        field.setBackground(bg);
        field.setForeground(fg);
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
    }

    private void styleButton(JButton button, Color color) {
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(new LineBorder(Color.BLACK, 1, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DBconnection.getConnection()) {
            String sql = "SELECT name FROM owner WHERE username = ? AND pasword = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                JOptionPane.showMessageDialog(this, "Hello, " + name + "!");
                dispose();
                new RS.Dashboards.OwnerDashboard(name).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private BufferedImage blurImage(BufferedImage image) {
        float[] matrix = {
            1f / 9f, 1f / 9f, 1f / 9f,
            1f / 9f, 1f / 9f, 1f / 9f,
            1f / 9f, 1f / 9f, 1f / 9f,
        };
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrix));
        return op.filter(image, null);
    }

    private void addPlaceholderBehavior(JTextField field, String placeholder) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar('•');
                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar((char) 0);
                    }
                }
            }
        });
    }

}
