package console;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterShoppingManagerTest {

    @Test
    void addingElectronicProduct() {

            Product electronic = new Electronic("123", "TV", 1000.00,"Electronic", 10, "Samsung", "2 Years");

            assertEquals("123", electronic.getProductID());
            assertEquals("TV", electronic.getProductName());
            assertEquals(1000.00, electronic.getProductPrice());
            assertEquals("Electronic", electronic.getProductType());
            assertEquals(10, electronic.getNumberOfAvailableItems());
            assertEquals("Samsung", ((Electronic) electronic).getBrand());
            assertEquals("2 Years", ((Electronic) electronic).getWarrantyPeriod());
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        assertEquals(true, manager.addingElectronicProduct("123", "TV", 1000.00, 10, "Samsung", "2 Years"));
    }

    @Test
    void addingClothingProduct() {

            Product clothing = new Clothing("123", "T-Shirt", 1000.00,"Clothing", 10, "M", "Black");

            assertEquals("123", clothing.getProductID());
            assertEquals("T-Shirt", clothing.getProductName());
            assertEquals(1000.00, clothing.getProductPrice());
            assertEquals("Clothing", clothing.getProductType());
            assertEquals(10, clothing.getNumberOfAvailableItems());
            assertEquals("M", ((Clothing) clothing).getSize().toUpperCase());
            assertEquals("Black", ((Clothing) clothing).getColour());

        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        assertEquals(true, manager.addingClothingProduct("123", "T-Shirt", 1000.00, 10, "M", "Black"));
    }



    @Test
    void deleteProduct() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.addingClothingProduct("123", "T-Shirt", 1000.00, 10, "M", "Black");
        assertEquals("Product 123 deleted successfully. Total products left: 0", manager.deleteProduct("123"));
    }

    void deleteProduct1() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        assertEquals("Product 123 not found in the system.", manager.deleteProduct("123"));
    }



    @Test
    void printAllProducts() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.addingClothingProduct("123", "T-Shirt", 1000.00, 10, "M", "Black");
        assertEquals("Successfully Printed all the Products", manager.printAllProducts());
    }

    @Test
    void saveFile() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.addingClothingProduct("123", "T-Shirt", 1000.00, 10, "M", "Black");
        assertEquals("Successfully saved to the file", manager.saveFile());
    }
    @Test
    void saveFile1() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.addingClothingProduct("123", "T-Shirt", 1000.00, 10, "M", "Black");
        assertEquals("Error in saving the file", manager.saveFile());
    }

    @Test
    void readFile() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.addingClothingProduct("123", "T-Shirt", 1000.00, 10, "M", "Black");
        assertEquals("Successfully read to the file", manager.readFile());
    }

    @Test
    void readFile1(){
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.addingClothingProduct("123", "T-Shirt", 1000.00, 10, "M", "Black");
        assertEquals("Error in loading the file", manager.readFile());
    }
}