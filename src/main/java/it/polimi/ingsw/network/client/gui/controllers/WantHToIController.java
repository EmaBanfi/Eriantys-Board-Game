package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.messages.clientMessages.cmStudentsMovementsHToI;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

public class WantHToIController extends GenericController {

    @FXML
    private RadioButton yesButton;

    /**
     * Activation of "OnAskButton" in the "WantHToI.fxml" scene; if the player selects "yes" then open the "MoveHToI.fxml" scene to manage the students movements, else sends a "cmStudentsMovementsHToI" to the server with null parameter.
     */
    @FXML
    public void onAskButton() {
        if (yesButton.isSelected()) {
            getGui().getSceneManager("hToI/MoveHToI.fxml").getController().update();
            getGui().updateSceneOnStage("hToI/MoveHToI.fxml");
        }
        else
            getGui().getClient().send(getGui().getGson().toJson(new cmStudentsMovementsHToI(null), cmStudentsMovementsHToI.class));

        reset();
    }

    private void reset() {
        yesButton.setSelected(true);
    }
}
