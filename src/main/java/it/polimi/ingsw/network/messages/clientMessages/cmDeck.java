package it.polimi.ingsw.network.messages.clientMessages;

public class cmDeck extends ClientMessage{
    String deck;

    public cmDeck(String deck) {
        this.deck = deck;
    }
}
