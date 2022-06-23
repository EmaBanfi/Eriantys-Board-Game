package it.polimi.ingsw.network.client.gui.controllers;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.gui.GUI;
import it.polimi.ingsw.network.messages.clientMessages.cmNickname;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SetUp implements GenericController {

    @FXML
    private TextField ipAddress;
    @FXML
    private TextField nickname;
    private GUI gui;
    private Gson gson = new Gson();

    @FXML
    public void onSendButtonClick(ActionEvent event) {
        cmNickname message = new cmNickname(nickname.getText());
        String text = gson.toJson(message, cmNickname.class);
        gui.getClient().send(text);
    }

    @FXML
    public void onPlayButton(ActionEvent event){
        gui.setClient(new Client(gui, ipAddress.getText()));
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}