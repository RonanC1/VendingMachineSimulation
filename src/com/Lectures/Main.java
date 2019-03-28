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

//       Verification verification = new Verification();
//       verification.loadUsers();
//       verification.printClients();
//       verification.printAdmins();
//       verification.addTo("Shep","domino",23.55);
//       verification.printClients();
//       verification.saveClients();
//       vendingMachine.addProduct("Mars", 1.70, "A1", 1);
//       vendingMachine.addProduct("Snickers", 1.70, "A2", 3);
//       vendingMachine.addProduct("Fanta", 2, "A3", 2);
//       vendingMachine.addProduct("Coke", 2, "C1", 10);

       vendingMachine.powerOn(vendingMachineMenu);

      // vendingMachine.getAllProductLocations();

//       for(int i = 0; i < 24; i++){
//           if(i%4 == 0){
//               System.out.println(i);
//           }
//       }

  }
}
