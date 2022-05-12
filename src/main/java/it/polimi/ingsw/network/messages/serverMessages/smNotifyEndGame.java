package it.polimi.ingsw.network.messages.serverMessages;

@SuppressWarnings("FieldCanBeLocal")
public class smNotifyEndGame extends ServerMessage {

    private String endCause;

    public smNotifyEndGame() {
    }

    public smNotifyEndGame(String message, String endCause) {
        super(message);
        this.endCause = endCause;
        setType("notify end game");
    }

}
