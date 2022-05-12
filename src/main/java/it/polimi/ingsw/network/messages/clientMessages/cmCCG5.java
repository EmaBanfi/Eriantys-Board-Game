package it.polimi.ingsw.network.messages.clientMessages;

@SuppressWarnings("FieldCanBeLocal")
public class cmCCG5 extends ClientMessage{

    private int cardId;
    private int island;

    public cmCCG5() {
    }

    public cmCCG5(int cardId, int island) {
        this.cardId = cardId;
        this.island = island;
        setType("CCG5");
    }

}
