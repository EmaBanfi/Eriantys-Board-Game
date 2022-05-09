package it.polimi.ingsw.network.messages.serverMessages;

public class smNotifyUsedDeck extends ServerMessage{

    public smNotifyUsedDeck() {
        super();
    }

    public smNotifyUsedDeck(String deck) {
        super(deck);
        setType("notify used deck");
    }
}
