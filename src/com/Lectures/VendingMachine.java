package com.Lectures;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a vending machine. It has a list of type ProductLocation. Each instance of productLocation can
 * have an List of products. The machine has a power status. The class is controlled by the VendingMachine class. The
 * class has an instance of Verification which handles loading, saving and verifying users.
 */

public class VendingMachine {
    private List<ProductLocation> arrayOfLocations;
    private boolean poweredOn;
    private final int maxVendingSize;
    private char locationLetter;
    private int locationNumber;
    private String location;
    private Verification verification;

    /**
     * Constructs a VendingMachine object.
     * Initializes an arrayList of ProductLocations, and initializes maxVendingSize ProductLocation
     * objects. Sets power status to false.
     */
    public VendingMachine(){
        arrayOfLocations = new ArrayList<>();
        maxVendingSize = 24;
        //ProductLocation location field char and int
        locationLetter = 'A';
        locationNumber = 1;
        //initialize each element in arrayOfLocations
        for(int i = 0; i < maxVendingSize; i++){
            //this assigns a string to each location parameter
            if(i%4 == 0 && i>0){
                locationLetter++;
                locationNumber = 1;
            }
            location = "" + locationLetter + locationNumber;
            arrayOfLocations.add(new ProductLocation(location));
            locationNumber++;
        }
        verification = new Verification();
        poweredOn = false;
    }

    /**
     * This method turns the vending machine on. It is passed a vendingMachineMenu object as a parameter,
     * then calls the displayMenu() method. The menu will display so long as the VendingMachine is on.
     * This method also loads all products, clients and admins
     */
    public void powerOn(VendingMachineMenu vendingMachineMenu) throws FileNotFoundException {
        System.out.println("Vending machine is powered on");
        poweredOn = true;
        //loads all products
        loadProducts();
        //loads all Admins and Clients
        verification.loadUsers();
        vendingMachineMenu.displayMenu();
    }

    /**
     * This method turns the vending machine status to off. The menu will then stop displaying
     */
    public void powerOff() throws IOException {
        saveProducts();
        verification.saveClients();
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
     * Loads products from Products.dat into the vending machine
     */
    private void loadProducts() throws FileNotFoundException{
        String pattern = "^[A-Za-z.]+,\\d\\.\\d\\d?,[A-Za-z][1-4],\\d?\\d$";//????????????????????//
        //create a String arrayList and assign it the return from loadFile()
        List<String> inputFiles;
//        inputFiles = productsFileInput.loadFile();
        inputFiles = FileInput2.loadFile("Products.txt");
        String[] currentFile;

        //break up each String element on the "," and add a new product
        for (int i = 0; i < inputFiles.size(); i++) {

            currentFile = inputFiles.get(i).split(",");

            try {
                if (inputFiles.get(i).matches(pattern)) {
                    addProduct(currentFile[0], Double.parseDouble(currentFile[1]), currentFile[2], Integer.parseInt(currentFile[3]));
                } else {
                    throw new VendingException("Could not successfully add " + currentFile[0] + " to the machine");
                }
            }catch(VendingException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Saves product details from the vendingMachine to Products.dat
     * @throws IOException
     */
    private void saveProducts() throws IOException {
        List<String> productsToSave = new ArrayList<>();

        for(ProductLocation productLocation : arrayOfLocations){
            //if the quantity is 0 there is nothing in the current array list so we skip it
            if(productLocation.getQuantity() > 0) {
                //add a new String to productsToSave. The String contains the product objects toString(), and the quantity is also
                //added by getting the size of the current arrayList
                productsToSave.add(productLocation.getProductArrayList().get(0).toString() + "," + productLocation.getQuantity());
            }
        }
        FileOutput.saveFile("Products.txt", productsToSave);
    }





    /**
     * Adds a product to the vending machine.
     * The method checks if the same Product type is in the specified location, or if that location is empty,
     * and will add accordingly and return true. If the productLocation does not contain the same type of Product and isn't empty, the method
     * returns false.
     * @param quantity the quantity
     */

//    public boolean addProduct(String description, double price, String location, int quantity)
//    {
//        //first we create a product object that will be passed to various methods
//        Product product = new Product(description, price, location);
//        //we get the index of the location in arrayOfLocations by calling getIndexOf()
//        int index = getIndexOf(location);
//        //Now we check if the location has products of the same type by calling isInLocation and passing the product
//        //object and location
//        //If the specified location has Product objects of the same type we add the quantity of product objects.
//        //Or if it's an empty location, we can add them too
//        if(isInLocation(product,location) && index > -1){
//            arrayOfLocations.get(index).addProductToLocation(description, price, location, quantity);
//            //update/save products
//            return true;
//            ///////////////////////////????????????????????????????????????????????????????????????
//        }else if(arrayOfLocations.get(index).getProductArrayList().isEmpty() && index > -1){//???????????
//            arrayOfLocations.get(index).addProductToLocation(description, price, location, quantity);
//            //update/save products
//            return true;
//        }
//        //Otherwise, return false
//        return false;
//    }
    public boolean addProduct(String description, double price, String location, int quantity)
    {
        //first we create a product object that will be passed to various methods
        Product product = new Product(description, price, location);
        //we get the index of the location in arrayOfLocations by calling getIndexOf()
        int index = getIndexOf(location);
        //Now we check if the location has products of the same type by calling isInLocation and passing the product
        //object and location
        //If the specified location has Product objects of the same type we add the quantity of product objects.
        //Or if it's an empty location, we can add them too
        if(index > -1) {
            if (isInLocation(product, location)) {//??????????
                arrayOfLocations.get(index).addProductToLocation(description, price, location, quantity);
                //update/save products
                return true;
                ///////////////////////////????????????????????????????????????????????????????????????
            } else if (arrayOfLocations.get(index).getProductArrayList().isEmpty()) {//???????????
                arrayOfLocations.get(index).addProductToLocation(description, price, location, quantity);
                //update/save products
                return true;
            }
        }else{
            throw new VendingException(description + " could not be added to " + location);
        }
        return false;
    }

    /**
     * This method checks if the ProductLocation in arrayOfLocations has the same product types
     * @param product is the product to be added
     * @param location the specified location form the user
     * @return true or false
     */
    private boolean isInLocation(Product product,String location){
        for(int i = 0; i < arrayOfLocations.size(); i++){
            if(arrayOfLocations.get(i).getLocation().equalsIgnoreCase(location)) {
                //call ProductLocation.isInProductLocation() to check if the passed product exists in the ArrayList
                if (arrayOfLocations.get(i).isInProductLocation(product)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;

    }

    /**
     * This method gets the index of the object of type ProductLocation with the same location field as
     * the String passed to the method
     * @param location is the location the user wishes to add a product
     * @return the index of the object, or -1
     */
    private int getIndexOf(String location){
        for(ProductLocation productLocation : arrayOfLocations){
            if(productLocation.getLocation().equalsIgnoreCase(location)){
                return arrayOfLocations.indexOf(productLocation);
            }
        }
        return -1;
    }

    /**
     * Method specifically for the Admin to show all and empty ProductLocations. Gets a String representation of current
     * product descriptions and price currently in each product location.
     * We only take the first element of the List in each ProductLocation, as each element there after will be the same.
     * @return allProducts, an array of Strings representing products sold in this machine.
     */
    public List<String> getAllProductsAdmin(){
        List<String> allProducts = new ArrayList<>();

        //for each ProductLocation object in arrayOfLocations
        for (ProductLocation productLocation : arrayOfLocations) {
            //if the arrayList in productLocation is not empty, assign the first element of the arrayList
            // within ProductLocation object of the current index to Product product
            if(!productLocation.getProductArrayList().isEmpty()) {
                Product product = productLocation.getProductArrayList().get(0);

                //if allProducts doesn't contain product then add a String representation of the object
                //getInfo() returns a String in the format description, €price. Then getQuantity() is called
                //(the size of the ArrayList in the object) to get them amount of Products in that location
                if (!allProducts.contains(product)) {
                    allProducts.add(productLocation.getLocation() + " " + product.getInfo() + ", " + productLocation.getQuantity());
                }
            }
            //If the current list in the current ProductLocation object is empty, add
            // an empty String to represent an empty space
            else{
                allProducts.add(productLocation.getLocation() + "");
            }
        }

        return allProducts;
    }

    /**
     * Simplified version of getAllAdminProducts. Gets a String representation all product descriptions and price currently
     * in the vending machine. We only take the first element of the List in each ProductLocation, as each element there
     * after will be the same. Doesn't show empty Product Locations
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
     * Allows the user to buy a product from the vending machine.
     * @param location the product location to buy from
     */
    public String buyProduct(String location)
    {
        //for each element in the ArrayList
        for(ProductLocation productLocation : arrayOfLocations){
                //if the current object location equals the String passed to the method
            if (productLocation.getLocation().equalsIgnoreCase(location)) {
                //check that there is actually product objects in the arrayList
                if(productLocation.getQuantity() > 0) {

                    Product product = productLocation.getProductArrayList().get(0);
                    //remove the current product by calling the object's method to remove items
                    productLocation.removeItem(product);
                    //save products
                    //update/save clients
                    return product.getInfo();
                }else{
                    throw new VendingException("Product not in stock");
                }
            }
        }

        return null;
    }

    public double getPrice(String location){
        for(ProductLocation productLocation : arrayOfLocations){
            if(productLocation.getLocation().equalsIgnoreCase(location)){

                if(productLocation.getQuantity() > 0) {
                    return productLocation.getProductArrayList().get(0).getPrice();
                }else{
                    throw new VendingException("No products at this location");
                }
            }
        }
        return -2;
    }

    public Client validateClient(String username, String password){
       return verification.validateClient(username, password);
    }

    public boolean validateAdmin(String username, String password){
        return verification.validateAdmin(username, password);
    }

//    public void getAllProductLocations(){
//        for(int i = 0; i < arrayOfLocations.size(); i++){
//            System.out.println(arrayOfLocations.get(i).getLocation());
//        }
//    }


}
