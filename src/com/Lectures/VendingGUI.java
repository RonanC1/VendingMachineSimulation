package com.Lectures;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class VendingGUI extends Application {
//    private Button button1 = new Button("Button 1");
//    private Label label1 = new Label("Label 1");
    private static VendingMachine vendingMachine = new VendingMachine();
    private Button showProducts = new Button("Show Products");
    private Button a1 = new Button();
    private Button a2 = new Button();

    private static TextField textField = new TextField();
    private Button buyProduct = new Button("Buy Product");

    private String productText;

    @Override
    public void start(Stage primaryStage) throws  Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        //root.setStyle("-fx-background-color: #dbdbdb");
        vendingMachine.powerOn();
        //GRID PANE
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #dbdbdb");
        gridPane.setAlignment(Pos.CENTER);

        //A1
        a1.setText(getNextProduct());
        gridPane.add(a1,1,0);
        a1.setOnAction(e -> selectProduct(a1.getText()));

        //A2
        a2.setText(getNextProduct());
        gridPane.add(a2,2,0);
        a2.setOnAction(e -> selectProduct(a2.getText()));

        //TextField
        textField.setEditable(false);
        gridPane.add(textField,2,4);

        //Buy Products
        gridPane.add(buyProduct,2,5);
        GridPane.setHalignment(buyProduct, HPos.CENTER);
        buyProduct.setOnAction(e -> buyProduct(textField.getText()));

        //Show all products
        gridPane.add(showProducts,0,0);
        showProducts.setOnAction(e -> showAllProducts());












        primaryStage.setTitle("Vending Machine");
        primaryStage.setScene(new Scene(gridPane,800, 500));
        primaryStage.show();
    }

    public static void buyProduct(String text){

        String location = text.substring(0,2);
        System.out.println(location);
//        double price = -1;
//        try {
//            price = vendingMachine.getPrice(location);
//        }catch (VendingException e){
//            System.out.println(e.getMessage());
//        }
//
//        if (location.matches(pattern) && price > -1) {
//
//            //Pop up window
//
//            Client client = vendingMachine.validateClient(username, password);
//
//            //getCredentials will get user details, verify them, and return true if they are found in the Verification object
//            if (client != null) {//////ADD CHECK BALANCE
//                //check is in stock
//                //if(vendingMachine.checkIsInStock(location)) {
//
//                try {
//                    client.updateBalance(price);
//                    //if client does not have enough credit in their balance, the next line will not be executed
//                    String pInfo = vendingMachine.buyProduct(location);
//                    System.out.println(client.getUsername() + " purchased a " + pInfo);
//                } catch (VendingException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//        }else {
//            System.out.println("Username or password is incorrect.");
//        }
    }

    public static void selectProduct(String text){
        textField.setText(text);
    }

    static int i = 0;
    public static String getNextProduct(){
        String product = vendingMachine.getAllProducts().get(i);
        i++;
        return product;
    }
    public static void showAllProducts(){
        List<String> products = new ArrayList<>();
        products = vendingMachine.getAllProducts();

        for(String p:products){
            System.out.println(p);
        }
    }
}
