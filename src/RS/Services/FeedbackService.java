package RS.Services;

import RS.DBconnection;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.*;

public class FeedbackService extends JFrame {
    private final String customerName;
    private final int customerId;

    public FeedbackService(int customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;

        setTitle("Submit Feedback");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(30, 20, 10));

        JLabel title = new JLabel("Enter your feedback:");
        title.setFont(new Font("Serif", Font.BOLD, 20));
        title.setForeground(new Color(240, 217, 181));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JTextArea feedbackArea = new JTextArea();
        feedbackArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        feedbackArea.setBackground(new Color(60, 40, 20));
        feedbackArea.setForeground(Color.WHITE);
        feedbackArea.setCaretColor(Color.WHITE);
        feedbackArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 60, 30)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setBackground(new Color(100, 60, 30));
        submitBtn.setFocusPainted(false);
        submitBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        submitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        submitBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                submitBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                submitBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
            }
        });

        submitBtn.addActionListener(e -> {
            String message = feedbackArea.getText().trim();
            if (!message.isEmpty()) {
                try (Connection conn = DBconnection.getConnection()) {
                    String sql = "INSERT INTO feedback (customer_id, message) VALUES (?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, customerId);
                    stmt.setString(2, message);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Thank you for your feedback!");
                    dispose(); // Close feedback window
                    new RS.Dashboards.CustomerDashboard(customerId, customerName);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error saving feedback.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Feedback cannot be empty.");
            }
        });

        // Back Button
        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(new Color(80, 80, 80));  // Slightly different color
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
            }
        });

        backBtn.addActionListener(e -> {
            dispose();  // Close feedback window
            new RS.Dashboards.CustomerDashboard(customerId, customerName);
        });

        add(title, BorderLayout.NORTH);
        add(new JScrollPane(feedbackArea), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.add(backBtn);
        btnPanel.add(submitBtn);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
