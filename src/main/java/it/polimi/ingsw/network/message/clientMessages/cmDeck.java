package it.polimi.ingsw.network.message.clientMessages;

public class cmDeck extends ClientMessage{
    String deck;

    public cmDeck(String deck) {
        this.deck = deck;
    }
}
