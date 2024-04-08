package console;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Serializable {
    private List<Product> productsInCart = new ArrayList<>();


    public List<Product> getProductsInCart() {
        return productsInCart;
    }

    public void setProductsInCart(List<Product> productsInCart) {
        this.productsInCart = productsInCart;
    }

    public String addProduct(Product product) {
        productsInCart.add(product);
        return "Successfully Product Added";
    }


//    public String removeProduct(String productID) {
//        for (Product product : productsInCart) {
//            if (product.getProductID().equals(productID)) {
//                productsInCart.remove(product);
//                return "Product " + productID + " removed successfully.";
//
//
//            }
//        }
//        return "Product " + productID + " not found (404)";
//
//    }


//    public double totalCost() {
//        double cost = 0;
//        for (Product p : productsInCart) {
//            cost += p.getProductPrice();
//        }
//
//        return cost;
//
//    }


}





