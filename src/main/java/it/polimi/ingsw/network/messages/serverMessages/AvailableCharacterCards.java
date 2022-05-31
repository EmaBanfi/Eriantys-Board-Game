package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.model.CharacterCards.CharacterCard;

import java.util.ArrayList;

public class AvailableCharacterCards extends ServerMessage{

    private ArrayList<CharacterCard> availableCharacterCards;

    public AvailableCharacterCards() {
    }

    public AvailableCharacterCards( ArrayList<CharacterCard> availableCharacterCards) {
        this.availableCharacterCards = availableCharacterCards;
        setType("available cc");
    }

    /**
     * print the message and call the method closeConnection() of the client
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {

        client.getView().updateCharacterCards(availableCharacterCards);
    }
}
