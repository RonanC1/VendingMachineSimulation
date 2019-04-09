package com.Lectures;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VendingGUI extends Application {
    private static VendingMachine vendingMachine = new VendingMachine();
    private Button showProducts = new Button("Show Products");

    private static TextField selectedProductInfo = new TextField();
    private static Button buyProductButton = new Button("Buy Product");

    private String productText;
    //arrayList of ProductButtons
    private static List<ProductButton> productButtons = new ArrayList<>();

    private static MenuItem addProductsItem = new MenuItem();
    private static MenuItem shutDownItem = new MenuItem();

    private static boolean isInAdminMode = false;

    @Override
    public void start(Stage primaryStage) throws Exception {

        vendingMachine.powerOn();

        //Border Pane
        BorderPane borderPane = new BorderPane();





        //GRID PANE
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #dbdbdb");
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        //set the preferred width of each button so they aren't all different sizes
        gridPane.setPrefWidth(80);

        /////////////PRODUCTS//////////////

        //A1
        //creates a new object of type ProductButton
        ProductButton a1 = newProductButton();
        //Add it to our arrayList so we can access it outside the main method
        productButtons.add(a1);
        //set the button text
        a1.getButton().setText(a1.getDescription());
        //set the width
        a1.getButton().setPrefWidth(gridPane.getPrefWidth());
        //add the button to our gridPane
        gridPane.add(a1.getButton(), 1, 0);
        //when pressed, call handleProductButton() and call the method to return a String of "location,description,price"
        a1.getButton().setOnAction(e -> handleProductButtonPress(a1.getProductInfo()));


        //A2
        ProductButton a2 = newProductButton();
        productButtons.add(a2);
        a2.getButton().setText(a2.getDescription());
        a2.getButton().setPrefWidth(gridPane.getPrefWidth());
        gridPane.add(a2.getButton(), 2, 0);
        a2.getButton().setOnAction(e -> handleProductButtonPress(a2.getProductInfo()));

        //A3
        ProductButton a3 = newProductButton();
        productButtons.add(a3);
        a3.getButton().setText(a3.getDescription());
        a3.getButton().setPrefWidth(gridPane.getPrefWidth());
        gridPane.add(a3.getButton(), 3, 0);
        a3.getButton().setOnAction(e -> handleProductButtonPress(a3.getProductInfo()));

        //Ar
        ProductButton a4 = newProductButton();
        productButtons.add(a4);
        a4.getButton().setText(a4.getDescription());
//        if(a4.getDescription().equalsIgnoreCase("Empty")){
//            a4.getButton().setDisable(true);
//        }
        a4.getButton().setPrefWidth(gridPane.getPrefWidth());
        gridPane.add(a4.getButton(), 4, 0);
        a4.getButton().setOnAction(e -> handleProductButtonPress(a4.getProductInfo()));


        /////////////END OF PRODUCTS//////////////

        //Show all products
        gridPane.add(showProducts, 0, 0);
        showProducts.setOnAction(e -> showAllProducts());


        ////////////Left Pane/////////////

        //Buy Products//text field must not be empty
        VBox vBox = new VBox(10);
        vBox.setStyle("-fx-background-color: #a7dcef");
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPadding(new Insets(10));

        /////User sign in///////
        TextField nameTextField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button login = new Button("Login");

        GridPane signInGridPane = new GridPane();
        signInGridPane.setAlignment(Pos.CENTER);
        signInGridPane.setHgap(10);
        signInGridPane.setVgap(10);

        //Login Icon//
        Image loginIcon = new Image(new FileInputStream("userLoginIcon.png"));
        ImageView imageView = new ImageView(loginIcon);
        vBox.getChildren().add(imageView);
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);

        //Username label
        Label nameLabel = new Label("Username");
        signInGridPane.add(nameLabel, 0, 0);

        //Username text
        signInGridPane.add(nameTextField, 1, 0);
        //if a key is pressed, the password text field will be enabled. Login button will be disabled
        //if the username is ever empty.
        nameTextField.setOnKeyReleased(e -> handleKeyReleased(nameTextField, passwordField, login));


        //Password label
        Label passwordLabel = new Label("Password");
        signInGridPane.add(passwordLabel, 0, 1);

        //Password text
        signInGridPane.add(passwordField, 1, 1);
        passwordField.setDisable(true);
        //if a key is pressed, the login button will be enabled
        passwordField.setOnKeyReleased(e -> handleKeyReleased(passwordField, login));

        vBox.getChildren().add(signInGridPane);

        //HBox for the two login buttons
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets (0,0,30,0));
        hBox.setAlignment(Pos.CENTER_RIGHT);


        //Login button
        login.setDisable(true);
        login.setAlignment(Pos.CENTER_RIGHT);
        //Verify user credentials. Checks if admin or customer
        login.setOnAction(e -> verifyUser(nameTextField.getText(), passwordField.getText(),login));
        hBox.getChildren().add(0,login);

        //Logout button
        Button logout = new Button("Logout");
        logout.setOnAction(e -> handleLogout(nameTextField, passwordField, selectedProductInfo, buyProductButton));
        hBox.getChildren().add(1,logout);

        //Add the HBox to the VBox
        vBox.getChildren().add(hBox);

        ///////End of User Login//////////

        //TextField
        selectedProductInfo.setEditable(false);
        vBox.getChildren().add(selectedProductInfo);
        //selectedProductInfo.setOnKeyReleased(e -> handleKeyReleased(selectedProductInfo, buyProductButton));

        //gridPane.add(buyProductButton,2,5);
        vBox.getChildren().add(buyProductButton);
        buyProductButton.setDisable(true);

        ///////////////// Admin Menu /////////////////////////
        MenuBar menuBar = new MenuBar();

        //Coloured bar
        VBox vBox1 = new VBox();
        vBox1.getChildren().add(menuBar);
//        Region space = new Region();
//        space.setPrefHeight(40);
//        space.setStyle("-fx-background-color: #b291c7");
//        vBox1.getChildren().add(space);
//
//        borderPane.setTop(vBox1);

        Menu menu = new Menu();
        menuBar.getMenus().add(menu);
        menu.setText("Admin");

//        MenuItem loginAdminItem = new MenuItem();
//        loginAdminItem.setText("Login");
//        menu.getItems().add(loginAdminItem);

        //Admin add products
        addProductsItem.setText("Add Products");
        addProductsItem.setDisable(true);
        addProductsItem.setOnAction(e -> handleAddProducts(buyProductButton, selectedProductInfo));
        menu.getItems().add(addProductsItem);

        //Admin shut down
        shutDownItem.setText("Shut down machine");
        shutDownItem.setDisable(true);
        shutDownItem.setOnAction(e -> {
            try {
                shutDown();
            } catch (IOException e1) {
                createNewErrorAlert(e1.getMessage());
            }
        });
        menu.getItems().add(shutDownItem);

        borderPane.setLeft(vBox);
        borderPane.setCenter(gridPane);
        primaryStage.setTitle("Vending Machine");
        primaryStage.setScene(new Scene(borderPane, 800, 500));
        primaryStage.show();



    }

    private static void verifyUser(String username, String password,Button login){
        //checks if it's an admin
        boolean isAdmin = vendingMachine.validateAdmin(username, password);

        if(isAdmin){
            createNewConfirmationAlert("Now in admin mode. Use the Admin tab in the toolbar to add products or turn off the vending machine");
            isInAdminMode = true;
            addProductsItem.setDisable(false);
            shutDownItem.setDisable(false);
            login.setDisable(true);

        }else {
            verifyClient(username, password);
        }
    }

    /**
     * Checks the system to see if the username and password match a user
     *
     * @param name        the name of the username retrieved from the textfield
     * @param password    the password of the username retrieved from the textfield
     */
    public static void verifyClient(String name, String password) {

        Client client = vendingMachine.validateClient(name, password);

        //getCredentials will get user details, verify them, and return true if they are found in the Verification object
        if (client != null) {
            createNewConfirmationAlert("\t\t\t\t" + name + " signed in successfully!" + "\n\n\t\t\t\tCurrent balance is €" + client.getBalance());

            //now that we have successfully logged in, enable the buy product button
            buyProductButton.setDisable(false);
            //when the buyProductButton is clicked, buyProduct() will be called, it will throw an exception if there
            //is no text in the selected product text field
            buyProductButton.setOnAction(e -> {
                try {
                    buyProduct(client, selectedProductInfo.getText());
                } catch (VendingException e1) {
                    createNewConfirmationAlert(e1.getMessage());
                }
            });

        } else {
            createNewConfirmationAlert("Username or password is incorrect!");
        }

    }

    /**
     * buys the selected product from the vending machine. This method is called from verifyClient().
     *
     * @param client      is the user purchasing the item
     * @param productInfo is the product's toString()
     */

    private static void buyProduct(Client client, String productInfo){

        //if there are no products selected, throw an exception
        if (selectedProductInfo.getText().isEmpty() || selectedProductInfo.getText().trim().isEmpty()) {
            throw new VendingException("Error! There is no product currently selected");
        }

        //first we get the product location
        String location = productInfo.substring(0, 2);//fix when nothing in the yoke

        //now we get the price of that product. If there is no product in that location, thereAreProducts
        //will be false
        boolean thereAreProducts = true;
        double price = -1;
        try {
            price = vendingMachine.getPrice(location);
        } catch (VendingException e) {
            thereAreProducts = false;
            //find button
            //setButton
            createNewConfirmationAlert("\t\t\t\t" + e.getMessage());
        }

        //if we were able to get the price of a product
        if (thereAreProducts) {
            try {
                //update the clients balance
                client.updateBalance(price);
                //if client does not have enough credit in their balance, the following lines will not be executed,
                //instead jumping to the catch clause.
                String pInfo = vendingMachine.buyProduct(location);
                createNewConfirmationAlert("\t\t\t\t" + client.getUsername() + " purchased a " + pInfo + "\n\n\t\t\t\tBalance = €" + client.getBalance());
                //clears the label displaying selected product info
                selectedProductInfo.clear();
                //updates the corresponding product button that was pressed. Changes that button's text to "Empty" if there are no more products left
                updateButtonsText(location);
            } catch (VendingException e) {
                createNewConfirmationAlert("\t\t\t\t" + e.getMessage());
            }
        }

    }

    /**
     * When called, this method checks the quantity of product left in the machine. If 0,
     * the button text will be changed to "Empty"
     * @param location
     */
    private static void updateButtonsText(String location){
        int quantity = vendingMachine.getProductQuantity(location);

        if(quantity <= 0) {
            for (ProductButton productButton : productButtons) {
                if (productButton.getLocation().equalsIgnoreCase(location)) {
                    productButton.getButton().setText("Empty");
                }
            }
        }
    }



    /**
     * Event handler for when a product button is pressed. The string representation of the product is
     * entered into the text field selectedProductInfo. This allows that product to then be purchased.
     * @param text
     */
    public static void changeSelectedProduct(String text){
        selectedProductInfo.setText(text);
    }

    /**
     * This method gets the i'th product from the vending machine and returns it to display product
     * information on the GUI
     */
    static int i = 0;
//    public static String getNextProductName(){
//        String product = vendingMachine.getAllProducts().get(i);
//        i++;
//        return product;
//    }

    /**
     * When called this method creates a new ProductButton object, which will contain a button,
     * product description, location and price.
     * The method gets a list of type String all product object information from the vendingMachine object,
     * where each element has the products information separated by commas.
     * int buttonObjectCounter decides what element of the array is to be selected
     */
    private static int buttonObjectCounter = 0;
    public static ProductButton newProductButton(){
        List<String> allProducts = vendingMachine.getAllProductsCSV();
        String[] products = allProducts.get(buttonObjectCounter).split(",");
        buttonObjectCounter++;
        for (int i = 0; i < products.length; i++){
            if (products[i].isEmpty() || products[i].trim().isEmpty()){
                products[i] = "Empty";
            }
        }
        return new ProductButton(products[0], products[1], products[2]);
    }

    public static void showAllProducts(){
        List<String> products = new ArrayList<>();
        products = vendingMachine.getAllProducts();

        for(String p:products){
            System.out.println(p);
        }
    }

    /**
     * Creates a new pop up dialog box
     * @param message is the message to be displayed
     */

    public static void createNewConfirmationAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    /**
     * Creates a new pop up dialog box
     * @param message is the message to be displayed
     */

    public static void createNewErrorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    public static String createNewWarningAlert(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.getButtonTypes().add(ButtonType.NO);
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();

        return alert.getResult().getText();
    }

    /**
     * this method changes the product description label when a product button is pressed
     * @param info is the product information as a String, returned from productButton.getProductInfo()
     */
    private static void handleProductButtonPress(String info){
        changeSelectedProduct(info);
    }

    /**
     * When the logout button is pressed, this method clears all text fields and disables the buy button
     * @param name username textField
     * @param password password textField
     * @param products selectedProduct textField
     * @param buy the buy button
     */
    public static void handleLogout(TextField name, PasswordField password, TextField products, Button buy){
        name.clear();
        password.clear();
        password.setDisable(true);
        products.clear();
        buy.setDisable(true);
        if(isInAdminMode){
            handleAdminLogout();
        }
    }

    private static void handleAdminLogout(){
        //System.out.println("It's in admin mode");
        isInAdminMode = false;
        addProductsItem.setDisable(true);
        shutDownItem.setDisable(true);
    }

    private static void handleAddProducts(Button buy, TextField productInfo){
        buy.setDisable(true);
        Dialog dialog = new Dialog();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.setTitle("Add products");
        dialog.setHeaderText("Pick a product location to add products");


        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        //gridPane.setPrefWidth(100);
        dialog.getDialogPane().setContent(gridPane);


        ComboBox<String> comboBox = new ComboBox<>();
        for(ProductButton button : productButtons){
            comboBox.getItems().add(button.getLocation());
        }

        comboBox.setValue("Select location");
        gridPane.add(comboBox,0,0,2,1);

        Label locationLabel = new Label("Location:");
        gridPane.add(locationLabel,0,1);
        gridPane.setHalignment(locationLabel, HPos.CENTER);
        TextField location = new TextField();
        location.setEditable(false);
        gridPane.add(location,1,1);

        Label productLabel = new Label("Description:");
        gridPane.add(productLabel,0,2);
        gridPane.setHalignment(productLabel, HPos.CENTER);
        TextField name = new TextField();
        name.setEditable(false);
        gridPane.add(name,1,2);

        Label priceLabel = new Label("Price:");
        gridPane.add(priceLabel,0,3);
        gridPane.setHalignment(priceLabel, HPos.CENTER);
        TextField price = new TextField();
        price.setEditable(false);
        gridPane.add(price,1,3);

        Label quantityLabel = new Label("Quantity in stock:");
        gridPane.add(quantityLabel,0,4);
        gridPane.setHalignment(quantityLabel, HPos.CENTER);
        TextField quantity = new TextField();
        quantity.setEditable(false);
        gridPane.add(quantity,1,4);

        Spinner<Integer> spinner = new Spinner<>();
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,0));
        spinner.setEditable(false);
        gridPane.add(spinner,1,5);


        comboBox.setOnAction(e -> changeAddProductFields(comboBox.getValue(),location,name,price,quantity));


        dialog.show();


        }

    private static void changeAddProductFields(String location, TextField locationField, TextField name, TextField price, TextField quantity){
        ProductButton productButton;
        for(ProductButton pb : productButtons){
            if(pb.getLocation().equalsIgnoreCase(location)){
                productButton = pb;
                locationField.setText(location);
                name.setText(productButton.getDescription());
                price.setText(productButton.getPrice());
                quantity.setText(Integer.toString(vendingMachine.getProductQuantity(location)));
            }
        }



    }

    private static void shutDown() throws IOException{
        String shutDown = createNewWarningAlert("Shut down vending machine. Are you sure?");
        if(shutDown.equalsIgnoreCase("yes")){
            try {
                vendingMachine.powerOff();
                Platform.exit();
            }catch (IOException e){
                throw new IOException(e.getMessage());
            }
        }else if (shutDown.equalsIgnoreCase("no")){
            System.out.println("no");
        }
    }

    /**
     * Handles when a character is typed into a text field. Makes a button active or inactive if there is
     * or isn't text in the text field
     * @param textField is the text field we are checking for text
     * @param button is the button we want to disable/ enable
     */
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
}
