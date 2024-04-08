package console;



public interface ShoppingManager {
    int maxItems = 50;


    boolean addingElectronicProduct(String productID, String productName, double productPrice, int numberOfAvailableItems, String brand, String warrantyPeriod);

    boolean addingClothingProduct(String productID, String productName, double productPrice, int numberOfAvailableItems, String size, String colour);

    String deleteProduct(String productID);
    String printAllProducts();
    String saveFile();

    String readFile();
}
