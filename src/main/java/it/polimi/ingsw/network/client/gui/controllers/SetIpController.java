package it.polimi.ingsw.network.client.gui.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SetIpController extends GenericController {

    @FXML
    private TextField ipAddress;

    /**
     * Activation of "onPlayButton" in the "SetIp.fxml" scene; sets the server ip to connect to.
     * @param event
     */
    @FXML
    public void onPlayButton(ActionEvent event) {
        if(!ipAddress.getText().isEmpty()) {
            getGui().setClient(ipAddress.getText());

            getGui().updateSceneOnStage("Wait.fxml");
        }else
            ipAddress.setStyle("-fx-border-color: red");
    }
}
