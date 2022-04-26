package it.polimi.ingsw.network.message.serverMessages;

public class smNotifyEndGame extends ServerMessage{
    String endCause;
    boolean immediate;

    public smNotifyEndGame(String endCause, boolean immediate) {
        this.endCause = endCause;
        this.immediate = immediate;
    }
}
