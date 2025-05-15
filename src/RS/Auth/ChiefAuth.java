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

public class ChiefAuth extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField securityKeyField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JLabel keyLabel,fullNameLabel, emailLabel, authModeLabel;
    private boolean isLoginMode = true;
    private JButton toggleBtn, authBtn;
    private Image bgImage;

    public ChiefAuth() {
        setTitle("FeastFlow - Restaurant Management");
        setSize(700, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

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

    private void initUI(JPanel background) {
        int panelWidth = 500;
        int panelHeight = 350;

        JPanel centerPanel = new JPanel(null);
        centerPanel.setSize(panelWidth, panelHeight);
        background.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int x = (background.getWidth() - panelWidth) / 2;
                int y = (background.getHeight() - panelHeight) / 2;
                centerPanel.setLocation(x, y);
            }
        });
        centerPanel.setOpaque(false);

        Color cream = new Color(240, 217, 181);
        Color darkBrown = new Color(33, 20, 10);
        Font labelFont = new Font("Serif", Font.BOLD, 18);

        authModeLabel = new JLabel("Chief Login");
        authModeLabel.setBounds(150, 10, 300, 30);
        authModeLabel.setFont(new Font("Serif", Font.BOLD, 26));
        authModeLabel.setForeground(cream);
        centerPanel.add(authModeLabel);

        fullNameLabel = new JLabel("👤 Full Name:");
        fullNameLabel.setBounds(20, 50, 120, 30);
        fullNameLabel.setFont(labelFont);
        fullNameLabel.setForeground(cream);
        centerPanel.add(fullNameLabel);

        fullNameField = new JTextField("your full name");
        fullNameField.setBounds(160, 50, 260, 30);
        styleInput(fullNameField, cream, darkBrown);
        addPlaceholderBehavior(fullNameField, "your full name");
        centerPanel.add(fullNameField);

        // Username
        JLabel userLabel = new JLabel("👤 Username:");
        userLabel.setBounds(20, 90, 120, 30);
        userLabel.setFont(labelFont);
        userLabel.setForeground(cream);
        centerPanel.add(userLabel);

        usernameField = new JTextField("your username");
        usernameField.setBounds(160, 90, 260, 30);
        styleInput(usernameField, cream, darkBrown);
        addPlaceholderBehavior(usernameField, "your username");
        centerPanel.add(usernameField);

        emailLabel = new JLabel("📧 Email:");
        emailLabel.setBounds(20, 130, 120, 30);
        emailLabel.setFont(labelFont);
        emailLabel.setForeground(cream);
        centerPanel.add(emailLabel);

        emailField = new JTextField("your email");
        emailField.setBounds(160, 130, 260, 30);
        styleInput(emailField, cream, darkBrown);
        addPlaceholderBehavior(emailField, "your email");
        centerPanel.add(emailField);

        JLabel passLabel = new JLabel("🔒 Password:");
        passLabel.setBounds(20, 170, 120, 30);
        passLabel.setFont(labelFont);
        passLabel.setForeground(cream);
        centerPanel.add(passLabel);

        passwordField = new JPasswordField("your password");
        passwordField.setBounds(160, 170, 260, 30);
        passwordField.setForeground(Color.GRAY);
        styleInput(passwordField, cream, darkBrown);
        addPlaceholderBehavior(passwordField, "your password");
        centerPanel.add(passwordField);

        keyLabel = new JLabel("\uD83D\uDD11 Security Key:");
        keyLabel.setBounds(20, 210, 140, 30);
        keyLabel.setFont(labelFont);
        keyLabel.setForeground(cream);
        centerPanel.add(keyLabel);

        securityKeyField = new JTextField();
        securityKeyField.setBounds(160, 210, 260, 30);
        styleInput(securityKeyField, cream, darkBrown);
        centerPanel.add(securityKeyField);

         // Login/Signup Button
        authBtn = new JButton("Login");
        authBtn.setBounds(100, 260, 100, 40);
        styleButton(authBtn, new Color(25, 42, 86));
        authBtn.addActionListener(e -> {
            if (isLoginMode) login();
            else register();
        });
        centerPanel.add(authBtn);

        // Toggle Button
        toggleBtn = new JButton("Switch to Signup");
        toggleBtn.setBounds(210, 260, 150, 40);
        styleButton(toggleBtn, new Color(102, 204, 102));
        toggleBtn.addActionListener(e -> toggleAuthMode());
        centerPanel.add(toggleBtn);

        // Back Button
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(180, 310, 100, 35);
        styleButton(backBtn, new Color(139, 0, 0));
        backBtn.addActionListener(e -> {
            this.dispose();
            new Main.WelcomePage().setVisible(true);
        });
        centerPanel.add(backBtn);

        background.add(centerPanel);
        toggleFieldVisibility();
    }

    private void toggleAuthMode() {
        isLoginMode = !isLoginMode;
        authModeLabel.setText(isLoginMode ? "Chief Login" : "Chief Signup");
        authBtn.setText(isLoginMode ? "Login" : "Signup");
        toggleBtn.setText(isLoginMode ? "Switch to Signup" : "Switch to Login");
        toggleFieldVisibility();
    }
    private void toggleFieldVisibility() {
        boolean showSignupFields = !isLoginMode;
        fullNameLabel.setVisible(showSignupFields);
        fullNameField.setVisible(showSignupFields);
        emailLabel.setVisible(showSignupFields);
        emailField.setVisible(showSignupFields);
        keyLabel.setVisible(showSignupFields);
        securityKeyField.setVisible(showSignupFields);
    }

    private void styleInput(JTextField field, Color bg, Color fg) {
        field.setBackground(bg);
        field.setForeground(fg);
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
    }

    private void styleButton(JButton button, Color color) {
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
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
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DBconnection.getConnection()) {
            String sql = "SELECT fullname FROM chief WHERE username = ? AND pasword = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("fullname");
                JOptionPane.showMessageDialog(this, "Hello, " + name + "!");
                dispose();
                new RS.Dashboards.CustomerDashboard(name).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void register() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || securityKeyField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!"kernemi".equals(securityKeyField.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Invalid security key.", "Signup Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DBconnection.getConnection()) {
            String checkSql = "SELECT username FROM chief WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet checkRs = checkStmt.executeQuery();

            if (checkRs.next()) {
                JOptionPane.showMessageDialog(this, "Username already exists!", "Signup Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String insertSql = "INSERT INTO chief (fullname, username, email, pasword) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, fullName);
            insertStmt.setString(2, username);
            insertStmt.setString(3, email);
            insertStmt.setString(4, password);
            insertStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Signup successful! Please login.");
            toggleAuthMode();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private BufferedImage blurImage(BufferedImage image) {
        float[] matrix = new float[9];
        for (int i = 0; i < 9; i++) matrix[i] = 1.0f / 9.0f;
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrix));
        return op.filter(image, null);
    }

    private void addPlaceholderBehavior(JTextField field, String placeholder) {
        field.setForeground(Color.GRAY);
        field.setText(placeholder);

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    if (field instanceof JPasswordField) ((JPasswordField) field).setEchoChar('•');
                }
            }

            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                    if (field instanceof JPasswordField) ((JPasswordField) field).setEchoChar((char) 0);
                }
            }
        });
    }
}
