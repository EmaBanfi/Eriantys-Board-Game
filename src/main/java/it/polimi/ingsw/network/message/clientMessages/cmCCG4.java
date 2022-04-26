package it.polimi.ingsw.network.message.clientMessages;


import it.polimi.ingsw.network.server.model.StudentColor;

public class cmCCG4 extends ClientMessage{
    int cardId;
    StudentColor color;

    public cmCCG4(int cardId, StudentColor color) {
        this.cardId = cardId;
        this.color = color;
    }
}
