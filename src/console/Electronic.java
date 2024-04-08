package console;

public class Electronic extends Product{

    private String brand;
    private String warrantyPeriod;

    public Electronic(String brand, String warrantyPeriod) {
        super();
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public Electronic(String productID, String productName, double productPrice, String productType, int numberOfAvailableItems, String brand, String warrantyPeriod) {
        super(productID, productName, productPrice, numberOfAvailableItems, productType);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public Electronic() {
        super();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String getProductInfo() {
        return super.getProductInfo() +"\n"+
                "brand=" + brand + '\n' +
                 "warrantyPeriod="+warrantyPeriod;
    }
}
