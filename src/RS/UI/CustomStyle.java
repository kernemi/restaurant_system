package RS.UI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class CustomStyle {

    public static void showCustomMessage(Component parent, String title, String message) {
        Window window = parent instanceof Window ? (Window) parent : SwingUtilities.getWindowAncestor(parent);
        JDialog dialog = new JDialog(window, title, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(350, 180);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(new Color(60, 40, 20)); // dark brown

        JLabel label = new JLabel("<html><body style='text-align:center;'>" + message + "</body></html>", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setForeground(new Color(255, 204, 102)); // gold text
        dialog.add(label, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        styleButton(okButton);
        okButton.addActionListener(e -> dialog.dispose());

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(60, 40, 20));
        btnPanel.add(okButton);

        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    public static void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        button.setBackground(new Color(255, 204, 102));
        button.setForeground(new Color(30, 20, 10));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(new Color(120, 90, 40)));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 220, 130));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 204, 102));
            }
        });
    }
}
