package it.polimi.ingsw.network.messages.clientMessages;

public class cmCCG3 extends ClientMessage {
    int cardId;
    int movements;

    public cmCCG3(int cardId, int movements) {
        this.cardId = cardId;
        this.movements = movements;
    }
}
