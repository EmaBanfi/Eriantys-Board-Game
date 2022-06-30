package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.messages.clientMessages.cmCCG3;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

public class CCG3Controller extends GenericController {

    @FXML
    private RadioButton oneMoreMove;
    @FXML
    private Button sendButton;

    @FXML
    public void onSendButton() {
        cmCCG3 message;

        if (oneMoreMove.isSelected()) {
            getGui().getViewController().updateMotherPosition((getGui().getViewController().getMotherPosition() + 1) % getGui().getViewController().getAvailableIslands().size());
            message = new cmCCG3(1);
        }
        else {
            getGui().getViewController().updateMotherPosition((getGui().getViewController().getMotherPosition() + 2) % getGui().getViewController().getAvailableIslands().size());
            message = new cmCCG3(2);
        }

        getGui().getClient().send(getGui().getGson().toJson(message, cmCCG3.class));

        getGui().setUsedCC();
        Stage stage = (Stage) sendButton.getScene().getWindow();
        stage.close();
    }
}
