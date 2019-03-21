package com.Lectures;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class ProductLocation {
    private List<Product> productArrayList;

    /**
     *
     */
    public ProductLocation(){
        productArrayList = new ArrayList<>();
    }

    /**
     *
     */
    public List<Product> getProductArrayList(){
        return this.productArrayList;
    }

    /**
     *
     * @return
     */
    public int getQuantity(){
        return productArrayList.size();
    }

    /**
     *
     * @param product
     * @param quantity
     */
    public void addProductToLocation(Product product, int quantity){
        for(int i = 0; i < quantity; i++){
            productArrayList.add(product);
        }
    }

    /**
     *
     * @param product
     * @return
     */
    public boolean isInProductLocation(Product product){
        if(productArrayList.contains(product)) {
            return true;
        }else{
            return false;
        }
    }
}
