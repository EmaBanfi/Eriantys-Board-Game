package it.polimi.ingsw.network.messages.serverMessages;

public class smNotifyIgnoreTower extends ServerMessage{
    int island;
    boolean ignored;

    public smNotifyIgnoreTower(int island, boolean ignored) {
        this.island = island;
        this.ignored = ignored;
    }
}
