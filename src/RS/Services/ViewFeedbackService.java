package RS.Services;

import RS.DBconnection;
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class ViewFeedbackService extends JFrame {
    private final int chiefId;
    private final String chiefName;

    public ViewFeedbackService(int chiefId, String chiefName) {
        this.chiefId = chiefId;
        this.chiefName = chiefName;

        setTitle("📬 View Customers Feedback");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(30, 20, 10));
        setLayout(new BorderLayout(10, 10));

        JTextArea feedbackArea = new JTextArea();
        feedbackArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        feedbackArea.setEditable(false);
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        feedbackArea.setBackground(new Color(60, 40, 20));
        feedbackArea.setForeground(Color.WHITE);
        feedbackArea.setCaretColor(Color.WHITE);
        feedbackArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 60, 30)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JScrollPane scrollPane = new JScrollPane(feedbackArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        JButton backBtn = new JButton("⬅ Back");
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(new Color(80, 80, 80));
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new RS.Dashboards.ChiefDashboard(chiefId, chiefName);
        });

        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);

        loadFeedback(feedbackArea);
        setVisible(true);
    }

    private void loadFeedback(JTextArea feedbackArea) {
        try (Connection conn = DBconnection.getConnection()) {
            String sql = "SELECT customer_id, message, submitted_at FROM feedback ORDER BY submitted_at DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            StringBuilder allFeedback = new StringBuilder();
            while (rs.next()) {
                int customerId = rs.getInt("customer_id");
                String message = rs.getString("message");
                Timestamp timestamp = rs.getTimestamp("submitted_at");
                allFeedback.append("👤 Customer ID: ").append(customerId).append("\n")
                        .append("📅 ").append(timestamp.toString()).append("\n")
                        .append("📝 ").append(message).append("\n\n");
            }

            if (allFeedback.length() == 0) {
                allFeedback.append("No feedback submitted yet.");
            }

            feedbackArea.setText(allFeedback.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            feedbackArea.setText("⚠️ Error loading feedback. Please try again later.");
        }
    }

}
