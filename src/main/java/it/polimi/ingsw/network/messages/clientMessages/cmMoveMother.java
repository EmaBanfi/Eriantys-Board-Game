package it.polimi.ingsw.network.messages.clientMessages;

public class cmMoveMother extends ClientMessage{
    int position;

    public cmMoveMother(int position) {
        this.position = position;
    }
}
