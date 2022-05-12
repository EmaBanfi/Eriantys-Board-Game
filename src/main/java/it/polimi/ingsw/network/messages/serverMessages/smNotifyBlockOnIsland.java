package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyBlockOnIsland extends ServerMessage{

    private int island;
    private boolean blocked;

    public smNotifyBlockOnIsland() {
    }

    public smNotifyBlockOnIsland(String message, int island, boolean blocked) {
        super(message);
        this.island = island;
        this.blocked = blocked;
        setType("notify block on island");
    }

}
