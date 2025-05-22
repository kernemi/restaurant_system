package RS.Services;

import RS.DBconnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewIngredients extends JFrame {

    public ViewIngredients() {
        setTitle("Ingredients Inventory");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Table columns
        String[] columns = {"Ingredient Name", "Quantity (kg/liter)", "Price ($)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        table.setBackground(new Color(25, 20, 20));
        table.setForeground(new Color(255, 204, 102)); 
        table.setGridColor(new Color(255, 204, 102)); 
        table.setShowGrid(true);
        table.setRowHeight(25);

        try (Connection conn = DBconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name, quantity, price FROM ingredients")) {

            while (rs.next()) {
                String name = rs.getString("name");
                String quantity = rs.getString("quantity");
                double price = rs.getDouble("price");

                model.addRow(new Object[]{name, quantity, price});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading ingredients: " + e.getMessage());
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        getContentPane().setBackground(new Color(25, 20, 20)); // frame bg
        setVisible(true);
    }
}
