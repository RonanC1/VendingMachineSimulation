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
   private String getDescription()
   {
      return description;
   }

   /**
    Gets the price.
    @return the price
    */
   private double getPrice()
   {
      return price;
   }

   public String getLocation(){
      return this.location;
   }

   /**
    Determines of this product is the same as the other product.
    @param other the other product
    @return true if the products are equal, false otherwise
    */
//   public boolean equals(Object other)
//   {
//      if (other == null) return false;
//      Product b = (Product) other;
//      return description.equals(b.description) && price == b.price;
//   }

   /**
    Formats the product's description and price.
    */
   public String toString()
   {
      return description + "," + price + "," + location;
   }

   public String getInfo()
   {
      return description + " @ €" + price;
   }

   public String getPrintInfo()
   {
      return location + ". " + description + " @ €" + price;
   }


}
