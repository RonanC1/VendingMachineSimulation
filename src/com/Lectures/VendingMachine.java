package com.Lectures;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a vending machine. It has a list of type ProductLocation. Each instance of productLocation can
 * have an List of products. The machine has a power status. The class is controlled by the VendingMachine class
 */

public class VendingMachine {
    private List<ProductLocation> arrayOfLocations;
    private boolean poweredOn;
    private final int maxVendingSize;

    /**
     * Constructs a VendingMachine object.
     * Initializes an arrayList of ProductLocations, and initializes maxVendingSize ProductLocation
     * objects. Sets power status to false.
     */
    public VendingMachine()
    {
        arrayOfLocations = new ArrayList<ProductLocation>();
        maxVendingSize = 24;
        for(int i = 0; i < maxVendingSize; i++){
            arrayOfLocations.add(new ProductLocation());
        }
        poweredOn = false;
    }

    /**
     * This method turns the vending machine on. It is passed a vendingMachineMenu object
     *  as a parameter, then calls the displayMenu() method. The menu will display so long as the VendingMachine is on
     */
    public void powerOn(VendingMachineMenu vendingMachineMenu){
        System.out.println("Vending machine is powered on");
        poweredOn = true;
        vendingMachineMenu.displayMenu();
    }

    /**
     * This method turns the vending machine status to off. The menu will then stop displaying
     */
    public void powerOff(){
        System.out.println("Vending machine is off");
        poweredOn = false;
    }

    /**
     * returns the power status of the machine. Both menu objects depend on the power being on
     */
    public boolean isOn(){
        return this.poweredOn;
    }

    /**
     * Adds a product to the vending machine.
     * The method checks if the same Product type is in the specified location, or if that location is empty,
     * and will add accordingly and return true. If the productLocation does not contain the same type of Product, the method
     * returns false.
     * @param quantity the quantity
     */
    public boolean addProduct(String description, double price, String location, int quantity)
    {
        //convert Product location from alphaNumerical to a corresponding number by calling convertLocation
        //int index = convertLocation(product.getLocation());
        int index = convertLocation(location);
        //if -1 it cannot be converted. Return false
        if(index == -1){
            return false;
        }

        Product product = new Product(description, price, location);
        //Now we check if the location has products of the same type by calling isInLocation and passing the product
        //and the index we got from convertLocation().
        //If the specified location has Product objects of the same type we add the quantity of product objects.
        //Or if it's an empty location, we can add them
        if(isInLocation(product,index)){
            arrayOfLocations.get(index).addProductToLocation(description, price, location, quantity);
            return true;
        }else if(arrayOfLocations.get(index).getProductArrayList().isEmpty()){
            arrayOfLocations.get(index).addProductToLocation(description, price, location, quantity);
            return true;
        }
        //Otherwise, return false
        return false;
    }

    /**
     * This method checks if the ProductLocation in arrayOfLocations has the same product types
     * @param product is the product to be added
     * @param index the specified location form the user
     * @return true or false
     */
    private boolean isInLocation(Product product,int index){
        if(arrayOfLocations.get(index).isInProductLocation(product)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * This method converts the location given by the user to an integer which represents a location
     * in the List arrayOfLocations
     * @param location is the String passed by the user
     * @return index, the position in the array
     */
    private int convertLocation(String location){
        //split them up into a char and int
        //convert char to lowercase
        char letter = location.charAt(0);
        letter = Character.toLowerCase(letter);

        //reduce index by 1 as arrays start from 0
        String charToNumber = "" + location.charAt(1);
        int index = Integer.parseInt(charToNumber);
        index -= 1;
        //convert letter to a digit that's 0 or a multiple of 4
        //then add that digit to int index to get our index
        switch(letter) {
            case 'a':
                index += 0;
                break;
            case 'b':
                index += 4;
                break;
            case 'c':
                index += 8;
                break;
            case 'd':
                index += 12;
                break;
            case 'e':
                index += 16;
                break;
            case 'f':
                index += 20;
                break;

        }

        return index;
    }


    /**
     * Gets a String representation all product descriptions and price currently in the vending machine.
     * We only take the first element of the List in each ProductLocation, as each element there after will be the same.
     * @return allProducts, an array of Strings representing products sold in this machine.
     */
    public List<String> getAllProductsAdmin()
    {/////////////////////////ADMIN INSTEAD????????? SIMPLIFY USER ONE WITH JUST AVAILABLE PRODUCTS
        List<String> allProducts = new ArrayList<>();

        //for each ProductLocation object in arrayOfLocations
        for (ProductLocation productLocation : arrayOfLocations) {
            //if the arrayList in productLocation is not empty, assign the first element of the arrayList
            // within ProductLocation object of the current index to Product product
            if(!productLocation.getProductArrayList().isEmpty()) {
                Product product = productLocation.getProductArrayList().get(0);

                //if allProducts doesn't contain product then add a String representation of the object
                //getInfo() returns a String in the format description, €price. Then getQuantity() is called
                //to get them amount of Products in that location
                if (!allProducts.contains(product)) {
                    allProducts.add(product.getInfo() + ", " + productLocation.getQuantity());
                }
            }
            //If the current list in the current ProductLocation object is empty, add
            // an empty String to represent an empty space
            else{
                allProducts.add("");
            }
        }

        return allProducts;
    }


//    public List<String> getAllProducts()
//    {
//        List<String> allProducts = new ArrayList<>();
//
//        for (Product p : products)
//            if (!allProducts.contains(p.toString())){
//                allProducts.add(p.toString());
//            }
//        return allProducts;
//    }

    /**
     * Gets a String representation all product descriptions and price currently in the vending machine.
     * We only take the first element of the List in each ProductLocation, as each element there after will be the same.
     * @return allProducts, an array of Strings representing products sold in this machine.
     */
    public List<String> getAllProducts(){
        List<String> allProducts = new ArrayList<>();

        //for each ProductLocation object in arrayOfLocations
        for (ProductLocation productLocation : arrayOfLocations) {
            //if the arrayList in productLocation is not empty, assign the first element of the arrayList
            // within ProductLocation object of the current index to Product product
            if(!productLocation.getProductArrayList().isEmpty()) {
                Product product = productLocation.getProductArrayList().get(0);

                //if allProducts doesn't contain product then add a String representation of the object
                //getClientInfo() returns a String in the format location, description, €price
                if (!allProducts.contains(product)) {
                    allProducts.add(product.getClientInfo());
                }
            }
        }

        return allProducts;
    }

    /**
     Adds the coin to the vending machine.
     @param c the coin to add
     */
//   public void addCoin(Coin c)
//   {
//      currentCoins.addCoin(c);
//   }

    /**
     Buys a product from the vending machine.
     @param p the product to buy
     */
//   public void buyProduct(Product p)
//   {
//      for (int i = 0; i < products.size(); i++)
//      {
//         Product prod = products.get(i);
//         if (prod.equals(p))
//         {
////            double payment = currentCoins.getValue();
////            if (p.getPrice() <= payment)
////            {
////               products.remove(i);
//////               coins.addCoins(currentCoins);
//////               currentCoins.removeAllCoins();
////               return;
////            }
////            else
////            {
////               throw new VendingException("Insufficient money");
////            }
//         }
//      }
//      throw new VendingException("No such product");
//   }


}
