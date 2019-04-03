package com.Lectures;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

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
    private static Button buyProductButton = new Button("Buy Product");

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
        gridPane.setHgap(10);
        gridPane.setVgap(10);

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
        //textField.setOnKeyReleased(e -> handleKeyReleased(textField, buyProductButton));

        //Buy Products//text field must not be empty
        gridPane.add(buyProductButton,2,5);
        GridPane.setHalignment(buyProductButton, HPos.CENTER);
        buyProductButton.setDisable(true);
        //buyProductButton.setOnAction(e -> buyProduct(textField.getText()));
        buyProductButton.setOnAction(e -> showUserSignIn());

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
//                    String pInfo = vendingMachine.buyProductButton(location);
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
        buyProductButton.setDisable(false);
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

    public static void showUserSignIn(){

        Dialog<Pair<String,String>> userSignIn = new Dialog<>();
        userSignIn.setTitle("User sign in");
        userSignIn.setHeaderText("Sign in with your user credentials");


        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        TextField nameTextField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button login = new Button("Login");

        //Username label
        Label nameLabel = new Label("Username");
        gridPane.add(nameLabel,0,0);

        //Username text
        gridPane.add(nameTextField,1,0);
        //if a key is pressed, the password text field will be enabled. Login button will be disabled
        //if the username is ever empty.
        nameTextField.setOnKeyReleased(e -> handleKeyReleased(nameTextField,passwordField,login));

        //Password label
        Label passwordLabel = new Label("Password");
        gridPane.add(passwordLabel,0,1);

        //Password text
        gridPane.add(passwordField,1,1);
        passwordField.setDisable(true);
        //if a key is pressed, the login button will be enabled
        passwordField.setOnKeyReleased(e -> handleKeyReleased(passwordField, login));

        //Login button
        login.setDisable(true);
        gridPane.add(login,0,2);


        //Cancel button
        Button cancel = new Button("Cancel");
        gridPane.add(cancel,1,2);
        //Dialog box technically doesn't have a close button. So the functionality needs to be added.
        //Dialogue.close() does not work unless this button is available
        userSignIn.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        //now we assign the close button to a node and set it to not be visible
        Node closeNode = userSignIn.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeNode.setVisible(false);
        //we are using our own cancel button now because we can move it around the grid pane
        cancel.setOnAction(e -> userSignIn.close());






        //assign the grid pane and it's contents
        userSignIn.getDialogPane().setContent(gridPane);
        userSignIn.show();





    }

    public static void handleKeyReleased(TextField textField, Button button){
        String text = textField.getText();
        //assign either true or false depending on boolean returned
        boolean disableButtons = text.isEmpty() || text.trim().isEmpty();//.trim is for white spaces
        button.setDisable(disableButtons);
    }

    /**
     * Handles Username events on a login page. Sets password field to enabled. Always disables login button
     * if there is no text in the username field
     */
    public static void handleKeyReleased(TextField username, TextField password, Button button){
        String text = username.getText();
        //assign either true or false depending on boolean returned
        boolean disableButtons = text.isEmpty() || text.trim().isEmpty();//.trim is for white spaces
        password.setDisable(disableButtons);

        //The login must be disabled if there is no text in the username field
        if(text.isEmpty() || text.trim().isEmpty()){
            button.setDisable(true);
        }
    }

}
