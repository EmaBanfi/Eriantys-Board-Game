package it.polimi.ingsw.network.message.serverMessages;

public class smNotifyUsedDeck extends ServerMessage{
    String deck;

    public smNotifyUsedDeck(String deck) {
        this.deck = deck;
    }
}
