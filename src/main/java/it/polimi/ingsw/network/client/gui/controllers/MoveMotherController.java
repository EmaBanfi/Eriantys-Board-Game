package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.messages.clientMessages.cmMoveMother;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class MoveMotherController extends GenericController {

    @FXML
    private ChoiceBox<Integer> newPosition;
    @FXML
    private Button activateCC;

    /**
     * Updates the "MoveMother.fxml" file, showing the islands on which Mother Nature can move.
     */
    @Override
    public void update() {
        int maxMovements = getGui().getViewController().getMainPlayer().getUsedSupportCard().getMovement();
        int motherPosition = getGui().getViewController().getMotherNature().getCurrentIsland() + 1;
        int numOfIslands = getGui().getViewController().getAvailableIslands().size();

        if (motherPosition + maxMovements >= numOfIslands) {
            for (int i = motherPosition + 1; i <= numOfIslands; i++)
                newPosition.getItems().add(i);
            int diff = (motherPosition + maxMovements) - numOfIslands;
            for (int i = 1; i <= diff; i++)
                newPosition.getItems().add(i);
        }
        else
            for (int i = motherPosition + 1; i <= (maxMovements + motherPosition); i++)
                newPosition.getItems().add(i);

        newPosition.setValue(newPosition.getItems().get(0));

        if (getGui().getViewController().getMode().equals("expert") && !getGui().getUsedCC())
            activateCC.setVisible(true);
    }

    /**
     * Activation of "onSHowIslandButton" in the "MoveMother.fxml" scene; shows the available islands on which Mother Nature can move.
     */
    @FXML
    public void onShowIslandButton() {
        Stage stage = new Stage();

        stage.setMinWidth(1200);
        stage.setMinHeight(800);

        getGui().getSceneManager("ShowIslands.fxml").getController().setInput(getGui().getViewController().getMainPlayer().getUsedSupportCard().getMovement());
        stage.setScene(getGui().getSceneManager("ShowIslands.fxml").getScene());

        stage.show();
    }

    @FXML
    public void onActivateCCButton() {
        getGui().getSceneManager("AskActivateCC.fxml").getController().update();
        getGui().updateSceneOnStageOnlyForCC("AskActivateCC.fxml");
    }

    /**
     * Activation of "onSendButton" in the "MoveMother.fxml" scene; sets the new Mother Nature position and sends a "cmMoverMother" message to the server with the new position chosen by the player.
     */
    @FXML
    public void onSendButton() {
        int position = newPosition.getValue() - 1;

        cmMoveMother message = new cmMoveMother(convertIslandToMovements(position));
        getGui().getClient().send(getGui().getGson().toJson(message, cmMoveMother.class));

        getGui().getViewController().getMotherNature().setCurrentIsland(position);

        reset();
    }

    /**
     * Converts the new chosen island of Mother Nature into the number of movements needed to reach that specific island from the current Mother Nature island.
     * @param island Island chosen by the player.
     */
    private int convertIslandToMovements(int island){
        int movements = island - getGui().getViewController().getMotherPosition();

        if(movements < 0)
            movements = getGui().getViewController().getAvailableIslands().size() + movements;

        return movements;
    }

    private void reset() {
        newPosition.getItems().clear();
    }
}
