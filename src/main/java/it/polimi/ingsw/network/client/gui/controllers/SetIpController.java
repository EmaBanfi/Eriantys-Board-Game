package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.gui.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SetIpController implements GenericController {

    private GUI gui;
    @FXML
    private TextField ipAddress;

    @FXML
    public void onPlayButton(ActionEvent event) {
        gui.setClient(ipAddress.getText());
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
