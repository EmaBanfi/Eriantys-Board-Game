package it.polimi.ingsw.network.messages.clientMessages;

@SuppressWarnings("FieldCanBeLocal")
public class cmMoveMother extends ClientMessage {

    private int position;

    public cmMoveMother() {
    }

    public cmMoveMother(int position) {
        this.position = position;
        setType("move mother");
    }

}
