package RS.Services;

import RS.DBconnection;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ViewFood extends JFrame {

    public ViewFood() {
        setTitle("All Menu Items");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Dark brown background color
        Color darkBrown = new Color(25, 20, 20);
        // Soft gold color for text and borders
        Color gold = new Color(255, 204, 102);

        // Set frame background
        getContentPane().setBackground(darkBrown);

        String[] columns = {"Name", "Category", "Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        // Table background and foreground colors
        table.setBackground(darkBrown);
        table.setForeground(gold);
        table.setGridColor(gold);
        table.setShowGrid(true);
        table.setSelectionBackground(new Color(60, 50, 30));
        table.setSelectionForeground(gold);

        // Set header style
        table.getTableHeader().setBackground(darkBrown);
        table.getTableHeader().setForeground(gold);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        table.getTableHeader().setBorder(new LineBorder(gold, 1));

        // Set cell renderer for text color and background
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBackground(darkBrown);
        renderer.setForeground(gold);
        renderer.setBorder(new LineBorder(gold, 1));
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        try (Connection conn = DBconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name, category, price FROM menu_items")) {

            while (rs.next()) {
                String name = rs.getString("name");
                String category = rs.getString("category");
                double price = rs.getDouble("price");

                model.addRow(new Object[]{name, category, price});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading food items: " + e.getMessage());
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(darkBrown);
        scrollPane.setBorder(BorderFactory.createLineBorder(gold, 1));

        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
