package com.Lectures;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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

    private static TextField selectedProductInfo = new TextField();
    private static Button buyProductButton = new Button("Buy Product");

    private String productText;

    @Override
    public void start(Stage primaryStage) throws  Exception {
        vendingMachine.powerOn();

        //Border Pane
        BorderPane borderPane = new BorderPane();

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

        //Show all products
        gridPane.add(showProducts,0,0);
        showProducts.setOnAction(e -> showAllProducts());





        ////////////Left Pane/////////////

        //Buy Products//text field must not be empty
        VBox vBox = new VBox(10);
        vBox.setStyle("-fx-background-color: #a7dcef");
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));

        /////User sign in///////
        TextField nameTextField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button login = new Button("Login");

        GridPane signInGridPane = new GridPane();
        signInGridPane.setAlignment(Pos.CENTER);
        signInGridPane.setHgap(10);
        signInGridPane.setVgap(10);

        //Username label
        Label nameLabel = new Label("Username");
        signInGridPane.add(nameLabel,0,0);

        //Username text
        signInGridPane.add(nameTextField, 1, 0);
        //if a key is pressed, the password text field will be enabled. Login button will be disabled
        //if the username is ever empty.
        nameTextField.setOnKeyReleased(e -> handleKeyReleased(nameTextField,passwordField,login));


        //Password label
        Label passwordLabel = new Label("Password");
        signInGridPane.add(passwordLabel, 0,1);

        //Password text
        signInGridPane.add(passwordField, 1,1);
        passwordField.setDisable(true);
        //if a key is pressed, the login button will be enabled
        passwordField.setOnKeyReleased(e -> handleKeyReleased(passwordField, login));

        vBox.getChildren().add(signInGridPane);

        //Login button
        login.setDisable(true);
        //signInGridPane.add(login,1,2);
        vBox.getChildren().add(login);
        login.setAlignment(Pos.CENTER_RIGHT);
        login.setOnAction(e -> verifyClient(nameTextField.getText(),passwordField.getText(), selectedProductInfo.getText()));

        ///////End of User Login//////////

        //TextField
        selectedProductInfo.setEditable(false);
        vBox.getChildren().add(selectedProductInfo);
        //selectedProductInfo.setOnKeyReleased(e -> handleKeyReleased(selectedProductInfo, buyProductButton));

        //gridPane.add(buyProductButton,2,5);
        vBox.getChildren().add(buyProductButton);
        buyProductButton.setDisable(true);
        //buyProductButton.setOnAction(e -> buyProduct(selectedProductInfo.getText()));
        //buyProductButton.setOnAction(e -> showUserSignIn());





        borderPane.setLeft(vBox);
        borderPane.setCenter(gridPane);
        primaryStage.setTitle("Vending Machine");
        primaryStage.setScene(new Scene(borderPane,800, 500));
        primaryStage.show();
    }

    public static void verifyClient(String name, String password, String productInfo){

        Client client = vendingMachine.validateClient(name, password);

        //getCredentials will get user details, verify them, and return true if they are found in the Verification object
        if (client != null) {//////ADD CHECK BALANCE
            createNewDialog(name + " signed in successfully!" + "\n\n\tBalance = €" + client.getBalance());
            if(!selectedProductInfo.getText().isEmpty() || !selectedProductInfo.getText().trim().isEmpty()) {
                buyProductButton.setDisable(false);
            }
            buyProductButton.setOnAction(e -> buyProduct(client, productInfo));
        }else{
            createNewDialog("Username or password is incorrect!");
        }

    }

    public static void buyProduct(Client client, String productInfo){
        String location = productInfo.substring(0,2);//fix when nothing in the yoke
        //Client client = vendingMachine.validateClient(name, password);

        boolean thereAreProducts = true;
        double price = -1;
        try {
            price = vendingMachine.getPrice(location);
        }catch (VendingException e){
            thereAreProducts = false;
            createNewDialog(e.getMessage());
        }

        if(thereAreProducts) {
            try {
                client.updateBalance(price);
                //if client does not have enough credit in their balance, the next line will not be executed
                String pInfo = vendingMachine.buyProduct(location);
                createNewDialog(client.getUsername() + " purchased a " + pInfo + "\n\n\tBalance = €" + client.getBalance());
            } catch (VendingException e) {
                createNewDialog(e.getMessage());
            }
        }

    }

    public static void selectProduct(String text){
        selectedProductInfo.setText(text);
        //buyProductButton.setDisable(false);
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

    public static void createNewDialog(String message){
        Dialog<Pair<String,String>> dialogBox = new Dialog<>();
        //userSignIn.setTitle("User sign in");
        //userSignIn.setHeaderText(message);
        //Username label
        Label label = new Label(message);
        dialogBox.getDialogPane().setContent(label);
        dialogBox.setHeight(100);
        dialogBox.setWidth(200);

//        gridPane.add(nameLabel,0,0);
        //Dialog box technically doesn't have a close button. So the functionality needs to be added.
        //Dialogue.close() does not work unless this button is available
        dialogBox.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        //now we assign the close button to a node and set it to not be visible
        Node closeNode = dialogBox.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeNode.setVisible(false);
        dialogBox.show();
    }

//    public static void showUserSignIn(){
//
//        Dialog<Pair<String,String>> userSignIn = new Dialog<>();
//        userSignIn.setTitle("User sign in");
//        userSignIn.setHeaderText("Sign in with your user credentials");
//
//
//        GridPane gridPane = new GridPane();
//        gridPane.setAlignment(Pos.CENTER);
//        gridPane.setHgap(10);
//        gridPane.setVgap(10);
//
//        TextField nameTextField = new TextField();
//        PasswordField passwordField = new PasswordField();
//        Button login = new Button("Login");
//
//        //Username label
//        Label nameLabel = new Label("Username");
//        gridPane.add(nameLabel,0,0);
//
//        //Username text
//        gridPane.add(nameTextField,1,0);
//        //if a key is pressed, the password text field will be enabled. Login button will be disabled
//        //if the username is ever empty.
//        nameTextField.setOnKeyReleased(e -> handleKeyReleased(nameTextField,passwordField,login));
//
//        //Password label
//        Label passwordLabel = new Label("Password");
//        gridPane.add(passwordLabel,0,1);
//
//        //Password text
//        gridPane.add(passwordField,1,1);
//        passwordField.setDisable(true);
//        //if a key is pressed, the login button will be enabled
//        passwordField.setOnKeyReleased(e -> handleKeyReleased(passwordField, login));
//
//        //Login button
//        login.setDisable(true);
//        gridPane.add(login,0,2);
//
//
//        //Cancel button
//        Button cancel = new Button("Cancel");
//        gridPane.add(cancel,1,2);
//        //Dialog box technically doesn't have a close button. So the functionality needs to be added.
//        //Dialogue.close() does not work unless this button is available
//        userSignIn.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
//        //now we assign the close button to a node and set it to not be visible
//        Node closeNode = userSignIn.getDialogPane().lookupButton(ButtonType.CLOSE);
//        closeNode.setVisible(false);
//        //we are using our own cancel button now because we can move it around the grid pane
//        cancel.setOnAction(e -> userSignIn.close());
//
//
//
//
//
//
//        //assign the grid pane and it's contents
//        userSignIn.getDialogPane().setContent(gridPane);
//        userSignIn.show();
//
//
//
//
//
//    }

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
