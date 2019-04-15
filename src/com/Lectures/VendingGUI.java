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
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
    //boolean determines if an admin is signed in
    private static boolean isInAdminMode = false;

    @Override
    public void start(Stage primaryStage) throws Exception {

        vendingMachine.powerOn();

        //Load custom font
        Font.loadFont(
                VendingGUI.class.getResource("digital-7.ttf").toExternalForm(),
                10);


        //Everything is inside the border Pane
        BorderPane borderPane = new BorderPane();

        //GRID PANE
        GridPane gridPane = new GridPane();
        gridPane.setId("borderCenter");
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        //set the preferred width of each button so they aren't all different sizes
        gridPane.setPrefSize(100,90);

        /////////////PRODUCT BUTTONS//////////////


        for(int i = 0; i < 20; i++){
            //create a new object of type ProductButton. Add it to our arrayList so we can access it outside
            //the main method.
            productButtons.add(newProductButton());
        }

        //all our buttons are the same, so we can just use a for each loop to access them
        int row = 0, column = 0;
        for(ProductButton productButton : productButtons){
            //get the button from the current ProductButton object
            productButton.getButton().setText(productButton.getDescription());
            //set the dimensions to the PrefSize of the gridPane
            productButton.getButton().setPrefSize(gridPane.getPrefWidth(),gridPane.getPrefHeight());
            //wrap the text if it's too long
            productButton.getButton().setWrapText(true);
            //set the text to center
            productButton.getButton().setTextAlignment(TextAlignment.CENTER);
            //add the button to our gridPane on the current column and row
            gridPane.add(productButton.getButton(), column, row);
            //set the style id of the button so we can use css from style.css
            productButton.getButton().setId("productButton");
            //when pressed, call handleProductButton() and call the method to return a String of "location,description,price"
            productButton.getButton().setOnAction(e -> handleProductButtonPress(productButton.getProductInfo()));

            //increment the column position
            column++;
            //when column mod 4 gives a remainder of 0
            if(column%4 == 0 && column > 0){
                //reset to column index 0
                column = 0;
                //increment our row index
                row += 1;
            }
        }
        /////////////END OF PRODUCTS//////////////

        ////////////Left Pane/////////////

        //Buy Products//text field must not be empty
        VBox vBox = new VBox(10);
        vBox.setId("borderLeft");
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


        //set our login icon image. Surrounded in a try catch encase of an IOException
        try {
            Image loginIcon = new Image(new FileInputStream("userLoginIcon.png"));
            ImageView imageView = new ImageView(loginIcon);
            vBox.getChildren().add(imageView);
            imageView.setFitHeight(150);
            imageView.setFitWidth(150);
        }catch(IOException e){
            createNewErrorAlert(e.getMessage() + "\n\nIcon could not be loaded");
        }



        //Username label
        Label nameLabel = new Label("Username");
        nameLabel.setId("loginLabel");
        signInGridPane.add(nameLabel, 0, 0);

        //Username text
        signInGridPane.add(nameTextField, 1, 0);
        nameTextField.setId("loginTextField");
        //if a key is pressed, the password text field will be enabled. Login button will be disabled
        //if the username is ever empty.
        nameTextField.setOnKeyReleased(e -> handleKeyReleased(nameTextField, passwordField, login));


        //Password label
        Label passwordLabel = new Label("Password");
        passwordLabel.setId("loginLabel");
        signInGridPane.add(passwordLabel, 0, 1);

        //Password text
        signInGridPane.add(passwordField, 1, 1);
        passwordField.setDisable(true);
        passwordField.setId("loginTextField");
        //if a key is pressed, the login button will be enabled
        passwordField.setOnKeyReleased(e -> handleKeyReleased(passwordField, login));

        vBox.getChildren().add(signInGridPane);

        //HBox for the two login buttons
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets (0,0,30,0));
        hBox.setPrefWidth(100);
        hBox.setAlignment(Pos.CENTER_RIGHT);


        //Login button
        login.setDisable(true);
        login.setPrefWidth(hBox.getPrefWidth());
        login.setId("loginButton");
        //Verify user credentials when login button is clicked. Checks if admin or customer.
        //the method is passed the text in nameTextField and passwordField, and the login button
        login.setOnAction(e -> verifyUser(nameTextField, passwordField,login));
        hBox.getChildren().add(0,login);

        //Logout button
        Button logout = new Button("Logout");
        logout.setPrefWidth(hBox.getPrefWidth());
        logout.setId("loginButton");
        //when clicked, the called method will clear the name, password and product info textFields, and disable buy product button
        logout.setOnAction(e -> handleLogout(nameTextField, passwordField, selectedProductInfo, buyProductButton));
        hBox.getChildren().add(1,logout);

        //Add the HBox to the VBox
        vBox.getChildren().add(hBox);

        ///////End of User Login//////////

        /////Product info and buy///////////

        //TextField
        selectedProductInfo.setEditable(false);
        selectedProductInfo.setId("selectedProductTextField");
        vBox.getChildren().add(selectedProductInfo);

        //buy button
        vBox.getChildren().add(buyProductButton);
        buyProductButton.setId("buyButton");
        buyProductButton.setPrefSize(130,35);
        buyProductButton.setDisable(true);

        ///////////////// Admin Menu /////////////////////////
        //This section creates an admin menu in the menu bar, that has items that allow the admin to add products
        //or shut down the machine

        MenuBar menuBar = new MenuBar();

        borderPane.setTop(menuBar);

        Menu menu = new Menu();
        menuBar.getMenus().add(menu);
        menu.setText("Admin");

        //Admin add products
        addProductsItem.setText("Add Products");
        addProductsItem.setDisable(true);
        //when the add products item is clicked, the called method will create a new dialog, enabling the admin to add products
        addProductsItem.setOnAction(e -> handleAddProductsDialog());
        menu.getItems().add(addProductsItem);

        //Admin shut down
        //Saves the state of the machine and closes the program
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
        Scene scene = new Scene(borderPane, 880, 550);
        //load our css file
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();



    }

    /**
     * Method verifies the credentials passed. First checks if they are an admin. If not, then checks if they are a client.
     * @param name the text from nameTextField
     * @param pass the text from passwordField
     * @param login the login button
     */
    private static void verifyUser(TextField name, PasswordField pass,Button login){
        String username = name.getText();
        String password = pass.getText();
        //checks if it's an admin
        boolean isAdmin = vendingMachine.validateAdmin(username, password);

        if(isAdmin){
            createNewConfirmationAlert("Now in admin mode. Use the Admin drop down menu tab to add products or turn off the vending machine");
            isInAdminMode = true;
            //enable the items in the admin menu
            addProductsItem.setDisable(false);
            shutDownItem.setDisable(false);
            login.setDisable(true);
            name.setEditable(false);
            pass.setEditable(false);

        }else {
            //Looks for the client. If the credentials are correct, the method will add a setOnAction() to the buy button
            login.setDisable(true);
            verifyClient(username, password, name, pass);
        }
    }

    /**
     * Checks the system to see if the username and password match a user. If so, the buy product button can now be used.
     * @param name        the name of the username retrieved from the textfield
     * @param password    the password of the username retrieved from the textfield
     */
    public static void verifyClient(String name, String password, TextField textField, PasswordField passwordField) {

        Client client = vendingMachine.validateClient(name, password);

        //getCredentials will get user details, verify them, and return true if they are found in the Verification object
        if (client != null) {
            NumberFormat formatter = new DecimalFormat("#0.00");
            createNewConfirmationAlert("\t\t\t\t" + name + " signed in successfully!" + "\n\n\t\t\t\tCurrent balance is €" + formatter.format(client.getBalance()) +
                    "\n\nSelect a product to see information, and press the \"Buy product\" button to purchase that product.");

            textField.setEditable(false);
            passwordField.setEditable(false);
            //now that we have successfully logged in, enable the buy product button
            buyProductButton.setDisable(false);
            //when the buyProductButton is clicked, buyProduct() will be called, it will throw an exception if there
            //is no text in the selected product text field
            buyProductButton.setOnAction(e -> {
                try {
                    buyProduct(client, selectedProductInfo.getText());
                } catch (VendingException e1) {
                    createNewErrorAlert(e1.getMessage());
                }
            });

        } else {
            createNewErrorAlert("Username or password is incorrect!");
        }

    }

    /**
     * buys the selected product from the vending machine. This method is called from verifyClient().
     * @param client      is the user purchasing the item
     * @param productInfo is the product's toString()
     */

    private static void buyProduct(Client client, String productInfo){

        //if there are no products selected, throw an exception
        if (selectedProductInfo.getText().isEmpty() || selectedProductInfo.getText().trim().isEmpty()) {
            throw new VendingException("Error! There is no product currently selected");
        }

        //first we get the product location
        String location = productInfo.substring(0, 2);

        //now we get the price of that product. If there is no product in that location, thereAreProducts
        //will be false
        boolean thereAreProducts = true;
        double price = -1;
        try {
            price = vendingMachine.getPrice(location);
        } catch (VendingException e) {
            thereAreProducts = false;
            createNewErrorAlert("\t\t\t\t" + e.getMessage());
        }

        //if we were able to get the price of a product
        if (thereAreProducts) {
            try {
                //update the clients balance
                client.updateBalance(price);
                //if client does not have enough credit in their balance, the following lines will not be executed,
                //instead jumping to the catch clause.
                String pInfo = vendingMachine.buyProduct(location);
                NumberFormat formatter = new DecimalFormat("#0.00");
                createNewConfirmationAlert("\t\t\t\t" + client.getUsername() + " purchased a " + pInfo + "\n\n\t\t\t\tBalance = €" + formatter.format(client.getBalance()));
                //clears the label displaying selected product info
                selectedProductInfo.clear();
                //updates the corresponding product button that was pressed. Changes that button's text to "Empty" if there are no more products left
                updateButtonsText(location);
            } catch (VendingException e) {
                createNewErrorAlert("\t\t\t\t" + e.getMessage());
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
                    productButton.setDescription("Empty");
                    productButton.setPrice("0.00");
                }
            }
        }
    }



    /**
     * Event handler for when a product button is pressed. The string representation of the product is
     * entered into the text field selectedProductInfo. This allows that product to then be purchased.
     * @param text
     */
    private static void changeSelectedProduct(String text){
        selectedProductInfo.setText(text);
    }


    /**
     * When called this method creates a new ProductButton object, which will contain a button,
     * product description, location and price.
     * The method gets a list of type String all product object information from the vendingMachine object,
     * where each element has the products information separated by commas.
     * int buttonObjectCounter decides what element of the array is to be selected
     */
    private static int buttonObjectCounter = 0;
    private static ProductButton newProductButton(){
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

    /**
     * Creates a new pop up confirmation dialog box
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
     * Creates a new pop up error dialog box
     * @param message is the message to be displayed
     */

    public static void createNewErrorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    /**
     * Creates a new warning dialog box. Method returns the user confirmation (Yes/No)from the
     * dialogPane as a String. Used when admin is shutting down machine
     * @param message is the message to be displayed
     * @return the users button choice
     */
    public static String createNewWarningAlert(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        //Yes and no buttons need to be added
        alert.getButtonTypes().add(ButtonType.YES);
        alert.getButtonTypes().add(ButtonType.NO);
        //OK and Cancel buttons need to be removed as we don't need the,
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        alert.getButtonTypes().remove(ButtonType.OK);
        //this is to stop the dialog from cutting off the text
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
    private static void handleLogout(TextField name, PasswordField password, TextField products, Button buy){
        String user = name.getText();
        if(user.isEmpty() || user.trim().isEmpty()){
            createNewConfirmationAlert("No user currently signed in!");
        }else {
            name.clear();
            name.setEditable(true);
            password.clear();
            password.setDisable(true);
            password.setEditable(true);
            products.clear();
            buy.setDisable(true);
            //if it is an admin that was signed in....
            if (isInAdminMode) {
                handleAdminLogout();
            }

            createNewConfirmationAlert(user + " has signed out successfully");
        }

    }

    /**
     * Method is called by handleLogout(). It handles when an admin tries to logout.
     * Disables admin specific buttons. Sets boolean isInAdminMode to false as the
     * system is no longer in admin mode.
     */
    private static void handleAdminLogout(){
        isInAdminMode = false;
        addProductsItem.setDisable(true);
        shutDownItem.setDisable(true);
    }

    //////////////////////////////ADD PRODUCTS/////////////////////////////
    /**
     * This method creates a new Dialog for the admin to interact with and add products
     */
    private static void handleAddProductsDialog(){
        //Creating a new Dialog object which will have a TextField objects etc added to it
        Dialog dialog = new Dialog();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.setTitle("Add products");
        dialog.setHeaderText("Pick a product location to re-stock products or, pick\nan empty location to add a new product");


        //GridPane will contain our textFields,ComboBox and Spinner
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        //Add the gridPane to the dialog box
        dialog.getDialogPane().setContent(gridPane);


        //This combo box will display all product locations
        ComboBox<String> comboBox = new ComboBox<>();
        for(ProductButton button : productButtons){
            //add each location as a new item
            comboBox.getItems().add(button.getLocation());
        }

        //List of locations
        comboBox.setValue("Select location");
        //allow for span of two columns
        gridPane.add(comboBox,0,0,2,1);

        //Location label
        Label locationLabel = new Label("Location:");
        gridPane.add(locationLabel,0,1);
        gridPane.setHalignment(locationLabel, HPos.CENTER);
        //Location textField
        TextField location = new TextField();
        location.setEditable(false);
        gridPane.add(location,1,1);

        //Description label
        Label productLabel = new Label("Description:");
        gridPane.add(productLabel,0,2);
        gridPane.setHalignment(productLabel, HPos.CENTER);
        //Description textField
        TextField name = new TextField();
        name.setEditable(false);
        name.setStyle("-fx-border-color: transparent");
        gridPane.add(name,1,2);

        //Price label
        Label priceLabel = new Label("Price:");
        gridPane.add(priceLabel,0,3);
        gridPane.setHalignment(priceLabel, HPos.CENTER);
        //Price textField
        TextField price = new TextField();
        price.setEditable(false);
        price.setStyle("-fx-border-color: transparent");
        gridPane.add(price,1,3);

        //Quantity in stock label
        Label quantityLabel = new Label("Quantity in stock:");
        gridPane.add(quantityLabel,0,4);
        gridPane.setHalignment(quantityLabel, HPos.CENTER);
        //Quantity in stock textField
        TextField quantity = new TextField();
        quantity.setEditable(false);
        gridPane.add(quantity,1,4);

        //Spinner so the user can increment the amount of new products to be added
        Spinner<Integer> spinner = new Spinner<>();
        //set range 1 - 10 and default value of 1 to be shown
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10,1));
        spinner.setEditable(false);
        gridPane.add(spinner,1,5);

        //Add button to add the input
        Button addProductsButton = new Button("Add");
        gridPane.add(addProductsButton,1,6);
        addProductsButton.setPrefWidth(80);
        gridPane.setHalignment(addProductsButton, HPos.RIGHT);
        //adds the new product / updates current product quantity if product already exists
        addProductsButton.setOnAction(e -> {
            try {
                handleAddNewProducts(location,name,price,quantity,spinner);
            } catch (VendingException e1) {
                createNewErrorAlert(e1.getMessage());
            }
        });

        //when an item is selected from the combo box, each respective TextField will change
        comboBox.setOnAction(e -> changeAddProductFields(comboBox.getValue(),location,name,price,quantity));

        dialog.show();
        }

    /**
     * This method is called by handleAddProductsDialog. The method finds the ProductButton object with the same location passed
     * as a parameter, in ArrayList buttons. Each textField passed has its text changed to corresponding values from the
     * ProductButton object.
     * If the selected location is empty, the description and price textField will have a red border
     * @param location is the location of the product chosen from a combo box
     * @param locationField is the displayed location
     * @param name displayed product name
     * @param price price
     * @param quantity how many of that product are in the machine
     */
    private static void changeAddProductFields(String location, TextField locationField, TextField name, TextField price, TextField quantity){
        ProductButton productButton;
        for(ProductButton pb : productButtons){
            if(pb.getLocation().equalsIgnoreCase(location)){

                //if it's an empty location, we set as editable so we can add new products, and add a style border
                if(pb.getDescription().equalsIgnoreCase("Empty") && vendingMachine.getProductQuantity(location) == 0){
                    name.setEditable(true);
                    price.setEditable(true);
                    name.setStyle("-fx-border-color: Red");
                    price.setStyle("-fx-border-color: Red");
                    name.setOnKeyReleased(e -> handleAddProductsTextKeyRelease(name, "name"));
                    price.setOnKeyReleased(e -> handleAddProductsTextKeyRelease(price, "price"));
                }else{
                    //otherwise, set the borders back to transparent for a seamless transition, and set the textFields to un-editable
                    name.setStyle("-fx-border-color: transparent");
                    price.setStyle("-fx-border-color: transparent");
                    name.setEditable(false);
                    price.setEditable(false);
                }

                //sets appropriate  textFields to their values
                productButton = pb;
                locationField.setText(location);
                name.setText(productButton.getDescription());
                price.setText(productButton.getPrice());
                quantity.setText(Integer.toString(vendingMachine.getProductQuantity(location)));

            }
        }
    }

    /**
     * This method verifies user input when they are trying to add a new product to an empty location.
     * @throws VendingException
     */
    private static void handleAddNewProducts(TextField location, TextField name, TextField price, TextField quantity, Spinner<Integer> spinner) throws VendingException{
        //Regexs used to check if user input matches what we want
        String pricePattern = "^\\d\\.\\d\\d?||\\d$";
        String namePattern = "^[A-Za-z .-]+$";

        if(location.getText().isEmpty() || location.getText().trim().isEmpty()){
            throw new VendingException("No location selected");
        }else if(name.getText().equalsIgnoreCase("Empty")){
            throw new VendingException("Product description can not equal \"Empty\"");
        }else if(!name.getText().matches(namePattern)){
            throw new VendingException("Product description does not match required format");
        }else if(name.getText().isEmpty() || name.getText().trim().isEmpty()){
            throw new VendingException("Product description cannot only contain blank spaces");
        }else if(spinner.getValue() <= 0){
            throw new VendingException("Incorrect product quantity of " + spinner.getValue() + ". Product quantity must be > 0" );
        }else if(!price.getText().matches(pricePattern) || Double.parseDouble(price.getText()) < 0.01) {
            throw new VendingException("Incorrect price format. Price of item must be less than €10 and greater than €0.01, and follow the format \"1\", \"1.1\" or \"1.11\"");
        } else {
            //if everything matches, try to add the product with vendingMachine.addProduct(). Method returns true if the product was added successfully
            try{
                boolean success = vendingMachine.addProduct(name.getText(),Double.parseDouble(price.getText()),location.getText(),spinner.getValue());
                if(success){
                    int value = spinner.getValue();
                    //reset our spinner to 1
                    spinner.getValueFactory().setValue(1);
                    //update the product button variables
                    updateProductButton(location.getText(),name.getText(),price.getText());
                    //update the text fields in the add products dialog to display new information
                    changeAddProductFields(location.getText(),location,name,price,quantity);
                    throw new VendingException(value + " " + name.getText() + " @ €" + price.getText() + " added successfully to " + location.getText());
                }
            }catch(VendingException e){
                throw new VendingException(e.getMessage());
            }
        }
    }

    /**
     * This method updates the text of a button representing a product. When empty, the text is changed to "Empty"
     * @param location location of product
     * @param description the new description
     * @param price the new price
     */
    private static void updateProductButton(String location, String description,String price){
        for(ProductButton pb : productButtons){
            if(pb.getLocation().equalsIgnoreCase(location)){
                pb.setDescription(description);
                pb.setPrice(price);
                pb.getButton().setText(description);
            }
        }
    }

    /**
     * This method is for when the admin is trying to add a new product to an empty location. The colour of the description and
     * price fields will change to red or green depending on if the input meets our requirements
     * @param textField the textfield the user is typing in
     * @param choice determines whether its the price or description label
     */
    private static void handleAddProductsTextKeyRelease(TextField textField, String choice){
        String text = textField.getText();
        String namePattern = "^[A-Za-z .-]+$";
        String pricePattern = "^\\d\\.\\d\\d?||\\d$";

        //css styling assigned to Strings
        String red = "-fx-border-color: Red;" +
                "-fx-focus-color:rgba(255,0,0,1.2);";
        String green = "-fx-border-color: #00ff00;" +
                "-fx-focus-color:rgba(0,255,0,1.2);";

        if(choice.equalsIgnoreCase("name") && text.matches(namePattern) && !text.isEmpty() && !text.trim().isEmpty() && !text.equalsIgnoreCase("empty")){
            textField.setStyle(green);
        }else if(choice.equalsIgnoreCase("price") && text.matches(pricePattern)  && !text.isEmpty() && !text.trim().isEmpty()){
            if(Double.parseDouble(text) > 0){
                textField.setStyle(green);
            }else{
                textField.setStyle(red);
            }
        } else{
            textField.setStyle(red);
        }
    }

    ///////////////////////END OF ADD PRODUCTS////////////////////////

    /**
     * This method prompts the user with a yes or no answer to turn off the vendingMachine, by
     * calling vendingMachine.powerOff()
     *
     * @throws IOException encase the vendingMachine object cannot correctly save all data
     */
    private static void shutDown() throws IOException{
        //createNewWarningAlert() prompts the user to select yes/ no, and the return is assigned
        //to shutDown
        String shutDown = createNewWarningAlert("Shut down vending machine. Are you sure?");
        if(shutDown.equalsIgnoreCase("yes")) {
            try {
                vendingMachine.powerOff();
                //closes GUI
                Platform.exit();
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }
        }
    }

    /**
     * Handles when a character is typed into a text field. Makes a button active or inactive if there is
     * or isn't text in the text field
     * @param textField is the text field we are checking for text
     * @param button is the button we want to disable/ enable
     */
    private static void handleKeyReleased(TextField textField, Button button){
        String text = textField.getText();
        //assign either true or false depending on boolean returned
        boolean disableButtons = text.isEmpty() || text.trim().isEmpty();//.trim is for white spaces
        button.setDisable(disableButtons);
    }

    /**
     * Handles Username events on a login page. Sets password field to enabled. Always disables login button
     * if there is no text in the username field
     */
    private static void handleKeyReleased(TextField username, TextField password, Button button){
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
