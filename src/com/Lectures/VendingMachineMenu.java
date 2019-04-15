/**
 * This class provides a menu for the VendingMachine class.
 * The class prints a menu, takes user input and calls methods based on the user input.
 * The class implements the Menu Interface, and has an admin menu object
 */
package com.Lectures;

import java.util.List;
import java.util.Scanner;

public class VendingMachineMenu implements Menu {
    private Scanner scanner;
    private VendingMachine vendingMachine;
    private AdminMenu adminMenu;
    private boolean isLoggedIn;
    private Client client;

    /**
     * This is the constructor fot the class
     * scanner is used to get CL input from the user.
     * @param vendingMachine is the vendingMachine object. The menu controls that object's methods.
     * adminMenu is a hidden menu that has privileged functionality. It is implemented by creating a
     * new instance of the AdminMenu Class.
     */
    public VendingMachineMenu(VendingMachine vendingMachine){
        scanner = new Scanner(System.in);
        this.vendingMachine = vendingMachine;
        this.adminMenu = new AdminMenu(vendingMachine);
        this.isLoggedIn = false;
        this.client = null;

    }

    /**
     * This method prints a Menu to the command line.
     * The menu is bound to a loop, and will only stop looping once the vendingMachine is turned off.
     * This is determined by the vendingMachine.isOn(), which returns the power state of the vendingMachine
     */
    public void displayMenu(){
        //RegEx for checking against user input
        String pattern = "^[1-8]$";
        String userInput;

        do{
            System.out.println("\n\tVending Machine Menu\n" +
                    "____________________________\n" +
                    "Enter your choice\n" +
                    "\t1) Show all products\n" +
                    "\t2) Login / Logout\n" +
                    "\t3) Buy product\n" +
                    "\t4) Admin Mode\n");

            //user input is assigned to a String
            userInput = scanner.nextLine();

            //userInput is checked against a pattern to see if it's in range
            if(userInput.matches(pattern)){
                //choice is assigned the userInput given. It's then passed to the handleInput() method
                //which will call a method based on what number choice is
                int choice = Integer.parseInt(userInput);
                handleInput(choice);
            }else {
                System.out.println("Please enter a selection, between 1 and 4 inclusive");
            }

        }while(vendingMachine.isOn());
    }

    /**
     * This method takes user input and calls the corresponding method by using a switch statement
     * @param choice is the input number given by the user from the menu
     */
    private void handleInput(int choice){
        switch(choice){
            //Print a list of products and their prices
            case 1:
                //We get an arrayList of type String containing all products and their prices by calling
                //getAllProducts(). Using a for loop, we can print each element of that list. The method
                //convertToLocation() is also called which converts i into a location. E.g. 0 -> A1
                List<String> allProducts = vendingMachine.getAllProducts();
                for(int i = 0; i < allProducts.size(); i++){
                    System.out.println(allProducts.get(i));
                }
                break;
            //Login / Logout
            case 2:
                if(!isLoggedIn) {
                    System.out.println("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();

                    client = vendingMachine.validateClient(username, password);

                    //getCredentials will get user details, verify them, and return true if they are found in the Verification object
                    if (client != null) {//////ADD CHECK BALANCE
                        isLoggedIn = true;
                        System.out.println(client.getUsername() + " logged in. Your balance is €" + client.getBalance());


                    } else {
                        System.out.println("Username or password is incorrect.");
                    }
                }else {
                    isLoggedIn = false;
                    System.out.println(client.getUsername() + " logged out successfully.");
                }
                break;
            //Buy Product
            case 3:
                //check account using verify class
                //if false, break

                if(!isLoggedIn){
                    System.out.println("No user currently logged in. Log in to purchase a product");
                    break;
                }

                String pattern = "^[A-Da-d][1-4]$";
                System.out.println("Enter product location in the format \"A1\"");
                String location = scanner.nextLine();

                double price = -1;
                try {
                    price = vendingMachine.getPrice(location);
                }catch (VendingException e){
                    System.out.println(e.getMessage());
                }

                if (location.matches(pattern) && price > -1) {

                    try {
                        client.updateBalance(price);
                        //if client does not have enough credit in their balance, the next line will not be executed
                        String pInfo = vendingMachine.buyProduct(location);
                        System.out.println(client.getUsername() + " purchased a " + pInfo + "\nYour balance is €" + client.getBalance());
                    } catch (VendingException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
            //Admin mode
            case 4:
                //Enters into adminMode by calling the displayMenu() method on the object
                System.out.println("Enter username: ");
                String username = scanner.nextLine();
                System.out.println("Enter password: ");
                String password = scanner.nextLine();

                if(vendingMachine.validateAdmin(username, password)) {
                    adminMenu.displayMenu();
                }else{
                    System.out.println("Incorrect Admin credentials");
                }
                break;
            //default output if the user gives incorrect input
            default:
                System.out.println("Incorrect input given");
                break;
        }
    }


}
