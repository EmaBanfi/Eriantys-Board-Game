package it.polimi.ingsw.network.client.gui.controllers;

import com.google.gson.Gson;
import it.polimi.ingsw.network.client.gui.ValueToUpdate;
import it.polimi.ingsw.network.messages.clientMessages.cmDeck;
import it.polimi.ingsw.network.messages.clientMessages.cmTower;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SetDeckController extends GenericController {

    @FXML
    private ImageView mage;
    @FXML
    private ImageView king;
    @FXML
    private ImageView sage;
    @FXML
    private ImageView witch;
    private final ColorAdjust monochrome = new ColorAdjust();

    /**
     * Updates the "SetTower.fxml" scene, unblocking the available decks.
     */
    @Override
    public void update(){
        monochrome.setSaturation(-1);

        if(!getGui().getViewController().getAvailableDecks().contains("KING"))
            disablePlusGray(king);
        if(!getGui().getViewController().getAvailableDecks().contains("MAGE"))
            disablePlusGray(mage);
        if(!getGui().getViewController().getAvailableDecks().contains("WITCH"))
            disablePlusGray(witch);
        if(!getGui().getViewController().getAvailableDecks().contains("SAGE"))
            disablePlusGray(sage);
    }

    private void disablePlusGray(ImageView deck) {
        deck.setDisable(true);
        deck.setEffect(monochrome);
    }

    /**
     * Sets the chosen deck of a player and sends a "cmDeck" message to the server with the chosen deck (king).
     */
    @FXML
    void onKingImage(MouseEvent event) {
        sendMessage("KING");
    }

    /**
     * Sets the chosen deck of a player and sends a "cmDeck" message to the server with the chosen deck (mage).
     */
    @FXML
    void onMageImage(MouseEvent event) {
        sendMessage("MAGE");
    }

    /**
     * Sets the chosen deck of a player and sends a "cmDeck" message to the server with the chosen deck (sage).
     */
    @FXML
    void onSageImage(MouseEvent event) {
        sendMessage("SAGE");
    }

    /**
     * Sets the chosen deck of a player and sends a "cmDeck" message to the server with the chosen deck (witch).
     */
    @FXML
    void onWitchImage(MouseEvent event) {
        sendMessage("WITCH");
    }

    private void sendMessage(String string) {
        getGui().getViewController().getMainPlayer().setDeckColor(string);

        getGui().updateGameBoard(ValueToUpdate.DECK);

        cmDeck message = new cmDeck(string);
        getGui().getClient().send(getGui().getGson().toJson(message, cmDeck.class));

        getGui().updateSceneOnStage("Wait.fxml");
    }
}
