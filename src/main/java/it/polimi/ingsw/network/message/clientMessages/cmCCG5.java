package it.polimi.ingsw.network.message.clientMessages;

public class cmCCG5 extends ClientMessage{
    int cardId;
    int island;

    public cmCCG5(int cardId, int island) {
        this.cardId = cardId;
        this.island = island;
    }
}
