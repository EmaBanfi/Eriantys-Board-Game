package it.polimi.ingsw.network.messages.clientMessages;

@SuppressWarnings("FieldCanBeLocal")
public class cmDeck extends ClientMessage {

    private String deck;

    public cmDeck() {
    }

    public cmDeck(String deck) {
        this.deck = deck;
        setType("deck");
    }

}
