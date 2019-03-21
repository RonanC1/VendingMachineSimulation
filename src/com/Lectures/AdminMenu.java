/**
 * This class provides an Admin Menu for the VendingMachine class.
 * The class prints a menu, takes user input and calls methods based on the user input.
 * The class implements the Menu Interface
 */

package com.Lectures;

import java.util.Scanner;

public class AdminMenu implements Menu {
    private Scanner scanner;
    private VendingMachine vendingMachine;
    private boolean quit;

    /**
     * This is the constructor fot the class
     * scanner is used to get CL input from the user
     * @param vendingMachine is the vendingMachine object that is using this class
     * boolean quit is used to exit the do while loop in displayMenu()
     */
    public AdminMenu(VendingMachine vendingMachine){
        scanner = new Scanner(System.in);
        this.vendingMachine = vendingMachine;
        quit = false;
    }

    /**
     * This method prints an Admin Menu to the command line.
     * The menu is bound to a loop, and will only stop looping once the vendingMachine is turned off or
     * until quit is selected, which will revert back to the Menu in the VendingMachineMenu class
     */
    public void displayMenu(){
        //RegEx for checking against user input
        String pattern = "^[1-8]$";
        String userInput;
        do{
            quit = false;
            System.out.println("Enter your choice\n" +
                    "__________________\n" +
                    "\t1) Show product quantities\n" +
                    "\t2) Add product\n" +
                    "\t7) Quit\n" +
                    "\t8) Power off");

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

        }while(!quit);
    }

    private void handleInput(int choice){
        String userInput;

        switch(choice){
            case 1:
                System.out.println("Show all quantities");
                break;
            case 2://??????????????????????????????????
                System.out.println("Enter product description, price, location and cost in the format \"description,0.00,A1,10\"");
                userInput = scanner.nextLine();
                String pattern = "^[A-Za-z]+,\\d\\.\\d\\d,[A-Za-z][1-4],[1-8]\\d?$";;

                if(userInput.matches(pattern)){
                    String[] array = userInput.split(",");
                    Product product = new Product(array[0], Double.parseDouble(array[1]), array[2]);

                    if(!vendingMachine.addProduct(product, Integer.parseInt(array[3]))){
                        System.out.println(array[0] + " not added successfully");
                    }else{
                        System.out.println(array[0] + " added successfully");
                    }

                }else{
                    System.out.println("Incorrect product format.");
                }


                System.out.println("Adding products");
                break;
            case 7:
                //changes boolean quit to true. This means the condition of the do while loop is met
                //and the admin menu will close
                System.out.println("Quitting admin mode");
                quit = true;
                break;
            case 8:
                System.out.println("Power off machine. Are you sure? Y/N");
                String answer = scanner.nextLine();
                if(answer.equalsIgnoreCase("Y")) {
                    System.out.println("Powering off");
                    quit = true;
                    vendingMachine.powerOff();
                }
                break;
            default:
                System.out.println("Incorrect input given");
                break;
        }
    }
}
