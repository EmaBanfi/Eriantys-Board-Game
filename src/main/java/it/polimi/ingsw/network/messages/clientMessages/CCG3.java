package it.polimi.ingsw.network.messages.clientMessages;

@SuppressWarnings("FieldCanBeLocal")
public class CCG3 extends ClientMessage {

    private int cardId;
    private int movements;

    public CCG3() {
    }

    public CCG3(int cardId, int movements) {
        this.cardId = cardId;
        this.movements = movements;
        setType("CCG3");
    }
}
