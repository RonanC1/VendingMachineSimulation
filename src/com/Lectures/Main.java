package com.Lectures;

import java.io.IOException;
/**
   This program simulates a vending machine.
*/
public class Main
{ 
   public static void main(String[] args)
         throws IOException
   {
       VendingMachine vendingMachine = new VendingMachine();
       VendingMachineMenu vendingMachineMenu = new VendingMachineMenu(vendingMachine);

       vendingMachine.addProduct((new Product("Mars", 1.70, "A1")), 1);
       vendingMachine.addProduct((new Product("Snickers", 1.70, "A2")), 3);
       vendingMachine.addProduct((new Product("Fanta", 2, "A3")), 2);
       vendingMachine.addProduct((new Product("Coke", 2, "C1")), 10);

       vendingMachine.powerOn(vendingMachineMenu);

  }
}