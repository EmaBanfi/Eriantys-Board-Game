package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyUsedDeck extends ServerMessage {

    private String deck;

    public smNotifyUsedDeck() {
    }

    public smNotifyUsedDeck(String message, String deck) {
        super(message);
        this.deck = deck;
        setType("notify used deck");
    }

}
