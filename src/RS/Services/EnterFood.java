package RS.Services;

import RS.DBconnection;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class EnterFood {

    private JFrame mainFrame;
    private JTextField tfName, tfPrice, tfCategory, tfImagePath;
    private JTextArea taDescription;

    public EnterFood() {
        prepareGUI();
        showForm();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("➕ Add Menu Item");
        mainFrame.setSize(500, 500);
        mainFrame.setLayout(new BorderLayout(10, 10));
        mainFrame.getContentPane().setBackground(new Color(25, 20, 20)); // dark brown/black background
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        JLabel headerLabel = new JLabel("Add New Food Item", JLabel.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerLabel.setForeground(new Color(255, 204, 102));  // soft gold
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        mainFrame.add(headerLabel, BorderLayout.NORTH);
    }

    private void showForm() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBackground(new Color(25, 20, 20)); // dark background
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        Font labelFont = new Font("SansSerif", Font.PLAIN, 16);
        Color labelColor = new Color(255, 204, 102); // soft gold
        Color fieldBgColor = new Color(40, 35, 35);  // dark input background
        Color fieldFgColor = new Color(255, 204, 102); // soft gold text

        tfName = createField("Food Name*:", panel, labelFont, labelColor, fieldBgColor, fieldFgColor);
        
        taDescription = new JTextArea(3, 20);
        taDescription.setFont(labelFont);
        taDescription.setBackground(fieldBgColor);
        taDescription.setForeground(fieldFgColor);
        taDescription.setCaretColor(fieldFgColor);
        taDescription.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 102)));
        panel.add(createLabel("Description (optional):", labelFont, labelColor));
        panel.add(new JScrollPane(taDescription));

        tfPrice = createField("Price*:", panel, labelFont, labelColor, fieldBgColor, fieldFgColor);
        tfCategory = createField("Category* (Breakfast,Lunch,Desserts,Drinks):", panel, labelFont, labelColor, fieldBgColor, fieldFgColor);
        tfImagePath = createField("Image Path (optional):", panel, labelFont, labelColor, fieldBgColor, fieldFgColor);

        JButton btnInsert = new JButton("Insert");
        btnInsert.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnInsert.setBackground(new Color(255, 204, 102));  // soft gold button
        btnInsert.setForeground(new Color(30, 20, 10));     // dark text
        btnInsert.setFocusPainted(false);
        btnInsert.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnInsert.setBorder(BorderFactory.createLineBorder(new Color(120, 90, 40)));
        btnInsert.addActionListener(this::handleInsert);
        btnInsert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnInsert.setBackground(new Color(255, 220, 130));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnInsert.setBackground(new Color(255, 204, 102));
            }
        });

        panel.add(new JLabel());
        panel.add(btnInsert);

        mainFrame.add(panel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
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

    private void handleInsert(ActionEvent e) {
        String name = tfName.getText().trim();
        String description = taDescription.getText().trim();
        String priceText = tfPrice.getText().trim();
        String category = tfCategory.getText().trim();
        String imagePath = tfImagePath.getText().trim();

        if (name.isEmpty() || priceText.isEmpty() || category.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Please fill fields marked with *");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            boolean success = addItem(name, description, price, category, imagePath);
            if (success) {
                JOptionPane.showMessageDialog(mainFrame, "Item added successfully!");
                mainFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Failed to add item.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Invalid price format.");
        }
    }

    private boolean addItem(String name, String description, double price, String category, String imagePath) {
        String sql = "INSERT INTO menu_items (name, description, price, category, image_path) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDouble(3, price);
            stmt.setString(4, category);
            stmt.setString(5, imagePath);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, "Database Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
