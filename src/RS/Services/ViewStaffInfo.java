package RS.Services;

import RS.DBconnection;
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class ViewStaffInfo extends JFrame {
    public ViewStaffInfo() {
        setTitle("System Management - Staff Info");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setBackground(new Color(30, 20, 10)); 
        textArea.setForeground(new Color(255, 204, 102)); 

        StringBuilder sb = new StringBuilder();
        sb.append("👨‍🍳 CHEFS:\n");

        try (Connection conn = DBconnection.getConnection()) {
            String chefQuery = "SELECT fullname, email FROM chief";
            try (PreparedStatement psChef = conn.prepareStatement(chefQuery);
                 ResultSet rsChef = psChef.executeQuery()) {
                while (rsChef.next()) {
                    sb.append("• ").append(rsChef.getString("fullname"))
                      .append(" - ").append(rsChef.getString("email")).append("\n");
                }
            }

            sb.append("\n👔 MANAGERS:\n");

            String managerQuery = "SELECT name, email FROM manager";
            try (PreparedStatement psManager = conn.prepareStatement(managerQuery);
                 ResultSet rsManager = psManager.executeQuery()) {
                while (rsManager.next()) {
                    sb.append("• ").append(rsManager.getString("name"))
                      .append(" - ").append(rsManager.getString("email")).append("\n");
                }
            }

        } catch (SQLException e) {
            sb.append("\n⚠️ Error loading data: ").append(e.getMessage());
        }

        textArea.setText(sb.toString());

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane);

        setVisible(true);
    }
}

