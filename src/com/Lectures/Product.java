/**
 A product in a vending machine.
 */

package com.Lectures;

public class Product
{
    private String description;
    private double price;
    private String location;

    /**
     Constructs a Product object
     @param description the description of the product
     @param price the price of the product
     */
    public Product(String description, double price, String location)
    {
        this.description = description;
        this.price = price;
        this.location = location;
    }

    /**
     Gets the description.
     @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     Gets the price.
     @return the price
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * @return the location
     */
    public String getLocation(){
        return this.location;
    }

    /**
     * Returns a String representation of the class
     */
    public String toString()
    {
        return description + "," + price + "," + location;
    }

    /**
     * Returns a String representation of the product description and price
     */
    public String getInfo()
    {
        return description + " @ €" + price;
    }

    /**
     * Returns a String representation of the class for the user
     */
    public String getClientInfo()
    {
        return location + " " + description + " @ €" + price;
    }


}
