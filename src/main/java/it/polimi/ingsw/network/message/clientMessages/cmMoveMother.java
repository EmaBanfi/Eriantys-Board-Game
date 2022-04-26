package it.polimi.ingsw.network.message.clientMessages;

public class cmMoveMother extends ClientMessage{
    int position;

    public cmMoveMother(int position) {
        this.position = position;
    }
}
