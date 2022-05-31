package it.polimi.ingsw.network.messages.clientMessages;

@SuppressWarnings("FieldCanBeLocal")
public class CCG2 extends ClientMessage{

    private int cardId;

    public CCG2() {
    }

    public CCG2(int cardId) {
        this.cardId = cardId;
        setType("CCG2");
    }
}
