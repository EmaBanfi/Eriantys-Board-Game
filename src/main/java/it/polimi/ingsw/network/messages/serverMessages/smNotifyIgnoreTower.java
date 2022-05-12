package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyIgnoreTower extends ServerMessage{

    private int island;
    private boolean ignored;

    public smNotifyIgnoreTower() {
    }

    public smNotifyIgnoreTower(String message, int island, boolean ignored) {
        super(message);
        this.island = island;
        this.ignored = ignored;
        setType("notify ignore tower");
    }

}
