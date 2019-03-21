package com.Lectures;

import java.util.ArrayList;
import java.util.List;

/**
 * ?????????????????????????????????????????
 * This class represents a vending machine. It has a list of all products in the machine with a corresponding list
 * the quantity of each product. It also keeps track of the amount of money in the machine, and its power status.
 * The machine also has a menu object, to print and receive information from the user
 */

public class VendingMachine
{
    //   private List<Product> products;
//   private List<Integer> productQuantity;
    private List<ProductLocation> arrayOfLocations;
    private boolean poweredOn;
    private int maxVendingSize;


    /**
     * ??????????????????????????????????????
     * Constructs a VendingMachine object.
     * Initializes an arrayList of products, an arrayList of product quantities, the cash currently in the machine
     * and sets the vending machine to off. An instance of VendingMachineMenu is also created and this object is passed
     * to that
     */
    public VendingMachine()
    {
//      products = new ArrayList<>();
//      productQuantity = new ArrayList<>();
        arrayOfLocations = new ArrayList<ProductLocation>();
        maxVendingSize = 24;
        for(int i = 0; i < maxVendingSize; i++){
            arrayOfLocations.add(new ProductLocation());
        }
        poweredOn = false;
    }

    /**?????????????????????????????????????????????
     * This method turns the vending machine on. It then calls the vendingMachineMenu object
     * displayMenu() method. The menu will display so long as the VendingMachine is on
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
     * returns the power status of the machine
     */
    public boolean isOn(){
        return this.poweredOn;
    }

    /**
     * Adds a product to the vending machine.???????????????????????????
     * First it checks if the product exists in the machine. If not, the product will be added
     * to products, and the corresponding element of productsQuantity will be increased. If it already
     * exists, only the corresponding element in productsQuantity will be increased
     * @param product the product to add
     * @param quantity the quantity
     */
    public boolean addProduct(Product product, int quantity)
    {
        //convert location from alphaNumerical to a corresponding number
        int index = convertLocation(product.getLocation());
        //if -1 return false
        if(index == -1){
            return false;
        }
        //check if product is in location
        if(isInLocation(product,index)){
            arrayOfLocations.get(index).addProductToLocation(product, quantity);
            return true;
        }else if(arrayOfLocations.get(index).getProductArrayList().isEmpty()){
            arrayOfLocations.get(index).addProductToLocation(product, quantity);
            return true;
        }
        //if false return false
        return false;
    }
//    public void addProduct(Product product, int quantity)
//    {
//        //gets the index of the product by calling queryProduct(). If it doesn't exist,
//        //it will return -1
//        int index = queryProduct(product);
//        if(index >= 0){
//            //if it exists, we must get the current quantity in the machine, then add the new amount to that
//            int currentQuantity = productQuantity.get(index);
//            productQuantity.set(index, (currentQuantity + quantity));
//        } else{
//            products.add(product);
//            index = queryProduct(product);
//            productQuantity.add(quantity);
//        }
//    }

    /**
     *
     * @param product
     * @param index
     * @return
     */
    private boolean isInLocation(Product product,int index){
        if(arrayOfLocations.get(index).isInProductLocation(product)){
            return true;
        }else{
            return false;
        }
    }

    /**
     *
     * @param location
     * @return
     */
    private int convertLocation(String location){
        //split them up into a char and int
        //convert char to lowercase
        char letter = location.charAt(0);
        letter = Character.toLowerCase(letter);

        //reduce number by 1 as arrays start from 0
        String charToNumber = "" + location.charAt(1);
        int number = Integer.parseInt(charToNumber);
        number -= 1;
        //convert letter to a digit that's 0 or a multiple of 4
        //then add that digit to int number to get our index
        switch(letter) {
            case 'a':
                number += 0;
                break;
            case 'b':
                number += 4;
                break;
            case 'c':
                number += 8;
                break;
            case 'd':
                number += 12;
                break;
            case 'e':
                number += 16;
                break;
            case 'f':
                number += 20;
                break;

        }

        return number;
    }

    /**
     * ????????????????????????????????????????
     * Method checks if the products passed exists in list products. If not, it returns -1
     * @param product is the product we are looking for
     * @return the index of the product, or -1
     */
//    private int queryProduct(Product product, int location){
//
//        if(arrayOfLocations.get(location).getProductArrayList().contains(product)){
//            return location;
//        }
//        return  -1;
//    }
//    private int queryProduct(Product product){
//        if(products.contains(product)){
//            return products.indexOf(product);
//        }
//        else return -1;
//    }

    /**
     * ??????????????????????????????
     Gets all products in the vending machine, including their location
     @return an array of Strings representing products sold in this machine.
     */
    public List<String> getAllProducts()
    {
        List<String> allProducts = new ArrayList<>();

        //for each ProductLocation object in arrayOfLocations
        for (ProductLocation productLocation : arrayOfLocations) {
            //assign the first element of the arrayList within ProductLocation object the current index to product
            if(!productLocation.getProductArrayList().isEmpty()) {
                Product product = productLocation.getProductArrayList().get(0);

                //if allProducts doesn't contain product then add it's toString()
                if (!allProducts.contains(product)) {
                    allProducts.add(product.getInfo());
                }
            }
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
     * ??????????????????????????????
     Gets all products in the vending machine, including their location
     @return an array of Strings representing products sold in this machine.
     */
//    public List<String> getAllProductsAdmin()
//    {
//        List<String> allProducts = new ArrayList<>();
//
//        for (Product p : products)
//            if (!allProducts.contains(p.toString())){
//                allProducts.add(p.toString());
//            }
//        return allProducts;
//    }
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



    /**
     Removes the money from the vending machine.
     @return the amount of money removed
     */
//   public double removeMoney()
//   {
//      double r = coins.getValue();
//      coins.removeAllCoins();
//      return r;
//   }
}
