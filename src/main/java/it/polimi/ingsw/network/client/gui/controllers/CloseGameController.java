package it.polimi.ingsw.network.client.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CloseGameController extends GenericController {

    @FXML
    private Label message;
    private int exit;

    @Override
    public void setInput(String value) {
        message.setText(value);
        message.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(message, 0.0);
        AnchorPane.setRightAnchor(message, 0.0);
        message.setAlignment(Pos.CENTER);
    }

    @Override
    public void setInput(Integer value) {
        exit = value;
    }

    @FXML
    void onQuitButton(ActionEvent event) {
        getGui().systemExit(exit);
    }

}
