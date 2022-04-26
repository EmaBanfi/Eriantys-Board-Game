package it.polimi.ingsw.network.message.serverMessages;

public class smAskMoveMother extends ServerMessage{
    int maxPosition;

    public smAskMoveMother(int maxPosition) {
        this.maxPosition = maxPosition;
    }
}
