package it.polimi.ingsw.network.client.gui.controllers;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.gui.ValueToUpdate;
import it.polimi.ingsw.network.messages.clientMessages.cmDeck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class SetDeckController extends GenericController {

    @FXML
    private ChoiceBox<String> setDeck;

    /**
     * Activation of the "deckButton" in "SetDeck.fxml" scene; sets the chosen deck of a player and sends a "cmDeck" message to the server with the chosen deck.
     * @param event
     */
    @FXML
    public void deckButton(ActionEvent event){
        String chosenDeck = setDeck.getValue();
        getGui().getViewController().getMainPlayer().setDeckColor(chosenDeck);

        getGui().updateGameBoard(ValueToUpdate.DECK);

        Gson gson = new Gson();
        cmDeck message = new cmDeck(chosenDeck);
        String text = gson.toJson(message, cmDeck.class);
        getGui().getClient().send(text);
    }

    /**
     * Updates the "SetTower.fxml" scene, adding the available decks to the choice box.
     */
    @Override
    public void update(){
        if(getGui().getViewController().getAvailableDecks().contains("KING"))
            setDeck.getItems().add("King");
        if(getGui().getViewController().getAvailableDecks().contains("MAGE"))
            setDeck.getItems().add("Mage");
        if(getGui().getViewController().getAvailableDecks().contains("WITCH"))
            setDeck.getItems().add("Witch");
        if(getGui().getViewController().getAvailableDecks().contains("SAGE"))
            setDeck.getItems().add("Sage");

        setDeck.setValue(setDeck.getItems().get(0));
    }
}
