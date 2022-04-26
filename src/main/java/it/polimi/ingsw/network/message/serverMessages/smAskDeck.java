package it.polimi.ingsw.network.message.serverMessages;

import java.util.ArrayList;

public class smAskDeck extends ServerMessage{
    ArrayList<String> availableDecks;

    public smAskDeck(ArrayList<String> availableDecks) {
        this.availableDecks = availableDecks;
    }
}
