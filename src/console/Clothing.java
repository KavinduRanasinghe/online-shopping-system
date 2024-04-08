package console;

public class Clothing extends Product{
    private String size;
    private String colour;

    public Clothing(String size, String colour) {
        super();
        this.size = size;
        this.colour = colour;
    }

    public Clothing(String productID, String productName, double productPrice,String productType, int numberOfAvailableItems, String size, String colour) {
        super(productID, productName, productPrice, numberOfAvailableItems, productType);
        this.size = size;
        this.colour = colour;
    }

    public Clothing() {
        super();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String getProductInfo() {
        return  "Clothing Product\n"+super.getProductInfo() +"\n"+
                "size=" + size + '\n' +
                "colour=" + colour + '\n';
    }
}
