package RS;

import RS.Auth.*;
import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomePage());
    }

    public static class WelcomePage extends JFrame {
        public WelcomePage() {
            setTitle("Restaurant Management System");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 500);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            JLabel title = new JLabel("🍽 Welcome to RMS", SwingConstants.CENTER);
            title.setFont(new Font("Arial", Font.BOLD, 24));
            title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
            panel.add(title, BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 15, 15));
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

            String[] roles = {"Owner", "Manager", "Chief", "Customer"};
            for (String role : roles) {
                JButton btn = new JButton(role);
                btn.setFont(new Font("Arial", Font.PLAIN, 18));
                btn.addActionListener(e -> {
                    dispose(); 
                    switch(role) {
                        case "Owner": new OwnerAuth(); break;
                        case "Manager": new ManagerAuth(); break;
                        case "Chief": new ChiefAuth(); break;
                        case "Customer": new CustomerAuth(); break;
                    }
                });
                buttonPanel.add(btn);
            }

            panel.add(buttonPanel, BorderLayout.CENTER);
            add(panel);
            setVisible(true);
        }
    }
}
