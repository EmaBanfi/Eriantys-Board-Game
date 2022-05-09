package it.polimi.ingsw.network.messages.serverMessages;

public class smNotifyBlockOnIsland extends ServerMessage{
    int island;
    boolean blocked;

    public smNotifyBlockOnIsland(int island, boolean blocked) {
        this.island = island;
        this.blocked = blocked;
    }
}
