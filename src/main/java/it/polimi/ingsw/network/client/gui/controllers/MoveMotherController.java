package it.polimi.ingsw.network.client.gui.controllers;

import it.polimi.ingsw.network.messages.clientMessages.cmMoveMother;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class MoveMotherController extends GenericController {

    @FXML
    private ChoiceBox<Integer> newPosition;

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
    }

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
    public void onSendButton() {
        int position = newPosition.getValue() - 1;

        cmMoveMother message = new cmMoveMother(convertIslandToMovements(position));
        getGui().getClient().send(getGui().getGson().toJson(message, cmMoveMother.class));

        getGui().getViewController().getMotherNature().setCurrentIsland(position);
    }

    private int convertIslandToMovements(int island){
        int movements = island - getGui().getViewController().getMotherPosition();

        if(movements < 0)
            movements = getGui().getViewController().getAvailableIslands().size() + movements;

        return movements;
    }
}
