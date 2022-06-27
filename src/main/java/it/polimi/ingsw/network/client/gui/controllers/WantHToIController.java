package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.messages.clientMessages.cmStudentsMovementsHToI;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

public class WantHToIController extends GenericController {

    @FXML
    private RadioButton yesButton;

    @FXML
    public void onAskButton() {
        if (yesButton.isSelected()) {
            getGui().getSceneManager("hToI/MoveHToI.fxml").getController().update();
            getGui().updateSceneOnStage("hToI/MoveHToI.fxml");
        }
        else
            getGui().getClient().send(getGui().getGson().toJson(new cmStudentsMovementsHToI(null), cmStudentsMovementsHToI.class));
    }
}
