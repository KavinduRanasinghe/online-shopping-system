package console;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private ShoppingCart shoppingCart ;
    private Boolean isBuyed;
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public User(String userName,String password){
        this.username = userName;
        this.password = password;
    }

    public User(String username, String password, ShoppingCart shoppingCart) {
        this.username = username;
        this.password = password;
        this.shoppingCart = shoppingCart;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getBuyed() {
        return isBuyed;
    }

    public void setBuyed(Boolean buyed) {
        isBuyed = buyed;
    }


}
