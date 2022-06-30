package it.polimi.ingsw.network.client.gui.controllers;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.gui.ValueToUpdate;
import it.polimi.ingsw.network.messages.clientMessages.cmDeck;
import it.polimi.ingsw.network.messages.clientMessages.cmTower;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class SetDeckController extends GenericController {

    @FXML
    private Button mage;

    @FXML
    private Button king;

    @FXML
    private Button sage;

    @FXML
    private Button witch;

    /**
     * Updates the "SetTower.fxml" scene, unblocking the available decks.
     */
    @Override
    public void update(){
        if(getGui().getViewController().getAvailableDecks().contains("KING"))
            king.setDisable(false);
        if(getGui().getViewController().getAvailableDecks().contains("MAGE"))
            mage.setDisable(false);
        if(getGui().getViewController().getAvailableDecks().contains("WITCH"))
            witch.setDisable(false);
        if(getGui().getViewController().getAvailableDecks().contains("SAGE"))
            sage.setDisable(false);
    }

    /**
     * Sets the chosen deck of a player and sends a "cmDeck" message to the server with the chosen deck (king).
     */
    @FXML
    void onKingButton(ActionEvent event) {
        getGui().getViewController().getMainPlayer().setDeckColor("KING");

        getGui().updateGameBoard(ValueToUpdate.DECK);

        cmDeck message = new cmDeck("KING");
        getGui().getClient().send(getGui().getGson().toJson(message, cmDeck.class));

        getGui().updateSceneOnStage("Wait.fxml");
    }

    /**
     * Sets the chosen deck of a player and sends a "cmDeck" message to the server with the chosen deck (mage).
     */
    @FXML
    void onMageButton(ActionEvent event) {
        getGui().getViewController().getMainPlayer().setDeckColor("MAGE");

        getGui().updateGameBoard(ValueToUpdate.DECK);

        cmDeck message = new cmDeck("MAGE");
        getGui().getClient().send(getGui().getGson().toJson(message, cmDeck.class));

        getGui().updateSceneOnStage("Wait.fxml");
    }

    /**
     * Sets the chosen deck of a player and sends a "cmDeck" message to the server with the chosen deck (sage).
     */
    @FXML
    void onSageButton(ActionEvent event) {
        getGui().getViewController().getMainPlayer().setDeckColor("SAGE");

        getGui().updateGameBoard(ValueToUpdate.DECK);

        cmDeck message = new cmDeck("SAGE");
        getGui().getClient().send(getGui().getGson().toJson(message, cmDeck.class));

        getGui().updateSceneOnStage("Wait.fxml");
    }

    /**
     * Sets the chosen deck of a player and sends a "cmDeck" message to the server with the chosen deck (witch).
     */
    @FXML
    void onWitchButton(ActionEvent event) {
        getGui().getViewController().getMainPlayer().setDeckColor("WITCH");

        getGui().updateGameBoard(ValueToUpdate.DECK);

        cmDeck message = new cmDeck("WITCH");
        getGui().getClient().send(getGui().getGson().toJson(message, cmDeck.class));

        getGui().updateSceneOnStage("Wait.fxml");
    }
}
