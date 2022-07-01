package it.polimi.ingsw.network.client.gui.controllers;

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.clientMessages.cmCCG5;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CCG5Controller extends GenericController{

    private int cardId;
    @FXML
    private ChoiceBox<Integer> availableIslands;
    @FXML
    private Label effectTitle;
    @FXML
    private Button sendButton;

    /**
     * Sets the ID of the card used.
     * @param value cardID
     */
    @Override
    public void setInput(Integer value){
        cardId = value;
    }

    /**
     * Activation of "onSendButton" in the "CCG5.fxml" scene; sends a "cmCCG5" message to the server and decreases the number of block cards if the card 5 is used.
     */
    @FXML
    public void onSendButton(){
        int island = availableIslands.getValue();

        if(cardId==3)
            island = island -1;

        cmCCG5 message = new cmCCG5(cardId, island);
        getGui().getClient().send(new Gson().toJson(message, cmCCG5.class));

        if(cardId == 5)
            getGui().getCharacterCardById(5).updateAvailableBlockCards(false);

        reset();

        getGui().setUsedCC();
        getGui().backToScene();
    }

    /**
     * Activation of "onSHowIslandButton" in the "MoveHToI.fxml" scene; shows the islands available on the GameBoard.
     */
    @FXML
    public void onShowIslandsButton() {
        Stage stage = new Stage();

        stage.setMinWidth(1200);
        stage.setMinHeight(800);

        getGui().getSceneManager("ShowIslands.fxml").getController().setInput((Integer) null);
        stage.setScene(getGui().getSceneManager("ShowIslands.fxml").getScene());

        stage.show();
    }

    /**
     * Updates the "CCG5.fxml" scene, adding the available islands to the choice box.
     */
    @Override
    public void update() {
        int maxNumIslands = getGui().getViewController().getAvailableIslands().size();

        for (int i = 1; i <= maxNumIslands; i++) {
            availableIslands.getItems().add(i);
        }

        if(cardId == 3)
            effectTitle.setText("Effect of the card 3");
        else if(cardId == 5)
            effectTitle.setText("Effect of the card 5");
        else
            effectTitle.setText("Effect of the card 6");

        availableIslands.setValue(availableIslands.getItems().get(0));
    }

    private void reset() {
        availableIslands.getItems().clear();
    }
}
