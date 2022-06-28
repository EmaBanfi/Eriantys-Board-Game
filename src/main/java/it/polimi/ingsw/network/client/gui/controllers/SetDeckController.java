package it.polimi.ingsw.network.client.gui.controllers;

import com.google.gson.Gson;
import it.polimi.ingsw.network.messages.clientMessages.cmDeck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class SetDeckController extends GenericController {

    @FXML
    private ChoiceBox<String> setDeck;

    @FXML
    public void deckButton(ActionEvent event){
        String chosenDeck = setDeck.getValue();
        getGui().getViewController().getMainPlayer().setDeckColor(chosenDeck);
        Gson gson = new Gson();
        cmDeck message = new cmDeck(chosenDeck);
        String text = gson.toJson(message, cmDeck.class);
        getGui().getClient().send(text);
    }

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
