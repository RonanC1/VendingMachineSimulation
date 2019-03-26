package com.Lectures;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a location within a vendingMachine object. This location has a List
 * of stored products
 */

public class ProductLocation {
    private List<Product> productArrayList;
    private String location;

    /**
     * Default constructor
     */
    public ProductLocation(String location){
        productArrayList = new ArrayList<>();
        this.location = location;
    }

    /**
     * returns the arrayList of Products
     */
    public List<Product> getProductArrayList(){
        return this.productArrayList;
    }

    /**
     * returns a representation of how many products are at the current location
     * @return productArrayList.size() - the size of the arrayList
     */
    public int getQuantity(){
        return productArrayList.size();
    }

    public String getLocation(){
        return this.location;
    }

    /**
     * Adds a product(s) to productArrayList
     * @param quantity the amount passed by the user
     */
    public void addProductToLocation(String description, double price, String location, int quantity){
        //adds quantity many new Product objects
        for(int i = 0; i < quantity; i++){
            productArrayList.add(new Product(description, price, location));
        }
    }

    /**
     *  Checks if productArrayList contains a similar product passed to this method
     * @param product is the product to be added
     * @return true or false
     */
    public boolean isInProductLocation(Product product){
        String productName = product.getDescription();
        for(Product p: productArrayList){
            if(p.getDescription().equalsIgnoreCase(productName)){
                return true;
            }
        }
        return false;
    }

    /**
     * ???????????????????????????????
     * @param product
     * @return
     */
    public boolean removeItem(Product product){
        productArrayList.remove(product);
        return true;
    }


}
