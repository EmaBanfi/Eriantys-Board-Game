package it.polimi.ingsw.network.messages.clientMessages;

@SuppressWarnings("FieldCanBeLocal")
public class CCG5 extends ClientMessage{

    private int cardId;
    private int island;

    public CCG5() {
    }

    public CCG5(int cardId, int island) {
        this.cardId = cardId;
        this.island = island;
        setType("CCG5");
    }

}
