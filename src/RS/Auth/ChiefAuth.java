package RS.Auth;

import RS.DBconnection;
import RS.Dashboards.ChiefDashboard;
import RS.UI.ScaledBackgroundPanel;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class ChiefAuth extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField securityKeyField;
    private JTextField fullNameField;
    private JLabel keyLabel;
    private JLabel fullNameLabel;
    private boolean isLoginMode = true;
    private JLabel authModeLabel;
    private JButton toggleBtn;
    private JButton authBtn;

    public ChiefAuth() {
        setTitle("Chief Authentication");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ScaledBackgroundPanel background = new ScaledBackgroundPanel("RS/UI/restaurant.jpg");
        background.setLayout(null);
        setContentPane(background);

        initUI(background);
        setVisible(true);
    }

    private void initUI(JPanel background) {
        JLabel headerLabel = new JLabel("Restaurant Management System");
        headerLabel.setBounds(150, 20, 500, 40);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headerLabel.setForeground(Color.WHITE);
        background.add(headerLabel);

        authModeLabel = new JLabel("Chief Login");
        authModeLabel.setBounds(300, 60, 200, 30);
        authModeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        authModeLabel.setForeground(Color.WHITE);
        background.add(authModeLabel);

        fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setBounds(170, 100, 120, 30);
        fullNameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        fullNameLabel.setForeground(Color.WHITE);
        background.add(fullNameLabel);

        fullNameField = new JTextField();
        fullNameField.setBounds(290, 100, 200, 30);
        styleInput(fullNameField);
        background.add(fullNameField);

        JLabel userLabel = new JLabel("\uD83D\uDC68\u200D\uD83C\uDF73 Username:");
        userLabel.setBounds(170, 150, 120, 30);
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        userLabel.setForeground(Color.WHITE);
        background.add(userLabel);

        JLabel passLabel = new JLabel("\uD83D\uDD10 Password:");
        passLabel.setBounds(170, 200, 120, 30);
        passLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        passLabel.setForeground(Color.WHITE);
        background.add(passLabel);

        keyLabel = new JLabel("\uD83D\uDD11 Security Key:");
        keyLabel.setBounds(170, 250, 120, 30);
        keyLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        keyLabel.setForeground(Color.WHITE);
        background.add(keyLabel);

        usernameField = new JTextField();
        usernameField.setBounds(290, 150, 200, 30);
        styleInput(usernameField);
        background.add(usernameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(290, 200, 200, 30);
        styleInput(passwordField);
        background.add(passwordField);

        securityKeyField = new JTextField();
        securityKeyField.setBounds(290, 250, 200, 30);
        styleInput(securityKeyField);
        background.add(securityKeyField);

        authBtn = new JButton("Login");
        authBtn.setBounds(300, 300, 100, 35);
        styleButton(authBtn, new Color(0, 128, 255));
        authBtn.addActionListener(e -> {
            if (isLoginMode) login();
            else register();
        });
        background.add(authBtn);

        toggleBtn = new JButton("Switch to Signup");
        toggleBtn.setBounds(280, 345, 140, 30);
        styleButton(toggleBtn, new Color(102, 204, 102));
        toggleBtn.addActionListener(e -> toggleAuthMode());
        background.add(toggleBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(300, 385, 100, 30);
        styleButton(backBtn, new Color(255, 102, 102));
        backBtn.addActionListener(e -> {
            this.dispose();
            new RS.Main.WelcomePage();
        });
        background.add(backBtn);

        keyLabel.setVisible(false);
        securityKeyField.setVisible(false);
        fullNameLabel.setVisible(false);
        fullNameField.setVisible(false);
    }

    private void toggleAuthMode() {
        isLoginMode = !isLoginMode;
        authModeLabel.setText(isLoginMode ? "Chief Login" : "Chief Signup");
        authBtn.setText(isLoginMode ? "Login" : "Signup");
        toggleBtn.setText(isLoginMode ? "Switch to Signup" : "Switch to Login");
        keyLabel.setVisible(!isLoginMode);
        securityKeyField.setVisible(!isLoginMode);

        fullNameLabel.setVisible(!isLoginMode);
        fullNameField.setVisible(!isLoginMode);
        fullNameField.setText("");
    }

    private void styleInput(JTextField field) {
        field.setBackground(new Color(255, 255, 255, 200));
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBorder(new LineBorder(Color.GRAY, 1, true));
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
            String sql = "SELECT name, password_hash, salt FROM chief WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String storedHash = rs.getString("password_hash");
                String storedSalt = rs.getString("salt");

                // Validate password using the stored salt and hash
                if (validatePassword(password, storedHash, storedSalt)) {
                    JOptionPane.showMessageDialog(this, "Welcome, Chef " + name + "!");
                    dispose();
                    new ChiefDashboard(name);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validatePassword(String password, String storedHash, String storedSalt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] salt = storedSalt.getBytes();
            digest.update(salt);
            byte[] hash = digest.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().equals(storedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void register() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String fullName = fullNameField.getText().trim();

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

            // Generate salt and hash the password
            String salt = generateSalt();
            String hash = hashPassword(password, salt);

            String insertSql = "INSERT INTO chief (username, name, password_hash, salt) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, username);
            insertStmt.setString(2, fullName);
            insertStmt.setString(3, hash);
            insertStmt.setString(4, salt);
            insertStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Signup successful! Please login.");
            toggleAuthMode();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return new String(salt);
    }

    private String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(salt.getBytes());
            byte[] hash = digest.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
