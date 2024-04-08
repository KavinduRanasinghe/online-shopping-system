package console;

import gui.Dashboard;
import gui.LoginFrame;

import java.util.Scanner;

import static console.Product.maxItems;
import static console.WestminsterShoppingManager.productList;

public class ConsoleMenu {
    public static void main(String[] args) {
        System.out.println("\nWelcome to Westminster Shopping Manager\n");
        Scanner sc = new Scanner(System.in);
        consoleMenu();
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        String option;
        Dashboard dashboard = null;
        LoginFrame loginFrame = null;
        manager.readFile();
        do {

            System.out.print("\nChoose an option [menu -> console Menu] : ");
            option = sc.next();

            switch (option.toLowerCase()) {
                case "add":
                    System.out.println(manager.addProduct());

                    break;

                case "delete":
                    System.out.println("Please Enter the product ID");
                    String productID = sc.next();
                    System.out.println(manager.deleteProduct(productID));
                    break;

                case "save":
                    System.out.println(manager.saveFile());
                    break;

                case "load":
                    System.out.println( manager.readFile());
                    break;

                case  "print":
                    System.out.println(manager.printAllProducts());
                    break;

                case "gui":
                    if (loginFrame != null) {
                        loginFrame.dispose();
                    }
                    loginFrame = new LoginFrame();



                    break;
                case "menu":
                    consoleMenu();
                    break;
            }
        }
        while (!option.equals("exit"));

//        if (dashboard != null) {
//            dashboard.dispose();
//        }
    }

    public static void consoleMenu() {
        System.out.println("""
                       ******  Console  ******
                            
                   Menu                              menu
                   Add a new product                 add
                   Delete a product                  delete
                   Print the list of the products    print
                   Save in a file                    save
                   Load the data                     load
                   GUI                               gui
                   Exit                              exit     
                                    
                """);
    }
}

