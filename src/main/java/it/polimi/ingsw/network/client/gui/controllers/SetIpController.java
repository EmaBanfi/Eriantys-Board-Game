package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.gui.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SetIpController extends GenericController {

    @FXML
    private TextField ipAddress;

    @FXML
    public void onPlayButton(ActionEvent event) {
        getGui().setClient(ipAddress.getText());
    }


}
