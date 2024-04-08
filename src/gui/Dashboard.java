package gui;

import console.*;
import console.ShoppingCart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends JFrame {
    private JComboBox<String> productTypeComboBox;
    private JTable productTable;
    private JTextArea productDetailsTextArea;

    private JButton shoppingCartButton;


    private List<Product> productList = WestminsterShoppingManager.productList;


    private User user;

    List<User> existingUsers;

    public Dashboard(User shoppingUser) {



        setTitle("Westminster Shopping Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window



        // Create a panel for the GUI components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Create a panel for the top section (combo box, label, and shopping cart button) with BoxLayout
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));


        // Create a label for the product category selection and add it to the top panel
        JLabel categoryLabel = new JLabel("Select Product Category:");
        topPanel.add(categoryLabel);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add horizontal space

        // Create a dropdown menu for product types and add it to the top panel
        String[] productTypes = {"All", "Electronic", "Clothing"};
        productTypeComboBox = new JComboBox<>(productTypes);
        topPanel.add(productTypeComboBox);
        topPanel.add(Box.createHorizontalGlue()); // Add flexible space to push the shopping cart button to the right

        // Create a shopping cart button and add it to the top panel
        shoppingCartButton = new JButton("Shopping Cart");
        topPanel.add(shoppingCartButton);

        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(user.getShoppingCart().getProductsInCart().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Shopping cart is empty");
                    }
                    else
                    {
                        ShoppingCartView shoppingCartView = new ShoppingCartView(user);
                        shoppingCartView.setVisible(true);
                    }
                }
                catch (NullPointerException ex){
                    JOptionPane.showMessageDialog(null, "Shopping cart is empty");
                }
            }
        });

        mainPanel.add(topPanel, BorderLayout.NORTH);


        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding to the details panel

        if (productList != null && !productList.isEmpty()) {
            // Create a table to display products
            String[] columnNames = {"ID", "Name", "Category", "Price", "Info"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            productTable = new JTable(tableModel);
            for (Product product : productList) {
                Object[] rowData = {
                        product.getProductID(),
                        product.getProductName(),
                        product.getProductType(),
                        product.getProductPrice(),
                        getProductInfoByCategory(product)

                };
                tableModel.addRow(rowData);
            }

            // Set the table model to the JTable
            productTable.setModel(tableModel);
            JScrollPane tableScrollPane = new JScrollPane(productTable);
            mainPanel.add(tableScrollPane);

            // Create a text area for product details
            productDetailsTextArea = new JTextArea(10, 40);

            // Create a scroll pane for the text area with padding
            JScrollPane detailsScrollPane = new JScrollPane(productDetailsTextArea);
            detailsScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding to the scroll pane

            detailsPanel.add(detailsScrollPane, BorderLayout.CENTER);

            productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        int selectedRow = productTable.getSelectedRow();
                        if (selectedRow != -1) {
                            // Get the selected product from the table model
                            DefaultTableModel model = (DefaultTableModel) productTable.getModel();
                            String productID = (String) model.getValueAt(selectedRow, 0);
                            Product selectedProduct = getProductByID(productID);

                            // Update the product details in the text area
                            productDetailsTextArea.setText(getProductDetails(selectedProduct));
                        }
                    }
                }
            });

            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    String productID = model.getValueAt(row, 0).toString(); // Assuming product ID is in the first column

                    // Retrieve the product from the productList based on the product ID
                    Product product = getProductByID(productID);

                    // Check if the product exists and has reduced availability
                    if (product != null && product.getNumberOfAvailableItems() < 3) {
                        c.setForeground(Color.RED);
                    } else {
                        c.setForeground(table.getForeground());
                    }
                    return c;
                }
            };

            productTable.setDefaultRenderer(Object.class, cellRenderer);

            productTypeComboBox.addActionListener(e -> {
                productDetailsTextArea.setText("");
                String selectedCategory = (String) productTypeComboBox.getSelectedItem();
                updateProductTable(selectedCategory);
            });

        } else {

            // Display a label indicating "No products found"
            JLabel noProductsLabel = new JLabel("No products found");
            noProductsLabel.setFont(new Font("Arial", Font.BOLD, 28)); // Set the font size to 20
            noProductsLabel.setHorizontalAlignment(JLabel.CENTER); // Center align the label

            // Add negative bottom insets to move the label up
            noProductsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 60, 0)); // Adjust the value (-20) as needed

            detailsPanel.add(noProductsLabel, BorderLayout.CENTER);
        }

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        if (productList != null && !productList.isEmpty()) {
            // Create a button to add products to the shopping cart
            JButton addToCartButton = new JButton("Add to Shopping Cart");

            // Create a panel for the button and center it horizontally
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(addToCartButton);


            addToCartButton.addActionListener(e -> {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the selected product from the table model
                    DefaultTableModel model = (DefaultTableModel) productTable.getModel();
                    String productID = (String) model.getValueAt(selectedRow, 0);
                    Product selectedProduct = getProductByID(productID);

                    if (selectedProduct != null && selectedProduct.getNumberOfAvailableItems() > 0) {
                        // Add the product to the user's shopping cart
                        if(shoppingUser.getShoppingCart() == null){
                            shoppingUser.setShoppingCart(new ShoppingCart());
                        }

                        shoppingUser.getShoppingCart().addProduct(selectedProduct);
                        selectedProduct.setNumberOfAvailableItems(selectedProduct.getNumberOfAvailableItems() - 1);

                        // Save the updated user information (including the shopping cart) into a text file
                        saveUserToFile(shoppingUser);

                        user = shoppingUser;

                        JOptionPane.showMessageDialog(null, "Product added to shopping cart");
                    } else {
                        JOptionPane.showMessageDialog(null, "Not enough items available in the product.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a product to add to the shopping cart.");
                }
            });
            // Add the button panel to the main panel
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        }

        JPanel exitButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            int confirmed = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to exit the application?", "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                dispose(); // Close the JFrame
            }
        });
        exitButtonPanel.add(exitButton);

        mainPanel.add(exitButtonPanel, BorderLayout.SOUTH);

        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        // Add the main panel to the frame
        add(mainPanel);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }


    private void updateProductTable(String selectedCategory) {
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Category", "Price", "Info"}, 0);

        if ("All".equals(selectedCategory)) {
            for (Product product : productList) {
                Object[] rowData = {
                        product.getProductID(),
                        product.getProductName(),
                        product.getProductType(),
                        product.getProductPrice(),
                        getProductInfoByCategory(product)
                };
                tableModel.addRow(rowData);
            }
        } else {
            for (Product product : productList) {
                if (product.getProductType().equals(selectedCategory)) {
                    Object[] rowData = {
                            product.getProductID(),
                            product.getProductName(),
                            product.getProductType(),
                            product.getProductPrice(),
                            getProductInfoByCategory(product)
                    };
                    tableModel.addRow(rowData);
                }
            }
        }

        productTable.setModel(tableModel);
    }


    private String getProductInfoByCategory(Product product) {
        String category = product.getProductType();
        String info = "";

        if (category.equals("Clothing") && product instanceof Clothing) {
            Clothing clothing = (Clothing) product;
            info = clothing.getSize() + ", " + clothing.getColour();
        } else if (category.equals("Electronic") && product instanceof Electronic) {
            Electronic electronics = (Electronic) product;
            info = electronics.getBrand() + ", " + electronics.getWarrantyPeriod() + " weeks warranty";
        }

        return info;
    }

    private String getProductDetails(Product product) {
        if (product == null) {
            return "No product selected";
        }

        String details = "Selected product details\n\n";
        details += "Product ID: " + product.getProductID() + "\n";
        details += "Category: " + product.getProductType() + "\n";
        details += "Name: " + product.getProductName() + "\n";

        if (product instanceof Clothing) {
            Clothing clothing = (Clothing) product;
            details += "Size: " + clothing.getSize() + "\n";
            details += "Colour: " + clothing.getColour() + "\n";
        } else if (product instanceof Electronic) {
            Electronic electronics = (Electronic) product;
            details += "Brand: " + electronics.getBrand() + "\n";
            details += "Warranty: " + electronics.getWarrantyPeriod() + "\n";
        }

        details += "Items available: " + product.getNumberOfAvailableItems() + "\n";

        return details;
    }

    private Product getProductByID(String productID) {
        for (Product product : productList) {
            if (product.getProductID().equals(productID)) {
                return product;
            }
        }
        return null; // Return null if no matching product is found
    }

    private void saveUserToFile(User user) {


        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("usersInfo.txt"))) {
            boolean userExists = false;

            if (user.getBuyed() == null || !user.getBuyed()) {
                user.setBuyed(false);
            }
            else {
                user.setBuyed(true);
            }

            if( readUsersFromFile() != null){

                existingUsers = readUsersFromFile();

                for (User existingUser : existingUsers) {
                    if (existingUser.getUsername().equals(user.getUsername())) {
                        // Update the shopping cart for the existing user
                        existingUser.setShoppingCart(user.getShoppingCart());
                        userExists = true;
                        break;
                    }
                }

                if (!userExists) {
                    // If the user does not exist, save the entire user object
                    existingUsers.add(user);
                }

                // Write the updated list of users back to the file
                for (User u : existingUsers) {
                    outputStream.writeObject(u);
                }
            }else{
                outputStream.writeObject(user);
            }


        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving user to file: " + e.getMessage());
        }
    }


    public static List<User> readUsersFromFile() {
        List<User> users = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("usersInfo.txt"))) {
            Object obj;
            while ((obj = inputStream.readObject()) != null) {
                if (obj instanceof User) {
                    users.add((User) obj);
                }
            }
        } catch (EOFException e) {
            // End of file reached, no more objects to read
        } catch (IOException | ClassNotFoundException e) {
            return null; // Handle exceptions appropriately
        }
        return users;
    }




}
