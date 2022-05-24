package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.model.StudentColor;

@SuppressWarnings("FieldCanBeLocal")
public class CCG4 extends ClientMessage{

    private int cardId;
    private StudentColor color;

    public CCG4() {
    }

    public CCG4(int cardId, StudentColor color) {
        this.cardId = cardId;
        this.color = color;
        setType("CCG4");
    }

}
