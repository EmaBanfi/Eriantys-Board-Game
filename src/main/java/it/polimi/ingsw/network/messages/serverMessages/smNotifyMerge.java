package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyMerge extends ServerMessage {

    private int island1;
    private int island2;

    public smNotifyMerge() {
    }

    public smNotifyMerge(String message, int island1, int island2) {
        super(message);
        this.island1 = island1;
        this.island2 = island2;
        setType("notify merge");
    }

}
