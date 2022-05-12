package it.polimi.ingsw.network.messages.clientMessages;

@SuppressWarnings("FieldCanBeLocal")
public class cmCCG2 extends ClientMessage{

    private int cardId;

    public cmCCG2() {
    }

    public cmCCG2(int cardId) {
        this.cardId = cardId;
        setType("CCG2");
    }

}
