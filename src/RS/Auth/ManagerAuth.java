package RS.Auth;

import RS.DBconnection;
import RS.Main;
import RS.UI.ScaledBackgroundPanel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class ManagerAuth extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField securityKeyField;   // Added security key input

    public ManagerAuth() {
        setTitle("Manager Login");
        setSize(700, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ScaledBackgroundPanel background = new ScaledBackgroundPanel("restaurant.jpg");
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

        JLabel loginLabel = new JLabel("Manager Login");
        loginLabel.setBounds(280, 60, 200, 30);
        loginLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        loginLabel.setForeground(Color.WHITE);
        background.add(loginLabel);

        JLabel userLabel = new JLabel("👤 Username:");
        userLabel.setBounds(170, 100, 120, 30);
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        userLabel.setForeground(Color.WHITE);
        background.add(userLabel);

        JLabel passLabel = new JLabel("🔒 Password:");
        passLabel.setBounds(170, 150, 120, 30);
        passLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        passLabel.setForeground(Color.WHITE);
        background.add(passLabel);

        usernameField = new JTextField();
        usernameField.setBounds(290, 100, 200, 30);
        styleInput(usernameField);
        background.add(usernameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(290, 150, 200, 30);
        styleInput(passwordField);
        background.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(300, 250, 100, 35);
        styleButton(loginBtn, new Color(0, 128, 255));
        loginBtn.addActionListener(e -> login());
        background.add(loginBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(300, 300, 100, 30);
        styleButton(backBtn, new Color(255, 102, 102));
        backBtn.addActionListener(e -> {
            this.dispose();
            new Main.WelcomePage();
        });
        background.add(backBtn);

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
            String sql = "SELECT name FROM manager WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                JOptionPane.showMessageDialog(this, "Hello, " + name + "!");
                dispose();
                new RS.Dashboards.ManagerDashboard(name);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
