package RS.Services;

import RS.DBconnection;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class UpdateFood {
    private JFrame frame;
    private JTextField tfNewName, tfPrice, tfImagePath;
    private JTextArea taDescription;
    private String currentName;

    public UpdateFood() {
        askCurrentName();
    }

    private void askCurrentName() {
        currentName = JOptionPane.showInputDialog(null, "Enter the name of the food to update:", "Update Food", JOptionPane.QUESTION_MESSAGE);
        if (currentName != null && !currentName.trim().isEmpty()) {
            prepareGUI();
            showForm();
        } else {
            JOptionPane.showMessageDialog(null, "Food name is required to update.");
        }
    }

    private void prepareGUI() {
        frame = new JFrame("✏️ Update Food Item");
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(new Color(25, 20, 20));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JLabel headerLabel = new JLabel("Update Food: " + currentName, JLabel.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerLabel.setForeground(new Color(255, 204, 102));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        frame.add(headerLabel, BorderLayout.NORTH);
    }

    private void showForm() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBackground(new Color(25, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        Font labelFont = new Font("SansSerif", Font.PLAIN, 16);
        Color labelColor = new Color(255, 204, 102);
        Color fieldBgColor = new Color(40, 35, 35);
        Color fieldFgColor = new Color(255, 204, 102);

        tfNewName = createField("New Name*:", panel, labelFont, labelColor, fieldBgColor, fieldFgColor);

        taDescription = new JTextArea(3, 20);
        taDescription.setFont(labelFont);
        taDescription.setBackground(fieldBgColor);
        taDescription.setForeground(fieldFgColor);
        taDescription.setCaretColor(fieldFgColor);
        taDescription.setBorder(BorderFactory.createLineBorder(labelColor));
        panel.add(createLabel("New Description:", labelFont, labelColor));
        panel.add(new JScrollPane(taDescription));

        tfPrice = createField("New Price*:", panel, labelFont, labelColor, fieldBgColor, fieldFgColor);
        tfImagePath = createField("New Image Path (optional):", panel, labelFont, labelColor, fieldBgColor, fieldFgColor);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnUpdate.setBackground(new Color(255, 204, 102));
        btnUpdate.setForeground(new Color(30, 20, 10));
        btnUpdate.setFocusPainted(false);
        btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnUpdate.setBorder(BorderFactory.createLineBorder(new Color(120, 90, 40)));
        btnUpdate.addActionListener(this::handleUpdate);
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnUpdate.setBackground(new Color(255, 220, 130));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnUpdate.setBackground(new Color(255, 204, 102));
            }
        });

        panel.add(new JLabel());
        panel.add(btnUpdate);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text, JLabel.RIGHT);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    private JTextField createField(String labelText, JPanel panel, Font font, Color labelColor, Color bgColor, Color fgColor) {
        JLabel label = createLabel(labelText, font, labelColor);
        JTextField field = new JTextField();
        field.setFont(font);
        field.setBackground(bgColor);
        field.setForeground(fgColor);
        field.setCaretColor(fgColor);
        field.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 102)));
        panel.add(label);
        panel.add(field);
        return field;
    }

    private void handleUpdate(ActionEvent e) {
        String newName = tfNewName.getText().trim();
        String description = taDescription.getText().trim();
        String priceStr = tfPrice.getText().trim();
        String imagePath = tfImagePath.getText().trim();

        if (newName.isEmpty() || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "New name and price are required.");
            return;
        }

        try {
            double newPrice = Double.parseDouble(priceStr);
            boolean success = updateItem(currentName, newName, description, newPrice, imagePath);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Food item updated successfully.");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to update food item.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid price format.");
        }
    }

    private boolean updateItem(String oldName, String newName, String description, double price, String imagePath) {
        String sql = "UPDATE menu_items SET name = ?, description = ?, price = ?, image_path = ? WHERE name = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newName);
            stmt.setString(2, description);
            stmt.setDouble(3, price);
            stmt.setString(4, imagePath);
            stmt.setString(5, oldName);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Database Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
