package com.Lectures;

import javafx.application.Application;

import java.io.IOException;
import java.util.Scanner;

/**
   This program simulates a vending machine on the command line, or as a GUI
*/
public class Main
{ 
   public static void main(String[] args) throws IOException {

       Scanner scanner = new Scanner(System.in);
       String userInput;

       do{
           System.out.println("- Enter 1 for command line" +
                   "\n- Enter 2 for GUI" +
                   "\n- Enter Q to quit");
           userInput = scanner.nextLine();

           if(userInput.equals("1")) {
               VendingMachine vendingMachine = new VendingMachine();
               VendingMachineMenu vendingMachineMenu = new VendingMachineMenu(vendingMachine);
               vendingMachine.powerOn(vendingMachineMenu);

           } else if(userInput.equals("2")) {
               Application.launch(VendingGUI.class);
           }
       }while(!userInput.equalsIgnoreCase("Q"));


  }
}
