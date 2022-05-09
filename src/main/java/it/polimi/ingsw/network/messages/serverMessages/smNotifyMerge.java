package it.polimi.ingsw.network.messages.serverMessages;

public class smNotifyMerge extends ServerMessage{
    int island1;
    int island2;

    public smNotifyMerge(int island1, int island2) {
        this.island1 = island1;
        this.island2 = island2;
    }
}
