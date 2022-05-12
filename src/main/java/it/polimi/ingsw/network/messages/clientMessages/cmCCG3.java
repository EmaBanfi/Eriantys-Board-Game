package it.polimi.ingsw.network.messages.clientMessages;

@SuppressWarnings("FieldCanBeLocal")
public class cmCCG3 extends ClientMessage {

    private int cardId;
    private int movements;

    public cmCCG3() {
    }

    public cmCCG3(int cardId, int movements) {
        this.cardId = cardId;
        this.movements = movements;
        setType("CCG3");
    }
}
