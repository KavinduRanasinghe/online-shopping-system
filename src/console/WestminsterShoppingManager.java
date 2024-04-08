package console;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager {
    public static List<Product> productList = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    /**
     * This method is used to add a new product to the system
     *
     * @return the message of the status of the adding
     */
    public String addProduct() {
        if (maxItems < productList.size()) {
            return "Maximum number of products reached";
        } else {
            String choice;

            do {
                System.out.println("The type of the product [clothing /electronics]\n1. Clothing   - > c \n2. Electronic - > e");
                System.out.print("Enter your choice : ");

                choice = sc.next();

                switch (choice.toLowerCase()) {

                    case "e":


                        System.out.print("Electronic item ID              : ");
                        String electronicID = sc.next();
                        for (Product product : productList) {
                            if (product.getProductID().equals(electronicID)) {
                                return "Product ID already exists";
                            }
                        }

                        System.out.print("Electronic item Name            : ");
                        String electronicName = sc.next();

                        System.out.print("Electronic item Price           : ");
                        double electronicPrice = validatePrice();

                        System.out.print("Electronic item Brand           : ");
                        String brand = sc.next();

                        System.out.print("Electronic item Warranty Period : ");
                        String warrantyPeriod = sc.next();


                        System.out.print("Electronic item Quantity         : ");
                        int quantity = validateInt();

                        addingElectronicProduct(electronicID, electronicName, electronicPrice, quantity, brand, warrantyPeriod);
                        System.out.println();
                        return "Successfully added an Electronic Item";

                    case "c":


                        System.out.print("Clothing item ID                  : ");

                        String clothingID = sc.next();
                        for (Product product : productList) {
                            if (product.getProductID().equals(clothingID)) {
                                return "Product ID already exists";
                            }
                        }

                        System.out.print("Clothing item name                : ");

                        String clothingName = sc.next();

                        System.out.print("Clothing item Price               : ");
                        double clothingPrice = validatePrice();

                        String size;
                        while (true) {
                            System.out.println("** Size chart **\nXS - Extra Small\n S - Small\n M - Medium\n L - Large\n XL - Extra Large\n");
                            System.out.print("Select the clothing item Size     : ");
                            String s = sc.next();
                            if (s.equalsIgnoreCase("XS") || s.equalsIgnoreCase("S") || s.equalsIgnoreCase("M") || s.equalsIgnoreCase("L") || s.equalsIgnoreCase("XL")) {
                                size = s;
                                break;
                            }
                        }


                        System.out.print("Clothing item Colour              : ");
                        String colour = sc.next();

                        System.out.print("Clothing Item Quantity            : ");
                        int productQuantity = validateInt();

                        addingClothingProduct(clothingID, clothingName, clothingPrice, productQuantity, size, colour);
                        System.out.println();
                        return "Successfully added an Clothing Item";

                    default:
                        System.out.println("Please enter a valid choice\n");
                        break;
                }
            }
            while (!choice.equals("c") || !choice.equals("e"));


        }
        return "Successfully added a product";
    }


    /**
     * This method is used to add a new electronic product to the system
     *
     * @param productID              the id of the product to be added
     * @param productName            the name of the product to be added
     * @param productPrice           the price of the product to be added
     * @param numberOfAvailableItems the quantity of the product to be added
     * @param brand                  the brand of the product to be added
     * @param warrantyPeriod         the warranty period of the product to be added
     * @return true when the electronic product is added successfully else false
     */
    @Override
    public boolean addingElectronicProduct(String productID, String productName, double productPrice, int numberOfAvailableItems, String brand, String warrantyPeriod) {

        Electronic electronics = new Electronic(productID, productName, productPrice, "Electronic", numberOfAvailableItems, brand, warrantyPeriod);
        productList.add(electronics);
        return true;
    }

    /**
     * This method is used to add a new clothing product to the system
     *
     * @param productID              the id of the product to be added
     * @param productName            the name of the product to be added
     * @param productPrice           the price of the product to be added
     * @param numberOfAvailableItems the quantity of the product to be added
     * @param size                   the size of the product to be added
     * @param colour                 the colour of the product to be added
     *                               1
     * @return true when the clothing product is added successfully else false
     */
    @Override
    public boolean addingClothingProduct(String productID, String productName, double productPrice, int numberOfAvailableItems, String size, String colour) {
        Clothing clothing = new Clothing(productID, productName, productPrice, "Clothing", numberOfAvailableItems, size, colour);
        productList.add(clothing);

        return true;
    }

    /**
     * This method is used to delete a product from the system
     *
     * @param productId the id of the product to be deleted
     * @return the message of the status of the deletion
     */

    @Override
    public String deleteProduct(String productId) {
        for (Product product : productList) {
            if (product.getProductID().equals(productId)) {
                productList.remove(product);
                return "Product " + productId + " deleted successfully. Total products left: " + productList.size();
            }
        }
        return "Product " + productId + " not found in the system.";
    }

    /**
     * This method is used to print the details of a product
     *
     * @return the message of the status of the printing
     */
    @Override
    public String printAllProducts() {


        productList.sort(Comparator.comparing(Product::getProductID));

        if (productList.size() == 0) {
            return "No products available";
        }
        System.out.println("*** Product Information ***\n");
        for (Product product : productList) {
            if (product instanceof Electronic) {

                System.out.println("Product Type : " + product.getProductType() + "\n" + "Product ID :  " + product.getProductID() + "\n" + "Product Name : " + product.getProductName() + "\n" + "Product Price : " + product.getProductPrice() + "\n" + "Product Brand : " + ((Electronic) product).getBrand() + "\n" + "Product Warranty Period : " + ((Electronic) product).getWarrantyPeriod() + "\n" + "Product Quantity: " + product.getNumberOfAvailableItems() + "\n");
            }

            if (product instanceof Clothing) {

                System.out.println("Product Type : " + product.getProductType() + "\n" + "Product ID: " + product.getProductID() + "\n" + "Product Name: " + product.getProductName() + "\n" + "Product Price: " + product.getProductPrice() + "\n" + "Product Size: " + ((Clothing) product).getSize() + "\n" + "Product Colour: " + ((Clothing) product).getColour() + "\n" + "Product Quantity : " + product.getNumberOfAvailableItems() + "\n");
            }
        }
        return "Successfully Printed all the Products";
    }


    /**
     * This method is used to save the details of a product
     *
     * @return when the file saved successfully success message and when the file not saved error message
     */

    @Override
    public String saveFile() {

        try {
            FileOutputStream fos = new FileOutputStream("Product.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(productList);
            oos.close();
            fos.close();
            return "Successfully saved to the file";


        } catch (IOException e) {
            return "Error in saving to the file";
        }

    }


    /**
     * This method is used to read the details of a product
     *
     * @return when the file read successfully success message and when the file not read error message
     */


    public String readFile() {
        try {
            FileInputStream fis = new FileInputStream("Product.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            productList = (ArrayList<Product>) ois.readObject();
            ois.close();
            fis.close();
            return "Successfully read to the file";

        } catch (Exception e) {
            return "Error in reading to the file";
        }


    }

    /**
     * this method is used to validate the price and verify before adding to the system
     *
     * @return the price of the product
     */

    public double validatePrice() {
        double price = 0;
        boolean valid = false;
        while (!valid) {
            try {
                price = sc.nextDouble();
                valid = true;
            } catch (Exception e) {
                sc.next();
                System.out.println("Please enter a valid price");
            }
        }
        return price;
    }


    /**
     * this method is used to validate the quantity and verify before adding to the system
     *
     * @return the quantity of the product
     */
    public int validateInt() {
        int numberOfItems = 0;
        boolean valid = false;
        while (!valid) {
            try {
                numberOfItems = sc.nextInt();
                valid = true;
            } catch (Exception e) {
                sc.next();
                System.out.println("Please enter a valid Number of Items");

            }
        }
        return numberOfItems;
    }


}