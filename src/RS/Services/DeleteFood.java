package RS.Services;

import RS.DBconnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteFood {

    public DeleteFood() {
        String nameToDelete = JOptionPane.showInputDialog(null, "Enter the name of the food to delete:", "Delete Food", JOptionPane.QUESTION_MESSAGE);

        if (nameToDelete == null || nameToDelete.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Food name is required.");
            return;
        }

        if (foodExists(nameToDelete)) {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete \"" + nameToDelete + "\"?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                deleteFood(nameToDelete);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Food \"" + nameToDelete + "\" not found in the database.");
        }
    }

    private boolean foodExists(String name) {
        String sql = "SELECT id FROM menu_items WHERE name = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error checking food: " + e.getMessage());
            return false;
        }
    }

    private void deleteFood(String name) {
        String sql = "DELETE FROM menu_items WHERE name = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Food \"" + name + "\" deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete food. It may not exist.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting food: " + e.getMessage());
        }
    }
}
