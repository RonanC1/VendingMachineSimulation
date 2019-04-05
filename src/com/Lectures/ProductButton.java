package com.Lectures;

import javafx.scene.control.Button;

public class ProductButton {
    private Button button;
    private String description;
    private String location;
    private String price;

    public ProductButton(String description,  String price, String location) {
        this.button = new Button();
        this.description = description;
        this.location = location;
        this.price = price;
    }

    public String getProductInfo(){
        return this.location + " " + this.description + " €" + this.price;
    }

    public Button getButton() {
        return button;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
