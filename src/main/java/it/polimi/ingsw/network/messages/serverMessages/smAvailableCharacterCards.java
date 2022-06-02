package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.client.Client;

import java.util.ArrayList;

public class smAvailableCharacterCards extends ServerMessage{

    private ArrayList<Integer> availableCharacterCards;

    public smAvailableCharacterCards() {
    }

    public smAvailableCharacterCards(ArrayList<Integer> availableCharacterCards) {
        this.availableCharacterCards = availableCharacterCards;
        setType("available cc");
    }

    /**
     * print the message and call the method closeConnection() of the client
     * @param client client to which the message is sent
     */
    @Override
    public void processMessage(Client client) {

        for (Integer card : availableCharacterCards)
            client.getView().addAvailableCard(card);
    }
}
