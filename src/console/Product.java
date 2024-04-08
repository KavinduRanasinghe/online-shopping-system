package console;

import java.io.Serializable;

public class Product implements Serializable {
    static int maxItems = 50;


    private String productID;
    private String productName;
    private double productPrice;
    private int numberOfAvailableItems;

    private String productType;

    public Product() {}
    public Product(String productID, String productName, double productPrice, int numberOfAvailableItems, String productType) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.numberOfAvailableItems = numberOfAvailableItems;
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getNumberOfAvailableItems() {
        return numberOfAvailableItems;
    }

    public void setNumberOfAvailableItems(int numberOfAvailableItems) {
        this.numberOfAvailableItems = numberOfAvailableItems;
    }

    public String getProductInfo() {
        return "Product ID=" + productID + '\n' +
                "Product Name=" + productName + '\n' +
                "Product Price=" + productPrice
                ;
    }


}
