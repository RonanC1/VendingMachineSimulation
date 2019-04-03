package com.Lectures;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {
    @FXML
    private Button button1;

    @FXML
    public void onButton1Clicked(ActionEvent e){
        System.out.println("Hello button");
    }
}
