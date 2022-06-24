package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.gui.GUI;
import it.polimi.ingsw.network.messages.clientMessages.cmSetGameStatus;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class SetGameStatusController implements GenericController {

    private GUI gui;
    @FXML
    private ChoiceBox numOfPlayers;
    @FXML
    private RadioButton normal;
    @FXML
    private RadioButton expert;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    @FXML
    public void onSendButtonClick() {
        cmSetGameStatus message;

        if (normal.isSelected())
            message =new cmSetGameStatus(Integer.parseInt(numOfPlayers.getValue().toString()), "normal");
        else
            message = new cmSetGameStatus(Integer.parseInt(numOfPlayers.getValue().toString()), "expert");
        String text = gui.getGson().toJson(message, cmSetGameStatus.class);
        gui.getClient().send(text);
    }
}
