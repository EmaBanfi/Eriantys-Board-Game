package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.client.gui.GUI;
import it.polimi.ingsw.network.messages.clientMessages.cmNickname;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SetNicknameController implements GenericController {

    private GUI gui;
    @FXML
    private TextField nickname;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    @FXML
    public void onSendButtonClick(ActionEvent event) {
        cmNickname message = new cmNickname(nickname.getText());
        String text = gui.getGson().toJson(message, cmNickname.class);
        gui.getClient().send(text);
    }
}