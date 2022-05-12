package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.model.StudentColor;

@SuppressWarnings("FieldCanBeLocal")
public class cmCCG4 extends ClientMessage{

    private int cardId;
    private StudentColor color;

    public cmCCG4() {
    }

    public cmCCG4(int cardId, StudentColor color) {
        this.cardId = cardId;
        this.color = color;
        setType("CCG4");
    }

}
