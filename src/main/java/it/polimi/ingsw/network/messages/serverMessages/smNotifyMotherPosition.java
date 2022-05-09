package it.polimi.ingsw.network.messages.serverMessages;

public class smNotifyMotherPosition extends ServerMessage{
    int island;

    public smNotifyMotherPosition(int island) {
        this.island = island;
    }
}
