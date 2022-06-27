package it.polimi.ingsw.network.client.gui.controllers;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.gui.GUI;
import it.polimi.ingsw.network.messages.clientMessages.cmDeck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class SetDeckController extends GenericController {
    private GUI gui;
    private ChoiceBox choiceBox = new ChoiceBox();
    @FXML
    private String chosenDeck;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    @FXML
    public void deckButton(ActionEvent event){
        chosenDeck = choiceBox.getValue().toString();
        gui.getViewController().getMainPlayer().setDeckColor(chosenDeck);
        Gson gson = new Gson();
        cmDeck message = new cmDeck(chosenDeck);
        String text = gson.toJson(message, cmDeck.class);
        gui.getClient().send(text);
    }

    @Override
    public void update(){
        if(gui.getViewController().getAvailableDecks().contains("KING"))
            choiceBox.getItems().add("KING");
        if(gui.getViewController().getAvailableDecks().contains("MAGE"))
            choiceBox.getItems().add("MAGE");
        if(gui.getViewController().getAvailableDecks().contains("WITCH"))
            choiceBox.getItems().add("WITCH");
        if(gui.getViewController().getAvailableDecks().contains("SAGE"))
            choiceBox.getItems().add("SAGE");
    }

    public String getOutputString(){
        while(chosenDeck == null){}
        return chosenDeck;
    }
}
