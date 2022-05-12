package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyMotherPosition extends ServerMessage {

    private int island;

    public smNotifyMotherPosition() {
    }

    public smNotifyMotherPosition(String message, int island) {
        super(message);
        this.island = island;
        setType("notify mother position");
    }
}
