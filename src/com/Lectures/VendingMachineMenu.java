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

    /**??????????????????????????????????
     * This is the constructor fot the class
     * scanner is used to get CL input from the user.
     * @param vendingMachine is the vendingMachine object. The menu controls that object's methods
     * adminMenu is a hidden menu that has privileged functionality. It is implemented by creating a
     * new instance of the AdminClass.
     */
    public VendingMachineMenu(VendingMachine vendingMachine){
        scanner = new Scanner(System.in);
        this.vendingMachine = vendingMachine;
        this.adminMenu = new AdminMenu(vendingMachine);
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
            System.out.println("Enter your choice\n" +
                    "__________________\n" +
                    "\t1) Show all products\n" +
                    "\t2) Buy product\n" +
                    "\t8) Admin Mode\n");

            //user input is assigned to a String
            userInput = scanner.nextLine();

            //userInput is checked against a pattern to see if it's in range
            if(userInput.matches(pattern)){
                //choice is assigned the userInput given. It's then passed to the handleInput() method
                //which will call a method based on what number choice is
                int choice = Integer.parseInt(userInput);
                handleInput(choice);
            }else {
                System.out.println("Please enter a selection, between 1 and 8 inclusive");
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
            case 2:
                //check account using verify class
                //if false, break
                String pattern = "^[A-Fa-f][1-4]$";
                System.out.println("Enter product location in the format \"A1\"");
                String location = scanner.nextLine();
                //pass user details too
                if(location.matches(pattern)) {
                    //check is in stock
                    //if(vendingMachine.checkIsInStock(location)) {
                    try {
                        vendingMachine.buyProduct(location);
                    }catch (VendingException e){
                        System.out.println(e.getMessage());
                    }
                }else{
                    System.out.println("Incorrect product location");
                }
                break;
            case 8:
                //Enters into adminMode by calling the displayMenu() method on the object
                System.out.println("Admin mode");
                adminMenu.displayMenu();
                break;
            //default output if the user gives incorrect input
            default:
                System.out.println("Incorrect input given");
                break;
        }
    }


}
