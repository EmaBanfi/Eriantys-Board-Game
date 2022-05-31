package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.server.ClientHandler;

public class RefillRequest extends ClientMessage {

    private int cardId;
    private int studentsOnCard;

    public RefillRequest() {
    }

    public RefillRequest(int cardId, int studentsOnCard) {
        this.cardId = cardId;
        this.studentsOnCard = studentsOnCard;
        setType("refill request");
    }

    @Override
    public void processMessage(ClientHandler handler) {
        handler.getServer().getController().refillCard(cardId, studentsOnCard);
    }
}
