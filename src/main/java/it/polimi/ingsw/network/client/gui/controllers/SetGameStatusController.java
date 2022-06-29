package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.messages.clientMessages.cmSetGameStatus;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;

public class SetGameStatusController extends GenericController {

    @FXML
    private ChoiceBox numOfPlayers;
    @FXML
    private RadioButton normal;

    /**
     * Activation of "onSendButtonClick" in the "SetGameStatus.fxml" scene; sends a "cmSetGameStatus" message to the server, setting the number of players and game mode chosen by the first connected player.
     */
    @FXML
    public void onSendButtonClick() {
        cmSetGameStatus message;

        if (normal.isSelected()) {
            getGui().getViewController().updateGameStatus(Integer.parseInt(numOfPlayers.getValue().toString()), "normal");
            message = new cmSetGameStatus(Integer.parseInt(numOfPlayers.getValue().toString()), "normal");
        }
        else {
            getGui().getViewController().updateGameStatus(Integer.parseInt(numOfPlayers.getValue().toString()), "expert");
            message = new cmSetGameStatus(Integer.parseInt(numOfPlayers.getValue().toString()), "expert");
        }

        String text = getGui().getGson().toJson(message, cmSetGameStatus.class);
        getGui().getClient().send(text);
    }
}
