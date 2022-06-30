package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.messages.clientMessages.cmNickname;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SetNicknameController extends GenericController {

    @FXML
    private TextField nickname;

    /**
     * Activation of the "onSendButtonClick" in "SetNickName.fxml" scene; sets the nickname chosen by a player and sends a "cmNickname" message to the server with the chosen nickname
     * @param event
     */
    @FXML
    public void onSendButtonClick(ActionEvent event) {
        if(!nickname.getText().isEmpty()) {
            getGui().getViewController().setMainPlayer(nickname.getText());

            cmNickname message = new cmNickname(nickname.getText());
            String text = getGui().getGson().toJson(message, cmNickname.class);
            getGui().getClient().send(text);
            getGui().updateSceneOnStage("Wait.fxml");
        }else{
            nickname.setStyle("-fx-border-color: red");
        }
    }
}