package RS.Services;

import RS.Dashboards.CustomerDashboard;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PlaceOrder extends JFrame {

    private final int customerId;
    private final String customerName;

    private JTable menuTable;
    private DefaultTableModel menuTableModel;

    private JTable cartTable;
    private DefaultTableModel cartTableModel;

    private JLabel totalLabel;
    private JLabel netLabel;

    private double total = 0.0;
    private final double TAX_RATE = 0.15;

    public PlaceOrder(int customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;

        setTitle("Place Order - " + customerName);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        loadMenuItems();

        setVisible(true);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(100, 60, 30));

        // Title label
        JLabel titleLabel = new JLabel("Menu Items - Click 'Add' to add to cart");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(240, 217, 181));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Back button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JButton backButton = new JButton("← Back");
        backButton.setBackground(new Color(100, 60, 30));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            dispose();
            new CustomerDashboard(customerId, customerName); // Ensure this class exists
        });

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Menu table
        menuTableModel = new DefaultTableModel(new Object[]{"Name", "Price (Birr)", "Category", "Add"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        menuTable = new JTable(menuTableModel);
        menuTable.setRowHeight(30);
        menuTable.getColumn("Add").setCellRenderer(new ButtonRenderer());
        menuTable.getColumn("Add").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane menuScroll = new JScrollPane(menuTable);
        menuScroll.setPreferredSize(new Dimension(880, 250));
        mainPanel.add(menuScroll, BorderLayout.CENTER);

        // Cart panel
        JPanel cartPanel = new JPanel(new BorderLayout(5, 5));
        cartPanel.setOpaque(false);

        JLabel cartLabel = new JLabel("Your Cart");
        cartLabel.setFont(new Font("Serif", Font.BOLD, 20));
        cartLabel.setForeground(new Color(240, 217, 181));
        cartPanel.add(cartLabel, BorderLayout.NORTH);

        cartTableModel = new DefaultTableModel(new Object[]{"Name", "Price (Birr)", "Quantity"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cartTable = new JTable(cartTableModel);
        cartTable.setRowHeight(25);
        JScrollPane cartScroll = new JScrollPane(cartTable);
        cartScroll.setPreferredSize(new Dimension(880, 150));
        cartPanel.add(cartScroll, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);

        totalLabel = new JLabel("Total: 0.00 Birr");
        totalLabel.setForeground(new Color(240, 217, 181));
        totalLabel.setFont(new Font("Serif", Font.BOLD, 18));
        bottomPanel.add(totalLabel);

        JButton totalBtn = new JButton("Calculate Total (incl. Tax)");
        totalBtn.setBackground(new Color(100, 60, 30));
        totalBtn.setForeground(Color.WHITE);
        totalBtn.setFocusPainted(false);
        totalBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        totalBtn.addActionListener(e -> calculateNetAmount());
        bottomPanel.add(totalBtn);

        netLabel = new JLabel("Net Amount: 0.00 Birr");
        netLabel.setForeground(new Color(240, 217, 181));
        netLabel.setFont(new Font("Serif", Font.BOLD, 18));
        bottomPanel.add(netLabel);

        cartPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(cartPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadMenuItems() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rs", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name, price, category FROM menu_items");

            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String category = rs.getString("category");
                menuTableModel.addRow(new Object[]{name, price, category, "Add"});
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading menu items: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addItemToCart(String name, double price) {
        boolean found = false;
        for (int i = 0; i < cartTableModel.getRowCount(); i++) {
            String cartName = (String) cartTableModel.getValueAt(i, 0);
            if (cartName.equals(name)) {
                int qty = (int) cartTableModel.getValueAt(i, 2);
                cartTableModel.setValueAt(qty + 1, i, 2);
                found = true;
                break;
            }
        }
        if (!found) {
            cartTableModel.addRow(new Object[]{name, price, 1});
        }

        total += price;
        updateTotalLabel();
    }

    private void updateTotalLabel() {
        totalLabel.setText(String.format("Total: %.2f Birr", total));
    }

    private void calculateNetAmount() {
        double tax = total * TAX_RATE;
        double net = total + tax;
        netLabel.setText(String.format("Net Amount (incl. 15%% tax): %.2f Birr", net));
    }

    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(new Color(100, 60, 30));
            setForeground(Color.WHITE);
            setFont(new Font("SansSerif", Font.BOLD, 14));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(new Color(100, 60, 30));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("SansSerif", Font.BOLD, 14));

            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.label = (value == null) ? "" : value.toString();
            button.setText(label);
            this.clicked = true;
            this.row = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                String name = (String) menuTableModel.getValueAt(row, 0);
                double price = (double) menuTableModel.getValueAt(row, 1);
                addItemToCart(name, price);
            }
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
        }

        SwingUtilities.invokeLater(() -> new PlaceOrder(1, "TestCustomer"));
    }
}
