package gui;

import console.Clothing;
import console.Electronic;
import console.Product;
import console.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartView extends JFrame {
    private JTable cartTable;
    private JLabel totalLabel;
    private User user;

    private JLabel discount1Label;
    private JLabel discount2Label;

    private JLabel finalTotalLabel;

    public ShoppingCartView(User user) {
        this.user = user;

        setTitle("Shopping Cart");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize cart items from the user's shopping cart
        List<Product> cartItems = user.getShoppingCart().getProductsInCart();

        // Create a table model with columns for product name, quantity, and price
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Product", "Quantity", "Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };

        // Create a map to store the total quantity and price for each product
        Map<String, Integer> totalQuantities = new HashMap<>();
        Map<String, Double> totalPrices = new HashMap<>();

        // Calculate the total quantity and price for each product
        for (Product product : cartItems) {
            String productID = product.getProductID();
            int quantity = totalQuantities.getOrDefault(productID, 0) + 1;
            double price = totalPrices.getOrDefault(productID, 0.0) + product.getProductPrice();
            totalQuantities.put(productID, quantity);
            totalPrices.put(productID, price);
        }

        // Populate the table model with the total quantities and prices
        for (Map.Entry<String, Integer> entry : totalQuantities.entrySet()) {
            String productID = entry.getKey();
            Product product = findProductById(productID);
            String productInfo = getProductInfoByCategory(product);
            int quantity = entry.getValue();
            double price = totalPrices.get(productID);
            tableModel.addRow(new Object[]{productInfo, quantity, price});
        }

        // Create the cart table with the table model
        cartTable = new JTable(tableModel);

        // Set the row height for the table
        cartTable.setRowHeight(100); // Adjust the row height as needed


        class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {
            MultiLineTableCellRenderer() {
                setLineWrap(true);
                setWrapStyleWord(true);
                setOpaque(true);
                setEditable(false); // Make the text area non-editable
            }

            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                setText((value == null) ? "" : value.toString());

                // Calculate the preferred height for the row based on the text content
                int preferredHeight = getPreferredSize().height;
                if (table.getRowHeight(row) != preferredHeight) {
                    // Set the row height to the preferred height if it's different
                    table.setRowHeight(row, preferredHeight);
                }

                // Center the text both horizontally and vertically within the cell
                setAlignmentX(JTextArea.CENTER_ALIGNMENT);
                setAlignmentY(JTextArea.CENTER_ALIGNMENT);

                return this;
            }
        }



        // Set custom cell renderer for the product column
        MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer();
        cartTable.getColumnModel().getColumn(0).setCellRenderer(renderer);



        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 1; i < cartTable.getColumnCount(); i++) {
            cartTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Create a scroll pane for the cart table
        JScrollPane tableScrollPane = new JScrollPane(cartTable);

        // Calculate the total price of all products in the cart
        double totalPrice = calculateTotalPrice(cartItems);

        // Update the total label with the calculated total price
        totalLabel = new JLabel("Total: $" + totalPrice);

        discount1Label = new JLabel("Three item in the same category Discount (20%): $-0");
        discount2Label = new JLabel("First time purchase discount (10%): $-0");
        finalTotalLabel = new JLabel("Final Total: $" + totalPrice);


        if (hasThreeItemsInSameCategory(cartItems)) {
            // Apply a discount if the condition is met
            double discount = 0.2; // 20% discount
            double discountedTotal = totalPrice * (1 - discount);
            discount1Label.setText("Three item in the same category Discount (20%): -$" + ( totalPrice * discount));
            finalTotalLabel.setText("Final Total: $" + discountedTotal);
        }


        double discountedTotal=0;
        if (isFirstTimePurchase(user)) {
            // Apply a discount if the condition is met
            double discount = 0.1; // 10% discount
            discountedTotal = totalPrice * (1 - discount);
            discount2Label.setText("First time purchase discount (10%): -$" + ( totalPrice * discount));

        }
        finalTotalLabel.setText("Final Total: $" + discountedTotal);

        // Create a panel for the total label
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.Y_AXIS));

        totalPanel.add(totalLabel);
        totalPanel.add(discount1Label);
        totalPanel.add(discount2Label);
        totalPanel.add(finalTotalLabel);


        // Create an exit button
        JButton purchaseButton = new JButton("Purchase");
        purchaseButton.addActionListener(e -> {
            // Remove all products from the shopping cart
            user.getShoppingCart().getProductsInCart().clear();
            user.setBuyed(true);
            // Close the shopping cart window
            JOptionPane.showMessageDialog(null, "Purchase Successful!");
            dispose();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    dispose();
            }});

        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        exitPanel.add(purchaseButton);

        totalPanel.add(exitPanel);
        // Add components to the frame
        add(tableScrollPane, BorderLayout.CENTER);
        add(totalPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }





    private double calculateTotalPrice(List<Product> products) {
        double totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getProductPrice();
        }
        return totalPrice;
    }

//    public static void main(String[] args) {
//        // Example usage:
//        User user = new User("username", "password");
//        user.setShoppingCart(new console.ShoppingCart());// Replace with actual user data
//        // Add shopping cart items to the user (replace with actual shopping cart items)
//        user.getShoppingCart().addProduct(new Electronic("1","samsung",200.00,"Electronic",3,"Samsung","2"));
//        user.getShoppingCart().addProduct(new Electronic("1","samsung",200.00,"Electronic",3,"Samsung","2"));
//        user.getShoppingCart().addProduct(new Electronic("1","samsung",200.00,"Electronic",3,"Samsung","2"));
//        user.getShoppingCart().addProduct(new Clothing("2","samsung",300.00,"Clothing",3,"s","white"));
//
//        SwingUtilities.invokeLater(() -> {
//            ShoppingCartView shoppingCartView = new ShoppingCartView(user);
//            shoppingCartView.setVisible(true);
//        });
//    }

    private String getProductInfoByCategory(Product product) {



        String category = product.getProductType();
        String info = "";



        if (category.equals("Clothing") && product instanceof Clothing) {
            Clothing clothing = (Clothing) product;
            info = clothing.getProductID()+"\n" + clothing.getProductName()+"\n"+ clothing.getSize() + "\n" + clothing.getColour();
        } else if (category.equals("Electronic") && product instanceof Electronic) {
            Electronic electronics = (Electronic) product;
            info = electronics.getProductID()+"\n"+electronics.getProductName()+"\n"+electronics.getBrand() + "\n" + electronics.getWarrantyPeriod() + " weeks warranty";
        }
        return info;
    }

    public Product findProductById(String productId) {
        // Iterate through the cart items to find the product with the matching ID
        for (Product product : user.getShoppingCart().getProductsInCart()) {
            if (product.getProductID().equals(productId)) {
                return product; // Return the product if found
            }
        }
        return null; // Return null if the product ID is not found in the shopping cart
    }

    private boolean hasThreeItemsInSameCategory(List<Product> cartItems) {
        Map<String, Integer> categoryCounts = new HashMap<>();
        for (Product product : cartItems) {
            String category = product.getProductType();
            categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
        }
        return categoryCounts.values().stream().anyMatch(count -> count >= 3);
    }

    private boolean isFirstTimePurchase(User user)
    {
        return !user.getBuyed();
    }
}
