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
            System.out.println("Buying");
            //check account using verify class
            //if false, break
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

/**
 * This method is passed the index of an element in an array.
 * The method converts an integer to a String representing a location in the vending machine.
 * E.g. when passed 0, "A1" is returned. When passed 4, "B1" is returned
 */
   public String convertToLocation(int index){
       String location = "";
       int letter = index/4;

       switch (letter){
           case 0:
               location = "A";
               break;
           case 1:
               location = "B";
               break;
           case 2:
               location = "C";
               break;
           case 3:
               location = "D";
               break;
           case 4:
               location = "E";
               break;
           case 5:
               location = "F";
               break;
       }

       location += (index%4)+1;
       return location;
   }
}
